package com.example.effect.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.effect.common.CommonUtils;

import java.util.List;

public class MyMenuAdapter extends MenuAdapter<String, String> {
    public MyMenuAdapter(Context context, List<String> menuData, List<String> optionData) {
        super(context, menuData, optionData);
    }

    @Override
    public View getMenuView(int position) {
        TextView textView = new TextView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setText(getMenuItem(position));
        return textView;
    }

    @Override
    public View getOptionView(int position) {
        FrameLayout frameLayout = new FrameLayout(mContext);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (0.6 * CommonUtils.getScreenWidth(mContext)));
        frameLayout.setBackgroundColor(Color.WHITE);
        frameLayout.setLayoutParams(params);
        return frameLayout;
    }

}
