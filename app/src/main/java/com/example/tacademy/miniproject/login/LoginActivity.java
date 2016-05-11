package com.example.tacademy.miniproject.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tacademy.miniproject.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }

    }
}
