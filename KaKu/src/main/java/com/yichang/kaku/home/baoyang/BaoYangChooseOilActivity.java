package com.yichang.kaku.home.baoyang;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.BigOilObj;
import com.yichang.kaku.obj.BrandBigOilObj;
import com.yichang.kaku.request.BaoYangListReq;
import com.yichang.kaku.response.BaoYangChangeListResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class BaoYangChooseOilActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private XListView xListView;
    private List<BigOilObj> list_oil = new ArrayList<>();
    private List<BrandBigOilObj> list_brand = new ArrayList<>();
    private String id_brand = "";
    private GridView gv_baoyang_change;
    private final static int STEP = 5;
    private int start = 0, pageindex = 0, pagesize = STEP;
    private final static int INDEX = 5;// 一屏显示的个数
    private boolean isShowProgress = false;
    private TextView tv_baoyang_change;
    private BrandBigOilAdapter adapter1;
    private int gv_position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baoyangchangelist);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("可更换商品");
        tv_baoyang_change = (TextView) findViewById(R.id.tv_baoyang_change);
        tv_baoyang_change.setOnClickListener(this);
        xListView = (XListView) findViewById(R.id.lv_baoyang_change);
        gv_baoyang_change = (GridView) findViewById(R.id.gv_baoyang_change);
        gv_baoyang_change.setVisibility(View.GONE);
        gv_baoyang_change.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                id_brand = list_brand.get(position).getId_brand();
                gv_position = position;
                gv_baoyang_change.setVisibility(View.GONE);
                tv_baoyang_change.setText(list_brand.get(position).getName_brand());
                Drawable drawable = getResources().getDrawable(R.drawable.hongsanjiaoxia);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_baoyang_change.setCompoundDrawables(null, null, drawable, null);
                start = 0;
                pageindex = 0;
                setPullState(false);
            }
        });
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KaKuApplication.id_item = list_oil.get(position - 1).getId_item();
                finish();
            }
        });
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
        } else if (R.id.tv_baoyang_change == id) {
            if (gv_baoyang_change.getVisibility() == 0) {
                gv_baoyang_change.setVisibility(View.GONE);
                Drawable drawable = getResources().getDrawable(R.drawable.hongsanjiaoxia);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_baoyang_change.setCompoundDrawables(null, null, drawable, null);
            } else {
                gv_baoyang_change.setVisibility(View.VISIBLE);
                Drawable drawable = getResources().getDrawable(R.drawable.hongsanjiaoshang);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_baoyang_change.setCompoundDrawables(null, null, drawable, null);
            }
        }
    }

    public void GetList(int pageIndex, int pageSize) {
        showProgressDialog();
        BaoYangListReq req = new BaoYangListReq();
        req.code = "400112";
        req.id_brand = id_brand;
        req.id_item = KaKuApplication.id_item;
        req.start = String.valueOf(pageIndex);
        req.len = String.valueOf(pageSize);
        KaKuApiProvider.BaoYangChangeOil(req, new KakuResponseListener<BaoYangChangeListResp>(context, BaoYangChangeListResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("BaoYangChangeOil res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t.items);
                        list_brand = t.brands;
                        if (gv_position != -1) {
                            for (int i = 0; i < list_brand.size(); i++) {
                                list_brand.get(i).setFlag("B");
                            }
                            list_brand.get(gv_position).setFlag("H");
                        }
                        adapter1 = new BrandBigOilAdapter(context, list_brand);
                        gv_baoyang_change.setAdapter(adapter1);
                        onLoadStop();
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

    private void setData(List<BigOilObj> list) {
        // TODO Auto-generated method stub
        if (list != null) {
            list_oil.addAll(list);
        }
        BaoYangChooseOilAdapter adapter = new BaoYangChooseOilAdapter(context, list_oil);
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
            if (list_oil != null) {
                list_oil.clear();
            }
        }
        GetList(pageindex, pagesize);
    }

    private void onLoadStop() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(DateUtil.dateFormat());
    }

}
