package com.example.wallet.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.wallet.MainActivity;
import com.example.wallet.PersonDialog.PersonDialogFragment;
import com.example.wallet.R;
import com.example.wallet.databinding.FragmentFrendsBinding;
import com.example.wallet.models.Person;
import com.example.wallet.ui.rankings.RankingRegion;
import com.example.wallet.ui.rankings.RegionAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FriendsFragment extends Fragment {

    private FragmentFrendsBinding binding;
    SwipeRefreshLayout swipeRefreshLayoutFrendsFragment,swipeRefreshLayoutFrendRequsts;
    Person avtarizationPerson;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFrendsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.progressBarFragmetFriends.setVisibility(View.VISIBLE);
        FriendsViewModel friendsViewModel = new ViewModelProvider(this).get(FriendsViewModel.class);
        avtarizationPerson = getActivity().getIntent().getParcelableExtra("person");
        List<FrendsItem> frends = new ArrayList<>();
        List<RankingRegion> rankingRegionslist = new ArrayList<>();
        rankingRegionslist.add(new RankingRegion("Список друзей:", R.drawable.ic_home_black_24dp));
        rankingRegionslist.add(new RankingRegion("Список друзей:", R.drawable.ic_home_black_24dp));
        rankingRegionslist.add(new RankingRegion("Зявки в друзья", R.drawable.ic_home_black_24dp));
        List<RequestItem> requestItemList = new ArrayList<>();
        swipeRefreshLayoutFrendsFragment = view.findViewById(R.id.swipeRefreshHomeFragment);
        swipeRefreshLayoutFrendRequsts = view.findViewById(R.id.swipeRefreshFrendRequests);
        binding.spinerFragmentFriends.setAdapter(new RegionAdapter(getContext(),rankingRegionslist));
        int savedPosition =friendsViewModel.getSelectedPosition().getValue();
        binding.spinerFragmentFriends.setSelection(savedPosition);
        binding.spinerFragmentFriends.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {public void onItemSelected(AdapterView<?> parent, View view, int position, long id)  // спинер - друзья/запросы
            {
                binding.swipeRefreshHomeFragment.setVisibility(View.GONE);

                if(position==2)
                {
                    Log.d("TAG", "onItemSelected: "+avtarizationPerson.getFriendrequest());
                    friendsViewModel.getRequstFrindsList(avtarizationPerson.getFriendrequest());
                    binding.progressBarFragmetFriends.setVisibility(View.VISIBLE);
                }
                if(position==1)
                {
                    friendsViewModel.getFrindsList(avtarizationPerson.getFrendslistid());
                    Log.d("TAG", "onItemSelected: 1");
                    binding.progressBarFragmetFriends.setVisibility(View.VISIBLE);
                }
                friendsViewModel.setSelectedPosition(position);
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        RequstFriendsViewAdapter.OnRequstFriendsClickListener onRequstFriendsClickListener = (requestItem, position) -> {
            PersonDialogFragment personDialogFragment =  PersonDialogFragment.newInstance(requestItem.getUsername());
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.homeFragmentLayout,personDialogFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        };//клик по запросу в др
        FrendsRecyclerViewAdapter.OnFrendsClickListener frendsRecylckerViewclickListener = (frend, position) -> {
            PersonDialogFragment personDialogFragment =  PersonDialogFragment.newInstance(frend.getUsername());
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.homeFragmentLayout,personDialogFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }; // клик по другу
        friendsViewModel.getMutableRequestFrinedList().observe(getViewLifecycleOwner(), people -> { //приход запросов с viewModel
            binding.progressBarFragmetFriends.setVisibility(View.INVISIBLE);
            requestItemList.clear();
            frends.clear();
            if (people!=null){
                binding.emtyTextViewFragmentFriends.setVisibility(View.INVISIBLE);
            requestItemList.clear();
            for (int i = 0; i < people.size(); i++) {
                requestItemList.add(new RequestItem(people.get(i).getAvatarlink(),people.get(i).userfio,people.get(i).getUsername(),people.get(i).getId()));
            }
            }else { Toast.makeText(getContext(),"нет запросов в друзья",Toast.LENGTH_LONG).show();
            binding.emtyTextViewFragmentFriends.setVisibility(View.VISIBLE);
            }
            binding.swipeRefreshHomeFragment.setVisibility(View.GONE);
            binding.swipeRefreshFrendRequests.setVisibility(View.VISIBLE);
            binding.swipeRefreshFrendRequests.setRefreshing(false);
            binding.requstsFriendsRecyclerView.setAdapter(new RequstFriendsViewAdapter(getViewLifecycleOwner(),avtarizationPerson,friendsViewModel,getContext(),requestItemList,onRequstFriendsClickListener));
            binding.requstsFriendsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        });
        friendsViewModel.getMutableFriendList().observe(getViewLifecycleOwner(), people -> { //приход списка друзей из вьюмодел
            binding.progressBarFragmetFriends.setVisibility(View.INVISIBLE);
            frends.clear();
            requestItemList.clear();
            if (people!=null){
                Log.d("TAG", "peoplenull: "+people.size());
                binding.emtyTextViewFragmentFriends.setVisibility(View.INVISIBLE);
            Collections.sort(people, Comparator.comparing(Person::getCapital).reversed());
            for (int i = 0; i < people.size(); i++) {
                frends.add(new FrendsItem(people.get(i).getAvatarlink(),people.get(i).userfio,people.get(i).getUsername(),String.valueOf(i+1),String.valueOf(people.get(i).getCapital())));
            }}else {
                Toast.makeText(getContext(),"Список друзей пуст",Toast.LENGTH_LONG).show();
                binding.emtyTextViewFragmentFriends.setVisibility(View.VISIBLE);
            }
            binding.swipeRefreshHomeFragment.setVisibility(View.VISIBLE);
            binding.swipeRefreshHomeFragment.setRefreshing(false);
            binding.swipeRefreshFrendRequests.setVisibility(View.GONE);
            binding.frendsRecyclerView.setAdapter(new FrendsRecyclerViewAdapter(getContext(),frends,frendsRecylckerViewclickListener));
            binding.frendsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        });

        friendsViewModel.getUpdateSuccses().observe(getViewLifecycleOwner(), person -> {  //при отправки обновления на сервер обновления perdon внутри класса
            avtarizationPerson=person;
        });

        swipeRefreshLayoutFrendsFragment.setOnRefreshListener(() ->  //свайп друзей, новый запрос списка
        {
            friendsViewModel.getUserInfo();
            }
        );
        swipeRefreshLayoutFrendRequsts.setOnRefreshListener(() -> {  //свайп запросов в друзья, новый запрос списка
            friendsViewModel.getUserInfo();
        });
        friendsViewModel.getUserInfoSucsess().observe(getViewLifecycleOwner(), new Observer<Person>() {
            @Override
            public void onChanged(Person person) {
                avtarizationPerson=person;
                getActivity().getIntent().putExtra("person",avtarizationPerson);
                if (binding.swipeRefreshFrendRequests.isRefreshing()){
                    friendsViewModel.getRequstFrindsList(avtarizationPerson.getFriendrequest());
                }
                if (binding.swipeRefreshHomeFragment.isRefreshing()){
                    friendsViewModel.getFrindsList(avtarizationPerson.getFrendslistid());
                }

            }
        });
        return view;
    }
}