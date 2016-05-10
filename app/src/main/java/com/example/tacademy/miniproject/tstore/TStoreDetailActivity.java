package com.example.tacademy.miniproject.tstore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.tacademy.miniproject.R;
import com.example.tacademy.miniproject.data.TStoreProduct;
import com.example.tacademy.miniproject.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;


public class TStoreDetailActivity extends AppCompatActivity {


    RecyclerView listView;

    ProductDetailAdapter mAdapter;

    public static final String EXTRA_PRODUCT_ID = "productId";

    String productId;

    GridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tstore_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (RecyclerView)findViewById(R.id.rv_list);

        mAdapter = new ProductDetailAdapter();

        listView.setAdapter(mAdapter);
        mLayoutManager = new GridLayoutManager(this, 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) { //포지션에 해당하는것에 대해 몇칸을 차지할지 결정

                int type = mAdapter.getItemViewType(position);
                if(type == ProductDetailAdapter.VIEW_TYPE_HEADER || type == ProductDetailAdapter.VIEW_TYPE_TITLE){
                    return 2;
                }else{
                    return 1;
                }
               
            }
        });

        listView.setLayoutManager(mLayoutManager);

        Intent intent = getIntent();
        productId = intent.getStringExtra(EXTRA_PRODUCT_ID);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setData();
    }

    private void setData() {
        NetworkManager.getInstance().getTStoreDetailProduct(this, productId, new NetworkManager.OnResultListener<TStoreProduct>() {
            @Override
            public void onSuccess(Request request, TStoreProduct result) {
                mAdapter.setProduct(result);
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}