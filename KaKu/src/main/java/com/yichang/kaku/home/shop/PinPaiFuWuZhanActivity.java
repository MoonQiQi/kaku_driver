package com.yichang.kaku.home.shop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.SouSuoActivity;
import com.yichang.kaku.home.mycar.PinPaiZiAdapter;
import com.yichang.kaku.zhaohuo.LineGridView;
import com.yichang.kaku.zhaohuo.province.CityAdapter;
import com.yichang.kaku.obj.AreaObj;
import com.yichang.kaku.obj.PinPaiXuanZeObj;
import com.yichang.kaku.obj.Shops_wxzObj;
import com.yichang.kaku.request.AreaReq;
import com.yichang.kaku.request.PinPaiFuWuZhanReq;
import com.yichang.kaku.request.PinPaiXuanZeReq;
import com.yichang.kaku.response.AreaResp;
import com.yichang.kaku.response.PinPaiFuWuZhanResp;
import com.yichang.kaku.response.PinPaiXuanZeResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.AreaPopWindow;
import com.yichang.kaku.view.widget.MenDianPopWindow;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class PinPaiFuWuZhanActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private final static int STEP = 5;
    private int start = 0, pageindex = 0, pagesize = STEP;
    private final static int INDEX = 5;// 一屏显示的个数
    private RelativeLayout rela_ppfwz_quanbudiqu, rela_ppfwz_pinpai, rela_ppfwz_tuijianpaixu,rela_zhaohuo_grid;
    private TextView tv_ppfwz_quanbudiqu,tv_ppfwz_pinpai,tv_ppfwz_tuijianpaixu;
    private XListView xListView;
    private String id_area = "";
    private String id_brand = "";
    private String flag_type = "";
    private boolean isShowProgress = false;
    private List<Shops_wxzObj> list_shop = new ArrayList<Shops_wxzObj>();
    private TextView tv_pup_left,tv_pup_mid,tv_pup_right;
    private String id_province,id_city,id_county;
    private String id_type = "province";
    private LineGridView gv_city;
    private CityAdapter adapter;
    private PinPaiZiAdapter adapter_pinpai;
    private List<AreaObj> list_province = new ArrayList<AreaObj>();
    private List<PinPaiXuanZeObj> list_pinpai = new ArrayList<PinPaiXuanZeObj>();
    private String tv_name;
    private Boolean isPwdPopWindowShow = false;
    private Boolean isAreaPopWindowShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinpaifuwuzhan);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        left.setText("");
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("地图");
        right.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setBackgroundResource(R.drawable.sousuofuwuzhan);
        title.setText("");
        title.setOnClickListener(this);
        tv_ppfwz_quanbudiqu= (TextView) findViewById(R.id.tv_ppfwz_quanbudiqu);
        tv_ppfwz_pinpai= (TextView) findViewById(R.id.tv_ppfwz_pinpai);
        tv_ppfwz_tuijianpaixu= (TextView) findViewById(R.id.tv_ppfwz_tuijianpaixu);
        rela_ppfwz_quanbudiqu = (RelativeLayout) findViewById(R.id.rela_ppfwz_quanbudiqu);
        rela_ppfwz_quanbudiqu.setOnClickListener(this);
        rela_ppfwz_pinpai = (RelativeLayout) findViewById(R.id.rela_ppfwz_pinpai);
        rela_ppfwz_pinpai.setOnClickListener(this);
        rela_ppfwz_tuijianpaixu = (RelativeLayout) findViewById(R.id.rela_ppfwz_tuijianpaixu);
        rela_ppfwz_tuijianpaixu.setOnClickListener(this);
        xListView = (XListView) findViewById(R.id.lv_ppfwz);
        xListView.setOnItemClickListener(this);
        tv_pup_mid= (TextView) findViewById(R.id.tv_pupp_mid);
        tv_pup_left= (TextView) findViewById(R.id.tv_pupp_left);
        tv_pup_left.setOnClickListener(this);
        tv_pup_right= (TextView) findViewById(R.id.tv_pupp_right);
        tv_pup_right.setOnClickListener(this);
        gv_city = (LineGridView)findViewById(R.id.gv_shoplist_city);
        gv_city.setOnItemClickListener(this);
        rela_zhaohuo_grid= (RelativeLayout) findViewById(R.id.rela_shoplist_grid);
        rela_zhaohuo_grid.setOnClickListener(this);
        tv_pup_right.setVisibility(View.GONE);
        setPullState(false);
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
            MobclickAgent.onEvent(context, "Map");
            GoToMap();
        } else if (R.id.rela_ppfwz_quanbudiqu == id){
            //flag_type = "";
            //GetProvince();
            showAreaWindow();
        } else if (R.id.rela_ppfwz_pinpai == id){
            flag_type = "";
            PinPaiXuanZe();
        } else if (R.id.rela_ppfwz_tuijianpaixu == id){
            flag_type = "1";
            setPullState(false);
        } else if (R.id.tv_pupp_left == id){
            rela_zhaohuo_grid.setVisibility(View.GONE);
            id_type = "province";
            tv_pup_mid.setText("品牌");
            tv_pup_right.setVisibility(View.GONE);
        } else if (R.id.tv_pupp_right == id){
            tv_ppfwz_quanbudiqu.setText(tv_name);
            rela_zhaohuo_grid.setVisibility(View.GONE);
            tv_pup_mid.setText("中国");
            tv_pup_right.setVisibility(View.GONE);
            setPullState(false);
        } else if (R.id.tv_mid == id){
            startActivity(new Intent(this,SouSuoActivity.class));
        }
    }

    public void PinPaiFuWuZhan(int pageIndex, int pageSize) {
        Utils.NoNet(this);
        showProgressDialog();
        PinPaiFuWuZhanReq req = new PinPaiFuWuZhanReq();
        req.code = "8003";
        req.id_car = Utils.getIdCar();
        req.id_driver = Utils.getIdDriver();
        req.lat = Utils.getLat();
        req.lon = Utils.getLon();
        req.start = String.valueOf(pageIndex);
        req.len = String.valueOf(pageSize);
        req.flag_type = flag_type;
        req.id_brand = id_brand;
        req.id_area = id_area;
        KaKuApiProvider.PinPaiFuWuZhan(req, new BaseCallback<PinPaiFuWuZhanResp>(PinPaiFuWuZhanResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, PinPaiFuWuZhanResp t) {
                if (t != null) {
                    LogUtil.E("pinpaifuwuzhan res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        if (TextUtils.equals(t.flag_enter,"N")){
                            if (!"".equals(Utils.getIdCar())) {
                                showPwdInputWindow(t.mobile_brand);
                            }
                        }
                        setData(t.shops);
                        onLoadStop();
                    }  else {
                        if (Constants.RES_TEN.equals(t.res)){
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

    private void setData(List<Shops_wxzObj> list) {
        // TODO Auto-generated method stub
        if (list != null) {
            list_shop.addAll(list);
        }
        ShopItemAdapter adapter = new ShopItemAdapter(context, list_shop);
        xListView.setAdapter(adapter);
        xListView.setPullLoadEnable(list.size() < INDEX ? false : true);
        xListView.setSelection(pageindex-2);
        xListView.setXListViewListener(new XListView.IXListViewListener() {

            @Override
            public void onRefresh() {
                if (!Utils.checkNetworkConnection(context)) {
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    xListView.stopRefresh();
                    return;
                }
                setPullState(false);
            }

            @Override
            public void onLoadMore() {
                if (!Utils.checkNetworkConnection(context)) {
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    xListView.stopLoadMore();
                    return;
                }
                setPullState(true);
            }
        });
    }

    private void setPullState(boolean isUp) {
        if (isUp) {
            isShowProgress = true;
            start++;
            pageindex = start * STEP;
        } else {
            start = 0;
            pageindex = 0;
            if (list_shop != null) {
                list_shop.clear();
            }
        }
        PinPaiFuWuZhan(pageindex, pagesize);
    }

    private void onLoadStop() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(DateUtil.dateFormat());
    }

    public void GoToMap() {
        Intent intent = new Intent(this, FWZMapActivity.class);
        startActivity(intent);
    }

    public void GetProvince(){
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
                        if (t.areas.size() != 0){
                            list_province = t.areas;
                            id_type = "province";
                            adapter = new CityAdapter(context,list_province);
                            gv_city.setAdapter(adapter);
                            rela_zhaohuo_grid.setVisibility(View.VISIBLE);
                        }
                    }else {
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

    public void GetCity(String id_province){
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
                        if (t.areas.size() != 0) {
                            list_province = t.areas;
                            adapter = new CityAdapter(context, list_province);
                            gv_city.setAdapter(adapter);
                            tv_pup_right.setVisibility(View.VISIBLE);
                        }
                    }else {
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

    public void GetCounty(String id_city){
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
                        if (t.areas.size() != 0) {
                            list_province = t.areas;
                            adapter = new CityAdapter(context, list_province);
                            gv_city.setAdapter(adapter);
                        }
                    }else {
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
        int parentId = parent.getId();
        if (R.id.gv_shoplist_city == parentId) {
            if ("province".equals(id_type)) {
                id_province = list_province.get(position).getId_area();
                tv_pup_mid.setText(list_province.get(position).getName_area());
                GetCity(id_province);
                id_type = "city";
                id_area = list_province.get(position).getId_area();
                tv_name = list_province.get(position).getName_area();
            } else if ("city".equals(id_type)) {
                id_city = list_province.get(position).getId_area();
                tv_pup_mid.setText(list_province.get(position).getName_area());
                GetCounty(id_city);
                id_type = "county";
                id_area = list_province.get(position).getId_area();
                tv_name = list_province.get(position).getName_area();
            } else if ("county".equals(id_type)) {
                id_county = list_province.get(position).getId_area();
                tv_pup_mid.setText(list_province.get(position).getName_area());
                id_type = "province";
                rela_zhaohuo_grid.setVisibility(View.GONE);
                id_area = list_province.get(position).getId_area();
                tv_name = list_province.get(position).getName_area();
                tv_ppfwz_quanbudiqu.setText(tv_name);
                setPullState(false);
            } else if ("pinpai".equals(id_type)) {
                id_brand = list_pinpai.get(position).getId_brand();
                rela_zhaohuo_grid.setVisibility(View.GONE);
                tv_ppfwz_pinpai.setText(list_pinpai.get(position).getName_brand());
                setPullState(false);
            }

        } else if (R.id.lv_ppfwz == parentId){
            KaKuApplication.id_shop = list_shop.get(position-1).getId_shop();
            startActivity(new Intent(this,ShopDetailActivity.class));
        }
    }

    public void PinPaiXuanZe(){
        showProgressDialog();
        PinPaiXuanZeReq req = new PinPaiXuanZeReq();
        req.code = "2008";
        KaKuApiProvider.PinPaiXuanZe(req, new BaseCallback<PinPaiXuanZeResp>(PinPaiXuanZeResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, PinPaiXuanZeResp t) {
                if (t != null) {
                    LogUtil.E("area res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        if (t.brands.size() != 0) {
                            list_pinpai = t.brands;
                            id_type = "pinpai";
                            adapter_pinpai = new PinPaiZiAdapter(context, list_pinpai);
                            gv_city.setAdapter(adapter_pinpai);
                            rela_zhaohuo_grid.setVisibility(View.VISIBLE);
                        }
                    }else {
                        if (Constants.RES_TEN.equals(t.res)){
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

            }
        });
    }

    private void showPwdInputWindow(final String phone) {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                isPwdPopWindowShow = true;

                MenDianPopWindow input =
                        new MenDianPopWindow(PinPaiFuWuZhanActivity.this ,phone);

                input.show();

            }
        }, 200);
    }

    private void showAreaWindow() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                isAreaPopWindowShow = true;

                final AreaPopWindow input =
                        new AreaPopWindow(PinPaiFuWuZhanActivity.this);

                input.show();

                input.setShowPopWindowCallBack(new AreaPopWindow.ShowPopWindowCallBack() {

                    @Override
                    public void close(String sName, String mId) {
                        input.dismiss();
                        id_area = mId;
                        tv_ppfwz_quanbudiqu.setText(sName);
                        setPullState(false);
                    }

                });

            }
        }, 200);
    }

}
