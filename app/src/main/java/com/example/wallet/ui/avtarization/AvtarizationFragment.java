package com.example.wallet.ui.avtarization;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.wallet.MainActivity;
import com.example.wallet.R;
import com.example.wallet.sharedPref.SheredPrefsRepository;
import com.example.wallet.ui.splashActivity.SplashActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AvtarizationFragment extends Fragment {
    Button LoginButon;
    Button regButton;
    EditText loginEditText;
    EditText passwordEditText;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    @Inject
    SheredPrefsRepository msheredPrefsRepository;
    String login;
    String password;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_avtarization, container, false);
      AvtorizationViewModel  avtorizationViewModel = new ViewModelProvider(getActivity()).get(AvtorizationViewModel.class);
        progressBar = getActivity().findViewById(R.id.progressBarAuth);
      loginEditText= view.findViewById(R.id.NIckNamelEditText);
        passwordEditText= view.findViewById(R.id.passwordEditText);
        LoginButon = view.findViewById(R.id.loginButton);
        LoginButon.setOnClickListener(v -> {
             login = loginEditText.getText().toString();
             password = passwordEditText.getText().toString();
        if (!login.isEmpty() && !password.isEmpty())
            {

                progressBar.setVisibility(View.VISIBLE);
                String basicAuth = "Basic " + Base64.encodeToString((login + ":" + password).getBytes(), Base64.NO_WRAP);
                avtorizationViewModel.login(login,password,basicAuth);
            }
        if (loginEditText.getText().toString().isEmpty()){
            Toast.makeText(getContext(),"Введите логин",Toast.LENGTH_SHORT).show();
        }
            if (passwordEditText.getText().toString().isEmpty()){
                Toast.makeText(getContext(),"Введите пароль",Toast.LENGTH_SHORT).show();
            }
        });
       regButton =view.findViewById(R.id.registbutton);
       regButton.setOnClickListener(v -> {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.avtarization_registration, new Fragment_registration());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
       });
        avtorizationViewModel.getLoginSuccses().observe(getViewLifecycleOwner(), aBoolean -> {
            progressBar.setVisibility(View.INVISIBLE);
            if (aBoolean){
                String basicAuth = "Basic " + Base64.encodeToString((login + ":" + password).getBytes(), Base64.NO_WRAP);
                msheredPrefsRepository.pootLogPas("logPassEncode",basicAuth);
                msheredPrefsRepository.pootLogPas("login",login);
                Log.d("TAG", "onCreateView:"+login + password);
                startActivity(new Intent(getContext(), SplashActivity.class));
                getActivity().finish();
            }else Toast.makeText(getContext(), "Не верный логин или пароль",Toast.LENGTH_SHORT).show();
        });
        return view;
    }
}