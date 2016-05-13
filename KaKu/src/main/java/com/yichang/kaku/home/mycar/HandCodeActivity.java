package com.yichang.kaku.home.mycar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.HandCodeObj;
import com.yichang.kaku.request.GetHandDataReq;
import com.yichang.kaku.response.GetHandDataResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandCodeActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private EditText et_handcode;
    private TextView tv_handcode_tv;
    private ImageView iv_handcode_iv;
    private String code_string = "";
    private String code_string6 = "";
    private ListView lv_handcode;
    private List<String> code_list = new ArrayList<>();
    private List<String> code_list2 = new ArrayList<>();
    private List<String> code_list6 = new ArrayList<>();
    public List<HandCodeObj> suggestions_list = new ArrayList<>();
    private HandCodeAdapter adapter;
    SharedPreferences.Editor editor = KaKuApplication.editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handcode);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("输码识车");
        tv_handcode_tv = (TextView) findViewById(R.id.tv_handcode_tv);
        iv_handcode_iv = (ImageView) findViewById(R.id.iv_handcode_iv);
        lv_handcode = (ListView) findViewById(R.id.lv_handcode);
        lv_handcode.setOnItemClickListener(this);
        et_handcode = (EditText) findViewById(R.id.et_handcode);
        et_handcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tv_handcode_tv.setVisibility(View.GONE);
                iv_handcode_iv.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                et_handcode.removeTextChangedListener(this);//解除文字改变事件
                et_handcode.setText(s.toString().toUpperCase());//转换
                et_handcode.setSelection(s.toString().length());//重新设置光标位置
                et_handcode.addTextChangedListener(this);//重新绑

                code_list2.clear();
                for (int i = 0; i < code_list.size(); i++) {
                    if (code_list.get(i).startsWith(et_handcode.getText().toString().trim()) && !"".equals(et_handcode.getText().toString().trim())) {
                        code_list2.add(code_list.get(i));
                    }
                }
                adapter = new HandCodeAdapter(context, code_list2);
                lv_handcode.setAdapter(adapter);
                lv_handcode.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (code_list2.size() == 0 && "".equals(et_handcode.getText().toString().trim())) {
                    tv_handcode_tv.setVisibility(View.VISIBLE);
                    iv_handcode_iv.setVisibility(View.VISIBLE);
                }
            }
        });
        GetData();
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
        }
    }

    public void GetData() {
        showProgressDialog();
        GetHandDataReq req = new GetHandDataReq();
        req.flag_code = Utils.getHandCode();
        req.code = "20022";
        KaKuApiProvider.GetHandCode(req, new KakuResponseListener<GetHandDataResp>(context, GetHandDataResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getdata res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        SetText(t);
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

    public void SetText(GetHandDataResp t) {
        if (t.suggestions.size() == 0) {
            String code_sp = KaKuApplication.sp.getString("CODE_SP", "");
            String code_sp6 = KaKuApplication.sp.getString("CODE_SP6", "");
            code_list = Arrays.asList(code_sp.split(","));
            code_list6 = Arrays.asList(code_sp6.split(","));
            adapter = new HandCodeAdapter(context, code_list);
            lv_handcode.setAdapter(adapter);
        } else {

            KaKuApplication.sp.edit().putString("CODE_SP", "").commit();
            KaKuApplication.sp.edit().putString("CODE_SP6", "").commit();
            suggestions_list = t.suggestions;
            for (int i = 0; i < suggestions_list.size(); i++) {
                code_string += (suggestions_list.get(i).getCode() + ",");
                code_string6 += (suggestions_list.get(i).getId_brand() + ",");
            }
            editor.putString("CODE_SP", code_string);
            editor.putString("CODE_SP6", code_string6);
            editor.putString("FLAG_CODE", t.flag_code);
            editor.commit();

            String code_sp = KaKuApplication.sp.getString("CODE_SP", "");
            String code_sp6 = KaKuApplication.sp.getString("CODE_SP6", "");
            code_list = Arrays.asList(code_sp.split(","));
            code_list6 = Arrays.asList(code_sp6.split(","));
            adapter = new HandCodeAdapter(context, code_list);
            lv_handcode.setAdapter(adapter);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        KaKuApplication.car_code = code_list2.get(position);
        for (int i = 0; i < code_list.size(); i++) {
            if (KaKuApplication.car_code.equals(code_list.get(i))) {
                KaKuApplication.id_brand = code_list6.get(i);
            }
        }
        LogUtil.E("car_code:" + KaKuApplication.car_code);
        LogUtil.E("id_brand:" + KaKuApplication.id_brand);
        startActivity(new Intent(this, FaDongJiInfoActivity.class));
    }
}
