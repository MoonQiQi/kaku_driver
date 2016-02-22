package com.yichang.kaku.home.giftmall.sliding;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by ysnow on 2015/4/20.
 */
public class ProductDetailScrollView extends ScrollView {
    public float oldY;
    private int t;

    public ProductDetailScrollView(Context context) {
        super(context);
    }

    public ProductDetailScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductDetailScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float Y = ev.getY();
                float Ys = Y - oldY;
                if (Ys >0 &&t==0) {
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
this.t=t;
        int duoshao=t-oldt;
//        Log.e("TAG", duoshao + "DUOSHAO");
//        Log.e("TAG", t + "T");
//        Log.e("TAG", oldt + "OLDT");
        if (t-oldt==0) {
        }


        super.onScrollChanged(l, t, oldl, oldt);
    }


}
