package com.yichang.kaku.obj;

import java.io.Serializable;

public class CheTiePingJiaObj implements Serializable {

    private String name_driver;
    private String time_eval;
    private String content_eval;
    private String image_eval;
    private String image_eval1;
    private String image_eval2;
    private String image_eval3;

    public String getName_driver() {
        return name_driver;
    }

    public void setName_driver(String name_driver) {
        this.name_driver = name_driver;
    }

    public String getImage_eval() {
        return image_eval;
    }

    public void setImage_eval(String image_eval) {
        this.image_eval = image_eval;
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

    public String getImage_eval1() {
        return image_eval1;
    }

    public void setImage_eval1(String image_eval1) {
        this.image_eval1 = image_eval1;
    }

    public String getImage_eval2() {
        return image_eval2;
    }

    public void setImage_eval2(String image_eval2) {
        this.image_eval2 = image_eval2;
    }

    public String getImage_eval3() {
        return image_eval3;
    }

    public void setImage_eval3(String image_eval3) {
        this.image_eval3 = image_eval3;
    }

    @Override
    public String toString() {
        return "CheTiePingJiaObj{" +
                "name_driver='" + name_driver + '\'' +
                ", time_eval='" + time_eval + '\'' +
                ", content_eval='" + content_eval + '\'' +
                ", image_eval='" + image_eval + '\'' +
                ", image_eval1='" + image_eval1 + '\'' +
                ", image_eval2='" + image_eval2 + '\'' +
                ", image_eval3='" + image_eval3 + '\'' +
                '}';
    }
}
