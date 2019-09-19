package com.tuya.smart.ipc.androiduiwidget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tuya.smart.ipc.androiduiwidget.R;

import java.util.ArrayList;

/**
 * huangdaju
 * 2019-09-19
 **/

public class GalleryHelperAdapter  extends RecyclerView.Adapter<GalleryHelperAdapter.GalleryViewHoler> {
    LayoutInflater mInflater;
    ArrayList<String> mData;
    int[] imgs = new int[]{R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher};


    public GalleryHelperAdapter(Context context, ArrayList<String> data) {
        mInflater = LayoutInflater.from(context);
        mData = data;

    }

    @Override
    public GalleryViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.gallery_item_layout, parent, false);

        return new GalleryViewHoler(view);
    }

    @Override
    public void onBindViewHolder(final GalleryViewHoler holder, int position) {
        holder.mImageView.setImageResource(imgs[position % 8]);
        holder.mTextView.setText(mData.get(position));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class GalleryViewHoler extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView;

        public GalleryViewHoler(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image);
            mTextView = itemView.findViewById(R.id.tv_num);
        }
    }
}
