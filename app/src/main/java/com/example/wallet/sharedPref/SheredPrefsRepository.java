package com.example.wallet.sharedPref;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SheredPrefsRepository implements SheredPrefsRepositoryImpl{
    Context context;
    SharedPreferences Prefs;
    public SheredPrefsRepository(Context context){
        this.context=context;
        Prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
    }

    @Override
    public void pootLogPas(String key,String logPassEncode) {
        SharedPreferences.Editor editor = Prefs.edit();
        editor.putString(key, logPassEncode);
        editor.apply();
        Log.e("pootToken", "положил токен");
    }

    @Override
    public String getLogPasEncode(String logPasEncode) {
        return Prefs.getString(logPasEncode,"empty");
    }


}
