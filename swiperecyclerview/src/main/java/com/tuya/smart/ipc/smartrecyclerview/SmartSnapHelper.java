package com.tuya.smart.ipc.smartrecyclerview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

/**
 * huangdaju
 * 2019-09-20
 **/

public class SmartSnapHelper extends SnapHelper {

    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] ints = new int[2];
        ints[1] = distanceToCenter(layoutManager, targetView);
        return ints;
    }


    private int distanceToCenter(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        if (layoutManager instanceof SmartLayoutManager) {
            SmartLayoutManager lm = (SmartLayoutManager) layoutManager;
            int offsetForCurrentView = lm.getOffsetForCurrentView(targetView);
            return -offsetForCurrentView;
        }
        return 0;
    }

    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof SmartLayoutManager) {
            SmartLayoutManager lm = (SmartLayoutManager) layoutManager;
            return lm.getCenterChildView();
        }
        return null;
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        if (layoutManager instanceof SmartLayoutManager) {
            SmartLayoutManager lm = (SmartLayoutManager) layoutManager;
            if ((float) velocityY == 0) {
                return 0;
            }
            int sign = (float) velocityY > 0 ? 1 : -1;
            float mTriggerOffset = 0.25f;
            int i = (int) (sign * Math.ceil(((float) velocityY * sign * 0.2 / lm.mDecoratedChildHeight) - mTriggerOffset));
            return lm.centerPosition() + i;
        }
        return 0;
    }
}
