package com.yichang.kaku.tools.okhttp;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.yichang.kaku.BuildConfig;
import com.yichang.kaku.response.BaseResp;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Created by 小苏 on 2015/10/19.
 */
public class OkHttpUtil {

    private static final OkHttpClient mOkHttpClient = new OkHttpClient();
    public static Handler handler;

    public static void init(Handler handler) {
        OkHttpUtil.handler = handler;
    }

    public static void getAsync(String url, RequestCallback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    public static Call postAsync(String url, Params params, RequestCallback<? extends BaseResp> callback) {

        Set<Map.Entry<String, String>> entrySet = params.entrySet();
        FormEncodingBuilder builder = new FormEncodingBuilder();

        for (Iterator<Map.Entry<String, String>> iterator = entrySet.iterator(); iterator.hasNext(); ) {
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if (TextUtils.isEmpty(value)) {
                value = "";
            }
            builder.add(key, value);
            if (BuildConfig.DEBUG) {
                Log.d("xiaosu", key + " = " + value);
            }
        }

        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);

        return call;
    }

    public static Call postAsync(String url, Params.builder builder, RequestCallback<? extends BaseResp> callback) {
        return postAsync(url, builder.build(), callback);
    }

}
