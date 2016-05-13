package com.yichang.kaku.obj;

import java.io.Serializable;

public class BaoYangItemObj implements Serializable {

    private String image_item;
    private String name_item;
    private String name1_item;
    private String id_item;

    public String getImage_item() {
        return image_item;
    }

    public void setImage_item(String image_item) {
        this.image_item = image_item;
    }

    public String getName_item() {
        return name_item;
    }

    public void setName_item(String name_item) {
        this.name_item = name_item;
    }

    public String getName1_item() {
        return name1_item;
    }

    public void setName1_item(String name1_item) {
        this.name1_item = name1_item;
    }

    public String getId_item() {
        return id_item;
    }

    public void setId_item(String id_item) {
        this.id_item = id_item;
    }

    @Override
    public String toString() {
        return "BaoYangItemObj{" +
                "image_item='" + image_item + '\'' +
                ", name_item='" + name_item + '\'' +
                ", name1_item='" + name1_item + '\'' +
                ", id_item='" + id_item + '\'' +
                '}';
    }
}
