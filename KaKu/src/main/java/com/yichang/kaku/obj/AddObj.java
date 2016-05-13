package com.yichang.kaku.obj;

import java.io.Serializable;

public class AddObj implements Serializable {

    private String name_advert;
    private String day_earnings;
    private String day_growth;
    private String day_remaining;
    private String now_earnings;
    private String now_earnings_0;
    private String now_earnings_1;
    private String total_earnings;
    private String earnings_total;
    private String flag_type;
    private String flag_recommended;
    private String num_privilege;
    private String flag_show;
    private String flag_position;
    private String flag_advert;
    private String id_advert;
    private String flag_agent;
    private String code_recommended;

    public String getName_advert() {
        return name_advert;
    }

    public void setName_advert(String name_advert) {
        this.name_advert = name_advert;
    }

    public String getDay_earnings() {
        return day_earnings;
    }

    public void setDay_earnings(String day_earnings) {
        this.day_earnings = day_earnings;
    }

    public String getDay_growth() {
        return day_growth;
    }

    public void setDay_growth(String day_growth) {
        this.day_growth = day_growth;
    }

    public String getDay_remaining() {
        return day_remaining;
    }

    public void setDay_remaining(String day_remaining) {
        this.day_remaining = day_remaining;
    }

    public String getNow_earnings() {
        return now_earnings;
    }

    public void setNow_earnings(String now_earnings) {
        this.now_earnings = now_earnings;
    }

    public String getTotal_earnings() {
        return total_earnings;
    }

    public void setTotal_earnings(String total_earnings) {
        this.total_earnings = total_earnings;
    }

    public String getFlag_type() {
        return flag_type;
    }

    public void setFlag_type(String flag_type) {
        this.flag_type = flag_type;
    }

    public String getFlag_recommended() {
        return flag_recommended;
    }

    public void setFlag_recommended(String flag_recommended) {
        this.flag_recommended = flag_recommended;
    }

    public String getNum_privilege() {
        return num_privilege;
    }

    public void setNum_privilege(String num_privilege) {
        this.num_privilege = num_privilege;
    }

    public String getFlag_show() {
        return flag_show;
    }

    public void setFlag_show(String flag_show) {
        this.flag_show = flag_show;
    }

    public String getFlag_position() {
        return flag_position;
    }

    public void setFlag_position(String flag_position) {
        this.flag_position = flag_position;
    }

    public String getId_advert() {
        return id_advert;
    }

    public void setId_advert(String id_advert) {
        this.id_advert = id_advert;
    }

    public String getCode_recommended() {
        return code_recommended;
    }

    public void setCode_recommended(String code_recommended) {
        this.code_recommended = code_recommended;
    }

    public String getEarnings_total() {
        return earnings_total;
    }

    public void setEarnings_total(String earnings_total) {
        this.earnings_total = earnings_total;
    }

    public String getFlag_advert() {
        return flag_advert;
    }

    public void setFlag_advert(String flag_advert) {
        this.flag_advert = flag_advert;
    }

    public String getFlag_agent() {
        return flag_agent;
    }

    public void setFlag_agent(String flag_agent) {
        this.flag_agent = flag_agent;
    }

    public String getNow_earnings_0() {
        return now_earnings_0;
    }

    public void setNow_earnings_0(String now_earnings_0) {
        this.now_earnings_0 = now_earnings_0;
    }

    public String getNow_earnings_1() {
        return now_earnings_1;
    }

    public void setNow_earnings_1(String now_earnings_1) {
        this.now_earnings_1 = now_earnings_1;
    }

    @Override
    public String toString() {
        return "AddObj{" +
                "name_advert='" + name_advert + '\'' +
                ", day_earnings='" + day_earnings + '\'' +
                ", day_growth='" + day_growth + '\'' +
                ", day_remaining='" + day_remaining + '\'' +
                ", now_earnings='" + now_earnings + '\'' +
                ", now_earnings_0='" + now_earnings_0 + '\'' +
                ", now_earnings_1='" + now_earnings_1 + '\'' +
                ", total_earnings='" + total_earnings + '\'' +
                ", earnings_total='" + earnings_total + '\'' +
                ", flag_type='" + flag_type + '\'' +
                ", flag_recommended='" + flag_recommended + '\'' +
                ", num_privilege='" + num_privilege + '\'' +
                ", flag_show='" + flag_show + '\'' +
                ", flag_position='" + flag_position + '\'' +
                ", flag_advert='" + flag_advert + '\'' +
                ", id_advert='" + id_advert + '\'' +
                ", flag_agent='" + flag_agent + '\'' +
                ", code_recommended='" + code_recommended + '\'' +
                '}';
    }
}
