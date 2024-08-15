package com.example.wallet.ui.sitings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.wallet.R;
import com.example.wallet.databinding.FragmentLkBinding;
import com.example.wallet.databinding.FragmentSettingsBinding;
import com.example.wallet.models.Person;
import com.example.wallet.ui.splashActivity.SplashActivityViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingsFragment extends Fragment {
        FragmentSettingsBinding binding;
        SplashActivityViewModel splashActivityViewModel;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View view=binding.getRoot();
        splashActivityViewModel = new ViewModelProvider(this).get(SplashActivityViewModel.class);
        Person appPerson =getActivity().getIntent().getParcelableExtra("person");
        if (appPerson.getIsOpen_acc()==0){
            Log.d("TAG", "isIs_open_acc"+appPerson.getIsOpen_acc());
            binding.switchBlockProfilSittingsFragment.setChecked(true);
        }
        binding.switchBlockProfilSittingsFragment.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {//вкл
                Log.d("TAG", "isChecked: ");
                appPerson.setIs_open_acc(0);
                splashActivityViewModel.updatePerson(appPerson);
                binding.switchBlockProfilSittingsFragment.setClickable(false);
                binding.switchBlockProfilSittingsFragment.setEnabled(true);
            } else {
                appPerson.setIs_open_acc(1);
                Log.d("TAG", "elseelseelse:");
                splashActivityViewModel.updatePerson(appPerson);
            }
        });
        splashActivityViewModel.getUpdateSuccses().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.switchBlockProfilSittingsFragment.setClickable(true);
            }
        });
        return view;
    }
}