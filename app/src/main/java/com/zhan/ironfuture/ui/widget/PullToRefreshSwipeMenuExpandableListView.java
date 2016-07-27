package com.zhan.ironfuture.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.handmark.pulltorefresh.library.OverscrollHelper;
import com.handmark.pulltorefresh.library.PullToRefreshAdapterViewBase;
import com.handmark.pulltorefresh.library.internal.EmptyViewMethodAccessor;

/**
 * Created by WuYue on 2016/4/27.
 */
public class PullToRefreshSwipeMenuExpandableListView extends PullToRefreshAdapterViewBase<SwipeMenuExpandableListView> {

    public PullToRefreshSwipeMenuExpandableListView(Context context) {
        super(context);
    }

    public PullToRefreshSwipeMenuExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshSwipeMenuExpandableListView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshSwipeMenuExpandableListView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected SwipeMenuExpandableListView createRefreshableView(Context context, AttributeSet attrs) {
        final SwipeMenuExpandableListView lv;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            lv = new InternalExpandableListViewSDK9(context, attrs);
        } else {
            lv = new InternalExpandableListView(context, attrs);
        }

        // Set it to this so it can be used in ListActivity/ListFragment
        lv.setId(android.R.id.list);
        return lv;
    }

    class InternalExpandableListView extends SwipeMenuExpandableListView implements EmptyViewMethodAccessor {

        public InternalExpandableListView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public void setEmptyView(View emptyView) {
            PullToRefreshSwipeMenuExpandableListView.this.setEmptyView(emptyView);
        }

        @Override
        public void setEmptyViewInternal(View emptyView) {
            super.setEmptyView(emptyView);
        }
    }

    @TargetApi(9)
    final class InternalExpandableListViewSDK9 extends InternalExpandableListView {

        public InternalExpandableListViewSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                       int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

            final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                    scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

            // Does all of the hard work...
            OverscrollHelper.overScrollBy(PullToRefreshSwipeMenuExpandableListView.this, deltaX, scrollX, deltaY, scrollY,
                    isTouchEvent);

            return returnValue;
        }
    }
}