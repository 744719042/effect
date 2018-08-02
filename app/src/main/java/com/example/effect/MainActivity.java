package com.example.effect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button progressBar;
    private Button loading;
    private Button spinTop;
    private Button qq;
    private Button ballLoading;
    private Button collapsePage;
    private Button optionBar;
    private Button nineGrids;
    private Button parallex;
    private Button bezier;
    private Button percent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setOnClickListener(this);
        loading = findViewById(R.id.loading);
        loading.setOnClickListener(this);
        spinTop = findViewById(R.id.spinTop);
        spinTop.setOnClickListener(this);
        qq = findViewById(R.id.qq);
        qq.setOnClickListener(this);
        ballLoading = findViewById(R.id.ballLoading);
        ballLoading.setOnClickListener(this);
        collapsePage = findViewById(R.id.collapsePage);
        collapsePage.setOnClickListener(this);
        optionBar = findViewById(R.id.optionBar);
        optionBar.setOnClickListener(this);
        nineGrids = findViewById(R.id.ninegrids);
        nineGrids.setOnClickListener(this);
        parallex = findViewById(R.id.parallex);
        parallex.setOnClickListener(this);
        percent = findViewById(R.id.percent);
        percent.setOnClickListener(this);
        bezier = findViewById(R.id.bezier);
        bezier.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == progressBar) {
            Intent intent = new Intent(this, ProgressActivity.class);
            startActivity(intent);
        } else if (view == loading) {
            Intent intent = new Intent(this, LoadingActivity.class);
            startActivity(intent);
        } else if (view == spinTop) {
            Intent intent = new Intent(this, SpinActivity.class);
            startActivity(intent);
        } else if (view == qq) {
            Intent intent = new Intent(this, QQSlideActivity.class);
            startActivity(intent);
        } else if (view == ballLoading) {
            Intent intent = new Intent(this, BallLoadingActivity.class);
            startActivity(intent);
        } else if (view == collapsePage) {
            Intent intent = new Intent(this, CollapsePageActivity.class);
            startActivity(intent);
        } else if (view == optionBar) {
            Intent intent = new Intent(this, MenuOptionsActivity.class);
            startActivity(intent);
        } else if (view == nineGrids) {
            Intent intent = new Intent(this, NineGridsActivity.class);
            startActivity(intent);
        } else if (view == parallex) {
            Intent intent = new Intent(this, ParallexActivity.class);
            startActivity(intent);
        } else if (view == percent) {
            Intent intent = new Intent(this, ScrollViewActivity.class);
            startActivity(intent);
        } else if (view == bezier) {
            Intent intent = new Intent(this, BezierActivity.class);
            startActivity(intent);
        }
    }
}
