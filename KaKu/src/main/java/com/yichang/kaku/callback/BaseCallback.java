package com.yichang.kaku.callback;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yichang.kaku.global.MyActivityManager;
import com.yichang.kaku.response.BaseResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;

import org.apache.http.Header;

public abstract class BaseCallback<T> extends AsyncHttpResponseHandler {
    protected Class clazz;

    protected BaseCallback(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        //super.onSuccess(statusCode, headers, responseBody);
        T t = null;
        if (responseBody != null && responseBody.length > 0) {
            String json = new String(responseBody);

            t = (T) Json2ObjHelper.gson.fromJson(json, clazz);
            //如果是违章查询接口，则不判定，违章查询接口未实现BaseResp,未实现BaseResp的接口全部不判定
            if(!"com.yichang.kaku.response.BaseResp".equals(clazz.getSuperclass().getName())){
                onSuccessful(statusCode, headers, t);
            }else {

                if(((BaseResp)t).res!=null){

                    Log.e("chaih res", ((BaseResp) t).res);
                }

                if("10".equals(((BaseResp)t).res)){
                    //当前账号在其他设备登陆，显示登陆页面
                    Utils.Exit();
                    MyActivityManager.getInstance().finishCurrentActivity();
                }else if("11".equals(((BaseResp)t).res)){
                    //接口访问无效，不进行任何操作

                }else {
                    onSuccessful(statusCode, headers, t);
                }
            }
        } else {
            onSuccessful(statusCode, headers, t);
            LogUtil.E(" responseBody is NULL");
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        //super.onFailure(statusCode, headers, responseBody, error);
        String msg = "";
        if (responseBody != null && responseBody.length > 0) {
            msg = new String(responseBody);
        } else {
            msg = error.getMessage();
        }
        onFailure(statusCode, headers, msg, error);
    }

    public abstract void onSuccessful(int statusCode, Header[] headers, T t);

    public abstract void onFailure(int statusCode, Header[] headers, String msg, Throwable error);

}
