package com.yichang.kaku.obj;

import java.io.Serializable;

public class HotActivityObj implements Serializable {

    private String image;
    private String id_shop;

    public String getId_shop() {
        return id_shop;
    }

    public void setId_shop(String id_shop) {
        this.id_shop = id_shop;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "HotActivityObj{" +
                "image='" + image + '\'' +
                ", id_shop='" + id_shop + '\'' +
                '}';
    }
}
