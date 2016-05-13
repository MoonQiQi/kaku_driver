package com.yichang.kaku.obj;

import java.io.Serializable;

public class BaoYangShopObj implements Serializable {

    private String name_shop;
    private String id_shop;
    private String price_item;
    private String num_star;
    private String num_bill;
    private String activity_shop_content;
    private String distance;
    private String addr_shop;
    private String tel_shop;
    private String hour_shop_begin;
    private String hour_shop_end;
    private String image_shop;

    public String getName_shop() {
        return name_shop;
    }

    public void setName_shop(String name_shop) {
        this.name_shop = name_shop;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getActivity_shop_content() {
        return activity_shop_content;
    }

    public void setActivity_shop_content(String activity_shop_content) {
        this.activity_shop_content = activity_shop_content;
    }

    public String getNum_star() {
        return num_star;
    }

    public void setNum_star(String num_star) {
        this.num_star = num_star;
    }

    public String getNum_bill() {
        return num_bill;
    }

    public void setNum_bill(String num_bill) {
        this.num_bill = num_bill;
    }

    public String getPrice_item() {
        return price_item;
    }

    public void setPrice_item(String price_item) {
        this.price_item = price_item;
    }

    public String getId_shop() {
        return id_shop;
    }

    public void setId_shop(String id_shop) {
        this.id_shop = id_shop;
    }

    public String getAddr_shop() {
        return addr_shop;
    }

    public void setAddr_shop(String addr_shop) {
        this.addr_shop = addr_shop;
    }

    public String getTel_shop() {
        return tel_shop;
    }

    public void setTel_shop(String tel_shop) {
        this.tel_shop = tel_shop;
    }

    public String getHour_shop_begin() {
        return hour_shop_begin;
    }

    public void setHour_shop_begin(String hour_shop_begin) {
        this.hour_shop_begin = hour_shop_begin;
    }

    public String getHour_shop_end() {
        return hour_shop_end;
    }

    public void setHour_shop_end(String hour_shop_end) {
        this.hour_shop_end = hour_shop_end;
    }

    public String getImage_shop() {
        return image_shop;
    }

    public void setImage_shop(String image_shop) {
        this.image_shop = image_shop;
    }

    @Override
    public String toString() {
        return "BaoYangShopObj{" +
                "name_shop='" + name_shop + '\'' +
                ", id_shop='" + id_shop + '\'' +
                ", price_item='" + price_item + '\'' +
                ", num_star='" + num_star + '\'' +
                ", num_bill='" + num_bill + '\'' +
                ", activity_shop_content='" + activity_shop_content + '\'' +
                ", distance='" + distance + '\'' +
                ", addr_shop='" + addr_shop + '\'' +
                ", tel_shop='" + tel_shop + '\'' +
                ", hour_shop_begin='" + hour_shop_begin + '\'' +
                ", hour_shop_end='" + hour_shop_end + '\'' +
                ", image_shop='" + image_shop + '\'' +
                '}';
    }
}
