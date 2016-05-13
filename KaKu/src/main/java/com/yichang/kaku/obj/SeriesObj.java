package com.yichang.kaku.obj;

import java.io.Serializable;

public class SeriesObj implements Serializable {

    private String id_series;
    private String name_series;

    public String getId_series() {
        return id_series;
    }

    public void setId_series(String id_series) {
        this.id_series = id_series;
    }

    public String getName_series() {
        return name_series;
    }

    public void setName_series(String name_series) {
        this.name_series = name_series;
    }

    @Override
    public String toString() {
        return "SeriesObj{" +
                "id_series='" + id_series + '\'' +
                ", name_series='" + name_series + '\'' +
                '}';
    }
}
