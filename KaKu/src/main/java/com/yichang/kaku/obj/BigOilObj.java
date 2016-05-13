package com.yichang.kaku.obj;

import java.io.Serializable;

public class BigOilObj implements Serializable {

    private String id_item;
    private String image_item;
    private String name_item;
    private String name1_item;
    private String flag_choose;
    private String price_item;
    private String num_item;

    public String getId_item() {
        return id_item;
    }

    public void setId_item(String id_item) {
        this.id_item = id_item;
    }

    public String getFlag_choose() {
        return flag_choose;
    }

    public void setFlag_choose(String flag_choose) {
        this.flag_choose = flag_choose;
    }

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

    public String getPrice_item() {
        return price_item;
    }

    public void setPrice_item(String price_item) {
        this.price_item = price_item;
    }

    public String getNum_item() {
        return num_item;
    }

    public void setNum_item(String num_item) {
        this.num_item = num_item;
    }

    @Override
    public String toString() {
        return "BigOilObj{" +
                "id_item='" + id_item + '\'' +
                ", image_item='" + image_item + '\'' +
                ", name_item='" + name_item + '\'' +
                ", name1_item='" + name1_item + '\'' +
                ", flag_choose='" + flag_choose + '\'' +
                ", price_item='" + price_item + '\'' +
                ", num_item='" + num_item + '\'' +
                '}';
    }
}
