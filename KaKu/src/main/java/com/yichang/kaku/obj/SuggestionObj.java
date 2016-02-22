package com.yichang.kaku.obj;

import java.io.Serializable;

public class SuggestionObj implements Serializable {
    private String create_time;
    private String content_suggest;
    private String type_suggest;
    private String reply_content;
    private String reply_time;

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public String getReply_time() {
        return reply_time;
    }

    public void setReply_time(String reply_time) {
        this.reply_time = reply_time;
    }

    @Override
    public String toString() {
        return "SuggestionObj{" +
                "create_time='" + create_time + '\'' +
                ", content_suggest='" + content_suggest + '\'' +
                ", type_suggest='" + type_suggest + '\'' +
                '}';
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getContent_suggest() {
        return content_suggest;
    }

    public void setContent_suggest(String content_suggest) {
        this.content_suggest = content_suggest;
    }

    public String getType_suggest() {
        return type_suggest;
    }

    public void setType_suggest(String type_suggest) {
        this.type_suggest = type_suggest;
    }
}
