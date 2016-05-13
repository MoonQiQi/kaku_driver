package com.yichang.kaku.obj;

import java.io.Serializable;

public class MoneyObj implements Serializable {

    private String money_income_total;
    private String point_his;
    private String ranking;

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getMoney_income_total() {
        return money_income_total;
    }

    public void setMoney_income_total(String money_income_total) {
        this.money_income_total = money_income_total;
    }

    public String getPoint_his() {
        return point_his;
    }

    public void setPoint_his(String point_his) {
        this.point_his = point_his;
    }

    @Override
    public String toString() {
        return "MoneyObj{" +
                "money_income_total='" + money_income_total + '\'' +
                ", point_his='" + point_his + '\'' +
                ", ranking='" + ranking + '\'' +
                '}';
    }
}
