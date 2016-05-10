package com.example.tacademy.miniproject.tstore;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import java.io.UnsupportedEncodingException;
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


    LinearLayoutManager mLayoutManager;
    Boolean isLast= false;


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

        mLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mLayoutManager);

        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if(isLast && newState == RecyclerView.SCROLL_STATE_IDLE){
                    getMoreData();
                }

            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int totalCount = mAdapter.getItemCount();
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition(); //마지막에 보이는 아이템의 인덱스
                if (totalCount > 0 && lastVisibleItem >= totalCount - 1) { //맨끝에 도달

                    isLast = true;
                } else {
                    isLast = false;
                }
            }

        });


        return view;
    }
    boolean isMoreData = false;

    private void getMoreData(){ //데이터 더보기
        if(!isMoreData && mAdapter.isMore()){
            isMoreData = true;
            final int page = mAdapter.getLastPage()+1; //페이지 증가
            try {
                NetworkManager.getInstance().getTStoreSearchProductList(this, 10, NetworkManager.CATEGORY_PRODUCT_ORDER_R, page, mAdapter.getKeyword(), new NetworkManager.OnResultListener<TStoreCategoryProduct>() {
                    @Override
                    public void onSuccess(Request request, TStoreCategoryProduct result) {

                        mAdapter.addAll(result.products.productList);
                        mAdapter.setLastPage(page);
                        isMoreData = false;
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {
                        isMoreData = false;

                    }
                });
            }catch (Exception e){
                e.printStackTrace();
                isMoreData = false;
            }

        }
    }


    private void setData(final String keyword) {

        //검색
        if(!TextUtils.isEmpty(keyword)) {
            try {

                NetworkManager.getInstance().getTStoreSearchProductList(this, 10, NetworkManager.CATEGORY_PRODUCT_ORDER_R, 1, keyword, new NetworkManager.OnResultListener<TStoreCategoryProduct>() {
                    @Override
                    public void onSuccess(Request request, TStoreCategoryProduct result) {
                        mAdapter.setKeyword(keyword);
                        mAdapter.setLastPage(1);
                        mAdapter.setTotalCount(result.totalCount);
                        mAdapter.clear();
                        mAdapter.addAll(result.products.productList);
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {
                        Toast.makeText(getContext(), "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}
