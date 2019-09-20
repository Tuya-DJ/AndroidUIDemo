package com.tuya.smart.ipc.horizontallayoutmanager;

import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * huangdaju
 * 2019-09-20
 **/

public class HorizontalLayoutManager extends RecyclerView.LayoutManager implements RecyclerView.SmoothScroller.ScrollVectorProvider {

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public PointF computeScrollVectorForPosition(int targetPosition) {
        if (getChildCount() == 0) {
            return null;
        }
        int firstChildPosition = getPosition(getChildAt(0));
        int direction = -1;
        if (targetPosition < firstChildPosition) {
            direction = -1;
        } else {
            direction = 1;
        }
        return new PointF(direction, 0f);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //分离并且回收当前附加的所有View
        detachAndScrapAttachedViews(recycler);
        if (getItemCount() == 0) {
            return;
        }
        int offsetX = 0;

        for (int i = 0; i < getItemCount(); i++) {
            final View view = recycler.getViewForPosition(i);
            addView(view);

            measureChildWithMargins(view, 0, 0);
            int viewWidth = getDecoratedMeasuredWidth(view);
            int viewHeight = getDecoratedMeasuredHeight(view);
            layoutDecorated(view, offsetX, 0, offsetX + viewWidth, viewHeight);
            offsetX += viewWidth;

            if (offsetX > getWidth()) {
                break;
            }
        }

    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        recycleViews(dx, recycler);
        fill(dx, recycler);
        offsetChildrenHorizontal(dx * -1);
        return dx;
    }

    private void fill(int dx, RecyclerView.Recycler recycler) {
        //左滑
        if (dx > 0) {
            final View lastVisibleView = getChildAt(getChildCount() - 1);
            if (lastVisibleView == null) {
                return;
            }
            if (lastVisibleView.getRight() - dx > getWidth()) {
                return;
            }
            int layoutPosition = getPosition(lastVisibleView);

            View nextView;
            if (layoutPosition == getItemCount() - 1) {
                nextView = recycler.getViewForPosition(0);
            } else {
                nextView = recycler.getViewForPosition(layoutPosition + 1);
            }

            addView(nextView);
            measureChildWithMargins(nextView, 0, 0);
            int viewWidth = getDecoratedMeasuredWidth(nextView);
            int viewHeight = getDecoratedMeasuredHeight(nextView);
            int offsetX = lastVisibleView.getRight();
            layoutDecorated(nextView, offsetX, 0, offsetX + viewWidth, viewHeight);

        } else {
            final View firstVisibleView = getChildAt(0);
            if (firstVisibleView == null) {
                return;
            }
            if (firstVisibleView.getLeft() - dx < 0) {
                return;
            }
            int layoutPosition = getPosition(firstVisibleView);
            View nextView;
            if (layoutPosition == 0) {
                nextView = recycler.getViewForPosition(getItemCount() - 1);
            } else {
                nextView = recycler.getViewForPosition(layoutPosition - 1);
            }
            addView(nextView, 0);
            measureChildWithMargins(nextView, 0, 0);
            int viewWidth = getDecoratedMeasuredWidth(nextView);
            int viewHeight = getDecoratedMeasuredHeight(nextView);
            int offsetX = firstVisibleView.getLeft();
            layoutDecorated(nextView, offsetX-viewWidth, 0, offsetX, viewHeight);

        }
    }

    private void recycleViews(int dx, RecyclerView.Recycler recycler) {
        for (int i = 0; i < getItemCount(); i++) {
            final View childView = getChildAt(i);
            if (childView == null) {
                return;
            }

            if (dx > 0) {
                if (childView.getRight() - dx < 0) {
                    removeAndRecycleViewAt(i, recycler);
                }
            } else {
                if (childView.getLeft() - dx > getWidth()) {
                    removeAndRecycleViewAt(i, recycler);
                }
            }
        }
    }
}
