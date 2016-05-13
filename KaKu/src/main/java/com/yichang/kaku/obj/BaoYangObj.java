package com.yichang.kaku.obj;

import java.io.Serializable;

public class BaoYangObj implements Serializable {

    private String id_upkeep;
    private String name_goods;
    private String image_goods;
    private String num_buy;
    private String num_eval;
    private String price_goods;
    private String price_goods_line;
    private String remark_goods;
    private String url_goods;
    private String color;

    public String getId_upkeep() {
        return id_upkeep;
    }

    public void setId_upkeep(String id_upkeep) {
        this.id_upkeep = id_upkeep;
    }

    public String getName_goods() {
        return name_goods;
    }

    public void setName_goods(String name_goods) {
        this.name_goods = name_goods;
    }

    public String getImage_goods() {
        return image_goods;
    }

    public void setImage_goods(String image_goods) {
        this.image_goods = image_goods;
    }

    public String getNum_buy() {
        return num_buy;
    }

    public void setNum_buy(String num_buy) {
        this.num_buy = num_buy;
    }

    public String getNum_eval() {
        return num_eval;
    }

    public void setNum_eval(String num_eval) {
        this.num_eval = num_eval;
    }

    public String getPrice_goods() {
        return price_goods;
    }

    public void setPrice_goods(String price_goods) {
        this.price_goods = price_goods;
    }

    public String getPrice_goods_line() {
        return price_goods_line;
    }

    public void setPrice_goods_line(String price_goods_line) {
        this.price_goods_line = price_goods_line;
    }

    public String getRemark_goods() {
        return remark_goods;
    }

    public void setRemark_goods(String remark_goods) {
        this.remark_goods = remark_goods;
    }

    public String getUrl_goods() {
        return url_goods;
    }

    public void setUrl_goods(String url_goods) {
        this.url_goods = url_goods;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "BaoYangObj{" +
                "id_upkeep='" + id_upkeep + '\'' +
                ", name_goods='" + name_goods + '\'' +
                ", image_goods='" + image_goods + '\'' +
                ", num_buy='" + num_buy + '\'' +
                ", num_eval='" + num_eval + '\'' +
                ", price_goods='" + price_goods + '\'' +
                ", price_goods_line='" + price_goods_line + '\'' +
                ", remark_goods='" + remark_goods + '\'' +
                ", url_goods='" + url_goods + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
