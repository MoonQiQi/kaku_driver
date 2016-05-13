package com.yichang.kaku.home.baoyang;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.giftmall.ShopMallActivity;
import com.yichang.kaku.obj.BigOilObj;
import com.yichang.kaku.obj.BrandBigOilObj;
import com.yichang.kaku.request.BaoYangListReq;
import com.yichang.kaku.response.BaoYangHuoDongResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.DensityUtils;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.PullToRefreshView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class BaoYangHuoDongActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private ImageView iv_byhuodong_top;
    private GridView gv_byhuodong, gv_byhuodong_goods;
    private String id_brand = "";
    private PullToRefreshView mPullToRefreshView;
    private final static int STEP = 9;
    /*start:页码；pageindex:最后一条记录索引；pagesize：每页加载条目数量*/
    private int start = 0, pageindex = 0, pagesize = STEP;
    private final static int INDEX = 6;// 一屏显示的个数
    private List<BigOilObj> list_oil = new ArrayList<>();
    private List<BrandBigOilObj> list_brand = new ArrayList<>();
    private boolean isShowProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baoyanghuodong);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("做保养满千减百");
        iv_byhuodong_top = (ImageView) findViewById(R.id.iv_byhuodong_top);
        gv_byhuodong = (GridView) findViewById(R.id.gv_byhuodong_hori);
        gv_byhuodong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                id_brand = list_brand.get(position).getId_brand();
                setPullState(false);
            }
        });
        gv_byhuodong_goods = (GridView) findViewById(R.id.gv_byhuodong_goods);
        gv_byhuodong_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KaKuApplication.flag_activity = "Y";
                KaKuApplication.id_item = list_oil.get(position).getId_item();
                startActivity(new Intent(context, BaoYangListActivity.class));
            }
        });
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pulltofresh_byhuodong);
        mPullToRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                mPullToRefreshView.onHeaderRefreshComplete();
            }
        });
        mPullToRefreshView.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                if (!Utils.checkNetworkConnection(context)) {
                    stopProgressDialog();
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    return;
                }
                setPullState(true);
                mPullToRefreshView.onFooterRefreshComplete();
                //        设置最后一页时不能下拉刷新数据并提示
                if (list_oil.size() < INDEX) {
                    LogUtil.showShortToast(ShopMallActivity.context, "没有更多了");
                }
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
        }
    }

    public void getInfo(int pageIndex, int pageSize) {
        showProgressDialog();
        BaoYangListReq req = new BaoYangListReq();
        req.code = "400117";
        req.id_shop = KaKuApplication.id_baoyangshop;
        req.id_brand = id_brand;
        req.len = String.valueOf(pageSize);
        req.start = String.valueOf(pageIndex);
        KaKuApiProvider.ShopHuoDong(req, new KakuResponseListener<BaoYangHuoDongResp>(context, BaoYangHuoDongResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("ShopHuoDong res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        list_brand = t.brands;
                        BaoYangHuoDongHotAdapter adapter = new BaoYangHuoDongHotAdapter(context, list_brand);
                        int size = list_brand.size();
                        int length = DensityUtils.dp2px(context, 88);
                        DisplayMetrics dm = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(dm);
                        int gridviewWidth = (int) (size * (length + 1));
                        int itemWidth = (int) (length);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                        gv_byhuodong.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
                        gv_byhuodong.setColumnWidth(itemWidth); // 设置列表项宽
                        gv_byhuodong.setHorizontalSpacing(0); // 设置列表项水平间距
                        gv_byhuodong.setStretchMode(GridView.NO_STRETCH);
                        gv_byhuodong.setNumColumns(size); // 设置列数量=列表集合数
                        gv_byhuodong.setAdapter(adapter);
                        gv_byhuodong_goods.setFocusable(false);
                        BitmapUtil.getInstance(context).download(iv_byhuodong_top, KaKuApplication.qian_zhui + t.image_activity);
                        setData(t.items);
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

    private void setData(List<BigOilObj> list) {

        // TODO Auto-generated method stub
        if (list != null) {
            list_oil.addAll(list);
        } else {
            return;
        }
        ShopHuoDongAdapter adapter = new ShopHuoDongAdapter(context, list_oil);

        gv_byhuodong_goods.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        gv_byhuodong_goods.requestFocus();
        gv_byhuodong_goods.setItemChecked(pageindex + 1, true);
        gv_byhuodong_goods.setSelection(pageindex + 1);
        mPullToRefreshView.setEnablePullTorefresh(pageindex == 0 ? false : true);
        mPullToRefreshView.setEnablePullLoadMoreDataStatus(list.size() < INDEX ? false : true);
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
        getInfo(pageindex, pagesize);
    }

}
