package com.example.effect.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.effect.fragment.FirstFragment;
import com.example.effect.fragment.SecondFragment;
import com.example.effect.fragment.ThirdFragment;

import java.util.ArrayList;
import java.util.List;

public class ParallexAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;

    public ParallexAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
        mFragments.add(new FirstFragment());
        mFragments.add(new SecondFragment());
        mFragments.add(new ThirdFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
