package com.yichang.kaku.home.baoyang;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.member.YouHuiQuanActivity;
import com.yichang.kaku.member.cash.SetWithDrawCodeActivity;
import com.yichang.kaku.payhelper.alipay.PaySuccessActivity;
import com.yichang.kaku.request.GenerateServiceOrderReq;
import com.yichang.kaku.request.GetBaoYangOrderReq;
import com.yichang.kaku.response.GenerateServiceOrderResp;
import com.yichang.kaku.response.GetBaoYangOrderResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.popwindow.InputPwdPopWindow;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.Calendar;

public class BaoYangOrderActivity extends BaseActivity implements OnClickListener {


    //titleBar,返回，购物车，标题
    private TextView left, right, title;

    private RelativeLayout rela_baoyangorder_time, rela_baoyangorder_qingdan, rela_baoyangorder_youhuiquan, rela_order_huodongprice;
    //实付款  //付款按钮
    private TextView tv_bottombar_pricebill, tv_bottombar_pay, tv_baoyangorder_addrname, tv_baoyangorder_addr, tv_youhuiquan_price, tv_baoyangorder_youhuiquan;
    private ImageView iv_img_fuwu0, iv_img_fuwu1, iv_img_fuwu2;
    //商品总价，优惠价格，使用余额，实付款
    private TextView tv_order_totalprice, tv_balance_price, tv_order_huodongprice;
    //余额使用开关
    private CheckBox cbx_balance_toggle;
    //可用余额
    private TextView tv_balance_available;
    //是否设置过支付密码
    //是否弹出密码输入框
    private Boolean isPwdPopWindowShow = false;
    //商品价格
    private String balance_choose = "N", price_bill, price_balance, price_goods, price_transport;
    private TextView tv_baoyangorder_jijianshangpin, tv_baoyangorder_time;
    private static final int SHOW_DATAPICK = 0;
    private static final int DATE_DIALOG_ID = 1;
    private int mYear;
    private int mMonth;
    private int mDay;
    /**
     * 处理日期和时间控件的Handler
     */
    Handler dateandtimeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BaoYangOrderActivity.SHOW_DATAPICK:
                    showDialog(DATE_DIALOG_ID);
                    break;
            }
        }

    };
    private long currentTime;
    private String id_items, num_items;
    private View view_huodongyouhui;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baoyang_order);
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    private void init() {

        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("确认订单");
        id_items = "";
        num_items = "";
        id_items = getIntent().getStringExtra("id_items");
        num_items = getIntent().getStringExtra("num_items");
        KaKuApplication.success_type = "baoyang";
        tv_baoyangorder_addrname = (TextView) findViewById(R.id.tv_baoyangorder_addrname);
        tv_baoyangorder_addr = (TextView) findViewById(R.id.tv_baoyangorder_addr);
        tv_baoyangorder_time = (TextView) findViewById(R.id.tv_baoyangorder_time);
        tv_bottombar_pricebill = (TextView) findViewById(R.id.tv_bottombar_pricebill);
        tv_baoyangorder_youhuiquan = (TextView) findViewById(R.id.tv_baoyangorder_youhuiquan);
        tv_order_huodongprice = (TextView) findViewById(R.id.tv_order_huodongprice);
        tv_bottombar_pay = (TextView) findViewById(R.id.tv_bottombar_pay);
        tv_bottombar_pay.setOnClickListener(this);
        tv_order_totalprice = (TextView) findViewById(R.id.tv_order_totalprice);
        tv_youhuiquan_price = (TextView) findViewById(R.id.tv_youhuiquan_price);
        tv_balance_price = (TextView) findViewById(R.id.tv_balance_price);
        tv_baoyangorder_jijianshangpin = (TextView) findViewById(R.id.tv_baoyangorder_jijianshangpin);
        iv_img_fuwu0 = (ImageView) findViewById(R.id.iv_img_fuwu0);
        iv_img_fuwu1 = (ImageView) findViewById(R.id.iv_img_fuwu1);
        iv_img_fuwu2 = (ImageView) findViewById(R.id.iv_img_fuwu2);
        view_huodongyouhui = findViewById(R.id.view_huodongyouhui);
        rela_baoyangorder_qingdan = (RelativeLayout) findViewById(R.id.rela_baoyangorder_qingdan);
        rela_baoyangorder_qingdan.setOnClickListener(this);
        rela_baoyangorder_time = (RelativeLayout) findViewById(R.id.rela_baoyangorder_time);
        rela_baoyangorder_time.setOnClickListener(this);
        rela_order_huodongprice = (RelativeLayout) findViewById(R.id.rela_order_huodongprice);
        rela_baoyangorder_youhuiquan = (RelativeLayout) findViewById(R.id.rela_baoyangorder_youhuiquan);
        rela_baoyangorder_youhuiquan.setOnClickListener(this);
        tv_balance_available = (TextView) findViewById(R.id.tv_balance_available);
        if ("Y".equals(KaKuApplication.flag_activity)) {
            view_huodongyouhui.setVisibility(View.VISIBLE);
            rela_order_huodongprice.setVisibility(View.VISIBLE);
        } else {
            view_huodongyouhui.setVisibility(View.GONE);
            rela_order_huodongprice.setVisibility(View.GONE);
        }
        cbx_balance_toggle = (CheckBox) findViewById(R.id.cbx_balance_toggle);
        cbx_balance_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    if (!KaKuApplication.flag_balance) {
                        //todo 弹出对话框
                        Intent intent = new Intent(BaoYangOrderActivity.this, SetWithDrawCodeActivity.class);
                        intent.putExtra("isPassExist", false);
                        intent.putExtra("flag_next_activity", "NONE");
                        startActivity(intent);
                        LogUtil.showShortToast(context, "为了您的资金安全，请先设置支付密码");
                    } else {
                        SetBalanceChecked(true);
                    }
                    KaKuApplication.id_upkeep_coupon = "";
                } else {
                    SetBalanceChecked(false);
                }
                GetBaoYangOrder();
            }
        });
        KaKuApplication.IsOrderSetPass = false;
        final Calendar c = Calendar.getInstance();
        currentTime = c.getTimeInMillis();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        setDateTime();
        GetBaoYangOrder();
    }

    private void GetBaoYangOrder() {

        showProgressDialog();
        GetBaoYangOrderReq req = new GetBaoYangOrderReq();
        req.code = "400115";
        req.id_shop = KaKuApplication.id_baoyangshop;
        if (cbx_balance_toggle.isChecked()) {
            balance_choose = "Y";
        } else {
            balance_choose = "N";
        }
        req.balance_choose = balance_choose;
        req.id_driver_coupon = KaKuApplication.id_upkeep_coupon;
        req.id_items = id_items;
        req.num_items = num_items;
        req.flag_activity = KaKuApplication.flag_activity;
        KaKuApiProvider.BaoYangOrder(req, new KakuResponseListener<GetBaoYangOrderResp>(context, GetBaoYangOrderResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("BaoYangOrder res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.AddrObj = t.addr;
                        if ("N".equals(t.flag_pay)) {
                            KaKuApplication.flag_balance = true;
                        } else {
                            KaKuApplication.flag_balance = false;
                        }
                        SetText(t);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        } else {
                            LogUtil.showShortToast(context, t.msg);
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

    private void SetText(GetBaoYangOrderResp t) {
        price_bill = t.price_bill;
        price_balance = t.price_balance;
        price_goods = t.price_goods;
        price_transport = t.price_transport;
        tv_baoyangorder_addrname.setText(t.addr.getName_addr());
        tv_baoyangorder_addr.setText(t.addr.getAddr());
        tv_balance_available.setText("¥" + t.money_balance);
        tv_baoyangorder_jijianshangpin.setText("共" + t.num_total + "件商品");
        if ("".equals(t.remark_coupon)) {
            tv_baoyangorder_youhuiquan.setText("未使用优惠券");
        } else {
            tv_baoyangorder_youhuiquan.setText(t.remark_coupon);
        }

        tv_order_totalprice.setText("¥" + Utils.numdouble(t.price_goods));
        tv_youhuiquan_price.setText("-¥" + Utils.numdouble(t.price_coupon));
        tv_balance_price.setText("-¥" + Utils.numdouble(t.price_balance));
        tv_bottombar_pricebill.setText("实付款:¥" + Utils.numdouble(t.price_bill));
        tv_order_huodongprice.setText("-¥" + Utils.numdouble(t.price_activity));
        if (t.items.size() == 2) {
            BitmapUtil.getInstance(context).download(iv_img_fuwu0, KaKuApplication.qian_zhui + t.items.get(0).getImage_item());
            BitmapUtil.getInstance(context).download(iv_img_fuwu1, KaKuApplication.qian_zhui + t.items.get(1).getImage_item());
        } else if (t.items.size() > 2) {
            BitmapUtil.getInstance(context).download(iv_img_fuwu0, KaKuApplication.qian_zhui + t.items.get(0).getImage_item());
            BitmapUtil.getInstance(context).download(iv_img_fuwu1, KaKuApplication.qian_zhui + t.items.get(1).getImage_item());
            BitmapUtil.getInstance(context).download(iv_img_fuwu2, KaKuApplication.qian_zhui + t.items.get(2).getImage_item());
        }

    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();

        if (R.id.tv_left == id) {
            KaKuApplication.id_upkeep_picture = "";
            finish();
        } else if (R.id.tv_bottombar_pay == id) {
            if (cbx_balance_toggle.isChecked() && (!"0.00".equals(Utils.numdouble(price_bill)))) {
                LogUtil.showShortToast(context, "余额不足，请选择其他支付方式");
                return;
            }
            if ("Y".equals(balance_choose)) {
                showPwdInputWindow();
            } else {
                generateOrderId();
            }
        } else if (R.id.rela_baoyangorder_time == id) {
            Message msg = new Message();
            msg.what = BaoYangOrderActivity.SHOW_DATAPICK;
            BaoYangOrderActivity.this.dateandtimeHandler.sendMessage(msg);
        } else if (R.id.rela_baoyangorder_qingdan == id) {
            startActivity(new Intent(this, BaoYangQingDanActivity.class));
        } else if (R.id.rela_baoyangorder_youhuiquan == id) {
            if (cbx_balance_toggle.isChecked()) {
                LogUtil.showShortToast(context, "余额支付暂不支持使用优惠券");
                return;
            }
            KaKuApplication.flag_coupon = "upkeep";
            KaKuApplication.price_service = price_bill;
            startActivity(new Intent(this, YouHuiQuanActivity.class));
        }
    }

    public void SetBalanceChecked(boolean flag) {
        if (flag) {
            balance_choose = "Y";
            tv_balance_price.setTextColor(Color.parseColor("#000000"));
            tv_balance_available.setTextColor(Color.parseColor("#000000"));
            GetBaoYangOrder();
        } else {
            tv_balance_price.setTextColor(Color.parseColor("#999999"));
            tv_balance_available.setTextColor(Color.parseColor("#999999"));
            balance_choose = "N";
            GetBaoYangOrder();
        }
    }

    private void showPwdInputWindow() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                isPwdPopWindowShow = true;

                InputPwdPopWindow input =
                        new InputPwdPopWindow(BaoYangOrderActivity.this);
                input.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                input.setConfirmListener(new InputPwdPopWindow.ConfirmListener() {
                    @Override
                    public void confirmPwd(Boolean isConfirmed) {
                        if (isConfirmed) {

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

            }
        }, 200);
    }

    private void generateOrderId() {

        showProgressDialog();
        GenerateServiceOrderReq req = new GenerateServiceOrderReq();
        req.code = "400116";
        req.time_order = tv_baoyangorder_time.getText().toString().trim();
        req.id_shop = KaKuApplication.id_baoyangshop;
        req.price_balance = price_balance;
        req.id_driver_coupon = KaKuApplication.id_upkeep_coupon;
        req.sign = Utils.getHmacMd5Str(price_balance);
        req.flag_activity = KaKuApplication.flag_activity;

        KaKuApiProvider.CommitBill(req, new KakuResponseListener<GenerateServiceOrderResp>(this, GenerateServiceOrderResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    if (Constants.RES.equals(t.res)) {
                        LogUtil.E("generateServiceOrder res: " + t.res);
                        KaKuApplication.payType = "SERVICE";
                        //todo 进入订单列表页
                        KaKuApplication.realPayment = price_bill;
                        if ("0.00".equals(price_bill) || "0".equals(price_bill)) {
                            Intent intent = new Intent(context, PaySuccessActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        } else {
                            Intent intent = new Intent(context, OrderBaoYangPayActivity.class);
                            intent.putExtra("no_bill", t.no_bill);
                            intent.putExtra("price_bill", price_bill);
                            startActivity(intent);
                            finish();
                        }
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

    /**
     * 设置日期
     */
    private void setDateTime() {
        final Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        updateDateDisplay();
    }

    /**
     * 更新日期显示
     */
    private void updateDateDisplay() {
        tv_baoyangorder_time.setText(new StringBuilder().append(mYear).append("-")
                .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
                .append((mDay < 10) ? "0" + mDay : mDay));
    }

    /**
     * 日期控件的事件
     */
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            updateDateDisplay();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                DatePickerDialog dp = new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
                dp.getDatePicker().setMinDate(currentTime);
                return dp;

        }

        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            KaKuApplication.id_upkeep_picture = "";
            finish();
        }
        return false;
    }

}