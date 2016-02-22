package com.yichang.kaku.response;

import java.util.List;

/**
 * Created by xiaosu on 2015/11/16.
 */
public class AskMianResp extends BaseResp {

    /**
     * name_driver :
     * head : head/statues.jpg
     * id_question : 1
     * content : 机油滤芯的型号
     * id_child : 2
     * image_question :
     * time_create : 2015-11-11 11:45:11
     * reply : {"name_user":"汪峰","head_user":"brand/1437113937.png","content":"不是问题","image_question":"","time_create":"2015-11-11 18:21:06"}
     */

    public List<ListEntity> list;

    public static class ListEntity {
        public String name_driver;
        public String head;
        public String id_question;
        public String content;
        public String id_child;
        public String image_question;
        public String time_create;
        public NumEntity num;
        /**
         * name_user : 汪峰
         * head_user : brand/1437113937.png
         * content : 不是问题
         * image_question :
         * time_create : 2015-11-11 18:21:06
         */

        public ReplyEntity reply;

        public static class ReplyEntity {
            public String name_user;
            public String head_user;
            public String content;
            public String image_question;
            public String time_create;
        }

        public static class NumEntity {
            public String num;
            public String person;
        }
    }
}
