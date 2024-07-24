package com.example.wallet.ui.rankings;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wallet.MyApiService;
import com.example.wallet.models.Person;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class RankingsViewModel extends ViewModel {
  private MutableLiveData<List<Person>> rangingsCityList;
  private MutableLiveData<List<Person>> rangingsCountryList;
  private MutableLiveData<List<Person>> searchPersons;
    public MutableLiveData<List<Person>> getMytableRangingsCountryList() {
        if (rangingsCountryList==null){
            rangingsCountryList=new MutableLiveData<>();
        }
        return rangingsCountryList;
    }

    public MutableLiveData<List<Person>> getMytableRangingsCityList() {
        if (rangingsCityList==null){
            rangingsCityList=new MutableLiveData<>();
        }
        return rangingsCityList;
    }
    public MutableLiveData<List<Person>> getSearchPersons(){
        if (searchPersons==null){
            searchPersons = new MutableLiveData<>();
        }
        return searchPersons;
    }
    @Inject
    @Named("withToken")
    MyApiService apiService;
    @Inject
    public RankingsViewModel(){
    }
   public void getRangingsCity(String city){
    apiService.rankingsCity(city).enqueue(new Callback<List<Person>>() {
        @Override
        public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
            if (response.body()!=null) {
                if (response.body().size() > 0) {
                    getMytableRangingsCityList().setValue(response.body());
                }
            }
        }
        @Override
        public void onFailure(Call<List<Person>> call, Throwable t) {
        }
    });
   }
    public void getRangingsCountry(String country){
        apiService.rankingsCountry(country).enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.body()!=null) {
                    if (response.body().size() > 0) {
                        getMytableRangingsCountryList().setValue(response.body());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
            }
        });
    }
    public void SearchPersons(String chars){
    apiService.searchPersons(chars).enqueue(new Callback<List<Person>>() {
        @Override
        public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
            getSearchPersons().setValue(response.body());
        }

        @Override
        public void onFailure(Call<List<Person>> call, Throwable t) {

        }
    });
    }
}