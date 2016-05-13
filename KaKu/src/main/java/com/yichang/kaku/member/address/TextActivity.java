package com.yichang.kaku.member.address;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;

public class TextActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private EditText et_text;
    private Button btn_text;
    private String strTitle = "";
    private String mTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        init();
    }

    private void init() {
        et_text = (EditText) findViewById(R.id.et_text);
        et_text.requestFocus();
        mTitle = getIntent().getStringExtra("title");
        switch (mTitle) {
            case "name":
                strTitle = "编辑收货人";
                if ("new".equals(KaKuApplication.new_addr)) {
                    et_text.setText("");
                } else {
                    et_text.setText(KaKuApplication.new_nametext);
                }
                et_text.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case "phone":
                strTitle = "编辑手机号码";
                if ("new".equals(KaKuApplication.new_addr)) {
                    et_text.setText("");
                } else {
                    et_text.setText(KaKuApplication.new_phonetext);
                }
                et_text.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case "address":
                strTitle = "编辑详细地址";
                if ("new".equals(KaKuApplication.new_addr)) {
                    et_text.setText("");
                } else {
                    et_text.setText(KaKuApplication.new_addrtext);
                }
                et_text.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
        }

        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText(strTitle);
        btn_text = (Button) findViewById(R.id.btn_text);
        btn_text.setOnClickListener(this);
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
        } else if (R.id.btn_text == id) {
            KaKuApplication.new_addr = "modify";
            Save();
        }
    }

    public void Save() {
        String inputText = et_text.getText().toString().trim();

        switch (mTitle) {
            case "name":

                if (TextUtils.isEmpty(inputText)) {
                    LogUtil.showShortToast(this, "用户名不能为空");
                    return;
                } else {

                    if (inputText.length() > 15) {
                        LogUtil.showShortToast(this, "用户名长度不能超出15个字符");
                        return;
                    }
                }
                break;
            case "phone":

                if (TextUtils.isEmpty(inputText)) {
                    LogUtil.showShortToast(this, "手机号码不能为空");
                    return;
                } else {
                    /*if (strTitle.equals("编辑手机号码")) {*/
                    if (!inputText.matches("^[1]\\d{10}$")) {
                        LogUtil.showShortToast(this, "请输入正确的手机号码");
                        return;
                    }
                 /*   }*/
                }
                break;
            case "address":

                if (TextUtils.isEmpty(inputText)) {
                    LogUtil.showShortToast(this, "收货地址不能为空");
                    return;
                } else {
                    if (inputText.length() > 40) {
                        LogUtil.showShortToast(this, "收货地址长度不能超过40个字符");
                        return;
                    }
                }
                break;
        }

        Intent intent = new Intent();
        intent.putExtra("string", inputText);
        setResult(RESULT_OK, intent);
        finish();
    }
}
