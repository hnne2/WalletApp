package com.example.wallet.ui.avtarization;

import static android.app.ProgressDialog.show;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.wallet.MainActivity;
import com.example.wallet.R;
import com.example.wallet.XMLParser;
import com.example.wallet.models.City;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Fragment_registration extends Fragment {
    Button backToLiginButton;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    private Map<String, City> cityMap;
    private List<City> cityList;
    EditText nickNameEditText;
    EditText userFioEditText;
    EditText passordEditText;
    EditText confirmPasswordEditText;
    AutoCompleteTextView cityAutoCompleteTextView;
    InputStream inputStream;
    XMLParser parser;
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        AvtorizationViewModel avtorizationViewModel = new ViewModelProvider(getActivity()).get(AvtorizationViewModel.class);
        progressBar = getActivity().findViewById(R.id.progressBarAuth);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Фоновая задача
                InputStream inputStream = getResources().openRawResource(R.raw.rocid);
                XMLParser parser = new XMLParser();
                List<City> cityList = parser.parse(inputStream);
                final Map<String, City> cityMap = new HashMap<>();
                // заполнение мап для поиска выбранного города
                for (City city : cityList) {
                    cityMap.put(city.getName(), city);
                }

                // Обновление UI
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (getActivity() != null) {
                        cityAutoCompleteTextView = view.findViewById(R.id.CityAutoCompleteTextViewFragmentREg);
                        ArrayAdapter<City> adapter = new ArrayAdapter<>(getActivity(),
                                R.layout.auto_coplite_item, cityList);
                        cityAutoCompleteTextView.setAdapter(adapter);
                        cityAutoCompleteTextView.setVisibility(View.VISIBLE);
                        }else Toast.makeText(getContext(), "eror", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        nickNameEditText = view.findViewById(R.id.NIckNamelEditText);
        userFioEditText = view.findViewById(R.id.UserFioEditText);
        passordEditText = view.findViewById(R.id.passwordEditTextRegFrag);
        confirmPasswordEditText= view.findViewById(R.id.confirmPasswordEditTextRegFrag);
        backToLiginButton = view.findViewById(R.id.backLogin);
        backToLiginButton.setOnClickListener(v -> {
            fragmentManager = getActivity().getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.avtarization_registration, new AvtarizationFragment());
            fragmentTransaction.commit();
        });
        Button regButton = view.findViewById(R.id.to_registr_but);
        regButton.setOnClickListener(v -> {
            String selectedCityName = cityAutoCompleteTextView.getText().toString();
            City selectedCity = cityMap.get(selectedCityName);
            String nickname = nickNameEditText.getText().toString();
            String userfio = userFioEditText.getText().toString();
            String password =passordEditText.getText().toString();
            String confirmPassword =confirmPasswordEditText.getText().toString();
            if (!nickname.isEmpty() && !userfio.isEmpty() && selectedCity != null
                    && !password.isEmpty() && confirmPassword.equals(password)
            ) {
                if (progressBar.getVisibility()==View.INVISIBLE){
                avtorizationViewModel.registration(nickname,userfio,String.valueOf(selectedCity.countryId),selectedCity.name,password );
                }
                progressBar.setVisibility(View.VISIBLE);
            }
            if (nickname.isEmpty()){
                Toast.makeText(getContext(),
                        "Введите Никнейм",
                        Toast.LENGTH_SHORT).show();
            }
            if (userfio.isEmpty()){
                Toast.makeText(getContext(),
                        "Введите ИМЯ",
                        Toast.LENGTH_SHORT).show();
            }
            if (selectedCity == null) {
                Toast.makeText(getContext(),
                        "Выберите город из выпадающего списка",
                        Toast.LENGTH_SHORT).show();
            }
            if (password.isEmpty()){
                Toast.makeText(getContext(),
                        "Введите пароль",
                        Toast.LENGTH_SHORT).show();
            }else {
                if (!confirmPassword.equals(password)){
                    Toast.makeText(getContext(),
                            "Пароли не совпадают",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

        avtorizationViewModel.getRegestragionSuccses().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                progressBar.setVisibility(View.INVISIBLE);
                if (aBoolean){
                    Toast.makeText(getContext(),"Регистрация успешна",Toast.LENGTH_LONG);
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.avtarization_registration, new AvtarizationFragment());
                    fragmentTransaction.commit();
                }
            }
        });
        return view;
    }



}


