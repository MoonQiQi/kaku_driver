package com.yichang.kaku.home.baoyang;

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
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.payhelper.alipay.PaySuccessActivity;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class OrderBaoYangPayActivity extends BaseActivity implements OnClickListener {

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
        KaKuApplication.payType = "SERVICE";
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
                    KaKuApplication.state_order = "";
                    gotoServiceOrderListActivity();
                }
            });
            builder.create().show();
        } else if (R.id.tv_right == id) {
            /*todo 转到订单列表*/
            gotoServiceOrderListActivity();

        } else if (R.id.rela_alipay == id) {
            new PaymentTask().execute(new PaymentRequest(CHANNEL_ALIPAY, mPrice_bill, mNo_bill));
        } else if (R.id.rela_wxpay == id) {
            new PaymentTask().execute(new PaymentRequest(CHANNEL_WECHAT, mPrice_bill, mNo_bill));
        }
    }

    private void gotoServiceOrderListActivity() {
        Intent intent = new Intent(context, BaoYangOrderListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
            Pingpp.createPayment(OrderBaoYangPayActivity.this, data, "qwalletXXXXXXX");
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
                    startActivity(new Intent(OrderBaoYangPayActivity.this, PaySuccessActivity.class));
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
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
                    KaKuApplication.state_order = "";
                    gotoServiceOrderListActivity();
                }
            });
            builder.create().show();
        }
        return false;
    }
}
