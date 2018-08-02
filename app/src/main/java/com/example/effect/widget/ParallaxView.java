package com.example.effect.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.example.effect.R;

public class ParallaxView extends View {
    private int[] mColors = new int[] { Color.RED, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA, Color.YELLOW };
    private Paint mPaint;
    private DrawState mDrawState = new RotateState();
    private final static int NORMAL_RADIUS = dp2px(60);
    private final static int MAX_RADIUS = dp2px(120);
    private final static int CIRCLE_RADIUS = dp2px(10);
    private int mOffset = 0;
    private int mDistance = NORMAL_RADIUS;

    private ValueAnimator mRotateAnimation;
    private ValueAnimator mTranslateAnimation;

    private int mCircle;
    private Paint mBackgroundPaint;
    private BitmapShader mBitmapShader;
    private Path mPath;

    private static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public interface DrawState {
        void draw(Canvas canvas);
    }

    public ParallaxView(Context context) {
        this(context, null);
    }

    public ParallaxView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParallaxView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setDither(true);
        mBackgroundPaint.setAntiAlias(true);
        mBitmapShader = new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.stars),
                BitmapShader.TileMode.MIRROR, BitmapShader.TileMode.MIRROR);
        mBackgroundPaint.setShader(mBitmapShader);

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 360);
        valueAnimator.setDuration(800);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
        mRotateAnimation = valueAnimator;
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDrawState != null) {
            mDrawState.draw(canvas);
        }
    }

    public class RotateState implements DrawState {

        @Override
        public void draw(Canvas canvas) {
            for (int i = 0; i < mColors.length; i++) {
                int color = mColors[i];
                mPaint.setColor(color);
                int angle = i * 60 + mOffset;
                int centerX = getWidth () / 2 + (int) (NORMAL_RADIUS * Math.cos(angle * Math.PI / 180));
                int centerY = getHeight() / 2 + (int) (NORMAL_RADIUS * Math.sin(angle * Math.PI / 180));
                canvas.drawCircle(centerX, centerY, CIRCLE_RADIUS, mPaint);
            }
        }
    }

    public class MergeState implements DrawState {

        @Override
        public void draw(Canvas canvas) {
            for (int i = 0; i < mColors.length; i++) {
                int color = mColors[i];
                mPaint.setColor(color);
                int angle = i * 60 + mOffset;
                int centerX = getWidth () / 2 + (int) (mDistance * Math.cos(angle * Math.PI / 180));
                int centerY = getHeight() / 2 + (int) (mDistance * Math.sin(angle * Math.PI / 180));
                canvas.drawCircle(centerX, centerY, CIRCLE_RADIUS, mPaint);
            }
        }
    }

    public class SpreadState implements DrawState {

        @Override
        public void draw(Canvas canvas) {
            if (mCircle == 0) {
                mPaint.setColor(Color.WHITE);
                canvas.drawPaint(mPaint);
            }
//            mPath.reset();
//            mPath.addCircle(getWidth() / 2, getHeight() / 2, mCircle, Path.Direction.CCW);
//            canvas.clipPath(mPath);

//            mBackgroundPaint.setStrokeWidth(mCircle);
            canvas.drawCircle(getWidth()/ 2, getHeight() / 2, mCircle, mBackgroundPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_UP:
                mDrawState = new MergeState();
                final ValueAnimator valueAnimator = ValueAnimator.ofInt(NORMAL_RADIUS, MAX_RADIUS, 0);
                valueAnimator.setDuration(1500);
                valueAnimator.setInterpolator(new AccelerateInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mDistance = (int) valueAnimator.getAnimatedValue();
                        invalidate();
                    }
                });

                valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mDrawState = new SpreadState();
                        startSpread();
                    }
                });
                valueAnimator.start();
                mRotateAnimation.cancel();
                mTranslateAnimation = valueAnimator;
                break;
        }
        return true;
    }

    private void startSpread() {
        mTranslateAnimation.cancel();
        int lastValue = (int) Math.hypot(getHeight() / 2, getWidth() / 2);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, lastValue);
        valueAnimator.setDuration(1500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCircle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }
}
