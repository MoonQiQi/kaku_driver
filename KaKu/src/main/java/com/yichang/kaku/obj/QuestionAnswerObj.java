package com.yichang.kaku.obj;

import java.io.Serializable;
import java.util.List;

public class QuestionAnswerObj implements Serializable {

    private String content;
    private String count;
    private String head_user;
    private String id_question;
    private String id_shop_user;
    private String image_question;
    private String name_user;
    private List<QuestionAnswerItemObj> question_list;
    private String remark_question;
    private String time_create;

    @Override
    public String toString() {
        return "QuestionAnswerObj{" +
                "content='" + content + '\'' +
                ", count='" + count + '\'' +
                ", head_user='" + head_user + '\'' +
                ", id_question='" + id_question + '\'' +
                ", id_shop_user='" + id_shop_user + '\'' +
                ", image_question='" + image_question + '\'' +
                ", name_user='" + name_user + '\'' +
                ", question_list=" + question_list +
                ", remark_question='" + remark_question + '\'' +
                ", time_create='" + time_create + '\'' +
                '}';
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


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getHead_user() {
        return head_user;
    }

    public void setHead_user(String head_user) {
        this.head_user = head_user;
    }

    public String getId_question() {
        return id_question;
    }

    public void setId_question(String id_question) {
        this.id_question = id_question;
    }

    public String getId_shop_user() {
        return id_shop_user;
    }

    public void setId_shop_user(String id_shop_user) {
        this.id_shop_user = id_shop_user;
    }

    public String getImage_question() {
        return image_question;
    }

    public void setImage_question(String image_question) {
        this.image_question = image_question;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public List<QuestionAnswerItemObj> getQuestion_list() {
        return question_list;
    }

    public void setQuestion_list(List<QuestionAnswerItemObj> question_list) {
        this.question_list = question_list;
    }
}
