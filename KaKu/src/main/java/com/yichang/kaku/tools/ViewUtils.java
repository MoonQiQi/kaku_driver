package com.yichang.kaku.tools;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xiaosu on 2015/11/19.
 * view工具
 */
public class ViewUtils {

    public static void updateMargin(View view, int leftMargin, int rightMargin, int topMargin, int bottomMargin) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            marginLayoutParams.topMargin = topMargin;
            marginLayoutParams.leftMargin = leftMargin;
            marginLayoutParams.rightMargin = rightMargin;
            marginLayoutParams.bottomMargin = bottomMargin;
            view.setLayoutParams(marginLayoutParams);
        } else {
            return;
        }
    }

    public static <T extends View> T findView(View rootView, int ResId) {
        return (T) rootView.findViewById(ResId);
    }

}
