package com.yichang.kaku.obj;

import java.io.Serializable;

public class QuestionAnswerItemObj implements Serializable {

    private String content;
    private String id_driver;
    private String id_shop_user;
    private String image;
    private String time_create;
    private String type;

    @Override
    public String toString() {
        return "QuestionAnswerItemObj{" +
                "content='" + content + '\'' +
                ", id_driver='" + id_driver + '\'' +
                ", id_shop_user='" + id_shop_user + '\'' +
                ", image='" + image + '\'' +
                ", time_create='" + time_create + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId_driver() {
        return id_driver;
    }

    public void setId_driver(String id_driver) {
        this.id_driver = id_driver;
    }

    public String getId_shop_user() {
        return id_shop_user;
    }

    public void setId_shop_user(String id_shop_user) {
        this.id_shop_user = id_shop_user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime_create() {
        return time_create;
    }

    public void setTime_create(String time_create) {
        this.time_create = time_create;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
