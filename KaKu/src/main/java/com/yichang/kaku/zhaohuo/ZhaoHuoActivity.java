package com.yichang.kaku.zhaohuo;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.home.AdActivity;
import com.yichang.kaku.obj.AreaObj;
import com.yichang.kaku.obj.ZhaoHuoObj;
import com.yichang.kaku.request.AreaReq;
import com.yichang.kaku.request.ZhaoHuoReq;
import com.yichang.kaku.response.AreaResp;
import com.yichang.kaku.response.ZhaoHuoResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yichang.kaku.zhaohuo.province.CityAdapter;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class ZhaoHuoActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private TextView tv_zone_left, tv_zone_mid, tv_zone_right;
    private XListView xListView;
    private List<ZhaoHuoObj> list_zhaohuo = new ArrayList<ZhaoHuoObj>();
    private final static int STEP = 6;
    private int start = 0, pageindex = 0, pagesize = STEP;
    private final static int INDEX = 6;// 一屏显示的个数
    private boolean isShowProgress = false;
    private RelativeLayout rela_zhaohuo_chufadi, rela_zhaohuo_mudidi, rela_zhaohuo_grid, rela_zhaohuo_chechang;
    private LineGridView gv_city;
    private CityAdapter adapter;
    private CheChangAdapter adapter_chechang;
    private List<AreaObj> list_province = new ArrayList<AreaObj>();
    private TextView tv_pup_left, tv_pup_mid, tv_pup_right;
    private String id_province, id_city, id_county;
    private String id_type = "province";
    private String chufadi_type = "chufadi";
    private String chufadi_id = "", mudidi_id = "";
    private TextView tv_zhaohuo_chufadi, tv_zhaohuo_mudidi, tv_zhaohuo_chechang;
    private List<String> list_chechang = new ArrayList<String>();
    private String left_name, right_name;
    private String flag_di;
    private String chengchang = "";
    //空白页面控件
    private RelativeLayout layout_data_none, layout_net_none;
    private TextView tv_desc, tv_advice;
    private Button btn_refresh;
    private LinearLayout ll_container;
    private LinearLayout rela_zone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhaohuo);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("找货");
        xListView = (XListView) findViewById(R.id.lv_zhaohuo);
        xListView.setOnItemClickListener(this);
        rela_zhaohuo_chufadi = (RelativeLayout) findViewById(R.id.rela_zhaohuo_chufadi);
        rela_zhaohuo_chufadi.setOnClickListener(this);
        rela_zhaohuo_mudidi = (RelativeLayout) findViewById(R.id.rela_zhaohuo_mudidi);
        rela_zhaohuo_mudidi.setOnClickListener(this);
        rela_zone = (LinearLayout) findViewById(R.id.rela_zone);
        rela_zhaohuo_grid = (RelativeLayout) findViewById(R.id.rela_zhaohuo_grid);
        rela_zhaohuo_grid.setOnClickListener(this);
        rela_zhaohuo_chechang = (RelativeLayout) findViewById(R.id.rela_zhaohuo_chechang);
        rela_zhaohuo_chechang.setOnClickListener(this);
        list_chechang.add("不限");
        list_chechang.add("3.3");
        list_chechang.add("3.8");
        list_chechang.add("4");
        list_chechang.add("4.2");
        list_chechang.add("4.3");
        list_chechang.add("4.5");
        list_chechang.add("4.8");
        list_chechang.add("5");
        list_chechang.add("5.2");
        list_chechang.add("5.4");
        list_chechang.add("5.8");
        list_chechang.add("6");
        list_chechang.add("6.2");
        list_chechang.add("6.8");
        list_chechang.add("7");
        list_chechang.add("7.2");
        list_chechang.add("7.4");
        list_chechang.add("7.6");
        list_chechang.add("7.7");
        list_chechang.add("7.8");
        list_chechang.add("8");
        list_chechang.add("8.6");
        list_chechang.add("8.7");
        list_chechang.add("8.8");
        list_chechang.add("9");
        list_chechang.add("9.2");
        list_chechang.add("9.6");
        list_chechang.add("10.5");
        list_chechang.add("12");
        list_chechang.add("12.5");
        list_chechang.add("13");
        list_chechang.add("14");
        list_chechang.add("16");
        list_chechang.add("17");
        list_chechang.add("17.5");
        list_chechang.add("18");
        list_chechang.add("20");

        gv_city = (LineGridView) findViewById(R.id.gv_city);
        gv_city.setOnItemClickListener(this);
        tv_pup_mid = (TextView) findViewById(R.id.tv_pup_mid);
        tv_pup_left = (TextView) findViewById(R.id.tv_pup_left);
        tv_pup_left.setOnClickListener(this);
        tv_pup_right = (TextView) findViewById(R.id.tv_pup_right);
        tv_pup_right.setOnClickListener(this);
        tv_zhaohuo_chufadi = (TextView) findViewById(R.id.tv_zhaohuo_chufadi);
        tv_zhaohuo_mudidi = (TextView) findViewById(R.id.tv_zhaohuo_mudidi);
        tv_zhaohuo_chechang = (TextView) findViewById(R.id.tv_zhaohuo_chechang);
        rela_zhaohuo_grid.setVisibility(View.GONE);
        id_type = "province";
        tv_pup_mid.setText("中国");
        tv_zhaohuo_chufadi.setText("出发地");
        tv_zhaohuo_mudidi.setText("目的地");
        tv_zhaohuo_chechang.setText("车长");
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
        if (R.id.rela_zhaohuo_chufadi == id) {
            tv_pup_mid.setText("中国");
            chufadi_type = "chufadi";
            GetProvince();
        } else if (R.id.rela_zhaohuo_mudidi == id) {
            tv_pup_mid.setText("中国");
            chufadi_type = "mudidi";
            GetProvince();
        } else if (R.id.tv_pup_left == id) {
            rela_zhaohuo_grid.setVisibility(View.GONE);
            id_type = "province";
            tv_pup_mid.setText("中国");
            SetEnable();
            tv_pup_right.setVisibility(View.GONE);
        } else if (R.id.btn_refresh == id) {
            setPullState(false);
        } else if (R.id.tv_pup_right == id) {
            if ("chufadi".equals(chufadi_type)) {
                tv_zhaohuo_chufadi.setText(left_name);
            } else {
                tv_zhaohuo_mudidi.setText(right_name);
            }
            rela_zhaohuo_grid.setVisibility(View.GONE);
            id_type = "province";
            tv_pup_mid.setText("中国");
            SetEnable();
            tv_pup_right.setVisibility(View.GONE);
            setPullState(false);
        } else if (R.id.rela_zhaohuo_chechang == id) {
            rela_zhaohuo_grid.setVisibility(View.VISIBLE);
            id_type = "chechang";
            tv_pup_mid.setText("车长");
            SetEnable();
            tv_pup_right.setVisibility(View.GONE);
            GetCheChang();
        } else if (R.id.rela_zhaohuo_grid == id) {
            rela_zhaohuo_grid.setVisibility(View.GONE);
            id_type = "province";
            tv_pup_mid.setText("中国");
            SetEnable();
            tv_pup_right.setVisibility(View.GONE);
        } else if (R.id.tv_left == id) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
            startActivity(intent);
            finish();
        }
    }

    public void ZhaoHuo(int pageIndex, int pageSize) {
        if (!Utils.checkNetworkConnection(context)) {
            setNoDataLayoutState(layout_net_none);

            return;
        } else {
            setNoDataLayoutState(ll_container);

        }
        ZhaoHuoReq req = new ZhaoHuoReq();
        req.code = "6001";
        req.id_depart = chufadi_id;
        req.id_arrive = mudidi_id;
        req.id_car_len = chengchang;
        req.start = String.valueOf(pageIndex);
        req.len = String.valueOf(pageSize);
        KaKuApiProvider.ZhaoHuo(req, new KakuResponseListener<ZhaoHuoResp>(context, ZhaoHuoResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("zhaohuo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t.supplys);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                    onLoadStop();
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }


        });
    }

    private void setData(List<ZhaoHuoObj> list) {
        // TODO Auto-generated method stub
        if (list != null) {
            list_zhaohuo.addAll(list);
        }
        if (list_zhaohuo.size() == 0) {
            setNoDataLayoutState(layout_data_none);
            return;
        } else {
            setNoDataLayoutState(ll_container);
        }
        ZhaoHuoAdapter adapter = new ZhaoHuoAdapter(context, list_zhaohuo);
        xListView.setAdapter(adapter);
        xListView.setPullLoadEnable(list.size() < INDEX ? false : true);
        xListView.setSelection(pageindex);
        xListView.setXListViewListener(new XListView.IXListViewListener() {

            @Override
            public void onRefresh() {
                if (!Utils.checkNetworkConnection(context)) {
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    setNoDataLayoutState(layout_net_none);
                    xListView.stopRefresh();
                    return;
                }
                setPullState(false);
            }

            @Override
            public void onLoadMore() {
                if (!Utils.checkNetworkConnection(context)) {
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    setNoDataLayoutState(layout_net_none);
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
            if (list_zhaohuo != null) {
                list_zhaohuo.clear();
            }
        }
        ZhaoHuo(pageindex, pagesize);
    }

    private void onLoadStop() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(DateUtil.dateFormat());
    }

    public void GoToLeft() {
        Intent intent = new Intent(context, MyHuoActivity.class);
        startActivity(intent);
    }

    public void GoToRight() {
        Intent intent = new Intent(context, MyCheYuanActivity.class);
        startActivity(intent);
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
                        rela_zhaohuo_grid.setVisibility(View.VISIBLE);
                        SetUnEnable();
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

    public void GetCheChang() {
        adapter_chechang = new CheChangAdapter(context, list_chechang);
        gv_city.setAdapter(adapter_chechang);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (Utils.Many()) {
            return;
        }
        int parentId = parent.getId();
        if (R.id.gv_city == parentId) {
            if ("province".equals(id_type)) {
                id_province = list_province.get(position).getId_area();
                tv_pup_mid.setText(list_province.get(position).getName_area());
                GetCity(id_province);
                id_type = "city";
                if ("chufadi".equals(chufadi_type)) {
                    chufadi_id = list_province.get(position).getId_area();
                    left_name = list_province.get(position).getName_area();
                } else {
                    mudidi_id = list_province.get(position).getId_area();
                    right_name = list_province.get(position).getName_area();
                }
            } else if ("city".equals(id_type)) {
                id_city = list_province.get(position).getId_area();
                tv_pup_mid.setText(list_province.get(position).getName_area());
                GetCounty(id_city);
                id_type = "county";
                if ("chufadi".equals(chufadi_type)) {
                    chufadi_id = list_province.get(position).getId_area();
                    left_name = list_province.get(position).getName_area();
                } else {
                    mudidi_id = list_province.get(position).getId_area();
                    right_name = list_province.get(position).getName_area();
                }
            } else if ("county".equals(id_type)) {
                id_county = list_province.get(position).getId_area();
                tv_pup_mid.setText(list_province.get(position).getName_area());
                id_type = "province";
                rela_zhaohuo_grid.setVisibility(View.GONE);
                SetEnable();
                if ("chufadi".equals(chufadi_type)) {
                    chufadi_id = list_province.get(position).getId_area();
                    left_name = list_province.get(position).getName_area();
                    tv_zhaohuo_chufadi.setText(left_name);
                } else {
                    mudidi_id = list_province.get(position).getId_area();
                    right_name = list_province.get(position).getName_area();
                    tv_zhaohuo_mudidi.setText(right_name);
                }
                setPullState(false);
            } else if ("chechang".equals(id_type)) {
                id_type = "province";
                chengchang = list_chechang.get(position);
                tv_zhaohuo_chechang.setText(chengchang);
                rela_zhaohuo_grid.setVisibility(View.GONE);
                SetEnable();
                setPullState(false);
            }

        } else if (R.id.lv_zhaohuo == parentId) {
            if ((position) % 6 == 0) {
                Intent intent = new Intent(this, AdActivity.class);
                intent.putExtra("url_ad", list_zhaohuo.get(position - 1).getUrl_roll());
                intent.putExtra("name_ad", list_zhaohuo.get(position - 1).getName_roll());
                startActivity(intent);
            } else {
                Intent intent = new Intent(context, HuoYuanDetailActivity.class);
                intent.putExtra("id_supply", list_zhaohuo.get(position - 1).getId_supply());
                startActivity(intent);
            }
        }
    }

    //设置空白页可视性
    private void setNoDataLayoutState(View view) {
        /*layout_data_none.setVisibility(View.GONE);
        ll_container.setVisibility(View.GONE);
		layout_net_none.setVisibility(View.GONE);

		view.setVisibility(View.VISIBLE);*/
    }

    public void SetUnEnable() {
        left.setEnabled(false);
        xListView.setEnabled(false);
        rela_zhaohuo_chufadi.setEnabled(false);
        rela_zhaohuo_mudidi.setEnabled(false);
        rela_zhaohuo_chechang.setEnabled(false);
    }

    public void SetEnable() {
        left.setEnabled(true);
        xListView.setEnabled(true);
        rela_zhaohuo_chufadi.setEnabled(true);
        rela_zhaohuo_mudidi.setEnabled(true);
        rela_zhaohuo_chechang.setEnabled(true);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME4);
            startActivity(intent);
            finish();
        }
        return true;
    }
}
