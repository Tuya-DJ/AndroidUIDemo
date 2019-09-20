package com.tuya.smart.ipc.androiduiwidget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.tuya.smart.ipc.smartrecyclerview.MyAdapter;
import com.tuya.smart.ipc.smartrecyclerview.SmartLayoutManager;
import com.tuya.smart.ipc.smartrecyclerview.SmartSnapHelper;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_rv);
        recyclerView.setLayoutManager(new SmartLayoutManager());
        new SmartSnapHelper().attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(new MyAdapter());
    }
}
