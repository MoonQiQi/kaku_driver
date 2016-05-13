package com.yichang.kaku.obj;

import java.io.Serializable;

public class MyCarObj implements Serializable {

    private String car_no;
    private String flag_default;
    private String name_brand;
    private String image_brand;
    private String series_engine;
    private String name_engine;
    private String power;
    private String id_driver_car;

    public String getCar_no() {
        return car_no;
    }

    public void setCar_no(String car_no) {
        this.car_no = car_no;
    }

    public String getFlag_default() {
        return flag_default;
    }

    public void setFlag_default(String flag_default) {
        this.flag_default = flag_default;
    }

    public String getName_brand() {
        return name_brand;
    }

    public void setName_brand(String name_brand) {
        this.name_brand = name_brand;
    }

    public String getImage_brand() {
        return image_brand;
    }

    public void setImage_brand(String image_brand) {
        this.image_brand = image_brand;
    }

    public String getSeries_engine() {
        return series_engine;
    }

    public void setSeries_engine(String series_engine) {
        this.series_engine = series_engine;
    }

    public String getName_engine() {
        return name_engine;
    }

    public void setName_engine(String name_engine) {
        this.name_engine = name_engine;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getId_driver_car() {
        return id_driver_car;
    }

    public void setId_driver_car(String id_driver_car) {
        this.id_driver_car = id_driver_car;
    }

    @Override
    public String toString() {
        return "MyCarObj{" +
                "car_no='" + car_no + '\'' +
                ", flag_default='" + flag_default + '\'' +
                ", name_brand='" + name_brand + '\'' +
                ", image_brand='" + image_brand + '\'' +
                ", series_engine='" + series_engine + '\'' +
                ", name_engine='" + name_engine + '\'' +
                ", power='" + power + '\'' +
                ", id_driver_car='" + id_driver_car + '\'' +
                '}';
    }
}
