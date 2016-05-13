package com.yichang.kaku.obj;

import java.io.Serializable;

public class IllegalInfo implements Serializable {

    private String date;// 时间
    private String area;// 地点
    private String act;// 原因
    private String fen;// 分数
    private String money;// 金钱

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getFen() {
        return fen;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "IllegalInfo{" +
                "date='" + date + '\'' +
                ", area='" + area + '\'' +
                ", act='" + act + '\'' +
                ", fen='" + fen + '\'' +
                ", money='" + money + '\'' +
                '}';
    }
}
