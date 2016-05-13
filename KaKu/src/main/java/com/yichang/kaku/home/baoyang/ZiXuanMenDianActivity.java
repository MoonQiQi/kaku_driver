package com.yichang.kaku.home.baoyang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.AreaObj;
import com.yichang.kaku.request.AreaReq;
import com.yichang.kaku.request.CommitShopReq;
import com.yichang.kaku.response.AreaResp;
import com.yichang.kaku.response.ExitResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yichang.kaku.zhaohuo.LineGridView;
import com.yichang.kaku.zhaohuo.province.CityAdapter;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class ZiXuanMenDianActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private EditText et_zixuanmendian1, et_zixuanmendian2, et_zixuanmendian4;
    private Button btn_zixuanmendian_commit;
    private TextView tv_zixuanmendian_people, tv_zixuanmendian_city;
    private TextView tv_pup_left, tv_pup_mid;
    private String id_type = "province";
    private String area_name1, area_name2, area_name3;
    private GridView gv_city;
    private String id_province, id_city, id_county;
    private List<AreaObj> list_province = new ArrayList<AreaObj>();
    private CityAdapter adapter;
    private RelativeLayout rela_zhaohuo_grid, rela_zixuanmendian_city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zixuanmendian);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("自选门店");
        et_zixuanmendian1 = (EditText) findViewById(R.id.et_zixuanmendian1);
        et_zixuanmendian2 = (EditText) findViewById(R.id.et_zixuanmendian2);
        et_zixuanmendian4 = (EditText) findViewById(R.id.et_zixuanmendian4);
        tv_zixuanmendian_people = (TextView) findViewById(R.id.tv_zixuanmendian_people);
        tv_zixuanmendian_city = (TextView) findViewById(R.id.tv_zixuanmendian_city);
        btn_zixuanmendian_commit = (Button) findViewById(R.id.btn_zixuanmendian_commit);
        btn_zixuanmendian_commit.setOnClickListener(this);
        String total_num = getIntent().getStringExtra("total_num");
        tv_zixuanmendian_people.setText("已有" + total_num + "位推荐自选门店");
        tv_pup_mid = (TextView) findViewById(R.id.tv_pup_mid);
        tv_pup_left = (TextView) findViewById(R.id.tv_pup_left);
        tv_pup_left.setOnClickListener(this);
        gv_city = (LineGridView) findViewById(R.id.gv_city);
        gv_city.setOnItemClickListener(this);
        rela_zixuanmendian_city = (RelativeLayout) findViewById(R.id.rela_zixuanmendian_city);
        rela_zixuanmendian_city.setOnClickListener(this);
        rela_zhaohuo_grid = (RelativeLayout) findViewById(R.id.rela_zhaohuo_grid);
        rela_zhaohuo_grid.setOnClickListener(this);
        rela_zhaohuo_grid.setVisibility(View.GONE);
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
        } else if (R.id.btn_zixuanmendian_commit == id) {
            if ("".equals(et_zixuanmendian1.getText().toString().trim()) || "".equals(et_zixuanmendian2.getText().toString().trim())
                    || "".equals(tv_zixuanmendian_city.getText().toString().trim()) || "请选择".equals(tv_zixuanmendian_city.getText().toString().trim()) || "".equals(et_zixuanmendian4.getText().toString().trim())) {
                LogUtil.showShortToast(context, "请填写门店信息");
                return;
            }
            Commit();
        } else if (R.id.rela_zixuanmendian_city == id) {
            tv_pup_mid.setText("中国");
            GetProvince();
        } else if (R.id.rela_zhaohuo_grid == id) {
            rela_zhaohuo_grid.setVisibility(View.GONE);
            id_type = "province";
            tv_pup_mid.setText("中国");
        } else if (R.id.tv_pup_left == id) {
            rela_zhaohuo_grid.setVisibility(View.GONE);
            id_type = "province";
            tv_pup_mid.setText("中国");
        }
    }

    public void Commit() {
        showProgressDialog();
        CommitShopReq req = new CommitShopReq();
        req.code = "40076";
        req.id_area = id_county;
        req.addr = et_zixuanmendian4.getText().toString().trim();
        req.name_addr = et_zixuanmendian1.getText().toString().trim();
        req.phone_addr = et_zixuanmendian2.getText().toString().trim();
        KaKuApiProvider.CommitMen(req, new KakuResponseListener<ExitResp>(context, ExitResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("commitmen res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.id_baoyangshop = "-1";
                        startActivity(new Intent(context, BaoYangOrderActivity.class));
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

    public void GetProvince() {
        Utils.NoNet(context);
        showProgressDialog();
        AreaReq req = new AreaReq();
        req.code = "10018";
        req.id_area = "0";
        KaKuApiProvider.Area(req, new KakuResponseListener<AreaResp>(context, AreaResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("area res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        list_province = t.areas;
                        adapter = new CityAdapter(context, list_province);
                        gv_city.setAdapter(adapter);
                        rela_zhaohuo_grid.setVisibility(View.VISIBLE);
                    } else {
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

    public void GetCity(String id_province) {
        Utils.NoNet(context);
        showProgressDialog();
        AreaReq req = new AreaReq();
        req.code = "10018";
        req.id_area = id_province;
        KaKuApiProvider.Area(req, new KakuResponseListener<AreaResp>(context, AreaResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("area res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        list_province = t.areas;
                        adapter = new CityAdapter(context, list_province);
                        gv_city.setAdapter(adapter);
                    } else {
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

    public void GetCounty(String id_city) {
        Utils.NoNet(context);
        showProgressDialog();
        AreaReq req = new AreaReq();
        req.code = "10018";
        req.id_area = id_city;
        KaKuApiProvider.Area(req, new KakuResponseListener<AreaResp>(context, AreaResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("area res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        list_province = t.areas;
                        adapter = new CityAdapter(context, list_province);
                        gv_city.setAdapter(adapter);
                    } else {
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Utils.Many()) {
            return;
        }
        int idParent = parent.getId();
        if (R.id.gv_city == idParent) {
            if ("province".equals(id_type)) {
                id_province = list_province.get(position).getId_area();
                tv_pup_mid.setText(list_province.get(position).getName_area());
                GetCity(id_province);
                id_type = "city";
                area_name1 = list_province.get(position).getName_area();
            } else if ("city".equals(id_type)) {
                id_city = list_province.get(position).getId_area();
                tv_pup_mid.setText(list_province.get(position).getName_area());
                GetCounty(id_city);
                id_type = "county";
                area_name2 = list_province.get(position).getName_area();
            } else if ("county".equals(id_type)) {
                id_county = list_province.get(position).getId_area();
                tv_pup_mid.setText(list_province.get(position).getName_area());
                id_type = "province";
                rela_zhaohuo_grid.setVisibility(View.GONE);
                area_name3 = list_province.get(position).getName_area();
                tv_zixuanmendian_city.setText(area_name1 + area_name2 + area_name3);
            }
        }
    }
}
