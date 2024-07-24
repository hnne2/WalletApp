package com.example.wallet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment_registration extends Fragment {
    Button backToLiginButton;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        backToLiginButton = view.findViewById(R.id.backLogin);
        backToLiginButton.setOnClickListener(v -> {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.avtarization_registration, new AvtarizationFragment());
        fragmentTransaction.commit();
        });
        return view;
    }
}