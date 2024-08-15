package com.example.wallet.ui.items;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.wallet.ItemTextFragment;
import com.example.wallet.R;
import com.example.wallet.databinding.FragmentItemsBinding;
import com.example.wallet.models.Person;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class itemsFragment extends Fragment {
    RecyclerView itemsRecyclerView;
    List<ItemsItem> itemList;
    FragmentItemsBinding binding;
    Person person;
    SwipeRefreshLayout swipeRefreshLayout;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentItemsBinding.inflate(inflater,container,false);
        View view =binding.getRoot();
                person = getActivity().getIntent().getParcelableExtra("person");
        ItemsViewModel itemsViewModel = new ViewModelProvider(this).get(ItemsViewModel.class);
        itemsViewModel.getItems();
        itemList = new ArrayList<>();
        swipeRefreshLayout =view.findViewById(R.id.swipeRefreshLayouItemsFragment);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                itemsViewModel.getItems();
            }
        });
        ItemsRecyclerViewAdapter.onItemClickListener itemClickListener = (itemsItem, position) -> {
            ItemTextFragment itemTextFragment = ItemTextFragment.newInstance(itemsItem.description);
            fragmentManager = getChildFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.ConstraintLayoutItemsFragments, itemTextFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        };
        itemsRecyclerView=view.findViewById(R.id.recyclerViewItemsFragment);
        itemsRecyclerView.setVisibility(View.VISIBLE);
        itemsViewModel.getMutableItemsResult().observe(getViewLifecycleOwner(), itemsItems -> {
            if (itemsItems.size()!=itemList.size()){
                itemList=itemsItems;
                itemsRecyclerView.setAdapter(new ItemsRecyclerViewAdapter(person.getId(),itemsViewModel,getContext(),itemList,itemClickListener));
                itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
            swipeRefreshLayout.setRefreshing(false);
        });
        Log.d("TAG", "onCreateView: "+itemList.size());



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        itemsRecyclerView=getView().findViewById(R.id.recyclerViewItemsFragment);
        itemsRecyclerView.setVisibility(View.VISIBLE);
    }
}