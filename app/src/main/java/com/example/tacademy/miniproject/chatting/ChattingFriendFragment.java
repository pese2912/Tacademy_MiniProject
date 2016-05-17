package com.example.tacademy.miniproject.chatting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.miniproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChattingFriendFragment extends Fragment {


    public ChattingFriendFragment() {
        // Required empty public constructor
    }

    RecyclerView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chatting_friend, container, false);
        listView= (RecyclerView)view.findViewById(R.id.rv_list);

        return  view;
    }

}
