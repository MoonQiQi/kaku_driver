package com.yichang.kaku.home.giftmall.sliding;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.yichang.kaku.global.MyActivityManager;
import com.yichang.kaku.tools.DensityUtils;
import com.yichang.kaku.tools.LogUtil;

/**
 * Created by ysnow on 2015/3/3.
 */
public class ProductDetailSlidingMenu extends ScrollView {
    private Context mContext;
    private int mScreenHeight;
//      private int mOnePage;
//      private int mMenuPadding=220;


    private ProductDetailScrollViewPage wrapperMenu;
    private ProductDetailWebView wrapperContent;
    private boolean isSetted = false;
    private boolean ispageOne = true;
    private String webUrl;

    private Scroller mScroller;

    public void setWebUrl(String url){
        webUrl=url;
    }

    public ProductDetailSlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext=context;
        mScroller = new Scroller(context);
    }

    public ProductDetailSlidingMenu(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        mScroller = new Scroller(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        mScreenHeight = metrics.heightPixels;
        //mScreenHeight = mScreenHeight - DensityUtils.dp2px(context, 119) - DensityUtils.getStatusHeight(MyActivityManager.getInstance().getActivty());
    }

    /**
     *
     * @param dpVal 需要去除的高度
     * */
    public void setScreenHeight(int dpVal){
        LogUtil.E("menu ScreenHeight:"+mScreenHeight);
        mScreenHeight=mScreenHeight-DensityUtils.dp2px(mContext,dpVal)- DensityUtils.getStatusHeight(MyActivityManager.getInstance().getActivty());
        LogUtil.E("menu ScreenHeight after:"+mScreenHeight);
    }


    public ProductDetailSlidingMenu(Context context) {
        this(context, null);
        mContext=context;
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isSetted) {
            final LinearLayout wrapper = (LinearLayout) getChildAt(0);
            wrapperMenu = (ProductDetailScrollViewPage) wrapper.getChildAt(0);
            wrapperContent = (ProductDetailWebView) wrapper.getChildAt(1);
            WebSettings settings = wrapperContent.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);

            wrapperContent.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    wrapperContent.loadUrl(url);
                    return true;
                }
            });
            //����������View�ĸ߶�Ϊ�ֻ��ĸ߶�
            wrapperMenu.getLayoutParams().height = mScreenHeight;
            wrapperContent.getLayoutParams().height = mScreenHeight;
            isSetted = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            LogUtil.E("onLayout");
            this.scrollTo(0, 0);
        }

    }

    //调用此方法滚动到目标位置  duration滚动时间  
    public void smoothScrollToSlow(int fx, int fy) {
        int duration=1000;
        int dx = fx - getScrollX();//mScroller.getFinalX();  普通view使用这种方法  
        int dy = fy - getScrollY();  //mScroller.getFinalY();
        smoothScrollBySlow(dx, dy, duration);
    }

    //调用此方法设置滚动的相对偏移    
    public void smoothScrollBySlow(int dx, int dy,int duration) {

        //设置mScroller的滚动偏移量    
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy,duration);//scrollView使用的方法（因为可以触摸拖动）    
//        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy, duration);  //普通view使用的方法  
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果    
    }

    @Override
    public void computeScroll() {

        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {

            //这里调用View的scrollTo()完成实际的滚动    
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            //必须调用该方法，否则不一定能看到滚动效果    
            postInvalidate();
        }
        super.computeScroll();
    }

    /**
     * 滑动事件，这是控制手指滑动的惯性速度  
     */
    @Override
    public void fling(int velocityY) {
        super.fling(velocityY/4 );
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                //��������ߵľ���?
                int scrollY = getScrollY();
                int creteria = mScreenHeight / 5;//�������پ���
                if (ispageOne) {
//                    LogUtil.E("chai ispageOne");
//                    LogUtil.E("chai scrollY:"+scrollY);
                    if (scrollY <= 5) {
                        //��ʾ�˵�
//                        LogUtil.E("chai smoothScrollToSlow(0, 0)");
                        this.smoothScrollToSlow(0, 0);
                    } else {
                        //���ز˵�
//                        LogUtil.E("chai smoothScrollToSlow(0, mScreenHeight);");
                        this.smoothScrollToSlow(0, mScreenHeight);
                        wrapperContent.loadUrl(webUrl);
                        this.setFocusable(false);
                        ispageOne = false;
                    }
                } else {
//                    LogUtil.E("chai isWeb");
                    int scrollpadding = mScreenHeight - scrollY;
                    if (scrollpadding >= 5) {
                        this.smoothScrollToSlow(0, 0);
                        ispageOne = true;
                    } else {
                        this.smoothScrollToSlow(0, mScreenHeight);
                        wrapperContent.loadUrl(webUrl);
                    }
                }

                return true;
        }
        return super.onTouchEvent(ev);
    }




    public void closeMenu() {
        if (ispageOne) return;
        this.smoothScrollToSlow(0, 0);
        ispageOne = true;
    }

    public void openMenu() {
        if (!ispageOne) return;
        this.smoothScrollToSlow(0, mScreenHeight);
        ispageOne = false;
    }

    /**
     * �򿪺͹رղ˵�
     */
    public void toggleMenu() {
        if (ispageOne) {
            openMenu();
        } else {
            closeMenu();
        }
    }


}
