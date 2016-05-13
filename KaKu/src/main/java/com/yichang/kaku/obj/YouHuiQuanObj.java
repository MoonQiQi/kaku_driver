package com.yichang.kaku.obj;

import java.io.Serializable;

public class YouHuiQuanObj implements Serializable {
    private String id_driver_coupon;
    private String name_coupon;
    private String content_coupon;
    private String money_coupon;
    private String money_limit;
    private String time_start;
    private String time_end;
    private String flag_usable;

    public String getId_driver_coupon() {
        return id_driver_coupon;
    }

    public void setId_driver_coupon(String id_driver_coupon) {
        this.id_driver_coupon = id_driver_coupon;
    }

    public String getName_coupon() {
        return name_coupon;
    }

    public void setName_coupon(String name_coupon) {
        this.name_coupon = name_coupon;
    }

    public String getContent_coupon() {
        return content_coupon;
    }

    public void setContent_coupon(String content_coupon) {
        this.content_coupon = content_coupon;
    }

    public String getMoney_coupon() {
        return money_coupon;
    }

    public void setMoney_coupon(String money_coupon) {
        this.money_coupon = money_coupon;
    }

    public String getMoney_limit() {
        return money_limit;
    }

    public void setMoney_limit(String money_limit) {
        this.money_limit = money_limit;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getFlag_usable() {
        return flag_usable;
    }

    public void setFlag_usable(String flag_usable) {
        this.flag_usable = flag_usable;
    }

    @Override
    public String toString() {
        return "YouHuiQuanObj{" +
                "id_driver_coupon='" + id_driver_coupon + '\'' +
                ", name_coupon='" + name_coupon + '\'' +
                ", content_coupon='" + content_coupon + '\'' +
                ", money_coupon='" + money_coupon + '\'' +
                ", money_limit='" + money_limit + '\'' +
                ", time_start='" + time_start + '\'' +
                ", time_end='" + time_end + '\'' +
                ", flag_usable='" + flag_usable + '\'' +
                '}';
    }
}
