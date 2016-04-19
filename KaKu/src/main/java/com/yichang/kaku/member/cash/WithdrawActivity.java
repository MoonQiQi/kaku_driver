package com.yichang.kaku.member.cash;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.MyActivityManager;
import com.yichang.kaku.response.WithdrawResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.tools.okhttp.OkHttpUtil;
import com.yichang.kaku.tools.okhttp.Params;
import com.yichang.kaku.tools.okhttp.RequestCallback;
import com.yichang.kaku.webService.UrlCtnt;

import java.io.IOException;

/**
 * Created by xiaosu on 2015/12/14.
 */
public class WithdrawActivity extends BaseActivity implements View.OnClickListener {

    public static int BANK_INFO_REQUEST = 200;
    public static int BANK_INFO_RESPONSE_SUCCESS = 201;
   /* private TextView getCode;
    private EditText line2_1;*/
    /*mCardBank：银行卡号
        mNameBank：银行
        mNameUser:用户名
        mCityBanknd：开户地

        */
   /* private String mCardBank;
    private String mNameUser;*/
/*
    Runnable countDownTask = new Runnable() {
        @Override
        public void run() {
            String text = getText(getCode);
            switch (text) {
                case "获取验证码":
                    getCode.setText("60");
                    getCode.setEnabled(false);
                    getCode.postDelayed(this, 1000);
                    break;
                case "0":
                    getCode.setText("获取验证码");
                    getCode.setEnabled(true);
                    break;
                default:
                    int i = Integer.parseInt(text) - 1;
                    getCode.setText(i + "");
                    getCode.postDelayed(this, 1000);
                    break;
            }
        }
    };*/
    private TextView count;
    private TextView car_bank;
    private EditText money;

    public void submit(View view) {
        String text = getText(car_bank);
        if (TextUtils.isEmpty(text)) {
            showShortToast("请选择银行卡");
            return;
        }

        String text1 = getText(money);

        if (TextUtils.isEmpty(text1)) {
            showShortToast("请输入提现金额");
            return;
        }else if(Float.parseFloat(text1)<1.00f){
            showShortToast("提现金额必须大于1元");
            return;
        }

        if (!isValide()) {
            showShortToast("可提现金额不足");
            return;
        }

        showProgressDialog();
        Params.builder builder = new Params.builder();
        builder.p("id_driver", Utils.getIdDriver())
                .p("code", "5003")
                .p("sid", Utils.getSid())
                .p("id_driver_bank", mId_driver_bank)
                .p("num_money", getText(money));

        OkHttpUtil.postAsync(UrlCtnt.BASEIP + "pay/deposit_submit", builder.build(), new RequestCallback<WithdrawResp>(this, WithdrawResp.class) {
            @Override
            public void onSuccess(WithdrawResp obj, String result) {
                stopProgressDialog();
                LogUtil.showShortToast(context,obj.msg);
                MyActivityManager.getInstance().finishActivity(YueActivity.class);
                startActivity(new Intent(context, YueActivity.class));
                finish();

            }

            @Override
            public void onInnerFailure(Request request, IOException e) {
                stopProgressDialog();
                showShortToast("网络连接失败，请稍后再试");
            }
        });
    }

    private boolean isValide() {
        String text = getText(count);
        String s = getText(money);


        if (!TextUtils.isEmpty(text) && !TextUtils.isEmpty(s)) {
            float allMoney = Float.parseFloat(text.substring(1, text.length()));
            float v = Float.parseFloat(s);
            if (v > allMoney) {
                return false;
            }
        }
        return true;
    }

    private void getData() {
        showProgressDialog();
        Params.builder builder = new Params.builder();
        builder.p("id_driver", Utils.getIdDriver())
                .p("sid", Utils.getSid())
                .p("code", "5002");

        OkHttpUtil.postAsync(UrlCtnt.BASEIP + "pay/deposit_confirm", builder.build(), new RequestCallback<WithdrawResp>(this, WithdrawResp.class) {
            @Override
            public void onSuccess(WithdrawResp obj, String result) {
                stopProgressDialog();
                count.setText("¥ " + obj.money_balance);
                car_bank.setText(obj.remark_driver_bank);
                mId_driver_bank = obj.id_driver_bank;
            }

            @Override
            public void onInnerFailure(Request request, IOException e) {
                stopProgressDialog();
                showShortToast("网络连接失败，请稍后再试");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_assets_withdraw);
        count = findView(R.id.line0_1);
        money = findView(R.id.line2_1);

        money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String dValue = s.toString();
                int dotIndex = dValue.indexOf('.');
                LogUtil.E("dotIndex" + dotIndex);
                if (dotIndex != -1) {
                    if (dValue.length() - dotIndex-1 > 2) {
                        money.setText(dValue.substring(0,dotIndex+3));
                    }
                    Editable ea = money.getEditableText();
                    Selection.setSelection(ea, ea.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //money.setFilters(new InputFilter[]{lengthfilter});

        TextView tv_mid = findView(R.id.tv_mid);
        tv_mid.setText("提现");
        findView(R.id.tv_left).setOnClickListener(this);

        car_bank = (TextView) findViewById(R.id.line1_1);
        car_bank.setOnClickListener(this);

        getData();
    }

   /* private void startCountDown() {
        getCode.post(countDownTask);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                onBackPressed();
                break;
            case R.id.line1_1:
                //ForResult();
                Intent intent = new Intent(context, BankCardListActivity.class);
                startActivityForResult(intent,BANK_INFO_REQUEST);

                break;
        }
    }


    private String mId_driver_bank;
    private String mCardInfo;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == BANK_INFO_RESPONSE_SUCCESS) {
            /*mCardBank = data.getStringExtra("bankcard_no");
            mNameUser = data.getStringExtra("bankcard_name");*/
            mCardInfo=data.getStringExtra("cardInfo");
            mId_driver_bank=data.getStringExtra("id_driver_bank");
            if (!TextUtils.isEmpty(mCardInfo)) {
               // String temp = mNameUser + "**" + mCardBank.substring(mCardBank.length() - 4, mCardBank.length());
                car_bank.setText(mCardInfo);
            }
        }
    }

    /** 输入框小数的位数*/
    private static final int DECIMAL_DIGITS = 2;

    /**
     *  设置小数位数控制
     */
    InputFilter lengthfilter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            // 删除等特殊字符，直接返回
            if ("".equals(source.toString())) {
                return null;
            }
            String dValue = dest.toString();
            String[] splitArray = dValue.split("//.");
            if (splitArray.length > 1) {
                String dotValue = splitArray[1];
                int diff = dotValue.length() + 1 - DECIMAL_DIGITS;
                if (diff > 0) {
                    return source.subSequence(start, end - diff);
                }
            }
            return null;
        }
    };
}
