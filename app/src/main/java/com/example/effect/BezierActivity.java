package com.example.effect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.effect.widget.FlowerView;

public class BezierActivity extends AppCompatActivity implements View.OnClickListener {
    private Button addFlower;
    private FlowerView flowerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);
        addFlower = findViewById(R.id.addFlower);
        addFlower.setOnClickListener(this);
        flowerView = findViewById(R.id.flower_view);
        flowerView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == addFlower) {
            flowerView.addImageView();
        }
    }
}
