package com.yichang.kaku.home.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.obj.DiscoveryItemObj;
import com.yichang.kaku.request.DiscoveryListReq;
import com.yichang.kaku.response.DiscoveryListResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class DiscoveryActivity extends BaseActivity implements OnClickListener {

    //    titleBar
    private TextView title, left, right;
    /*private Activity mActivity;*/
    private XListView xListView;

    private final static int STEP = 5;// 每次加载5条
    private int start = 0, pageindex = 0, pagesize = STEP, sort = 0;
    private final static int INDEX = 5;// 一屏显示的个数
    private boolean isShowProgress = false;

    List<DiscoveryItemObj> discoveryItemList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //mActivity = getActivity();
        setContentView(R.layout.activity_discovery);
        init();

    }

    @Override
    public void onStart() {
        super.onStart();
        setPullState(false);
    }


    private void init() {
        initTitleBar();
        xListView = (XListView) findViewById(R.id.lv_discovery);
        xListView.setOverScrollMode(View.OVER_SCROLL_NEVER);

    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("每日资讯");

        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("收藏");
        right.setOnClickListener(this);

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
            startActivity(new Intent(this, DiscoveryFavorActivity.class));
        }

    }

    public void getDiscoveryList(int pageIndex, int pageSize) {

        showProgressDialog();
        DiscoveryListReq req = new DiscoveryListReq();
        req.code = "70010";
        req.start = String.valueOf(pageIndex);
        req.len = String.valueOf(pageSize);
        req.id_driver = Utils.getIdDriver();
        KaKuApiProvider.getDiscoveryList(req, new BaseCallback<DiscoveryListResp>(DiscoveryListResp.class) {

            @Override
            public void onSuccessful(int statusCode, Header[] headers, DiscoveryListResp t) {
                // TODO Auto-generated method stub

                if (t != null) {
                    LogUtil.E("yidiantongshoucang res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t.newss);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                        Toast.makeText(context, t.msg, Toast.LENGTH_SHORT).show();
                    }
                    onLoadStop();
                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg,
                                  Throwable error) {
                // TODO Auto-generated method stub
                stopProgressDialog();
            }
        });
    }

    private void setData(List<DiscoveryItemObj> list) {

        if (list != null) {
            discoveryItemList.addAll(list);
        } else {
            return;
        }


        DiscoveryAdapter adapter = new DiscoveryAdapter(DiscoveryActivity.this, discoveryItemList);
        xListView.setAdapter(adapter);
        adapter.setShowProgress(new DiscoveryAdapter.ShowProgress() {
            @Override
            public void showDialog() {
                showProgressDialog();
            }

            @Override
            public void stopDialog() {
                stopProgressDialog();
            }
        });

        xListView.setPullLoadEnable(list.size() < INDEX ? false : true);
        xListView.setSelection(pageindex);
        xListView.setPullRefreshEnable(false);
        xListView.setXListViewListener(new XListView.IXListViewListener() {

            @Override
            public void onRefresh() {
                if (!Utils.checkNetworkConnection(context)) {
                    stopProgressDialog();
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    xListView.stopRefresh();
                    return;
                }
                setPullState(false);
            }

            @Override
            public void onLoadMore() {
                if (!Utils.checkNetworkConnection(context)) {
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
        getDiscoveryList(pageindex, pagesize);
    }

    private void onLoadStop() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(DateUtil.dateFormat());
    }

}
