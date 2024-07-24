package com.example.wallet.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleCoroutineScope;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.wallet.PersonDialog.PersonDialogFragment;
import com.example.wallet.R;
import com.example.wallet.databinding.FragmentHomeBinding;
import com.example.wallet.models.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        List<FrendsItem> frends = new ArrayList<>();

        //список друзей
        Person person = getActivity().getIntent().getParcelableExtra("person");
        homeViewModel.getFrindsList(person.getFrendslistid());

        System.out.println(person.getCity());

        FrendsRecyclerViewAdapter.OnStateClickListener frendsRecylckerViewclickListener = new FrendsRecyclerViewAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(FrendsItem frend, int position) {
                PersonDialogFragment personDialogFragment =  new PersonDialogFragment();
                personDialogFragment.setUsername(frend.getUsername());

                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.homeFragmentLayout,personDialogFragment);
                fragmentTransaction.commit();
            }
        };

        homeViewModel.getMutableFriendList().observe(getViewLifecycleOwner(), new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> people) {
                frends.clear();
                Collections.sort(people, Comparator.comparing(Person::getCapital).reversed());
                for (int i = 0; i < people.size(); i++) {
                    frends.add(new FrendsItem(R.drawable.avatar,people.get(i).userfio,people.get(i).getUsername(),String.valueOf(i+1),String.valueOf(people.get(i).getCapital())));
                }
                binding.frendsRecyclerView.setAdapter(new FrendsRecyclerViewAdapter(getContext(),frends,frendsRecylckerViewclickListener));
                binding.frendsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}