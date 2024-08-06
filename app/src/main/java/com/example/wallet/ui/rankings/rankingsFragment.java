package com.example.wallet.ui.rankings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.wallet.PersonDialog.PersonDialogFragment;
import com.example.wallet.R;
import com.example.wallet.sharedPref.SheredPrefsRepository;
import com.example.wallet.databinding.FragmentRankingsBinding;
import com.example.wallet.models.Person;
import com.example.wallet.ui.home.FrendsItem;
import com.example.wallet.ui.home.FrendsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class rankingsFragment extends Fragment {
    @Inject
    public rankingsFragment(){}
    @Inject
    SheredPrefsRepository msheredPrefsRepository;

    private FragmentRankingsBinding binding;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RankingsViewModel rankingsViewModel =
                new ViewModelProvider(this).get(RankingsViewModel.class);
        fragmentManager = getChildFragmentManager();
        Person person= getActivity().getIntent().getParcelableExtra("person");
        binding = FragmentRankingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayoutRankingsFragment);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(()-> rankingsViewModel.getRangingsCity(person.getCity()));
        List<RankingRegion> rankingRegionslist = new ArrayList<>();
        rankingRegionslist.add(new RankingRegion("Город", R.drawable.ic_home_black_24dp));
        rankingRegionslist.add(new RankingRegion("Страна", R.drawable.ic_home_black_24dp));
        rankingRegionslist.add(new RankingRegion("Город", R.drawable.ic_home_black_24dp));

        binding.searchImageButton.setOnClickListener(v -> {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentRangingsConstraintLayout,new searchFragment());
           binding.RangingsRecyclerView.setVisibility(View.INVISIBLE);
           binding.spinnerRegion.setVisibility(View.INVISIBLE);
           binding.textView5.setVisibility(View.INVISIBLE);
           binding.searchImageButton.setVisibility(View.INVISIBLE);
            fragmentTransaction.commit();
        });
        binding.spinnerRegion.setAdapter(new RegionAdapter(getContext(),rankingRegionslist));
        binding.spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position==1)
                {
                    rankingsViewModel.getRangingsCountry(person.getCountry());
                }
                if(position==2)
                {
                    rankingsViewModel.getRangingsCity(person.getCity());
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        List<FrendsItem> frendsItems = new ArrayList<>();
        FrendsRecyclerViewAdapter.OnFrendsClickListener rankingsClickListener = (frend, position) -> {
            PersonDialogFragment personDialogFragment = PersonDialogFragment.newInstance(frend.getUsername());
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragmentRangingsConstraintLayout,personDialogFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        };
       rankingsViewModel.getMytableRangingsCityList().observe(getViewLifecycleOwner(), people -> {
//           if (frendsItems.get(0).equals(people.get(0))){
//               Log.d("TAG", "equals: true");
//           }else  Log.d("TAG", "equals: false");
           frendsItems.clear();
           for (int i = 0; i < people.size(); i++) {
               frendsItems.add(new FrendsItem(R.drawable.avatar,people.get(i).userfio,people.get(i).getUsername(),String.valueOf(i+1),String.valueOf(people.get(i).getCapital())));

           }
           binding.RangingsRecyclerView.setAdapter(new FrendsRecyclerViewAdapter(getContext(),frendsItems,rankingsClickListener));
           binding.RangingsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
           swipeRefreshLayout.setRefreshing(false);
       });
       rankingsViewModel.getMytableRangingsCountryList().observe(getViewLifecycleOwner(), new Observer<List<Person>>() {
           @Override
           public void onChanged(List<Person> people) {
               frendsItems.clear();
               for (int i = 0; i < people.size(); i++) {
                   frendsItems.add(new FrendsItem(R.drawable.avatar,people.get(i).userfio,people.get(i).getUsername(),String.valueOf(i+1),String.valueOf(people.get(i).getCapital())));
               }
               binding.RangingsRecyclerView.setAdapter(new FrendsRecyclerViewAdapter(getContext(),frendsItems,rankingsClickListener));
               binding.RangingsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
           }
       });
        rankingsViewModel.getRangingsCity(person.getCity());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}