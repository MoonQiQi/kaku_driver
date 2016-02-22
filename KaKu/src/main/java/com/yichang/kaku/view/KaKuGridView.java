package com.yichang.kaku.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by Administrator on 2015/11/6.
 */
public class KaKuGridView extends GridView {

    public KaKuGridView(Context context) {
        super(context);
    }

    public KaKuGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KaKuGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public KaKuGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected boolean dispatchGenericFocusedEvent(MotionEvent event) {
        return super.dispatchGenericFocusedEvent(event);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
        //return true;
    }
}
