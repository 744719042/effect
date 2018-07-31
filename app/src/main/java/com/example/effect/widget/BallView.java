package com.example.effect.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class BallView extends View {
    private Paint mPaint;
    public BallView(Context context) {
        this(context, null);
    }

    public BallView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int radius = Math.min(getWidth() / 2, getHeight() / 2);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);
    }

    public void setColor(@ColorInt int color) {
        mPaint.setColor(color);
        invalidate();
    }
}
