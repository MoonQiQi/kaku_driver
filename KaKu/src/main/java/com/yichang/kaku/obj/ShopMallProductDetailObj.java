package com.yichang.kaku.obj;

/**
 * Created by chaih on 2015/8/10.
 * 车品商城商品信息
 */
public class ShopMallProductDetailObj {

    private String name_goods;
    private String image_goods;
    private String type_goods;
    private String num_goods;
    private String price_goods;
    private String eval_goods;
    private String num_exchange;
    private String promotion_goods;

    private String url_goods;


    public String getUrl_goods() {
        return url_goods;
    }

    public void setUrl_goods(String url_goods) {
        this.url_goods = url_goods;
    }

    @Override
    public String toString() {
        return "ShopMallProductDetailObj{" +
                "name_goods='" + name_goods + '\'' +
                ", image_goods='" + image_goods + '\'' +
                ", type_goods='" + type_goods + '\'' +
                ", num_goods='" + num_goods + '\'' +
                ", price_goods='" + price_goods + '\'' +
                ", eval_goods='" + eval_goods + '\'' +
                ", num_exchange='" + num_exchange + '\'' +
                ", promotion_goods='" + promotion_goods + '\'' +
                '}';
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

    public String getEval_goods() {
        return eval_goods;
    }

    public void setEval_goods(String eval_goods) {
        this.eval_goods = eval_goods;
    }

    public String getNum_exchange() {
        return num_exchange;
    }

    public void setNum_exchange(String num_exchange) {
        this.num_exchange = num_exchange;
    }

    public String getPromotion_goods() {
        return promotion_goods;
    }

    public void setPromotion_goods(String promotion_goods) {
        this.promotion_goods = promotion_goods;
    }
}
