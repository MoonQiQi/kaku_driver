package com.yichang.kaku.callback;

import android.content.Context;

import com.yichang.kaku.global.MyActivityManager;
import com.yichang.kaku.response.BaseResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.WaitDialog;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Response;

/**
 * Created by chaih on 2016/2/18.
 * 网络请求回调接口，用于处理网络请求回调
 * 所有网络进度条依托于网络请求，不再使用BaseActivity中的进度条。
 */
public abstract  class KakuResponseListener<T> implements OnResponseListener {

//    类的class
    private Class mClass;
//    上下文对象，用于构造进度条
    private Context mContext;
//    父类中构造一个参数t，类型为传入的mClass,在实现类中直接使用t进行赋值等操作即可
    protected T t;
    public WaitDialog mWaitDialog;
    private boolean isShowDialog=true;
//控制进度条是否显示，传入false则不显示进度条
    private void setIsShowDialog(boolean isShow){
        isShowDialog=isShow;
    }

    public KakuResponseListener(Context context, Class clazz){

        mClass=clazz;
        mContext=context;
    }

    @Override
    public void onStart(int what) {
        Utils.NoNet(mContext);
        /*mWaitDialog = WaitDialog.getWdialog(mContext);
        mWaitDialog.setCancelable(false);
        mWaitDialog.setCanceledOnTouchOutside(false);
        mWaitDialog.show();*/
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
        }  else if("11".equals(((BaseResp)t).res)){
            //接口访问无效，不进行任何操作
            //子类中判断if (t!=null),否则不能进行任何操作
            t = null;
            return;
        }

    }

    @Override
    public void onFailed(int what, String url, Object t, CharSequence message, int responseCode, long networkMillis) {
        //重新加载数据提示框
    }

    @Override
    public void onFinish(int what) {
        /*if (mWaitDialog.isShowing())
            mWaitDialog.dismiss();*/
    }

}
