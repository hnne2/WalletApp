package com.example.wallet.ui.splashActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.wallet.FoundSms.FoundSms;
import com.example.wallet.MainActivity;
import com.example.wallet.models.Person;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashActivity extends AppCompatActivity {
    private static final int REQUEST_SMS_PERMISSION = 1;
    ArrayList<String> balans = new ArrayList<>();
    FoundSms foundSms= new FoundSms();
    @Inject
    public SplashActivity(){}

    SplashActivityViewModel splashActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         splashActivityViewModel = new ViewModelProvider(this).get(SplashActivityViewModel.class);
        Log.e("my","splashSkreenOpen");



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Если разрешение не предоставлено, запрашиваем его
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    REQUEST_SMS_PERMISSION);
        } else {
            // Если разрешение уже предоставлено, получаем сообщения
            splashActivityViewModel.getUserInfo();

            Log.e("my","разрешение предоставленно");


        }


        splashActivityViewModel.getUserInfoSucsess().observe(this, new Observer<Person>() {
            @Override
            public void onChanged(Person person) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                person.setBalansinsber(foundSms.getSMSMessagesFromAddressTinkoff(getContentResolver()));
                person.setBalansintinkoff(foundSms.getBalansSber(getContentResolver()));
                intent.putExtra("person",person);
                Log.e("my","Положил балансы");
                startActivity(intent);
                splashActivityViewModel.updatePerson(person);
                finish();
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SMS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                splashActivityViewModel.getUserInfo();
                Log.e("log","111");
            } else {
                Toast.makeText(this, "Для продожения необходимо разрешение", Toast.LENGTH_SHORT).show();
            }
        }
    }
}