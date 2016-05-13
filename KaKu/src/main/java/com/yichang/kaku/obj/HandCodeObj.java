package com.yichang.kaku.obj;

import java.io.Serializable;

public class HandCodeObj implements Serializable {

    private String code;
    private String id_brand;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId_brand() {
        return id_brand;
    }

    public void setId_brand(String id_brand) {
        this.id_brand = id_brand;
    }

    @Override
    public String toString() {
        return "HandCodeObj{" +
                "code='" + code + '\'' +
                ", id_brand='" + id_brand + '\'' +
                '}';
    }
}
