package com.example.wallet.ui.items;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wallet.R;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class itemsFragment extends Fragment {
    RecyclerView itemsRecyclerView;
    List<ItemsItem> itemList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_items, container, false);
        ItemsViewModel itemsViewModel = new ViewModelProvider(this).get(ItemsViewModel.class);
        itemsViewModel.getItems();
        itemList = new ArrayList<>();
        ItemsRecyclerViewAdapter.onItemClickListener itemClickListener = (itemsItem, position) -> {
            //клик
        };
        itemsRecyclerView=view.findViewById(R.id.recyclerViewItemsFragment);
        itemsViewModel.getMutableItemsResult().observe(getViewLifecycleOwner(), new Observer<List<ItemsItem>>() {
            @Override
            public void onChanged(List<ItemsItem> itemsItems) {
            itemList=itemsItems;
                itemsRecyclerView.setAdapter(new ItemsRecyclerViewAdapter(getContext(),itemList,itemClickListener));
                itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
        Log.d("TAG", "onCreateView: "+itemList.size());



        return view;
    }
}