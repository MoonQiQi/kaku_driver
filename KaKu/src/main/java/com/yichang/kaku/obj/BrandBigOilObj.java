package com.yichang.kaku.obj;

import java.io.Serializable;

public class BrandBigOilObj implements Serializable {

    private String name_brand;
    private String id_brand;
    private String flag;

    public String getName_brand() {
        return name_brand;
    }

    public void setName_brand(String name_brand) {
        this.name_brand = name_brand;
    }

    public String getId_brand() {
        return id_brand;
    }

    public void setId_brand(String id_brand) {
        this.id_brand = id_brand;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "BrandBigOilObj{" +
                "name_brand='" + name_brand + '\'' +
                ", id_brand='" + id_brand + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
