package com.example.tacademy.miniproject.tstore;

import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.tacademy.miniproject.data.TStorePhoneModel;

/**
 * Created by Tacademy on 2016-05-10.
 */
public class ModelViewHolder extends RecyclerView.ViewHolder {
    TextView nameView;
    public ModelViewHolder(View itemView) {
        super(itemView);
        nameView = (TextView)itemView;
    }
    TStorePhoneModel model;
    public void setModel(TStorePhoneModel model) {
        this.model = model;
        nameView.setText(model.modelName + "(" + model.modelCode + ")");
    }
}