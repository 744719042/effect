package com.example.effect;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.ImageView;

import com.example.effect.widget.MyScrollView;

public class SpinActivity extends AppCompatActivity {
    private MyScrollView scrollView;
    private ImageView imageView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView = findViewById(R.id.image);
        scrollView = findViewById(R.id.scroll_view);
        scrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScrollStateChange(MyScrollView scrollView, int l, int t, int oldl, int oldt) {
                int total = imageView.getHeight() - dp2px(56);
                if (t <= total) {
                    float ratio = t * 1.0f / total;
                    int alpha = (int) (255 * ratio);
                    toolbar.setBackgroundColor(Color.argb(alpha, 0x3F, 0x51, 0xB5));
                } else {
                    toolbar.setBackgroundColor(Color.argb(255, 0x3F, 0x51, 0xB5));
                }
            }
        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
