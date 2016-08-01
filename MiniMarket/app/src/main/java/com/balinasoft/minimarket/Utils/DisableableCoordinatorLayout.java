package com.balinasoft.minimarket.Utils;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

public class DisableableCoordinatorLayout extends CoordinatorLayout {
    private boolean mPassScrolling = true;

    public DisableableCoordinatorLayout(Context context) {
        super(context);
    }

    public DisableableCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DisableableCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return mPassScrolling && super.onStartNestedScroll(child, target, nestedScrollAxes);
    }

    public void setPassScrolling(boolean passScrolling) {
        mPassScrolling = passScrolling;
    }
}