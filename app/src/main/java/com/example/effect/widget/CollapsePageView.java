package com.example.effect.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ListViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

public class CollapsePageView extends FrameLayout {
    private View menuView;
    private View listView;
    private ViewDragHelper viewDragHelper;
    private int mDownY;
    private boolean mIsMenuOpen = false;

    public CollapsePageView(@NonNull Context context) {
        this(context, null);
    }

    public CollapsePageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapsePageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return listView == child;
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                return 0;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                if (top < menuView.getHeight()) {
                    return top;
                }
                return menuView.getHeight();
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (listView.getTop() < menuView.getHeight() / 2) {
                    viewDragHelper.settleCapturedViewAt(0, 0);
                    mIsMenuOpen = false;
                } else {
                    viewDragHelper.settleCapturedViewAt(0, menuView.getHeight());
                    mIsMenuOpen = true;
                }
                invalidate();
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        menuView = getChildAt(0);
        listView = getChildAt(1);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (viewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                viewDragHelper.processTouchEvent(ev);
                mDownY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) ev.getY();
                if ((mIsMenuOpen || y - mDownY > 0) && !canChildScrollUp()) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    public boolean canChildScrollUp() {
        if (listView instanceof ListView) {
            return ListViewCompat.canScrollList((ListView) listView, -1);
        }
        return listView.canScrollVertically(-1);
    }
}
