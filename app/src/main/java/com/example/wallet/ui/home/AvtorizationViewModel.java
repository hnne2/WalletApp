package com.example.wallet.ui.home;

import android.util.Base64;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wallet.MyApiService;
import com.example.wallet.sharedPref.SheredPrefsRepository;
import com.example.wallet.models.Person;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class AvtorizationViewModel extends ViewModel {
    @Inject
    SheredPrefsRepository mSheredPrefsRepository;

    private MutableLiveData<Boolean> LoginSuccses;

    public MutableLiveData<Boolean> getLoginSuccses(){
        if (LoginSuccses ==null){
            LoginSuccses =new MutableLiveData<>();
        }
        return LoginSuccses;
    }

    @Inject
    AvtorizationViewModel(){
    }

    @Inject
    @Named("withoutToken")
    MyApiService apiService;
    public void login(String login, String password){

        String credentials = login + ":" + password;
        String basicAuth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

        apiService.getUserData(login,basicAuth).enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if (response.body()!=null){
                getLoginSuccses().setValue(true);
                    mSheredPrefsRepository.pootLogPas("logPassEncode",basicAuth);
                    mSheredPrefsRepository.pootLogPas("login",login);
                Log.e("запрос",response.body().userfio);

                }else{ Log.e("ошибка",response.message());
                getLoginSuccses().setValue(false);}
            }
            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                getLoginSuccses().setValue(false);
                Log.e("onFailure",t.getMessage());
            }
        });

    }


}
