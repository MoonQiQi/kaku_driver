package com.yichang.kaku.home.sousuo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.HomeShopMallAdapter;
import com.yichang.kaku.home.giftmall.ProductDetailActivity;
import com.yichang.kaku.home.giftmall.ShopMallActivity;
import com.yichang.kaku.member.login.LoginActivity;
import com.yichang.kaku.obj.ShopMallProductObj;
import com.yichang.kaku.obj.SouSuoHotObj;
import com.yichang.kaku.request.ExitReq;
import com.yichang.kaku.request.ShopMallProductsReq;
import com.yichang.kaku.response.ShopMallProductsResp;
import com.yichang.kaku.response.SouSuoHotResp;
import com.yichang.kaku.tools.DensityUtils;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.PullToRefreshView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SouSuoActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener, OnEditorActionListener {

    private ImageView iv_sousuo_fanhui;
    private EditText et_sousuo_sousuo;
    private PullToRefreshView mPullToRefreshView;
    private ListView lv_lishi;
    private GridView lv_home_item1;
    private List<ShopMallProductObj> list_wxz = new ArrayList<>();
    private HomeShopMallAdapter adapter;
    //    设置步数
    private final static int STEP = 10;
    /*start:页码；pageindex:最后一条记录索引；pagesize：每页加载条目数量*/
    private int start = 0, pageindex = 0, pagesize = STEP;
    private final static int INDEX = 6;// 一屏显示的个数
    private SouSuoLiShiAdapter adapter_lishi;
    private LinearLayout line_sousuo_content, ll_sousuo_lishi;
    private String save_history;
    private List<String> list_string;
    private StringBuilder sb;
    private boolean isShowProgress;
    private TextView tv_lishi_qingkong, tv_sousuo_quxiao;
    private GridView grid_sousuo_hot;
    private List<SouSuoHotObj> list_hot = new ArrayList<>();
    private String sousuo_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sousuo);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        iv_sousuo_fanhui = (ImageView) findViewById(R.id.iv_sousuo_fanhui);
        iv_sousuo_fanhui.setOnClickListener(this);
        et_sousuo_sousuo = (EditText) findViewById(R.id.et_sousuo_sousuo);
        et_sousuo_sousuo.setOnEditorActionListener(this);
        line_sousuo_content = (LinearLayout) findViewById(R.id.line_sousuo_content);
        ll_sousuo_lishi = (LinearLayout) findViewById(R.id.ll_sousuo_lishi);
        lv_home_item1 = (GridView) findViewById(R.id.lv_sousuo_item1);
        lv_home_item1.setOnItemClickListener(this);
        lv_lishi = (ListView) findViewById(R.id.lv_lishi);
        lv_lishi.setOnItemClickListener(this);
        tv_lishi_qingkong = (TextView) findViewById(R.id.tv_lishi_qingkong);
        tv_lishi_qingkong.setOnClickListener(this);
        tv_sousuo_quxiao = (TextView) findViewById(R.id.tv_sousuo_quxiao);
        tv_sousuo_quxiao.setOnClickListener(this);
        grid_sousuo_hot = (GridView) findViewById(R.id.grid_sousuo_hot);
        grid_sousuo_hot.setOnItemClickListener(this);
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pulltofresh_shopmall_products2);
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
                if (list_wxz.size() < INDEX) {
                    LogUtil.showShortToast(ShopMallActivity.context, "没有更多了");
                }
            }
        });
        Hot();
        Show();
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.iv_sousuo_fanhui == id) {
            finish();
        } else if (R.id.tv_lishi_qingkong == id) {
            QingKong();
        } else if (R.id.tv_sousuo_quxiao == id) {
            et_sousuo_sousuo.setText("");
        }

    }

    public void SouSuo(final int pageIndex, int pageSize, String sousuo) {
        Utils.NoNet(this);
        showProgressDialog();
        ShopMallProductsReq req = new ShopMallProductsReq();
        req.code = "3001";
        req.len = String.valueOf(pageSize);
        req.sort = "N";
        req.start = String.valueOf(pageIndex);
        req.type_goods = "0";
        req.id_goods_type = "1";
        req.name_goods = sousuo;
        KaKuApiProvider.getShopMallProductsLst(req, new KakuResponseListener<ShopMallProductsResp>(this, ShopMallProductsResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("sousuo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t.goods);
                        lv_home_item1.setFocusable(false);
                        line_sousuo_content.setVisibility(View.VISIBLE);
                        ll_sousuo_lishi.setVisibility(View.GONE);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                    stopProgressDialog();
                }
            }

        });
    }

    private void setData(List<ShopMallProductObj> list) {

        // TODO Auto-generated method stub
        if (list != null) {
            list_wxz.addAll(list);
        } else {
            return;
        }
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int anInt = outMetrics.widthPixels / 2;
        HomeShopMallAdapter adapter = new HomeShopMallAdapter(context, list_wxz, anInt);

        lv_home_item1.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        lv_home_item1.requestFocus();
        lv_home_item1.setItemChecked(pageindex + 1, true);

        lv_home_item1.setSelection(pageindex + 1);

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
            if (list_wxz != null) {
                list_wxz.clear();
            }
        }
        SouSuo(pageindex, pagesize, sousuo_string);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        params.height += 5;

        listView.setLayoutParams(params);
    }

    public void Save() {

        String text = et_sousuo_sousuo.getText().toString();
        SharedPreferences sp = getSharedPreferences("history_strs", 0);
        String save_Str = sp.getString("history", "");
        list_string = Arrays.asList(save_Str.split(","));
        List arrayList = new ArrayList(list_string);
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(text)) {
                return;
            }
        }
        sb = new StringBuilder(save_Str);
        sb.append(text + ",");

        sp.edit().putString("history", sb.toString()).commit();
    }

    public void Show() {
        SharedPreferences sp = getSharedPreferences("history_strs", 0);
        save_history = sp.getString("history", "");
        if ("".equals(save_history)) {
            return;
        }
        list_string = Arrays.asList(save_history.split(","));
        List arrayList = new ArrayList(list_string);
        adapter_lishi = new SouSuoLiShiAdapter(this, arrayList);
        lv_lishi.setAdapter(adapter_lishi);

    }

    public void QingKong() {
        SharedPreferences sp = getSharedPreferences("history_strs", 0);
        sp.edit().putString("history", "").commit();
        lv_lishi.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int parentId = parent.getId();
        if (R.id.lv_lishi == parentId) {
            et_sousuo_sousuo.setText(list_string.get(position));
            list_wxz.clear();
            sousuo_string = list_string.get(position);
            start = 0; pageindex = 0; pagesize = 10;
            SouSuo(pageindex, pagesize, sousuo_string);
        } else if (R.id.lv_sousuo_item1 == parentId) {
            MobclickAgent.onEvent(context, "BaoYang");
            KaKuApplication.id_goods = list_wxz.get(position).getId_goods();
            GoToShopDetail();
        } else if (R.id.grid_sousuo_hot == parentId) {
            Utils.NoNet(context);
            if (!Utils.isLogin()) {
                startActivity(new Intent(context, LoginActivity.class));
                return;
            }
            if (Utils.Many()) {
                return;
            }
            list_wxz.clear();
            sousuo_string = list_hot.get(position).getKeyword_goods();
            start = 0; pageindex = 0; pagesize = 10;
            SouSuo(pageindex, pagesize, sousuo_string);
        }
    }

    public void GoToShopDetail() {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            //点软键盘的搜索
            String tempWord = et_sousuo_sousuo.getText().toString().trim();

            if (TextUtils.isEmpty(tempWord)) {
                LogUtil.showShortToast(context, "请输入搜索内容");
                return false;
            }
            Save();
            list_wxz.clear();
            sousuo_string = tempWord;
            SouSuo(pageindex, pagesize, sousuo_string);
            return true;
        } else {
            return false;
        }
    }

    public void Hot() {
        showProgressDialog();
        ExitReq req = new ExitReq();
        req.code = "3002";
        KaKuApiProvider.SouSuoHot(req, new KakuResponseListener<SouSuoHotResp>(context, SouSuoHotResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("sousuohot res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        list_hot = t.hots;
                        SouSouHotAdapter adapter = new SouSouHotAdapter(context, list_hot);
                        int size = list_hot.size();
                        int length = DensityUtils.dp2px(context, 88);
                        DisplayMetrics dm = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(dm);
                        float density = dm.density;
                        int gridviewWidth = (int) (size * (length + 1));
                        int itemWidth = (int) (length);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                        grid_sousuo_hot.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
                        grid_sousuo_hot.setColumnWidth(itemWidth); // 设置列表项宽
                        grid_sousuo_hot.setHorizontalSpacing(0); // 设置列表项水平间距
                        grid_sousuo_hot.setStretchMode(GridView.NO_STRETCH);
                        grid_sousuo_hot.setNumColumns(size); // 设置列数量=列表集合数
                        grid_sousuo_hot.setAdapter(adapter);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                    }
                    LogUtil.showShortToast(context, t.msg);
                }
                stopProgressDialog();
            }
        });
    }
}