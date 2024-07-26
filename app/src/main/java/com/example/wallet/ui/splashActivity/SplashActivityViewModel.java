package com.example.wallet.ui.splashActivity;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wallet.MyApiService;
import com.example.wallet.sharedPref.SheredPrefsRepository;
import com.example.wallet.models.Person;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class SplashActivityViewModel extends ViewModel {
    @Inject
    public SplashActivityViewModel(){
    }
    @Inject
    @Named("withToken")
    MyApiService apiService;
    @Inject
    SheredPrefsRepository mSheredPrefsRepository;
    MutableLiveData<Person> userInfoSucsess;
    public MutableLiveData<Person> getUserInfoSucsess() {
        if(userInfoSucsess==null){
            userInfoSucsess = new MutableLiveData<>();
        }
        return userInfoSucsess;
    }
    public void getUserInfo(){
        apiService.userByLogin(mSheredPrefsRepository.getLogPasEncode("login")).enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if (response.body()!=null){
                    getUserInfoSucsess().setValue(response.body());
                    Log.d("TAG", "onResponse:"+response.body());

                }
            }
            @Override
            public void onFailure(Call<Person> call, Throwable t) {
            }
        });
    }

    public void updatePerson(Person person){
        apiService.updatePerson(person).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body()!=null){
                    Log.e("my","updatePersonSucsses");
                }else Log.e("my","erorUpdate");
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

}
