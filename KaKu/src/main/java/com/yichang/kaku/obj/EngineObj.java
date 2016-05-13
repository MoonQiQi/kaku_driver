package com.yichang.kaku.obj;

import java.io.Serializable;

public class EngineObj implements Serializable {

    private String power;
    private String emissions;
    private String series_engine;
    private String name_engine;

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getEmissions() {
        return emissions;
    }

    public void setEmissions(String emissions) {
        this.emissions = emissions;
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

    @Override
    public String toString() {
        return "EngineObj{" +
                "power='" + power + '\'' +
                ", emissions='" + emissions + '\'' +
                ", series_engine='" + series_engine + '\'' +
                ", name_engine='" + name_engine + '\'' +
                '}';
    }
}
