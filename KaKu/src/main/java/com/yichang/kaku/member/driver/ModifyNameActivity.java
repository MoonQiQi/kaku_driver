package com.yichang.kaku.member.driver;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.request.ModifyDriverNameReq;
import com.yichang.kaku.response.ModifyDriverNameResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class ModifyNameActivity extends BaseActivity implements OnClickListener, View.OnFocusChangeListener, AdapterView.OnItemClickListener {
    private TextView title, left, right;
    private EditText et_firstname_name;

    private ImageView iv_modifyme_del;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_name);
        init();
    }

    private void init() {

        initTitleBar();
        iv_modifyme_del = (ImageView) findViewById(R.id.iv_modifyme_del);
        iv_modifyme_del.setOnClickListener(this);

        et_firstname_name = (EditText) findViewById(R.id.et_modifymename_name);
        et_firstname_name.requestFocus();
        et_firstname_name.setOnFocusChangeListener(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("name");
        et_firstname_name.setText(name);
    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("用户名");

        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("保存");
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
            finish();
        } else if (R.id.tv_right == id) {
            if (!TextUtils.isEmpty(et_firstname_name.getText().toString().trim())) {
                SaveInfo();
            } else {
                LogUtil.showShortToast(context, "用户名不能为空");
            }
        } else if (R.id.iv_modifyme_del == id) {
            et_firstname_name.setText("");
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
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
        final String drivername = et_firstname_name.getText().toString().trim();

        ModifyDriverNameReq req = new ModifyDriverNameReq();
        req.code = "10025";
        req.id_driver = Utils.getIdDriver();
        req.name_driver = drivername;

        KaKuApiProvider.modifyDriverName(req, new KakuResponseListener<ModifyDriverNameResp>(this, ModifyDriverNameResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getDriverInfo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        //LogUtil.showShortToast(context, t.msg);
                        //设置返回结果
                        Intent intent = new Intent();
                        intent.putExtra("drivername", drivername);
                        setResult(101, intent);
                        finish();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
