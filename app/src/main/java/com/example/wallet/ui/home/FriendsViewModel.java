package com.example.wallet.ui.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wallet.MyApiService;
import com.example.wallet.models.AddFriendResponseBody;
import com.example.wallet.sharedPref.SheredPrefsRepository;
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
public class FriendsViewModel extends ViewModel {
    @Inject
    public FriendsViewModel(){
    }
    MutableLiveData<List<Person>> mutableFriendList;
    MutableLiveData<List<Person>> mutableRequestFrinedList;
    MutableLiveData<Boolean> mutableAddFriendSuccses;
    MutableLiveData<Boolean> mutableAddFriendRequestSuccses;
    private MutableLiveData<Integer> selectedPosition = new MutableLiveData<>(1);

    public MutableLiveData<Integer> getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int position) {
        selectedPosition.setValue(position);
    }


        public MutableLiveData<List<Person>> getMutableFriendList() {
        if (mutableFriendList==null){
            mutableFriendList=new MutableLiveData<>();
        }
        return mutableFriendList;
    }
    public MutableLiveData<List<Person>> getMutableRequestFrinedList() {
        if (mutableRequestFrinedList==null){
            mutableRequestFrinedList=new MutableLiveData<>();
        }
        return mutableRequestFrinedList;
    }
    public MutableLiveData<Boolean> getMutableAddFriendSuccses() {
        if (mutableAddFriendSuccses==null){
            mutableAddFriendSuccses=new MutableLiveData<>();
        }
        return mutableAddFriendSuccses;
    } public MutableLiveData<Boolean> getMutableAddFriendRequestSuccses() {
        if (mutableAddFriendRequestSuccses==null){
            mutableAddFriendRequestSuccses=new MutableLiveData<>();
        }
        return mutableAddFriendRequestSuccses;
    }
    MutableLiveData<Person> MutableUpdateSuccses;
    public MutableLiveData<Person> getUpdateSuccses() {
        if(MutableUpdateSuccses==null){
            MutableUpdateSuccses = new MutableLiveData<>();
        }
        return MutableUpdateSuccses;
    }
    MutableLiveData<Person> userInfoSucsess;

    public MutableLiveData<Person> getUserInfoSucsess() {
        if(userInfoSucsess==null){
            userInfoSucsess = new MutableLiveData<>();
        }
        return userInfoSucsess;
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
                Log.d("TAG", "onResponse getFrindsList");

                    getMutableFriendList().setValue(response.body());
                Log.d("TAG", "onResponse:Friendlist пустой ответ");
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                System.out.println("FrindsList-OnFailur");
            }
        });
    }
    public void getRequstFrindsList(String friendList){
        apiService.getRequstFrindsList(friendList).enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                Log.d("TAG", "onResponse getRequstFrindsList");
                getMutableRequestFrinedList().setValue(response.body());
                 Log.d("TAG", "on emty getRequstFrindsList");
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                 Log.d("TAG", "on failure getRequstFrindsList");
            }
        });
    }
    public void addFriend(int addId,int addedId){
        apiService.addTofriend(new AddFriendResponseBody(addId,addedId)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body()!=null){
        getMutableAddFriendSuccses().setValue(true);
                }else System.out.println("addFriend пустой ответ");

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("addFriend-OnFailur");
            }
        });
    }
    public void updatePerson(Person person){
        apiService.updatePerson(person).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body()!=null){
                    Log.e("TAG","updatePersonSucsses"+person.getUsername());
                    getUpdateSuccses().setValue(person);
                }else{ Log.e("TAG","erorUpdate");
                    getUpdateSuccses().setValue(person);}
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                getUpdateSuccses().setValue(person);
                Log.e("TAG","onFailure");
            }
        });
    }
    public void getUserInfo(){
        apiService.userByLogin(mSheredPrefsRepository.getLogPasEncode("login")).enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if (response.body()!=null){
                    getUserInfoSucsess().setValue(response.body());
                    Log.d("TAG", "onResponseuserByLogin"+response.body().getIsOpen_acc());
                }
            }
            @Override
            public void onFailure(Call<Person> call, Throwable t) {
            }
        });
    }
    public void addTofriendrequests(int appPersonId,int komyOtpravitZayavrkuID){
    apiService.addTofriendrequests(new AddFriendResponseBody(appPersonId,komyOtpravitZayavrkuID)).enqueue(new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.body()!=null){
            getMutableAddFriendRequestSuccses().setValue(true);
            }else getMutableAddFriendRequestSuccses().setValue(false);
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
         getMutableAddFriendRequestSuccses().setValue(false);
        }
    });}
}