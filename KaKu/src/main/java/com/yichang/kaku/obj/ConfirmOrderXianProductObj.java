package com.yichang.kaku.obj;

import java.io.Serializable;

/**
 * Created by chaih on 2015/8/10.
 * 确认订单商品信息
 */
public class ConfirmOrderXianProductObj implements Serializable {

    private String name_goods;
    private String num_shopcar;
    private String image_goods;
    private String price_goods_buy;

    public String getName_goods() {
        return name_goods;
    }

    public void setName_goods(String name_goods) {
        this.name_goods = name_goods;
    }

    public String getNum_shopcar() {
        return num_shopcar;
    }

    public void setNum_shopcar(String num_shopcar) {
        this.num_shopcar = num_shopcar;
    }

    public String getImage_goods() {
        return image_goods;
    }

    public void setImage_goods(String image_goods) {
        this.image_goods = image_goods;
    }

    public String getPrice_goods_buy() {
        return price_goods_buy;
    }

    public void setPrice_goods_buy(String price_goods_buy) {
        this.price_goods_buy = price_goods_buy;
    }

    @Override
    public String toString() {
        return "ConfirmOrderXianProductObj{" +
                "name_goods='" + name_goods + '\'' +
                ", num_shopcar='" + num_shopcar + '\'' +
                ", image_goods='" + image_goods + '\'' +
                ", price_goods_buy='" + price_goods_buy + '\'' +
                '}';
    }
}
