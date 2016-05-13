package com.yichang.kaku.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.home.ad.CheTieOrderListActivity;
import com.yichang.kaku.home.baoyang.BaoYangOrderListActivity;
import com.yichang.kaku.member.truckorder.TruckOrderListActivity;
import com.yichang.kaku.tools.Utils;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    //titleBar
    private TextView left, right, title;
    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;
    TextView textView, tv_price;
    private Button btn_result_list,btn_result_home;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        textView = (TextView) this.findViewById(R.id.tv_payresult_info);
        tv_price = (TextView) this.findViewById(R.id.tv_price);

        tv_price.setText("￥" + KaKuApplication.realPayment);

        btn_result_list = (Button) findViewById(R.id.btn_result_list);
        btn_result_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (KaKuApplication.payType.equals("SERVICE")) {
                    gotoShopListActivity("");
                    KaKuApplication.payType = "";
                } else if (KaKuApplication.payType.equals("TRUCK")) {

                    gotoTruckOrderListActivity("");
                    KaKuApplication.payType = "";
                }else if (KaKuApplication.payType.equals("STICKER")) {

                    gotoCheTieOrderListActivity();
                    KaKuApplication.payType = "";
                }
                finish();
            }
        });

        btn_result_home = (Button) findViewById(R.id.btn_result_home);
        btn_result_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
                startActivity(intent);
                finish();
            }
        });
        api = WXAPIFactory.createWXAPI(this, KaKuApplication.APP_ID);

        api.handleIntent(getIntent(), this);
        iniTitleBar();
    }
    private void iniTitleBar() {

        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回时跳转到待安装界面或待发货界面
                if (KaKuApplication.payType.equals("SERVICE")) {
                    gotoShopListActivity("B");
                    KaKuApplication.payType = "";
                } else if (KaKuApplication.payType.equals("TRUCK")) {

                    gotoTruckOrderListActivity("B");
                    KaKuApplication.payType = "";
                }else if (KaKuApplication.payType.equals("STICKER")) {

                    gotoCheTieOrderListActivity();
                    KaKuApplication.payType = "";
                }
                finish();
            }
        });
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("支付成功");
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {


        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            switch (resp.errCode) {
                case 0:
                    KaKuApplication.WXPayResult = "success";
                    Utils.NoNet(context);
                    break;
                case -1:
                    textView.setText("支付失败");
                    KaKuApplication.WXPayResult = "failed";
                    if (KaKuApplication.payType.equals("SERVICE")) {
                        gotoShopListActivity("A");
                        KaKuApplication.payType = "";
                    } else if (KaKuApplication.payType.equals("TRUCK")) {

                        gotoTruckOrderListActivity("A");
                        KaKuApplication.payType = "";
                    }else if (KaKuApplication.payType.equals("STICKER")) {

                        gotoCheTieOrderListActivity();
                        KaKuApplication.payType = "";
                    }
                    finish();
                    break;
                case -2:
                    textView.setText("取消支付");
                    KaKuApplication.WXPayResult = "cancle";
                    btn_result_list.setEnabled(false);
                    if (KaKuApplication.payType.equals("SERVICE")) {
                        gotoShopListActivity("A");
                        KaKuApplication.payType = "";
                    } else if (KaKuApplication.payType.equals("TRUCK")) {

                        gotoTruckOrderListActivity("A");
                        KaKuApplication.payType = "";
                    }else if (KaKuApplication.payType.equals("STICKER")) {

                        gotoCheTieOrderListActivity();
                        KaKuApplication.payType = "";
                    }
                    finish();
                    break;
            }
        }
    }

    /*private void notifyServiceOrder() {
        ServiceOrderPayReq req = new ServiceOrderPayReq();
        req.code = "40012";
        req.no_order = KaKuApplication.id_order;

        KaKuApiProvider.payServiceOrder(req, new KakuResponseListener<ServiceOrderPayResp>(this, ServiceOrderPayResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                stopProgressDialog();
                if (t != null) {
                    LogUtil.E("payTruckOrder res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.id_order = "";

                    }
                    LogUtil.showShortToast(context, t.msg);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, CharSequence message, int responseCode, long networkMillis) {
                super.onFailed(what, url, tag, message, responseCode, networkMillis);
                stopProgressDialog();
                KaKuApplication.id_order = "";
            }

        });
    }

    private void notifyTruckOrder() {
        TruckOrderPayReq req = new TruckOrderPayReq();
        req.code = "3009";
        req.no_bill = KaKuApplication.id_bill;

        KaKuApiProvider.payTruckOrder(req, new KakuResponseListener<TruckOrderPayResp>(this, TruckOrderPayResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);

                if (t != null) {
                    LogUtil.E("payTruckOrder res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.id_bill = "";
                        // KaKuApplication.payType = "";
                    }
                    LogUtil.showShortToast(context, t.msg);
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int what, String url, Object tag, CharSequence message, int responseCode, long networkMillis) {
                super.onFailed(what, url, tag, message, responseCode, networkMillis);
                stopProgressDialog();
                KaKuApplication.id_bill = "";
            }


        });
    }*/

    private void gotoCheTieOrderListActivity() {
        Intent intent =new Intent(context,CheTieOrderListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void gotoTruckOrderListActivity(String state) {
        Intent intent = new Intent(context, TruckOrderListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        KaKuApplication.truck_order_state = state;
        //intent.putExtra("state", "");
        startActivity(intent);
    }

    private void gotoShopListActivity(String state) {
        Intent intent = new Intent(context, BaoYangOrderListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        KaKuApplication.color_order = state;
        KaKuApplication.state_order = state;
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}