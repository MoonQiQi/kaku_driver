package com.yichang.kaku.obj;

import java.io.Serializable;

public class Home4Obj implements Serializable {

    private String image;
    private String url;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Home4Obj{" +
                "image='" + image + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
