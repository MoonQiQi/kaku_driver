package com.yichang.kaku.home.Ad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.AdvertBillObj;
import com.yichang.kaku.obj.ConfirmOrderProductObj;
import com.yichang.kaku.request.CheTieOrderCancleReq;
import com.yichang.kaku.request.CheTieOrderDetailReq;
import com.yichang.kaku.response.BaseResp;
import com.yichang.kaku.response.CheTieOrderDetailResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class CheTieOrderDetailActivity extends BaseActivity implements OnClickListener {
    //titleBar,返回，购物车，标题
    private TextView left, title;

    //private TextView tv_truck_order_id, tv_truck_order_state;
    private TextView tv_truck_order_id;

    //地址：收货人姓名、收货人电话、收货地址
    private TextView tv_address_name, tv_address_phone, tv_address_address;
    //支付方式
    private TextView tv_truck_paytype;

    //总价、运费、-积分、实付款
    private TextView tv_truck_totalprice, tv_truck_pricepoint, tv_truck_pricebill;
    //-余额
    private TextView tv_truck_moneybalance;
    //下单时间
    private TextView tv_order_time_create;
    //底部按钮
    private Button btn_top, btn_bottom;
    private String no_bill;
    private String id_bill;


    private List<ConfirmOrderProductObj> list = new ArrayList<>();
    //支付参数
    private String price_bill, desc, notify_url, out_trade_no;

    //产品图片
    private ImageView iv_sticker_product;
    //产品标题、价格
    private TextView tv_sticker_title;
    private TextView tv_sticker_price;
    private TextView tv_sticker_num;

    private View view_company_delivery;
    private View view_no_delivery;
    private View view_no_delivery1;
    private RelativeLayout rela_company_delivery;
    private RelativeLayout rela_no_delivery;

    private TextView tv_company_delivery;
    private TextView tv_no_delivery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chetie_order_detail);
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        initTitleBar();

        initOrderId();
        initAddress();

        tv_truck_paytype = (TextView) findViewById(R.id.tv_truck_paytype);
        tv_order_time_create = (TextView) findViewById(R.id.tv_order_time_create);

        initPrice();

        initButton();

        initStickerView();


        initDeliveryView();


        id_bill = getIntent().getStringExtra("id_bill");


        LogUtil.E("车品订单详情：idbill::" + id_bill);
        getOrderDetailInfo();
    }

    private void initDeliveryView() {
        view_company_delivery = findViewById(R.id.view_company_delivery);
        view_no_delivery = findViewById(R.id.view_no_delivery);
        view_no_delivery1 = findViewById(R.id.view_no_delivery1);

        rela_company_delivery = (RelativeLayout) findViewById(R.id.rela_company_delivery);
        rela_no_delivery = (RelativeLayout) findViewById(R.id.rela_no_delivery);
        tv_company_delivery = (TextView) findViewById(R.id.tv_company_delivery);
        tv_no_delivery = (TextView) findViewById(R.id.tv_no_delivery);
    }

    private void initStickerView() {
        iv_sticker_product = (ImageView) findViewById(R.id.iv_sticker_product1);
        tv_sticker_title = (TextView) findViewById(R.id.tv_sticker_title1);
        tv_sticker_price = (TextView) findViewById(R.id.tv_sticker_price1);
        tv_sticker_num = (TextView) findViewById(R.id.tv_sticker_num1);
    }

    private void initOrderId() {
        tv_truck_order_id = (TextView) findViewById(R.id.tv_truck_order_id);


    }

    private void getOrderDetailInfo() {
        Utils.NoNet(context);
        showProgressDialog();

        CheTieOrderDetailReq req = new CheTieOrderDetailReq();
        req.code = "60036";
        req.id_advert_bill = id_bill;

        KaKuApiProvider.getCheTieOrderDetail(req, new BaseCallback<CheTieOrderDetailResp>(CheTieOrderDetailResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, CheTieOrderDetailResp t) {
                if (t != null) {
                    LogUtil.E("getTruckOrderDetailInfo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {

                        setData(t.advert_bill);
//                        LogUtil.E("getTruckOrderDetailInfo res: " + t.order.toString());
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }

                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });


    }


    private void setData(AdvertBillObj order) {
        LogUtil.E("chaih order" + order.toString());
        BitmapUtil.download(iv_sticker_product, KaKuApplication.qian_zhui + order.getImage_advert());
        tv_sticker_title.setText(order.getName_advert().toString());
        tv_sticker_price.setText(order.getPrice_advert_single().toString());
        tv_sticker_num.setText("X"+order.getNum_advert().toString());

        tv_company_delivery.setText(order.getCompany_delivery());
        tv_no_delivery.setText(order.getNo_delivery());

        String type_pay = order.getType_pay();
        switch (type_pay) {

            case "1":
                tv_truck_paytype.setText("微信支付");
                break;
            case "0":
                tv_truck_paytype.setText("支付宝支付");
                break;
            case "9":
                tv_truck_paytype.setText("余额支付");
                break;
        }
        //如果订单状态为待支付，则支付方式为待支付
        setPageWidget(order.getState_bill());


        tv_truck_order_id.setText("订单号：" + order.getNo_bill().toUpperCase());
        no_bill = order.getNo_bill();
        tv_address_name.setText(order.getName_addr());
        tv_address_phone.setText(order.getPhone_addr());
        tv_address_address.setText(order.getAddr());


        tv_truck_totalprice.setText("￥" + order.getPrice_advert());


//        // TODO: 2015/8/19 暂无运费 tv_order_time_create
        //tv_truck_express_fee.setText("￥0.00");
        tv_truck_pricepoint.setText("-￥" + order.getBreaks_money());
        tv_truck_pricebill.setText("￥" + order.getPrice_bill());

        tv_truck_moneybalance.setText("￥" + order.getMoney_balance());
//为实付款赋值，在支付完成回调页上显示
        KaKuApplication.realPayment = order.getPrice_bill();

        price_bill = order.getPrice_bill();


        tv_order_time_create.setText("下单日期：" + order.getTime_create());


    }


    private void setPageWidget(String state) {
        switch (state) {
//            A待支付B已支付，待发货C已完成D已取消
            case "A":
                tv_truck_paytype.setText("待支付");

                setState("待支付");
                btn_top.setText("取消订单");
                btn_top.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        //未付款，取消订单使用60037 接口
                        cancleOrder(id_bill);
                    }
                });
                btn_bottom.setText("立即支付");
                btn_bottom.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        Intent intent = new Intent(context, OrderCheTiePayActivity.class);
                        intent.putExtra("no_bill", no_bill);
                        intent.putExtra("price_bill", price_bill);
                        startActivity(intent);
                    }
                });

                setDeliveryViews(true);
                break;
            case "B":
                setState("待发货");
                btn_top.setText("取消订单");
                btn_top.setVisibility(View.VISIBLE);
                btn_top.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        //未付款，取消订单使用60037 接口
                        cancleOrder(id_bill);

                    }
                });
                btn_bottom.setVisibility(View.GONE);
                setDeliveryViews(false);
                break;
            case "C":
                setState("已完成");
                btn_top.setVisibility(View.GONE);
                btn_bottom.setVisibility(View.GONE);
                setDeliveryViews(false);
                break;
            case "D":
                setState("已取消");
                btn_top.setVisibility(View.GONE);
                btn_bottom.setVisibility(View.GONE);
                setDeliveryViews(false);
                break;

        }
    }

    private void setDeliveryViews(Boolean isStateA) {
        if (isStateA) {

            view_company_delivery.setVisibility(View.GONE);
            view_no_delivery.setVisibility(View.GONE);
            view_no_delivery1.setVisibility(View.GONE);
            rela_company_delivery.setVisibility(View.GONE);
            rela_no_delivery.setVisibility(View.GONE);
        } else {
            view_company_delivery.setVisibility(View.VISIBLE);
            view_no_delivery.setVisibility(View.VISIBLE);
            view_no_delivery1.setVisibility(View.VISIBLE);
            rela_company_delivery.setVisibility(View.VISIBLE);
            rela_no_delivery.setVisibility(View.VISIBLE);

        }


    }



    @Override
    protected void onStop() {
        stopProgressDialog();
        //finish();
        super.onStop();
    }

    /*待支付页面 取消订单*/
    private void cancleOrder(String id_bill) {
        Utils.NoNet(context);
        showProgressDialog();

        CheTieOrderCancleReq req = new CheTieOrderCancleReq();
        req.code = "60037";
        req.id_advert_bill = id_bill;

        KaKuApiProvider.cancleCheTieOrder(req, new BaseCallback<BaseResp>(BaseResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, BaseResp t) {
                if (t != null) {
                    LogUtil.E("cancleTruckOrder res: " + t.res);
                    gotoCheTieOrderList();
                    LogUtil.showShortToast(context, t.msg);
                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });
    }


    private void gotoCheTieOrderList() {
        Intent intent = new Intent(context, CheTieOrderListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void setState(String state) {
        title.setText(state);
        //tv_truck_order_state.setText(state);
    }

    private void initButton() {
        btn_top = (Button) findViewById(R.id.btn_top);
        btn_bottom = (Button) findViewById(R.id.btn_bottom);
    }

    private void initPrice() {
        tv_truck_totalprice = (TextView) findViewById(R.id.tv_truck_totalprice);
        //tv_truck_express_fee = (TextView) findViewById(R.id.tv_truck_express_fee);
        tv_truck_pricepoint = (TextView) findViewById(R.id.tv_truck_pricepoint);
        tv_truck_pricebill = (TextView) findViewById(R.id.tv_truck_pricebill);

        tv_truck_moneybalance = (TextView) findViewById(R.id.tv_truck_moneybalance);
    }


    private void initAddress() {
        tv_address_name = (TextView) findViewById(R.id.tv_address_name);
        tv_address_phone = (TextView) findViewById(R.id.tv_address_phone);
        tv_address_address = (TextView) findViewById(R.id.tv_address_address);
    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        //title.setText("待付款");
    }

    @Override
    public void onClick(View v) {

        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();

        /*点击事件*/
        if (R.id.tv_left == id) {
            finish();
        }

    }


}
