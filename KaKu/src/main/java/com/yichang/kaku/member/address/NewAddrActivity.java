package com.yichang.kaku.member.address;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.yichang.kaku.request.NewAddrReq;
import com.yichang.kaku.response.AreaResp;
import com.yichang.kaku.response.NewAddrResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yichang.kaku.zhaohuo.LineGridView;
import com.yichang.kaku.zhaohuo.province.CityAdapter;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class NewAddrActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private TextView tv_newaddr_name, tv_newaddr_phone, tv_newaddr_dizhi, tv_newaddr_area;
    private RelativeLayout rela_newaddr_name, rela_newaddr_phone, rela_newaddr_dizhi, rela_newaddr_area, rela_zhaohuo_grid;
    private Button btn_addr_save;
    private String string1;
    private String string2;
    private String string3;
    private TextView tv_pup_left, tv_pup_mid;
    private String id_type = "province";
    private String area_name1, area_name2, area_name3;
    private GridView gv_city;
    private String id_province, id_city, id_county;
    private List<AreaObj> list_province = new ArrayList<AreaObj>();
    private CityAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newaddr);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        id_county = KaKuApplication.id_area;
        if (KaKuApplication.isEditAddr) {
            title.setText("编辑收货地址");
        } else {
            title.setText("新建收货地址");
        }
        rela_newaddr_name = (RelativeLayout) findViewById(R.id.rela_newaddr_name);
        rela_newaddr_name.setOnClickListener(this);
        rela_newaddr_phone = (RelativeLayout) findViewById(R.id.rela_newaddr_phone);
        rela_newaddr_phone.setOnClickListener(this);
        rela_newaddr_dizhi = (RelativeLayout) findViewById(R.id.rela_newaddr_dizhi);
        rela_newaddr_dizhi.setOnClickListener(this);
        rela_newaddr_area = (RelativeLayout) findViewById(R.id.rela_newaddr_area);
        rela_newaddr_area.setOnClickListener(this);
        tv_newaddr_name = (TextView) findViewById(R.id.tv_newaddr_name);
        tv_newaddr_phone = (TextView) findViewById(R.id.tv_newaddr_phone);
        tv_newaddr_dizhi = (TextView) findViewById(R.id.tv_newaddr_dizhi);
        tv_newaddr_area = (TextView) findViewById(R.id.tv_newaddr_area);
        btn_addr_save = (Button) findViewById(R.id.btn_addr_save);
        btn_addr_save.setOnClickListener(this);
        tv_pup_mid = (TextView) findViewById(R.id.tv_pup_mid);
        tv_pup_left = (TextView) findViewById(R.id.tv_pup_left);
        tv_pup_left.setOnClickListener(this);
        gv_city = (LineGridView) findViewById(R.id.gv_city);
        gv_city.setOnItemClickListener(this);
        rela_zhaohuo_grid = (RelativeLayout) findViewById(R.id.rela_zhaohuo_grid);
        rela_zhaohuo_grid.setOnClickListener(this);
        rela_zhaohuo_grid.setVisibility(View.GONE);
        if ("new".equals(KaKuApplication.new_addr)) {
            tv_newaddr_name.setText("");
            tv_newaddr_phone.setText("");
            tv_newaddr_dizhi.setText("");
            tv_newaddr_area.setText("");
        } else {
            tv_newaddr_name.setText(KaKuApplication.name_addr);
            tv_newaddr_phone.setText(KaKuApplication.phone_addr);
            tv_newaddr_dizhi.setText(KaKuApplication.dizhi_addr);
            tv_newaddr_area.setText(KaKuApplication.area_addr);
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
        } else if (R.id.rela_newaddr_name == id) {
            ModifyData(0);
        } else if (R.id.rela_newaddr_phone == id) {
            ModifyData(1);
        } else if (R.id.rela_newaddr_dizhi == id) {
            ModifyData(2);
        } else if (R.id.btn_addr_save == id) {
            if (TextUtils.isEmpty(tv_newaddr_name.getText().toString().trim())) {
                LogUtil.showShortToast(context, "请填写收货人姓名");
                return;
            }
            if (TextUtils.isEmpty(tv_newaddr_phone.getText().toString().trim())) {
                LogUtil.showShortToast(context, "请填写联系方式");
                return;
            }
            if (TextUtils.isEmpty(tv_newaddr_dizhi.getText().toString().trim())) {
                LogUtil.showShortToast(context, "请填写详细地址");
                return;
            }
            if (TextUtils.isEmpty(tv_newaddr_area.getText().toString().trim())) {
                LogUtil.showShortToast(context, "请选择所在区域");
                return;
            }
            NewAddr();
        } else if (R.id.rela_newaddr_area == id) {
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

    public void ModifyData(int num) {
        Intent intent = new Intent(this, TextActivity.class);
        if (num == 0) {
            intent.putExtra("title", "name");
            KaKuApplication.new_nametext = tv_newaddr_name.getText().toString().trim();
        } else if (num == 1) {
            intent.putExtra("title", "phone");
            KaKuApplication.new_phonetext = tv_newaddr_phone.getText().toString().trim();
        } else if (num == 2) {
            intent.putExtra("title", "address");
            KaKuApplication.new_addrtext = tv_newaddr_dizhi.getText().toString().trim();
        }
        startActivityForResult(intent, num);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (data != null) {
                    string1 = data.getExtras().getString("string");
                    tv_newaddr_name.setText(string1);
                }
                break;

            case 1:
                if (data != null) {
                    string2 = data.getExtras().getString("string");
                    tv_newaddr_phone.setText(string2);
                }
                break;

            case 2:
                if (data != null) {
                    string3 = data.getExtras().getString("string");
                    tv_newaddr_dizhi.setText(string3);
                }
                break;

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void NewAddr() {
        Utils.NoNet(context);
        showProgressDialog();
        NewAddrReq req = new NewAddrReq();
        req.code = "10015";
        req.id_driver = Utils.getIdDriver();
        req.flag_default = KaKuApplication.flag_addr;
        req.id_addr = KaKuApplication.id_dizhi;
        req.addr = tv_newaddr_dizhi.getText().toString().trim();
        req.name_addr = tv_newaddr_name.getText().toString().trim();
        req.phone_addr = tv_newaddr_phone.getText().toString().trim();
        req.id_area = id_county;
        KaKuApiProvider.NewAddr(req, new KakuResponseListener<NewAddrResp>(this, NewAddrResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("newaddr res: " + t.res);
                    if (Constants.RES.equals(t.res) || Constants.RES_TWO.equals(t.res)) {
                        finish();

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
        if (Utils.Many()){
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
                tv_newaddr_area.setText(area_name1 + area_name2 + area_name3);
            }
        }
    }
}
