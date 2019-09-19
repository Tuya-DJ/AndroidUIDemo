package com.tuya.smart.ipc.androiduiwidget;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tuya.smart.ipc.androiduiwidget.adapter.GalleryHelperAdapter;

import java.util.ArrayList;

import snaphelper.GallerySnapHelper;

/**
 * huangdaju
 * 2019-09-19
 **/

public class GallerySnaperActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    ArrayList<String> mData;
    LinearLayoutManager mLayoutManager;
    GallerySnapHelper mGallerySnapHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_helper);
        mRecyclerView = findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        initData();
        mRecyclerView.setAdapter(new GalleryHelperAdapter(this, mData));
        mGallerySnapHelper = new GallerySnapHelper();
        mGallerySnapHelper.attachToRecyclerView(mRecyclerView);
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i= 0;i<60;i++){
            mData.add("i="+i);
        }
    }
}
