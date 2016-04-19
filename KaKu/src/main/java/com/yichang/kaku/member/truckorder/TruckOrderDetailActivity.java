package com.yichang.kaku.member.truckorder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.OrderPayActivity;
import com.yichang.kaku.home.giftmall.ConfirmOrderAdapter;
import com.yichang.kaku.obj.ConfirmOrderProductObj;
import com.yichang.kaku.obj.TruckOrderDetailObj;
import com.yichang.kaku.request.TruckOrderCancleReq;
import com.yichang.kaku.request.TruckOrderConfirmReceiptReq;
import com.yichang.kaku.request.TruckOrderDeleteReq;
import com.yichang.kaku.request.TruckOrderDetailReq;
import com.yichang.kaku.response.TruckOrderCancleResp;
import com.yichang.kaku.response.TruckOrderConfirmReceiptResp;
import com.yichang.kaku.response.TruckOrderDeleteResp;
import com.yichang.kaku.response.TruckOrderDetailResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TruckOrderDetailActivity extends BaseActivity implements OnClickListener {
    //titleBar,返回，购物车，标题
    private TextView left, title;

    //private TextView tv_truck_order_id, tv_truck_order_state;
    private TextView tv_truck_order_id;
    private ListView lv_truck_detail_list;
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
    private LinearLayout line_address_xianchanggoumai;
    private View view_order_xianchang;
    private List<ConfirmOrderProductObj> list = new ArrayList<>();
    //支付参数
    private String price_bill, desc, notify_url, out_trade_no;
    private String type_receive;
    private String message;
    private RelativeLayout rela_chepinorder_noaddr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_order_detail);

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
        lv_truck_detail_list = (ListView) findViewById(R.id.lv_truck_detail_list);


        tv_truck_paytype = (TextView) findViewById(R.id.tv_truck_paytype);
        tv_order_time_create = (TextView) findViewById(R.id.tv_order_time_create);
        line_address_xianchanggoumai = (LinearLayout) findViewById(R.id.line_address_xianchanggoumai);
        rela_chepinorder_noaddr = (RelativeLayout) findViewById(R.id.rela_chepinorder_noaddr);
        view_order_xianchang = findViewById(R.id.view_order_xianchang);
        initPrice();

        initButton();

        id_bill = getIntent().getStringExtra("idbill");

        getOrderDetailInfo();
    }

    private void initOrderId() {
        tv_truck_order_id = (TextView) findViewById(R.id.tv_truck_order_id);

    }

    private void getOrderDetailInfo() {
        Utils.NoNet(context);

        TruckOrderDetailReq req = new TruckOrderDetailReq();
        req.code = "30016";
        req.id_bill = id_bill;

        KaKuApiProvider.getTruckOrderDetailInfo(req, new KakuResponseListener<TruckOrderDetailResp>(this, TruckOrderDetailResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getTruckOrderDetailInfo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t.shopcar);
                        setData(t.bill);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }
        });
    }

    private void setData(List<ConfirmOrderProductObj> shopcar) {
        if (shopcar != null) {
            list.clear();
            list.addAll(shopcar);
        }

        ConfirmOrderAdapter adapter = new ConfirmOrderAdapter(context, list);
        lv_truck_detail_list.setAdapter(adapter);
        setListViewHeightBasedOnChildren(lv_truck_detail_list);
    }

    private void setData(TruckOrderDetailObj order) {
        String type_pay = order.getType_pay();
        type_receive = order.getType_receive();
        if ("X".equals(type_receive)) {
            line_address_xianchanggoumai.setVisibility(View.GONE);
            view_order_xianchang.setVisibility(View.GONE);
            rela_chepinorder_noaddr.setVisibility(View.VISIBLE);
        } else {
            line_address_xianchanggoumai.setVisibility(View.VISIBLE);
            view_order_xianchang.setVisibility(View.VISIBLE);
            rela_chepinorder_noaddr.setVisibility(View.GONE);
        }
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


        tv_truck_order_id.setText("订单号：" + order.getNo_bill());
        no_bill = order.getNo_bill();
        tv_address_name.setText(order.getName_addr());
        tv_address_phone.setText(order.getPhone_addr());
        tv_address_address.setText(order.getAddr());


        tv_truck_totalprice.setText("￥" + order.getPrice_goods());


//        // TODO: 2015/8/19 暂无运费 tv_order_time_create
        tv_truck_pricepoint.setText("-￥" + order.getPrice_point());
        tv_truck_pricebill.setText("￥" + order.getPrice_bill());

        tv_truck_moneybalance.setText("￥" + order.getMoney_balance());
//为实付款赋值，在支付完成回调页上显示
        KaKuApplication.realPayment = order.getPrice_bill();

        price_bill = order.getPrice_bill();

        tv_order_time_create.setText("下单日期：" + order.getTime_create());


    }


    private void setPageWidget(String state) {
        switch (state) {
//            待付款A，待收货B，已签收D，待评价E，已评价F，已取消G，已删除H退换货处理中Z
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
                        //未付款，取消订单使用30012接口
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
                        Intent intent = new Intent(context, OrderPayActivity.class);
                        intent.putExtra("no_bill", no_bill);
                        intent.putExtra("price_bill", price_bill);
                        startActivity(intent);
                    }
                });
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
                        //已付款，申请取消订单使用30020接口
                        requestCancleOrder(id_bill);

                    }
                });
                btn_bottom.setVisibility(View.GONE);
                break;
            case "D":
                setState("待收货");
                btn_top.setVisibility(View.GONE);
                if ("Y".equals(type_receive)) {
                    btn_bottom.setText("确认收货");
                    message = "是否确认收货？";
                } else {
                    btn_bottom.setText("现场领取");
                    message = "您确认已领取到商品？";
                }
                btn_bottom.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(TruckOrderDetailActivity.this);
                        builder.setTitle("提示");
                        builder.setMessage(message);
                        builder.setNegativeButton("是", new android.content.DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                confirmReceiptOrder(id_bill);
                            }
                        });

                        builder.setPositiveButton("否", new android.content.DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                });
                break;
            case "E":
                setState("待评价");
                if ("X".equals(type_receive)) {
                    btn_top.setVisibility(View.GONE);
                } else {
                    btn_top.setVisibility(View.VISIBLE );
                }
                btn_top.setText("申请退货");
                btn_top.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (Utils.Many()) {
                            return;
                        }

                        Intent intent = new Intent(TruckOrderDetailActivity.this, TruckOrderReturnActivity.class);
                        intent.putExtra("id_bill", id_bill);
                        startActivity(intent);

                    }
                });
                btn_bottom.setText("评价订单");
                btn_bottom.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*todo 评价订单*/
                        if (Utils.Many()) {
                            return;
                        }
                        Intent intent = new Intent(context, OrderCommentActivity.class);
                        intent.putExtra("shopCarList", (Serializable) list);
                        intent.putExtra("id_bill", id_bill);
                        startActivity(intent);

                    }
                });
                break;
            case "F":
                setState("已评价");
                btn_top.setText("申请退货");
                btn_top.setVisibility(View.GONE);
                btn_top.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*todo 评价订单*/
                        if (Utils.Many()) {
                            return;
                        }
                        //已评价订单，申请退换货使用30014接口

                        Intent intent = new Intent(TruckOrderDetailActivity.this, TruckOrderReturnActivity.class);
                        intent.putExtra("id_bill", id_bill);
                        startActivity(intent);

                    }
                });
                btn_bottom.setText("删除订单");
                btn_bottom.setVisibility(View.GONE);
                btn_bottom.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("提示");
                        builder.setMessage("是否删除订单？");
                        builder.setNegativeButton("是", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteOrder(id_bill);
                            }
                        });

                        builder.setPositiveButton("否", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();

                    }
                });
                break;
            case "G":
                setState("已取消");
                btn_top.setVisibility(View.GONE);
                btn_bottom.setText("删除订单");
                btn_bottom.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("提示");
                        builder.setMessage("是否删除订单？");
                        builder.setNegativeButton("是", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteOrder(id_bill);
                            }
                        });

                        builder.setPositiveButton("否", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                });
                break;
            case "H":
                setState("取消申请中");
                btn_top.setVisibility(View.GONE);
                btn_bottom.setVisibility(View.GONE);
                break;
            case "I":
                setState("已退货");
                btn_top.setVisibility(View.GONE);
                btn_bottom.setText("删除订单");
                btn_bottom.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("提示");
                        builder.setMessage("是否删除订单？");
                        builder.setNegativeButton("是", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteOrder(id_bill);
                            }
                        });

                        builder.setPositiveButton("否", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                });
                break;
            case "Z":
                setState("退换货处理中");
                btn_top.setVisibility(View.GONE);
                btn_bottom.setVisibility(View.GONE);
                /*todo 退换货弹窗*/
                break;
        }
    }

    private void setComment(String id_bill) {
        Intent intent = new Intent(getApplicationContext(), TruckOrderCommentActivity.class);
        intent.putExtra("idbill", id_bill);
        startActivity(intent);
    }

    //确认收货
    private void confirmReceiptOrder(String id_bill) {
        Utils.NoNet(context);

        TruckOrderConfirmReceiptReq req = new TruckOrderConfirmReceiptReq();
        req.code = "30010";
        req.id_bill = id_bill;

        KaKuApiProvider.comfirmReceiptTruckOrder(req, new KakuResponseListener<TruckOrderConfirmReceiptResp>(this, TruckOrderConfirmReceiptResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("comfirmReceiptTruckOrder res: " + t.res);
                    gotoTruckOrderList();
                    LogUtil.showShortToast(context, t.msg);
                }
            }

        });
    }

    private void deleteOrder(String id_bill) {
        Utils.NoNet(context);

        TruckOrderDeleteReq req = new TruckOrderDeleteReq();
        req.code = "30013";
        req.id_bill = id_bill;

        KaKuApiProvider.deleteTruckOrder(req, new KakuResponseListener<TruckOrderDeleteResp>(this, TruckOrderDeleteResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("deleteTruckOrder res: " + t.res);
                    gotoTruckOrderList();
                    LogUtil.showShortToast(context, t.msg);
                }
            }
        });
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

        TruckOrderCancleReq req = new TruckOrderCancleReq();
        req.code = "30012";
        req.id_bill = id_bill;

        KaKuApiProvider.cancleTruckOrder(req, new KakuResponseListener<TruckOrderCancleResp>(this, TruckOrderCancleResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("cancleTruckOrder res: " + t.res);
                    gotoTruckOrderList();
                    LogUtil.showShortToast(context, t.msg);
                }
            }

        });
    }

    /*待支付页面 取消订单*/
    private void requestCancleOrder(String id_bill) {
        Utils.NoNet(context);

        TruckOrderCancleReq req = new TruckOrderCancleReq();
        req.code = "30012";
        req.id_bill = id_bill;

        KaKuApiProvider.cancleTruckOrder(req, new KakuResponseListener<TruckOrderCancleResp>(this, TruckOrderCancleResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("cancleTruckOrder res: " + t.res);
                    gotoTruckOrderList();
                    LogUtil.showShortToast(context, t.msg);
                }
            }

        });
    }

    private void gotoTruckOrderList() {
        Intent intent = new Intent(context, TruckOrderListActivity.class);
//        intent.putExtra("state", "");
        KaKuApplication.truck_order_state = "";
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

    /*
   * 显示listView条目，在使用ScrollView嵌套时必须注意要在setAdapter之后调用以下方法
   * 否则listView条目显示不全，只能显示其中一条
   * */
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

}
