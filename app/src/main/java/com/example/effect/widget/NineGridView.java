package com.example.effect.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class NineGridView extends View {
    private List<Point> mSelected = new ArrayList<>();
    private Point[] mPoints = new Point[9];
    private int mInnerRadius = dp2px(10);
    private int mOuterRadius;

    private Paint mInnerPaint;
    private Paint mOuterPaint;
    private int mLastX;
    private int mLastY;

    public NineGridView(Context context) {
        this(context, null);
    }

    public NineGridView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mInnerPaint = new Paint();
        mInnerPaint.setDither(true);
        mInnerPaint.setColor(Color.GRAY);
        mInnerPaint.setStrokeWidth(dp2px(2));
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setAntiAlias(true);

        mOuterPaint = new Paint();
        mOuterPaint.setDither(true);
        mOuterPaint.setStrokeWidth(dp2px(3));
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mOuterPaint.setColor(Color.DKGRAY);
        mOuterPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initPoints();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private void initPoints() {
        int width = getMeasuredWidth(), height = getMeasuredHeight();
        if (getMeasuredWidth() > getMeasuredHeight()) {
            width = getMeasuredHeight();
            height = getMeasuredWidth();
        }

        int gridWidth = (width - getPaddingLeft() - getPaddingRight()) / 3;
        mOuterRadius = (gridWidth - dp2px(40)) / 2;
        int topOffset = (height - width) / 2;
        for (int i = 0; i < 9; i++) {
            mPoints[i] = new Point();
            int row = i / 3, col = i % 3;
            mPoints[i].centerX = getPaddingLeft() + col * gridWidth + gridWidth / 2;
            mPoints[i].centerY = topOffset + row * gridWidth + gridWidth / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < 9; i++) {
            Point point = mPoints[i];
            switch (point.state) {
                case NORMAL:
                    mInnerPaint.setColor(Color.GRAY);
                    mOuterPaint.setColor(Color.DKGRAY);
                    break;
                case SELECTED:
                    mInnerPaint.setColor(Color.MAGENTA);
                    mOuterPaint.setColor(Color.MAGENTA);
                    break;
                case RIGHT:
                    mInnerPaint.setColor(Color.GREEN);
                    mOuterPaint.setColor(Color.GREEN);
                    break;
                case WRONG:
                    mInnerPaint.setColor(Color.RED);
                    mOuterPaint.setColor(Color.RED);
                    break;
            }
            canvas.drawCircle(point.centerX, point.centerY, mInnerRadius, mInnerPaint);
            canvas.drawCircle(point.centerX, point.centerY, mOuterRadius, mOuterPaint);
        }

        for (int i = 1; i < mSelected.size(); i++) {
            Point pre = mSelected.get(i - 1);
            Point cur = mSelected.get(i);

            canvas.drawLine(pre.centerX, pre.centerY, cur.centerX, cur.centerY, mOuterPaint);
            if (i == mSelected.size() - 1 && mSelected.size() != 9) {
                canvas.drawLine(cur.centerX, cur.centerY, mLastX, mLastY, mOuterPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX(), y = (int) event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                Point point = getPoint(x, y);
                if (point == null) {
                    return false;
                }

                mSelected.add(point);
                point.state = State.SELECTED;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                Point point = getPoint(x, y);
                if (point != null && !mSelected.contains(point)) {
                    point.state = State.SELECTED;
                    mSelected.add(point);
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                for (Point point : mSelected) {
                    point.state = State.NORMAL;
                }
                mSelected.clear();
                break;
            }
        }
        mLastX = x;
        mLastY = y;
        invalidate();
        return true;
    }

    public Point getPoint(int x, int y) {
        for (int i = 0; i < 9; i++) {
            Point point = mPoints[i];
            if (isInRound(point, x, y)) {
                return point;
            }
        }

        return null;
    }

    private boolean isInRound(Point point, int x, int y) {
        if (Math.hypot(x - point.centerX, y - point.centerY) <= mOuterRadius) {
            return true;
        }
        return false;
    }

    enum State {
        NORMAL, SELECTED, RIGHT, WRONG
    }

    static class Point {
        public int centerX;
        public int centerY;
        public State state = State.NORMAL;
    }
}
