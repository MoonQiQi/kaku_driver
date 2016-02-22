package com.yichang.kaku.obj;

import java.io.Serializable;

/**
 * Created by chaih on 2015/8/10.
 * 车品商城商品信息
 */
public class ShopCartProductObj implements Serializable {

    private String id_goods_shopcar,id_goods,name_goods,num_shopcar,image_goods,price_goods;
    private Boolean isChecked=true;

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public String toString() {
        return "ShopCartProductObj{" +
                "id_goods_shopcar='" + id_goods_shopcar + '\'' +
                ", id_goods='" + id_goods + '\'' +
                ", name_goods='" + name_goods + '\'' +
                ", num_shopcar='" + num_shopcar + '\'' +
                ", image_goods='" + image_goods + '\'' +
                ", price_goods='" + price_goods + '\'' +
                '}';
    }

    public String getId_goods_shopcar() {
        return id_goods_shopcar;
    }

    public void setId_goods_shopcar(String id_goods_shopcar) {
        this.id_goods_shopcar = id_goods_shopcar;
    }

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

    public String getPrice_goods() {
        return price_goods;
    }

    public void setPrice_goods(String price_goods) {
        this.price_goods = price_goods;
    }
}
