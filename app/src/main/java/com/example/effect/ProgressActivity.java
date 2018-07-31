package com.example.effect;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;

import com.example.effect.widget.ProgressView;

public class ProgressActivity extends AppCompatActivity {
    private ProgressView mRoundText;
    private ProgressView mRoundPercent;
    private ProgressView mLineText;
    private ProgressView mLinePercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        mRoundText = findViewById(R.id.round_text);
        mRoundPercent = findViewById(R.id.round_percent);
        mLineText = findViewById(R.id.line_text);
        mLinePercent = findViewById(R.id.line_percent);

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mRoundPercent.getMax());
        valueAnimator.setDuration(3000);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int current = (int) valueAnimator.getAnimatedValue();
                mRoundPercent.setCurrent(current);
                mRoundText.setCurrent(current);
                mLinePercent.setCurrent(current);
                mLineText.setCurrent(current);
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.start();
    }
}
