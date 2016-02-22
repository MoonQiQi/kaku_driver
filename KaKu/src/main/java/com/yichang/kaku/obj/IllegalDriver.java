package com.yichang.kaku.obj;

import java.io.Serializable;

public class IllegalDriver implements Serializable {

    //车牌号，车架号，发动机号，短名称，省份，城市

    private String carnumber, carcode, cardrivenumber, carno, carpro, carci;

    @Override
    public String toString() {
        return "IllegalDriver{" +
                "carnumber='" + carnumber + '\'' +
                ", carcode='" + carcode + '\'' +
                ", cardrivenumber='" + cardrivenumber + '\'' +
                ", carno='" + carno + '\'' +
                ", carpro='" + carpro + '\'' +
                ", carci='" + carci + '\'' +
                '}';
    }

    public String getCarnumber() {
        int i = this.carno.length();
        if (carnumber.length() > i) {

            return carnumber.substring(i, carnumber.length());
        }
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }

    public String getCarcode() {
        return carcode;
    }

    public void setCarcode(String carcode) {
        this.carcode = carcode;
    }

    public String getCardrivenumber() {
        return cardrivenumber;
    }

    public void setCardrivenumber(String cardrivenumber) {
        this.cardrivenumber = cardrivenumber;
    }

    public String getCarno() {
        return carno;
    }

    public void setCarno(String carno) {
        this.carno = carno;
    }

    public String getCarpro() {
        return carpro;
    }

    public void setCarpro(String carpro) {
        this.carpro = carpro;
    }

    public String getCarci() {
        return carci;
    }

    public void setCarci(String carci) {
        this.carci = carci;
    }
}

