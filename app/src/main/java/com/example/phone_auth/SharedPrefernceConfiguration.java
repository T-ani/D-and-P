package com.example.phone_auth;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefernceConfiguration {
    private SharedPreferences sharedPreferences;
    //private Context context;
    String l="Hello";
    String l1="HHello";

    public SharedPrefernceConfiguration(Context k)
    {
        sharedPreferences=k.getSharedPreferences(l1, k.MODE_PRIVATE);
    }


    /*public void SharedPreferenceConfig(Context applicationContext)
    {
        this.context=applicationContext;
        sharedPreferences=applicationContext.getSharedPreferences(context.getResources().getString(R.string.login_shared_preference),Context.MODE_PRIVATE);
    }*/
    public void login_status(boolean status)
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(l,status);
        editor.commit();
    }
    public boolean read_login_status()
    {
        boolean status;
        status=sharedPreferences.getBoolean(l,false);
        return status;
    }

}
