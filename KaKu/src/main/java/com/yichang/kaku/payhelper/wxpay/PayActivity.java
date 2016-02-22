package com.yichang.kaku.payhelper.wxpay;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.payhelper.wxpay.net.sourceforge.simcpux.MD5;
import com.yichang.kaku.tools.LogUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class PayActivity extends BaseActivity {

    private static final String TAG = "MicroMsg.SDKSample.PayActivity";

    PayReq req;
    final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);


//    private String no_order;
    private String appid;
    private String noncestr;
    private String package0;
    private String partnerid;
    private String prepay_id;
    private String timestamp;
    private String sign;

    StringBuffer sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showProgressDialog();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);

/*        if(!msgApi.isWXAppInstalled()){
            LogUtil.showShortToast(getApplicationContext(),"您尚未安装微信客户端");
            finish();
            return;
        }
        if(!msgApi.isWXAppSupportAPI()){
            LogUtil.showShortToast(getApplicationContext(),"您的微信版本不支持微信支付");
            finish();
            return;
        }*/


        //TODO 获取页面传递的数据
        getPackageParams();


//初始化
        req = new PayReq();
        sb = new StringBuffer();
//注册OpenAPI
        msgApi.registerApp(Constants.APP_ID);
        //异步获取PrepayId
        GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
        getPrepayId.execute();



    }

    private void getPackageParams() {
//        no_order=getIntent().getStringExtra("no_order").toString().trim();
        appid=getIntent().getStringExtra("appid").toString().trim();
        KaKuApplication.APP_ID=appid;
        noncestr=getIntent().getStringExtra("noncestr").toString().trim();
        package0=getIntent().getStringExtra("package").toString().trim();
        partnerid=getIntent().getStringExtra("partnerid").toString().trim();
        prepay_id=getIntent().getStringExtra("prepayid").toString().trim();
        timestamp=getIntent().getStringExtra("timestamp").toString().trim();
        sign=getIntent().getStringExtra("sign").toString().trim();

    }




    private class GetPrepayIdTask extends AsyncTask<Void, Void, Void> {

       //private ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
           // dialog = ProgressDialog.show(PayActivity.this, getString(R.string.app_tip), "微信支付页面跳转中...");
        }

        @Override
        protected void onPostExecute(Void params) {
            sendPayReq();

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... params) {
            genPayReq();
            return null;
        }
    }

    private void genPayReq() {

        req.appId = appid;
        req.partnerId = partnerid;
        req.prepayId = prepay_id;
        req.packageValue = package0;
        req.nonceStr = noncestr;
        req.timeStamp = timestamp;
        /*req.sign = sign;*/
       /* req.nonceStr= genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());*/

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);


    }
    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion", appSign);
        return appSign;
    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }


    private void sendPayReq() {
        msgApi.registerApp(Constants.APP_ID);
        msgApi.sendReq(req);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        stopProgressDialog();
        finish();
        super.onDestroy();
    }
}

