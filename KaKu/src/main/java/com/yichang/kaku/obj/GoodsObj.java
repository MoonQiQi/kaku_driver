package com.yichang.kaku.obj;

import java.io.Serializable;

public class GoodsObj implements Serializable {

    private String id_goods;
    private String name_goods;
    private String price_goods_buy;
    private String image_goods;

    public String getId_goods() {
        return id_goods;
    }

    public void setId_goods(String id_goods) {
        this.id_goods = id_goods;
    }

    public String getName_goods() {
        return name_goods;
    }

    public void setName_goods(String name_goods) {
        this.name_goods = name_goods;
    }

    public String getprice_goods_buy() {
        return price_goods_buy;
    }

    public void setprice_goods_buy(String price_goods_buy) {
        this.price_goods_buy = price_goods_buy;
    }

    public String getImage_goods() {
        return image_goods;
    }

    public void setImage_goods(String image_goods) {
        this.image_goods = image_goods;
    }

    @Override
    public String toString() {
        return "GoodsObj [id_goods=" + id_goods + ", name_goods=" + name_goods
                + ", price_goods_buy=" + price_goods_buy + ", image_goods="
                + image_goods + "]";
    }

}
