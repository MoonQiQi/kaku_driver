package com.yichang.kaku.obj;

import java.io.Serializable;

public class CalendarObj implements Serializable {
    /*sign：Y黄金币F灰金币C沙漏N不处于活动中显示空白I处于活动中显示背景色P照相机
sign0 ： A正常B开始E结束
    */
    private String day;
    private String sign;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;

    public String getSign0() {
        return sign0;
    }

    public void setSign0(String sign0) {
        this.sign0 = sign0;
    }

    private String sign0;

    @Override
    public String toString() {
        return "CalendarObj{" +
                "day='" + day + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
