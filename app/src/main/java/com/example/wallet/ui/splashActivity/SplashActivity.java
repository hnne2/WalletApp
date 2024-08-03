package com.example.wallet.ui.splashActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.Person;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.wallet.FoundSms.FoundSms;
import com.example.wallet.MainActivity;
import com.example.wallet.sharedPref.SheredPrefsRepository;
import com.example.wallet.ui.avtarization.avtarization;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashActivity extends AppCompatActivity {
    @Inject
    SheredPrefsRepository msheredPrefsRepository;
    private static final int REQUEST_SMS_PERMISSION = 1;
    FoundSms foundSms= new FoundSms();
    @Inject
    public SplashActivity(){}
    SplashActivityViewModel splashActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         splashActivityViewModel = new ViewModelProvider(this).get(SplashActivityViewModel.class);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Если разрешение не предоставлено, запрашиваем его
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    REQUEST_SMS_PERMISSION);
        }else {
            CheckLign();
        }

        splashActivityViewModel.getUserInfoSucsess().observe(this, person -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            foundSms.updateCapital(person,getContentResolver());
            intent.putExtra("person",person);
            Log.e("my","Положил балансы");
            splashActivityViewModel.updatePerson(person);
            startActivity(intent);
            finish();
        });


    }
    void CheckLign(){
        if (msheredPrefsRepository.getLogPasEncode("logPassEncode").equals("empty"))  // проверка на авторизацию
        {
            startActivity(new Intent(this, avtarization.class));
        }
        else {
            splashActivityViewModel.getUserInfo();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SMS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CheckLign();
            } else {
                Toast.makeText(this,"Предоставьте разрешение", Toast.LENGTH_LONG);
            }
        }
    }

}