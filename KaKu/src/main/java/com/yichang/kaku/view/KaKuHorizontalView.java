package com.yichang.kaku.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.HorizontalScrollView;

import com.yichang.kaku.global.KaKuApplication;

/**
 * Created by chaih on 2015/11/6.
 */
public class KaKuHorizontalView extends HorizontalScrollView {

    public KaKuHorizontalView(Context context) {
        super(context);
    }

    public KaKuHorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KaKuHorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public KaKuHorizontalView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("tet", KaKuApplication.scroll_flag + "  ");
        if (KaKuApplication.scroll_flag == 0 || KaKuApplication.scroll_flag == 2) {
            return false;
        }else{
            return super.onInterceptTouchEvent(ev);
        }
    }
}
