package com.example.tacademy.miniproject.manager;

import android.content.Context;
import android.net.NetworkRequest;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.tacademy.miniproject.MyApplication;
import com.example.tacademy.miniproject.data.TStoreCategory;
import com.example.tacademy.miniproject.data.TStoreCategoryProduct;
import com.example.tacademy.miniproject.data.TStoreCategoryProductResult;
import com.example.tacademy.miniproject.data.TStoreCategoryResult;
import com.google.gson.Gson;
import com.google.gson.internal.Streams;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Tacademy on 2016-05-09.
 */


public class NetworkManager {
    private static NetworkManager instance;
    public static NetworkManager getInstance(){
        if(instance == null){
            instance = new NetworkManager();
        }
        return instance;
    }

    private static final int DEFAULT_CACHE_SIZE = 50*1024*1024;
    private static final String DEFAULT_CACHE_DIR="miniapp";
    OkHttpClient mClient;

    private NetworkManager(){

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Context context = MyApplication.getContext();
        CookieManager cookieManager = new CookieManager();
        builder.cookieJar(new JavaNetCookieJar(cookieManager)); //메모리 저장하는 쿠키

        File dir = new File(context.getExternalCacheDir(),DEFAULT_CACHE_DIR);
        if(!dir.exists()){
            dir.mkdir();
        }

        builder.cache(new Cache(dir, DEFAULT_CACHE_SIZE));

        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);

        mClient = builder.build(); // 외장메모리 저장하는 캐시

    }

    public interface OnResultListener<T>{
        public  void onSuccess(Request request, T result);
        public void onFail(Request request, IOException exception);
    }

    private static final  int MESSAGE_SUCCESS= 1;
    private static final int MESSAGE_FAIL = 2;

    class NetworkHandler extends Handler{
        public NetworkHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            NetworkResult result  = (NetworkResult)msg.obj;

            switch (msg.what){
                case  MESSAGE_SUCCESS:
                    result.listener.onSuccess(result.request, result.result);
                    break;

                case MESSAGE_FAIL:
                    result.listener.onFail(result.request, result.exception);
                    break;

            }
        }
    }

    NetworkHandler mHandler = new NetworkHandler(Looper.getMainLooper());

    static class NetworkResult<T>{
        Request request;
        OnResultListener<T> listener;
        IOException exception;
        T result;

    }
    Gson gson = new Gson();

    private static final String TSTORE_SERVER="http://apis.skplanetx.com";
    private static final String TSTORE_CATEGORY_SERVER_URL = TSTORE_SERVER+"/tstore/categories?version=1";
    public Request getTStoreCategory(Object tag, OnResultListener<List<TStoreCategory>> listener){

        Request request = new Request.Builder()
                .url(TSTORE_CATEGORY_SERVER_URL)
                .header("Accept","application/json")
                .header("appKey", "ec449f14-3190-30de-9143-75e1be5e7521")
                .build();

        final  NetworkResult<List<TStoreCategory>> result = new NetworkResult<>();
        result.request= request;
        result.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){

                    TStoreCategoryResult data = gson.fromJson(response.body().charStream(),TStoreCategoryResult.class);

                    result.result=data.tstore.categories.categoryList;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS,result));


                }else{
                    throw new IOException(response.message());
                }

            }
        });

        return request;
    }

    private static final String TSTORE_CATEGORY_PRODUCT_URL = TSTORE_SERVER + "/tstore/categories/%s?version=1&page=%s&count=%s&order=%s";

    public static final String CATEGORY_PRODUCT_ORDER_BEST_C = "C";
    public static final String CATEGORY_PRODUCT_ORDER_BEST_F = "F";
    public static final String CATEGORY_PRODUCT_ORDER_NEW = "N";
    public static final String CATEGORY_PRODUCT_ORDER_R = "R";

    public Request getTStoreCategoryProductList(Object tag, String code, int page, int count, String order,
                                                OnResultListener<TStoreCategoryProduct> listener) {
        String url = String.format(TSTORE_CATEGORY_PRODUCT_URL, code, page, count, order);
        Request request = new Request.Builder()
                .url(url)
                .header("Accept","application/json")
                .header("appKey","ec449f14-3190-30de-9143-75e1be5e7521")
                .build();

        final NetworkResult<TStoreCategoryProduct> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    TStoreCategoryProductResult data = gson.fromJson(response.body().charStream(), TStoreCategoryProductResult.class);
                    if(data!= null) {
                        result.result = data.tstore;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    }

                    Log.i("result",data.toString());
                }else{
                    throw new IOException(response.message());
                }
            }
        });

        return request;
    }




    private static final String TSTORE_SEARCH_PRODUCT_URL = TSTORE_SERVER + "/tstore/products?count=%s&order=%s&page=%s&searchKeyword=%s&version=1";



    public Request getTStoreSearchProductList(Object tag, int count, String order,int page,String keyword,
                                                OnResultListener<TStoreCategoryProduct> listener) {
        String url = null;
        try {
            url = String.format(TSTORE_SEARCH_PRODUCT_URL, count, order, page, URLEncoder.encode(keyword,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Request request = new Request.Builder()
                .url(url)
                .header("Accept","application/json")
                .header("appKey","ec449f14-3190-30de-9143-75e1be5e7521")
                .build();

        final NetworkResult<TStoreCategoryProduct> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.exception = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    TStoreCategoryProductResult data = gson.fromJson(response.body().charStream(), TStoreCategoryProductResult.class);
                    if(data!= null) {
                        result.result = data.tstore;
                        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                    }

                    Log.i("result",data.toString());
                }else{
                    throw new IOException(response.message());
                }
            }
        });

        return request;
    }


}
