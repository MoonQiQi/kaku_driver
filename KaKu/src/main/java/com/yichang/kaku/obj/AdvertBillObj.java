package com.yichang.kaku.obj;

import java.io.Serializable;

public class AdvertBillObj implements Serializable {

    private String id_advert_bill;
    private String name_advert;
    private String image_advert;
    private String state_bill;
    private String price_bill;
    private String price_advert;
    private String breaks_money;
    private String money_balance;
    private String no_bill;
    private String type_pay;
    private String time_create;
    private String num_advert;
    private String addr;
    private String name_addr;
    private String phone_addr;
    private String company_delivery;
    private String no_delivery;
    private String flag_operate;
    private String price_advert_single;
    private String flag_recommended;

    public String getPrice_advert_single() {
        return price_advert_single;
    }

    public void setPrice_advert_single(String price_advert_single) {
        this.price_advert_single = price_advert_single;
    }

    public String getId_advert_bill() {
        return id_advert_bill;
    }

    public void setId_advert_bill(String id_advert_bill) {
        this.id_advert_bill = id_advert_bill;
    }

    public String getName_advert() {
        return name_advert;
    }

    public void setName_advert(String name_advert) {
        this.name_advert = name_advert;
    }

    public String getImage_advert() {
        return image_advert;
    }

    public void setImage_advert(String image_advert) {
        this.image_advert = image_advert;
    }

    public String getState_bill() {
        return state_bill;
    }

    public void setState_bill(String state_bill) {
        this.state_bill = state_bill;
    }

    public String getPrice_bill() {
        return price_bill;
    }

    public void setPrice_bill(String price_bill) {
        this.price_bill = price_bill;
    }

    public String getPrice_advert() {
        return price_advert;
    }

    public void setPrice_advert(String price_advert) {
        this.price_advert = price_advert;
    }

    public String getBreaks_money() {
        return breaks_money;
    }

    public void setBreaks_money(String breaks_money) {
        this.breaks_money = breaks_money;
    }

    public String getMoney_balance() {
        return money_balance;
    }

    public void setMoney_balance(String money_balance) {
        this.money_balance = money_balance;
    }

    public String getNo_bill() {
        return no_bill;
    }

    public void setNo_bill(String no_bill) {
        this.no_bill = no_bill;
    }

    public String getType_pay() {
        return type_pay;
    }

    public void setType_pay(String type_pay) {
        this.type_pay = type_pay;
    }

    public String getTime_create() {
        return time_create;
    }

    public void setTime_create(String time_create) {
        this.time_create = time_create;
    }

    public String getNum_advert() {
        return num_advert;
    }

    public void setNum_advert(String num_advert) {
        this.num_advert = num_advert;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getName_addr() {
        return name_addr;
    }

    public void setName_addr(String name_addr) {
        this.name_addr = name_addr;
    }

    public String getPhone_addr() {
        return phone_addr;
    }

    public void setPhone_addr(String phone_addr) {
        this.phone_addr = phone_addr;
    }

    public String getCompany_delivery() {
        return company_delivery;
    }

    public void setCompany_delivery(String company_delivery) {
        this.company_delivery = company_delivery;
    }

    public String getNo_delivery() {
        return no_delivery;
    }

    public void setNo_delivery(String no_delivery) {
        this.no_delivery = no_delivery;
    }

    public String getFlag_operate() {
        return flag_operate;
    }

    public void setFlag_operate(String flag_operate) {
        this.flag_operate = flag_operate;
    }

    public String getFlag_recommended() {
        return flag_recommended;
    }

    public void setFlag_recommended(String flag_recommended) {
        this.flag_recommended = flag_recommended;
    }

    @Override
    public String toString() {
        return "AdvertBillObj{" +
                "id_advert_bill='" + id_advert_bill + '\'' +
                ", name_advert='" + name_advert + '\'' +
                ", image_advert='" + image_advert + '\'' +
                ", state_bill='" + state_bill + '\'' +
                ", price_bill='" + price_bill + '\'' +
                ", price_advert='" + price_advert + '\'' +
                ", breaks_money='" + breaks_money + '\'' +
                ", money_balance='" + money_balance + '\'' +
                ", no_bill='" + no_bill + '\'' +
                ", type_pay='" + type_pay + '\'' +
                ", time_create='" + time_create + '\'' +
                ", num_advert='" + num_advert + '\'' +
                ", addr='" + addr + '\'' +
                ", name_addr='" + name_addr + '\'' +
                ", phone_addr='" + phone_addr + '\'' +
                ", company_delivery='" + company_delivery + '\'' +
                ", no_delivery='" + no_delivery + '\'' +
                ", flag_operate='" + flag_operate + '\'' +
                ", flag_recommended='" + flag_recommended + '\'' +
                ", price_advert_single='" + price_advert_single + '\'' +
                '}';
    }
}
