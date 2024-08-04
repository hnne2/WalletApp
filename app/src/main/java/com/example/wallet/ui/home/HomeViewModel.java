package com.example.wallet.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wallet.MyApiService;
import com.example.wallet.sharedPref.SheredPrefsRepository;
import com.example.wallet.models.Person;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class HomeViewModel extends ViewModel {
    @Inject
    public HomeViewModel(){
    }
    MutableLiveData<List<Person>> mutableFriendList;


    public MutableLiveData<List<Person>> getMutableFriendList() {
        if (mutableFriendList==null){
            mutableFriendList=new MutableLiveData<>();
        }
        return mutableFriendList;
    }



    @Inject
    @Named("withToken")
    MyApiService apiService;
    @Inject
    SheredPrefsRepository mSheredPrefsRepository;



    public void getFrindsList(String friendList){
        apiService.getFrindsList(friendList).enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.body()!=null){
                    if (response.body().size()>0){
                    getMutableFriendList().setValue(response.body());}
                }else System.out.println("Friendlist пустой ответ");
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                System.out.println("FrindsList-OnFailur");
            }
        });

    }
}