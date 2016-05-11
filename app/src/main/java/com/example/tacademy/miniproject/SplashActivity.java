package com.example.tacademy.miniproject;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tacademy.miniproject.login.LoginActivity;
import com.facebook.AccessToken;

public class SplashActivity extends AppCompatActivity {

    Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AccessToken token = AccessToken.getCurrentAccessToken();
        if(token==null) {
            mHandler.postDelayed(new Runnable() { // 로그인 안되있으면 로그인 화면으로
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }, 2000);
        }else{
            mHandler.postDelayed(new Runnable() { // 로그인 되있으면 메인화면으로
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));

                }
            },2000);
        }
    }
}
