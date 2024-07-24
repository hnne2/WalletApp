package com.example.wallet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wallet.ui.home.AvtorizationViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AvtarizationFragment extends Fragment {
    Button LoginButon;
    Button regButton;
    EditText loginEditText;
    EditText passwordEditText;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_avtarization, container, false);
      AvtorizationViewModel  avtorizationViewModel = new ViewModelProvider(getActivity()).get(AvtorizationViewModel.class);
                loginEditText= view.findViewById(R.id.emailEditTextImya);
        passwordEditText= view.findViewById(R.id.passwordEditText);
        LoginButon = view.findViewById(R.id.loginButton);
        LoginButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if (!loginEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty()){
             avtorizationViewModel.login(loginEditText.getText().toString(),passwordEditText.getText().toString());}


            if (loginEditText.getText().toString().isEmpty()){
                Toast.makeText(getContext(),"Введите логин",Toast.LENGTH_SHORT).show();
            }

                if (passwordEditText.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Введите пароль",Toast.LENGTH_SHORT).show();
                }
            }
        });

       regButton =view.findViewById(R.id.registbutton);
       regButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            fragmentManager = getActivity().getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.avtarization_registration, new Fragment_registration());
            fragmentTransaction.commit();


           }
       });

        return view;
    }
}