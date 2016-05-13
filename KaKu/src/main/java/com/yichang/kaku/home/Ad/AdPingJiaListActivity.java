package com.yichang.kaku.home.ad;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.CheTiePingJiaObj;
import com.yichang.kaku.request.AdPingJialistReq;
import com.yichang.kaku.response.AdPingJiaListResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class AdPingJiaListActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private XListView xListView;
    private List<CheTiePingJiaObj> list_pingjia = new ArrayList<CheTiePingJiaObj>();
    private final static int STEP = 8;
    private int start = 0, pageindex = 0, pagesize = STEP;
    private final static int INDEX = 8;// 一屏显示的个数
    private boolean isShowProgress = false;

		/*无数据和无网络界面*/

    private RelativeLayout layout_data_none, layout_net_none;
    private TextView tv_desc, tv_advice;
    private Button btn_refresh;
    private LinearLayout ll_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adpingjialist);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("全部评价");
        xListView = (XListView) findViewById(R.id.lv_pingjia_list);

        initNoDataLayout();
        setPullState(false);
    }

    /*初始化空白页页面布局*/
    private void initNoDataLayout() {
        layout_data_none = (RelativeLayout) findViewById(R.id.layout_data_none);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_desc.setText("该店铺暂无评价");
        tv_advice = (TextView) findViewById(R.id.tv_advice);

        layout_net_none = (RelativeLayout) findViewById(R.id.layout_net_none);

        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(this);

        ll_container = (LinearLayout) findViewById(R.id.ll_container);


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
        } else if (R.id.btn_refresh == id) {
            setPullState(false);
        }
    }

    private void setNoDataLayoutState(View view) {
        layout_data_none.setVisibility(View.GONE);
        ll_container.setVisibility(View.GONE);
        layout_net_none.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
    }

    public void PingJia(int pageIndex, int pageSize) {
        if (!Utils.checkNetworkConnection(context)) {
            setNoDataLayoutState(layout_net_none);
            return;
        } else {
            setNoDataLayoutState(ll_container);
        }
        showProgressDialog();
        AdPingJialistReq req = new AdPingJialistReq();
        req.code = "30023";
        req.id_advert = KaKuApplication.id_advert;
        req.start = String.valueOf(pageIndex);
        req.len = String.valueOf(pageSize);
        KaKuApiProvider.adPingJiaList(req, new KakuResponseListener<AdPingJiaListResp>(this, AdPingJiaListResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("adpingjialist res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t.evals);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                    onLoadStop();
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    private void setData(List<CheTiePingJiaObj> list) {
        // TODO Auto-generated method stub
        if (list != null) {
            list_pingjia.addAll(list);
        }
        if (list_pingjia.size() == 0) {

            setNoDataLayoutState(layout_data_none);
            return;
        } else {

            setNoDataLayoutState(ll_container);
        }

        CheTiePingJiaAdapter adapter = new CheTiePingJiaAdapter(AdPingJiaListActivity.this, list_pingjia);
        xListView.setAdapter(adapter);
        xListView.setPullLoadEnable(list.size() < INDEX ? false : true);
        xListView.setSelection(pageindex);
        xListView.setPullRefreshEnable(true);
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
            if (list_pingjia != null) {
                list_pingjia.clear();
            }
        }
        PingJia(pageindex, pagesize);
    }

    private void onLoadStop() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(DateUtil.dateFormat());
    }

}
