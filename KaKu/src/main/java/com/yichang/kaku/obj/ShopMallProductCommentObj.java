package com.yichang.kaku.obj;

/**
 * Created by chaih on 2015/8/10.
 * 车品商城商品信息
 */
public class ShopMallProductCommentObj {


    private String name_driver;
    private String time_eval;
    private String content_eval;
    private String image_eval;
    private String star_eval;

    @Override
    public String toString() {
        return "ShopMallProductCommentObj{" +
                "name_driver='" + name_driver + '\'' +
                ", time_eval='" + time_eval + '\'' +
                ", content_eval='" + content_eval + '\'' +
                ", image_eval='" + image_eval + '\'' +
                ", star_eval='" + star_eval + '\'' +
                '}';
    }

    public String getName_driver() {
        return name_driver;
    }

    public void setName_driver(String name_driver) {
        this.name_driver = name_driver;
    }

    public String getTime_eval() {
        return time_eval;
    }

    public void setTime_eval(String time_eval) {
        this.time_eval = time_eval;
    }

    public String getContent_eval() {
        return content_eval;
    }

    public void setContent_eval(String content_eval) {
        this.content_eval = content_eval;
    }

    public String getImage_eval() {
        return image_eval;
    }

    public void setImage_eval(String image_eval) {
        this.image_eval = image_eval;
    }

    public String getStar_eval() {
        return star_eval;
    }

    public void setStar_eval(String star_eval) {
        this.star_eval = star_eval;
    }
}
