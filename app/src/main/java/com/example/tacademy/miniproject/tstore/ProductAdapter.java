package com.example.tacademy.miniproject.tstore;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.tacademy.miniproject.R;
import com.example.tacademy.miniproject.data.TStoreProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongja94 on 2016-05-09.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    List<TStoreProduct> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(TStoreProduct product) {
        items.add(product);
        notifyDataSetChanged();
    }

    public void addAll(List<TStoreProduct> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    ProductViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(ProductViewHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tstore_product, null);
        return new ProductViewHolder(view);
    }

    private int totalCount= 0;
    public int getTotalCount(){
        return totalCount;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    private int lastPage= 0;


    public boolean isMore(){
        return totalCount == 0 ? false : (totalCount > items.size() ? true : false);
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    private String keyword;


    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.setProduct(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}