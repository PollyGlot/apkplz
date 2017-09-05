package com.example.pollyglot.apkplz.helper;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class ScrollFabBehavior extends FloatingActionButton.Behavior {


    private static final String TAG = "ScrollFabBehavior";
    private Handler mHandler;

    public ScrollFabBehavior(Context context, AttributeSet attrs) {
        super();
         Log.e(TAG, "ScrollAwareFABBehavior");
    }


    public boolean onStartNestedScroll(CoordinatorLayout parent, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        if(mHandler!=null) {
            mHandler.removeMessages(0);
            Log.d("Scrolling","stopHandler()");
        }
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, final FloatingActionButton mFab, View target) {
        super.onStopNestedScroll(coordinatorLayout, mFab, target);

        if (mHandler == null)
            mHandler = new Handler();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFab.show();
                Log.d("FabAnim","startHandler()");
            }
        },1000);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton mFab, View dependency) {
        return dependency instanceof RecyclerView;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton mFab, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, mFab, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

        if (dyConsumed > 0) {
            Log.d("Scrolling","Up");
            mFab.hide();
        } else if (dyConsumed < 0) {
            Log.d("Scrolling","down");
            mFab.show();
        }

    }
}