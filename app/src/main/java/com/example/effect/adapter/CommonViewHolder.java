package com.example.effect.adapter;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * RecyclerView通用ViewHolder
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;

    public CommonViewHolder(View itemView) {
        super(itemView);
        this.mViews = new SparseArray<>();
    }

    public <T extends View> T getView(@IdRes int id) {
        T view = (T) mViews.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            mViews.put(id, view);
        }

        return view;
    }

    public void setText(@IdRes int id, String text) {
        TextView textView = getView(id);
        textView.setText(text);
    }

    public void setText(@IdRes int id, @StringRes int text) {
        TextView textView = getView(id);
        textView.setText(text);
    }

    public void setImageBitmap(@IdRes int id, Bitmap bitmap) {
        ImageView imageView = getView(id);
        imageView.setImageBitmap(bitmap);
    }

    public void setImageResource(@IdRes int id, @DrawableRes int drawable) {
        ImageView imageView = getView(id);
        imageView.setImageResource(drawable);
    }

//    public void setImageUrl(@IdRes int id, String url, @DrawableRes int placeholder) {
//        ImageView imageView = getView(id);
//        Glide.with(imageView.getContext())
//                .load(url)
//                .apply(new RequestOptions().placeholder(placeholder).dontTransform())
//                .into(imageView);
//    }

    public void setOnClickListener(@IdRes int id, View.OnClickListener onClickListener) {
        getView(id).setOnClickListener(onClickListener);
    }

    public void setOnLongClickListener(@IdRes int id, View.OnLongClickListener onLongClickListener) {
        getView(id).setOnLongClickListener(onLongClickListener);
    }
}
