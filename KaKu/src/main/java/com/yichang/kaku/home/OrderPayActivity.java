package com.yichang.kaku.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.member.truckorder.TruckOrderListActivity;
import com.yichang.kaku.payhelper.alipay.AlipayHelper;
import com.yichang.kaku.payhelper.wxpay.PayActivity;
import com.yichang.kaku.request.OrderOverTimeReq;
import com.yichang.kaku.request.OrderTimeLimitReq;
import com.yichang.kaku.request.WXPayInfoReq;
import com.yichang.kaku.response.OrderOverTimeResp;
import com.yichang.kaku.response.OrderTimeLimitResp;
import com.yichang.kaku.response.WXPayInfoResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.Timer;
import java.util.TimerTask;

public class OrderPayActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;

    private TextView tv_price_bill;
    private RelativeLayout rela_alipay, rela_wxpay;

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

        KaKuApiProvider.getOrderTimeLimit(req, new BaseCallback<OrderTimeLimitResp>(OrderTimeLimitResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, OrderTimeLimitResp t) {
                if (t != null) {
                    LogUtil.E("getDriverInfo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {

                        tv_order_invalid_time.setText("请在" + t.time_limit + "内完成支付，否则订单会失效哦~");
                    } else {
                        if (Constants.RES_TEN.equals(t.res)){
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });
    }

    private void init() {
        initTitleBar();

        mPrice_bill = getIntent().getStringExtra("price_bill");
        mNo_bill = getIntent().getStringExtra("no_bill");
        //为支付完成页赋值
        KaKuApplication.realPayment = mPrice_bill;

        tv_price_bill = (TextView) findViewById(R.id.tv_price_bill);
        tv_price_bill.setText("￥" + mPrice_bill);

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
            builder.setNegativeButton("继续支付", new android.content.DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.setPositiveButton("放弃支付", new android.content.DialogInterface.OnClickListener() {

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
            //payType = "alipay";
            aliPay();

        } else if (R.id.rela_wxpay == id) {
            //payType = "wxpay";
            //getOrderState();
            wxPay();
        }
    }

    private void getOrderState() {
        Utils.NoNet(context);
        showProgressDialog();

        OrderOverTimeReq req = new OrderOverTimeReq();
        req.code = "80011";
        req.no_bill = mNo_bill;

        KaKuApiProvider.isOrderOverTime(req, new BaseCallback<OrderOverTimeResp>(OrderOverTimeResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, OrderOverTimeResp t) {
                if (t != null) {
                    LogUtil.E("isOrderOverTime res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        ///可以支付

                       /* switch (payType) {
                            case "alipay":
                                aliPay();
                                break;
                            case "wxpay":
                        wxPay();
                        break;

                    }*/
                        getOrderTimeLimit();
                    } else if (Constants.RES_ONE.equals(t.res)) {
                        //秒杀订单超时失效
                        LogUtil.showShortToast(context, t.msg);
                        Timer timer = new Timer();
                        timer.schedule(task, 2000);

                        //finish();
                    }
                } else {
                    if (Constants.RES_TEN.equals(t.res)){
                        Utils.Exit(context);
                        finish();
                    }
                    LogUtil.showShortToast(context, t.msg);
                    stopProgressDialog();
                }
                //stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
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

    //支付类型 alipay，wxpay
    private String payType;

    private void gotoTruckOrderListActivity(String state) {
        Intent intent = new Intent(context, TruckOrderListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        KaKuApplication.truck_order_state = state;
        //intent.putExtra("state", "");
        startActivity(intent);
        finish();
    }

    private void wxPay() {

        KaKuApplication.payType = "TRUCK";
        Utils.NoNet(context);
        showProgressDialog();

        WXPayInfoReq req = new WXPayInfoReq();
        req.code = "30021";
        req.no_bill = mNo_bill;
        //req.fee

        KaKuApiProvider.getWXPayInfo(req, new BaseCallback<WXPayInfoResp>(WXPayInfoResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, WXPayInfoResp t) {

                if (t != null) {
                    LogUtil.E("getWXPayInfo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        wxPay(t);
                    } else if (Constants.RES_TWO.equals(t.res)) {
                        //秒杀订单失效

                    } else {
                        //发起支付失败
                        LogUtil.showShortToast(context, t.msg);
                        gotoTruckOrderListActivity("");
                        finish();
                    }
                    //LogUtil.showShortToast(context, t.msg);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
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

        Utils.NoNet(context);
        showProgressDialog();

        OrderOverTimeReq req = new OrderOverTimeReq();
        req.code = "80011";
        req.no_bill = mNo_bill;

        KaKuApiProvider.isOrderOverTime(req, new BaseCallback<OrderOverTimeResp>(OrderOverTimeResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, OrderOverTimeResp t) {
                if (t != null) {
                    LogUtil.E("isOrderOverTime res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.payType = "TRUCK";
                        //支付宝支付
                        AlipayHelper helper = new AlipayHelper(OrderPayActivity.this);
                        //todo dou
                        helper.pay("卡库养车" + mNo_bill, "车品订单", mPrice_bill, mNo_bill);

                    } else if (Constants.RES_ONE.equals(t.res)) {
                        //秒杀订单超时失效
                        LogUtil.showShortToast(context, t.msg);
                        Timer timer = new Timer();
                        timer.schedule(task, 2000);

                        //finish();
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("");
            builder.setMessage("确认要放弃付款吗？");
            builder.setNegativeButton("继续支付", new android.content.DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.setPositiveButton("放弃支付", new android.content.DialogInterface.OnClickListener() {

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
