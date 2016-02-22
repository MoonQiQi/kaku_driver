package com.yichang.kaku.tools;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;

/**
 * Created by xiaosu on 2015/11/17.
 */
public class LoadMoreController implements ViewTreeObserver.OnGlobalLayoutListener, AbsListView.OnScrollListener {

    public final static int STATE_LOADING = 100000;
    public final static int STATE_SUC = 100001;
    public final static int STATE_FAIL = 100002;
    public final static int STATE_NOMORE = 100003;
    public final static int STATE_NULL = 100004;

    private ListView listView;

    View loadView;

    private ImageView iv_state;
    private TextView tv_state;
    private OnLoadingListner listner;

    private int mState;
    private boolean show = true;
    private int leftMargin;
    private int mHeight = -1;
    private int mScrollState;

    public LoadMoreController(@LayoutRes int layoutId, Context context, OnLoadingListner listner, ListView listView) {
        if (listView == null) {
            throw new RuntimeException("listView不能为空");
        }
        this.loadView = LayoutInflater.from(context).inflate(layoutId, null);
        loadView.measure(0, 0);
        mHeight = loadView.getMeasuredHeight();
        loadView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        loadViewCreate(loadView);

        this.listView = listView;

        listView.setOnScrollListener(this);
        listView.addFooterView(loadView);
        ViewTreeObserver viewTreeObserver = listView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(this);
    }

    public LoadMoreController(Context context, OnLoadingListner listner, ListView listView) {
        this(R.layout.layout_load_more_item, context, listner, listView);
    }

    public LoadMoreController(Context context, ListView listView) {
        this(context, null, listView);
    }

    public LoadMoreController(Context context) {
        this(context, null);
    }

    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        if (listView != null) {
            this.listView = listView;
            ViewTreeObserver viewTreeObserver = listView.getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(this);
        }
    }

    /**
     * loadView刚刚创建。一般做一些初始化的操作
     *
     * @param loadView
     */
    protected void loadViewCreate(View loadView) {
        iv_state = (ImageView) loadView.findViewById(R.id.iv_state);
        tv_state = (TextView) loadView.findViewById(R.id.tv_state);

        leftMargin = loadView.getContext().getResources().getDimensionPixelOffset(R.dimen.x30);
    }

    /**
     * 修改加载view的表现形式
     *
     * @param state
     */
    public void updateState(@State int state) {
        if (setState(state)) return;

        switch (state) {
            case STATE_LOADING:
                showLoading();
                break;
            case STATE_SUC:
                showSuccess();
                break;
            case STATE_FAIL:
                showFail();
                break;
            case STATE_NOMORE:
                showNoMore();
                break;
            case STATE_NULL:
                showNull();
                break;
        }
    }

    /**
     * 隐藏（一般是在listview的高度没有填充屏幕的时候调用）
     */
    protected void showNull() {
        iv_state.setVisibility(View.INVISIBLE);
        tv_state.setVisibility(View.INVISIBLE);
        tv_state.setOnClickListener(null);
    }

    protected boolean setState(@State int state) {
        if (mState != state) {
            mState = state;
        } else {
            return true;
        }
        return false;
    }

    protected void showNoMore() {
        tv_state.setText("没有更多数据了");
        iv_state.setVisibility(View.GONE);
        ViewUtils.updateMargin(tv_state, 0, 0, 0, 0);
        iv_state.setVisibility(View.GONE);
        tv_state.setOnClickListener(null);
    }

    protected void showFail() {
        iv_state.setVisibility(View.GONE);
        ViewUtils.updateMargin(tv_state, 0, 0, 0, 0);
        tv_state.setText("加载失败，请点击重试");
        tv_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateState(STATE_LOADING);
            }
        });
        tv_state.setVisibility(View.VISIBLE);
    }

    protected void showSuccess() {
        iv_state.setVisibility(View.GONE);
        ViewUtils.updateMargin(tv_state, 0, 0, 0, 0);
        tv_state.setText("加载完成");
        tv_state.setVisibility(View.VISIBLE);
        tv_state.setOnClickListener(null);
    }

    protected void showLoading() {
        iv_state.setVisibility(View.VISIBLE);
        tv_state.setVisibility(View.VISIBLE);
        tv_state.setOnClickListener(null);
        ViewUtils.updateMargin(tv_state, leftMargin, 0, 0, 0);
        tv_state.setText("正在加载更多");
        if (listner != null) {
            listner.onLoading(loadView, this);
        }
    }

    /**
     * 获取加载的view
     *
     * @return
     */
    public View getLoadView() {
        return loadView;
    }

    @Override
    public void onGlobalLayout() {
        if (shouldHide()) {
            show = false;
            loadView.setPadding(0, -mHeight, 0, 0);
            Log.d("xiaosu", "关闭加载功能");
        } else if (shouldShow()) {
            show = true;
            loadView.setPadding(0, 0, 0, 0);
            listView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            Log.d("xiaosu", "开启加载功能");
        }
    }

    private boolean shouldHide() {
        View lastChild = listView.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition());
        Log.d("xiaosu", "shouldHide");
        if (lastChild == null) {
            Log.d("xiaosu", "asChild为空");
        }
        return (listView.getCount() == 0 || (null != lastChild && lastChild.getBottom() <= listView.getMeasuredHeight())) && show;
    }

    private boolean shouldShow() {
        View lastChild = listView.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition());
        Log.d("xiaosu", "shouldHide");
        if (lastChild == null) {
            Log.d("xiaosu", "lasChild为空");
        }
        return (null != lastChild && lastChild.getBottom() > listView.getMeasuredHeight()) && !show;
    }

    public void notifyDataSetChanged() {
        if (show) {//刷新数据的时候刷新loadView的状态
            if (listView != null) {
                ViewTreeObserver viewTreeObserver = listView.getViewTreeObserver();
                viewTreeObserver.addOnGlobalLayoutListener(this);
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        mScrollState = scrollState;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        /*这一步是必须的，不然会造成抖动*/
        if (mScrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            return;
        }

        if (show && listView.getCount() > 0 && (firstVisibleItem + visibleItemCount == totalItemCount)) {
            Log.d("xiaosu", "加载中。。。。。");
            updateState(STATE_LOADING);
        }
    }

    public static interface OnLoadingListner {
        void onLoading(View loadingview, LoadMoreController controller);
    }

    public void setOnLoadingListner(OnLoadingListner listner) {
        this.listner = listner;
    }

    @IntDef({STATE_LOADING, STATE_SUC, STATE_FAIL, STATE_NOMORE, STATE_NULL})
    public @interface State {
    }

}
