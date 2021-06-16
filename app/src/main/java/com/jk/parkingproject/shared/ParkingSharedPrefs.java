package com.jk.parkingproject.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Pair;

public class ParkingSharedPrefs {
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;

    private final String CURRENT_USER = "currentUser";
    private final String EMAIL = "email";
    private final String PASSWORD = "password";
    private final String REMEMBER = "remember";

    public ParkingSharedPrefs(Context context) {
        shared = PreferenceManager.getDefaultSharedPreferences(context);
        editor = shared.edit();
    }

    public void setCurrentUser(String email){
       editor.putString(CURRENT_USER, email);
       editor.commit();
    }

    public String getCurrentUser(){
        String currentUser = shared.getString(CURRENT_USER, "empty");
        return currentUser;
    }

    public boolean getIsRemembered(){
        return shared.getBoolean(REMEMBER, false);
    }

    public Pair<String, String> getInfo(){
        return new Pair<>(shared.getString(EMAIL, ""), shared.getString(PASSWORD, ""));
    }

    public void setPassword(String password){
        editor.putString(PASSWORD, password);
        editor.commit();
    }

    public void saveUserInfo(String email, String password, Boolean remember){
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);
        editor.putBoolean(REMEMBER, remember);
        editor.commit();
    }

    public void userLogout(){
        editor.remove(CURRENT_USER);
        editor.remove(EMAIL);
        editor.remove(PASSWORD);
        editor.remove(REMEMBER);
        editor.commit();
    }
}
