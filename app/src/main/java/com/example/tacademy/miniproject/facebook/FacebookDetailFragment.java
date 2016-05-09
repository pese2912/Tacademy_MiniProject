package com.example.tacademy.miniproject.facebook;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.miniproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FacebookDetailFragment extends Fragment {


    public FacebookDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_facebook_detail, container, false);
    }

}
