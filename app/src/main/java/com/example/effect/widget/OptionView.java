package com.example.effect.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.effect.adapter.MenuAdapter;

import java.util.ArrayList;
import java.util.List;

public class OptionView extends LinearLayout {
    private LinearLayout mMenuLayout;
    private FrameLayout mOptionLayout;
    private TextView mBottomView;
    private MenuAdapter mAdapter;

    private int mSelected = -1;
    private List<View> mMenuViews = new ArrayList<>();
    private List<View> mOptionViews = new ArrayList<>();

    public OptionView(Context context) {
        this(context, null);
    }

    public OptionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OptionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);

        mMenuLayout = new LinearLayout(getContext());
        mMenuLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams menuParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp2px(45)
        );
        addView(mMenuLayout, menuParams);

        mOptionLayout = new FrameLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mOptionLayout, params);

        mBottomView = new TextView(getContext());
        mBottomView.setBackgroundColor(Color.argb(0x99, 0x99, 0x99, 0x99));
        mBottomView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelected != -1) {
                    View optionView = mOptionViews.get(mSelected);
                    mBottomView.setVisibility(View.GONE);
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(optionView, View.TRANSLATION_Y, 0, -optionView.getHeight());
                    objectAnimator.setDuration(500);
                    objectAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mSelected = -1;
                            mOptionLayout.setVisibility(View.INVISIBLE);
                        }
                    });
                    objectAnimator.start();
                }
            }
        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public void setAdapter(MenuAdapter adapter) {
        this.mAdapter = adapter;
        this.mOptionViews.clear();
        this.mMenuViews.clear();
        mMenuLayout.removeAllViews();
        mOptionLayout.removeAllViews();
        FrameLayout.LayoutParams bottomParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mOptionLayout.addView(mBottomView, bottomParams);
        mOptionLayout.setVisibility(View.INVISIBLE);

        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            View menuView = mAdapter.getMenuView(i);
            mMenuViews.add(menuView);
            mMenuLayout.addView(menuView);
            final View optionView = mAdapter.getOptionView(i);
            optionView.setVisibility(View.INVISIBLE);
            mOptionViews.add(optionView);
            mOptionLayout.addView(optionView);
            final int position = i;
            menuView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSelected != -1) {
                        View preView = mOptionViews.get(mSelected);
                        preView.setVisibility(View.INVISIBLE);
                        preView.setTranslationY(-preView.getHeight());
                    }

                    optionView.setVisibility(View.VISIBLE);
                    mBottomView.setVisibility(View.VISIBLE);
                    mOptionLayout.setVisibility(View.VISIBLE);
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(optionView, View.TRANSLATION_Y, -optionView.getHeight(), 0);
                    objectAnimator.setDuration(500);
                    objectAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mSelected = position;
                        }
                    });
                    objectAnimator.start();
                }
            });
        }
    }
}
