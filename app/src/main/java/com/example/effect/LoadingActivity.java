package com.example.effect;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.example.effect.widget.ShapeView;

public class LoadingActivity extends AppCompatActivity {
    private ShapeView shapeView;
    private ImageView shadowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        shadowView = findViewById(R.id.shadow_view);
        shapeView = findViewById(R.id.shape_view);

        upAnimate();
    }

    private void downAnimate() {
        final ObjectAnimator shapeDown = ObjectAnimator.ofFloat(shapeView, View.TRANSLATION_Y, -dp2px(100), 0);
        final ObjectAnimator shadowSmall = ObjectAnimator.ofFloat(shadowView, View.SCALE_X, 1f, 0.5f);
        AnimatorSet downSet = new AnimatorSet();
        downSet.playTogether(shapeDown, shadowSmall);
        downSet.setInterpolator(new AccelerateDecelerateInterpolator());
        downSet.setDuration(500);
        downSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                shapeView.exchangeShape();
                upAnimate();
            }
        });
        downSet.start();
    }

    private void upAnimate() {
        AnimatorSet upSet = new AnimatorSet();
        final ObjectAnimator shapeUp = ObjectAnimator.ofFloat(shapeView, View.TRANSLATION_Y, 0, -dp2px(100));
        final ObjectAnimator squareRotate = ObjectAnimator.ofFloat(shapeView, View.ROTATION, 0, -90);
        final ObjectAnimator triangleRotate = ObjectAnimator.ofFloat(shapeView, View.ROTATION, 0, -120);
        final ObjectAnimator shadowLarge = ObjectAnimator.ofFloat(shadowView, View.SCALE_X, 0.5f, 1f);

        if (shapeView.isRound()) {
            upSet.playTogether(shapeUp, shadowLarge);
        } else if (shapeView.isSquare()) {
            upSet.playTogether(shapeUp, squareRotate, shadowLarge);
        } else {
            upSet.playTogether(shapeUp, triangleRotate, shadowLarge);
        }
        upSet.setInterpolator(new DecelerateInterpolator());
        upSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                downAnimate();
            }
        });
        upSet.start();
    }

    private float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
