package com.yichang.kaku.view.uploadimages.zoom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ViewPagerFixed extends android.support.v4.view.ViewPager {

    public ViewPagerFixed(Context context) {
        super(context);
    }

    public ViewPagerFixed(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            /*long[] mHits = new long[2];//这个数组的长度就是你要点击的次数。长度是几，就是几次点击事件。

            switch (ev.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mHits[0]=System.currentTimeMillis();
                    break;
                default:
                    break;
            }
*/

            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
