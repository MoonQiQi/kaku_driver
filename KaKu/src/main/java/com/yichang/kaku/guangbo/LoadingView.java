package com.yichang.kaku.guangbo;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaolafm.sdk.vehicle.ErrorCode;
import com.yichang.kaku.R;

/**
 * @author cp
 * @des Loading view
 */
public class LoadingView extends RelativeLayout {

    private int emptyView, errorView, loadingView, contentView;
    private OnClickListener onRetryClickListener;
    private OnClickListener onEmptyClickListener;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingView, 0, 0);
        try {
            emptyView = a.getResourceId(R.styleable.LoadingView_emptyView, R.layout.aloading_empty_view);
            errorView = a.getResourceId(R.styleable.LoadingView_errorView, R.layout.aloading_error_view);
            loadingView = a.getResourceId(R.styleable.LoadingView_loadingView, R.layout.aloading_view);
            contentView = a.getResourceId(R.styleable.LoadingView_contentsView, 0);
            LayoutInflater inflater = LayoutInflater.from(getContext());
            inflater.inflate(emptyView, this, true);  // 0
            inflater.inflate(errorView, this, true); // 1
            inflater.inflate(loadingView, this, true); //2
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        for (int i = 0; i < getChildCount() - 1; i++) {
            ViewUtil.setViewVisibility(getChildAt(i), View.GONE);
        }
        findViewById(R.id.btn_empty_info).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onEmptyClickListener) {
                    onEmptyClickListener.onClick(view);
                }
            }
        });
        findViewById(R.id.layout_load_fail).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onRetryClickListener) {
                    onRetryClickListener.onClick(view);
                }
            }
        });
    }

    public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
        this.onRetryClickListener = onRetryClickListener;
    }

    public void setOnEmptyClickListener(OnClickListener onEmptyClickListener) {
        this.onEmptyClickListener = onEmptyClickListener;
    }

    /**
     * 显示指定内容的空页面
     *
     * @param info
     * @param resId
     */
    public void showEmpty(String info, int resId) {
        for (int i = 0, count = getChildCount(); i < count; i++) {
            View child = this.getChildAt(i);
            if (child == null) {
                continue;
            }
            if (i == 0) {
                setEmptyInfoCustom(info, resId, "", child);
                ViewUtil.setViewVisibility(child, VISIBLE);
            } else {
                ViewUtil.setViewVisibility(child, GONE);
            }
        }
    }

    /**
     * 显示指定内容的空页面
     *
     * @param info
     * @param resId
     */
    public void showEmpty(String info, int resId, String hintInfo) {
        for (int i = 0, count = getChildCount(); i < count; i++) {
            View child = this.getChildAt(i);
            if (child == null) {
                continue;
            }
            if (i == 0) {
                setEmptyInfoCustom(info, resId, hintInfo, child);
                ViewUtil.setViewVisibility(child, VISIBLE);
            } else {
                ViewUtil.setViewVisibility(child, GONE);
            }
        }
    }

    /**
     * 显示错误页
     */
    public void showError(int error) {
        String info;
        int resId;
        Resources resources = getContext().getResources();
        if (error == ErrorCode.NO_NET_WORK_ERROR) {
            info = resources.getString(R.string.timeout_try_again);
            resId = R.drawable.no_net_error_icon;
        } else {
            info = resources.getString(R.string.error_unknown);
            resId = R.drawable.ic_unkown_error_icon;
        }
        showError(info, resId, false);
    }

    /**
     * 显示错误页，指定内容
     *
     * @param info
     * @param resId
     */
    public void showError(String info, int resId, String hintInfo) {
        for (int i = 0, count = getChildCount(); i < count; i++) {
            View child = this.getChildAt(i);
            if (child == null) {
                continue;
            }
            if (i == 1) {
                setErrorInfoCustom(info, resId, hintInfo, child);
                ViewUtil.setViewVisibility(child, VISIBLE);
            } else {
                ViewUtil.setViewVisibility(child, GONE);
            }
        }
    }

    /**
     * 显示错误页，指定内容、是否显示重试按钮
     *
     * @param info
     * @param resId
     * @param showRetryButton
     */
    public void showError(String info, int resId, boolean showRetryButton) {
        showError(info, resId, null);
        View retry = findViewById(R.id.btn_retry);
        ViewUtil.setViewVisibility(retry, GONE);
    }

    private void setErrorInfoCustom(String info, int resId, String hintInfo, View child) {
        if (!TextUtils.isEmpty(info)) {
            TextView tv = (TextView) child.findViewById(R.id.tv_error_info);
            if (tv != null) {
                tv.setText(info);
            }
            ViewUtil.setViewVisibility(tv, VISIBLE);
        }
        if (resId > 0) {
            ImageView iv = (ImageView) child.findViewById(R.id.iv_error);
            if (iv != null) {
                iv.setImageResource(resId);
            }
            ViewUtil.setViewVisibility(iv, VISIBLE);
        }
        if (!TextUtils.isEmpty(hintInfo)) {
            TextView btn = (TextView) child.findViewById(R.id.btn_retry);
            if (btn != null) {
                btn.setText(hintInfo);
            }
            ViewUtil.setViewVisibility(btn, VISIBLE);
        }
    }

    private void setEmptyInfoCustom(String info, int resId, String hintInfo, View child) {
        if (!TextUtils.isEmpty(info)) {
            TextView tv = (TextView) child.findViewById(R.id.tv_empty_info);
            if (tv != null) {
                tv.setText(info);
            }
            ViewUtil.setViewVisibility(tv, VISIBLE);
        }
        if (resId > 0) {
            ImageView iv = (ImageView) child.findViewById(R.id.ic_empty_img);
            if (iv != null) {
                iv.setImageResource(resId);
            }
            ViewUtil.setViewVisibility(iv, VISIBLE);
        }
        if (!TextUtils.isEmpty(hintInfo)) {
            Button btn = (Button) child.findViewById(R.id.btn_empty_info);
            if (btn != null) {
                btn.setText(hintInfo);
            }
            ViewUtil.setViewVisibility(btn, VISIBLE);
        }
    }

    public void setContentView(View view) {
        view.setId(R.id.aContentView);
        LayoutParams lp = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(view, lp);
    }

    public void setContentView(View view, LayoutParams layoutParams) {
        view.setId(R.id.aContentView);
        addView(view, layoutParams);
    }

    /**
     * 显示Loading
     */
    public void showLoading() {
        for (int i = 0, count = getChildCount(); i < count; i++) {
            View child = this.getChildAt(i);
            if (child == null) {
                continue;
            }
            if (i == 2) {
                setLoading(child, true);
                ViewUtil.setViewVisibility(child, VISIBLE);
            } else {
                ViewUtil.setViewVisibility(child, GONE);
            }
        }
    }

    /**
     * 取消loading
     */
    public void hideLoading() {
        for (int i = 0, count = getChildCount(); i < count; i++) {
            View child = this.getChildAt(i);
            if (child == null) {
                continue;
            }
            if (i == 2) {
                setLoading(child, false);
                ViewUtil.setViewVisibility(child, VISIBLE);
            } else {
                ViewUtil.setViewVisibility(child, GONE);
            }
        }
    }

    private void setLoading(View child, boolean flag) {
        RelativeLayout loadingView = (RelativeLayout) child.findViewById(R.id.layout_login_loading);
        ImageView iv = (ImageView) child.findViewById(R.id.img_login_loading);
        if (iv != null) {
            iv.clearAnimation();
            if (flag) {
                Animation animation = AnimationUtil.createSmoothForeverAnimation(getContext());
                iv.startAnimation(animation);
                ViewUtil.setViewVisibility(loadingView, VISIBLE);
            } else {
                ViewUtil.setViewVisibility(loadingView, GONE);
            }
        }
    }

    /**
     * 显示 contentView
     */
    public void showContent() {
        for (int i = 0, count = getChildCount(); i < count; i++) {
            View child = this.getChildAt(i);
            if (child == null) {
                continue;
            }
            if (i == 3 || child.getId() == contentView) {
                ViewUtil.setViewVisibility(child, VISIBLE);
            } else {
                ViewUtil.setViewVisibility(child, GONE);
            }
        }
    }
}
