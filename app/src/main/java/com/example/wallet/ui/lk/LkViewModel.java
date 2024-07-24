package com.example.wallet.ui.lk;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wallet.MyApiService;
import com.example.wallet.sharedPref.SheredPrefsRepository;
import com.example.wallet.models.Person;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LkViewModel extends ViewModel {
        private MutableLiveData<Person> personMutableLiveData;

    public MutableLiveData<Person> getPersonMutableLiveData() {
        if (personMutableLiveData==null){
            personMutableLiveData= new MutableLiveData<>();
        }
        return personMutableLiveData;
    }

    @Inject
    @Named("withToken")
    MyApiService apiService;
    @Inject
    SheredPrefsRepository msheredPrefsRepository;
    @Inject
    public LkViewModel() {
    }



}