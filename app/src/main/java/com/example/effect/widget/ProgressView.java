package com.example.effect.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.effect.R;

public class ProgressView extends View {
    private static final int ROUND = 0;
    private static final int LINE = 1;

    private static final int PERCENT = 0;
    private static final int TEXT = 1;

    private int mShape = ROUND;
    private int mType = TEXT;

    private Paint mShapePaint;
    private Paint mTextPaint;

    @ColorInt
    private int mColorUnprocessed = Color.GRAY;
    @ColorInt
    private int mColorProcessed = Color.RED;
    @ColorInt
    private int mColorText = Color.BLACK;

    private int mMaxCount = 111;
    private int mCurrent = 0;

    private RectF mTmpRectF;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
            mMaxCount = array.getInt(R.styleable.ProgressView_progress_max_count, mMaxCount);
            mCurrent = array.getInt(R.styleable.ProgressView_progress_current, mCurrent);
            mShape = array.getInt(R.styleable.ProgressView_progress_shape, ROUND);
            mType = array.getInt(R.styleable.ProgressView_progress_type, TEXT);
            array.recycle();
        }
        mShapePaint = new Paint();
        mShapePaint.setAntiAlias(true);
        mShapePaint.setDither(true);
        mShapePaint.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setTextSize(dp2px(30));
        mTextPaint.setColor(mColorText);

        mTmpRectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float progress = mCurrent * 1.0f / mMaxCount;

        if (mShape == ROUND) {
            int radius = (int) Math.min(getWidth() / 2 - dp2px(5), getHeight() / 2 - dp2px(5));
            mShapePaint.setStyle(Paint.Style.STROKE);
            mShapePaint.setStrokeWidth(dp2px(5));
            mShapePaint.setColor(mColorUnprocessed);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mShapePaint);

            mTmpRectF.set(getWidth() / 2 - radius, getHeight() / 2 - radius, getWidth() / 2 + radius,
                    getHeight() / 2 + radius);
            mShapePaint.setColor(mColorProcessed);
            canvas.drawArc(mTmpRectF, 0, progress * 360, false, mShapePaint);
            Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
            int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;

            String text = mType == PERCENT ? String.format("%.2f%%", progress * 100f) : String.valueOf(mCurrent);
            int x = (int) (getWidth() / 2 - mTextPaint.measureText(text) / 2);
            int y = getHeight() / 2 + dy;
            canvas.drawText(text, x, y, mTextPaint);
        } else {
            mShapePaint.setStyle(Paint.Style.FILL);
            mShapePaint.setColor(mColorUnprocessed);
            mTmpRectF.set(getPaddingLeft(), getHeight() - getPaddingBottom() - dp2px(5),
                    getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
            canvas.drawRect(mTmpRectF, mShapePaint);

            mTmpRectF.set(getPaddingLeft(), getHeight() - getPaddingBottom() - dp2px(5),
                    getPaddingLeft() + (getWidth() - getPaddingLeft() - getPaddingRight()) * progress,
                    getHeight() - getPaddingBottom());
            mShapePaint.setColor(mColorProcessed);
            canvas.drawRect(mTmpRectF, mShapePaint);

            String text = mType == PERCENT ? String.format("%.2f%%", progress * 100f) : String.valueOf(mCurrent);
            int x = (int) (getPaddingLeft() + (getWidth() - getPaddingLeft() - getPaddingRight()) * progress);
            Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
            int y = getHeight() - getPaddingBottom() - fontMetricsInt.descent;
            canvas.drawText(text, x, y, mTextPaint);
        }
    }

    private float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public void setCurrent(int current) {
        this.mCurrent = current;
        invalidate();
    }

    public void setMaxCount(int maxCount) {
        if (maxCount == 0) {
            throw new IllegalArgumentException("mMaxCount must be positive!");
        }
        this.mMaxCount = maxCount;
        invalidate();
    }

    public int getMax() {
        return mMaxCount;
    }
}
