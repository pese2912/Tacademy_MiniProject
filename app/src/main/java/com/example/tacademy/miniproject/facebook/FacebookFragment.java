package com.example.tacademy.miniproject.facebook;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.miniproject.R;
import com.example.tacademy.miniproject.data.FacebookFeed;
import com.example.tacademy.miniproject.manager.NetworkManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class FacebookFragment extends Fragment {


    public FacebookFragment() {
        // Required empty public constructor
    }

    RecyclerView listView;
    FeedAdapter mAdapter;
    CallbackManager callbackManager;
    LoginManager loginManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new FeedAdapter();
        callbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_facebook, container, false);

        listView = (RecyclerView)view.findViewById(R.id.rv_list);
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        readFeed();
    }

    private void readFeed(){ // 피드 가져오기
        AccessToken token = AccessToken.getCurrentAccessToken();
        if(token != null){ // 로그인 되어 있으면
            if(token.getPermissions().contains("user_posts")){
                NetworkManager.getInstance().getFacebookFeeds(getContext(), token.getToken(), new NetworkManager.OnResultListener<List<FacebookFeed>>() { // 피드 가져오고
                    @Override
                    public void onSuccess(Request request, List<FacebookFeed> result) {
                        mAdapter.clear();
                        mAdapter.addAll(result);
                        Log.i("result", "success");
                        Log.i("result", result.toString());
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {

                    }
                });

                return;
            }
        }

        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() { // 안되있으면 로그인하고 다시 가져오기
            @Override
            public void onSuccess(LoginResult loginResult) {
                readFeed();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        loginManager.logInWithReadPermissions(this, Arrays.asList("user_posts"));
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
