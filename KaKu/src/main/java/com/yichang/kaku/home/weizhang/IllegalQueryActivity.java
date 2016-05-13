package com.yichang.kaku.home.weizhang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.WeiZhangCityObj;
import com.yichang.kaku.obj.WeiZhangResultObj;
import com.yichang.kaku.request.ExitReq;
import com.yichang.kaku.request.IllegalQueryReq;
import com.yichang.kaku.response.IllegalQueryResp;
import com.yichang.kaku.response.WeiZhangCityResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.popwindow.IllegalPopWindow;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;


public class IllegalQueryActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private TextView tv_weizhang_provincename, tv_weizhang_chepaijian;
    private EditText et_weizhang_carnum, et_weizhang_chejiahao, et_weizhang_fadongjihao;
    private LinearLayout line_weizhang_wenhao;
    private Button btn_weizhang_search;
    private GridView gv_weizhang_city;
    private RelativeLayout rela_weizhang_grid, rela_weizhang_chejiahao, rela_weizhang_fadongjihao;
    private WeiZhangProvinceAdapter adapter;
    private List<WeiZhangResultObj> list_province = new ArrayList<>();
    private List<WeiZhangCityObj> list_city = new ArrayList<>();
    private String city_no;
    private String type = "province";
    SharedPreferences.Editor editor = KaKuApplication.editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illegal_query);
        init();
    }


    public void init() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("查违章");
        tv_weizhang_provincename = (TextView) findViewById(R.id.tv_weizhang_provincename);
        tv_weizhang_provincename.setOnClickListener(this);
        tv_weizhang_chepaijian = (TextView) findViewById(R.id.tv_weizhang_chepaijian);
        et_weizhang_carnum = (EditText) findViewById(R.id.et_weizhang_carnum);
        et_weizhang_carnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_weizhang_carnum.removeTextChangedListener(this);//解除文字改变事件
                et_weizhang_carnum.setText(s.toString().toUpperCase());//转换
                et_weizhang_carnum.setSelection(s.toString().length());//重新设置光标位置
                et_weizhang_carnum.addTextChangedListener(this);//重新绑
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_weizhang_chejiahao = (EditText) findViewById(R.id.et_weizhang_chejiahao);
        et_weizhang_chejiahao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_weizhang_chejiahao.removeTextChangedListener(this);//解除文字改变事件
                et_weizhang_chejiahao.setText(s.toString().toUpperCase());//转换
                et_weizhang_chejiahao.setSelection(s.toString().length());//重新设置光标位置
                et_weizhang_chejiahao.addTextChangedListener(this);//重新绑
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_weizhang_fadongjihao = (EditText) findViewById(R.id.et_weizhang_fadongjihao);
        et_weizhang_fadongjihao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_weizhang_fadongjihao.removeTextChangedListener(this);//解除文字改变事件
                et_weizhang_fadongjihao.setText(s.toString().toUpperCase());//转换
                et_weizhang_fadongjihao.setSelection(s.toString().length());//重新设置光标位置
                et_weizhang_fadongjihao.addTextChangedListener(this);//重新绑
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        line_weizhang_wenhao = (LinearLayout) findViewById(R.id.line_weizhang_wenhao);
        line_weizhang_wenhao.setOnClickListener(this);
        btn_weizhang_search = (Button) findViewById(R.id.btn_weizhang_search);
        btn_weizhang_search.setOnClickListener(this);
        rela_weizhang_grid = (RelativeLayout) findViewById(R.id.rela_weizhang_grid);
        rela_weizhang_chejiahao = (RelativeLayout) findViewById(R.id.rela_weizhang_chejiahao);
        rela_weizhang_fadongjihao = (RelativeLayout) findViewById(R.id.rela_weizhang_fadongjihao);
        gv_weizhang_city = (GridView) findViewById(R.id.gv_weizhang_city);
        gv_weizhang_city.setOnItemClickListener(this);
        if ("".equals(Utils.getCityName())) {
            tv_weizhang_provincename.setText("选择城市");
        } else {
            tv_weizhang_provincename.setText(Utils.getCityName());
        }
        tv_weizhang_chepaijian.setText(Utils.getCityJian());
        et_weizhang_carnum.setText(Utils.getCarNumber());
        et_weizhang_fadongjihao.setText(Utils.getCarDriveNumber());
        et_weizhang_chejiahao.setText(Utils.getCarCode());
        city_no = Utils.getCityCode();
        getInfo();
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
        } else if (R.id.line_weizhang_wenhao == id) {
            ShowPop();
        } else if (R.id.btn_weizhang_search == id) {
            if ("".equals(tv_weizhang_provincename.getText().toString().trim())) {
                LogUtil.showShortToast(context, "请选择查询省份");
                return;
            } else if ("".equals(et_weizhang_carnum.getText().toString().trim())) {
                LogUtil.showShortToast(context, "请输入车牌号码");
                return;
            }
            Query();
        } else if (R.id.tv_weizhang_provincename == id) {
            rela_weizhang_grid.setVisibility(View.VISIBLE);
            adapter = new WeiZhangProvinceAdapter(context, list_province);
            gv_weizhang_city.setAdapter(adapter);
        }
    }

    public void getInfo() {
        showProgressDialog();
        ExitReq req = new ExitReq();
        req.code = "9001";
        KaKuApiProvider.CityQuery(req, new KakuResponseListener<WeiZhangCityResp>(this, WeiZhangCityResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if ("200".equals(t.resultcode)) {
                    LogUtil.E(t.result.toString());
                    SetText(t);
                } else {
                    LogUtil.showShortToast(context, t.reason);
                }

                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    public void SetText(WeiZhangCityResp t) {
        list_province = t.result;
        adapter = new WeiZhangProvinceAdapter(context, list_province);
        gv_weizhang_city.setAdapter(adapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (R.id.gv_weizhang_city == parent.getId()) {
            if ("province".equals(type)) {
                tv_weizhang_provincename.setText(list_province.get(position).getProvince());
                tv_weizhang_chepaijian.setText(list_province.get(position).citys.get(0).getAbbr());
                list_city = list_province.get(position).getCitys();
                WeiZhangCityAdapter adapter = new WeiZhangCityAdapter(context, list_city);
                gv_weizhang_city.setAdapter(adapter);
                type = "city";
            } else if ("city".equals(type)) {
                city_no = list_city.get(position).getCity_code();
                if (0 == list_city.get(position).getEngine()) {
                    rela_weizhang_fadongjihao.setVisibility(View.GONE);
                } else {
                    rela_weizhang_fadongjihao.setVisibility(View.VISIBLE);
                    et_weizhang_fadongjihao.setHint("请输入全部发动机号");
                }
                if (0 == list_city.get(position).getClassa()) {
                    rela_weizhang_chejiahao.setVisibility(View.GONE);
                } else {
                    et_weizhang_chejiahao.setVisibility(View.VISIBLE);
                    et_weizhang_chejiahao.setHint("请输入全部车架号");
                }
                type = "province";
                tv_weizhang_provincename.setText(list_city.get(position).getCity_name());
                rela_weizhang_grid.setVisibility(View.GONE);
                et_weizhang_chejiahao.setText("");
                et_weizhang_fadongjihao.setText("");
                et_weizhang_carnum.setText("");
            }
        }
    }

    public void Query() {
        showProgressDialog();
        IllegalQueryReq req = new IllegalQueryReq();
        req.carnumber = tv_weizhang_chepaijian.getText().toString().trim() + et_weizhang_carnum.getText().toString().trim();
        req.carcode = et_weizhang_chejiahao.getText().toString().trim();
        req.cardrivenumber = et_weizhang_fadongjihao.getText().toString().trim();
        req.city_code = city_no;
        editor.putString("carnumber", et_weizhang_carnum.getText().toString().trim());
        editor.putString("carcode", req.carcode);
        editor.putString("cardrivenumber", req.cardrivenumber);
        editor.putString("city_code", req.city_code);
        editor.putString("city_name", tv_weizhang_provincename.getText().toString().trim());
        editor.putString("city_jian", tv_weizhang_chepaijian.getText().toString().trim());
        editor.commit();
        KaKuApiProvider.IllegalQuery(req, new KakuResponseListener<IllegalQueryResp>(this, IllegalQueryResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("query res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        Intent intent = new Intent(context, IllegalQueryResultActivity.class);
                        intent.putExtra("t", t);
                        startActivity(intent);
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

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }


    public void ShowPop() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                new IllegalPopWindow(IllegalQueryActivity.this).show();

            }
        }, 0);
    }


}
