package com.yichang.kaku.obj;

import java.io.Serializable;

public class DiscoveryItemObj implements Serializable {

    private String id_news, name_news, intro_news, time_pub, num_collection, num_comments, thumbnail_news_new, is_collection, num_read, thumbnail_news;

    public String getId_news() {
        return id_news;
    }

    public void setId_news(String id_news) {
        this.id_news = id_news;
    }

    public String getName_news() {
        return name_news;
    }

    public void setName_news(String name_news) {
        this.name_news = name_news;
    }

    public String getIntro_news() {
        return intro_news;
    }

    public void setIntro_news(String intro_news) {
        this.intro_news = intro_news;
    }

    public String getTime_pub() {
        return time_pub;
    }

    public void setTime_pub(String time_pub) {
        this.time_pub = time_pub;
    }

    public String getNum_collection() {
        return num_collection;
    }

    public void setNum_collection(String num_collection) {
        this.num_collection = num_collection;
    }

    public String getNum_comments() {
        return num_comments;
    }

    public void setNum_comments(String num_comments) {
        this.num_comments = num_comments;
    }

    public String getIs_collection() {
        return is_collection;
    }

    public String getNum_read() {
        return num_read;
    }

    public void setNum_read(String num_read) {
        this.num_read = num_read;
    }

    public void setIs_collection(String is_collection) {

        this.is_collection = is_collection;
    }

    public String getThumbnail_news_new() {
        return thumbnail_news_new;
    }

    public void setThumbnail_news_new(String thumbnail_news_new) {
        this.thumbnail_news_new = thumbnail_news_new;
    }

    public String getThumbnail_news() {
        return thumbnail_news;
    }

    public void setThumbnail_news(String thumbnail_news) {
        this.thumbnail_news = thumbnail_news;
    }

    @Override
    public String toString() {
        return "DiscoveryItemObj{" +
                "id_news='" + id_news + '\'' +
                ", name_news='" + name_news + '\'' +
                ", intro_news='" + intro_news + '\'' +
                ", time_pub='" + time_pub + '\'' +
                ", num_collection='" + num_collection + '\'' +
                ", num_comments='" + num_comments + '\'' +
                ", thumbnail_news_new='" + thumbnail_news_new + '\'' +
                ", is_collection='" + is_collection + '\'' +
                ", num_read='" + num_read + '\'' +
                ", thumbnail_news='" + thumbnail_news + '\'' +
                '}';
    }
}
