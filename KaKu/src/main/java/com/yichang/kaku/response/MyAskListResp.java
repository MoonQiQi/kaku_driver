package com.yichang.kaku.response;

import java.util.List;

/**
 * Created by xiaosu on 2015/11/17.
 */
public class MyAskListResp extends BaseResp {


    /**
     * name_driver :
     * id_driver : 25
     * head : head/statues.jpg
     */

    public UserEntity user;
    /**
     * id_question : 1
     * content : 机油滤芯的型号
     * image_question :
     * time_create : 2015-11-11 11:45:11
     * flag_question : Y
     * count : 2
     */

    public List<ListEntity> list;

    public static class UserEntity {
        public String name_driver;
        public String id_driver;
        public String head;

    }

    public static class ListEntity {
        public String id_question;
        public String content;
        public String image_question;
        public String time_create;
        public String flag_question;
        public String count;
    }
}
