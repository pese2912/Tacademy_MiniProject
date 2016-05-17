package com.example.tacademy.miniproject.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tacademy.miniproject.MainActivity;
import com.example.tacademy.miniproject.R;
import com.example.tacademy.miniproject.manager.NetworkManager;
import com.example.tacademy.miniproject.manager.PropertyManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    Button facebookLoginButton;


    public LoginFragment() {
        // Required empty public constructor
    }


    EditText emailView, passwordView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        facebookLoginButton = (Button)view.findViewById(R.id.btn_facebook_login);
        facebookLoginButton.setOnClickListener(new View.OnClickListener() {
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

        emailView= (EditText)view.findViewById(R.id.edit_email);
        passwordView = (EditText)view.findViewById(R.id.edit_password);

        btn = (Button)view.findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String email = emailView.getText().toString();
                final String password = passwordView.getText().toString();

                NetworkManager.getInstance().signin(getContext(), email, password, PropertyManager.getInstance().getRegistrationToken(), new NetworkManager.OnResultListener<MyResultUser>() {
                    @Override
                    public void onSuccess(Request request, MyResultUser result) {
                        if(result.code == 1){
                            PropertyManager.getInstance().setLogin(true);
                            PropertyManager.getInstance().setUser(result.result);
                            PropertyManager.getInstance().setEmail(email);
                            PropertyManager.getInstance().setPassword(password);
                            startActivity(new Intent(getContext(), MainActivity.class));
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {

                    }
                });
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
            facebookLoginButton.setText("logout");
        }else{
            facebookLoginButton.setText("login");
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
                    AccessToken token = AccessToken.getCurrentAccessToken();
                    NetworkManager.getInstance().facebookSignIn(getContext(), token.getToken(),PropertyManager.getInstance().getRegistrationToken(), new NetworkManager.OnResultListener<MyResultUser>() {
                        @Override
                        public void onSuccess(Request request, MyResultUser result) {

                        }

                        @Override
                        public void onFail(Request request, IOException exception) {

                        }
                    });

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
        ((LoginActivity)getActivity()).changeSignUp();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { // 콜백 메소드 정의 필수
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
