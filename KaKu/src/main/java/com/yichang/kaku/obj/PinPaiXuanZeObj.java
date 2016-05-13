package com.yichang.kaku.obj;

import java.io.Serializable;

public class PinPaiXuanZeObj implements Serializable {

    private String id_brand;
    private String name_brand;
    private String image_brand;

    public String getId_brand() {
        return id_brand;
    }

    public void setId_brand(String id_brand) {
        this.id_brand = id_brand;
    }

    public String getName_brand() {
        return name_brand;
    }

    public void setName_brand(String name_brand) {
        this.name_brand = name_brand;
    }

    public String getImage_brand() {
        return image_brand;
    }

    public void setImage_brand(String image_brand) {
        this.image_brand = image_brand;
    }

    @Override
    public String toString() {
        return "PinPaiXuanZeObj{" +
                "id_brand='" + id_brand + '\'' +
                ", name_brand='" + name_brand + '\'' +
                ", image_brand='" + image_brand + '\'' +
                '}';
    }
}
