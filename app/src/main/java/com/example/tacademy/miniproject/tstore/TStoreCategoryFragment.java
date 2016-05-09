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
import android.widget.Toast;

import com.example.tacademy.miniproject.R;
import com.example.tacademy.miniproject.data.TStoreCategory;


/**
 * A simple {@link Fragment} subclass.
 */
public class TStoreCategoryFragment extends Fragment {

    RecyclerView listView;

    CategoryAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CategoryAdapter();
        mAdapter.setOnItemClickListener(new CategoryViewHolder.OnItemClickListener() { //카테고리 클릭시
            @Override
            public void onItemClick(View view, TStoreCategory category) {
                Toast.makeText(getContext(), "code : " + category.getCategoryCode(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), TStoreAppListActivity.class);
                intent.putExtra(TStoreAppListActivity.EXTRA_CATEGORY_CODE, category.getCategoryCode()); //코드 넘겨주고 액티비티 실행
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tstore_category, container, false);
        listView = (RecyclerView)view.findViewById(R.id.rv_list);
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onResume() { //리쥼 시 마다 업데이트
        super.onResume();
        setData();
    }

    private void setData() {
        mAdapter.clear(); //초기에 지우고
        for (int i = 0; i < 10; i++) {
            TStoreCategory category = new TStoreCategory();
            category.setCategoryName("Category " + i);
            category.setCategoryCode("Code : " + i);
            mAdapter.add(category); // 값 추가
        }
    }
}
