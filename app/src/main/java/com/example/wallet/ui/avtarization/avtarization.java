package com.example.wallet.ui.avtarization;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.wallet.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;


import com.example.wallet.R;
import com.example.wallet.sharedPref.SheredPrefsRepository;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class avtarization extends AppCompatActivity {
    AvtorizationViewModel avtorizationViewModel;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    @Inject
    SheredPrefsRepository msheredPrefsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avtarization);
        avtorizationViewModel = new ViewModelProvider(this).get(AvtorizationViewModel.class);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.avtarization_registration,new AvtarizationFragment());
        fragmentTransaction.commit();

    }
}