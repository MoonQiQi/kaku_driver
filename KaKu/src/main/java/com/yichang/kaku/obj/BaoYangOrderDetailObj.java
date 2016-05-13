package com.yichang.kaku.obj;

import java.io.Serializable;

public class BaoYangOrderDetailObj implements Serializable {

    private String time_order;
    private String id_upkeep_bill;
    private String price_balance;
    private String price_goods;
    private String price_bill;
    private String no_bill;
    private String type_pay;
    private String time_create;
    private String price_coupon;
    private String state_bill;
    private String price_activity;
    private String service_addr;
    private String name_addr;
    private String phone_addr;
    private String addr;
    private String code_used;
    private Worker2Obj worker;

    public String getTime_order() {
        return time_order;
    }

    public void setTime_order(String time_order) {
        this.time_order = time_order;
    }

    public String getId_upkeep_bill() {
        return id_upkeep_bill;
    }

    public void setId_upkeep_bill(String id_upkeep_bill) {
        this.id_upkeep_bill = id_upkeep_bill;
    }

    public String getPrice_balance() {
        return price_balance;
    }

    public void setPrice_balance(String price_balance) {
        this.price_balance = price_balance;
    }

    public String getPrice_goods() {
        return price_goods;
    }

    public void setPrice_goods(String price_goods) {
        this.price_goods = price_goods;
    }

    public String getPrice_bill() {
        return price_bill;
    }

    public void setPrice_bill(String price_bill) {
        this.price_bill = price_bill;
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

    public String getPrice_coupon() {
        return price_coupon;
    }

    public void setPrice_coupon(String price_coupon) {
        this.price_coupon = price_coupon;
    }

    public String getState_bill() {
        return state_bill;
    }

    public void setState_bill(String state_bill) {
        this.state_bill = state_bill;
    }

    public String getPrice_activity() {
        return price_activity;
    }

    public void setPrice_activity(String price_activity) {
        this.price_activity = price_activity;
    }

    public String getService_addr() {
        return service_addr;
    }

    public void setService_addr(String service_addr) {
        this.service_addr = service_addr;
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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Worker2Obj getWorker() {
        return worker;
    }

    public void setWorker(Worker2Obj worker) {
        this.worker = worker;
    }

    public String getCode_used() {
        return code_used;
    }

    public void setCode_used(String code_used) {
        this.code_used = code_used;
    }

    @Override
    public String toString() {
        return "BaoYangOrderDetailObj{" +
                "time_order='" + time_order + '\'' +
                ", id_upkeep_bill='" + id_upkeep_bill + '\'' +
                ", price_balance='" + price_balance + '\'' +
                ", price_goods='" + price_goods + '\'' +
                ", price_bill='" + price_bill + '\'' +
                ", no_bill='" + no_bill + '\'' +
                ", type_pay='" + type_pay + '\'' +
                ", time_create='" + time_create + '\'' +
                ", price_coupon='" + price_coupon + '\'' +
                ", state_bill='" + state_bill + '\'' +
                ", price_activity='" + price_activity + '\'' +
                ", service_addr='" + service_addr + '\'' +
                ", name_addr='" + name_addr + '\'' +
                ", phone_addr='" + phone_addr + '\'' +
                ", addr='" + addr + '\'' +
                ", code_used='" + code_used + '\'' +
                ", worker=" + worker +
                '}';
    }
}
