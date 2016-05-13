package com.yichang.kaku.obj;

import java.io.Serializable;

public class Worker2Obj implements Serializable {

    public String tel_worker;
    public String id_worker;
    public String name_worker;
    public String image_worker;
    public String num_bill;
    public String num_star;
    public String var_lat;
    public String var_lon;
    public String flag_butter;
    public String flag_tire;
    public String flag_wheel;

    public String getTel_worker() {
        return tel_worker;
    }

    public void setTel_worker(String tel_worker) {
        this.tel_worker = tel_worker;
    }

    public String getId_worker() {
        return id_worker;
    }

    public void setId_worker(String id_worker) {
        this.id_worker = id_worker;
    }

    public String getName_worker() {
        return name_worker;
    }

    public void setName_worker(String name_worker) {
        this.name_worker = name_worker;
    }

    public String getImage_worker() {
        return image_worker;
    }

    public void setImage_worker(String image_worker) {
        this.image_worker = image_worker;
    }

    public String getNum_bill() {
        return num_bill;
    }

    public void setNum_bill(String num_bill) {
        this.num_bill = num_bill;
    }

    public String getNum_star() {
        return num_star;
    }

    public void setNum_star(String num_star) {
        this.num_star = num_star;
    }

    public String getVar_lat() {
        return var_lat;
    }

    public void setVar_lat(String var_lat) {
        this.var_lat = var_lat;
    }

    public String getVar_lon() {
        return var_lon;
    }

    public void setVar_lon(String var_lon) {
        this.var_lon = var_lon;
    }

    public String getFlag_butter() {
        return flag_butter;
    }

    public void setFlag_butter(String flag_butter) {
        this.flag_butter = flag_butter;
    }

    public String getFlag_tire() {
        return flag_tire;
    }

    public void setFlag_tire(String flag_tire) {
        this.flag_tire = flag_tire;
    }

    public String getFlag_wheel() {
        return flag_wheel;
    }

    public void setFlag_wheel(String flag_wheel) {
        this.flag_wheel = flag_wheel;
    }

    @Override
    public String toString() {
        return "Worker2Obj{" +
                "tel_worker='" + tel_worker + '\'' +
                ", id_worker='" + id_worker + '\'' +
                ", name_worker='" + name_worker + '\'' +
                ", image_worker='" + image_worker + '\'' +
                ", num_bill='" + num_bill + '\'' +
                ", num_star='" + num_star + '\'' +
                ", var_lat='" + var_lat + '\'' +
                ", var_lon='" + var_lon + '\'' +
                ", flag_butter='" + flag_butter + '\'' +
                ", flag_tire='" + flag_tire + '\'' +
                ", flag_wheel='" + flag_wheel + '\'' +
                '}';
    }
}
