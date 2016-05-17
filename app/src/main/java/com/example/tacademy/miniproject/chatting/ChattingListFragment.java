package com.example.tacademy.miniproject.chatting;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tacademy.miniproject.R;
import com.example.tacademy.miniproject.manager.DataConstant;
import com.example.tacademy.miniproject.manager.DataManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChattingListFragment extends Fragment {


    public ChattingListFragment() {
        // Required empty public constructor
    }

    ListView listView;
    SimpleCursorAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] from = {DataConstant.ChatUserTable.COLUMN_NAME, DataConstant.ChatUserTable.COLUMN_EMAIL};
        int[] to = {R.id.text_name, R.id.text_email};
        mAdapter = new SimpleCursorAdapter(getContext(),R.layout.view_chat_list, null,from,to);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chatting_list, container, false);
        listView = (ListView)view.findViewById(R.id.listView);
        listView.setAdapter(mAdapter);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Cursor c = DataManager.getInstance().getChatUserList();
        mAdapter.changeCursor(c);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
