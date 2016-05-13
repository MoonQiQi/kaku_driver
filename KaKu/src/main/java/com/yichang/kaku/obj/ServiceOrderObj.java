package com.yichang.kaku.obj;

import java.io.Serializable;
import java.util.List;

public class ServiceOrderObj implements Serializable {

    public List<BigOilObj> shopcar;
    public String id_upkeep_bill;
    public String flag_type;
    public String state_bill;
    public String price_bill;
    public String no_bill;
    public String type_pay;
    public String addr;
    public String name_addr;
    public String phone_addr;
    public String time_create;
    public String price_goods;
    public Worker2Obj worker;

    public List<BigOilObj> getShopcar() {
        return shopcar;
    }

    public void setShopcar(List<BigOilObj> shopcar) {
        this.shopcar = shopcar;
    }

    public String getId_upkeep_bill() {
        return id_upkeep_bill;
    }

    public void setId_upkeep_bill(String id_upkeep_bill) {
        this.id_upkeep_bill = id_upkeep_bill;
    }

    public String getFlag_type() {
        return flag_type;
    }

    public void setFlag_type(String flag_type) {
        this.flag_type = flag_type;
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

    public String getPrice_goods() {
        return price_goods;
    }

    public void setPrice_goods(String price_goods) {
        this.price_goods = price_goods;
    }

    public Worker2Obj getWorker() {
        return worker;
    }

    public void setWorker(Worker2Obj worker) {
        this.worker = worker;
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

    @Override
    public String toString() {
        return "ServiceOrderObj{" +
                "shopcar=" + shopcar +
                ", id_upkeep_bill='" + id_upkeep_bill + '\'' +
                ", flag_type='" + flag_type + '\'' +
                ", state_bill='" + state_bill + '\'' +
                ", price_bill='" + price_bill + '\'' +
                ", no_bill='" + no_bill + '\'' +
                ", type_pay='" + type_pay + '\'' +
                ", addr='" + addr + '\'' +
                ", name_addr='" + name_addr + '\'' +
                ", phone_addr='" + phone_addr + '\'' +
                ", time_create='" + time_create + '\'' +
                ", price_goods='" + price_goods + '\'' +
                ", worker=" + worker +
                '}';
    }
}
