package com.yichang.kaku.home.faxian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseFragment;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.shop.ShopDetailActivity;
import com.yichang.kaku.home.shop.ShopFenLeiAdapter;
import com.yichang.kaku.home.shop.ShopItem2Adapter;
import com.yichang.kaku.home.shop.ShopItemAdapter;
import com.yichang.kaku.obj.ShopFenLeiObj;
import com.yichang.kaku.obj.Shops_wxzObj;
import com.yichang.kaku.request.PinPaiFuWuZhanReq;
import com.yichang.kaku.response.PinPaiFuWuZhanResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class Home4Fragment extends BaseFragment implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private Activity mActivity;
    private ImageView iv_mendian_1, iv_mendian_2, iv_mendian_3, iv_mendian_4;
    private XListView lv_mendian, lv_mendian2;
    private final static int STEP = 6;
    private int start = 0, pageindex = 0, pagesize = STEP;
    private final static int INDEX = 6;// 一屏显示的个数
    private boolean isShowProgress = false;
    private List<Shops_wxzObj> list_shop = new ArrayList<>();
    private List<Shops_wxzObj> list_shop2 = new ArrayList<>();
    private List<ShopFenLeiObj> list_fenlei = new ArrayList<>();
    private String type_gv = "N";
    private TextView tv_mendian_all;
    private GridView gv_mendian_fenlei;
    private RelativeLayout rela_shop_gv;
    private ShopItemAdapter adapter;
    private ShopItem2Adapter adapter2;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mActivity = getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_know, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        title = (TextView) view.findViewById(R.id.tv_mid);
        title.setText("周边门店");
        left = (TextView) view.findViewById(R.id.tv_left);
        left.setVisibility(View.GONE);
        iv_mendian_1 = (ImageView) view.findViewById(R.id.iv_mendian_1);
        iv_mendian_2 = (ImageView) view.findViewById(R.id.iv_mendian_2);
        iv_mendian_3 = (ImageView) view.findViewById(R.id.iv_mendian_3);
        iv_mendian_4 = (ImageView) view.findViewById(R.id.iv_mendian_4);
        iv_mendian_1.setOnClickListener(this);
        iv_mendian_2.setOnClickListener(this);
        iv_mendian_3.setOnClickListener(this);
        iv_mendian_4.setOnClickListener(this);
        lv_mendian = (XListView) view.findViewById(R.id.lv_mendian);
        lv_mendian.setOnItemClickListener(this);
        lv_mendian2 = (XListView) view.findViewById(R.id.lv_mendian2);
        lv_mendian2.setOnItemClickListener(this);
        tv_mendian_all = (TextView) view.findViewById(R.id.tv_mendian_all);
        tv_mendian_all.setOnClickListener(this);
        tv_mendian_all.setText(KaKuApplication.name_service);
        tv_mendian_all.setEnabled(false);
        gv_mendian_fenlei = (GridView) view.findViewById(R.id.gv_mendian_fenlei);
        gv_mendian_fenlei.setOnItemClickListener(this);
        rela_shop_gv = (RelativeLayout) view.findViewById(R.id.rela_shop_gv);
        rela_shop_gv.setOnClickListener(this);
        if ("7".equals(KaKuApplication.type_service)) {
            SetImage();
            iv_mendian_3.setImageResource(R.drawable.weixiudianhong);
        } else {
            setPullState(false);
        }
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(mActivity);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.iv_mendian_1 == id) {
            tv_mendian_all.setText("全部");
            KaKuApplication.id_service = "";
            KaKuApplication.type_service = "9";
            SetImage();
            iv_mendian_1.setImageResource(R.drawable.kuaixiudianhong);
        } else if (R.id.iv_mendian_2 == id) {
            tv_mendian_all.setText("全部");
            KaKuApplication.id_service = "";
            KaKuApplication.type_service = "8";
            SetImage();
            iv_mendian_2.setImageResource(R.drawable.yanghudianhong);
        } else if (R.id.iv_mendian_3 == id) {
            tv_mendian_all.setText("全部");
            KaKuApplication.id_service = "";
            KaKuApplication.type_service = "7";
            SetImage();
            iv_mendian_3.setImageResource(R.drawable.weixiudianhong);
        } else if (R.id.iv_mendian_4 == id) {
            tv_mendian_all.setText("做保养");
            KaKuApplication.id_service = "";
            KaKuApplication.type_service = "0";
            SetImage();
            tv_mendian_all.setEnabled(false);
            iv_mendian_4.setImageResource(R.drawable.fuwuzhanhong);
        } else if (R.id.tv_mendian_all == id) {
            if ("N".equals(type_gv)) {
                rela_shop_gv.setVisibility(View.VISIBLE);
                type_gv = "Y";
            } else {
                rela_shop_gv.setVisibility(View.GONE);
                type_gv = "N";
            }
        } else if (R.id.rela_shop_gv == id) {
            rela_shop_gv.setVisibility(View.GONE);
            type_gv = "N";
        }
    }

    public void getShop(int pageIndex, int pageSize) {
        showProgressDialog();
        PinPaiFuWuZhanReq req = new PinPaiFuWuZhanReq();
        req.code = "8003";
        req.lat = Utils.getLat();
        req.lon = Utils.getLon();
        req.start = String.valueOf(pageIndex);
        req.len = String.valueOf(pageSize);
        req.flag_type = KaKuApplication.type_service;
        req.id_service = KaKuApplication.id_service;
        KaKuApiProvider.PinPaiFuWuZhan(req, new KakuResponseListener<PinPaiFuWuZhanResp>(mActivity, PinPaiFuWuZhanResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getShop res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        if ("7".equals(KaKuApplication.type_service) || "8".equals(KaKuApplication.type_service) || "9".equals(KaKuApplication.type_service)) {
                            lv_mendian.setVisibility(View.INVISIBLE);
                            lv_mendian2.setVisibility(View.VISIBLE);
                            setData2(t.shops);
                            onLoadStop2();
                        } else {
                            lv_mendian.setVisibility(View.VISIBLE);
                            lv_mendian2.setVisibility(View.INVISIBLE);
                            setData(t.shops);
                            onLoadStop();
                        }
                        list_fenlei = t.services;
                        ShopFenLeiAdapter adapter_gv = new ShopFenLeiAdapter(getActivity(), list_fenlei);
                        gv_mendian_fenlei.setAdapter(adapter_gv);

                    } else {
                        LogUtil.showShortToast(mActivity, t.msg);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    private void setData(List<Shops_wxzObj> list) {
        // TODO Auto-generated method stub
        adapter = new ShopItemAdapter(mActivity, list_shop);
        if (list != null) {
            list_shop.addAll(list);
            adapter.notifyDataSetChanged();
        }
        lv_mendian.setAdapter(adapter);
        lv_mendian.setPullLoadEnable(list.size() < INDEX ? false : true);
        lv_mendian.setSelection(pageindex);
        lv_mendian.setPullRefreshEnable(false);
        lv_mendian.setXListViewListener(new XListView.IXListViewListener() {

            @Override
            public void onRefresh() {
                if (!Utils.checkNetworkConnection(mActivity)) {
                    Toast.makeText(mActivity, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    lv_mendian.stopRefresh();
                    return;
                }
                setPullState(false);
            }

            @Override
            public void onLoadMore() {
                if (!Utils.checkNetworkConnection(mActivity)) {
                    Toast.makeText(mActivity, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    lv_mendian.stopLoadMore();
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
            if (list_shop != null && list_shop.size() > 0) {
                list_shop.clear();
                adapter.notifyDataSetChanged();
            }
            if (list_shop2 != null && list_shop2.size() > 0) {
                list_shop2.clear();
                adapter2.notifyDataSetChanged();
            }
        }
        getShop(pageindex, pagesize);
    }

    private void onLoadStop() {
        lv_mendian.stopRefresh();
        lv_mendian.stopLoadMore();
        lv_mendian.setRefreshTime(DateUtil.dateFormat());
    }


    private void setData2(List<Shops_wxzObj> list) {
        // TODO Auto-generated method stub
        adapter2 = new ShopItem2Adapter(mActivity, list_shop2);
        if (list != null) {
            list_shop2.addAll(list);
            adapter2.notifyDataSetChanged();
        }
        lv_mendian2.setAdapter(adapter2);
        lv_mendian2.setPullLoadEnable(list.size() < INDEX ? false : true);
        lv_mendian2.setSelection(pageindex);
        lv_mendian2.setPullRefreshEnable(false);
        lv_mendian2.setXListViewListener(new XListView.IXListViewListener() {

            @Override
            public void onRefresh() {
                if (!Utils.checkNetworkConnection(mActivity)) {
                    Toast.makeText(mActivity, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    lv_mendian2.stopRefresh();
                    return;
                }
                setPullState(false);
            }

            @Override
            public void onLoadMore() {
                if (!Utils.checkNetworkConnection(mActivity)) {
                    Toast.makeText(mActivity, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    lv_mendian2.stopLoadMore();
                    return;
                }
                setPullState(true);
            }
        });
    }

    private void onLoadStop2() {
        lv_mendian2.stopRefresh();
        lv_mendian2.stopLoadMore();
        lv_mendian2.setRefreshTime(DateUtil.dateFormat());
    }

    private void SetImage() {
        tv_mendian_all.setEnabled(true);
        iv_mendian_1.setImageResource(R.drawable.kuaixiudianhui);
        iv_mendian_2.setImageResource(R.drawable.yanghudianhui);
        iv_mendian_3.setImageResource(R.drawable.weixiudianhui);
        iv_mendian_4.setImageResource(R.drawable.fuwuzhanhui);
        rela_shop_gv.setVisibility(View.GONE);
        type_gv = "N";
        setPullState(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int parentId = parent.getId();
        if (R.id.lv_mendian == parentId) {
            KaKuApplication.id_shop = list_shop.get(position - 1).getId_shop();
            startActivity(new Intent(getActivity(), ShopDetailActivity.class));
        } else if (R.id.lv_mendian2 == parentId) {
            KaKuApplication.id_shop = list_shop2.get(position - 1).getId_shop();
            startActivity(new Intent(getActivity(), ShopDetailActivity.class));
        } else if (R.id.gv_mendian_fenlei == parentId) {
            KaKuApplication.id_service = list_fenlei.get(position).getId_service();
            tv_mendian_all.setText(list_fenlei.get(position).getName_service());
            setPullState(false);
            rela_shop_gv.setVisibility(View.GONE);
            type_gv = "N";
        }
    }
}
