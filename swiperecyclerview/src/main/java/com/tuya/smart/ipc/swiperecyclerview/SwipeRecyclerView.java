package com.tuya.smart.ipc.swiperecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * huangdaju
 * 2019-09-14
 **/

public class SwipeRecyclerView extends RecyclerView {

    private View mRightView;
    private View mLeftView;
    public boolean isRightShow = false;
    public int screenWidth;

    private int touchDownX;
    private int touchDownY;
    private int mRightViewWidth;

    private LinearLayout.LayoutParams mLayoutParams;

    public SwipeRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public SwipeRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public void initView(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                actionDown(e);
                break;
            case MotionEvent.ACTION_MOVE:
                actionMove(e);
                break;
            case MotionEvent.ACTION_UP:
                actionUp(e);
                break;
            default:
                break;
        }
        return super.onTouchEvent(e);
    }

    private void actionUp(MotionEvent e) {
        if (Math.abs(mLayoutParams.leftMargin) < mRightViewWidth / 2) {
            backToNormal();
        } else {
            mLayoutParams.leftMargin = -mRightViewWidth;
            mLeftView.setLayoutParams(mLayoutParams);
            isRightShow = true;
        }
    }

    private boolean actionMove(MotionEvent e) {
        int scrollX = (int) (e.getX() - touchDownX);
        Log.d("scrollx", scrollX + "");
        int scrollY = (int) (e.getY() - touchDownY);
        if (Math.abs(scrollX) - Math.abs(scrollY) > 0) {
            if (scrollX < 0) {
                if (Math.abs(scrollX) >= mRightViewWidth) {
                    scrollX = -mRightViewWidth;
                }
                mLayoutParams = (LinearLayout.LayoutParams) mLeftView.getLayoutParams();
                mLayoutParams.leftMargin = scrollX;
                mLeftView.setLayoutParams(mLayoutParams);
            }else{
                mLayoutParams = (LinearLayout.LayoutParams) mLeftView.getLayoutParams();
                mLayoutParams.leftMargin = 0;
                mLeftView.setLayoutParams(mLayoutParams);
            }
            return true;
        }
        return super.onTouchEvent(e);
    }

    private void actionDown(MotionEvent e) {
        if (isRightShow) {
            backToNormal();
        }
        touchDownX = (int) e.getX();
        touchDownY = (int) e.getY();
        View itemView = findChildViewUnder(e.getX(), e.getY());
        if (itemView == null) {
            return;
        }
        SwipeRecyclerViewAdapter.SwipeViewHolder viewHolder = (SwipeRecyclerViewAdapter.SwipeViewHolder) getChildViewHolder(itemView);
        mRightView = viewHolder.getRightView();
        mLeftView = viewHolder.getLeftView();
        if (null != mLeftView) {
            mLayoutParams = (LinearLayout.LayoutParams) mLeftView.getLayoutParams();
            mLayoutParams.width = screenWidth;
            mLeftView.setLayoutParams(mLayoutParams);
        }

        if (null != mRightView) {
            mRightViewWidth = mRightView.getLayoutParams().width;
        }
    }


    private void backToNormal() {
        mLayoutParams.leftMargin = 0;
        if (null != mLeftView) {
            mLeftView.setLayoutParams(mLayoutParams);
            isRightShow = false;
        }
    }

}
