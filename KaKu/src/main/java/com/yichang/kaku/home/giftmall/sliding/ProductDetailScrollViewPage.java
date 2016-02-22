package com.yichang.kaku.home.giftmall.sliding;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ScrollView;

import com.yichang.kaku.global.MyActivityManager;
import com.yichang.kaku.tools.DensityUtils;
import com.yichang.kaku.tools.LogUtil;

/**
 * Created by chaih on 2015/12/7.
 */
public class ProductDetailScrollViewPage extends ScrollView {
    private Context mContext;
    private int mScreenHeight;
    public float oldY;
    public int t;

    public ProductDetailScrollViewPage(Context context) {
        this(context, null);
        mContext=context;
    }

    public ProductDetailScrollViewPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext=context;
    }

    public ProductDetailScrollViewPage(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        mScreenHeight = metrics.heightPixels;
        //mScreenHeight = mScreenHeight - DensityUtils.dp2px(context, 119) - DensityUtils.getStatusHeight(MyActivityManager.getInstance().getActivty());

    }
    /**
     *
     * @param dpVal ��Ҫȥ���ĸ߶�
     * */
    public void setScreenHeight(int dpVal){
        mScreenHeight=mScreenHeight-DensityUtils.dp2px(mContext,dpVal)- DensityUtils.getStatusHeight(MyActivityManager.getInstance().getActivty());
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float Y = ev.getY();
                float Ys = Y - oldY;
//                LogUtil.E("chai Y:"+Y+"--oldY:"+oldY+"--Ys:"+Ys+"--T:"+t);

                int childHeight = this.getChildAt(0).getMeasuredHeight();

                int padding = childHeight - t;
//                LogUtil.E("chai childHeight:"+childHeight);
//                LogUtil.E("chai padding:"+padding);
//                LogUtil.E("chai mScreenHeight:"+mScreenHeight);
                if (Ys < 0 && padding == mScreenHeight) {
//                    LogUtil.E("chai TouchEvent(false)");
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_DOWN:
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                oldY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        this.t = t;
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
