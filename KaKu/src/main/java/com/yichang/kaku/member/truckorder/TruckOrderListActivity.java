package com.yichang.kaku.member.truckorder;

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
import com.yichang.kaku.home.OrderPayActivity;
import com.yichang.kaku.home.giftmall.ProductDetailActivity;
import com.yichang.kaku.home.giftmall.ShopCartActivity;
import com.yichang.kaku.home.huafei.HuaFeiActivity;
import com.yichang.kaku.obj.TruckOrderObj;
import com.yichang.kaku.request.BuyAgainReq;
import com.yichang.kaku.request.TruckOrderListReq;
import com.yichang.kaku.response.BuyAgainResp;
import com.yichang.kaku.response.TruckOrderListResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class TruckOrderListActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {
    //titleBar,返回，购物车，标题
    private TextView left, right, title;
    //订单列表
    private XListView xListView;
    private List<TruckOrderObj> list_truckorder = new ArrayList<TruckOrderObj>();
    //全部，待付款，待收货，待评价，退/换货
//    private TextView tv_orderlist_total, tv_orderlist_pay, tv_orderlist_receipt, tv_orderlist_comment, tv_orderlist_exchange;
    private TextView tv_orderlist_total, tv_orderlist_pay, tv_orderlist_receipt, tv_orderlist_comment, tv_orderlist_exchange;
/*无数据和无网络界面*/

    private RelativeLayout layout_data_none, layout_net_none;
    private TextView tv_desc, tv_advice;
    private Button btn_refresh;
    private LinearLayout ll_container;


    //数据分页加载所需参数
    private TruckOrderAdapter adapter;
    private final static int STEP = 5;
    private int start = 0, pageindex = 0, pagesize = STEP;
    private final static int INDEX = 5;// 一屏显示的个数
    private boolean isShowProgress = false;
    //    当前页面状态，待付款、待评价。。。
    private String state = "";
    //文字被选中颜色
    private int selectColor, unSelectColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truckorderlist);
        init();
    }

    @Override
    protected void onStart() {
        getOrderState();
        changeTextViewBg(state);
        setPullState(false);
        super.onStart();
    }

    private void init() {
        initTitleBar();

        initNoDataLayout();
        xListView = (XListView) findViewById(R.id.lv_orderlist);
        xListView.setOnItemClickListener(this);
        selectColor = getResources().getColor(R.color.color_red);
        unSelectColor = getResources().getColor(R.color.black);

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

    private void changeTextViewBg(String state) {
        if (state != null) {
            switch (state) {
                case "A":
                    SetTextColor(tv_orderlist_pay);
                    break;
                case "B":
                    SetTextColor(tv_orderlist_receipt);
                    break;
                case "E":
                    SetTextColor(tv_orderlist_comment);
                    break;
                case "Z":
                    SetTextColor(tv_orderlist_exchange);
                    break;
                default:
                    SetTextColor(tv_orderlist_total);
                    break;
            }
        }

    }

    private void getOrderState() {
        state = KaKuApplication.truck_order_state;
        changeTextViewBg(state);
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
        req.code = "30015";
        req.id_driver = Utils.getIdDriver();
        req.state_bill = state;
        req.start = String.valueOf(pageindex);
        req.len = String.valueOf(pagesize);

        KaKuApiProvider.getTruckOrderList(req, new KakuResponseListener<TruckOrderListResp>(this, TruckOrderListResp.class) {
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

    private void setData(List<TruckOrderObj> list) {
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

        TruckOrderAdapter adapter = new TruckOrderAdapter(this, list_truckorder);
        adapter.setCallback(new TruckOrderAdapter.CallBack() {
            @Override
            public void payOrder(String no_bill, String flag_pay, String price_bill) {
                KaKuApplication.realPayment = price_bill;

                KaKuApplication.payType = "TRUCK";

                Intent intent = new Intent(context, OrderPayActivity.class);
                intent.putExtra("no_bill", no_bill);
                intent.putExtra("price_bill", price_bill);
                startActivity(intent);

            }

            @Override
            public void BuyAgain(String id_addr, String flag_recharge) {
                BuyAga(id_addr, flag_recharge);
            }
        });

        xListView.setAdapter(adapter);
        // adapter.notifyDataSetChanged();
        xListView.setPullLoadEnable(list.size() < INDEX ? false : true);
        xListView.setSelection(pageindex);
        xListView.setPullRefreshEnable(false);


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
        tv_orderlist_exchange = (TextView) findViewById(R.id.tv_orderlist_exchange);
        tv_orderlist_exchange.setOnClickListener(this);

    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("车品订单");
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

            MyActivityManager.getInstance().finishActivity(OrderPayActivity.class);
            gotoMemeberFragment();

        } else if (R.id.btn_refresh == id) {
            //changeTextViewBg(state);
            setPullState(false);

        } else {
            if (R.id.tv_orderlist_total == id) {
//          全部订单
                state = "";

            } else if (R.id.tv_orderlist_pay == id) {
//			待付款订单
                state = "A";
            } else if (R.id.tv_orderlist_receipt == id) {
//			待收货订单
                state = "B";
            } else if (R.id.tv_orderlist_comment == id) {
//			待评价订单
                state = "E";
            } else if (R.id.tv_orderlist_exchange == id) {
//          退换货订单
                state = "Z";
            }
            KaKuApplication.truck_order_state = state;
            changeTextViewBg(state);
            setPullState(false);
        }


    }

    private void SetTextColor(TextView tv) {
        tv_orderlist_total.setTextColor(unSelectColor);
        tv_orderlist_pay.setTextColor(unSelectColor);
        tv_orderlist_receipt.setTextColor(unSelectColor);
        tv_orderlist_comment.setTextColor(unSelectColor);
        tv_orderlist_exchange.setTextColor(unSelectColor);
        tv.setTextColor(selectColor);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Utils.Many()) {
            return;
        }
        Intent intent = new Intent(getApplicationContext(), TruckOrderDetailActivity.class);
        /*todo 修改idbill为nobill*/
        intent.putExtra("idbill", list_truckorder.get(position - 1).getId_bill());
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        /*if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键

            return false;
        }*/

        //清空栈里的支付页面
        MyActivityManager.getInstance().finishActivity(OrderPayActivity.class);
        //返回到我的页面
        gotoMemeberFragment();
        return super.onKeyDown(keyCode, event);
    }

    private void gotoMemeberFragment() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME5);
        startActivity(intent);
    }


    public void BuyAga(String id_bill, final String flag_recharge) {
        showProgressDialog();
        BuyAgainReq req = new BuyAgainReq();
        req.code = "30029";
        req.id_bill = id_bill;
        KaKuApiProvider.BuyAgain(req, new KakuResponseListener<BuyAgainResp>(context, BuyAgainResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("login res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        if ("Y".equals(flag_recharge)) {
                            context.startActivity(new Intent(context, HuaFeiActivity.class));
                        } else {
                            if ("Y".equals(t.add_can)) {
                                context.startActivity(new Intent(context, ShopCartActivity.class));
                            } else {
                                KaKuApplication.id_goods = t.id_goods;
                                context.startActivity(new Intent(context, ProductDetailActivity.class));
                            }
                        }
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                        }
                        LogUtil.showShortToast(context, t.msg);
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
