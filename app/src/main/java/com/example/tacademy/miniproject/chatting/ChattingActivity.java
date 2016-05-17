package com.example.tacademy.miniproject.chatting;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.example.tacademy.miniproject.R;
import com.example.tacademy.miniproject.login.User;
import com.example.tacademy.miniproject.manager.DataManager;

public class ChattingActivity extends AppCompatActivity {

    ListView listView;
    ChatCursorAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView)findViewById(R.id.listView);
        mAdapter = new ChatCursorAdapter(this);
        listView.setAdapter(mAdapter);
        initData();

    }

    User user;
    long userid = -1;
    private void initData(){
        if(userid== -1){
            //userid = DataManager.getInstance().getUserTableId(user.id);
        }
    }
}
