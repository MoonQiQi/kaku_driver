package com.yichang.kaku.webService;

import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.request.BaseReq;
import com.yichang.kaku.tools.LogUtil;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;

import java.lang.reflect.Field;

/**
 * Created by chaih on 2016/2/18.
 * 网络请求方法
 *
 */

public abstract class WebApiProvider {
/**
 * @param req 网络请求类
 * @param baseip 网络请求地址
 * @param resp 网络请求回调接口
 * */
    public static void postRequest(BaseReq req, String baseip, KakuResponseListener resp) {
        //建立String网络请求对象
        Request<String> request = NoHttp.createStringRequest(baseip, RequestMethod.POST);
//        解析req对象父类对象中的字段，并添加到request中
        for (Field field:req.getClass().getSuperclass().getDeclaredFields()) {
            String sValue= "";
            try {
                if (field.get(req) != null) {
                    sValue = field.get(req).toString();
                    LogUtil.W(field.getName() + ":" + sValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            request.add(field.getName(), sValue);
        }
        //解析req对象中的字段，并添加到request中
        for (Field field:req.getClass().getDeclaredFields()) {
            String sValue= "";
            try {
                if (field.get(req) != null){
                    sValue = field.get(req).toString();
                    LogUtil.W(field.getName()+":"+sValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            request.add(field.getName(),sValue);
        }
        request.setConnectTimeout(20000);
        //在请求队列中添加网络请求
        HttpContentQueue.getHttpContentQueue().add(0, request, resp);
        }
}
