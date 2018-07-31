package com.example.effect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.effect.widget.BallLoadingView;

public class BallLoadingActivity extends AppCompatActivity {
    private BallLoadingView ballLoadingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_loading);
        ballLoadingView = findViewById(R.id.ballLoading);
        ballLoadingView.startLoading();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ballLoadingView.cancelLoading();
    }
}
