package com.example.tacademy.miniproject.manager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.tacademy.miniproject.MyApplication;
import com.example.tacademy.miniproject.login.User;

/**
 * Created by Tacademy on 2016-05-16.
 */
public class PropertyManager {
    private static PropertyManager instance;
    public static PropertyManager getInstance(){
        if(instance == null){
            instance = new PropertyManager();
        }

        return instance;
    }
    SharedPreferences mPrefs;
    SharedPreferences.Editor mEdirot;

    private PropertyManager(){
        mPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        mEdirot = mPrefs.edit();
    }
    private static final String FIELD_EMAIL = "email";
    public void setEmail(String email){
        mEdirot.putString(FIELD_EMAIL,email);
        mEdirot.commit();
    }

    public String getEmail(){
        return mPrefs.getString(FIELD_EMAIL, "");
    }

    private static final String FIELD_PASSWORD= "password";
    public void setPassword(String password){

        mEdirot.putString(FIELD_PASSWORD,password);
        mEdirot.commit();
    }

    public String getPassword(){
        return mPrefs.getString(FIELD_PASSWORD, "");

    }

    private boolean isLogin= false;
    public void setLogin(boolean login){
        isLogin = login;
    }
    public boolean isLogin(){
        return isLogin;
    }

    private User user = null;
    public void setUser(User user){
        this.user = user;
    }
    public User getUser(){
        return user;
    }

}
