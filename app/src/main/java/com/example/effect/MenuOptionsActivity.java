package com.example.effect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.effect.adapter.MyMenuAdapter;
import com.example.effect.widget.OptionView;

import java.util.Arrays;

public class MenuOptionsActivity extends AppCompatActivity {
    private OptionView mOptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_options);
        mOptionView = findViewById(R.id.option_view);
        mOptionView.setAdapter(new MyMenuAdapter(this, Arrays.asList("位置", "价格", "折扣"),
                Arrays.asList("Position", "Price", "Discount")));
    }
}
