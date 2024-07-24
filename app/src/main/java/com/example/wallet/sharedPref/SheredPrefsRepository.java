package com.example.wallet.sharedPref;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SheredPrefsRepository implements SheredPrefsRepositoryImpl{
    Context context;
    public SheredPrefsRepository(Context context){
        this.context=context;

    }

    @Override
    public void pootLogPas(String key,String logPassEncode) {
        SharedPreferences Prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Prefs.edit();
        editor.putString(key, logPassEncode);
        editor.apply();
        Log.e("pootToken", "положил токен");
    }

    @Override
    public String getLogPasEncode(String LogPasEncode) {
        SharedPreferences Prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        return Prefs.getString(LogPasEncode,"123");
    }

}
