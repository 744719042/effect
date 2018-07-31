package com.example.effect.adapter;

import android.content.Context;
import android.view.View;

import com.example.effect.utils.CollectionUtils;

import java.util.List;

public abstract class MenuAdapter<M, O> {
    protected List<M> mMenuData;
    protected List<O> mOptionData;
    protected Context mContext;

    public MenuAdapter(Context context, List<M> menuData, List<O> optionData) {
        if (CollectionUtils.isEmpty(menuData) || CollectionUtils.isEmpty(optionData)) {
            throw new NullPointerException("Empty data");
        }

        if (menuData.size() != optionData.size()) {
            throw new IllegalArgumentException("Unfit data size");
        }

        this.mContext = context;
        this.mMenuData = menuData;
        this.mOptionData = optionData;
    }

    public int getItemCount() {
        return mMenuData.size();
    }

    public M getMenuItem(int position) {
        return mMenuData.get(position);
    }

    public O getOptionItem(int position) {
        return mOptionData.get(position);
    }

    public abstract View getMenuView(int position);

    public abstract View getOptionView(int position);
}
