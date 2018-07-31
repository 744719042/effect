package com.example.effect.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.example.effect.R;
import com.example.effect.common.CommonUtils;

public class SlideMenu extends HorizontalScrollView {
    private ViewGroup mContainer;
    private View mMenuView;
    private View mContentView;
//    private View mMaskView;
    private GestureDetector mDetector;
    private boolean mIsMenuOpen = false;

    public SlideMenu(Context context) {
        this(context, null);
    }

    public SlideMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (mIsMenuOpen) {
                    if (velocityX < -500f) {
                        closeMenu();
                        return true;
                    }
                } else {
                    if (velocityX > 500f) {
                        openMenu();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        scrollTo(mMenuView.getWidth(), 0);
    }

    @Override
    protected void onScrollChanged(final int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.d("TAG", "l = " + l);
//        mMaskView.post(new Runnable() {
//            @Override
//            public void run() {
//                float ratio = l * 1.0f / mMenuView.getWidth();
//                mMaskView.setAlpha(1- ratio);
//            }
//        });
        float ratio = l * 1.0f / mMenuView.getWidth();
        float scale = 0.7f + 0.3f * ratio;
        mContentView.setPivotX(0f);
        mContentView.setPivotY(mContainer.getHeight() / 2);
        mContentView.setScaleX(scale);
        mContentView.setScaleY(scale);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContainer = (ViewGroup) getChildAt(0);
        mMenuView = mContainer.getChildAt(0);
        mContentView = mContainer.getChildAt(1);
//        mMaskView = mContainer.findViewById(R.id.mask_view);

        int menuWidth = (int) (CommonUtils.getScreenWidth(getContext()) * 0.8f);
        mMenuView.getLayoutParams().width = menuWidth;
        mMenuView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        mContentView.getLayoutParams().width = CommonUtils.getScreenWidth(getContext());
        mContentView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        mContainer.getLayoutParams().width = (int) (1.8 * CommonUtils.getScreenWidth(getContext()));
        mContainer.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mDetector.onTouchEvent(ev)) {
            return true;
        }

        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int left = getScrollX();
                if (left > mMenuView.getWidth() / 2) {
                    closeMenu();
                } else {
                    openMenu();
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    private void closeMenu() {
        smoothScrollTo(mMenuView.getWidth(), 0);
        mIsMenuOpen = false;
    }

    private void openMenu() {
        smoothScrollTo(0, 0);
        mIsMenuOpen = true;
    }
}
