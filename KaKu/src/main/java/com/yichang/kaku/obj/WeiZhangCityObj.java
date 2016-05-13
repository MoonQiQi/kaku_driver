package com.yichang.kaku.obj;

import java.io.Serializable;

public class WeiZhangCityObj implements Serializable {

    private String city_name;
    private String city_code;
    private String abbr;
    private int engine;
    private int engineno;
    private int classa;
    private int classno;
    private String regist;
    private String registno;

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public int getEngine() {
        return engine;
    }

    public void setEngine(int engine) {
        this.engine = engine;
    }

    public int getEngineno() {
        return engineno;
    }

    public void setEngineno(int engineno) {
        this.engineno = engineno;
    }

    public int getClassa() {
        return classa;
    }

    public void setClassa(int classa) {
        this.classa = classa;
    }

    public int getClassno() {
        return classno;
    }

    public void setClassno(int classno) {
        this.classno = classno;
    }

    public String getRegist() {
        return regist;
    }

    public void setRegist(String regist) {
        this.regist = regist;
    }

    public String getRegistno() {
        return registno;
    }

    public void setRegistno(String registno) {
        this.registno = registno;
    }

    @Override
    public String toString() {
        return "WeiZhangCityObj{" +
                "city_name='" + city_name + '\'' +
                ", city_code='" + city_code + '\'' +
                ", abbr='" + abbr + '\'' +
                ", engine=" + engine +
                ", engineno=" + engineno +
                ", classa=" + classa +
                ", classno=" + classno +
                ", regist='" + regist + '\'' +
                ", registno='" + registno + '\'' +
                '}';
    }
}
