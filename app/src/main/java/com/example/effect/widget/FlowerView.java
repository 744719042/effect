package com.example.effect.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.effect.R;
import com.example.effect.evaluate.BezierEvaluation;

import java.util.Random;

public class FlowerView extends FrameLayout {
    private Random random = new Random();
    private int[] lover = new int[] {
            R.drawable.blue_lover,
            R.drawable.blue_stroke,
            R.drawable.red,
            R.drawable.yellow
    };
    public FlowerView(Context context) {
        this(context, null);
    }

    public FlowerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addImageView() {
        final ImageView imageView = new ImageView(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(dp2px(30), dp2px(30));
        params.bottomMargin = dp2px(30);
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        imageView.setImageResource(lover[random.nextInt(4)]);
        addView(imageView, params);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, View.ALPHA, 0f, 1.0f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, View.SCALE_X, 0.3f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, View.SCALE_Y, 0.3f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alpha, scaleX, scaleY);
        animatorSet.setDuration(300);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                playUpAnimation(imageView);
            }
        });
        animatorSet.start();
    }

    private void playUpAnimation(final ImageView imageView) {
        PointF start  = new PointF((imageView.getLeft() + imageView.getRight()) / 2,
                (imageView.getTop() + imageView.getBottom()) / 2);
        PointF end = new PointF(random.nextInt(getWidth()), -imageView.getHeight());

        PointF control1 = new PointF(random.nextInt(getWidth() / 2),
                random.nextInt(getHeight() / 2));
        PointF control2 = new PointF(random.nextInt(getWidth() / 2) + getWidth() / 2,
                random.nextInt(getHeight() / 2) + getHeight() / 2);


        ValueAnimator objectAnimator = ObjectAnimator.ofObject(new BezierEvaluation(control1, control2), start, end);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF value = (PointF) animation.getAnimatedValue();
                imageView.setX(value.x);
                imageView.setY(value.y);
            }
        });
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(imageView);
            }
        });
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
