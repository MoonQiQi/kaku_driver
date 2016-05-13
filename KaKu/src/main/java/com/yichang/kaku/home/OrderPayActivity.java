package com.yichang.kaku.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.yichang.kaku.member.truckorder.TruckOrderListActivity;
import com.yichang.kaku.payhelper.alipay.PaySuccessActivity;
import com.yichang.kaku.request.OrderOverTimeReq;
import com.yichang.kaku.request.OrderTimeLimitReq;
import com.yichang.kaku.response.OrderOverTimeResp;
import com.yichang.kaku.response.OrderTimeLimitResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class OrderPayActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;

    private TextView tv_price_bill;
    private RelativeLayout rela_alipay, rela_wxpay;
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

    //支付的方法参数
    final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);

    private String mPrice_bill;

    private String mNo_bill;

    private TextView tv_order_invalid_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);
        init();

    }

    private void getOrderTimeLimit() {
        Utils.NoNet(context);
        showProgressDialog();

        OrderTimeLimitReq req = new OrderTimeLimitReq();
        req.code = "80013";
        req.no_bill = mNo_bill;

        KaKuApiProvider.getOrderTimeLimit(req, new KakuResponseListener<OrderTimeLimitResp>(this, OrderTimeLimitResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getDriverInfo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {

                        tv_order_invalid_time.setText("请在" + t.time_limit + "内完成支付，否则订单会失效哦~");
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    private void init() {
        initTitleBar();
        KaKuApplication.payType = "TRUCK";
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

        tv_order_invalid_time = (TextView) findViewById(R.id.tv_order_invalid_time);

        getOrderState();
    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("卡库收银台");

        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.GONE);
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
                    gotoTruckOrderListActivity("");
                }
            });
            builder.create().show();
        } else if (R.id.tv_right == id) {
            /*todo 转到订单列表*/
            gotoTruckOrderListActivity("");

        } else if (R.id.rela_alipay == id) {
            //aliPay();
            new PaymentTask().execute(new PaymentRequest(CHANNEL_ALIPAY, mPrice_bill, mNo_bill));

        } else if (R.id.rela_wxpay == id) {
            //wxPay();
            new PaymentTask().execute(new PaymentRequest(CHANNEL_WECHAT, mPrice_bill, mNo_bill));
        }
    }

    private void getOrderState() {
        Utils.NoNet(context);

        OrderOverTimeReq req = new OrderOverTimeReq();
        req.code = "80011";
        req.no_bill = mNo_bill;

        KaKuApiProvider.isOrderOverTime(req, new KakuResponseListener<OrderOverTimeResp>(this, OrderOverTimeResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("isOrderOverTime res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        getOrderTimeLimit();
                    } else if (Constants.RES_ONE.equals(t.res)) {
                        //秒杀订单超时失效
                        LogUtil.showShortToast(context, t.msg);
                        Timer timer = new Timer();
                        timer.schedule(task, 2000);

                    }
                } else {
                    LogUtil.showShortToast(context, t.msg);
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });


    }

    TimerTask task = new TimerTask() {
        public void run() {
            stopProgressDialog();
            gotoTruckOrderListActivity("");
            finish();
        }
    };

    private void gotoTruckOrderListActivity(String state) {
        Intent intent = new Intent(context, TruckOrderListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        KaKuApplication.truck_order_state = state;
        //intent.putExtra("state", "");
        startActivity(intent);
        finish();
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
            Pingpp.createPayment(OrderPayActivity.this, data, "qwalletXXXXXXX");
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
                    startActivity(new Intent(OrderPayActivity.this, PaySuccessActivity.class));
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
                    gotoTruckOrderListActivity("");
                }
            });
            builder.create().show();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
