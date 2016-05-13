package com.yichang.kaku.home.baoyang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.shop.ShopItemAdapter;
import com.yichang.kaku.obj.AreaObj;
import com.yichang.kaku.obj.Shops_wxzObj;
import com.yichang.kaku.request.AreaReq;
import com.yichang.kaku.request.ChooseShopReq;
import com.yichang.kaku.response.AreaResp;
import com.yichang.kaku.response.ChooseShopResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yichang.kaku.zhaohuo.LineGridView;
import com.yichang.kaku.zhaohuo.province.CityAdapter;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class ChooseShopActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private RelativeLayout rela_chooseshop_city, rela_chooseshop_grid;
    private TextView tv_chooseshop_city;
    private ImageView iv_chooseshop_noshop;
    private XListView xListView;
    private final static int STEP = 6;
    private int start = 0, pageindex = 0, pagesize = STEP;
    private final static int INDEX = 6;// 一屏显示的个数
    private boolean isShowProgress = false;
    private LineGridView gv_city;
    private CityAdapter adapter;
    private List<Shops_wxzObj> list_mendian = new ArrayList<>();
    private List<AreaObj> list_province = new ArrayList<AreaObj>();
    private TextView tv_pup_left, tv_pup_mid, tv_pup_right;
    private String id_province, id_city, id_county;
    private String id_type = "province";
    private String id_area = "", name_area;
    private String total_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseshop);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("选择门店");
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("自选门店");
        right.setOnClickListener(this);
        tv_chooseshop_city = (TextView) findViewById(R.id.tv_chooseshop_city);
        iv_chooseshop_noshop = (ImageView) findViewById(R.id.iv_chooseshop_noshop);
        iv_chooseshop_noshop.setOnClickListener(this);
        xListView = (XListView) findViewById(R.id.lv_chooseshop_shop);
        xListView.setOnItemClickListener(this);
        rela_chooseshop_grid = (RelativeLayout) findViewById(R.id.rela_chooseshop_grid);
        rela_chooseshop_grid.setOnClickListener(this);
        rela_chooseshop_city = (RelativeLayout) findViewById(R.id.rela_chooseshop_city);
        rela_chooseshop_city.setOnClickListener(this);
        gv_city = (LineGridView) findViewById(R.id.gv_city);
        gv_city.setOnItemClickListener(this);
        tv_pup_mid = (TextView) findViewById(R.id.tv_pup_mid);
        tv_pup_left = (TextView) findViewById(R.id.tv_pup_left);
        tv_pup_left.setOnClickListener(this);
        tv_pup_right = (TextView) findViewById(R.id.tv_pup_right);
        tv_pup_right.setOnClickListener(this);
        rela_chooseshop_grid.setVisibility(View.GONE);
        id_type = "province";
        tv_pup_mid.setText("中国");
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
        } else if (R.id.rela_chooseshop_city == id) {
            tv_pup_mid.setText("中国");
            GetProvince();
        } else if (R.id.tv_right == id) {
            startActivity(new Intent(this, ZiXuanMenDianActivity.class));
        } else if (R.id.tv_pup_left == id) {
            rela_chooseshop_grid.setVisibility(View.GONE);
            id_type = "province";
            tv_pup_mid.setText("中国");
        } else if (R.id.tv_pup_right == id) {
            tv_chooseshop_city.setText(name_area);
            rela_chooseshop_grid.setVisibility(View.GONE);
            id_type = "province";
            tv_pup_mid.setText("中国");
            tv_pup_right.setVisibility(View.GONE);
            setPullState(false);
        } else if (R.id.iv_chooseshop_noshop == id) {
            Intent intent = new Intent(this, ZiXuanMenDianActivity.class);
            intent.putExtra("total_num", total_num);
            startActivity(intent);
        }
    }

    public void GetShop(int pageIndex, int pageSize) {
        showProgressDialog();
        ChooseShopReq req = new ChooseShopReq();
        req.code = "40074";
        req.id_area = id_area;
        req.lat = Utils.getLat();
        req.lon = Utils.getLon();
        req.start = String.valueOf(pageIndex);
        req.len = String.valueOf(pageSize);
        KaKuApiProvider.BYChooseShop(req, new KakuResponseListener<ChooseShopResp>(context, ChooseShopResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("zhaohuo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        total_num = t.total_num;
                        setData(t.shops);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                    onLoadStop();
                    stopProgressDialog();
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }


        });
    }

    private void setData(List<Shops_wxzObj> list) {
        // TODO Auto-generated method stub
        if (list != null) {
            list_mendian.addAll(list);
        }
        if (list_mendian.size() == 0) {
            iv_chooseshop_noshop.setVisibility(View.VISIBLE);
            return;
        } else {
            iv_chooseshop_noshop.setVisibility(View.GONE);
        }
        ShopItemAdapter adapter = new ShopItemAdapter(context, list_mendian);
        xListView.setAdapter(adapter);
        xListView.setPullLoadEnable(list.size() < INDEX ? false : true);
        xListView.setSelection(pageindex);
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
            if (list_mendian != null) {
                list_mendian.clear();
            }
        }
        GetShop(pageindex, pagesize);
    }

    private void onLoadStop() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(DateUtil.dateFormat());
    }

    public void GetProvince() {
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
                        rela_chooseshop_grid.setVisibility(View.VISIBLE);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                    stopProgressDialog();
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    public void GetCity(String id_province) {
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
                        tv_pup_right.setVisibility(View.VISIBLE);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                    stopProgressDialog();
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    public void GetCounty(String id_city) {
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
                    stopProgressDialog();
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int parentId = parent.getId();
        if (R.id.gv_city == parentId) {
            if ("province".equals(id_type)) {
                id_province = list_province.get(position).getId_area();
                tv_pup_mid.setText(list_province.get(position).getName_area());
                GetCity(id_province);
                id_type = "city";
                id_area = list_province.get(position).getId_area();
                name_area = list_province.get(position).getName_area();
            } else if ("city".equals(id_type)) {
                id_city = list_province.get(position).getId_area();
                tv_pup_mid.setText(list_province.get(position).getName_area());
                GetCounty(id_city);
                id_type = "county";
                id_area = list_province.get(position).getId_area();
                name_area = list_province.get(position).getName_area();
            } else if ("county".equals(id_type)) {
                id_county = list_province.get(position).getId_area();
                tv_pup_mid.setText(list_province.get(position).getName_area());
                id_type = "province";
                rela_chooseshop_grid.setVisibility(View.GONE);
                id_area = list_province.get(position).getId_area();
                name_area = list_province.get(position).getName_area();
                tv_chooseshop_city.setText(name_area);
                setPullState(false);
            }
        } else if (R.id.lv_chooseshop_shop == parentId) {
            KaKuApplication.id_baoyangshop = list_mendian.get(position - 1).getId_shop();
            startActivity(new Intent(this, BaoYangOrderActivity.class));
        }
    }
}
