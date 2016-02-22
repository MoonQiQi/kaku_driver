package com.yichang.kaku.response;

import android.support.annotation.IntDef;

import java.util.LinkedList;

/**
 * Created by xiaosu on 2015/11/13.
 */
public class ConversationBean extends BaseResp {

    /*等待状态*/
    public final static int STATE_WAITING = 0;
    /*发送成功*/
    public final static int STATE_SUC = 1;
    /*发送失败*/
    public final static int STATE_FAIL = 2;

    public LinkedList<conversationItem> list;
    public String flag;

    public static class conversationItem {

        public String content;
        public String time_create;
        public int id_driver;
        public String id_shop_user;
        public String type;
        public String image;
    }

    @IntDef({STATE_WAITING, STATE_SUC, STATE_FAIL})
    public @interface State {
    }

}
