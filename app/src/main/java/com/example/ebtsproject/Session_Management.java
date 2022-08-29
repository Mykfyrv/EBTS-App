package com.example.ebtsproject;

import android.content.Context;
import android.content.SharedPreferences;

public class Session_Management {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_key";

    public Session_Management(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(Session_User user){
        String id = user.getId();
        editor.putString(SESSION_KEY, id).commit();
    }

    public String getSession(){
        return sharedPreferences.getString(SESSION_KEY, "-1");
    }
    public void removeSession(){
        editor.putString(SESSION_KEY, "-1").commit();
    }
}
