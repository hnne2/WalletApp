package com.example.wallet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.wallet.AvtarizationFragment;
import com.example.wallet.MainActivity;
import com.example.wallet.ui.home.AvtorizationViewModel;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;


import com.example.wallet.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class avtarization extends AppCompatActivity {
    AvtorizationViewModel avtorizationViewModel;

    private AppBarConfiguration appBarConfiguration;

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avtarization);
        avtorizationViewModel = new ViewModelProvider(this).get(AvtorizationViewModel.class);
        avtorizationViewModel.getLoginSuccses().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean liginSuccses) {
                Log.e("pbsrt","22");
                if (liginSuccses){

                startActivity(new Intent(avtarization.this, MainActivity.class));
                }else Toast.makeText(avtarization.this, "Не верный логин или пароль",Toast.LENGTH_SHORT).show();

            }
        });



        fragmentManager =getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        AvtarizationFragment avtarizationFragment= new AvtarizationFragment();
        fragmentTransaction.add(R.id.avtarization_registration,avtarizationFragment);
        fragmentTransaction.commit();


    }


}