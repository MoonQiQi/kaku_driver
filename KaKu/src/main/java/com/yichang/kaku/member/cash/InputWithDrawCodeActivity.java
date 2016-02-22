package com.yichang.kaku.member.cash;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.payhelper.wxpay.net.sourceforge.simcpux.MD5;
import com.yichang.kaku.request.BankCardListReq;
import com.yichang.kaku.request.CheckWithDrawCodeReq;
import com.yichang.kaku.response.BankCardListResp;
import com.yichang.kaku.response.BaseResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.SecurityPasswordEditText;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

public class InputWithDrawCodeActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;

    private TextView tv_forget_code;
    //private EditText et_char_1, et_char_2, et_char_3,et_char_4,et_char_5,et_char_6;

    private SecurityPasswordEditText sEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrwa_code_input);
        init();
    }

    private void init() {
        initTitleBar();

        tv_forget_code= (TextView) findViewById(R.id.tv_forget_code);
        tv_forget_code.setOnClickListener(this);

        sEdit= (SecurityPasswordEditText) findViewById(R.id.et_s_pwd);
        sEdit.setSecurityEditCompleListener(new SecurityPasswordEditText.SecurityEditCompleListener() {
            @Override
            public void onNumCompleted(String num) {
                checkCode(num);
            }
        });


    }

    private void initTitleBar() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("验证支付密码");

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
        } else if (R.id.tv_forget_code == id) {
            /*忘记密码*/
            startActivity(new Intent(context,SetWithDrawCodeActivity.class).putExtra("flag_next_activity","INPUT"));
            finish();
        }
    }

    private String genAppSign(String id_driver) {
        StringBuilder sb = new StringBuilder();
        //拼接签名字符串
        sb.append("id_driver=");
        sb.append(id_driver);



        sb.append("&key=");
        sb.append(Constants.MSGKEY);
        LogUtil.E("sb:" + sb);

        String appSign1 = MD5.getMessageDigest(sb.toString().getBytes());
        String appSign = MD5.getMessageDigest(appSign1.getBytes()).toUpperCase();
        return appSign;
    }

    private void checkCode(String code) {
        Utils.NoNet(context);
        showProgressDialog();

        CheckWithDrawCodeReq req=new CheckWithDrawCodeReq();
        req.code="5008";
        req.id_driver= Utils.getIdDriver();
        req.pay_pass=code;
        req.sign=genAppSign(req.id_driver);



        KaKuApiProvider.checkWithDrawCode(req, new BaseCallback<BaseResp>(BaseResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, BaseResp t) {
                if (t != null) {
                    LogUtil.E("checkWithDrawCode res: " + t.res);
                    if (Constants.RES.equals(t.res)) {

                        startActivity(new Intent(InputWithDrawCodeActivity.context,WithdrawActivity.class));
                        finish();

                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }


                        LogUtil.showShortToast(context, t.msg);
                        sEdit.clearSecurityEdit();
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



}
