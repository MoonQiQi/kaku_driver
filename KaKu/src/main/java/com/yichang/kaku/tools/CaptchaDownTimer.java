package com.yichang.kaku.tools;

import com.yichang.kaku.global.KaKuApplication;

import android.os.CountDownTimer;

import android.widget.Button;

/* 定义一个倒计时的内部类 */
public class CaptchaDownTimer extends CountDownTimer {
    public Button button;

    /**
     * @param millisInFuture    总时长
     * @param countDownInterval 事件间隔时长
     */
    public CaptchaDownTimer(long millisInFuture, long countDownInterval, Button btn) {
        super(millisInFuture, countDownInterval);
        button = btn;
    }

    public void setView(Button btn) {
        this.button = btn;
    }

    @Override
    public void onFinish() {// 计时完毕时触发
        if (button == null)
            return;
        button.setText("获取验证码");
        button.setEnabled(true);
        KaKuApplication.timer = null;
    }

    @Override
    public void onTick(long millisUntilFinished) {// 计时过程显示
        if (button == null)
            return;
        button.setEnabled(false);
        int second = (int) (millisUntilFinished / 1000);
        button.setText(second+"秒后可重新获取");
    }
    
}
