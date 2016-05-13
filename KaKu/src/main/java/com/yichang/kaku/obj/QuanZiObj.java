package com.yichang.kaku.obj;

import java.io.Serializable;

public class QuanZiObj implements Serializable {

    private String id_circle;
    private String name_circle;
    private String time_pub;
    private String flag_up;
    private String flag_hot;
    private String num_read;
    private String image_circle1;
    private String num_comments;
    private String head;
    private String name_driver;

    public String getId_circle() {
        return id_circle;
    }

    public void setId_circle(String id_circle) {
        this.id_circle = id_circle;
    }

    public String getName_circle() {
        return name_circle;
    }

    public void setName_circle(String name_circle) {
        this.name_circle = name_circle;
    }

    public String getTime_pub() {
        return time_pub;
    }

    public void setTime_pub(String time_pub) {
        this.time_pub = time_pub;
    }

    public String getFlag_up() {
        return flag_up;
    }

    public void setFlag_up(String flag_up) {
        this.flag_up = flag_up;
    }

    public String getFlag_hot() {
        return flag_hot;
    }

    public void setFlag_hot(String flag_hot) {
        this.flag_hot = flag_hot;
    }

    public String getNum_read() {
        return num_read;
    }

    public void setNum_read(String num_read) {
        this.num_read = num_read;
    }

    public String getNum_comments() {
        return num_comments;
    }

    public void setNum_comments(String num_comments) {
        this.num_comments = num_comments;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getName_driver() {
        return name_driver;
    }

    public void setName_driver(String name_driver) {
        this.name_driver = name_driver;
    }

    public String getImage_circle1() {
        return image_circle1;
    }

    public void setImage_circle1(String image_circle1) {
        this.image_circle1 = image_circle1;
    }

    @Override
    public String toString() {
        return "QuanZiObj{" +
                "id_circle='" + id_circle + '\'' +
                ", name_circle='" + name_circle + '\'' +
                ", time_pub='" + time_pub + '\'' +
                ", flag_up='" + flag_up + '\'' +
                ", flag_hot='" + flag_hot + '\'' +
                ", num_read='" + num_read + '\'' +
                ", image_circle1='" + image_circle1 + '\'' +
                ", num_comments='" + num_comments + '\'' +
                ", head='" + head + '\'' +
                ", name_driver='" + name_driver + '\'' +
                '}';
    }
}
