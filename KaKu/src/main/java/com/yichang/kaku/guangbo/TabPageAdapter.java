package com.yichang.kaku.guangbo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaolafm on 2016/4/6.
 * 这个adapter是平分的 和CategoryTabPageIndicator 样式有很大差别
 */
public class TabPageAdapter extends FragmentPagerAdapter {

    private ArrayList<String> titleList;
    private List<Fragment> fragments;

    public TabPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public TabPageAdapter(FragmentManager fm, Context context, List<Fragment> fragments, ArrayList<String> titleList) {
        super(fm);
        this.titleList = titleList;
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}