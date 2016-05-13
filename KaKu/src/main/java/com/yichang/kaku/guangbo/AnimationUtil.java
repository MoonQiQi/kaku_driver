package com.yichang.kaku.guangbo;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.yichang.kaku.R;


public class AnimationUtil {

    /**
     * 创建一个平顺的不停旋转动画对象
     *
     * @param context
     * @return
     */
    public static Animation createSmoothForeverAnimation(Context context) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.rotate_round);
        animation.setInterpolator(new LinearInterpolator());
        return animation;
    }
}
