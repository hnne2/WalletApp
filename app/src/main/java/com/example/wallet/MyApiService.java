package com.example.wallet;

import com.example.wallet.models.Person;
import com.example.wallet.ui.home.LoginBody;
import com.example.wallet.ui.items.ItemsItem;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface MyApiService {
    @GET("rankings/city/{city}")
    Call<List<Person>> rankingsCity(@Path("city") String city);

    @GET("rankings/country/{countryName}")
    Call<List<Person>> rankingsCountry(@Path("countryName") String countryName);

    @GET("user/{id}")
    Call<ResponseBody> userById(@Path("id") String id);
    @GET("findByUsername/{username}")
    Call<Person> userByLogin(@Path("username") String login);
    @POST("process_login")
    Call<ResponseBody> login(@Body RequestBody loginBody);
    @GET("findByUsername/{username}")
    Call<Person> getUserData(@Path("username") String username, @Header("Authorization") String authorization);

    @GET("friendsById/{idString}")
    Call<List<Person>> getFrindsList(@Path("idString") String idString);
    @POST("update")
    Call<ResponseBody> updatePerson(@Body Person person);
    @GET("findPerson/{chars}")
    Call<List<Person>> searchPersons(@Path("chars") String chars);
    @POST("registration")
    Call<ResponseBody> regestration(@Body Person person);

    @GET("posts")
    Call<List<ItemsItem>> getItems();

}
