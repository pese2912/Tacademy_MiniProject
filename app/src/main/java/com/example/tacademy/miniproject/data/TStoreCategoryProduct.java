package com.example.tacademy.miniproject.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tacademy on 2016-05-09.
 */
public class TStoreCategoryProduct {
    @SerializedName("category")
    public TStoreCategoryInfo info;
    public TStoreProducts products;
    public int totalCount;

}
