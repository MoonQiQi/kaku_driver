package com.yichang.kaku.obj;

import java.io.Serializable;

public class WorkerObj implements Serializable {

    private String id_worker;
    private String name_worker;
    private String num_bill;
    private String num_star;
    private String var_lon;
    private String var_lat;
    private String distance;
    private String price_service_mix;
    private String price_service_max;
    private String price_service;

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

    public String getVar_lon() {
        return var_lon;
    }

    public void setVar_lon(String var_lon) {
        this.var_lon = var_lon;
    }

    public String getVar_lat() {
        return var_lat;
    }

    public void setVar_lat(String var_lat) {
        this.var_lat = var_lat;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPrice_service_mix() {
        return price_service_mix;
    }

    public void setPrice_service_mix(String price_service_mix) {
        this.price_service_mix = price_service_mix;
    }

    public String getPrice_service_max() {
        return price_service_max;
    }

    public void setPrice_service_max(String price_service_max) {
        this.price_service_max = price_service_max;
    }

    public String getPrice_service() {
        return price_service;
    }

    public void setPrice_service(String price_service) {
        this.price_service = price_service;
    }

    @Override
    public String toString() {
        return "WorkerObj{" +
                "id_worker='" + id_worker + '\'' +
                ", name_worker='" + name_worker + '\'' +
                ", num_bill='" + num_bill + '\'' +
                ", num_star='" + num_star + '\'' +
                ", var_lon='" + var_lon + '\'' +
                ", var_lat='" + var_lat + '\'' +
                ", distance='" + distance + '\'' +
                ", price_service_mix='" + price_service_mix + '\'' +
                ", price_service_max='" + price_service_max + '\'' +
                ", price_service='" + price_service + '\'' +
                '}';
    }
}
