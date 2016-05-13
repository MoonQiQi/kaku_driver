package com.yichang.kaku.webService;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.RequestQueue;

/**
 * Created by chaih on 2016/2/18.
 * 单例 返回一个httpConnectQueue，所有网络请求都添加进唯一一个链接队列
 */
public class HttpContentQueue {
    private HttpContentQueue(){}

    private static final RequestQueue httpContentQueue= NoHttp.newRequestQueue();

    public static RequestQueue getHttpContentQueue(){
        return  httpContentQueue;
    }



}
