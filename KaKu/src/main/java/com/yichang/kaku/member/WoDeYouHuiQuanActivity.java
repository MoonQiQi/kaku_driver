package com.yichang.kaku.member;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.obj.YouHuiQuanObj;
import com.yichang.kaku.request.ExitReq;
import com.yichang.kaku.response.MyCouponResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class WoDeYouHuiQuanActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private ListView lv_youhuiquan;
    private List<YouHuiQuanObj> list_wsy = new ArrayList<>();
    private List<YouHuiQuanObj> list_ysy = new ArrayList<>();
    private List<YouHuiQuanObj> list_ygq = new ArrayList<>();
    private WoDeYouHuiQuanAdapter adapter;
    private ImageView iv_youhuiquan_no;
    private RelativeLayout rela_youhuiquan_list, rela_youhuiquan_1, rela_youhuiquan_2, rela_youhuiquan_3;
    private TextView tv_youhuiquan_1, tv_youhuiquan_2, tv_youhuiquan_3;
    private View view_youhuiquan1, view_youhuiquan2, view_youhuiquan3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wodeyouhuiquan);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("我的优惠券");
        iv_youhuiquan_no = (ImageView) findViewById(R.id.iv_youhuiquan_no);
        tv_youhuiquan_1 = (TextView) findViewById(R.id.tv_youhuiquan_1);
        tv_youhuiquan_2 = (TextView) findViewById(R.id.tv_youhuiquan_2);
        tv_youhuiquan_3 = (TextView) findViewById(R.id.tv_youhuiquan_3);
        rela_youhuiquan_list = (RelativeLayout) findViewById(R.id.rela_youhuiquan_list);
        rela_youhuiquan_1 = (RelativeLayout) findViewById(R.id.rela_youhuiquan_1);
        rela_youhuiquan_1.setOnClickListener(this);
        rela_youhuiquan_2 = (RelativeLayout) findViewById(R.id.rela_youhuiquan_2);
        rela_youhuiquan_2.setOnClickListener(this);
        rela_youhuiquan_3 = (RelativeLayout) findViewById(R.id.rela_youhuiquan_3);
        rela_youhuiquan_3.setOnClickListener(this);
        lv_youhuiquan = (ListView) findViewById(R.id.lv_youhuiquan);
        view_youhuiquan1 = findViewById(R.id.view_youhuiquan1);
        view_youhuiquan2 = findViewById(R.id.view_youhuiquan2);
        view_youhuiquan3 = findViewById(R.id.view_youhuiquan3);
        tv_youhuiquan_1.setTextColor(getResources().getColor(R.color.color_red));
        view_youhuiquan1.setVisibility(View.VISIBLE);
        GetYouHuiQuan();
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
        } else if (R.id.rela_youhuiquan_1 == id) {
            SetTextColor();
            tv_youhuiquan_1.setTextColor(getResources().getColor(R.color.color_red));
            view_youhuiquan1.setVisibility(View.VISIBLE);
            if (list_wsy.size() == 0) {
                iv_youhuiquan_no.setVisibility(View.VISIBLE);
                rela_youhuiquan_list.setVisibility(View.GONE);
                stopProgressDialog();
                return;
            }
            iv_youhuiquan_no.setVisibility(View.GONE);
            rela_youhuiquan_list.setVisibility(View.VISIBLE);
            adapter = new WoDeYouHuiQuanAdapter(context, list_wsy);
            lv_youhuiquan.setAdapter(adapter);
        } else if (R.id.rela_youhuiquan_2 == id) {
            SetTextColor();
            tv_youhuiquan_2.setTextColor(getResources().getColor(R.color.color_red));
            view_youhuiquan2.setVisibility(View.VISIBLE);
            if (list_ysy.size() == 0) {
                iv_youhuiquan_no.setVisibility(View.VISIBLE);
                rela_youhuiquan_list.setVisibility(View.GONE);
                stopProgressDialog();
                return;
            }
            iv_youhuiquan_no.setVisibility(View.GONE);
            rela_youhuiquan_list.setVisibility(View.VISIBLE);
            adapter = new WoDeYouHuiQuanAdapter(context, list_ysy);
            lv_youhuiquan.setAdapter(adapter);
        } else if (R.id.rela_youhuiquan_3 == id) {
            SetTextColor();
            tv_youhuiquan_3.setTextColor(getResources().getColor(R.color.color_red));
            view_youhuiquan3.setVisibility(View.VISIBLE);
            if (list_ygq.size() == 0) {
                iv_youhuiquan_no.setVisibility(View.VISIBLE);
                rela_youhuiquan_list.setVisibility(View.GONE);
                stopProgressDialog();
                return;
            }
            iv_youhuiquan_no.setVisibility(View.GONE);
            rela_youhuiquan_list.setVisibility(View.VISIBLE);
            adapter = new WoDeYouHuiQuanAdapter(context, list_ygq);
            lv_youhuiquan.setAdapter(adapter);
        }
    }

    public void GetYouHuiQuan() {
        showProgressDialog();
        ExitReq req = new ExitReq();
        req.code = "10034";
        KaKuApiProvider.MyCoupon(req, new KakuResponseListener<MyCouponResp>(this, MyCouponResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("youhuiquan res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        list_wsy = t.coupons_wsy;
                        list_ysy = t.coupons_ysy;
                        list_ygq = t.coupons_ygq;
                        if (list_wsy.size() == 0) {
                            iv_youhuiquan_no.setVisibility(View.VISIBLE);
                            rela_youhuiquan_list.setVisibility(View.GONE);
                            stopProgressDialog();
                            return;
                        }
                        iv_youhuiquan_no.setVisibility(View.GONE);
                        rela_youhuiquan_list.setVisibility(View.VISIBLE);
                        adapter = new WoDeYouHuiQuanAdapter(context, list_wsy);
                        lv_youhuiquan.setAdapter(adapter);
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

    public void SetTextColor() {
        tv_youhuiquan_1.setTextColor(getResources().getColor(R.color.black));
        tv_youhuiquan_2.setTextColor(getResources().getColor(R.color.black));
        tv_youhuiquan_3.setTextColor(getResources().getColor(R.color.black));
        view_youhuiquan1.setVisibility(View.GONE);
        view_youhuiquan2.setVisibility(View.GONE);
        view_youhuiquan3.setVisibility(View.GONE);
    }
}
