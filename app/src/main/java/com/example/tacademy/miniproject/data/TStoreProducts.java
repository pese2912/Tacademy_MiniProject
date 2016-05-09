package com.example.tacademy.miniproject.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tacademy on 2016-05-09.
 */
public class TStoreProducts {
    @SerializedName("product")
    public List<TStoreProduct> productList;
}
