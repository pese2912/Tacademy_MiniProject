package com.example.tacademy.miniproject.youtube;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.miniproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class YoutubeMyContentFragment extends Fragment {


    public YoutubeMyContentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_youtube_my_content, container, false);
    }

}
