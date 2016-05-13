package com.yichang.kaku.home.giftmall;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.OrderPayActivity;
import com.yichang.kaku.member.YouHuiQuanActivity;
import com.yichang.kaku.member.address.AddrActivity;
import com.yichang.kaku.member.cash.SetWithDrawCodeActivity;
import com.yichang.kaku.obj.ConfirmOrderProductObj;
import com.yichang.kaku.payhelper.alipay.PaySuccessActivity;
import com.yichang.kaku.request.ConfirmOrderReq;
import com.yichang.kaku.request.GenerateOrderReq;
import com.yichang.kaku.response.ConfirmOrderResp;
import com.yichang.kaku.response.GenerateOrderResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.popwindow.InputPwdPopWindow;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class ConfirmOrderActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {


    //titleBar,返回，购物车，标题
    private TextView left, right, title;

    private ListView lv_confirmorder_list;//购物车商品列表

    private RelativeLayout rela_address_change;// 更改收货地址
    //    收货人姓名 手机号 收货地址
    private TextView tv_address_name, tv_address_phone, tv_address_address;
    private TextView tv_notify;
    private LinearLayout ll_address, line_order_jifen, line_order_yue;


    //商品总额 //减积分//减余额//实付款
    private TextView tv_confirmorder_totalprice, tv_point_pricepoint, tv_balance_pricepoint, tv_confirmorder_pricebill;
    //使用积分、积分总额、积分抵扣金额
    private CheckBox cbx_point_toggle, cb_point_xianchanggoumai;
    private TextView tv_point_total, tv_point_available;
    //使用余额、余额总额、余额抵扣金额
    private CheckBox cbx_balance_toggle, cb_peisong;
    private TextView tv_balance_total, tv_balance_available;
    //实付款  //付款按钮
    private TextView tv_bottombar_pricebill, tv_bottombar_pay, tv_addr_area;

    private List<ConfirmOrderProductObj> list = new ArrayList<>();

    //判断是否在修改地址
    private Boolean isPwdPopWindowShow = false;
    private boolean isNow = false;
    private TextView tv_peisong, tv_order_youhuiyong;
    private String id_goods_shopcars;
    private TextView tv_point_priceyouhuiquan;
    private String balance_choose, point_choose;
    private String price_point, price_goods, price_bill, flag_buy, price_balance, price_transprot, price_coupon, point_used;
    private RelativeLayout rela_confirmorder_youhuiquan, rela_point_pricepoint, rela_balance_pricepoint;
    private String checkstand, remark_area;
    private View view_jifenshang, view_jifenpriceshang, view_yueshang, view_order_yuexia;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);
        KaKuApplication.id_driver_coupon = "0";
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderInfo();
    }

    private void init() {
        lv_confirmorder_list = (ListView) findViewById(R.id.lv_confirmorder_list);
        initTitleBar();
        initText();
        initPointPrice();
        initBalancePrice();
    }


    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("确认订单");
        KaKuApplication.IsOrderSetPass = false;
    }

    private void initText() {
        KaKuApplication.success_type = "chepin";
        view_jifenshang = findViewById(R.id.view_jifenshang);
        view_yueshang = findViewById(R.id.view_yueshang);
        view_order_yuexia = findViewById(R.id.view_order_yuexia);
        view_jifenpriceshang = findViewById(R.id.view_jifenpriceshang);
        tv_balance_pricepoint = (TextView) findViewById(R.id.tv_balance_pricepoint);
        rela_point_pricepoint = (RelativeLayout) findViewById(R.id.rela_point_pricepoint);
        rela_balance_pricepoint = (RelativeLayout) findViewById(R.id.rela_balance_pricepoint);
        rela_confirmorder_youhuiquan = (RelativeLayout) findViewById(R.id.rela_confirmorder_youhuiquan);
        rela_confirmorder_youhuiquan.setOnClickListener(this);
        rela_address_change = (RelativeLayout) findViewById(R.id.rela_address_change);
        rela_address_change.setOnClickListener(this);
        tv_address_name = (TextView) findViewById(R.id.tv_address_name);
        tv_address_phone = (TextView) findViewById(R.id.tv_address_phone);
        tv_address_address = (TextView) findViewById(R.id.tv_address_address);
        tv_addr_area = (TextView) findViewById(R.id.tv_addr_area);
        tv_peisong = (TextView) findViewById(R.id.tv_peisong);
        tv_notify = (TextView) findViewById(R.id.tv_notify);
        tv_point_priceyouhuiquan = (TextView) findViewById(R.id.tv_point_priceyouhuiquan);
        tv_bottombar_pricebill = (TextView) findViewById(R.id.tv_bottombar_pricebill);
        tv_bottombar_pay = (TextView) findViewById(R.id.tv_bottombar_pay);
        tv_bottombar_pay.setOnClickListener(this);
        tv_order_youhuiyong = (TextView) findViewById(R.id.tv_order_youhuiyong);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);
        line_order_yue = (LinearLayout) findViewById(R.id.line_order_yue);
        line_order_jifen = (LinearLayout) findViewById(R.id.line_order_jifen);
        id_goods_shopcars = getIntent().getStringExtra("id_goods_shopcars");
        tv_point_available = (TextView) findViewById(R.id.tv_point_available);
        tv_point_total = (TextView) findViewById(R.id.tv_point_total);
        tv_balance_available = (TextView) findViewById(R.id.tv_balance_available);
        tv_balance_total = (TextView) findViewById(R.id.tv_balance_total);
        cb_peisong = (CheckBox) findViewById(R.id.cb_peisong);
        cb_peisong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tv_peisong.setText("配送  快递邮寄");
                    rela_address_change.setVisibility(View.VISIBLE);
                    flag_buy = "Y";
                } else {
                    tv_peisong.setText("自提");
                    rela_address_change.setVisibility(View.GONE);
                    flag_buy = "N";
                }
            }
        });
    }

    private void getOrderInfo() {
        Utils.NoNet(context);
        showProgressDialog();

        ConfirmOrderReq req = new ConfirmOrderReq();
        req.code = "3007";
        req.id_goods_shopcars = id_goods_shopcars;
        req.balance_choose = balance_choose;
        req.point_choose = point_choose;
        req.flag_buy = flag_buy;
        req.id_driver_coupon = KaKuApplication.id_driver_coupon;

        KaKuApiProvider.getConfirmOrderInfo(req, new KakuResponseListener<ConfirmOrderResp>(this, ConfirmOrderResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getConfirmOrderInfo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t);
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
            public void onFailed(int i, Response response) {

            }

        });
    }

    private void setData(ConfirmOrderResp t) {
        if (t.shopcar != null) {
            list.clear();
            list.addAll(t.shopcar);
        }

        ConfirmOrderAdapter adapter = new ConfirmOrderAdapter(context, list);
        lv_confirmorder_list.setAdapter(adapter);
        setListViewHeightBasedOnChildren(lv_confirmorder_list);
        KaKuApplication.AddrObj = t.addr;
        setAddress();
        remark_area = t.addr.getRemark_area();
        checkstand = t.flag_checkstand;
        price_point = t.price_point;
        price_goods = t.price_goods;
        price_bill = t.price_bill;
        price_balance = t.price_balance;
        price_transprot = t.price_transport;
        point_used = t.point_used;
        price_coupon = t.price_coupon;
        if ("N".equals(t.flag_pay)) {
            KaKuApplication.flag_balance = true;
        } else {
            KaKuApplication.flag_balance = false;
        }
        if ("0".equals(t.price_coupon) || "0.00".equals(t.price_coupon)) {
            tv_order_youhuiyong.setText("未使用");
        } else {
            tv_order_youhuiyong.setText(t.remark_coupon);
        }
        if ("0".equals(t.point_limit)) {
            view_jifenshang.setVisibility(View.GONE);
            line_order_jifen.setVisibility(View.GONE);
            view_jifenpriceshang.setVisibility(View.GONE);
            rela_point_pricepoint.setVisibility(View.GONE);
        } else {
            view_jifenshang.setVisibility(View.VISIBLE);
            line_order_jifen.setVisibility(View.VISIBLE);
            view_jifenpriceshang.setVisibility(View.VISIBLE);
            rela_point_pricepoint.setVisibility(View.VISIBLE);
        }
        if ("0".equals(t.money_balance) || "0.00".equals(t.money_balance)) {
            view_order_yuexia.setVisibility(View.GONE);
            view_yueshang.setVisibility(View.GONE);
            line_order_yue.setVisibility(View.GONE);
            rela_balance_pricepoint.setVisibility(View.GONE);
        } else {
            view_order_yuexia.setVisibility(View.VISIBLE);
            view_yueshang.setVisibility(View.VISIBLE);
            line_order_yue.setVisibility(View.VISIBLE);
            rela_balance_pricepoint.setVisibility(View.VISIBLE);
        }

        tv_balance_pricepoint.setText("- ￥" + t.price_balance);
        tv_point_total.setText("可用" + t.point_limit + "积分，抵用");
        tv_point_available.setText("￥" + t.price_point);
        tv_balance_available.setText("￥" + t.money_balance);
        tv_confirmorder_pricebill.setText("￥" + Utils.numdouble(t.price_bill));
        tv_confirmorder_totalprice.setText("￥" + t.price_goods);
        tv_point_pricepoint.setText("- ￥" + t.price_point);
        KaKuApplication.id_driver_coupon = t.id_driver_coupon;

        tv_bottombar_pricebill.setText("实付款：￥" + Utils.numdouble(t.price_bill));
        tv_point_priceyouhuiquan.setText("- ￥" + t.price_coupon);
        flag_buy = t.flag_buy;
        if ("Y".equals(t.flag_buy)) {
            rela_address_change.setVisibility(View.VISIBLE);
            cb_peisong.setChecked(true);
            tv_peisong.setText("配送 快递邮寄");
        } else {
            rela_address_change.setVisibility(View.GONE);
            cb_peisong.setChecked(false);
            tv_peisong.setText("自提");
        }
        if ("Y".equals(t.flag_buy_chose)) {
            cb_peisong.setVisibility(View.VISIBLE);
        } else {
            cb_peisong.setVisibility(View.GONE);
        }
        if ("Y".equals(t.balance_choose)) {
            cbx_balance_toggle.setChecked(true);
        } else {
            cbx_balance_toggle.setChecked(false);
        }
        if ("Y".equals(t.point_choose)) {
            cbx_point_toggle.setChecked(true);
        } else {
            cbx_point_toggle.setChecked(false);
        }
    }

    private void setAddress() {
        if (TextUtils.isEmpty(KaKuApplication.AddrObj.getName_addr())) {
            ll_address.setVisibility(View.GONE);
            tv_notify.setVisibility(View.VISIBLE);
        } else {
            ll_address.setVisibility(View.VISIBLE);
            tv_notify.setVisibility(View.GONE);
            tv_address_name.setText(KaKuApplication.AddrObj.getName_addr());
            tv_address_phone.setText(KaKuApplication.AddrObj.getPhone_addr());
            tv_address_address.setText(KaKuApplication.AddrObj.getAddr());
            tv_addr_area.setText(KaKuApplication.AddrObj.getRemark_area());
        }
    }

    private void initPointPrice() {
        cbx_point_toggle = (CheckBox) findViewById(R.id.cbx_point_toggle);
        cbx_point_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    point_choose = "Y";
                    getOrderInfo();
                    tv_point_total.setTextColor(Color.parseColor("#000000"));
                    tv_point_available.setTextColor(Color.parseColor("#d10000"));
                } else {
                    point_choose = "N";
                    getOrderInfo();
                    tv_point_total.setTextColor(Color.parseColor("#999999"));
                    tv_point_available.setTextColor(Color.parseColor("#999999"));
                }
            }

        });

        tv_confirmorder_totalprice = (TextView) findViewById(R.id.tv_confirmorder_totalprice);
        tv_point_pricepoint = (TextView) findViewById(R.id.tv_point_pricepoint);
        tv_confirmorder_pricebill = (TextView) findViewById(R.id.tv_confirmorder_pricebill);
    }

    private void initBalancePrice() {
        cbx_balance_toggle = (CheckBox) findViewById(R.id.cbx_balance_toggle);
        cbx_balance_toggle.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                            if (!KaKuApplication.flag_balance) {
                                //todo 弹出对话框
                                Intent intent = new Intent(ConfirmOrderActivity.this, SetWithDrawCodeActivity.class);
                                intent.putExtra("isPassExist", false);
                                intent.putExtra("flag_next_activity", "NONE");
                                startActivity(intent);
                                LogUtil.showShortToast(context, "为了您的资金安全，请先设置支付密码");
                            } else {
                                SetBalanceChecked(true);
                            }
                        } else {
                            SetBalanceChecked(false);
                        }
                    }
                }
        );
    }


    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();

        if (R.id.tv_left == id) {
            Intent intent = new Intent(getApplicationContext(), ShopCartActivity.class);
            startActivity(intent);
            finish();
        } else if (R.id.tv_bottombar_pay == id) {
            if ("N".equals(checkstand) && (Float.parseFloat(price_bill) > 0.00)) {
                LogUtil.showShortToast(context, "您的积分或余额不足");
                return;
            }
            MobclickAgent.onEvent(context, "CommitCarOrder");
            if ("Y".equals(balance_choose)) {
                showPwdInputWindow();
            } else {
                generateOrderId();
            }

        } else if (R.id.rela_address_change == id) {
            KaKuApplication.IsOrderToAddr = true;
            Intent intent = new Intent(this, AddrActivity.class);
            startActivity(intent);
        } else if (R.id.rela_confirmorder_youhuiquan == id) {
            KaKuApplication.id_goods = "";
            KaKuApplication.flag_coupon = "gift";
            Intent intent = new Intent(this, YouHuiQuanActivity.class);
            startActivity(intent);
        }
    }

    private void showPwdInputWindow() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                isPwdPopWindowShow = true;

                InputPwdPopWindow input =
                        new InputPwdPopWindow(ConfirmOrderActivity.this);
                input.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                input.setConfirmListener(new InputPwdPopWindow.ConfirmListener() {
                    @Override
                    public void confirmPwd(Boolean isConfirmed) {
                        if (isConfirmed) {
                            //如果使用过余额，则判断是否进入收银台
                            isPwdPopWindowShow = false;
                            generateOrderId();
                        }
                    }

                    @Override
                    public void showDialog() {
                        showProgressDialog();
                    }

                    @Override
                    public void stopDialog() {
                        stopProgressDialog();
                    }

                });

                input.show();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE |
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

            }
        }, 0);
    }

    private void generateOrderId() {

        if (!isNow) {
            if ("Y".equals(flag_buy)) {
                if (TextUtils.isEmpty(tv_address_address.getText().toString().trim()) || TextUtils.isEmpty(tv_address_name.getText().toString().trim()) || TextUtils.isEmpty(tv_address_phone.getText().toString().trim())) {
                    LogUtil.showShortToast(context, "请选择正确的收货地址");
                    return;
                }
            }
        }

        Utils.NoNet(context);
        showProgressDialog();
        tv_bottombar_pay.setEnabled(false);
        GenerateOrderReq req = new GenerateOrderReq();
        req.code = "3008";
        req.addr = remark_area + tv_address_address.getText().toString().trim();
        req.name_addr = tv_address_name.getText().toString().trim();
        req.phone_addr = tv_address_phone.getText().toString().trim();
        req.price_point = price_point;
        req.price_goods = price_goods;
        req.price_bill = price_bill;
        req.flag_buy = flag_buy;
        req.point_used = point_used;
        req.id_driver_coupon = KaKuApplication.id_driver_coupon;
        req.price_balance = Utils.numdouble(price_balance);
        req.price_transport = price_transprot;
        req.price_coupon = price_coupon;
        req.sign = Utils.getHmacMd5Str(Utils.numdouble(price_balance));

        KaKuApiProvider.generateOrderInfo(req, new KakuResponseListener<GenerateOrderResp>(this, GenerateOrderResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("CommitOrderInfo res: " + t.res);
                    tv_bottombar_pay.setEnabled(true);
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.realPayment = price_bill;
                        if ("0.00".equals(price_bill) || "0".equals(price_bill)) {
                            KaKuApplication.payType = "TRUCK";
                            Intent intent = new Intent(context, PaySuccessActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(context, OrderPayActivity.class);
                            intent.putExtra("no_bill", t.no_bill);
                            intent.putExtra("price_bill", price_bill);
                            startActivity(intent);
                        }
                    }
                    LogUtil.showShortToast(context, t.msg);
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    public void SetBalanceChecked(boolean flag) {
        if (flag) {
            balance_choose = "Y";
            tv_balance_total.setTextColor(Color.parseColor("#000000"));
            tv_balance_available.setTextColor(Color.parseColor("#000000"));
            getOrderInfo();
        } else {
            tv_balance_total.setTextColor(Color.parseColor("#999999"));
            tv_balance_available.setTextColor(Color.parseColor("#999999"));
            balance_choose = "N";
            getOrderInfo();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
        //params.height += 5;
        listView.setLayoutParams(params);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(getApplicationContext(), ShopCartActivity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }

}
