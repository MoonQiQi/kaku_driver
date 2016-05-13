package com.yichang.kaku.obj;

/**
 * Created by chaih on 2015/8/10.
 * 车品商城商品信息
 */
public class ShopMallProductDetailObj {

    private String name_goods;
    private String image_goods;
    private String flag_shopcar;
    private String num_goods;
    private String price_goods;
    private String price_goods_buy;
    private String eval_goods;
    private String num_exchange;
    private String promotion_goods;
    private String remark_medal;
    private String flag_medal;
    private String flag_point;
    private String name_model;
    private String name_goods_model;
    private String url_goods;

    public String getUrl_goods() {
        return url_goods;
    }

    public void setUrl_goods(String url_goods) {
        this.url_goods = url_goods;
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

    public String getRemark_medal() {
        return remark_medal;
    }

    public void setRemark_medal(String remark_medal) {
        this.remark_medal = remark_medal;
    }

    public String getFlag_medal() {
        return flag_medal;
    }

    public void setFlag_medal(String flag_medal) {
        this.flag_medal = flag_medal;
    }

    public String getFlag_point() {
        return flag_point;
    }

    public void setFlag_point(String flag_point) {
        this.flag_point = flag_point;
    }

    public String getName_model() {
        return name_model;
    }

    public void setName_model(String name_model) {
        this.name_model = name_model;
    }

    public String getName_goods_model() {
        return name_goods_model;
    }

    public void setName_goods_model(String name_goods_model) {
        this.name_goods_model = name_goods_model;
    }

    @Override
    public String toString() {
        return "ShopMallProductDetailObj{" +
                "name_goods='" + name_goods + '\'' +
                ", image_goods='" + image_goods + '\'' +
                ", flag_shopcar='" + flag_shopcar + '\'' +
                ", num_goods='" + num_goods + '\'' +
                ", price_goods='" + price_goods + '\'' +
                ", price_goods_buy='" + price_goods_buy + '\'' +
                ", eval_goods='" + eval_goods + '\'' +
                ", num_exchange='" + num_exchange + '\'' +
                ", promotion_goods='" + promotion_goods + '\'' +
                ", remark_medal='" + remark_medal + '\'' +
                ", flag_medal='" + flag_medal + '\'' +
                ", flag_point='" + flag_point + '\'' +
                ", name_model='" + name_model + '\'' +
                ", name_goods_model='" + name_goods_model + '\'' +
                ", url_goods='" + url_goods + '\'' +
                '}';
    }
}
