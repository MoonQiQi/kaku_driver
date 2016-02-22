package com.yichang.kaku.home.mycar;

import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;

public class EditCarInfoPageActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    //从上个页面传递过来的参数
    private String param;

    private EditText et_edit;
    private ImageView iv_clear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_carinfo_editpage);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        initTitleBar();
        param = getIntent().getStringExtra("param");

        initView();


    }

    private void initView() {
        et_edit= (EditText) findViewById(R.id.et_edit);
        et_edit.setText(param);
        et_edit.setSelection(param.length());
        et_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String comment = String.valueOf(s);
                if (comment.length() <= 60) {

                } else {
                    LogUtil.showShortToast(context, "已超出最大输入字符限制");
                    comment = comment.substring(0, 60);
                    et_edit.setText(comment);
                    Editable ea = et_edit.getEditableText();
                    Selection.setSelection(ea, ea.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        iv_clear= (ImageView) findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                et_edit.setText("");
            }
        });

    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText(KaKuApplication.editCarInfoPageTitle);


        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setOnClickListener(this);
        right.setText("保存");
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
        } else if (R.id.tv_right == id) {
            KaKuApplication.editCarInfo=et_edit.getText().toString().trim();
            if (!TextUtils.isEmpty(KaKuApplication.editCarInfo)){
                finish();
            }else {
                LogUtil.showShortToast(this,"请"+KaKuApplication.editCarInfoPageTitle+"后保存。");
            }
        }
    }


}
