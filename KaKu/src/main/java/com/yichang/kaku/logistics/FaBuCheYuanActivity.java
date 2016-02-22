package com.yichang.kaku.logistics;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.home.mycar.MyCarActivity;
import com.yichang.kaku.logistics.province.CityAdapter;
import com.yichang.kaku.obj.AreaObj;
import com.yichang.kaku.request.AreaReq;
import com.yichang.kaku.request.FaBuReq;
import com.yichang.kaku.response.AreaResp;
import com.yichang.kaku.response.FaBuResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class FaBuCheYuanActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private TextView tv_fabu_mudidi, tv_fabu_chufadi, tv_fabu_cheliang, tv_fabu_zhuanghuoshijian;
    private EditText et_fabu_beizhu, et_fabu_tujingchengshi;
    private Button btn_fabu_fabu;
    private RelativeLayout rela_fabu_grid, rela_fabu_zhuanghuoshijian, rela_fabu_car;
    private LineGridView gv_fabu_city;
    private CityAdapter adapter;
    private List<AreaObj> list_province = new ArrayList<AreaObj>();
    private TextView tv_pupfabu_left, tv_pup_mid;
    private String id_province, id_city, id_county;
    private String id_type = "province";
    private String chufadi_type = "chufadi";
    private TextView tv_fabu_zhuanghuoshijian1, tv_fabu_zhuanghuoshijian2;
    private String time = "", date = "";
    private String chufadi_id = "", mudidi_id = "";
    private String name_car = "", id_car = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabucheyuan);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("发布车源");
        tv_fabu_mudidi = (TextView) findViewById(R.id.tv_fabu_mudidi);
        tv_fabu_mudidi.setOnClickListener(this);
        tv_pupfabu_left = (TextView) findViewById(R.id.tv_pupfabu_left);
        tv_pupfabu_left.setOnClickListener(this);
        tv_fabu_chufadi = (TextView) findViewById(R.id.tv_fabu_chufadi);
        tv_fabu_chufadi.setOnClickListener(this);
        tv_pup_mid = (TextView) findViewById(R.id.tv_pupfabu_mid);
        tv_pup_mid.setOnClickListener(this);
        et_fabu_tujingchengshi = (EditText) findViewById(R.id.et_fabu_tujingchengshi);
        tv_fabu_cheliang = (TextView) findViewById(R.id.tv_fabu_cheliang);
        et_fabu_beizhu = (EditText) findViewById(R.id.et_fabu_beizhu);
        btn_fabu_fabu = (Button) findViewById(R.id.btn_fabu_fabu);
        btn_fabu_fabu.setOnClickListener(this);
        gv_fabu_city = (LineGridView) findViewById(R.id.gv_fabu_city);
        gv_fabu_city.setOnItemClickListener(this);
        rela_fabu_grid = (RelativeLayout) findViewById(R.id.rela_fabu_grid);
        rela_fabu_grid.setVisibility(View.GONE);
        rela_fabu_zhuanghuoshijian = (RelativeLayout) findViewById(R.id.rela_fabu_zhuanghuoshijian);
        rela_fabu_zhuanghuoshijian.setOnClickListener(this);
        rela_fabu_car = (RelativeLayout) findViewById(R.id.rela_fabu_car);
        rela_fabu_car.setOnClickListener(this);
        tv_fabu_zhuanghuoshijian1 = (TextView) findViewById(R.id.tv_fabu_zhuanghuoshijian1);
        tv_fabu_zhuanghuoshijian2 = (TextView) findViewById(R.id.tv_fabu_zhuanghuoshijian2);
        id_type = "province";
        tv_pup_mid.setText("中国");
        tv_fabu_chufadi.setText("出发地");
        tv_fabu_mudidi.setText("目的地");
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
        } else if (R.id.btn_fabu_fabu == id) {
            if ("".equals(tv_fabu_chufadi)) {
                LogUtil.showShortToast(context, "请选择出发地");
                return;
            }
            if ("".equals(tv_fabu_mudidi)) {
                LogUtil.showShortToast(context, "请选择目的地");
                return;
            }
            if (TextUtils.isEmpty(tv_fabu_zhuanghuoshijian1.getText().toString().trim())) {
                LogUtil.showShortToast(context, "请选择可装货时间");
                return;
            }
            if (TextUtils.isEmpty(tv_fabu_cheliang.getText().toString().trim())) {
                LogUtil.showShortToast(context, "请选择车辆");
                return;
            }
            FaBu();
        } else if (R.id.tv_fabu_mudidi == id) {
            chufadi_type = "mudidi";
            GetProvince();
        } else if (R.id.tv_pupfabu_left == id) {
            rela_fabu_grid.setVisibility(View.GONE);
            id_type = "province";
            tv_pup_mid.setText("中国");
        } else if (R.id.tv_fabu_chufadi == id) {
            chufadi_type = "chufadi";
            GetProvince();
        } else if (R.id.rela_fabu_zhuanghuoshijian == id) {
            GetTime();
        } else if (R.id.rela_fabu_car == id) {
            GoToMyLoveCar();
        }
    }

    public void FaBu() {
        Utils.NoNet(context);
        showProgressDialog();
        FaBuReq req = new FaBuReq();
        req.code = "6006";
        req.id_driver = Utils.getIdDriver();
        req.remark_options = et_fabu_beizhu.getText().toString().trim();
        req.areas_hsbc = et_fabu_tujingchengshi.getText().toString().trim();
        req.time_loading = tv_fabu_zhuanghuoshijian1.getText().toString().trim() + " " + tv_fabu_zhuanghuoshijian2.getText().toString().trim();
        req.id_area_depart = chufadi_id;
        req.id_area_arrive = mudidi_id;
        req.id_driver_car = id_car;
        KaKuApiProvider.FaBu(req, new BaseCallback<FaBuResp>(FaBuResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, FaBuResp t) {
                if (t != null) {
                    LogUtil.E("fabu res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        finish();
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
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });
    }

    public void GetProvince() {
        Utils.NoNet(context);
        showProgressDialog();
        AreaReq req = new AreaReq();
        req.code = "10018";
        req.id_area = "0";
        KaKuApiProvider.Area(req, new BaseCallback<AreaResp>(AreaResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, AreaResp t) {
                if (t != null) {
                    LogUtil.E("area res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        list_province = t.areas;
                        adapter = new CityAdapter(context, list_province);
                        gv_fabu_city.setAdapter(adapter);
                        rela_fabu_grid.setVisibility(View.VISIBLE);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
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

    public void GetCity(String id_province) {
        Utils.NoNet(context);
        showProgressDialog();
        AreaReq req = new AreaReq();
        req.code = "10018";
        req.id_area = id_province;
        KaKuApiProvider.Area(req, new BaseCallback<AreaResp>(AreaResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, AreaResp t) {
                if (t != null) {
                    LogUtil.E("area res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        list_province = t.areas;
                        adapter = new CityAdapter(context, list_province);
                        gv_fabu_city.setAdapter(adapter);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
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

    public void GetCounty(String id_city) {
        Utils.NoNet(context);
        showProgressDialog();
        AreaReq req = new AreaReq();
        req.code = "10018";
        req.id_area = id_city;
        KaKuApiProvider.Area(req, new BaseCallback<AreaResp>(AreaResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, AreaResp t) {
                if (t != null) {
                    LogUtil.E("area res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        list_province = t.areas;
                        adapter = new CityAdapter(context, list_province);
                        gv_fabu_city.setAdapter(adapter);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Utils.Many()) {
            return;
        }
        if ("province".equals(id_type)) {
            id_province = list_province.get(position).getId_area();
            tv_pup_mid.setText(list_province.get(position).getName_area());
            GetCity(id_province);
            id_type = "city";
        } else if ("city".equals(id_type)) {
            id_city = list_province.get(position).getId_area();
            tv_pup_mid.setText(list_province.get(position).getName_area());
            GetCounty(id_city);
            id_type = "county";
        } else if ("county".equals(id_type)) {
            id_county = list_province.get(position).getId_area();
            tv_pup_mid.setText(list_province.get(position).getName_area());
            id_type = "province";
            rela_fabu_grid.setVisibility(View.GONE);
            if ("chufadi".equals(chufadi_type)) {
                chufadi_id = list_province.get(position).getId_area();
                tv_fabu_chufadi.setText(list_province.get(position).getName_area());
            } else {
                mudidi_id = list_province.get(position).getId_area();
                tv_fabu_mudidi.setText(list_province.get(position).getName_area());
            }
        }
    }

    public void GetTime() {
        Intent intent = new Intent(context, FaBuTimeActivity.class);
        startActivityForResult(intent, 0);
    }

    public void GoToMyLoveCar() {
        Intent intent = new Intent(context, MyCarActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (data != null) {
                    time = data.getExtras().getString("time");
                    date = data.getExtras().getString("date");
                    tv_fabu_zhuanghuoshijian1.setText(date);
                    tv_fabu_zhuanghuoshijian2.setText(time);
                }
                break;

            case 1:
                if (data != null) {
                    id_car = data.getExtras().getString("id_car");
                    name_car = data.getExtras().getString("name_car");
                    tv_fabu_cheliang.setText(name_car);
                }
                break;
        }
    }
}
