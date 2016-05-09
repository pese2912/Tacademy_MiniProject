package com.example.tacademy.miniproject.tstore;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tacademy.miniproject.R;
import com.example.tacademy.miniproject.data.TStoreCategory;
import com.example.tacademy.miniproject.data.TStoreCategoryProduct;
import com.example.tacademy.miniproject.manager.NetworkManager;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class TStoreSearchFragment extends Fragment {

    RecyclerView listView;
    EditText inputView;
    ProductAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ProductAdapter();




    }

    public TStoreSearchFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tstore_search, container, false);
        inputView = (EditText)view.findViewById(R.id.edit_input);
        Button btn = (Button)view.findViewById(R.id.btn_search);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = inputView.getText().toString();
                Toast.makeText(getContext(),keyword, Toast.LENGTH_SHORT).show();
                setData(keyword);

            }
        });


        listView = (RecyclerView)view.findViewById(R.id.rv_list);
        mAdapter = new ProductAdapter();
        listView.setAdapter(mAdapter);

        listView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }


    private void setData(String keyword) {

        //검색

        NetworkManager.getInstance().getTStoreSearchProductList(this, 10, NetworkManager.CATEGORY_PRODUCT_ORDER_R, 1, keyword, new NetworkManager.OnResultListener<TStoreCategoryProduct>() {
            @Override
            public void onSuccess(Request request, TStoreCategoryProduct result) {
                mAdapter.clear();
                mAdapter.addAll(result.products.productList);
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(getContext(), "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
