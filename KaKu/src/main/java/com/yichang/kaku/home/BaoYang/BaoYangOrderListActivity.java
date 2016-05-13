package com.yichang.kaku.home.baoyang;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
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
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.global.MyActivityManager;
import com.yichang.kaku.obj.ServiceOrderObj;
import com.yichang.kaku.request.BaoYangOrderCancleReq;
import com.yichang.kaku.request.TruckOrderListReq;
import com.yichang.kaku.response.ServiceOrderListResp;
import com.yichang.kaku.response.TruckOrderConfirmReceiptResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class BaoYangOrderListActivity extends BaseActivity implements OnClickListener {
    //titleBar,返回，购物车，标题
    private TextView left, right, title;
    //订单列表
    private XListView xListView;
    private List<ServiceOrderObj> list_truckorder = new ArrayList<ServiceOrderObj>();
    //全部，待付款，待收货，待评价，退/换货
    private TextView tv_orderlist_total, tv_orderlist_pay, tv_orderlist_receipt, tv_orderlist_comment;
    /*无数据和无网络界面*/

    private RelativeLayout layout_data_none, layout_net_none;
    private TextView tv_desc, tv_advice;
    private Button btn_refresh;
    private LinearLayout ll_container;

    //数据分页加载所需参数
    private final static int STEP = 5;
    private int start = 0, pageindex = 0, pagesize = STEP;
    private final static int INDEX = 5;// 一屏显示的个数
    private boolean isShowProgress = false;
    private String state;
    private int selectColor, unSelectColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baoyangorderlist);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getOrderState();
        changeTextViewBg();
        setPullState(false);
    }

    private void init() {
        initTitleBar();

        initNoDataLayout();
        xListView = (XListView) findViewById(R.id.lv_orderlist);
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KaKuApplication.id_upkeep_bill = list_truckorder.get(position - 1).getId_upkeep_bill();
                Intent intent = new Intent(getApplicationContext(), BaoYangOrderDetailActivity.class);
                startActivity(intent);
            }
        });
        selectColor = getResources().getColor(R.color.color_red);
        unSelectColor = getResources().getColor(R.color.black);
        state = KaKuApplication.state_order;
        initClassifyButton();
    }

    /*初始化空白页页面布局*/
    private void initNoDataLayout() {
        layout_data_none = (RelativeLayout) findViewById(R.id.layout_data_none);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_advice = (TextView) findViewById(R.id.tv_advice);
        layout_net_none = (RelativeLayout) findViewById(R.id.layout_net_none);
        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(this);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
    }

    private void changeTextViewBg() {
        if (state != null) {
            switch (KaKuApplication.state_order) {
                case "A":
                    SetTextColor(tv_orderlist_pay);
                    break;
                case "D":
                    SetTextColor(tv_orderlist_receipt);
                    break;
                case "E":
                    SetTextColor(tv_orderlist_comment);
                    break;
                default:
                    SetTextColor(tv_orderlist_total);
                    break;
            }
        }
    }

    private void getOrderState() {
        changeTextViewBg();
    }

    private void getTruckOrderLst(int pageindex, int pagesize) {
        if (!Utils.checkNetworkConnection(context)) {
            setNoDataLayoutState(layout_net_none);
            return;
        } else {
            setNoDataLayoutState(ll_container);
        }
        showProgressDialog();
        TruckOrderListReq req = new TruckOrderListReq();
        req.code = "400121";
        req.state_bill = KaKuApplication.state_order;
        req.start = String.valueOf(pageindex);
        req.len = String.valueOf(pagesize);

        KaKuApiProvider.BaoYangOrderList(req, new KakuResponseListener<ServiceOrderListResp>(this, ServiceOrderListResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getTruckOrderList res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t.bills);
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

    /*
    * 设置view的Visibility为可见，其余空白页为gone
    * */
    private void setNoDataLayoutState(View view) {
        layout_data_none.setVisibility(View.GONE);
        ll_container.setVisibility(View.GONE);
        layout_net_none.setVisibility(View.GONE);

        view.setVisibility(View.VISIBLE);
    }

    private void setData(List<ServiceOrderObj> list) {
        // TODO Auto-generated method stub
        if (list != null) {
            list_truckorder.addAll(list);
        }

        if (list_truckorder.size() == 0) {
            setNoDataLayoutState(layout_data_none);
            return;
        } else {
            setNoDataLayoutState(ll_container);
        }

        BaoYangOrderAdapter adapter = new BaoYangOrderAdapter(this, list_truckorder);
        adapter.setCallback(new BaoYangOrderAdapter.CallBack() {
            @Override
            public void payOrder(String no_bill, String flag_pay, String price_bill, String flag_type, String id) {
                if ("U".equals(flag_type)) {
                    KaKuApplication.realPayment = price_bill;
                    KaKuApplication.payType = "SERVICE";
                    Intent intent = new Intent(context, OrderBaoYangPayActivity.class);
                    intent.putExtra("no_bill", no_bill);
                    intent.putExtra("price_bill", price_bill);
                    startActivity(intent);
                } else {
                    KaKuApplication.id_upkeep_bill = id;
                    startActivity(new Intent(context, YellowOilOrderActivity.class));
                }

            }

            @Override
            public void QueRenFuWu() {
                QueRenFuWu2();
            }
        });

        xListView.setAdapter(adapter);
        xListView.setPullLoadEnable(list.size() < INDEX ? false : true);
        xListView.setSelection(pageindex);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(new XListView.IXListViewListener() {

            @Override
            public void onRefresh() {
                if (!Utils.checkNetworkConnection(context)) {
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    setNoDataLayoutState(layout_net_none);
                    xListView.stopRefresh();
                    return;
                }
                setPullState(false);
            }

            @Override
            public void onLoadMore() {
                if (!Utils.checkNetworkConnection(context)) {
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    setNoDataLayoutState(layout_net_none);
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
            if (list_truckorder != null) {
                list_truckorder.clear();
            }
        }

        getTruckOrderLst(pageindex, pagesize);
    }

    private void onLoadStop() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(DateUtil.dateFormat());
    }

    private void initClassifyButton() {
        tv_orderlist_total = (TextView) findViewById(R.id.tv_orderlist_total);
        tv_orderlist_total.setOnClickListener(this);
        tv_orderlist_pay = (TextView) findViewById(R.id.tv_orderlist_pay);
        tv_orderlist_pay.setOnClickListener(this);
        tv_orderlist_receipt = (TextView) findViewById(R.id.tv_orderlist_receipt);
        tv_orderlist_receipt.setOnClickListener(this);
        tv_orderlist_comment = (TextView) findViewById(R.id.tv_orderlist_comment);
        tv_orderlist_comment.setOnClickListener(this);

    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("服务订单");
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            MyActivityManager.getInstance().finishActivity(BaoYangOrderListActivity.class);
            gotoMemeberFragment();
            finish();
        } else if (R.id.btn_refresh == id) {
            setPullState(false);
        } else {
            if (R.id.tv_orderlist_total == id) {
                KaKuApplication.state_order = "";
            } else if (R.id.tv_orderlist_pay == id) {
                KaKuApplication.state_order = "A";
            } else if (R.id.tv_orderlist_receipt == id) {
                KaKuApplication.state_order = "D";
            } else if (R.id.tv_orderlist_comment == id) {
                KaKuApplication.state_order = "E";
            }
            changeTextViewBg();
            setPullState(false);
        }
    }

    private void SetTextColor(TextView tv) {
        tv_orderlist_total.setTextColor(unSelectColor);
        tv_orderlist_pay.setTextColor(unSelectColor);
        tv_orderlist_receipt.setTextColor(unSelectColor);
        tv_orderlist_comment.setTextColor(unSelectColor);
        tv.setTextColor(selectColor);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //清空栈里的支付页面
        MyActivityManager.getInstance().finishActivity(BaoYangOrderListActivity.class);
        //返回到我的页面
        gotoMemeberFragment();
        return super.onKeyDown(keyCode, event);
    }

    private void gotoMemeberFragment() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME5);
        startActivity(intent);
    }


    //确认服务
    private void QueRenFuWu2() {
        showProgressDialog();
        BaoYangOrderCancleReq req = new BaoYangOrderCancleReq();
        req.code = "40064";
        req.id_upkeep_bill = KaKuApplication.id_upkeep_bill;

        KaKuApiProvider.BaoYangOrderFuWu(req, new KakuResponseListener<TruckOrderConfirmReceiptResp>(this, TruckOrderConfirmReceiptResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("querenfuwu res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        LogUtil.showShortToast(context, t.msg);
                        setPullState(false);
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
}
