package com.yichang.kaku.home.baoyang;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.home.mycar.MyCarActivity;
import com.yichang.kaku.member.login.LoginActivity;
import com.yichang.kaku.obj.BaoYangShopObj;
import com.yichang.kaku.request.BaoYangReq;
import com.yichang.kaku.response.BaoYangResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class BaoYangActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private ImageView iv_baoyang_brand, iv_baoyang_huanche, iv_baoyang_oil, iv_baoyang_genghuan, iv_baoyang_zwdp,
            iv_baoyang_jgpx, iv_baoyang_pjpx, iv_baoyang_jlpx;
    private TextView tv_baoyang_brand, tv_baoyang_xilie, tv_baoyang_oilname, tv_baoyang_oilxilie;
    private List<BaoYangShopObj> shop_list = new ArrayList<>();
    private ListView lv_baoyang;
    private BaoYangShopAdapter adapter_baoyangshop;
    private String sort = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baoyang);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("换机油");
        if (!Utils.isLogin()) {
            startActivity(new Intent(context, LoginActivity.class));
            return;
        }
        KaKuApplication.flag_addcar = "huanjiyou";
        KaKuApplication.id_item = "";
        iv_baoyang_oil = (ImageView) findViewById(R.id.iv_baoyang_oil);
        iv_baoyang_brand = (ImageView) findViewById(R.id.iv_baoyang_brand);
        iv_baoyang_zwdp = (ImageView) findViewById(R.id.iv_baoyang_zwdp);
        iv_baoyang_jgpx = (ImageView) findViewById(R.id.iv_baoyang_jgpx);
        iv_baoyang_jgpx.setOnClickListener(this);
        iv_baoyang_pjpx = (ImageView) findViewById(R.id.iv_baoyang_pjpx);
        iv_baoyang_pjpx.setOnClickListener(this);
        iv_baoyang_jlpx = (ImageView) findViewById(R.id.iv_baoyang_jlpx);
        iv_baoyang_jlpx.setOnClickListener(this);
        iv_baoyang_huanche = (ImageView) findViewById(R.id.iv_baoyang_huanche);
        iv_baoyang_huanche.setOnClickListener(this);
        iv_baoyang_genghuan = (ImageView) findViewById(R.id.iv_baoyang_genghuan);
        iv_baoyang_genghuan.setOnClickListener(this);
        tv_baoyang_brand = (TextView) findViewById(R.id.tv_baoyang_brand);
        tv_baoyang_xilie = (TextView) findViewById(R.id.tv_baoyang_xilie);
        tv_baoyang_oilname = (TextView) findViewById(R.id.tv_baoyang_oilname);
        tv_baoyang_oilxilie = (TextView) findViewById(R.id.tv_baoyang_oilxilie);
        lv_baoyang = (ListView) findViewById(R.id.lv_baoyang);
        lv_baoyang.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBaoYang();
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            goToHome();
        } else if (R.id.iv_baoyang_huanche == id) {
            KaKuApplication.id_item = "";
            startActivity(new Intent(context, BaoYangChooseCarActivity.class));
        } else if (R.id.iv_baoyang_genghuan == id) {
            startActivity(new Intent(context, BaoYangChooseOilActivity.class));
        } else if (R.id.iv_baoyang_jgpx == id) {
            if (!"PU".equals(sort)) {
                sort = "PU";
            } else {
                sort = "PD";
            }
            getBaoYang();
        } else if (R.id.iv_baoyang_pjpx == id) {
            sort = "E";
            getBaoYang();
        } else if (R.id.iv_baoyang_jlpx == id) {
            sort = "D";
            getBaoYang();
        }
    }

    public void getBaoYang() {
        showProgressDialog();
        BaoYangReq req = new BaoYangReq();
        req.code = "400111";
        req.id_item = KaKuApplication.id_item;
        req.var_lat = Utils.getLat();
        req.var_lon = Utils.getLon();
        req.sort = sort;
        KaKuApiProvider.BaoYang(req, new KakuResponseListener<BaoYangResp>(context, BaoYangResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("BaoYang res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        SetText(t);
                    } else if (Constants.RES_ONE.equals(t.res)) {
                        startActivity(new Intent(context, MyCarActivity.class));
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
        });
    }

    public void SetText(BaoYangResp t) {
        KaKuApplication.id_item = t.item.getId_item();
        tv_baoyang_brand.setText(t.car.getName_brand());
        tv_baoyang_xilie.setText(t.car.getSeries_engine() + "      " + t.car.getNum_power());
        tv_baoyang_oilname.setText(t.item.getName_item());
        tv_baoyang_oilxilie.setText(t.item.getName1_item());
        BitmapUtil.getInstance(context).download(iv_baoyang_brand, KaKuApplication.qian_zhui + t.car.getImage_brand());
        BitmapUtil.getInstance(context).download(iv_baoyang_oil, KaKuApplication.qian_zhui + t.item.getImage_item());

        shop_list.clear();
        shop_list = t.shops;
        if (shop_list.size() == 0) {
            iv_baoyang_zwdp.setVisibility(View.VISIBLE);
            lv_baoyang.setVisibility(View.GONE);
        } else {
            iv_baoyang_zwdp.setVisibility(View.GONE);
            lv_baoyang.setVisibility(View.VISIBLE);
            adapter_baoyangshop = new BaoYangShopAdapter(context, shop_list);
            lv_baoyang.setAdapter(adapter_baoyangshop);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        KaKuApplication.flag_activity = "";
        KaKuApplication.id_baoyangshop = shop_list.get(position).getId_shop();
        startActivity(new Intent(context, BaoYangListActivity.class));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goToHome();
        }
        return false;
    }

    private void goToHome() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
