package com.yichang.kaku.member.driver;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.request.DriverCertificationReq;
import com.yichang.kaku.response.DriverCertificationResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class DriverCertificationActivity extends BaseActivity implements OnClickListener, View.OnFocusChangeListener, AdapterView.OnItemClickListener {
    private TextView title, left, right;
    private EditText et_certification_name, et_certification_id;

    private Boolean isCertification = false;

    private TextView tv_certification_id, tv_certification_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_certification);
        init();
    }

    private void init() {

        if (getIntent().getStringExtra("flag").equals("Y") || getIntent().getStringExtra("flag").equals("D")) {
            isCertification = true;
        }


        initTitleBar();

        et_certification_name = (EditText) findViewById(R.id.et_certification_name);
        //et_certification_name.requestFocus();
        //et_certification_name.setOnFocusChangeListener(this);

        et_certification_id = (EditText) findViewById(R.id.et_certification_id);
        //et_certification_id.requestFocus();
        //et_certification_id.setOnFocusChangeListener(this);

        tv_certification_name = (TextView) findViewById(R.id.tv_certification_name);
        tv_certification_id = (TextView) findViewById(R.id.tv_certification_id);

        setData();
    }

    private void setData() {
        if (isCertification) {

            String name = getIntent().getStringExtra("name");
            String id = getIntent().getStringExtra("id");

            et_certification_name.setVisibility(View.GONE);
            et_certification_id.setVisibility(View.GONE);
            tv_certification_name.setVisibility(View.VISIBLE);
            tv_certification_id.setVisibility(View.VISIBLE);
            tv_certification_name.setText(name);
            tv_certification_id.setText(id);

        } else {
            tv_certification_name.setVisibility(View.GONE);
            tv_certification_id.setVisibility(View.GONE);
            et_certification_name.setVisibility(View.VISIBLE);
            et_certification_id.setVisibility(View.VISIBLE);
        }

    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("实名认证");
        if (!isCertification) {

            right = (TextView) findViewById(R.id.tv_right);
            right.setVisibility(View.VISIBLE);
            right.setText("保存");
            right.setOnClickListener(this);
        }

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
            if (TextUtils.isEmpty(et_certification_name.getText().toString().trim()) || et_certification_name.getText().toString().trim().length() > 15) {
                LogUtil.showShortToast(context, "用户名不能为空且长度小于15字符");
                return;
            } else if (TextUtils.isEmpty(et_certification_id.getText().toString().trim()) || et_certification_id.getText().toString().trim().length() != 18) {
                LogUtil.showShortToast(context, "身份证号码格式不正确");
                return;
            } else {
                SaveInfo();
            }

        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // TODO Auto-generated method stub
        EditText _v = (EditText) v;
        if (!hasFocus) {// 失去焦点
            _v.setHint(_v.getTag().toString());
        } else {
            String hint = _v.getHint().toString();
            _v.setTag(hint);
            _v.setCursorVisible(true);
            _v.setHint("");
        }
    }

    public void SaveInfo() {
        Utils.NoNet(context);
/*        final String name = et_certification_name.getText().toString().trim();
        final String id = et_certification_id.getText().toString().trim();*/
/*todo */
        DriverCertificationReq req = new DriverCertificationReq();
        req.code = "10026";
        req.id_driver = Utils.getIdDriver();
        req.card_driver = et_certification_id.getText().toString().trim();
        req.name_real = et_certification_name.getText().toString().trim();

        KaKuApiProvider.submitDriverCertification(req, new KakuResponseListener<DriverCertificationResp>(this, DriverCertificationResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("submitDriverCertification res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        // LogUtil.showShortToast(context, t.msg);
                        //设置返回结果
//设置返回结果
                        Intent intent = new Intent();
                        intent.putExtra("flag", "认证中");
                        intent.putExtra("certifi_name", et_certification_name.getText().toString().trim());
                        intent.putExtra("certifi_ID", et_certification_id.getText().toString().trim());
                        setResult(111, intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Utils.Many()){
            return;
        }
    }
}
