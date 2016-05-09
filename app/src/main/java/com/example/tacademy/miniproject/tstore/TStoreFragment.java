package com.example.tacademy.miniproject.tstore;

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
public class TStoreFragment extends Fragment {


    public TStoreFragment() {
        // Required empty public constructor
    }


    FragmentTabHost tabHost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tstore, container, false);
        tabHost = (FragmentTabHost)view.findViewById(R.id.tabhost);
        tabHost.setup(getContext(), getChildFragmentManager(), android.R.id.tabcontent);

        tabHost.addTab(tabHost.newTabSpec("category").setIndicator(getString(R.string.tstore_tab_category_title)), TStoreCategoryFragment.class, null); //카테고리 탭
        tabHost.addTab(tabHost.newTabSpec("search").setIndicator(getString(R.string.tstore_tab_search_title)), TStoreSearchFragment.class, null); // 검색 탭
        return view;
    }

}