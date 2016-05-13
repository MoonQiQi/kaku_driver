package com.yichang.kaku.tools.okhttp;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.response.BaseResp;

import java.io.IOException;


public abstract class RequestCallback<T extends BaseResp> implements Callback {

    private Class<? extends BaseResp> bean;

    private BaseActivity activity;

    public RequestCallback(BaseActivity activity, Class<? extends BaseResp> bean) {
        this.activity = activity;
        this.bean = bean;
    }

    @Override
    public void onFailure(final Request request, final IOException e) {
        OkHttpUtil.handler.post(new Runnable() {
            @Override
            public void run() {
                onInnerFailure(request, e);
            }
        });
    }

    @Override
    public void onResponse(Response response) throws IOException {

        final String body = response.body().string();
        try {
            final T obj = (T) new Gson().fromJson(body, bean);
            if (null == obj.res) {
                OkHttpUtil.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onSuccess1(obj, body);
                    }
                });
            } else {
                if (obj.res.equals("0")) {
                    OkHttpUtil.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onSuccess(obj, body);
                        }
                    });
                } else {
                    OkHttpUtil.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onResError(obj, body);
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            OkHttpUtil.handler.post(new Runnable() {
                @Override
                public void run() {
                    onDataParseError(body);
                }
            });
        }
    }

    public abstract void onSuccess(T obj, String result);

    public void onSuccess1(T obj, String result){};

    public abstract void onInnerFailure(Request request, IOException e);

    public void onResError(T obj, String result) {
        activity.showShortToast(obj.msg);
        switch (obj.res) {
            case "1":
                activity.stopProgressDialog();
//                activity.showShortToast(obj.msg);
                break;
            case "2":
                activity.stopProgressDialog();
//                activity.showShortToast(obj.msg);
                break;
            case "3":
                activity.stopProgressDialog();
//                activity.showShortToast(obj.msg);
                break;
            case "4":
                activity.stopProgressDialog();
//                activity.showShortToast(obj.msg);
                break;
            case "10":
                activity.stopProgressDialog();
//                activity.showShortToast(obj.msg);
                break;
            case "100":
                activity.stopProgressDialog();
//                activity.showShortToast(obj.msg);
                break;
        }
    }

    public void onDataParseError(String result) {
        activity.stopProgressDialog();
        activity.showShortToast("网络不好，请稍后再试");
    }
}
