package com.example.tacademy.miniproject.tstore;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Tacademy on 2016-05-10.
 */
public class TitleViewHolder extends RecyclerView.ViewHolder {
    TextView titleView;
    public TitleViewHolder(View itemView) {
        super(itemView);
        titleView = (TextView)itemView;
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }
}