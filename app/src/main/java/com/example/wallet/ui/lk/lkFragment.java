package com.example.wallet.ui.lk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.wallet.R;
import com.example.wallet.sharedPref.SheredPrefsRepository;
import com.example.wallet.databinding.FragmentLkBinding;
import com.example.wallet.models.Person;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class lkFragment extends Fragment {
    @Inject
    SheredPrefsRepository msheredPrefsRepository;

    private FragmentLkBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentLkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Person person = getActivity().getIntent().getParcelableExtra("person");
        LkViewModel lkViewModel = new ViewModelProvider(getActivity()).get(LkViewModel.class);

       binding.FiotextView.setText(person.getUserfio());
       if (person.getFrendslistid()!=null){
       binding.freindButton.setText(String.valueOf(person.getFrendslistid().split(",").length));}

        System.out.println(person.getPassword());

        List<BankItemReciclerView> banks = new ArrayList<>();
      banks.add(new BankItemReciclerView("Sberbank",person.getBalansintinkoff()+"р", R.drawable.spericon));
        banks.add(new BankItemReciclerView("Tincoff",person.getBalansinsber()+"р", R.drawable.tinkofficon));
        WallatsRecyclerViewAdapter.OnStateClickListener WalletClickListener = new WallatsRecyclerViewAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(BankItemReciclerView bank, int position) {
                //нажатие на банк
            }
        };
        binding.BalansRecyclerView.setAdapter(new WallatsRecyclerViewAdapter(getContext(),banks,WalletClickListener));
        binding.BalansRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.freindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                }
        });
        binding.buttonPlaceInFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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