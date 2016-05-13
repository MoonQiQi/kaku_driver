package com.yichang.kaku.home.baoyang;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.member.YouHuiQuanActivity;
import com.yichang.kaku.member.cash.SetWithDrawCodeActivity;
import com.yichang.kaku.request.ModifyPayTypeReq;
import com.yichang.kaku.request.YellowOilOrderReq;
import com.yichang.kaku.request.YellowOilSubmitReq;
import com.yichang.kaku.response.ExitResp;
import com.yichang.kaku.response.ModifyPayTypeResp;
import com.yichang.kaku.response.YellowOilOrderResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.CircularImage;
import com.yichang.kaku.view.popwindow.InputPwdPopWindow;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class YellowOilOrderActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private String balance_choose = "N", phone;
    private CircularImage iv_yellowoilorder_head;
    private TextView tv_yellowoilorder_name, tv_yellowoilorder_dan, tv_yellowoilorder_fuwufei, tv_yellowoilorder_shifukuan, tv_yellowoilorder_querenzhifu, tv_yellowoilorder_keyongyue;
    private RatingBar star_yellowoilorder_star;
    private ImageView iv_yellowoilorder_call, iv_yellowoilorder_dahuangyou, iv_yellowoilorder_luntai, iv_yellowoilorder_baolun;
    private CheckBox cb_yellowoi_yue, cb_yellowoi_zaixianzhifu, cb_yellowoi_xianxiazhifu;
    private String type_pay, no_bill, price_bill; //type_pay	7在线支付 8线下支付 9余额支付
    //是否弹出密码输入框
    private Boolean isPwdPopWindowShow = false;
    private RelativeLayout rela_yellowoilorder_youhuiquan;
    private EditText et_yellowoilorder_money;
    private TextView tv_yellowoilorder_price_you, tv_yellowoilorder_price_yu, tv_yellowoilorder_youhuiquan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yellowoilorder);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("确认服务");
        iv_yellowoilorder_head = (CircularImage) findViewById(R.id.iv_yellowoilorder_head);
        tv_yellowoilorder_name = (TextView) findViewById(R.id.tv_yellowoilorder_name);
        tv_yellowoilorder_dan = (TextView) findViewById(R.id.tv_yellowoilorder_dan);
        tv_yellowoilorder_keyongyue = (TextView) findViewById(R.id.tv_yellowoilorder_keyongyue);
        tv_yellowoilorder_fuwufei = (TextView) findViewById(R.id.tv_yellowoilorder_fuwufei);
        tv_yellowoilorder_shifukuan = (TextView) findViewById(R.id.tv_yellowoilorder_shifukuan);
        tv_yellowoilorder_price_you = (TextView) findViewById(R.id.tv_yellowoilorder_price_you);
        tv_yellowoilorder_price_yu = (TextView) findViewById(R.id.tv_yellowoilorder_price_yu);
        tv_yellowoilorder_youhuiquan = (TextView) findViewById(R.id.tv_yellowoilorder_youhuiquan);
        tv_yellowoilorder_querenzhifu = (TextView) findViewById(R.id.tv_yellowoilorder_querenzhifu);
        tv_yellowoilorder_querenzhifu.setOnClickListener(this);
        rela_yellowoilorder_youhuiquan = (RelativeLayout) findViewById(R.id.rela_yellowoilorder_youhuiquan);
        rela_yellowoilorder_youhuiquan.setOnClickListener(this);
        et_yellowoilorder_money = (EditText) findViewById(R.id.et_yellowoilorder_money);
        et_yellowoilorder_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cb_yellowoi_xianxiazhifu.setChecked(false);
                cb_yellowoi_yue.setChecked(false);
                cb_yellowoi_zaixianzhifu.setChecked(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        star_yellowoilorder_star = (RatingBar) findViewById(R.id.star_yellowoilorder_star);
        cb_yellowoi_zaixianzhifu = (CheckBox) findViewById(R.id.cb_yellowoi_zaixianzhifu);
        cb_yellowoi_zaixianzhifu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if ("".equals(et_yellowoilorder_money.getText().toString().trim())) {
                        cb_yellowoi_zaixianzhifu.setChecked(false);
                        LogUtil.showShortToast(context, "请输入实际服务金额");
                        return;
                    }
                    type_pay = "7";
                    KaKuApplication.id_service_coupon = "";
                    cb_yellowoi_yue.setChecked(false);
                    cb_yellowoi_xianxiazhifu.setChecked(false);
                    ModifyPayType();
                }
            }
        });
        cb_yellowoi_xianxiazhifu = (CheckBox) findViewById(R.id.cb_yellowoi_xianxiazhifu);
        cb_yellowoi_xianxiazhifu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ("".equals(et_yellowoilorder_money.getText().toString().trim())) {
                    cb_yellowoi_xianxiazhifu.setChecked(false);
                    LogUtil.showShortToast(context, "请输入实际服务金额");
                    return;
                }
                if (isChecked) {
                    type_pay = "8";
                    cb_yellowoi_yue.setChecked(false);
                    cb_yellowoi_zaixianzhifu.setChecked(false);
                    KaKuApplication.id_service_coupon = "";
                    ModifyPayType();
                }
            }
        });
        cb_yellowoi_yue = (CheckBox) findViewById(R.id.cb_yellowoi_yue);
        cb_yellowoi_yue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ("".equals(et_yellowoilorder_money.getText().toString().trim())) {
                    cb_yellowoi_yue.setChecked(false);
                    LogUtil.showShortToast(context, "请输入实际服务金额");
                    return;
                }
                if (isChecked) {
                    if (!KaKuApplication.flag_balance) {
                        //todo 弹出对话框
                        Intent intent = new Intent(YellowOilOrderActivity.this, SetWithDrawCodeActivity.class);
                        intent.putExtra("isPassExist", false);
                        intent.putExtra("flag_next_activity", "NONE");
                        startActivity(intent);
                        LogUtil.showShortToast(context, "为了您的资金安全，请先设置支付密码");
                    } else {
                        balance_choose = "Y";
                        getOrderInfo();
                    }
                    type_pay = "9";
                    cb_yellowoi_xianxiazhifu.setChecked(false);
                    cb_yellowoi_zaixianzhifu.setChecked(false);
                } else {
                    balance_choose = "N";
                    getOrderInfo();
                }
                KaKuApplication.id_service_coupon = "";
                ModifyPayType();
            }
        });
        iv_yellowoilorder_dahuangyou = (ImageView) findViewById(R.id.iv_yellowoilorder_dahuangyou);
        iv_yellowoilorder_luntai = (ImageView) findViewById(R.id.iv_yellowoilorder_luntai);
        iv_yellowoilorder_baolun = (ImageView) findViewById(R.id.iv_yellowoilorder_baolun);
        iv_yellowoilorder_call = (ImageView) findViewById(R.id.iv_yellowoilorder_call);
        iv_yellowoilorder_call.setOnClickListener(this);

        getOrderInfo();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (cb_yellowoi_yue.isChecked()) {
            balance_choose = "Y";
        } else {
            balance_choose = "N";
        }
        ModifyPayType();
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        } else if (R.id.iv_yellowoilorder_call == id) {
            Utils.Call(this, phone);
        } else if (R.id.tv_yellowoilorder_querenzhifu == id) {
            if (!cb_yellowoi_zaixianzhifu.isChecked() && !cb_yellowoi_yue.isChecked() && !cb_yellowoi_xianxiazhifu.isChecked()) {
                LogUtil.showShortToast(context, "请选择一种支付方式");
                return;
            }
            if (cb_yellowoi_yue.isChecked() && !"0.00".equals(price_bill)) {
                LogUtil.showShortToast(context, "余额不足，请选择其他支付方式");
                return;
            }
            if ("7".equals(type_pay)) {
                Submit();
            } else if ("8".equals(type_pay)) {
                Submit();
            } else {
                if ("Y".equals(balance_choose)) {
                    showPwdInputWindow();
                } else {
                    Submit();
                }
            }
        } else if (R.id.rela_yellowoilorder_youhuiquan == id) {
            if ("".equals(et_yellowoilorder_money.getText().toString().trim())) {
                LogUtil.showShortToast(context, "请输入实际服务金额");
                return;
            }
            if (!cb_yellowoi_zaixianzhifu.isChecked()) {
                LogUtil.showShortToast(context, "只有选择在线支付才可使用优惠券");
                return;
            }
            KaKuApplication.flag_coupon = "service";
            KaKuApplication.price_service = et_yellowoilorder_money.getText().toString().trim();
            startActivity(new Intent(this, YouHuiQuanActivity.class));
        }
    }

    public void getOrderInfo() {
        showProgressDialog();
        YellowOilOrderReq req = new YellowOilOrderReq();
        req.code = "400105";
        req.id_upkeep_bill = KaKuApplication.id_upkeep_bill;
        KaKuApiProvider.YellowOilOrder(req, new KakuResponseListener<YellowOilOrderResp>(context, YellowOilOrderResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("YellowOilOrder res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        SetText(t);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        } else {
                            LogUtil.showShortToast(context, t.msg);
                        }
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    public void SetText(YellowOilOrderResp t) {
        if ("Y".equals(t.worker.getFlag_butter())) {
            iv_yellowoilorder_dahuangyou.setVisibility(View.VISIBLE);
        } else {
            iv_yellowoilorder_dahuangyou.setVisibility(View.GONE);
        }
        if ("Y".equals(t.worker.getFlag_tire())) {
            iv_yellowoilorder_luntai.setVisibility(View.VISIBLE);
        } else {
            iv_yellowoilorder_luntai.setVisibility(View.GONE);
        }
        if ("Y".equals(t.worker.getFlag_wheel())) {
            iv_yellowoilorder_baolun.setVisibility(View.VISIBLE);
        } else {
            iv_yellowoilorder_baolun.setVisibility(View.GONE);
        }

        if ("N".equals(t.flag_pay)) {
            KaKuApplication.flag_balance = true;
        } else {
            KaKuApplication.flag_balance = false;
        }

        phone = t.worker.getTel_worker();
        no_bill = t.no_bill;
        tv_yellowoilorder_name.setText(t.worker.getName_worker());
        tv_yellowoilorder_keyongyue.setText("可用余额¥" + t.money_balance);
        tv_yellowoilorder_dan.setText(t.worker.getNum_bill() + "单");
        tv_yellowoilorder_fuwufei.setText("¥ " + Utils.numdouble(t.price_goods));
        BitmapUtil.getInstance(context).download(iv_yellowoilorder_head, KaKuApplication.qian_zhuikong + t.worker.getImage_worker());
        star_yellowoilorder_star.setRating(Float.parseFloat(t.worker.getNum_star()));
    }

    public void ModifyPayType() {
        showProgressDialog();
        ModifyPayTypeReq req = new ModifyPayTypeReq();
        req.code = "400106";
        req.id_driver_coupon = KaKuApplication.id_service_coupon;
        req.id_upkeep_bill = KaKuApplication.id_upkeep_bill;
        req.balance_choose = balance_choose;
        req.price_service = et_yellowoilorder_money.getText().toString().trim();
        KaKuApiProvider.ModifyPayType(req, new KakuResponseListener<ModifyPayTypeResp>(context, ModifyPayTypeResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("ModifyPayType res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        ModifyPay(t);
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
        });
    }

    public void ModifyPay(ModifyPayTypeResp t) {
        price_bill = Utils.numdouble(((double) Math.round(Double.parseDouble(t.price_bill) * 100) / 100) + "");
        LogUtil.E("AAAAA:" + price_bill);
        tv_yellowoilorder_price_you.setText("-¥" + t.price_coupon);
        tv_yellowoilorder_price_yu.setText("-¥" + t.price_balance);
        tv_yellowoilorder_shifukuan.setText("实付款：" + price_bill + "元");
        if ("".equals(t.remark_coupon)) {
            tv_yellowoilorder_youhuiquan.setText("未使用优惠券");
        } else {
            tv_yellowoilorder_youhuiquan.setText(t.remark_coupon);
        }
    }

    public void Submit() {
        showProgressDialog();
        YellowOilSubmitReq req = new YellowOilSubmitReq();
        req.code = "400107";
        req.type_pay = type_pay;
        req.id_upkeep_bill = KaKuApplication.id_upkeep_bill;
        req.price_service = et_yellowoilorder_money.getText().toString().trim();
        req.id_driver_coupon = KaKuApplication.id_service_coupon;
        req.sign = Utils.getHmacMd5Str(et_yellowoilorder_money.getText().toString().trim());

        KaKuApiProvider.YellowOilSubmit(req, new KakuResponseListener<ExitResp>(context, ExitResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("YellowOilSubmit res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        if ("8".equals(type_pay)) {
                            Intent intent = new Intent(context, XianXiaPayActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                        if ("0.00".equals(price_bill) || "0".equals(price_bill)) {
                            KaKuApplication.payType = "SERVICE";
                            KaKuApplication.realPayment = price_bill;
                            Intent intent = new Intent(context, OilPingJiaActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            KaKuApplication.payType = "SERVICE";
                            Intent intent = new Intent(context, OrderBaoYangPayActivity.class);
                            intent.putExtra("no_bill", no_bill);
                            intent.putExtra("price_bill", price_bill);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }
        });
    }

    private void showPwdInputWindow() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                isPwdPopWindowShow = true;

                InputPwdPopWindow input = new InputPwdPopWindow(YellowOilOrderActivity.this);
                input.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                input.setConfirmListener(new InputPwdPopWindow.ConfirmListener() {
                    @Override
                    public void confirmPwd(Boolean isConfirmed) {
                        if (isConfirmed) {
                            Submit();
                            isPwdPopWindowShow = false;
                        }
                    }

                    @Override
                    public void showDialog() {
                        showProgressDialog();
                    }

                    @Override
                    public void stopDialog() {
                        stopProgressDialog();
                    }

                });
                input.show();
            }
        }, 200);
    }

}
