package com.example.wallet.ui.rankings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.wallet.PersonDialog.PersonDialogFragment;
import com.example.wallet.R;
import com.example.wallet.ui.home.FrendsItem;
import com.example.wallet.ui.home.FrendsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class searchFragment extends Fragment {
    List<FrendsItem> frendsItems = new ArrayList<>();


    @Inject
 public searchFragment(){
 }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.search_fragment, container, false);
        RankingsViewModel viewModel =new ViewModelProvider(this).get(RankingsViewModel.class);

        RecyclerView searchRecyclerView = view.findViewById(R.id.searchRecuclerview);
        EditText searhEditText = view.findViewById(R.id.SearchEditText);
        Log.d("TAG", "onCreateView:  "+viewModel.toString());
        ImageButton searchButton = view.findViewById(R.id.searchFragmentSearchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.SearchPersons(searhEditText.getText().toString());
            }
        });
        FrendsRecyclerViewAdapter.OnFrendsClickListener rankingsClickListener = new FrendsRecyclerViewAdapter.OnFrendsClickListener() {
            @Override
            public void onFrendsClick(FrendsItem frend, int position) {
                PersonDialogFragment personDialogFragment = PersonDialogFragment.newInstance(frend.getUsername());
                FragmentManager fragmentManager = getChildFragmentManager();
               FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.searchFragmentConstrauntLayout,personDialogFragment);
                fragmentTransaction.commit();
            }
        };
        viewModel.getSearchPersons().observe(getViewLifecycleOwner(), people -> {
            if (people!=null) {
                frendsItems.clear();
                for (int i = 0; i < people.size(); i++) {
                    frendsItems.add(new FrendsItem(R.drawable.avatar, people.get(i).userfio, people.get(i).getUsername(), String.valueOf(i + 1), String.valueOf(people.get(i).getCapital())));
                }
                searchRecyclerView.setAdapter(new FrendsRecyclerViewAdapter(getContext(), frendsItems, rankingsClickListener));
                searchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
        EditText searchEditTextview = view.findViewById(R.id.SearchEditText);
        searchEditTextview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.SearchPersons(searhEditText.getText().toString());
            }
        });
        return view;
    }
}