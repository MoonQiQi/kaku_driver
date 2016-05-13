package com.yichang.kaku.obj;

import java.io.Serializable;

public class GoodsModelObj implements Serializable {

    private String id_goods;
    private String name_goods_model;
    private String image_goods;
    private String num_goods;
    private String price_goods_buy;
    private String flag_shopcar;
    private String color;
    private String name_model;

    public String getId_goods() {
        return id_goods;
    }

    public void setId_goods(String id_goods) {
        this.id_goods = id_goods;
    }

    public String getName_goods_model() {
        return name_goods_model;
    }

    public void setName_goods_model(String name_goods_model) {
        this.name_goods_model = name_goods_model;
    }

    public String getImage_goods() {
        return image_goods;
    }

    public void setImage_goods(String image_goods) {
        this.image_goods = image_goods;
    }

    public String getNum_goods() {
        return num_goods;
    }

    public void setNum_goods(String num_goods) {
        this.num_goods = num_goods;
    }

    public String getPrice_goods_buy() {
        return price_goods_buy;
    }

    public void setPrice_goods_buy(String price_goods_buy) {
        this.price_goods_buy = price_goods_buy;
    }

    public String getFlag_shopcar() {
        return flag_shopcar;
    }

    public void setFlag_shopcar(String flag_shopcar) {
        this.flag_shopcar = flag_shopcar;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName_model() {
        return name_model;
    }

    public void setName_model(String name_model) {
        this.name_model = name_model;
    }

    @Override
    public String toString() {
        return "GoodsModelObj{" +
                "id_goods='" + id_goods + '\'' +
                ", name_goods_model='" + name_goods_model + '\'' +
                ", image_goods='" + image_goods + '\'' +
                ", num_goods='" + num_goods + '\'' +
                ", price_goods_buy='" + price_goods_buy + '\'' +
                ", flag_shopcar='" + flag_shopcar + '\'' +
                ", color='" + color + '\'' +
                ", name_model='" + name_model + '\'' +
                '}';
    }
}

