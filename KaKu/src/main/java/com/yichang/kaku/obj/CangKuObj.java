package com.yichang.kaku.obj;

import java.io.Serializable;

public class CangKuObj implements Serializable {

    private String no_bill;
    private String image_item;
    private String name_item;
    private String name1_item;
    private String flag_used;
    private String code_used;
    private String time_used;
    private String name_shop;
    private String num_item;

    public String getNo_bill() {
        return no_bill;
    }

    public void setNo_bill(String no_bill) {
        this.no_bill = no_bill;
    }

    public String getImage_item() {
        return image_item;
    }

    public void setImage_item(String image_item) {
        this.image_item = image_item;
    }

    public String getName_item() {
        return name_item;
    }

    public void setName_item(String name_item) {
        this.name_item = name_item;
    }

    public String getName1_item() {
        return name1_item;
    }

    public void setName1_item(String name1_item) {
        this.name1_item = name1_item;
    }

    public String getFlag_used() {
        return flag_used;
    }

    public void setFlag_used(String flag_used) {
        this.flag_used = flag_used;
    }

    public String getCode_used() {
        return code_used;
    }

    public void setCode_used(String code_used) {
        this.code_used = code_used;
    }

    public String getName_shop() {
        return name_shop;
    }

    public void setName_shop(String name_shop) {
        this.name_shop = name_shop;
    }

    public String getNum_item() {
        return num_item;
    }

    public void setNum_item(String num_item) {
        this.num_item = num_item;
    }

    public String getTime_used() {
        return time_used;
    }

    public void setTime_used(String time_used) {
        this.time_used = time_used;
    }

    @Override
    public String toString() {
        return "CangKuObj{" +
                "no_bill='" + no_bill + '\'' +
                ", image_item='" + image_item + '\'' +
                ", name_item='" + name_item + '\'' +
                ", name1_item='" + name1_item + '\'' +
                ", flag_used='" + flag_used + '\'' +
                ", code_used='" + code_used + '\'' +
                ", time_used='" + time_used + '\'' +
                ", name_shop='" + name_shop + '\'' +
                ", num_item='" + num_item + '\'' +
                '}';
    }
}
