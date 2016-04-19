package com.yichang.kaku.home.faxian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.DiscoveryItemObj;
import com.yichang.kaku.request.DiscoveryCancelFavorReq;
import com.yichang.kaku.request.DiscoveryListReq;
import com.yichang.kaku.response.DiscoveryCancelFavorResp;
import com.yichang.kaku.response.DiscoveryListResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.util.ArrayList;
import java.util.List;

public class DiscoveryFavorActivity extends BaseActivity implements OnClickListener, OnItemClickListener {

    private TextView left, right;
    private TextView title;
    private XListView xListView;
    private final static int STEP = 5;// 每次加载5条
    private int start = 0, pageindex = 0, pagesize = STEP, sort = 0;
    private final static int INDEX = 4;// 一屏显示的个数
    private boolean isShowProgress = false;
    private List<DiscoveryItemObj> discoveryItemList = new ArrayList<DiscoveryItemObj>();
    private DiscoveryFavorAdapter adapter;

    		/*无数据和无网络界面*/

    private RelativeLayout layout_data_none, layout_net_none;
    private TextView tv_desc, tv_advice;
    private Button btn_refresh;
    private LinearLayout ll_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery_favor);
        init();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ShouCang"); //统计页面
        MobclickAgent.onResume(this);          //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ShouCang"); // 保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息
        MobclickAgent.onPause(this);
    }

    private void init() {
        // TODO Auto-generated method stub
        KaKuApplication.isShowRemoveImg_discovery = false;
        initTitleBar();

        xListView = (XListView) findViewById(R.id.lv_discovery_favor);
        xListView.setOnItemClickListener(this);
        xListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        initNoDataLayout();
        setPullState(false);
    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("我的收藏");
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("编辑");
        right.setOnClickListener(this);
    }

    /*初始化空白页页面布局*/
    private void initNoDataLayout() {
        layout_data_none = (RelativeLayout) findViewById(R.id.layout_data_none);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_desc.setText("您还没有收藏的活动");
        tv_advice = (TextView) findViewById(R.id.tv_advice);
        tv_advice.setVisibility(View.VISIBLE);
        tv_advice.setText("快去添加吧");

        layout_net_none = (RelativeLayout) findViewById(R.id.layout_net_none);

        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(this);

        ll_container = (LinearLayout) findViewById(R.id.ll_container);


    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()){
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        } else if (R.id.tv_right == id) {
            editFavorList();
        } else if (R.id.btn_refresh == id) {

            setPullState(false);

        }
    }

    private void editFavorList() {

        if (right.getText().toString().trim().equals("编辑")) {
            /*for (int i = 1; i < xListView.getChildCount(); i++) {
                View view = xListView.getChildAt(i);
                view.findViewById(R.id.ll_discovery_remove).setVisibility(View.VISIBLE);
            }*/
            KaKuApplication.isShowRemoveImg_discovery = true;
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            right.setText("完成");
        } else if (right.getText().toString().trim().equals("完成")) {
            /*for (int i = 1; i < xListView.getChildCount(); i++) {
                View view = xListView.getChildAt(i);
                view.findViewById(R.id.ll_discovery_remove).setVisibility(View.GONE);
            }*/
            KaKuApplication.isShowRemoveImg_discovery = false;
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            right.setText("编辑");
        }
    }


    public void YiDianTong(int pageIndex, int pageSize) {
        if (!Utils.checkNetworkConnection(this)) {
            setNoDataLayoutState(layout_net_none);

            return;
        } else {
            setNoDataLayoutState(ll_container);

        }
        showProgressDialog();
        DiscoveryListReq req = new DiscoveryListReq();
        req.code = "7003";
        req.start = String.valueOf(pageIndex);
        req.len = String.valueOf(pageSize);
        req.id_driver = Utils.getIdDriver();
        KaKuApiProvider.getDiscoveryFavorList(req, new KakuResponseListener<DiscoveryListResp>(this, DiscoveryListResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                // TODO Auto-generated method stub
                stopProgressDialog();
                if (t != null) {
                    LogUtil.E("discovery_favor res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t.newss);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }
                    onLoadStop();
                }
            }


        });
    }

    private void setData(List<DiscoveryItemObj> list) {
        // TODO Auto-generated method stub
        if (list != null) {
            discoveryItemList.addAll(list);
        }
        right.setEnabled(true);
        if (discoveryItemList.size() == 0) {

            setNoDataLayoutState(layout_data_none);
            right.setEnabled(false);
            return;
        } else {

            setNoDataLayoutState(ll_container);
        }

        adapter = new DiscoveryFavorAdapter(DiscoveryFavorActivity.this, discoveryItemList);
        adapter.setRemove(new DiscoveryFavorAdapter.IRemove() {
            @Override
            public void removeItem(DiscoveryItemObj obj) {
                removeFavorItem(obj);
            }
        });
        xListView.setAdapter(adapter);
        xListView.setPullLoadEnable(list.size() < INDEX ? false : true);
        xListView.setSelection(pageindex);
        xListView.setPullRefreshEnable(false);
        xListView.setXListViewListener(new XListView.IXListViewListener() {

            @Override
            public void onRefresh() {
                if (!Utils.checkNetworkConnection(DiscoveryFavorActivity.this)) {
                    stopProgressDialog();
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    xListView.stopRefresh();
                    return;
                }
                setPullState(false);
            }

            @Override
            public void onLoadMore() {
                if (!Utils.checkNetworkConnection(DiscoveryFavorActivity.this)) {
                    stopProgressDialog();
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
            // prize_info = new ArrayList<PrizeInfoObj>();
            if (discoveryItemList != null) {
                discoveryItemList.clear();
            }
        }
        YiDianTong(pageindex, pagesize);
    }

    private void onLoadStop() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(DateUtil.dateFormat());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(DiscoveryFavorActivity.this, DiscoveryDetailActivity.class);
        intent.putExtra("id_news", discoveryItemList.get(position - 1).getId_news());
        intent.putExtra("is_collection", "Y");
        intent.putExtra("num_collection", discoveryItemList.get(position - 1).getNum_collection());
        intent.putExtra("num_comments", discoveryItemList.get(position - 1).getNum_comments());
        startActivity(intent);
    }

    private void removeFavorItem(final DiscoveryItemObj obj) {
        showProgressDialog();
        if (!Utils.checkNetworkConnection(this)) {
            setNoDataLayoutState(layout_net_none);

            return;
        } else {
            setNoDataLayoutState(ll_container);

        }

        DiscoveryCancelFavorReq req = new DiscoveryCancelFavorReq();
        req.code = "7005";
        req.id_driver = Utils.getIdDriver();
        req.id_news = obj.getId_news();

        KaKuApiProvider.cancelDiscoveryFavor(req, new KakuResponseListener<DiscoveryCancelFavorResp>(this, DiscoveryCancelFavorResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("cancelDiscoveryFavor res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        discoveryItemList.remove(obj);
                        //xListView.getAdapter().notifyAll();

                        if (discoveryItemList.size() == 0) {
                            DiscoveryFavorActivity.this.finish();
                        } else {
                            if (adapter != null) {

                                adapter.notifyDataSetChanged();
                            }
                        }

                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                            LogUtil.showShortToast(context, t.msg);
                        }
                    }
                }
                stopProgressDialog();
            }

        });

    }

    private void setNoDataLayoutState(View view) {
        layout_data_none.setVisibility(View.GONE);
        ll_container.setVisibility(View.GONE);
        layout_net_none.setVisibility(View.GONE);

        view.setVisibility(View.VISIBLE);
    }

}
