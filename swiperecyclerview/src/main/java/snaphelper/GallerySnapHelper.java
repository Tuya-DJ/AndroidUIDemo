package snaphelper;

import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

/**
 * huangdaju
 * 2019-09-19
 **/

public class GallerySnapHelper extends SnapHelper {

    private static final float INVALID_DISTANCE = 1f;
    private static final float MILLISECONDS_PER_INCH = 40f;

    @Nullable
    private OrientationHelper mHorizontalHelper;
    @Nullable
    private RecyclerView mRecyclerView;
    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        mRecyclerView = recyclerView;
        super.attachToRecyclerView(recyclerView);
    }

    @Nullable
    @Override
    protected RecyclerView.SmoothScroller createScroller(RecyclerView.LayoutManager layoutManager) {
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return null;
        }
        return new LinearSmoothScroller(mRecyclerView.getContext()) {
            @Override
            protected void onTargetFound(View targetView, RecyclerView.State state, RecyclerView.SmoothScroller.Action action) {
                if (mRecyclerView == null) {
                    // The associated RecyclerView has been removed so there is no action to take.
                    return;
                }
                int[] snapDistances = calculateDistanceToFinalSnap(mRecyclerView.getLayoutManager(),
                        targetView);
                final int dx = snapDistances[0];
                final int dy = snapDistances[1];
                final int time = calculateTimeForDeceleration(Math.max(Math.abs(dx), Math.abs(dy)));
                if (time > 0) {
                    action.update(dx, dy, time, mDecelerateInterpolator);
                }
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
            }
        };
    }

    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager));
        } else {
            out[0] = 0;
        }
        return out;
    }

    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        return findStartView(layoutManager, getHorizontalHelper(layoutManager));
    }

    private int distanceToStart(
            @NonNull View targetView, OrientationHelper helper) {
        final int childCenter = helper.getDecoratedStart(targetView);
        final int containerCenter = helper.getStartAfterPadding();
        return childCenter - containerCenter;
    }


    private View findStartView(RecyclerView.LayoutManager layoutManager, OrientationHelper horizontalHelper) {
        if (layoutManager instanceof LinearLayoutManager) {
            int firstVisibleChildPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            if (firstVisibleChildPosition == RecyclerView.NO_POSITION) {
                return null;
            }
            int lastVisibleChildPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
            if (lastVisibleChildPosition == layoutManager.getItemCount() - 1) {
                return null;
            }
            View firstVisibleChildView = layoutManager.findViewByPosition(firstVisibleChildPosition);
            if (mHorizontalHelper.getDecoratedEnd(firstVisibleChildView) >= mHorizontalHelper.getDecoratedMeasurement(firstVisibleChildView) / 2 && mHorizontalHelper.getDecoratedEnd(firstVisibleChildView) > 0) {
                return firstVisibleChildView;
            } else {
                return layoutManager.findViewByPosition(firstVisibleChildPosition + 1);
            }
        } else {
            return null;
        }
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return RecyclerView.NO_POSITION;
        }
        final int itemCount = layoutManager.getItemCount();
        if (itemCount == 0) {
            return RecyclerView.NO_POSITION;
        }
        final View currentView = findSnapView(layoutManager);
        if (null == currentView) {
            return RecyclerView.NO_POSITION;
        }

        final int currentPosition = layoutManager.getPosition(currentView);
        if (currentPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION;
        }

        RecyclerView.SmoothScroller.ScrollVectorProvider vectorProvider =
                (RecyclerView.SmoothScroller.ScrollVectorProvider) layoutManager;

        PointF vectorForEnd = vectorProvider.computeScrollVectorForPosition(itemCount - 1);
        if (vectorForEnd == null) {
            return RecyclerView.NO_POSITION;
        }

        int deltaThresHold = layoutManager.getWidth() / getHorizontalHelper(layoutManager).getDecoratedMeasurement(currentView);
        int deltaJump;
        if ((layoutManager.canScrollHorizontally())) {
            deltaJump = estimateNextPositionDiffForFling(layoutManager,
                    getHorizontalHelper(layoutManager), velocityX, 0);

            if (deltaJump > deltaThresHold){
                deltaJump = deltaThresHold;
            }
            if (deltaJump < -deltaThresHold){
                deltaJump = -deltaThresHold;
            }
            if (vectorForEnd.x < 0) {
                deltaJump = -deltaJump;
            }
        } else {
            deltaJump = 0;
        }

        if (deltaJump == 0) {
            return RecyclerView.NO_POSITION;
        }
        int targetPos = currentPosition + deltaJump;
        if (targetPos < 0) {
            targetPos = 0;
        }
        if (targetPos >= itemCount) {
            targetPos = itemCount - 1;
        }
        return targetPos;
    }

    private int estimateNextPositionDiffForFling(RecyclerView.LayoutManager layoutManager,
                                                 OrientationHelper helper, int velocityX, int velocityY) {
        int[] distances = calculateScrollDistance(velocityX, velocityY);
        float distancePerChild = computeDistancePerChild(layoutManager, helper);
        if (distancePerChild <= 0) {
            return 0;
        }
        int distance = distances[0];
        if (distance > 0) {
            return (int) Math.floor(distance / distancePerChild);
        } else {
            return (int) Math.ceil(distance / distancePerChild);
        }
    }

    private float computeDistancePerChild(RecyclerView.LayoutManager layoutManager,
                                          OrientationHelper helper) {
        View minPosView = null;
        View maxPosView = null;
        int minPos = Integer.MAX_VALUE;
        int maxPos = Integer.MIN_VALUE;
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return INVALID_DISTANCE;
        }

        for (int i = 0; i < childCount; i++) {
            View child = layoutManager.getChildAt(i);
            final int pos = layoutManager.getPosition(child);
            if (pos == RecyclerView.NO_POSITION) {
                continue;
            }
            if (pos < minPos) {
                minPos = pos;
                minPosView = child;
            }
            if (pos > maxPos) {
                maxPos = pos;
                maxPosView = child;
            }
        }
        if (minPosView == null || maxPosView == null) {
            return INVALID_DISTANCE;
        }
        int start = Math.min(helper.getDecoratedStart(minPosView),
                helper.getDecoratedStart(maxPosView));
        int end = Math.max(helper.getDecoratedEnd(minPosView),
                helper.getDecoratedEnd(maxPosView));
        int distance = end - start;
        if (distance == 0) {
            return INVALID_DISTANCE;
        }
        return 1f * distance / ((maxPos - minPos) + 1);
    }

    @NonNull
    private OrientationHelper getHorizontalHelper(
            @NonNull RecyclerView.LayoutManager layoutManager) {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return mHorizontalHelper;
    }
}
