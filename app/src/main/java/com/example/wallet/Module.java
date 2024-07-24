package com.example.wallet;

import android.content.Context;
import android.util.Log;

import com.example.wallet.sharedPref.SheredPrefsRepository;

import javax.inject.Named;

import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@dagger.Module
@InstallIn(SingletonComponent.class)
public class Module {
    @Provides
    public OkHttpClient provideOkHttpClient(SheredPrefsRepository sheredPrefsRepository) {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();

                    // Добавляем заголовок "Authorization" к каждому запросу
                    Request authorizedRequest = originalRequest.newBuilder()
                            .header("Authorization", sheredPrefsRepository.getLogPasEncode("logPassEncode"))
                            .build();
                    Log.e("my","создал ок хтпп");
                    return chain.proceed(authorizedRequest);
                })
                .build();
    }
    @Provides
    @Named("withoutToken")
        public MyApiService getMyApiService(){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://walletserver-t5zt.onrender.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        Log.e("my","создал ретрофит withoutToken");
        return retrofit.create(MyApiService.class);
        }
    @Provides
    @Named("withToken")
    public MyApiService provideMyApiService(OkHttpClient okHttpClient) {
        Log.e("my","создал ретрофит c токеном");
        return new Retrofit.Builder()
                .baseUrl("https://walletserver-t5zt.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient) // Установка OkHttpClient для Retrofit
                .build()
                .create(MyApiService.class);
    }

    @Provides
    public  SheredPrefsRepository getSheredPefRepisitory(@ApplicationContext Context context){
        return new SheredPrefsRepository(context);
    }
}
