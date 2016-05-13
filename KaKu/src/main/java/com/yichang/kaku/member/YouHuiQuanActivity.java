package com.yichang.kaku.member;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.YouHuiQuanObj;
import com.yichang.kaku.request.YouHuiQuanReq;
import com.yichang.kaku.response.YouHuiQuanResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class YouHuiQuanActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private ListView lv_youhuiquan;
    private List<YouHuiQuanObj> list_youhuiquan = new ArrayList<>();
    private Button btn_youhuiquan_ok;
    private YouHuiQuanAdapter adapter;
    private String id_coupon, name_driver_coupon;
    private ImageView iv_youhuiquan_no;
    private RelativeLayout rela_youhuiquan_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhuiquan);
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("我的优惠券");
        iv_youhuiquan_no = (ImageView) findViewById(R.id.iv_youhuiquan_no);
        rela_youhuiquan_list = (RelativeLayout) findViewById(R.id.rela_youhuiquan_list);
        lv_youhuiquan = (ListView) findViewById(R.id.lv_youhuiquan);
        lv_youhuiquan.setOnItemClickListener(this);
        btn_youhuiquan_ok = (Button) findViewById(R.id.btn_youhuiquan_ok);
        btn_youhuiquan_ok.setOnClickListener(this);
        if ("gift".equals(KaKuApplication.flag_coupon)) {
            GetYouHuiQuan();
        } else {
            GetYouHuiQuan2();
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
        } else if (R.id.btn_youhuiquan_ok == id) {
            if ("gift".equals(KaKuApplication.flag_coupon)) {
                KaKuApplication.id_driver_coupon = id_coupon;
                KaKuApplication.name_driver_coupon = name_driver_coupon;
                finish();
            } else if ("service".equals(KaKuApplication.flag_coupon)) {
                KaKuApplication.id_service_coupon = id_coupon;
                KaKuApplication.name_service_coupon = name_driver_coupon;
                finish();
            } else if ("upkeep".equals(KaKuApplication.flag_coupon)) {
                KaKuApplication.id_upkeep_coupon = id_coupon;
                KaKuApplication.name_upkeep_coupon = name_driver_coupon;
                finish();
            }
        }
    }

    public void GetYouHuiQuan() {
        showProgressDialog();
        YouHuiQuanReq req = new YouHuiQuanReq();
        req.code = "40010";
        req.id_goods = KaKuApplication.id_goods;
        KaKuApiProvider.GetYouHuiQuan(req, new KakuResponseListener<YouHuiQuanResp>(this, YouHuiQuanResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("youhuiquan res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        if (t.coupons.size() == 0) {
                            iv_youhuiquan_no.setVisibility(View.VISIBLE);
                            rela_youhuiquan_list.setVisibility(View.GONE);
                            stopProgressDialog();
                            return;
                        }
                        iv_youhuiquan_no.setVisibility(View.GONE);
                        rela_youhuiquan_list.setVisibility(View.VISIBLE);
                        list_youhuiquan = t.coupons;
                        adapter = new YouHuiQuanAdapter(context, list_youhuiquan);
                        lv_youhuiquan.setAdapter(adapter);
                        if (!"0".equals(KaKuApplication.id_driver_coupon)) {
                            for (int i = 0; i < list_youhuiquan.size(); i++) {
                                if (KaKuApplication.id_driver_coupon.equals(list_youhuiquan.get(i).getId_driver_coupon())) {
                                    list_youhuiquan.get(i).setFlag_usable("O");
                                    id_coupon = list_youhuiquan.get(i).getId_driver_coupon();
                                }
                            }
                        }
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

    public void GetYouHuiQuan2() {
        showProgressDialog();
        YouHuiQuanReq req = new YouHuiQuanReq();
        req.code = "400124";
        req.price_service = KaKuApplication.price_service;
        if ("service".equals(KaKuApplication.flag_coupon)) {
            if ("11".equals(KaKuApplication.yellowoil)) {
                req.type_coupon = "B";
            } else if ("12".equals(KaKuApplication.yellowoil)) {
                req.type_coupon = "T";
            } else if ("13".equals(KaKuApplication.yellowoil)) {
                req.type_coupon = "W";
            }
        } else if ("upkeep".equals(KaKuApplication.flag_coupon)) {
            req.type_coupon = "U";
        }
        KaKuApiProvider.GetYouHuiQuan2(req, new KakuResponseListener<YouHuiQuanResp>(this, YouHuiQuanResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("youhuiquan res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        if (t.coupons.size() == 0) {
                            iv_youhuiquan_no.setVisibility(View.VISIBLE);
                            rela_youhuiquan_list.setVisibility(View.GONE);
                            stopProgressDialog();
                            return;
                        }
                        iv_youhuiquan_no.setVisibility(View.GONE);
                        rela_youhuiquan_list.setVisibility(View.VISIBLE);
                        list_youhuiquan = t.coupons;
                        adapter = new YouHuiQuanAdapter(context, list_youhuiquan);
                        lv_youhuiquan.setAdapter(adapter);
                        if (!"".equals(KaKuApplication.id_service_coupon)) {
                            for (int i = 0; i < list_youhuiquan.size(); i++) {
                                if (KaKuApplication.id_service_coupon.equals(list_youhuiquan.get(i).getId_driver_coupon())) {
                                    list_youhuiquan.get(i).setFlag_usable("O");
                                    id_coupon = list_youhuiquan.get(i).getId_driver_coupon();
                                }
                            }
                        }
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
        if ("Y".equals(list_youhuiquan.get(position).getFlag_usable())) {
            for (int i = 0; i < list_youhuiquan.size(); i++) {
                if ("Y".equals(list_youhuiquan.get(i).getFlag_usable()) || "O".equals(list_youhuiquan.get(i).getFlag_usable())) {
                    list_youhuiquan.get(i).setFlag_usable("Y");
                }
            }
            list_youhuiquan.get(position).setFlag_usable("O");
            id_coupon = list_youhuiquan.get(position).getId_driver_coupon();
            name_driver_coupon = list_youhuiquan.get(position).getName_coupon();
            adapter.notifyDataSetChanged();
        } else if ("O".equals(list_youhuiquan.get(position).getFlag_usable())) {
            for (int i = 0; i < list_youhuiquan.size(); i++) {
                if ("O".equals(list_youhuiquan.get(i).getFlag_usable())) {
                    list_youhuiquan.get(i).setFlag_usable("Y");
                }
            }
            id_coupon = "0";
            name_driver_coupon = "未使用";
            adapter.notifyDataSetChanged();
        }

    }
}
