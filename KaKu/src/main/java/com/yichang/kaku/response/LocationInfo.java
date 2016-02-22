package com.yichang.kaku.response;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by xiaosu on 2015/11/12.
 */
public class LocationInfo {
    public LocationInfo(LatLng latLng, String name, String city) {
        this.latLng = latLng;
        this.name = name;
        this.city = city;
    }

    public LatLng latLng;
    public String name;
    public String city;

    public void update(LatLng latLng, String name,String city) {
        this.latLng = latLng;
        this.name = name;
    }

}
