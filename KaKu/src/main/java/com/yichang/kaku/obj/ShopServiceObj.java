package com.yichang.kaku.obj;

import java.io.Serializable;
import java.util.List;

public class ShopServiceObj implements Serializable {

    public String name_service;
    public String image_service;
    public List<ShopService2Obj> service_list;

    public String getName_service() {
        return name_service;
    }

    public void setName_service(String name_service) {
        this.name_service = name_service;
    }

    public String getImage_service() {
        return image_service;
    }

    public void setImage_service(String image_service) {
        this.image_service = image_service;
    }

    public List<ShopService2Obj> getService_list() {
        return service_list;
    }

    public void setService_list(List<ShopService2Obj> service_list) {
        this.service_list = service_list;
    }

    @Override
    public String toString() {
        return "ShopServiceObj{" +
                "name_service='" + name_service + '\'' +
                ", image_service='" + image_service + '\'' +
                ", service_list=" + service_list +
                '}';
    }
}
