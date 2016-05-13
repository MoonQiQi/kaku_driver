package com.yichang.kaku.callback;

import android.content.Context;

import com.yichang.kaku.global.MyActivityManager;
import com.yichang.kaku.response.BaseResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Response;

/**
 * Created by chaih on 2016/2/18.
 * 网络请求回调接口，用于处理网络请求回调
 * 所有网络进度条依托于网络请求，不再使用BaseActivity中的进度条。
 */
public abstract class KakuResponseListener<T> implements OnResponseListener {

    private Class mClass;
    private Context mContext;
    protected T t;

    public KakuResponseListener(Context context, Class clazz) {

        mClass = clazz;
        mContext = context;
    }

    @Override
    public void onStart(int what) {
        Utils.NoNet(mContext);
    }

    @Override
    public void onSucceed(int what, Response response) {
        String result = response.get().toString();
        t = (T) Json2ObjHelper.gson.fromJson(result, mClass);
        if ("10".equals(((BaseResp) t).res)) {
            //当前账号在其他设备登陆，跳转到登陆页面
            Utils.Exit();
            LogUtil.showShortToast(mContext, ((BaseResp) t).msg.toString());
            MyActivityManager.getInstance().finishCurrentActivity();
        } else if ("11".equals(((BaseResp) t).res)) {
            //接口访问无效，不进行任何操作
            t = null;
            return;
        }

    }

    @Override
    public void onFailed(int i, Response response) {

    }

    @Override
    public void onFinish(int what) {

    }

}
