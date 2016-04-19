package com.yichang.kaku.member.serviceorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.obj.OrderObj;
import com.yichang.kaku.payhelper.alipay.AlipayHelper;
import com.yichang.kaku.payhelper.wxpay.PayActivity;
import com.yichang.kaku.request.OrderListReq;
import com.yichang.kaku.request.WXPayInfoReq;
import com.yichang.kaku.response.OrderListResp;
import com.yichang.kaku.response.WXPayInfoResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.util.ArrayList;
import java.util.List;

public class ServiceOrderListActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private ImageView iv_orderlist_dayuhao;
    private TextView tv_orderlist_quanbu, tv_orderlist_daifukuan, tv_orderlist_daianzhuang,
            tv_orderlist_daiqueren, tv_orderlist_daipingjia, tv_orderlist_fanxiu;
    private boolean flag = false;
    private XListView xListView;
    private List<OrderObj> list_order = new ArrayList<OrderObj>();
    private final static int STEP = 5;
    private int start = 0, pageindex = 0, pagesize = STEP;
    private final static int INDEX = 5;// 一屏显示的个数
    private boolean isShowProgress = false;
    private int selectColor, unSelectColor;

	/*无数据和无网络界面*/

    private RelativeLayout layout_data_none, layout_net_none;
    private TextView tv_desc, tv_advice;
    private Button btn_refresh;
    private LinearLayout ll_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceorderlist);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("保养订单");
