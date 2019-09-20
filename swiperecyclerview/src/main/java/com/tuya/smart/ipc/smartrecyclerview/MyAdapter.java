package com.tuya.smart.ipc.smartrecyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tuya.smart.ipc.swiperecyclerview.R;

/**
 * huangdaju
 * 2019-09-20
 **/

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        Log.i("SmartLayoutManager", "onCreateViewHolder");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.i("SmartLayoutManager", "onBindViewHolder");
        TextView tv_label = holder.itemView.findViewById(R.id.tv_label);
        tv_label.setText("item:" + position);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
