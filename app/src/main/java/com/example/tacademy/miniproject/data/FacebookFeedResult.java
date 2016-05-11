package com.example.tacademy.miniproject.data;

import android.hardware.camera2.params.Face;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tacademy on 2016-05-11.
 */
public class FacebookFeedResult {

    @SerializedName("data")
    public List<FacebookFeed> feeds;

    public void convertStringToDate(){
        for(FacebookFeed ff : feeds){
            ff.changeStringToDate();
        }
    }
}
