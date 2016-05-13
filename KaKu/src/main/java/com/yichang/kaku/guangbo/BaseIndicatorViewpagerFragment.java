package com.yichang.kaku.guangbo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yichang.kaku.R;

import java.util.ArrayList;

/******************************************
 * 类描述： indicator viewpager的基类类名称：BaseIndicatorViewpagerFragment
 *
 * @version: 1.0
 * @author: caopeng
 * @time: 2016-7-21 21:44
 ******************************************/
public abstract class BaseIndicatorViewpagerFragment extends Fragment {

    public interface IndicatorStyle {
        int INDICATOR_LEFT = 0;//靠左
        int INDICATOR_CENTER = 1;//平分样式
    }

    /**
     * 子类选择indicator样式
     *
     * @return indicator样式
     */
    protected abstract int getCustomIndicatorStyle();

    /**
     * 加载数据
     */
    protected abstract void requestData();

    /**
     * 创建viewpager的fragment
     *
     * @return fragmentList 数据源
     */
    protected abstract ArrayList<Fragment> createTabFragments();

    /**
     * 创建indicator的标题
     *
     * @return fragment title 数据源
     */
    protected abstract ArrayList<String> createTabTitles();


    /**
     * 设置theme 和indicator的背景颜色
     *
     * @return 返回主题样式资源id和 indicator的背景颜色资源id
     */
    public int[] getIndicatorThemeAndBackground() {
        return null;
    }

    /**
     * adapter 准备好了 可以调用setCurrentItem（）
     */
    public void onAdapterReady() {
    }

    /**
     * 当前选中的tab位置
     *
     * @param position 选中的页面位置下标
     */
    public void onIndicatorPageSelected(int position) {
    }

    /**
     * 加载失败view点击时（重新加载数据）
     */
    public void onLoadFailViewClicked() {
    }

    /**
     * 延迟500毫秒加载数据
     */
    private static final int TIME_DELAY_LOAD_DATA = 1000;

    LinearLayout mContentLayout;
    CategoryTabPageIndicator mCommonCategoryTabPageIndicator;
    TabPageIndicator mCommonTabPageIndicator;
    ViewPager mCommonViewPager;
    public LoadingView mLoadingView;

