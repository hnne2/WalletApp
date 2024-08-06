package com.example.wallet.ui.lk;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.example.wallet.FoundSms.FoundSms;
import com.example.wallet.R;
import com.example.wallet.sharedPref.SheredPrefsRepository;
import com.example.wallet.databinding.FragmentLkBinding;
import com.example.wallet.models.Person;
import com.example.wallet.ui.avtarization.avtarization;
import com.example.wallet.ui.splashActivity.SplashActivityViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
    Person person;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        SplashActivityViewModel splashActivityViewModel = new ViewModelProvider(this).get(SplashActivityViewModel.class);
        binding = FragmentLkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        FoundSms foundSms = new FoundSms();
        person = getActivity().getIntent().getParcelableExtra("person");
        BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);

        ImageView avatarImageView = root.findViewById(R.id.AvatarImageView);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child(person.getAvatarlink());
        GlideApp.with(this)
                .load(imageRef)
                .into(avatarImageView);

        binding.swipeRefreshLayoutLKkFragment.setOnRefreshListener(() ->{
                    Log.d("TAG", "onCreateView: "+person.toString());
                person =foundSms.updateCapital(person,getActivity().getContentResolver());
                binding.FiotextView.setText(person.getUserfio());
                if (person.getFrendslistid()!=null){
                    binding.freindButton.setText(String.valueOf(person.getFrendslistid().split(",").length));
                }
                List<BankItemReciclerView> banks = new ArrayList<>();
                banks.add(new BankItemReciclerView("Sberbank",person.getBalansinsber()+"р", R.drawable.spericon));
                banks.add(new BankItemReciclerView("Tincoff",person.getBalansintinkoff()+"р", R.drawable.tinkofficon));
                splashActivityViewModel.updatePerson(person);
        }
        );
        splashActivityViewModel.getUpdateSuccses().observe(getViewLifecycleOwner(), aBoolean -> {
            binding.swipeRefreshLayoutLKkFragment.setRefreshing(false);
        });
        binding.exitImageButton.setOnClickListener(v -> {
            msheredPrefsRepository.pootLogPas("logPassEncode","empty");
            startActivity(new Intent(getContext(), avtarization.class));
            getActivity().finish();
        });
       binding.FiotextView.setText(person.getUserfio());
       if (person.getFrendslistid()!=null){
       binding.freindButton.setText(String.valueOf(person.getFrendslistid().split(",").length));}
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
            navView.setSelectedItemId(R.id.navigation_home);
            });
        binding.buttonPlaceInFriend.setOnClickListener(v -> {
            navView.setSelectedItemId(R.id.navigation_home);

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