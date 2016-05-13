package com.yichang.kaku.home.ad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pingplusplus.android.Pingpp;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.payhelper.alipay.AlipayHelper;
import com.yichang.kaku.payhelper.alipay.PaySuccessActivity;
import com.yichang.kaku.payhelper.wxpay.PayActivity;
import com.yichang.kaku.request.WXPayInfoReq;
import com.yichang.kaku.response.WXPayInfoResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class OrderCheTiePayActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;

    private TextView tv_price_bill;
    private RelativeLayout rela_alipay, rela_wxpay;

    //支付的方法参数
    final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);

    private String mPrice_bill;

    private String mNo_bill;

    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_QPAY = "qpay";
    /**
     * 支付宝支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";
    private LinearLayout ll_warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);
        init();

    }


    private void init() {
        initTitleBar();
        KaKuApplication.payType = "STICKER";
        mPrice_bill = getIntent().getStringExtra("price_bill");
        mNo_bill = getIntent().getStringExtra("no_bill");
        //为支付完成页赋值
        KaKuApplication.realPayment = mPrice_bill;

        tv_price_bill = (TextView) findViewById(R.id.tv_price_bill);
        tv_price_bill.setText("￥" + Utils.numdouble(mPrice_bill));

        rela_alipay = (RelativeLayout) findViewById(R.id.rela_alipay);
        rela_alipay.setOnClickListener(this);
        rela_wxpay = (RelativeLayout) findViewById(R.id.rela_wxpay);
        rela_wxpay.setOnClickListener(this);


        ll_warning = (LinearLayout) findViewById(R.id.ll_warning);
        ll_warning.setVisibility(View.GONE);

    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("卡库收银台");

        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("订单中心");
        right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("");
            builder.setMessage("确认要放弃付款吗？");
            builder.setNegativeButton("继续支付", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.setPositiveButton("放弃支付", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO 转到订单列表
                    gotoCheTieOrderListActivity();
                }
            });
            builder.create().show();
        } else if (R.id.tv_right == id) {
            /*todo 转到订单列表*/
            gotoCheTieOrderListActivity();

        } else if (R.id.rela_alipay == id) {
            //payType = "alipay";
            //aliPay();
            new PaymentTask().execute(new PaymentRequest(CHANNEL_ALIPAY, mPrice_bill, mNo_bill));
        } else if (R.id.rela_wxpay == id) {
            //payType = "wxpay";
            //getOrderState();
            //wxPay();
            new PaymentTask().execute(new PaymentRequest(CHANNEL_WECHAT, mPrice_bill, mNo_bill));
        }
    }

    private void gotoCheTieOrderListActivity() {
        Intent intent = new Intent(context, CheTieOrderListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    private void wxPay() {

        Utils.NoNet(context);

        WXPayInfoReq req = new WXPayInfoReq();
        req.code = "30021";
        req.no_bill = mNo_bill;

        KaKuApiProvider.getWXPayInfo(req, new KakuResponseListener<WXPayInfoResp>(this, WXPayInfoResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);

                if (t != null) {
                    LogUtil.E("getWXPayInfo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        wxPay(t);
                    } else {
                        //发起支付失败
                        LogUtil.showShortToast(context, t.msg);
                        gotoCheTieOrderListActivity();
                    }
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    private void wxPay(WXPayInfoResp params) {

        if (!msgApi.isWXAppInstalled()) {
            LogUtil.showShortToast(context, "您尚未安装微信客户端");
            //finish();
            return;
        }
        if (!msgApi.isWXAppSupportAPI()) {
            LogUtil.showShortToast(context, "您的微信版本不支持微信支付");
            //finish();
            return;
        }
        /*todo 确定idorder by123456123*/
        KaKuApplication.id_bill = mNo_bill;

        LogUtil.E("支付参数" + params.toString());
        Intent intent = new Intent(context, PayActivity.class);

        //intent.putExtra("no_order", params.no_bill);
        intent.putExtra("appid", params.appid);
        intent.putExtra("noncestr", params.noncestr);
        intent.putExtra("package", params.package0);
        intent.putExtra("partnerid", params.partnerid);
        intent.putExtra("prepayid", params.prepay_id);
        intent.putExtra("timestamp", params.timestamp);
        intent.putExtra("sign", params.sign);


        try {
            stopProgressDialog();
            context.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //支付宝支付参数


    private void aliPay() {
        //支付宝支付
        AlipayHelper helper = new AlipayHelper(OrderCheTiePayActivity.this);
        //todo dou
        helper.pay("卡库养车" + mNo_bill, "车贴订单", mPrice_bill, mNo_bill);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("");
            builder.setMessage("确认要放弃付款吗？");
            builder.setNegativeButton("继续支付", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.setPositiveButton("放弃支付", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO 转到订单列表
                    gotoCheTieOrderListActivity();
                }
            });
            builder.create().show();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    class PaymentTask extends AsyncTask<PaymentRequest, Void, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(PaymentRequest... pr) {

            PaymentRequest paymentRequest = pr[0];
            String data = null;
            String json = new Gson().toJson(paymentRequest);
            try {
                //向Your Ping++ Server SDK请求数据
                data = postJson(KaKuApplication.ping_url, json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        /**
         * 获得服务端的charge，调用ping++ sdk。
         */
        @Override
        protected void onPostExecute(String data) {
            if (null == data) {
                return;
            }
            try {
                JSONObject object = new JSONObject(data);
                Object charges = object.get("Charges");
                data = String.valueOf(charges);
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            Pingpp.createPayment(ClientSDKActivity.this, data);
            //QQ钱包调起支付方式  “qwalletXXXXXXX”需与AndroidManifest.xml中的data值一致
            //建议填写规则:qwallet + APP_ID
            Pingpp.createPayment(OrderCheTiePayActivity.this, data, "qwalletXXXXXXX");
        }

    }

    /**
     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
     * 最终支付成功根据异步通知为准
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                LogUtil.E("pay_result:" + result);
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                if ("success".equals(result)) {
                    startActivity(new Intent(OrderCheTiePayActivity.this, PaySuccessActivity.class));
                    finish();
                }
            }
        }
    }

    private static String postJson(String url, String json) throws IOException {
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(type, json);
        Request request = new Request.Builder().url(url).post(body).build();

        OkHttpClient client = new OkHttpClient();
        com.squareup.okhttp.Response response = client.newCall(request).execute();

        return response.body().string();
    }

    class PaymentRequest {
        String channel;
        String amount;
        String orderNo;

        public PaymentRequest(String channel, String amount, String orderNo) {
            this.channel = channel;
            this.amount = amount;
            this.orderNo = orderNo;
        }
    }
}