    private int[] themeAndbackground;
    private PageIndicator mCommonIndicator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        themeAndbackground = getIndicatorThemeAndBackground();
        if (themeAndbackground == null) {
            themeAndbackground = new int[]{R.style.Theme_PageIndicatorBigDiscover, R.color.white};
        }
        LayoutInflater localLayoutInflater = getThemeLayoutInflater(getActivity(), inflater, themeAndbackground[0]);
        View view = localLayoutInflater.inflate(R.layout.common_indicator_viewpager, container, false);
        initView(view);
        setOnLayoutLoadFailViewListener();
        return view;
    }

    public LayoutInflater getThemeLayoutInflater(Activity activity, LayoutInflater inflater, int theme) {

        //使用ContextThemeWrapper通过目标Theme生成一个新的Context
        Context ctxWithTheme = new ContextThemeWrapper(
                activity.getApplicationContext(), theme);

        //通过生成的Context创建一个LayoutInflater
        return inflater.cloneInContext(ctxWithTheme);
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    protected void onClickOpenCategory() {

    }

    protected void onClickSearch() {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        delayInitData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onDestroyEvent();
    }

    protected void onDestroyEvent() {

    }

    private void initView(View view) {
        mContentLayout = (LinearLayout) view.findViewById(R.id.content_layout);
        mCommonCategoryTabPageIndicator = (CategoryTabPageIndicator) view.findViewById(R.id.common_category_tab_page_indicator);
        mCommonTabPageIndicator = (TabPageIndicator) view.findViewById(R.id.common_tab_page_indicator);
        mCommonViewPager = (ViewPager) view.findViewById(R.id.common_view_pager);
        mLoadingView = (LoadingView) view.findViewById(R.id.indicator_loading_view);
    }

    /**
     * 加载失败view
     */
    private void setOnLayoutLoadFailViewListener() {
        mLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showLoading();
                onLoadFailViewClicked();
            }
        });
    }

    /**
     * 设置indicator的滑动监听
     */
    private void setIndicatorPageChangeListener() {
        (mCommonIndicator).setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onIndicatorPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void delayInitData() {
//        showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }, TIME_DELAY_LOAD_DATA);
    }

    public ViewPager getViewPager() {
        return mCommonViewPager;
    }

    /**
     * 设置adapter
     */
    public void initAdapter(ArrayList<Fragment> mFragmentList, ArrayList<String> horizontalItemList) {

        if (ListUtils.equalsNull(mFragmentList) || ListUtils.equalsNull(horizontalItemList)) {
            onLoadFail();
            return;
        }
        if (getActivity() == null || !isAdded() || mCommonViewPager == null) {//跑monkey出现问题
            return;
        }

        switch (getCustomIndicatorStyle()) {
            case IndicatorStyle.INDICATOR_LEFT:
                setIndicatorLeftStyle(mFragmentList, horizontalItemList);
                break;
            case IndicatorStyle.INDICATOR_CENTER:
                setIndicatorCenterStyle(mFragmentList, horizontalItemList);
                break;
        }

        // 这个setViewPager()后必须调用setOnPageChangeListener（）这个方法 否则不会有回调执行 照做就好。
        mCommonIndicator.setViewPager(mCommonViewPager);
        setIndicatorPageChangeListener();
        onAdapterReady();
//        hideLoading();
    }

    /**
     * 可横向滚动的tab样式
     *
     * @param mFragmentList
     * @param horizontalItemList
     */
    private void setIndicatorLeftStyle(ArrayList<Fragment> mFragmentList, ArrayList<String> horizontalItemList) {
        if (mCommonTabPageIndicator == null) {
            return;
        }
        PagerAdapter mAdapter;
        mCommonTabPageIndicator.setBackgroundColor(themeAndbackground[1]);
        mAdapter = new FragmentViewPagerAdapter(BaseIndicatorViewpagerFragment.this.getChildFragmentManager(),
                mCommonTabPageIndicator, mCommonViewPager, mFragmentList, horizontalItemList);
        mCommonIndicator = mCommonCategoryTabPageIndicator;
        mCommonViewPager.setAdapter(mAdapter);
        mCommonCategoryTabPageIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        notifyChildOnHiddenChanged(hidden);
        if (!hidden) {
            onHiddenChange();
        }
    }

    protected void onHiddenChange() {

    }

    private void notifyChildOnHiddenChanged(boolean hidden) {
        if (mCommonViewPager == null) {
            return;
        }
        PagerAdapter pagerAdapter = mCommonViewPager.getAdapter();
        if (!(pagerAdapter instanceof FragmentViewPagerAdapter)) {
            return;
        }
        ArrayList<Fragment> fragmentArrayList = ((FragmentViewPagerAdapter) pagerAdapter).getFragments();
        if (ListUtils.equalsNull(fragmentArrayList)) {
            return;
        }
        for (int i = 0, size = fragmentArrayList.size(); i < size; i++) {
            Fragment fragment = fragmentArrayList.get(i);
            fragment.onHiddenChanged(hidden);
        }
    }

    /**
     * tab平分样式
     *
     * @param mFragmentList      tab页面fragment数组
     * @param horizontalItemList 标题数组
     */
    private void setIndicatorCenterStyle(ArrayList<Fragment> mFragmentList, ArrayList<String> horizontalItemList) {
        PagerAdapter mAdapter;
        mCommonTabPageIndicator.setBackgroundColor(themeAndbackground[1]);
        mAdapter = new TabPageAdapter(getChildFragmentManager(), getActivity(), mFragmentList, horizontalItemList);
        mCommonIndicator = mCommonTabPageIndicator;
        mCommonViewPager.setAdapter(mAdapter);
        mCommonTabPageIndicator.setVisibility(View.VISIBLE);//这句代码很重要 否则抛出viewPager has not been bound
    }

    /**
     * 加载失败
     */
    public void onLoadFail() {
//        hideLoading();
        ViewUtil.setViewVisibility(mContentLayout, View.GONE);
    }

    /**
     * 加载成功
     */
    public void onLoadSuccess() {
//        hideLoading();
        ViewUtil.setViewVisibility(mContentLayout, View.VISIBLE);
    }

    /**
     * 设置tab item
     *
     * @param position tab数组下标
     */
    public void setCurrentItem(int position) {
        if (position >= 0 && mCommonIndicator != null) {
            mCommonIndicator.setCurrentItem(position);
        }
    }
}
