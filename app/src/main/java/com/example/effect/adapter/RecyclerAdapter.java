package com.example.effect.adapter;

import android.content.Context;

import com.example.effect.R;

import java.util.List;

public class RecyclerAdapter extends CommonRecyclerAdapter<User> {
    public RecyclerAdapter(List<User> data, Context context, int layoutId) {
        super(data, context, layoutId);
    }

    @Override
    protected void bindView(CommonViewHolder holder, User user, int position) {
        holder.setText(R.id.name, user.getName());
        holder.setText(R.id.desc, user.getDesc());
        holder.setImageResource(R.id.portrait, user.getPortrait());
    }
}
