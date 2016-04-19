package com.yichang.kaku.member.driver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.shop.ShopDetailActivity;
import com.yichang.kaku.obj.AttentionShopObj;
import com.yichang.kaku.request.AttentionShopsReq;
import com.yichang.kaku.response.AttentionShopsResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.util.ArrayList;
import java.util.List;

public class AttentionShopsActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {
    //titleBar,返回，购物车，标题,标题栏布局
    private TextView left, right, title;
    private XListView xListView;
    private final static int STEP = 5;
    private int start = 0, pageindex = 0, pagesize = STEP;
    private final static int INDEX = 5;// 一屏显示的个数
    private boolean isShowProgress = false;

    private List<AttentionShopObj> list=new ArrayList<>();
		/*无数据和无网络界面*/

    private RelativeLayout layout_data_none, layout_net_none;
    private TextView tv_desc, tv_advice;
    private Button btn_refresh;
    private LinearLayout ll_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention_shops);
        init();
    }

    @Override
    protected void onStart() {
        setPullState(false);
        super.onStart();
    }

    private void init() {
        initTitleBar();
        initNoDataLayout();
        xListView = (XListView) findViewById(R.id.lv_attention_shops_list);
        xListView.setOnItemClickListener(this);
    }
    /*初始化空白页页面布局*/
    private void initNoDataLayout() {
        layout_data_none = (RelativeLayout) findViewById(R.id.layout_data_none);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_desc.setText("您还没有关注的店铺");
        tv_advice = (TextView) findViewById(R.id.tv_advice);
        tv_advice.setText("快去关注吧");

        layout_net_none = (RelativeLayout) findViewById(R.id.layout_net_none);

        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(this);

        ll_container = (LinearLayout) findViewById(R.id.ll_container);

    }

    private void getPointHistoryInfo(int pageindex, int pagesize) {

        if (!Utils.checkNetworkConnection(context)) {
            setNoDataLayoutState(layout_net_none);

            return;
        } else {
            setNoDataLayoutState(ll_container);

        }

        AttentionShopsReq req = new AttentionShopsReq();
        req.code = "10022";
        req.id_driver = Utils.getIdDriver();
        req.start = String.valueOf(pageindex);
        req.len = String.valueOf(pagesize);

        KaKuApiProvider.getAttentionShops(req, new KakuResponseListener<AttentionShopsResp>(this, AttentionShopsResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getMemberPointInfo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {

                        setData(t.shops);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                    onLoadStop();
                }
            }
        });
    }

    private void setData(List<AttentionShopObj> shops) {
        if (shops != null) {
            list.addAll(shops);
        }

        if (list.size() == 0) {
            setNoDataLayoutState(layout_data_none);
            return;
        } else {
            setNoDataLayoutState(ll_container);
        }

        AttentionShopsAdapter adapter = new AttentionShopsAdapter(this, list);
        xListView.setAdapter(adapter);
        xListView.setPullLoadEnable(list.size() < INDEX ? false : true);
        xListView.setSelection(pageindex);
        xListView.setPullRefreshEnable(false);

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
    private void setNoDataLayoutState(View view) {
        layout_data_none.setVisibility(View.GONE);
        ll_container.setVisibility(View.GONE);
        layout_net_none.setVisibility(View.GONE);

        view.setVisibility(View.VISIBLE);
    }

    private void setPullState(boolean isUp) {
        if (isUp) {
            isShowProgress = true;
            start++;
            pageindex = start * STEP;
        } else {
            start = 0;
            pageindex = 0;
            if (list != null) {
                list.clear();
            }
        }
        getPointHistoryInfo(pageindex, pagesize);
    }
    private void onLoadStop() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(DateUtil.dateFormat());
    }



    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);

        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("关注店铺");

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

        }else if (R.id.tv_right==id){
            /*todo 积分规则*/
        }else if (R.id.btn_refresh == id) {

            setPullState(false);

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//增加多次点击判断
        if (Utils.Many()) {
            return;
        }
        Intent intent = new Intent(context,ShopDetailActivity.class);
        //intent.putExtra("id_shop",list.get(position-1).getId_shop());
        KaKuApplication.id_shop=list.get(position-1).getId_shop().toString().trim();
        startActivity(intent);
    }
}
