package com.example.wallet.ui.lk;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.wallet.R;
import com.example.wallet.sharedPref.SheredPrefsRepository;
import com.example.wallet.databinding.FragmentLkBinding;
import com.example.wallet.models.Person;
import com.example.wallet.ui.avtarization.avtarization;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class lkFragment extends Fragment {
    @Inject
    SheredPrefsRepository msheredPrefsRepository;

    private static final int REQUEST_CALL_PERMISSION = 1;

    private FragmentLkBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Person person = getActivity().getIntent().getParcelableExtra("person");
        LkViewModel lkViewModel = new ViewModelProvider(getActivity()).get(LkViewModel.class);
        binding.exitImageButton.setOnClickListener(v -> {
            msheredPrefsRepository.pootLogPas("logPassEncode","empty");
            startActivity(new Intent(getContext(), avtarization.class));
        });
       binding.FiotextView.setText(person.getUserfio());
       if (person.getFrendslistid()!=null){
       binding.freindButton.setText(String.valueOf(person.getFrendslistid().split(",").length));}
        System.out.println(person.getPassword());
        List<BankItemReciclerView> banks = new ArrayList<>();
        banks.add(new BankItemReciclerView("Sberbank",person.getBalansintinkoff()+"р", R.drawable.spericon));
        banks.add(new BankItemReciclerView("Tincoff",person.getBalansinsber()+"р", R.drawable.tinkofficon));
        WallatsRecyclerViewAdapter.OnStateClickListener WalletClickListener = (bank, position) -> {
            if (bank.BankName.equals("Sberbank")){
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
            } else {
                callUSSD();
            }}
            //клик на банк
        };
        binding.BalansRecyclerView.setAdapter(new WallatsRecyclerViewAdapter(getContext(),banks,WalletClickListener));
        binding.BalansRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.freindButton.setOnClickListener(v -> {
            //клик
            });
        binding.buttonPlaceInFriend.setOnClickListener(v -> {
            // клик
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callUSSD();
            } else {
                Toast.makeText(getContext(),"Предоставьте разрешение",Toast.LENGTH_LONG);
            }
        }
    }
    private void callUSSD() {
        String ussdCode = "*900*01#";
        String encodedUssd = Uri.encode(ussdCode);
        String ussdUri = "tel:" + encodedUssd;
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(ussdUri));
        startActivity(intent);
    }
}