package ru.mediasoft.unipolls.data.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefRepository {

    public SharedPrefRepository(Context ctx) {
       myPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private SharedPreferences myPreferences;

    public void saveToken(String token){
        myPreferences.edit().putString(ACCESS_TOKEN, token).apply();
    }
    public String getToken(){
        return myPreferences.getString(ACCESS_TOKEN, "");
    }

    public void saveCode(String code) {
        myPreferences.edit().putString("CODE", code).apply();
    }

    public String getCode(){
        return myPreferences.getString("CODE","");
    }

    public void removeCodeAndToken(){
        myPreferences.edit().remove("CODE").remove("ACCESS_TOKEN").apply();
    }
}
