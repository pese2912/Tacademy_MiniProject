package com.example.tacademy.miniproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.tacademy.miniproject.gcm.RegistrationIntentService;
import com.example.tacademy.miniproject.login.LoginActivity;
import com.example.tacademy.miniproject.login.MyResultUser;
import com.example.tacademy.miniproject.manager.NetworkManager;
import com.example.tacademy.miniproject.manager.PropertyManager;
import com.facebook.AccessToken;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.IOException;

import okhttp3.Request;

public class SplashActivity extends AppCompatActivity {

    Handler mHandler = new Handler(Looper.getMainLooper());
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                doRealStart();
            }
        };
        setUpIfNeeded();



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

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(RegistrationIntentService.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLAY_SERVICES_RESOLUTION_REQUEST &&
                resultCode == Activity.RESULT_OK) {
            setUpIfNeeded();
        }
    }

    private void setUpIfNeeded() {
        if (checkPlayServices()) {
            String regId = PropertyManager.getInstance().getRegistrationToken();
            if (!regId.equals("")) {
                doRealStart(); // 등록
            } else {
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
        }
    }

    private void doRealStart() {
        goLogin();
        // activity start...
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                Dialog dialog = apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                });
                dialog.show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }


    private void goLogin(){
        String email = PropertyManager.getInstance().getEmail();
        if(!TextUtils.isEmpty(email)){
            String password = PropertyManager.getInstance().getPassword();
            NetworkManager.getInstance().signin(this, email, password, PropertyManager.getInstance().getRegistrationToken(), new NetworkManager.OnResultListener<MyResultUser>() {
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
    }
}
