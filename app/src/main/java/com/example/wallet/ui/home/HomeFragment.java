package com.example.wallet.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.wallet.PersonDialog.PersonDialogFragment;
import com.example.wallet.R;
import com.example.wallet.databinding.FragmentFrendsBinding;
import com.example.wallet.databinding.FragmentHomeBinding;
import com.example.wallet.models.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private FragmentFrendsBinding binding;
    SwipeRefreshLayout swipeRefreshLayoutHomeFragment;
    Person avtarizationPerson;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentFrendsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        List<FrendsItem> frends = new ArrayList<>();
        avtarizationPerson = getActivity().getIntent().getParcelableExtra("person");

        swipeRefreshLayoutHomeFragment = view.findViewById(R.id.swipeRefreshHomeFragment);
        swipeRefreshLayoutHomeFragment.setRefreshing(true);
        swipeRefreshLayoutHomeFragment.setOnRefreshListener(() ->
                homeViewModel.getFrindsList(avtarizationPerson.getFrendslistid())
        );
        homeViewModel.getFrindsList(avtarizationPerson.getFrendslistid());
        FrendsRecyclerViewAdapter.OnFrendsClickListener frendsRecylckerViewclickListener = (frend, position) -> {
            PersonDialogFragment personDialogFragment =  PersonDialogFragment.newInstance(frend.getUsername());
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.homeFragmentLayout,personDialogFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        };
        homeViewModel.getMutableFriendList().observe(getViewLifecycleOwner(), people -> {
            frends.clear();
            Collections.sort(people, Comparator.comparing(Person::getCapital).reversed());
            for (int i = 0; i < people.size(); i++) {
                frends.add(new FrendsItem(R.drawable.avatar,people.get(i).userfio,people.get(i).getUsername(),String.valueOf(i+1),String.valueOf(people.get(i).getCapital())));
            }
            binding.frendsRecyclerView.setAdapter(new FrendsRecyclerViewAdapter(getContext(),frends,frendsRecylckerViewclickListener));
            binding.frendsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            swipeRefreshLayoutHomeFragment.setRefreshing(false);
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}