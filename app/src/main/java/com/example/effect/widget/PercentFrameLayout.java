package com.example.effect.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.example.effect.R;

public class PercentFrameLayout extends FrameLayout {
    public PercentFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public PercentFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {
        private float widthRatio = -1f;
        private float aspect = -1f;

        public LayoutParams(@NonNull Context c, @Nullable AttributeSet attrs) {
            super(c, attrs);
            TypedArray array = c.obtainStyledAttributes(attrs, R.styleable.PercentFrameLayout);
            this.widthRatio = array.getFloat(R.styleable.PercentFrameLayout_layout_widthRatio, widthRatio);
            this.aspect = array.getFloat(R.styleable.PercentFrameLayout_layout_aspect, aspect);
            array.recycle();
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (layoutParams.widthRatio > 0 && layoutParams.aspect > 0) {
                int ratioWidth = (int) (getMeasuredWidth() * layoutParams.widthRatio);
                view.measure(MeasureSpec.makeMeasureSpec(ratioWidth, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec((int) (ratioWidth * layoutParams.aspect), MeasureSpec.EXACTLY));
            }
        }
    }
}
