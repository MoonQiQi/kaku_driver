package com.yichang.kaku.obj;

import java.io.Serializable;

public class UpkeepPictureObj implements Serializable {

    private String image_series;
    private String image_engine;
    private String image_filter1;
    private String image_filter2;
    private String remark_series;
    private String remark_engine;
    private String remark_filter1;
    private String remark_filter2;

    public String getImage_series() {
        return image_series;
    }

    public void setImage_series(String image_series) {
        this.image_series = image_series;
    }

    public String getImage_engine() {
        return image_engine;
    }

    public void setImage_engine(String image_engine) {
        this.image_engine = image_engine;
    }

    public String getImage_filter1() {
        return image_filter1;
    }

    public void setImage_filter1(String image_filter1) {
        this.image_filter1 = image_filter1;
    }

    public String getImage_filter2() {
        return image_filter2;
    }

    public void setImage_filter2(String image_filter2) {
        this.image_filter2 = image_filter2;
    }

    public String getRemark_series() {
        return remark_series;
    }

    public void setRemark_series(String remark_series) {
        this.remark_series = remark_series;
    }

    public String getRemark_engine() {
        return remark_engine;
    }

    public void setRemark_engine(String remark_engine) {
        this.remark_engine = remark_engine;
    }

    public String getRemark_filter1() {
        return remark_filter1;
    }

    public void setRemark_filter1(String remark_filter1) {
        this.remark_filter1 = remark_filter1;
    }

    public String getRemark_filter2() {
        return remark_filter2;
    }

    public void setRemark_filter2(String remark_filter2) {
        this.remark_filter2 = remark_filter2;
    }

    @Override
    public String toString() {
        return "UpkeepPictureObj{" +
                "image_series='" + image_series + '\'' +
                ", image_engine='" + image_engine + '\'' +
                ", image_filter1='" + image_filter1 + '\'' +
                ", image_filter2='" + image_filter2 + '\'' +
                ", remark_series='" + remark_series + '\'' +
                ", remark_engine='" + remark_engine + '\'' +
                ", remark_filter1='" + remark_filter1 + '\'' +
                ", remark_filter2='" + remark_filter2 + '\'' +
                '}';
    }
}
