package com.example.wallet.ui.items;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wallet.MyApiService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class ItemsViewModel extends ViewModel {
    MutableLiveData<List<ItemsItem>> items;

    public MutableLiveData<List<ItemsItem>> getMutableItemsResult() {
        if (items==null){
            items= new MutableLiveData<>();
        }
        return items;
    }

    @Inject
    @Named("withToken")
    MyApiService apiService;
    @Inject
    public ItemsViewModel(){
    }
    public void getItems(){
        apiService.getItems().enqueue(new Callback<List<ItemsItem>>() {
            @Override
            public void onResponse(Call<List<ItemsItem>> call, Response<List<ItemsItem>> response) {
                if (response.body()!=null){
                    getMutableItemsResult().setValue(response.body());
                }
                else Log.d("TAG", "itemsIsNull");
            }

            @Override
            public void onFailure(Call<List<ItemsItem>> call, Throwable t) {
                Log.d("TAG", "itemsFailure");
            }
        });
    }

    public void updateItems(ItemsItem item){
        apiService.updateItem(item).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body()!=null){
                    Log.d("TAG", "onResponse: recyclerview sucses zaproz");
                }else  Log.d("TAG", "onResponse: recyclerview null");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("TAG", "onResponse: recyclerview falure");
            }
        });
    }

}