//初始化空白页
        initNoDataLayout();
        iv_orderlist_dayuhao = (ImageView) findViewById(R.id.iv_orderlist_dayuhao);
        iv_orderlist_dayuhao.setOnClickListener(this);
        tv_orderlist_quanbu = (TextView) findViewById(R.id.tv_orderlist_quanbu);
        tv_orderlist_quanbu.setOnClickListener(this);
        tv_orderlist_daifukuan = (TextView) findViewById(R.id.tv_orderlist_daifukuan);
        tv_orderlist_daifukuan.setOnClickListener(this);
        tv_orderlist_daianzhuang = (TextView) findViewById(R.id.tv_orderlist_daianzhuang);
        tv_orderlist_daianzhuang.setOnClickListener(this);
        tv_orderlist_daiqueren = (TextView) findViewById(R.id.tv_orderlist_daiqueren);
        tv_orderlist_daiqueren.setOnClickListener(this);
        tv_orderlist_daipingjia = (TextView) findViewById(R.id.tv_orderlist_daipingjia);
        tv_orderlist_daipingjia.setOnClickListener(this);
        tv_orderlist_fanxiu = (TextView) findViewById(R.id.tv_orderlist_fanxiu);
        tv_orderlist_fanxiu.setOnClickListener(this);
        tv_orderlist_fanxiu.setVisibility(View.GONE);
        xListView = (XListView) findViewById(R.id.lv_orderlist);
        xListView.setOnItemClickListener(this);
        selectColor = getResources().getColor(R.color.color_red);
        unSelectColor = getResources().getColor(R.color.black);
        if ("".equals(KaKuApplication.color_order)) {
            tv_orderlist_quanbu.setTextColor(selectColor);
            //setPullState(false);
        } else if ("A".equals(KaKuApplication.color_order)) {
            tv_orderlist_daifukuan.setTextColor(selectColor);
            //setPullState(false);
        } else if ("B".equals(KaKuApplication.color_order)) {
            tv_orderlist_daianzhuang.setTextColor(selectColor);
            //setPullState(false);
        } else if ("C".equals(KaKuApplication.color_order)) {
            tv_orderlist_daiqueren.setTextColor(selectColor);
            //setPullState(false);
        } else if ("D".equals(KaKuApplication.color_order)) {
            tv_orderlist_daipingjia.setTextColor(selectColor);
            //setPullState(false);
        } else if ("Z".equals(KaKuApplication.color_order)) {
            tv_orderlist_fanxiu.setTextColor(selectColor);
            tv_orderlist_quanbu.setVisibility(View.GONE);
            tv_orderlist_fanxiu.setVisibility(View.VISIBLE);
            iv_orderlist_dayuhao.setImageResource(R.drawable.jiantou);
            flag = true;
        }

    }

    private void SetTextColor(TextView tv) {

        tv_orderlist_quanbu.setTextColor(unSelectColor);
        tv_orderlist_daifukuan.setTextColor(unSelectColor);
        tv_orderlist_daianzhuang.setTextColor(unSelectColor);
        tv_orderlist_daiqueren.setTextColor(unSelectColor);
        tv_orderlist_daipingjia.setTextColor(unSelectColor);
        tv_orderlist_fanxiu.setTextColor(unSelectColor);
        tv.setTextColor(selectColor);

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

    @Override
    protected void onStart() {
        //tv_orderlist_quanbu.setTextColor(selectColor);
        switch (KaKuApplication.color_order) {
            case "":
                SetTextColor(tv_orderlist_quanbu);
                break;
            case "A":
                SetTextColor(tv_orderlist_daifukuan);
                break;
            case "B":
                SetTextColor(tv_orderlist_daianzhuang);
                break;
            case "C":
                SetTextColor(tv_orderlist_daiqueren);
                break;
            case "D":
                SetTextColor(tv_orderlist_daipingjia);
                break;
            case "Z":
                SetTextColor(tv_orderlist_fanxiu);
                tv_orderlist_quanbu.setVisibility(View.GONE);
                tv_orderlist_fanxiu.setVisibility(View.VISIBLE);
                iv_orderlist_dayuhao.setImageResource(R.drawable.jiantou);
                flag = true;
                break;
        }
        setPullState(false);
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_MEMBER);
            startActivity(intent);
            finish();
        } else if (R.id.iv_orderlist_dayuhao == id) {
            if (flag) {
                tv_orderlist_quanbu.setVisibility(View.VISIBLE);
                tv_orderlist_fanxiu.setVisibility(View.GONE);
                iv_orderlist_dayuhao.setImageResource(R.drawable.jinqu);
                flag = false;
            } else {
                tv_orderlist_quanbu.setVisibility(View.GONE);
                tv_orderlist_fanxiu.setVisibility(View.VISIBLE);
                iv_orderlist_dayuhao.setImageResource(R.drawable.jiantou);
                flag = true;
            }

        } else if (R.id.tv_orderlist_quanbu == id) {
            SetColor();
            tv_orderlist_quanbu.setTextColor(selectColor);
            KaKuApplication.state_order = "";
            KaKuApplication.color_order = "";
            setPullState(false);
        } else if (R.id.tv_orderlist_daianzhuang == id) {
            SetColor();
            tv_orderlist_daianzhuang.setTextColor(selectColor);
            KaKuApplication.state_order = "B";
            KaKuApplication.color_order = "B";
            setPullState(false);
        } else if (R.id.tv_orderlist_daifukuan == id) {
            SetColor();
            tv_orderlist_daifukuan.setTextColor(selectColor);
            KaKuApplication.state_order = "A";
            KaKuApplication.color_order = "A";
            setPullState(false);
        } else if (R.id.tv_orderlist_daipingjia == id) {
            SetColor();
            tv_orderlist_daipingjia.setTextColor(selectColor);
            KaKuApplication.state_order = "D";
            KaKuApplication.color_order = "D";
            setPullState(false);
        } else if (R.id.tv_orderlist_daiqueren == id) {
            SetColor();
            tv_orderlist_daiqueren.setTextColor(selectColor);
            KaKuApplication.state_order = "C";
            KaKuApplication.color_order = "C";
            setPullState(false);
        } else if (R.id.tv_orderlist_fanxiu == id) {
            SetColor();
            tv_orderlist_fanxiu.setTextColor(selectColor);
            KaKuApplication.state_order = "Z";
            KaKuApplication.color_order = "Z";
            setPullState(false);
        } else if (R.id.btn_refresh == id) {

            setPullState(false);

        }

    }

    public void ServiceOrderList(int pageIndex, int pageSize) {
        //Utils.NoNet(context);
        if (!Utils.checkNetworkConnection(context)) {
            ll_container.setVisibility(View.GONE);
            layout_data_none.setVisibility(View.GONE);
            layout_net_none.setVisibility(View.VISIBLE);
            return;
        } else {
            layout_data_none.setVisibility(View.GONE);
            layout_net_none.setVisibility(View.GONE);
            ll_container.setVisibility(View.VISIBLE);
        }

        OrderListReq req = new OrderListReq();
        req.code = "40021";
        req.id_driver = Utils.getIdDriver();
        req.state_order = KaKuApplication.state_order;
        req.start = String.valueOf(pageIndex);
        req.len = String.valueOf(pageSize);
        KaKuApiProvider.SerViceOrderList(req, new KakuResponseListener<OrderListResp>(this, OrderListResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("orderlist res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t.orders);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                    onLoadStop();
                }
            }

        });
    }

    private void setData(List<OrderObj> list) {
        // TODO Auto-generated method stub
        if (list != null) {
            list_order.addAll(list);
        }
        LogUtil.E("service order list:::" + list_order.toString());

        if (list_order.size() == 0) {

            layout_data_none.setVisibility(View.VISIBLE);
            ll_container.setVisibility(View.GONE);
            layout_net_none.setVisibility(View.GONE);
            return;
        } else {
            layout_data_none.setVisibility(View.GONE);
            ll_container.setVisibility(View.VISIBLE);
            layout_net_none.setVisibility(View.GONE);
        }

        ServiceOrderAdapter adapter = new ServiceOrderAdapter(this, list_order);
        adapter.setCallback(new ServiceOrderAdapter.CallBack() {



            /*todo 获取支付参数，判断支付方式*/
            @Override
            public void payOrder(String no_order, String flag_pay, String price_order) {
                KaKuApplication.realPayment=price_order;
                mPrice_order = price_order;
            /*    mFlag_pay = flag_pay;*/

                mNo_order = no_order;
                KaKuApplication.payType = "SERVICE";
                switch (flag_pay) {
                    case "0":
                        ServiceOrderListActivity.this.payOrder();
                        //finish();
                        //WXPay();
                        break;
                    case "1":

                        ServiceOrderListActivity.this.aliPay();
                        // finish();
                        break;

                }

            }


        });
        xListView.setAdapter(adapter);
        xListView.setPullLoadEnable(list.size() < INDEX ? false : true);
        xListView.setSelection(pageindex);
        xListView.setPullRefreshEnable(false);
        xListView.setXListViewListener(new XListView.IXListViewListener() {

            @Override
            public void onRefresh() {
                if (!Utils.checkNetworkConnection(context)) {
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    layout_data_none.setVisibility(View.GONE);
                    layout_net_none.setVisibility(View.VISIBLE);
                    ll_container.setVisibility(View.GONE);
                    xListView.stopRefresh();
                    return;
                }
                setPullState(false);
            }

            @Override
            public void onLoadMore() {
                if (!Utils.checkNetworkConnection(context)) {
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    layout_data_none.setVisibility(View.GONE);
                    layout_net_none.setVisibility(View.VISIBLE);
                    ll_container.setVisibility(View.GONE);
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
            if (list_order != null) {
                list_order.clear();
            }
        }
        ServiceOrderList(pageindex, pagesize);
    }

    private void onLoadStop() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime(DateUtil.dateFormat());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Utils.Many()){
            return;
        }
        if ("A".equals(list_order.get(position - 1).getState_order())) {
            KaKuApplication.id_orderA = list_order.get(position - 1).getId_order();
            Intent intent = new Intent(context, OrderDetailAActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if ("B".equals(list_order.get(position - 1).getState_order())) {
            KaKuApplication.id_orderB = list_order.get(position - 1).getId_order();
            Intent intent = new Intent(context, OrderDetailBActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if ("C".equals(list_order.get(position - 1).getState_order())) {
            KaKuApplication.id_orderC = list_order.get(position - 1).getId_order();
            Intent intent = new Intent(context, OrderDetailCActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if ("D".equals(list_order.get(position - 1).getState_order())) {
            KaKuApplication.id_orderD = list_order.get(position - 1).getId_order();
            Intent intent = new Intent(context, OrderDetailDActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if ("E".equals(list_order.get(position - 1).getState_order())) {
            KaKuApplication.id_orderE = list_order.get(position - 1).getId_order();
            Intent intent = new Intent(context, OrderDetailEActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if ("F".equals(list_order.get(position - 1).getState_order())) {
            KaKuApplication.id_orderF = list_order.get(position - 1).getId_order();
            Intent intent = new Intent(context, OrderDetailFActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if ("G".equals(list_order.get(position - 1).getState_order())) {
            KaKuApplication.id_orderG = list_order.get(position - 1).getId_order();
            Intent intent = new Intent(context, OrderDetailGActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if ("Z".equals(list_order.get(position - 1).getState_order())) {
            KaKuApplication.id_orderZ = list_order.get(position - 1).getId_order();
            Intent intent = new Intent(context, OrderDetailZActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void SetColor() {
        tv_orderlist_quanbu.setTextColor(unSelectColor);
        tv_orderlist_daianzhuang.setTextColor(unSelectColor);
        tv_orderlist_daifukuan.setTextColor(unSelectColor);
        tv_orderlist_daipingjia.setTextColor(unSelectColor);
        tv_orderlist_daiqueren.setTextColor(unSelectColor);
        tv_orderlist_fanxiu.setTextColor(unSelectColor);
    }


    //支付的方法
    final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);

    private String mPrice_order;
/*    private String mFlag_pay;*/
    private String mNo_order;
 /*   public String mId_order;*/

    private void payOrder() {
        Utils.NoNet(context);

        WXPayInfoReq req = new WXPayInfoReq();
        req.code = "30021";
        req.no_bill=mNo_order;
        //req.fee

        KaKuApiProvider.getWXPayInfo(req, new KakuResponseListener<WXPayInfoResp>(this, WXPayInfoResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);

                if (t != null) {
                    LogUtil.E("getWXPayInfo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        wxPay(t);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

        });
    }

    private void wxPay(WXPayInfoResp params) {

        if (!msgApi.isWXAppInstalled()) {
            LogUtil.showShortToast(context, "您尚未安装微信客户端");
            //finish();
            return;
        }
        if (!msgApi.isWXAppSupportAPI()) {
            LogUtil.showShortToast(context, "您的微信版本不支持微信支付");
            //finish();
            return;
        }
        /*todo 确定idorder by123456123*/
        KaKuApplication.id_order = mNo_order;

        LogUtil.E("支付参数" + params.toString());
        Intent intent = new Intent(context, PayActivity.class);

        //intent.putExtra("no_order", params.no_bill);
        intent.putExtra("appid", params.appid);
        intent.putExtra("noncestr", params.noncestr);
        intent.putExtra("package", params.package0);
        intent.putExtra("partnerid", params.partnerid);
        intent.putExtra("prepayid", params.prepay_id);
        intent.putExtra("timestamp", params.timestamp);
        intent.putExtra("sign", params.sign);


        try {

            context.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //支付宝支付参数


    private void aliPay() {
        //支付宝支付
        AlipayHelper helper = new AlipayHelper(ServiceOrderListActivity.this);
        //todo dou
        helper.pay("卡库养车" + mNo_order, "服务订单", mPrice_order, mNo_order);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_MEMBER);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
