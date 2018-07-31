package com.example.effect.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

public class BallLoadingView extends LinearLayout {
    private BallView mGreen;
    private BallView mRed;
    private BallView mBlue;

    private int green = Color.GREEN;
    private int blue = Color.BLUE;
    private int red = Color.RED;

    private int newGreen = Color.CYAN;
    private int newBlue = Color.MAGENTA;
    private int newRed = Color.YELLOW;

    private boolean mIsNormalColor = true;
    private boolean mIsRunning = false;

    public BallLoadingView(Context context) {
        this(context, null);
    }

    public BallLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        mGreen = new BallView(getContext());
        LinearLayout.LayoutParams greenParams = new LinearLayout.LayoutParams(dp2px(20), dp2px(20));
        mGreen.setColor(green);
        addView(mGreen, greenParams);
        mRed = new BallView(getContext());
        mRed.setColor(red);
        LinearLayout.LayoutParams redParams = new LinearLayout.LayoutParams(dp2px(20), dp2px(20));
        redParams.leftMargin = dp2px(50);
        redParams.rightMargin = dp2px(50);
        addView(mRed, redParams);
        mBlue = new BallView(getContext());
        mBlue.setColor(blue);
        LinearLayout.LayoutParams blueParams = new LinearLayout.LayoutParams(dp2px(20), dp2px(20));
        addView(mBlue, blueParams);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public void startLoading() {
        mIsRunning = true;
        gotoCenter();
    }

    private void gotoCenter() {
        ObjectAnimator leftToCenter = ObjectAnimator.ofFloat(mGreen, View.TRANSLATION_X, 0, mRed.getLeft());
        ObjectAnimator rightToCenter = ObjectAnimator.ofFloat(mBlue, View.TRANSLATION_X, 0, mRed.getLeft() - mBlue.getLeft());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(leftToCenter, rightToCenter);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mIsRunning) {
                    changeColor();
                    gotoSide();
                }
            }
        });
        animatorSet.start();
    }

    private void gotoSide() {
        ObjectAnimator centerToLeft = ObjectAnimator.ofFloat(mGreen, View.TRANSLATION_X, mRed.getLeft(), 0);
        ObjectAnimator centerToRight = ObjectAnimator.ofFloat(mBlue, View.TRANSLATION_X, mRed.getLeft() - mBlue.getLeft(), 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(centerToLeft, centerToRight);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mIsRunning) {
                    gotoCenter();
                }
            }
        });
        animatorSet.start();
    }

    private void changeColor() {
        if (mIsNormalColor) {
            mGreen.setColor(newGreen);
            mRed.setColor(newRed);
            mBlue.setColor(newBlue);
            mIsNormalColor = false;
        } else {
            mGreen.setColor(green);
            mRed.setColor(red);
            mBlue.setColor(blue);
            mIsNormalColor = true;
        }
    }

    public void cancelLoading() {
        mIsRunning = false;
    }
}
