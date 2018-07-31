package com.example.effect.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ShapeView extends View {
    private static final int ROUND = 0;
    private static final int COLOR_ROUND = Color.CYAN;
    private static final int TRIANGLE = 1;
    private static final int COLOR_TRIANGLE = Color.RED;
    private static final int SQUARE = 2;
    private static final int COLOR_SQUARE = Color.GREEN;

    private int mShape = ROUND;
    private Paint mPaint;
    private Rect mTmpRect;
    private Path mPath;
    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mTmpRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mShape) {
            case ROUND: {
                int radius = Math.min(getWidth() / 2, getHeight() / 2);
                mPaint.setColor(COLOR_ROUND);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);
                break;
            }
            case TRIANGLE: {
                mPaint.setColor(COLOR_TRIANGLE);
                int radius = Math.min(getWidth() / 2, getHeight() / 2);
                int shortSize = (int) (radius * Math.sin(Math.PI / 6));
                int longSize = (int) (radius * Math.sin(Math.PI / 3));

                if (mPath == null) {
                    mPath = new Path();
                    mPath.moveTo(getWidth() / 2, getHeight() / 2 - longSize);
                    mPath.lineTo(getWidth() / 2 - longSize, getHeight() / 2 + shortSize);
                    mPath.lineTo(getWidth() / 2 + longSize, getHeight() / 2 + shortSize);
                    mPath.close();
                }
                canvas.drawPath(mPath, mPaint);
                break;
            }
            case SQUARE: {
                mPaint.setColor(COLOR_SQUARE);
                mTmpRect.set(0, 0, getWidth(), getHeight());
                canvas.drawRect(mTmpRect, mPaint);
                break;
            }
        }
    }

    public void exchangeShape() {
        if (mShape == ROUND) {
            mShape = TRIANGLE;
        } else if (mShape == TRIANGLE) {
            mShape = SQUARE;
        } else {
            mShape = ROUND;
        }
        invalidate();
    }

    public boolean isRound() {
        return mShape == ROUND;
    }

    public boolean isSquare() {
        return mShape == SQUARE;
    }

    public boolean isTriangle() {
        return mShape == TRIANGLE;
    }
}
