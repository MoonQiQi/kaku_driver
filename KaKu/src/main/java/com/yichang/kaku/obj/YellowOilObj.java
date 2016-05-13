package com.yichang.kaku.obj;

import java.io.Serializable;

public class YellowOilObj implements Serializable {

    private String id_service;
    private String name_service;
    private String price_drop;
    private String flag_check;

    public String getId_service() {
        return id_service;
    }

    public void setId_service(String id_service) {
        this.id_service = id_service;
    }

    public String getFlag_check() {
        return flag_check;
    }

    public void setFlag_check(String flag_check) {
        this.flag_check = flag_check;
    }

    public String getPrice_drop() {
        return price_drop;
    }

    public void setPrice_drop(String price_drop) {
        this.price_drop = price_drop;
    }

    public String getName_service() {
        return name_service;
    }

    public void setName_service(String name_service) {
        this.name_service = name_service;
    }

    @Override
    public String toString() {
        return "YellowOilObj{" +
                "id_service='" + id_service + '\'' +
                ", name_service='" + name_service + '\'' +
                ", price_drop='" + price_drop + '\'' +
                ", flag_check='" + flag_check + '\'' +
                '}';
    }
}
