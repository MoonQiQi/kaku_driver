package com.yichang.kaku.obj;

import java.io.Serializable;

public class MoneyRankObj implements Serializable {

    private String money_income_total;
    private String phone_driver;
    private String carno_advert;
    private String ranking;

    public String getMoney_income_total() {
        return money_income_total;
    }

    public void setMoney_income_total(String money_income_total) {
        this.money_income_total = money_income_total;
    }

    public String getPhone_driver() {
        return phone_driver;
    }

    public void setPhone_driver(String phone_driver) {
        this.phone_driver = phone_driver;
    }

    public String getCarno_advert() {
        return carno_advert;
    }

    public void setCarno_advert(String carno_advert) {
        this.carno_advert = carno_advert;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    @Override
    public String toString() {
        return "MoneyRankObj{" +
                "money_income_total='" + money_income_total + '\'' +
                ", phone_driver='" + phone_driver + '\'' +
                ", carno_advert='" + carno_advert + '\'' +
                ", ranking='" + ranking + '\'' +
                '}';
    }
}
