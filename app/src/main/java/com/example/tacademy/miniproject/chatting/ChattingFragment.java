package com.example.tacademy.miniproject.chatting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.miniproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChattingFragment extends Fragment {


    public ChattingFragment() {
        // Required empty public constructor
    }


    FragmentTabHost tabHost;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chatting, container, false);
        tabHost = (FragmentTabHost)view.findViewById(R.id.tabhost);
        tabHost.setup(getContext(),getChildFragmentManager(), android.R.id.tabcontent);
        tabHost.addTab(tabHost.newTabSpec("friend").setIndicator("Friend"),  ChattingFriendFragment.class,null);
        return  view;
    }

}
