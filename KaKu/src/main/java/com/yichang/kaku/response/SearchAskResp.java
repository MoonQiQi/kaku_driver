package com.yichang.kaku.response;

import java.util.List;

/**
 * Created by xiaosu on 2015/11/19.
 */
public class SearchAskResp extends BaseResp {

    public List<Entity> list;

    public static class Entity {
        public String id_question;
        public String content;
        public String time_create;
    }

}
