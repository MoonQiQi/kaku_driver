package com.yichang.kaku.obj;

import java.io.Serializable;
import java.util.List;

public class SeckillDetailObj implements Serializable {

    private String id_goods, time_begin, time_end, price_seckill, num_seckill, price_goods, name_goods, image_goods, percent;
    private String flag_type;

    private String url_goods;

    private String star_goods,num_eval,num_exchange;

    public String getStar_goods() {
        return star_goods;
    }

    public void setStar_goods(String star_goods) {
        this.star_goods = star_goods;
    }

    public String getNum_eval() {
        return num_eval;
    }

    public void setNum_eval(String num_eval) {
        this.num_eval = num_eval;
    }

    public String getNum_exchange() {
        return num_exchange;
    }

    public void setNum_exchange(String num_exchange) {
        this.num_exchange = num_exchange;
    }
    /*public List<ShopMallProductCommentObj> evals;*/


    public String getId_goods() {
        return id_goods;
    }

    public void setId_goods(String id_goods) {
        this.id_goods = id_goods;
    }

    public String getTime_begin() {
        return time_begin;
    }

    public void setTime_begin(String time_begin) {
        this.time_begin = time_begin;
    }

    @Override
    public String toString() {
        return "SeckillDetailObj{" +
                "id_goods='" + id_goods + '\'' +
                ", time_begin='" + time_begin + '\'' +
                ", time_end='" + time_end + '\'' +
                ", price_seckill='" + price_seckill + '\'' +
                ", num_seckill='" + num_seckill + '\'' +
                ", price_goods='" + price_goods + '\'' +
                ", name_goods='" + name_goods + '\'' +
                ", image_goods='" + image_goods + '\'' +
                ", percent='" + percent + '\'' +
                ", flag_type='" + flag_type + '\'' +
                ", url_goods='" + url_goods + '\''+
                '}';
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getPrice_seckill() {
        return price_seckill;
    }

    public void setPrice_seckill(String price_seckill) {
        this.price_seckill = price_seckill;
    }

    public String getNum_seckill() {
        return num_seckill;
    }

    public void setNum_seckill(String num_seckill) {
        this.num_seckill = num_seckill;
    }

    public String getPrice_goods() {
        return price_goods;
    }

    public void setPrice_goods(String price_goods) {
        this.price_goods = price_goods;
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

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }


    public String getUrl_goods() {
        return url_goods;
    }

    public void setUrl_goods(String url_goods) {
        this.url_goods = url_goods;
    }



    public String getFlag_type() {
        return flag_type;
    }

    public void setFlag_type(String flag_type) {
        this.flag_type = flag_type;
    }
}
