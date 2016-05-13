package com.yichang.kaku.home.huafei;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.OrderPayActivity;
import com.yichang.kaku.member.cash.SetWithDrawCodeActivity;
import com.yichang.kaku.payhelper.alipay.PaySuccessActivity;
import com.yichang.kaku.request.ChongZhiReq;
import com.yichang.kaku.request.ChongZhiZhiFuReq;
import com.yichang.kaku.response.ChongZhiResp;
import com.yichang.kaku.response.ChongZhiZhiFuResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.popwindow.InputPwdPopWindow;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class HuaFeiZhiFuActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private TextView tv_hfzf_phone, tv_hfzf_money, tv_hfzf_pay, tv_hfzf_keyongyue;
    private CheckBox cb_hfzf_zaixian, cb_hfzf_yue;
    private Button btn_hfzf_zhifu;
    private String balance_choose = "N";
    private String phone;
    private String type_goods;
    private String price_goods;
    private String price_balance;
    private String shifukuan;
    //是否弹出密码输入框
    private Boolean isPwdPopWindowShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huafeizhifu);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getOrderInfo();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("手机充值");
        KaKuApplication.flag_balance = false;
        tv_hfzf_phone = (TextView) findViewById(R.id.tv_hfzf_phone);
        tv_hfzf_money = (TextView) findViewById(R.id.tv_hfzf_money);
        tv_hfzf_pay = (TextView) findViewById(R.id.tv_hfzf_pay);
        tv_hfzf_keyongyue = (TextView) findViewById(R.id.tv_hfzf_keyongyue);
        cb_hfzf_zaixian = (CheckBox) findViewById(R.id.cb_hfzf_zaixian);
        cb_hfzf_zaixian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_hfzf_yue.setChecked(false);
                }
            }
        });
        cb_hfzf_yue = (CheckBox) findViewById(R.id.cb_hfzf_yue);
        cb_hfzf_yue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                       if (isChecked) {
                                                           if (!KaKuApplication.flag_balance) {
                                                               //todo 弹出对话框
                                                               Intent intent = new Intent(HuaFeiZhiFuActivity.this, SetWithDrawCodeActivity.class);
                                                               intent.putExtra("isPassExist", false);
                                                               intent.putExtra("flag_next_activity", "NONE");
                                                               startActivity(intent);
                                                               LogUtil.showShortToast(context, "为了您的资金安全，请先设置支付密码");
                                                           } else {
                                                               balance_choose = "Y";
                                                               KaKuApplication.flag_balance = true;
                                                               getOrderInfo();
                                                           }
                                                           cb_hfzf_zaixian.setChecked(false);
                                                       } else {
                                                           balance_choose = "N";
                                                           KaKuApplication.flag_balance = false;
                                                           getOrderInfo();
                                                       }
                                                   }
                                               }
        );
        btn_hfzf_zhifu = (Button) findViewById(R.id.btn_hfzf_zhifu);
        btn_hfzf_zhifu.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        phone = bundle.getString("phone");
        price_goods = bundle.getString("price_goods");
        type_goods = bundle.getString("type_goods");
        if ("SF".equals(type_goods) || "OF".equals(type_goods)) {
            tv_hfzf_money.setText("充值面额：¥50");
        } else {
            tv_hfzf_money.setText("充值面额：¥100");
        }

    }

    public void SetText(ChongZhiResp t) {
        shifukuan = t.price_bill;
        price_balance = t.price_balance;
        String shifukuan_string = "实付金额：¥" + t.price_bill;
        if (!shifukuan_string.contains(".")) {
            shifukuan_string = shifukuan_string + ".00";
        }
        tv_hfzf_keyongyue.setText("可用余额¥" + t.money_balance);
        if ("N".equals(t.flag_pay)) {
            KaKuApplication.flag_balance = true;
        } else {
            KaKuApplication.flag_balance = false;
        }
        SpannableStringBuilder spannableStringBuilder = Utils.ChangeTextColor(shifukuan_string, 5, shifukuan_string.length(), getResources().getColor(R.color.color_red));
        tv_hfzf_pay.setText(spannableStringBuilder);
        if (t.province.equals(t.cityname)) {
            tv_hfzf_phone.setText("手机号：" + phone + "（" + t.cityname + t.company + "）");
        } else {
            tv_hfzf_phone.setText("手机号：" + phone + "（" + t.province + t.cityname + t.company + "）");
        }
    }

    public void getOrderInfo() {
        showProgressDialog();
        ChongZhiReq req = new ChongZhiReq();
        req.code = "30031";
        req.phone = phone;
        req.type_goods = type_goods;
        if (KaKuApplication.flag_balance) {
            req.balance_choose = "Y";
        } else {
            req.balance_choose = "N";
        }
        req.price_goods = price_goods;
        KaKuApiProvider.ChongZhi(req, new KakuResponseListener<ChongZhiResp>(context, ChongZhiResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("ChongZhi res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        SetText(t);
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

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        } else if (R.id.btn_hfzf_zhifu == id) {
            if (!cb_hfzf_zaixian.isChecked() && !cb_hfzf_yue.isChecked()) {
                LogUtil.showShortToast(context, "请选择一种支付方式");
                return;
            }
            if (cb_hfzf_yue.isChecked()) {
                if (KaKuApplication.flag_balance) {
                    showPwdInputWindow();
                } else {
                    Submit();
                }
            } else {
                Submit();
            }

        }
    }

    private void showPwdInputWindow() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                isPwdPopWindowShow = true;

                InputPwdPopWindow input =
                        new InputPwdPopWindow(HuaFeiZhiFuActivity.this);
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

    public void Submit() {
        showProgressDialog();
        ChongZhiZhiFuReq req = new ChongZhiZhiFuReq();
        req.code = "30032";
        req.phone = phone;
        req.price_balance = price_balance;
        req.price_bill = shifukuan;
        req.type_goods = type_goods;
        req.price_goods = price_goods;
        req.sign = Utils.getHmacMd5Str(price_balance);

        KaKuApiProvider.ChongZhiZhiFu(req, new KakuResponseListener<ChongZhiZhiFuResp>(context, ChongZhiZhiFuResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("ChongZhiZhiFu res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.realPayment = shifukuan;
                        if ("0.00".equals(shifukuan) || "0".equals(shifukuan)) {
                            KaKuApplication.payType = "TRUCK";
                            Intent intent = new Intent(context, PaySuccessActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(context, OrderPayActivity.class);
                            intent.putExtra("no_bill", t.no_bill);
                            intent.putExtra("price_bill", shifukuan);
                            startActivity(intent);
                        }
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


}
