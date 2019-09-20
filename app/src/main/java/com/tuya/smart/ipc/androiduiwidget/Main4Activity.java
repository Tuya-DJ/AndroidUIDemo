package com.tuya.smart.ipc.androiduiwidget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.tuya.smart.ipc.horizontallayoutmanager.HorizontalLayoutManager;
import com.tuya.smart.ipc.smartrecyclerview.MyAdapter;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_rv);
        recyclerView.setLayoutManager(new HorizontalLayoutManager());
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(new MyAdapter());
    }
}
