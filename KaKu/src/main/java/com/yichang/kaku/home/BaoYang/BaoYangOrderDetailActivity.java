package com.yichang.kaku.home.baoyang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.Worker2Obj;
import com.yichang.kaku.request.BaoYangOrderCancleReq;
import com.yichang.kaku.request.BaoYangOrderDeleteReq;
import com.yichang.kaku.request.BaoYangOrderDetailReq;
import com.yichang.kaku.response.BaoYangOrderDetailResp;
import com.yichang.kaku.response.TruckOrderCancleResp;
import com.yichang.kaku.response.TruckOrderConfirmReceiptResp;
import com.yichang.kaku.response.TruckOrderDeleteResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class BaoYangOrderDetailActivity extends BaseActivity implements OnClickListener {
    //titleBar,返回，购物车，标题
    private TextView left, title, right;
    private TextView tv_truck_order_id;
    //支付方式
    private TextView tv_truck_paytype;
    private TextView tv_truck_moneybalance, tv_truck_totalprice, tv_truck_pricebill, tv_truck_moneyyouhuiquan;
    private TextView tv_order_time_create;
    private ImageView btn_left, btn_right, btn_mid;
    private String no_bill, phone, name_addr, phone_addr, addr;
    private String price_bill;
    private ImageView iv_order_saomahexiao, iv_order_qrcode, iv_baoyangorderrdetail_call;
    private FrameLayout frame_order;
    private TextView tv_baoyangorderrdetail_faqishijianzi, tv_baoyangorderrdetail_faqishijian, tv_baoyangorderrdetail_fuwudidian,
            tv_baoyangorderrdetail_addrname, tv_baoyangorderrdetail_addr, tv_baoyangdetail_gong, tv_order_huodongprice, tv_order_qrcode;
    private ListView lv_baoyangdetail;
    private String flag_state, type_pay;
    private RelativeLayout rela_baoyangorderrdetail_fuwudidian, rela_order_huodongprice;
    private Worker2Obj worker;
    private View view_huodongyouhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baoyang_order_detail);
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        title = (TextView) findViewById(R.id.tv_mid);
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title.setText("订单详情");
        tv_truck_order_id = (TextView) findViewById(R.id.tv_truck_order_id);
        tv_truck_paytype = (TextView) findViewById(R.id.tv_truck_paytype);
        tv_order_time_create = (TextView) findViewById(R.id.tv_order_time_create);
        tv_order_qrcode = (TextView) findViewById(R.id.tv_order_qrcode);
        tv_baoyangorderrdetail_faqishijianzi = (TextView) findViewById(R.id.tv_baoyangorderrdetail_faqishijianzi);
        tv_baoyangorderrdetail_faqishijian = (TextView) findViewById(R.id.tv_baoyangorderrdetail_faqishijian);
        tv_baoyangorderrdetail_fuwudidian = (TextView) findViewById(R.id.tv_baoyangorderrdetail_fuwudidian);
        tv_baoyangorderrdetail_addrname = (TextView) findViewById(R.id.tv_baoyangorderrdetail_addrname);
        tv_baoyangorderrdetail_addr = (TextView) findViewById(R.id.tv_baoyangorderrdetail_addr);
        tv_baoyangdetail_gong = (TextView) findViewById(R.id.tv_baoyangdetail_gong);
        tv_order_huodongprice = (TextView) findViewById(R.id.tv_order_huodongprice);
        rela_baoyangorderrdetail_fuwudidian = (RelativeLayout) findViewById(R.id.rela_baoyangorderrdetail_fuwudidian);
        rela_order_huodongprice = (RelativeLayout) findViewById(R.id.rela_order_huodongprice);
        tv_truck_totalprice = (TextView) findViewById(R.id.tv_truck_totalprice);
        tv_truck_pricebill = (TextView) findViewById(R.id.tv_truck_pricebill);
        tv_truck_moneybalance = (TextView) findViewById(R.id.tv_truck_moneybalance);
        tv_truck_moneyyouhuiquan = (TextView) findViewById(R.id.tv_truck_moneyyouhuiquan);
        view_huodongyouhui = findViewById(R.id.view_huodongyouhui);
        btn_left = (ImageView) findViewById(R.id.btn_left);
        btn_right = (ImageView) findViewById(R.id.btn_right);
        btn_mid = (ImageView) findViewById(R.id.btn_mid);
        iv_order_qrcode = (ImageView) findViewById(R.id.iv_order_qrcode);
        iv_baoyangorderrdetail_call = (ImageView) findViewById(R.id.iv_baoyangorderrdetail_call);
        iv_baoyangorderrdetail_call.setOnClickListener(this);
        iv_order_saomahexiao = (ImageView) findViewById(R.id.iv_order_saomahexiao);
        iv_order_saomahexiao.setOnClickListener(this);
        frame_order = (FrameLayout) findViewById(R.id.frame_order);
        frame_order.setOnClickListener(this);
        lv_baoyangdetail = (ListView) findViewById(R.id.lv_baoyangdetail);
        getOrderDetailInfo();
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
        } else if (R.id.iv_order_saomahexiao == id) {
            frame_order.setVisibility(View.VISIBLE);
        } else if (R.id.frame_order == id) {
            frame_order.setVisibility(View.GONE);
        } else if (R.id.iv_baoyangorderrdetail_call == id) {
            Utils.Call(context, phone);
        }
    }

    private void getOrderDetailInfo() {
        showProgressDialog();
        BaoYangOrderDetailReq req = new BaoYangOrderDetailReq();
        req.code = "400122";
        req.id_upkeep_bill = KaKuApplication.id_upkeep_bill;
        KaKuApiProvider.BaoYangOrderDetail(req, new KakuResponseListener<BaoYangOrderDetailResp>(this, BaoYangOrderDetailResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("BaoYangOrderDetail res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t);
                    } else {
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

    private void setData(BaoYangOrderDetailResp t) {
        flag_state = t.flag_type;
        if ("U".equals(flag_state)) {
            tv_baoyangorderrdetail_faqishijianzi.setText("预约时间");
            tv_baoyangorderrdetail_faqishijian.setText(t.upkeep_bill.getTime_order());
            rela_baoyangorderrdetail_fuwudidian.setVisibility(View.GONE);
        } else {
            tv_baoyangorderrdetail_faqishijianzi.setText("发起时间");
            tv_baoyangorderrdetail_faqishijian.setText(t.upkeep_bill.getTime_create());
            rela_baoyangorderrdetail_fuwudidian.setVisibility(View.VISIBLE);
            tv_baoyangdetail_gong.setVisibility(View.GONE);
        }

        type_pay = t.upkeep_bill.getType_pay();
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
            case "8":
                tv_truck_paytype.setText("线下支付");
                break;
            case "7":
                tv_truck_paytype.setText("未选择支付方式");
                break;
        }
        setPageWidget(t.upkeep_bill.getState_bill());
        no_bill = t.upkeep_bill.getNo_bill();
        phone = t.upkeep_bill.getPhone_addr();
        worker = t.upkeep_bill.getWorker();
        addr = t.upkeep_bill.getAddr();
        name_addr = t.upkeep_bill.getName_addr();
        phone_addr = t.upkeep_bill.getPhone_addr();
        if (!"".equals(t.upkeep_bill.getCode_used())) {
            iv_order_saomahexiao.setVisibility(View.VISIBLE);
            tv_order_qrcode.setText(t.upkeep_bill.getCode_used());
            Bitmap creatMyCode = Utils.createQRCode(t.upkeep_bill.getCode_used());
            iv_order_qrcode.setImageBitmap(creatMyCode);
        } else {
            iv_order_saomahexiao.setVisibility(View.INVISIBLE);
        }

        tv_truck_order_id.setText("订单编号：" + t.upkeep_bill.getNo_bill());
        tv_baoyangorderrdetail_fuwudidian.setText(t.upkeep_bill.getService_addr());
        tv_baoyangorderrdetail_addrname.setText(t.upkeep_bill.getName_addr());
        tv_baoyangorderrdetail_addr.setText(t.upkeep_bill.getAddr());
        tv_truck_totalprice.setText("￥" + t.upkeep_bill.getPrice_goods());
        tv_truck_pricebill.setText("￥" + t.upkeep_bill.getPrice_bill());
        tv_truck_moneybalance.setText("-￥" + t.upkeep_bill.getPrice_balance());
        tv_truck_moneyyouhuiquan.setText("-￥" + t.upkeep_bill.getPrice_coupon());
        if ("0.00".equals(Utils.numdouble(t.upkeep_bill.getPrice_activity()))) {
            rela_order_huodongprice.setVisibility(View.GONE);
            view_huodongyouhui.setVisibility(View.GONE);
        } else {
            rela_order_huodongprice.setVisibility(View.VISIBLE);
            view_huodongyouhui.setVisibility(View.VISIBLE);
        }
        tv_order_huodongprice.setText("-￥" + t.upkeep_bill.getPrice_activity());
        tv_baoyangdetail_gong.setText("共" + t.shopcar.size() + "件商品，1项工时费，应付：¥" + t.upkeep_bill.getPrice_bill());

        KaKuApplication.realPayment = t.upkeep_bill.getPrice_bill();
        price_bill = t.upkeep_bill.getPrice_bill();
        tv_order_time_create.setText("下单时间：" + t.upkeep_bill.getTime_create());
        BaoYangQingDanAdapter adapter = new BaoYangQingDanAdapter(context, t.shopcar);
        lv_baoyangdetail.setAdapter(adapter);
        Utils.setListViewHeightBasedOnChildren(lv_baoyangdetail);
    }


    private void setPageWidget(String state) {
        switch (state) {
            case "A":
                title.setText("待付款");
                if ("8".equals(type_pay)) {
                    btn_left.setVisibility(View.GONE);
                    btn_right.setVisibility(View.GONE);
                    btn_mid.setVisibility(View.VISIBLE);
                    btn_mid.setImageResource(R.drawable.shouhoufuwu);
                    btn_mid.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Utils.Call(context, Constants.PHONE_KAKU);
                        }
                    });
                } else {
                    btn_mid.setVisibility(View.GONE);
                    btn_left.setVisibility(View.VISIBLE);
                    btn_right.setVisibility(View.VISIBLE);
                    btn_left.setImageResource(R.drawable.quxiaodingdan);
                    btn_left.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Utils.Many()) {
                                return;
                            }
                            cancleOrder();
                        }
                    });
                    btn_right.setImageResource(R.drawable.lijifukuan);
                    btn_right.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Utils.Many()) {
                                return;
                            }
                            if ("U".equals(flag_state)) {
                                Intent intent = new Intent(context, OrderBaoYangPayActivity.class);
                                intent.putExtra("no_bill", no_bill);
                                intent.putExtra("price_bill", price_bill);
                                startActivity(intent);
                            } else {
                                startActivity(new Intent(context, YellowOilOrderActivity.class));
                            }
                        }
                    });
                }
                break;

            case "D":
                title.setText("待服务");
                btn_left.setVisibility(View.GONE);
                btn_right.setVisibility(View.VISIBLE);
                btn_right.setImageResource(R.drawable.querenfuwu);
                btn_right.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(BaoYangOrderDetailActivity.this);
                        builder.setTitle("提示");
                        builder.setMessage("是否确认服务？");
                        builder.setNegativeButton("是", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                QueRenFuWu2();
                            }
                        });

                        builder.setPositiveButton("否", new DialogInterface.OnClickListener() {

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
                title.setText("待评价");
                btn_left.setVisibility(View.VISIBLE);
                btn_right.setVisibility(View.VISIBLE);
                btn_mid.setVisibility(View.VISIBLE);
                btn_left.setImageResource(R.drawable.shanchudingdan);
                btn_mid.setImageResource(R.drawable.shouhoufuwu);
                btn_right.setImageResource(R.drawable.lijipingjia);
                btn_left.setOnClickListener(new OnClickListener() {
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
                                deleteOrder();
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
                btn_mid.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        Utils.Call(context, Constants.PHONE_KAKU);
                    }
                });
                btn_right.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        if ("U".equals(flag_state)) {
                            Intent intent = new Intent(context, BaoYangPingJiaActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("addr", addr);
                            bundle.putString("name_addr", name_addr);
                            bundle.putString("phone_addr", phone_addr);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            KaKuApplication.worker = worker;
                            startActivity(new Intent(context, OilPingJiaActivity.class));
                        }

                    }
                });
                break;
            case "F":
                title.setText("已完成");
                btn_left.setVisibility(View.GONE);
                btn_mid.setVisibility(View.GONE);
                btn_right.setVisibility(View.VISIBLE);
                btn_right.setImageResource(R.drawable.shanchudingdan);
                btn_right.setOnClickListener(new OnClickListener() {
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
                                deleteOrder();
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
                title.setText("已取消");
                btn_left.setVisibility(View.GONE);
                btn_mid.setVisibility(View.GONE);
                btn_right.setVisibility(View.VISIBLE);
                btn_right.setImageResource(R.drawable.shanchudingdan);
                btn_right.setOnClickListener(new OnClickListener() {
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
                                deleteOrder();
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
        }
    }

    private void deleteOrder() {
        Utils.NoNet(context);
        showProgressDialog();
        BaoYangOrderDeleteReq req = new BaoYangOrderDeleteReq();
        req.code = "40061";
        req.id_upkeep_bill = KaKuApplication.id_upkeep_bill;

        KaKuApiProvider.BaoYangOrderDelete(req, new KakuResponseListener<TruckOrderDeleteResp>(this, TruckOrderDeleteResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("deleteTruckOrder res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        stopProgressDialog();
                        gotoBaoYangOrderList();
                    }

                    LogUtil.showShortToast(context, t.msg);
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    /*待支付页面 取消订单*/
    private void cancleOrder() {
        Utils.NoNet(context);
        showProgressDialog();
        BaoYangOrderCancleReq req = new BaoYangOrderCancleReq();
        req.code = "40057";
        req.id_upkeep_bill = KaKuApplication.id_upkeep_bill;

        KaKuApiProvider.BaoYangOrderCancle(req, new KakuResponseListener<TruckOrderCancleResp>(this, TruckOrderCancleResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    if (Constants.RES.equals(t.res)) {
                        gotoBaoYangOrderList();
                        LogUtil.showShortToast(context, t.msg);
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
                        Intent intent = new Intent(context, BaoYangOrderListActivity.class);
                        KaKuApplication.state_order = "E";
                        startActivity(intent);
                        finish();
                        LogUtil.showShortToast(context, t.msg);
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

    private void gotoBaoYangOrderList() {
        Intent intent = new Intent(context, BaoYangOrderListActivity.class);
        KaKuApplication.state_order = "";
        startActivity(intent);
        finish();
    }

}

