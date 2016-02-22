package com.yichang.kaku.obj;

/**
 * Created by chaih on 2015/8/10.
 * 车品商城商品信息
 */
public class ShopMallProductObj {

    private String id_goods,sort_goods,name_goods,image_goods,type_goods,num_goods,price_goods,num_exchange,time_create,promotion_goods;

    @Override
    public String toString() {
        return "ShopMallProductObj{" +
                "id_goods='" + id_goods + '\'' +
                ", sort_goods='" + sort_goods + '\'' +
                ", name_goods='" + name_goods + '\'' +
                ", image_goods='" + image_goods + '\'' +
                ", type_goods='" + type_goods + '\'' +
                ", num_goods='" + num_goods + '\'' +
                ", price_goods='" + price_goods + '\'' +
                ", num_exchange='" + num_exchange + '\'' +
                ", time_create='" + time_create + '\'' +
                ", promotion_goods='" + promotion_goods + '\'' +
                '}';
    }

    public String getId_goods() {
        return id_goods;
    }

    public void setId_goods(String id_goods) {
        this.id_goods = id_goods;
    }

    public String getSort_goods() {
        return sort_goods;
    }

    public void setSort_goods(String sort_goods) {
        this.sort_goods = sort_goods;
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

    public String getType_goods() {
        return type_goods;
    }

    public void setType_goods(String type_goods) {
        this.type_goods = type_goods;
    }

    public String getNum_goods() {
        return num_goods;
    }

    public void setNum_goods(String num_goods) {
        this.num_goods = num_goods;
    }

    public String getPrice_goods() {
        return price_goods;
    }

    public void setPrice_goods(String price_goods) {
        this.price_goods = price_goods;
    }

    public String getNum_exchange() {
        return num_exchange;
    }

    public void setNum_exchange(String num_exchange) {
        this.num_exchange = num_exchange;
    }

    public String getTime_create() {
        return time_create;
    }

    public void setTime_create(String time_create) {
        this.time_create = time_create;
    }

    public String getPromotion_goods() {
        return promotion_goods;
    }

    public void setPromotion_goods(String promotion_goods) {
        this.promotion_goods = promotion_goods;
    }
}
