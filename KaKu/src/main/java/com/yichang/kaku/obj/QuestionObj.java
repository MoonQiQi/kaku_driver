package com.yichang.kaku.obj;

import java.io.Serializable;

public class QuestionObj implements Serializable {
    private String content;
    private String flag_question;
    private String head;
    private String id_child;
    private String id_driver;
    private String id_question;
    private String image_question;
    private String name_driver;
    private String remark_question;
    private String time_create;
    @Override
    public String toString() {
        return "QuestionObj{" +
                "content='" + content + '\'' +
                ", flag_question='" + flag_question + '\'' +
                ", head='" + head + '\'' +
                ", id_child='" + id_child + '\'' +
                ", id_driver='" + id_driver + '\'' +
                ", id_question='" + id_question + '\'' +
                ", image_question='" + image_question + '\'' +
                ", name_driver='" + name_driver + '\'' +
                ", remark_question='" + remark_question + '\'' +
                ", time_create='" + time_create + '\'' +
                '}';
    }

    public String getId_child() {
        return id_child;
    }

    public void setId_child(String id_child) {
        this.id_child = id_child;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFlag_question() {
        return flag_question;
    }

    public void setFlag_question(String flag_question) {
        this.flag_question = flag_question;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getId_driver() {
        return id_driver;
    }

    public void setId_driver(String id_driver) {
        this.id_driver = id_driver;
    }

    public String getId_question() {
        return id_question;
    }

    public void setId_question(String id_question) {
        this.id_question = id_question;
    }

    public String getImage_question() {
        return image_question;
    }

    public void setImage_question(String image_question) {
        this.image_question = image_question;
    }

    public String getName_driver() {
        return name_driver;
    }

    public void setName_driver(String name_driver) {
        this.name_driver = name_driver;
    }

    public String getRemark_question() {
        return remark_question;
    }

    public void setRemark_question(String remark_question) {
        this.remark_question = remark_question;
    }

    public String getTime_create() {
        return time_create;
    }

    public void setTime_create(String time_create) {
        this.time_create = time_create;
    }

}
