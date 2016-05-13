package com.yichang.kaku.guangbo;

import android.view.View;

/******************************************
 * 类描述： 处理View相关工具类 类名称：ViewUtil
 *
 * @version: 1.0
 * @author: shaoningYang
 * @time: 2015-12-5 14:43
 ******************************************/
public final class ViewUtil {
    private ViewUtil() {
    }

    /**
     * 设置当前是否需要隐藏
     *
     * @param view
     * @param visibility
     */
    public static void setViewVisibility(View view, int visibility) {
        if (view != null && view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
    }

    public static boolean isVisible(View view) {
        if (view != null && view.getVisibility() == View.VISIBLE) {
            return true;
        } else {
            return false;
        }
    }
}
