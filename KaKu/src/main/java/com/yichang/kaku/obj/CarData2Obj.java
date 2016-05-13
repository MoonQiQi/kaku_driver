package com.yichang.kaku.obj;

import java.io.Serializable;

public class CarData2Obj implements Serializable {

    private String name_data;
    private String id_data;
    private String color_type;

    public String getName_data() {
        return name_data;
    }

    public void setName_data(String name_data) {
        this.name_data = name_data;
    }

    public String getId_data() {
        return id_data;
    }

    public void setId_data(String id_data) {
        this.id_data = id_data;
    }

    public String getColor_type() {
        return color_type;
    }

    public void setColor_type(String color_type) {
        this.color_type = color_type;
    }

    @Override
    public String toString() {
        return "CarData2Obj{" +
                "name_data='" + name_data + '\'' +
                ", id_data='" + id_data + '\'' +
                ", color_type='" + color_type + '\'' +
                '}';
    }
}
