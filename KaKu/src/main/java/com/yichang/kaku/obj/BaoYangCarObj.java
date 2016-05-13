package com.yichang.kaku.obj;

import java.io.Serializable;

public class BaoYangCarObj implements Serializable {

    private String name_brand;
    private String image_brand;
    private String series_engine;
    private String num_power;

    public String getName_brand() {
        return name_brand;
    }

    public void setName_brand(String name_brand) {
        this.name_brand = name_brand;
    }

    public String getNum_power() {
        return num_power;
    }

    public void setNum_power(String num_power) {
        this.num_power = num_power;
    }

    public String getSeries_engine() {
        return series_engine;
    }

    public void setSeries_engine(String series_engine) {
        this.series_engine = series_engine;
    }

    public String getImage_brand() {
        return image_brand;
    }

    public void setImage_brand(String image_brand) {
        this.image_brand = image_brand;
    }

    @Override
    public String toString() {
        return "BaoYangCarObj{" +
                "name_brand='" + name_brand + '\'' +
                ", image_brand='" + image_brand + '\'' +
                ", series_engine='" + series_engine + '\'' +
                ", num_power='" + num_power + '\'' +
                '}';
    }
}
