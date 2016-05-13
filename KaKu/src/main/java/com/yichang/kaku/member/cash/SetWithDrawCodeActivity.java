package com.yichang.kaku.member.cash;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.payhelper.wxpay.net.sourceforge.simcpux.MD5;
import com.yichang.kaku.request.BankCardListReq;
import com.yichang.kaku.request.WithDrawCaptchaReq;
import com.yichang.kaku.request.WithDrawCodeReq;
import com.yichang.kaku.response.BankCardListResp;
import com.yichang.kaku.response.BaseResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class SetWithDrawCodeActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;

    private TextView tv_get_captcha;
    private EditText et_captcha, et_withdraw_code, et_withdraw_code_check;
    private LinearLayout ll_captcha;

    private String strPassword;
    private boolean isPassExist;

//Input,NONE

    private String flag_next_activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrwa_code_set);
        init();
    }

    private void init() {
        initTitleBar();
        isPassExist = getIntent().getBooleanExtra("isPassExist", true);
        flag_next_activity = getIntent().getStringExtra("flag_next_activity");
        ll_captcha = (LinearLayout) findViewById(R.id.ll_captcha);
        if (isPassExist) {
            ll_captcha.setVisibility(View.VISIBLE);
        } else {
            ll_captcha.setVisibility(View.GONE);
        }
        tv_get_captcha = (TextView) findViewById(R.id.tv_get_captcha);
        tv_get_captcha.setOnClickListener(this);

        et_captcha = (EditText) findViewById(R.id.et_captcha);
        et_captcha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_withdraw_code = (EditText) findViewById(R.id.et_withdraw_code);
        et_withdraw_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                strPassword = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_withdraw_code_check = (EditText) findViewById(R.id.et_withdraw_code_check);
        et_withdraw_code_check.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() >= 6) {
                    if (!s.toString().equals(strPassword)) {
                        LogUtil.showShortToast(context, "两次输入密码不一致请重新输入");
                        et_withdraw_code_check.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getBankCardList();
    }

    private void initTitleBar() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("设置支付密码");
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("提交");
        right.setOnClickListener(this);

    }

    private int tempTime = 60;
    private boolean isWaiting = false;

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        } else if (R.id.tv_right == id) {
            if (isPassExist) {
                //首次添加密码时不需要验证码

                if (TextUtils.isEmpty(et_captcha.getText())) {
                    LogUtil.showShortToast(context, "请填写验证码");
                    return;
                }
            }

            if (TextUtils.isEmpty(et_withdraw_code.getText())) {

                LogUtil.showShortToast(context, "请输入提现密码");
                return;
            } else if (et_withdraw_code.getText().toString().trim().length() < 6) {
                LogUtil.showShortToast(context, "请输入6位提现密码");
                return;
            }
            if (TextUtils.isEmpty(et_withdraw_code_check.getText())) {
                LogUtil.showShortToast(context, "请确认提现密码");
                return;
            } else if (!et_withdraw_code_check.getText().toString().trim().equals(strPassword)) {
                LogUtil.showShortToast(context, "请确认提现密码");
                return;
            }

            setWithDrawCode();

        } else if (R.id.tv_get_captcha == id) {
            //startActivity(new Intent(this, WithdrawActivity.class));
            if (isWaiting) {

            } else {
                // 获取验证码
                getCaptcha();

               /* tv_get_captcha.setBackgroundResource(R.drawable.login_bt_waiting);
                tv_get_captcha.setTextColor(Color.parseColor("#999999"));*/
                tv_get_captcha.setBackgroundColor(Color.parseColor("#66999999"));
                tv_get_captcha.setTextColor(Color.parseColor("#666666"));
                isWaiting = true;
                tv_get_captcha.setEnabled(false);

                handler.sendEmptyMessage(0);
            }

        }
    }

    private void setWithDrawCode() {
        Utils.NoNet(this);

        showProgressDialog();
        WithDrawCodeReq req = new WithDrawCodeReq();
        req.code = "5007";
        req.id_driver = Utils.getIdDriver();
        if (isPassExist) {

            req.vcode = et_captcha.getText().toString().trim();
        } else {

            req.vcode = "-1";
        }
        req.pay_pass = strPassword;
        req.sign = Utils.TwoMD5Big("pay_pass", strPassword);

        KaKuApiProvider.setWithDrawCode(req, new KakuResponseListener<BaseResp>(this, BaseResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("setWithDrawCode res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.IsOrderSetPass = true;
                        KaKuApplication.flag_balance = true;
                        LogUtil.showShortToast(context, "支付密码修改成功！");
                        switch (flag_next_activity) {
                            case "INPUT":
                                startActivity(new Intent(context, InputWithDrawCodeActivity.class));
                                break;
                            case "NONE":

                                break;
                            case "CONFIRM_ORDER":

                                break;
                            default:

                                break;
                        }
                        finish();

                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                    stopProgressDialog();
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    private void resetCheckButton() {
        tempTime = 60;
        tv_get_captcha.setBackgroundColor(Color.WHITE);
        tv_get_captcha.setTextColor(Color.parseColor("#0f7afa"));
        tv_get_captcha.setEnabled(true);
        tv_get_captcha.setText("获取验证码");
        isWaiting = false;
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:

                    if (tempTime == 0) {
                        // 状态重置
                        resetCheckButton();
                        return;
                    }

                    tv_get_captcha.setText(String.valueOf(tempTime) + "秒后重新获取");
                    tempTime--;

                    handler.sendEmptyMessageDelayed(0, 1000);
                    break;

                default:

                    break;
            }
        }

    };

    private void getCaptcha() {

        Utils.NoNet(this);

        WithDrawCaptchaReq req = new WithDrawCaptchaReq();
        req.code = "5006";
        req.id_driver = Utils.getIdDriver();
        req.sign = genAppSign(req.id_driver);

        KaKuApiProvider.getWithDrawCaptcha(req, new KakuResponseListener<BaseResp>(this, BaseResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getWithDrawCaptcha res: " + t.res);
                    if (Constants.RES.equals(t.res)) {

                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    private String genAppSign(String id_driver) {
        StringBuilder sb = new StringBuilder();
        //拼接签名字符串
        sb.append("id_driver=");
        sb.append(id_driver);


        sb.append("&key=");
        sb.append(Constants.MSGKEY);

        String appSign1 = MD5.getMessageDigest(sb.toString().getBytes());
        String appSign = MD5.getMessageDigest(appSign1.getBytes()).toUpperCase();
        return appSign;
    }

    public void getBankCardList() {
        if (!Utils.checkNetworkConnection(context)) {
            return;
        }

        BankCardListReq req = new BankCardListReq();
        req.code = "5004";
        req.id_driver = Utils.getIdDriver();

        KaKuApiProvider.getBankCardList(req, new KakuResponseListener<BankCardListResp>(this, BankCardListResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("yue res: " + t.res);
                    if (Constants.RES.equals(t.res)) {

                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }


        });
    }
}
