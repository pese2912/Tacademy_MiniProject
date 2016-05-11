package com.example.tacademy.miniproject;

import android.app.Application;
import android.content.Context;
import android.hardware.camera2.params.Face;

import com.facebook.FacebookSdk;

/**
 * Created by Tacademy on 2016-05-09.
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        FacebookSdk.sdkInitialize(this);
    }
    public  static Context getContext(){
        return  context;
    }
}
