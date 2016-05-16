package com.example.tacademy.miniproject;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.tacademy.miniproject.login.LoginActivity;
import com.example.tacademy.miniproject.login.MyResultUser;
import com.example.tacademy.miniproject.manager.NetworkManager;
import com.example.tacademy.miniproject.manager.PropertyManager;
import com.facebook.AccessToken;

import java.io.IOException;

import okhttp3.Request;

public class SplashActivity extends AppCompatActivity {

    Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        String email = PropertyManager.getInstance().getEmail();
        if(!TextUtils.isEmpty(email)){
            String password = PropertyManager.getInstance().getPassword();
            NetworkManager.getInstance().signin(this, email, password, "", new NetworkManager.OnResultListener<MyResultUser>() {
                @Override
                public void onSuccess(Request request, MyResultUser result) {
                    if(result.code == 1){
                        PropertyManager.getInstance().setLogin(true);
                        PropertyManager.getInstance().setUser(result.result);
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFail(Request request, IOException exception) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            });
        }else{
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            },2000);

        }

       // AccessToken token = AccessToken.getCurrentAccessToken();
      //  if(token==null) {
      //      mHandler.postDelayed(new Runnable() { // 로그인 안되있으면 로그인 화면으로
        //        @Override
         //       public void run() {
       //             startActivity(new Intent(SplashActivity.this, LoginActivity.class));
       //             finish();
       //         }
         //   }, 2000);
      //  }else{
       //     mHandler.postDelayed(new Runnable() { // 로그인 되있으면 메인화면으로
         //       @Override
          //      public void run() {
        //            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        //            finish();
         //       }
         //   },2000);
       // }
    }
}
