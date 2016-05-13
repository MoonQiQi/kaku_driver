package com.yichang.kaku.guangbo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 为ViewPager添加布局（Fragment），绑定和处理fragments和viewpager之间的逻辑关系
 * <p/>
 * Created by wangqing on 15/4/14.
 */
public class FragmentViewPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private ArrayList<Fragment> fragments; // 每个Fragment对应一个Page
    private FragmentManager fragmentManager;
    private ViewPager viewPager; // viewPager对象
    private PageIndicator tabPageIndicator; // tabPageIndicator
    private ArrayList<Object> titleList;
    private int currentPageIndex = 0; // 当前page索引（切换之前）

    private OnExtraPageChangeListener onExtraPageChangeListener; // ViewPager切换页面时的额外功能添加接口

    public FragmentViewPagerAdapter(FragmentManager fragmentManager, PageIndicator tabPageIndicator, ViewPager viewPager,
                                    ArrayList<Fragment> fragments, ArrayList titleList) {
        this.fragments = fragments;
        this.fragmentManager = fragmentManager;
        this.viewPager = viewPager;
        this.tabPageIndicator = tabPageIndicator;
        this.viewPager.setOnPageChangeListener(this);
        if (tabPageIndicator != null) {
            this.tabPageIndicator.setOnPageChangeListener(this);
        }
        this.titleList = titleList;
        this.viewPager.setAdapter(this);
    }

    /**
     * 获取当前Adapter所包含Fragment
     *
     * @return
     */
    public ArrayList<Fragment> getFragments() {
        return fragments;
    }

    public OnExtraPageChangeListener getOnExtraPageChangeListener() {
        return onExtraPageChangeListener;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (titleList != null) {
            Object obj = titleList.get(position);

            if (obj instanceof String) {
                return obj.toString();
            }
        }
        return "";
    }

    /**
     * 设置页面切换额外功能监听器
     *
     * @param onExtraPageChangeListener
     */
    public void setOnExtraPageChangeListener(OnExtraPageChangeListener onExtraPageChangeListener) {
        this.onExtraPageChangeListener = onExtraPageChangeListener;
    }

    @Override
    public int getCount() {
        return fragments != null && fragments.size() > 0 ? fragments.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = fragments.get(position);

        if (!fragment.isAdded()) {  // 如果fragment还没有added
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(fragment, String.valueOf(position)); //根据当前的Position 作为唯一值

            /**
             * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
             * 会在进程的主线程中，用异步的方式来执行。
             * 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
             * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
             */
            try {
                fragmentTransaction.commitAllowingStateLoss(); // 解决17090bug
                fragmentManager.executePendingTransactions();
            } catch (IllegalStateException ill) {
                ill.printStackTrace();
            }
        }

        if (fragment.getView() != null && fragment.getView().getParent() == null) {
            container.addView(fragment.getView());  // 为viewpager增加布局
        }

        return fragment.getView();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(fragments.get(position).getView());
    }

    /**
     * 当前page索引（切换之前）
     *
     * @return
     */
    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (null != onExtraPageChangeListener) { // 如果设置了额外功能接口
            onExtraPageChangeListener.onExtraPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(final int position) {
        int size = fragments.size();
        if (size <= position || size <= currentPageIndex) {
            return;
        }

        fragments.get(currentPageIndex).onPause(); //调用切换前Fargment的onPause()
        fragments.get(currentPageIndex).onStop();

        if (fragments.get(position).isAdded()) {
            fragments.get(position).onStart(); // 调用切换后Fargment的onStart()
            fragments.get(position).onResume(); // 调用切换后Fargment的onResume()
        }

        currentPageIndex = position;

        if (null != onExtraPageChangeListener) { // 如果设置了额外功能接口
            onExtraPageChangeListener.onExtraPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (null != onExtraPageChangeListener) { // 如果设置了额外功能接口
            onExtraPageChangeListener.onExtraPageScrollStateChanged(state);
        }
    }

    /**
     * page切换额外功能接口
     */
    public interface OnExtraPageChangeListener {
        void onExtraPageScrolled(int position, float positionOffset, int
                positionOffsetPixels);

        void onExtraPageSelected(int position);

        void onExtraPageScrollStateChanged(int state);
    }
}
