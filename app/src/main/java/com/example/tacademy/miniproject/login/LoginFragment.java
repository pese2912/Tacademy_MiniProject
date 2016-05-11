package com.example.tacademy.miniproject.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tacademy.miniproject.MainActivity;
import com.example.tacademy.miniproject.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    Button loginButton;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        loginButton = (Button)view.findViewById(R.id.btn_facebook_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
       Button btn= (Button)view.findViewById(R.id.btn_signup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siginUp();
            }
        });
        return view;
    }
    CallbackManager callbackManager;
    LoginManager loginManager;
    AccessTokenTracker tokenTracker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        if(tokenTracker == null){ // 로그인 여부 추적 확인
            tokenTracker = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                    updateButtonText();
                }
            };
        }
        else{
            tokenTracker.startTracking();
        }
        updateButtonText();
    }

    private void updateButtonText(){ // 로그인여부에 따라 버튼 텍스트 변경

        if(isLogin()){
            loginButton.setText("logout");
        }else{
            loginButton.setText("login");
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        tokenTracker.stopTracking();
    }

    private boolean isLogin(){ // 로그인 여부
        AccessToken token = AccessToken.getCurrentAccessToken();
        return token!= null;
    }

    private void login(){ //로그인 처리

        if(!isLogin()){ // 로그인 안됬을 경우
            loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    startActivity(new Intent(getContext(), MainActivity.class)); // 메인 화면으로 이동
                    getActivity().finish();
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {

                }
            });

            loginManager.logInWithReadPermissions(this, Arrays.asList("email")); //이메일 퍼미션 획득
        }else{
            loginManager.logOut(); // 로그인 되어있으면 로그아웃
        }
    }

    private void siginUp(){ // 회원가입

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { // 콜백 메소드 정의 필수
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

}
