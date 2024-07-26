package com.example.wallet.ui.avtarization;

import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

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
public class AvtorizationViewModel extends ViewModel {
    private MutableLiveData<Boolean> LoginSuccses;
    private MutableLiveData<Boolean> regestragionSuccses;


    public MutableLiveData<Boolean> getLoginSuccses(){
        if (LoginSuccses ==null){
            LoginSuccses =new MutableLiveData<>();
        }
        return LoginSuccses;
    }
    public MutableLiveData<Boolean> getRegestragionSuccses(){
        if (regestragionSuccses ==null){
            regestragionSuccses =new MutableLiveData<>();
        }
        return regestragionSuccses;
    }


    @Inject
    AvtorizationViewModel(){
    }

    @Inject
    @Named("withoutToken")
    MyApiService apiService;
    public void login(String login, String password,String basicAuth){
        apiService.getUserData(login,basicAuth).enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if (response.body()!=null)
                {
                    getLoginSuccses().setValue(true);
                    Log.e("запрос",response.body().userfio);
                }
                else {
                    Log.e("ошибка",response.message());
                    getLoginSuccses().setValue(false);
                      }
            }
            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                getLoginSuccses().setValue(false);
                Log.e("onFailure",t.getMessage());
            }
        });
    }
    public void registration(String nikName,String userFio,String country,String city,String password){
        apiService.regestration(new Person(nikName,userFio,country,city,password)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body()!=null){
                    getRegestragionSuccses().setValue(true);
                    Log.d("TAG", "onResponse: zaregal");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("TAG", "onResponse: ne zaregal");
            }
        });
    }


}
