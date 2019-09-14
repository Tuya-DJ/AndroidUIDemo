package com.tuya.smart.ipc.swiperecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * huangdaju
 * 2019-09-14
 **/

public class SwipeRecyclerViewAdapter extends RecyclerView.Adapter<SwipeRecyclerViewAdapter.SwipeViewHolder> {

    private LayoutInflater mInflater;
    private static ArrayList<String> dataList = new ArrayList<>();
    static {
        dataList.add("eeeeee");
        dataList.add("eeeeee");
        dataList.add("eeeeee");
        dataList.add("eeeeee");
        dataList.add("eeeeee");
        dataList.add("eeeeee");
        dataList.add("eeeeee");
    }

    public SwipeRecyclerViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SwipeRecyclerViewAdapter.SwipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SwipeRecyclerViewAdapter.SwipeViewHolder(mInflater.inflate(R.layout.item_right_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SwipeRecyclerViewAdapter.SwipeViewHolder holder, int position) {
        holder.mLeftView.setText(dataList.get(position) + position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class SwipeViewHolder extends RecyclerView.ViewHolder {
        private TextView mLeftView;
        private TextView mRightView;

        public SwipeViewHolder(@NonNull View itemView) {
            super(itemView);
            mLeftView = itemView.findViewById(R.id.tv_title);
            mRightView = itemView.findViewById(R.id.tv_delete);
        }

        public View getRightView() {
            return mRightView;
        }

        public View getLeftView() {
            return mLeftView;
        }
    }
}
