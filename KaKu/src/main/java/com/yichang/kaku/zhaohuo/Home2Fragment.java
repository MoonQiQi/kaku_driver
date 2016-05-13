package com.yichang.kaku.zhaohuo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.BaseFragment;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.guangbo.BoFangQiActivity;
import com.yichang.kaku.home.call.CallActivity;
import com.yichang.kaku.home.faxian.DiscoveryAdapter;
import com.yichang.kaku.home.faxian.DiscoveryDetailActivity;
import com.yichang.kaku.home.moneybook.MoneyBookActivity;
import com.yichang.kaku.obj.DiscoveryItemObj;
import com.yichang.kaku.obj.QuanZiObj;
import com.yichang.kaku.request.DiscoveryListReq;
import com.yichang.kaku.response.DiscoveryList2Resp;
import com.yichang.kaku.response.DiscoveryListResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class Home2Fragment extends BaseFragment implements OnClickListener, AdapterView.OnItemClickListener {

    private Activity context;
    private ImageView iv_faxian_mfdh, iv_faxian_czhy, iv_faxian_gsgb, iv_faxian_jzb, iv_faxian_fabu;
    private XListView xListView;
    private List<DiscoveryItemObj> discoveryItemList = new ArrayList<>();
    private List<QuanZiObj> discoveryItemList2 = new ArrayList<>();
    private static int STEP = 5;// 每次加载5条
    private int start = 0, pageindex = 0, pagesize = STEP, sort = 0;
    private static int INDEX = 5;// 一屏显示的个数
    private boolean isShowProgress = false;
    private ImageView tv_faxian_left, tv_faxian_right;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zone, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        iv_faxian_mfdh = (ImageView) view.findViewById(R.id.iv_faxian_mfdh);
        iv_faxian_mfdh.setOnClickListener(this);
        iv_faxian_czhy = (ImageView) view.findViewById(R.id.iv_faxian_czhy);
        iv_faxian_czhy.setOnClickListener(this);
        iv_faxian_gsgb = (ImageView) view.findViewById(R.id.iv_faxian_gsgb);
        iv_faxian_gsgb.setOnClickListener(this);
        iv_faxian_jzb = (ImageView) view.findViewById(R.id.iv_faxian_jzb);
        iv_faxian_jzb.setOnClickListener(this);
        iv_faxian_fabu = (ImageView) view.findViewById(R.id.iv_faxian_fabu);
        iv_faxian_fabu.setOnClickListener(this);
        xListView = (XListView) view.findViewById(R.id.lv_discovery);
        xListView.setOnItemClickListener(this);
        tv_faxian_left = (ImageView) view.findViewById(R.id.tv_faxian_left);
        tv_faxian_left.setOnClickListener(this);
        tv_faxian_right = (ImageView) view.findViewById(R.id.tv_faxian_right);
        tv_faxian_right.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.E("quanzitype:" + KaKuApplication.quanzitype);
        if ("left".equals(KaKuApplication.quanzitype)) {
            Left();
        } else {
            Right();
        }
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.iv_faxian_mfdh == id) {
            LogUtil.showShortToast(context, "敬请期待");
            return;
           /* MobclickAgent.onEvent(context, "mianfeidianhua");
            startActivity(new Intent(context, CallActivity.class));*/
        } else if (R.id.iv_faxian_czhy == id) {
            MobclickAgent.onEvent(context, "chazhaohuoyuan");
            startActivity(new Intent(context, ZhaoHuoActivity.class));
        } else if (R.id.iv_faxian_gsgb == id) {
            MobclickAgent.onEvent(context, "gaosuguangbo");
            startActivity(new Intent(context, BoFangQiActivity.class));
        } else if (R.id.iv_faxian_jzb == id) {
            MobclickAgent.onEvent(context, "jizhangben");
            startActivity(new Intent(context, MoneyBookActivity.class));
        } else if (R.id.tv_faxian_left == id) {
            MobclickAgent.onEvent(context, "zixun");
            Left();
        } else if (R.id.tv_faxian_right == id) {
            MobclickAgent.onEvent(context, "quanzi");
            Right();
        } else if (R.id.iv_faxian_fabu == id) {
            MobclickAgent.onEvent(context, "fabuquanzi");
            startActivity(new Intent(context, QuanZiFaBuActivity.class));
        }
    }

    public void Left() {
        iv_faxian_fabu.setVisibility(View.GONE);
        KaKuApplication.quanzitype = "left";
        STEP = 5;// 每次加载5条
        start = 0;
        pageindex = 0;
        pagesize = STEP;
        sort = 0;
        INDEX = 5;// 一屏显示的个数
        isShowProgress = false;
        tv_faxian_left.setImageResource(R.drawable.zixun1);
        tv_faxian_right.setImageResource(R.drawable.quanzi2);
        setPullState(false);
    }

    public void Right() {
        iv_faxian_fabu.setVisibility(View.VISIBLE);
        KaKuApplication.quanzitype = "right";
        STEP = 5;// 每次加载5条
        start = 0;
        pageindex = 0;
        pagesize = STEP;
        sort = 0;
        INDEX = 5;// 一屏显示的个数
        isShowProgress = false;
        tv_faxian_left.setImageResource(R.drawable.zixun2);
        tv_faxian_right.setImageResource(R.drawable.quanzi1);
        setPullState2(false);
    }

    public void getDiscoveryList(int pageIndex, int pageSize) {
        showProgressDialog();

        DiscoveryListReq req = new DiscoveryListReq();
        req.code = "70010";
        req.start = String.valueOf(pageIndex);
        req.len = String.valueOf(pageSize);
        KaKuApiProvider.getDiscoveryList(req, new KakuResponseListener<DiscoveryListResp>(context, DiscoveryListResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                // TODO Auto-generated method stub

                if (t != null) {
                    LogUtil.E("toutiao res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t.newss);
                    } else {
                        Toast.makeText(context, t.msg, Toast.LENGTH_SHORT).show();
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

    public void getDiscoveryList2(int pageIndex, int pageSize) {
        showProgressDialog();

        DiscoveryListReq req = new DiscoveryListReq();
        req.code = "70011";
        req.start = String.valueOf(pageIndex);
        req.len = String.valueOf(pageSize);
        KaKuApiProvider.getDiscoveryList2(req, new KakuResponseListener<DiscoveryList2Resp>(context, DiscoveryList2Resp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                // TODO Auto-generated method stub

                if (t != null) {
                    LogUtil.E("quanzi res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData2(t.circles);
                    } else {
                        Toast.makeText(context, t.msg, Toast.LENGTH_SHORT).show();
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

    private void setData(List<DiscoveryItemObj> list) {

        if (list != null) {
            discoveryItemList.addAll(list);
        } else {
            return;
        }


        DiscoveryAdapter adapter = new DiscoveryAdapter(context, discoveryItemList);
        xListView.setAdapter(adapter);
        xListView.setPullLoadEnable(list.size() < INDEX ? false : true);
        xListView.setSelection(pageindex - 1);
        xListView.setPullRefreshEnable(true);
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

    private void setData2(List<QuanZiObj> list) {

        if (list != null) {
            discoveryItemList2.addAll(list);
        } else {
            return;
        }


        QuanziAdapter adapter = new QuanziAdapter(context, discoveryItemList2);
        xListView.setAdapter(adapter);
        xListView.setPullLoadEnable(list.size() < INDEX ? false : true);
        xListView.setSelection(pageindex - 1);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(new XListView.IXListViewListener() {

            @Override
            public void onRefresh() {
                if (!Utils.checkNetworkConnection(context)) {
                    stopProgressDialog();
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    xListView.stopRefresh();
                    return;
                }
                setPullState2(false);
            }

            @Override
            public void onLoadMore() {
                if (!Utils.checkNetworkConnection(context)) {
                    stopProgressDialog();
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    xListView.stopLoadMore();
                    return;
                }
                setPullState2(true);
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
            if (discoveryItemList != null) {
                discoveryItemList.clear();
            }
        }
        getDiscoveryList(pageindex, pagesize);
    }

    private void setPullState2(boolean isUp) {
        if (isUp) {
            isShowProgress = true;
            start++;
            pageindex = start * STEP;
        } else {
            start = 0;
            pageindex = 0;
            if (discoveryItemList2 != null) {
                discoveryItemList2.clear();
            }
        }
        getDiscoveryList2(pageindex, pagesize);
    }

    private void onLoadStop() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(DateUtil.dateFormat());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if ("left".equals(KaKuApplication.quanzitype)) {
            Intent intent = new Intent(context, DiscoveryDetailActivity.class);
            KaKuApplication.position = position;
            KaKuApplication.id_news = discoveryItemList.get(position - 1).getId_news();
            startActivity(intent);
        } else if ("right".equals(KaKuApplication.quanzitype)) {
            Intent intent = new Intent(context, QuanziDetailActivity.class);
            KaKuApplication.position = position;
            KaKuApplication.id_circle = discoveryItemList2.get(position - 1).getId_circle();
            startActivity(intent);
        }
    }
}
