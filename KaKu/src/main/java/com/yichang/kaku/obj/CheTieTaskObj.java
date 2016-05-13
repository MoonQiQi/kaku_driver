package com.yichang.kaku.obj;

import java.io.Serializable;

public class CheTieTaskObj implements Serializable {


    private String id_advert;
    private String time_end_join;
    private String time_begin;
    private String time_end;
    private String name_advert;
    private String earnings_total;
    private String order_ceiling;
    private String num_driver;
    private String image_advert;
    private String flag_type;
    private String flag_join;
    private String flag_show;
    private String earnings_average;
    private String flag_active;
    private String flag_sign;
    private String month_continue;

    public String getId_advert() {
        return id_advert;
    }

    public void setId_advert(String id_advert) {
        this.id_advert = id_advert;
    }

    public String getName_advert() {
        return name_advert;
    }

    public void setName_advert(String name_advert) {
        this.name_advert = name_advert;
    }

    public String getTime_begin() {
        return time_begin;
    }

    public void setTime_begin(String time_begin) {
        this.time_begin = time_begin;
    }

    public String getEarnings_total() {
        return earnings_total;
    }

    public void setEarnings_total(String earnings_total) {
        this.earnings_total = earnings_total;
    }

    public String getOrder_ceiling() {
        return order_ceiling;
    }

    public void setOrder_ceiling(String order_ceiling) {
        this.order_ceiling = order_ceiling;
    }

    public String getNum_driver() {
        return num_driver;
    }

    public void setNum_driver(String num_driver) {
        this.num_driver = num_driver;
    }

    public String getImage_advert() {
        return image_advert;
    }

    public void setImage_advert(String image_advert) {
        this.image_advert = image_advert;
    }

    public String getFlag_type() {
        return flag_type;
    }

    public void setFlag_type(String flag_type) {
        this.flag_type = flag_type;
    }

    public String getFlag_join() {
        return flag_join;
    }

    public void setFlag_join(String flag_join) {
        this.flag_join = flag_join;
    }

    public String getEarnings_average() {
        return earnings_average;
    }

    public void setEarnings_average(String earnings_average) {
        this.earnings_average = earnings_average;
    }

    public String getTime_end_join() {
        return time_end_join;
    }

    public void setTime_end_join(String time_end_join) {
        this.time_end_join = time_end_join;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getFlag_active() {
        return flag_active;
    }

    public void setFlag_active(String flag_active) {
        this.flag_active = flag_active;
    }

    public String getFlag_show() {
        return flag_show;
    }

    public void setFlag_show(String flag_show) {
        this.flag_show = flag_show;
    }

    public String getMonth_continue() {
        return month_continue;
    }

    public void setMonth_continue(String month_continue) {
        this.month_continue = month_continue;
    }

    public String getFlag_sign() {
        return flag_sign;
    }

    public void setFlag_sign(String flag_sign) {
        this.flag_sign = flag_sign;
    }

    @Override
    public String toString() {
        return "CheTieTaskObj{" +
                "id_advert='" + id_advert + '\'' +
                ", time_end_join='" + time_end_join + '\'' +
                ", time_begin='" + time_begin + '\'' +
                ", time_end='" + time_end + '\'' +
                ", name_advert='" + name_advert + '\'' +
                ", earnings_total='" + earnings_total + '\'' +
                ", order_ceiling='" + order_ceiling + '\'' +
                ", num_driver='" + num_driver + '\'' +
                ", image_advert='" + image_advert + '\'' +
                ", flag_type='" + flag_type + '\'' +
                ", flag_join='" + flag_join + '\'' +
                ", flag_show='" + flag_show + '\'' +
                ", earnings_average='" + earnings_average + '\'' +
                ", flag_active='" + flag_active + '\'' +
                ", flag_sign='" + flag_sign + '\'' +
                ", month_continue='" + month_continue + '\'' +
                '}';
    }
}
