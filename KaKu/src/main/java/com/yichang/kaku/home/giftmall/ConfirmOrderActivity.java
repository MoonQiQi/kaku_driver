package com.yichang.kaku.home.giftmall;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.OrderPayActivity;
import com.yichang.kaku.member.address.AddrActivity;
import com.yichang.kaku.member.cash.SetWithDrawCodeActivity;
import com.yichang.kaku.obj.ConfirmOrderProductObj;
import com.yichang.kaku.payhelper.alipay.AlipayCallBackActivity;
import com.yichang.kaku.request.ConfirmOrderReq;
import com.yichang.kaku.request.GenerateOrderReq;
import com.yichang.kaku.response.ConfirmOrderResp;
import com.yichang.kaku.response.GenerateOrderResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.InputPwdPopWindow;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.text.DecimalFormat;
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
    private LinearLayout ll_address;


    //商品总额 //减积分//减余额//实付款
    private TextView tv_confirmorder_totalprice, tv_point_pricepoint, tv_balance_pricepoint, tv_confirmorder_pricebill;
    //使用积分、积分总额、积分抵扣金额
    private CheckBox cbx_point_toggle;
    private TextView tv_point_total, tv_point_available;
    //使用余额、余额总额、余额抵扣金额
    private CheckBox cbx_balance_toggle;
    private TextView tv_balance_total, tv_balance_available;
    //抵扣金额栏
    /*private RelativeLayout rela_point_pricepoint;
    private RelativeLayout rela_balance_pricepoint;*/

    //实付款  //付款按钮
    private TextView tv_bottombar_pricebill, tv_bottombar_pay;


    private List<ConfirmOrderProductObj> list = new ArrayList<>();

    //private String priceBill;

    /*收货地址字段*/
    private String addr, phone_addr, name_addr;
    //默认支付类型为微信支付
    private String type_pay = "0";

    /*//抵扣分值 //积分抵扣金额//总价//实付款*/
    //private String point_bill, price_point, price_goods, price_bill;


    private String var_invoice = "";
    private String type_invoice = "2";


    //判断是否在修改地址
    private boolean isSelectAddr = false;
    //是否勾选了积分支付
    private boolean isPointChecked = false;
    //是否勾选了余额支付
    private boolean isBalanceChecked = false;
    //是否设置过支付密码
    private boolean hasSetPwd = true;
    //积分支付金额
    private String mPointLimit;
    //余额支付金额
    private String mMoneyBalance;
    //积分抵扣金额，余额抵扣金额
    private String mPointDeduction, mBalanceDeduction;
    //商品总价，实付款
    private String mPriceGoods, mRealPayPrice;


    //积分金额，余额金额
    private Float fPointLimit, fBalanceLimit;
    //积分抵扣，余额抵扣
    private Float fPointDeduction, fBalanceDeduction;
    //商品总价，实付款
    private Float fPriceGoods, fRealPayPrice;
    private Boolean isPwdPopWindowShow = false;
    private boolean isCancleSetPwd;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);
        init();
    }

    private void init() {
        lv_confirmorder_list = (ListView) findViewById(R.id.lv_confirmorder_list);
        //初始化顶部titleBar
        initTitleBar();
        //初始化地址信息
        initAddress();
        //初始化积分信息
        initPointPrice();
        //初始化余额信息
        initBalancePrice();
        //初始化底部支付条
        initBottomBar();

        //获取订单数据
        getOrderInfo();
    }


    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("确认订单");
    }

    private void getOrderInfo() {
        //list = new ArrayList<>();
        String id_goods_shopcars = getIntent().getStringExtra("id_goods_shopcars");

        Utils.NoNet(context);
        showProgressDialog();

        ConfirmOrderReq req = new ConfirmOrderReq();
        //todo 测试用id_driver字段
        req.code = "3007";
//        req.id_driver = "1";
        req.id_driver = Utils.getIdDriver();
        req.id_goods_shopcars = id_goods_shopcars;

        KaKuApiProvider.getConfirmOrderInfo(req, new BaseCallback<ConfirmOrderResp>(ConfirmOrderResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, ConfirmOrderResp t) {
                if (t != null) {

//                    LogUtil.E("getConfirmOrderInfo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
//设置确认订单页面数据
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
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });


    }

    private void setData(ConfirmOrderResp t) {
        if (t.shopcar != null) {
            list.addAll(t.shopcar);
        }

        ConfirmOrderAdapter adapter = new ConfirmOrderAdapter(context, list);
        lv_confirmorder_list.setAdapter(adapter);
        setListViewHeightBasedOnChildren(lv_confirmorder_list);
        //设置地址数据
        phone_addr = t.addr.getPhone_addr();
        name_addr = t.addr.getName_addr();
        addr = t.addr.getAddr();
        setAddress();
        //设置积分金额，余额
        mPointLimit = t.point_limit;
        mMoneyBalance = t.money_balance;

        //todo 是否设置过支付密码 Y需要设置N不需要设置
        hasSetPwd = "N".equals(t.flag_pay) ? true : false;

        mPriceGoods = formatFloatPrice(t.price_goods);
        mRealPayPrice = t.price_bill;

        fPointLimit = Float.parseFloat(mPointLimit) / 100;
        fBalanceLimit = Float.parseFloat(mMoneyBalance);

        fPointDeduction = fPointLimit;
        //fBalanceDeduction的金额不确定，根据不同情况进行判断,初始化限额为余额总额
        fBalanceDeduction = 0f;

//暂时没有打折活动，mRealPayPrice为空
        fRealPayPrice = Float.parseFloat(mRealPayPrice);
        fPriceGoods = Float.parseFloat(mPriceGoods);
//设置积分金额
        setPointPrice();


    }

    private String formatFloatPrice(String strPrice) {
        float price = Float.parseFloat(strPrice);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(price);
    }

    private void setPointPrice() {
//        可用积分
        //priceBill = mRealPayPrice;

        tv_point_total.setText("可用" + mPointLimit + "积分，抵用");
        tv_point_available.setText("￥" + getDeduction(mPointLimit));
        //et_point_use.setText(price_point);
        //抵扣金额

        tv_balance_available.setText("￥" + String.valueOf(getFomatFloatString(fBalanceLimit)));

        tv_confirmorder_pricebill.setText("￥" + String.valueOf(getFomatFloatString(fRealPayPrice)));

//商品总额

//        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
//        String p = decimalFormat.format(Float.parseFloat(price_goods));//format 返回的是字符串
        tv_confirmorder_totalprice.setText("￥" + String.valueOf(getFomatFloatString(fPriceGoods)));

        tv_bottombar_pricebill.setText("实付款：￥" + String.valueOf(getFomatFloatString(fRealPayPrice)));
        //changePayPrice("0");

    }


    /*获取抵扣金额*/
    private String getDeduction(String point_limit) {
        float price = Float.parseFloat(point_limit) / 100;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = "";
       /* if (price < 1) {

            p = "0" + decimalFormat.format(price);//format 返回的是字符串
        } else {
            p = decimalFormat.format(price);//format 返回的是字符串
        }*/
        p = decimalFormat.format(price);//format 返回的是字符串
        return p;
    }

    private void setAddress() {

        if (TextUtils.isEmpty(name_addr)) {
            ll_address.setVisibility(View.INVISIBLE);
            tv_notify.setVisibility(View.VISIBLE);
        } else {
            ll_address.setVisibility(View.VISIBLE);
            tv_notify.setVisibility(View.INVISIBLE);
            tv_address_name.setText(name_addr);
            tv_address_phone.setText(phone_addr);
            tv_address_address.setText(addr);
        }
    }

    private void initAddress() {
        rela_address_change = (RelativeLayout) findViewById(R.id.rela_address_change);
        rela_address_change.setOnClickListener(this);
        tv_address_name = (TextView) findViewById(R.id.tv_address_name);
        tv_address_phone = (TextView) findViewById(R.id.tv_address_phone);
        tv_address_address = (TextView) findViewById(R.id.tv_address_address);

        tv_notify = (TextView) findViewById(R.id.tv_notify);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);
    }

    private void initBottomBar() {
        tv_bottombar_pricebill = (TextView) findViewById(R.id.tv_bottombar_pricebill);

        tv_bottombar_pay = (TextView) findViewById(R.id.tv_bottombar_pay);
        tv_bottombar_pay.setOnClickListener(this);
    }

    private void initPointPrice() {

        //cbx_point_toggle, tv_point_total, ;
        tv_point_available = (TextView) findViewById(R.id.tv_point_available);
        tv_point_total = (TextView) findViewById(R.id.tv_point_total);


        cbx_point_toggle = (CheckBox) findViewById(R.id.cbx_point_toggle);
        // cbx_point_toggle.setOnClickListener(this);
        cbx_point_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /*if (!hasSetPwd) {
                    //todo 弹出对话框
                    showSetPwdDialog();
                } else {*/

                isPointChecked = !isPointChecked;
                if (isPointChecked) {
                    mPointDeduction = mPointLimit;

                    tv_point_total.setTextColor(Color.parseColor("#000000"));
                    tv_point_available.setTextColor(Color.parseColor("#d10000"));
                    //先选择余额
                    if (isBalanceChecked) {
                        fRealPayPrice = fPriceGoods - fPointDeduction;
                        if (fBalanceDeduction >= fRealPayPrice) {

                            fBalanceDeduction = fRealPayPrice;
                            fRealPayPrice = 0f;
                        } else {
                            fRealPayPrice = fRealPayPrice - fBalanceDeduction;
                        }
                        tv_balance_pricepoint.setText("-￥" + getFomatFloatString(fBalanceDeduction));
                        //fRealPayPrice=fRealPayPrice-fPointDeduction;

                    } else {
                        fRealPayPrice = fPriceGoods - fPointDeduction;
                    }

                        /*//tv_point_pricepoint.setText("-￥"+);
                        changePointPayPrice(mPointLimit, false);*/
                    tv_point_pricepoint.setText("-￥" + getFomatFloatString(fPointDeduction));
                    tv_confirmorder_pricebill.setText("￥" + getFomatFloatString(fRealPayPrice));

                    tv_bottombar_pricebill.setText("实付款：￥" + getFomatFloatString(fRealPayPrice));

                } else {
                    tv_point_total.setTextColor(Color.parseColor("#999999"));
                    tv_point_available.setTextColor(Color.parseColor("#999999"));


//实付款=实付款+积分抵扣金额
                    fRealPayPrice = fRealPayPrice + fPointDeduction;
                    if (isBalanceChecked) {
                        //如果余额支付勾选中，则判断余额金额
                        //如果余额金额大于实付款+余额抵扣金额，则余额抵扣金额=余额抵扣金额+实付款

                        if (fBalanceLimit >= fBalanceDeduction + fRealPayPrice) {
                            fBalanceDeduction = fPriceGoods;
                            fRealPayPrice = 0f;
                            tv_balance_pricepoint.setText("-￥" + getFomatFloatString(fBalanceDeduction));
                        } else {
                            //如果余额金额小于实付款+余额抵扣金额，则余额抵扣金额=余额金额，剩余为实付款
                            fBalanceDeduction = fBalanceLimit;
                            fRealPayPrice = fPriceGoods - fBalanceLimit;
                            tv_balance_pricepoint.setText("-￥" + getFomatFloatString(fBalanceDeduction));
                            //fRealPayPrice=fRealPayPrice-fBalanceDeduction;
                        }
                    }


                    tv_point_pricepoint.setText("-￥0.00");
                    tv_confirmorder_pricebill.setText("￥" + getFomatFloatString(fRealPayPrice));

                    tv_bottombar_pricebill.setText("实付款：￥" + getFomatFloatString(fRealPayPrice));

                }
            }


        });


        tv_confirmorder_totalprice = (TextView) findViewById(R.id.tv_confirmorder_totalprice);
        tv_point_pricepoint = (TextView) findViewById(R.id.tv_point_pricepoint);
        tv_confirmorder_pricebill = (TextView) findViewById(R.id.tv_confirmorder_pricebill);


    }

    private void initBalancePrice() {
        tv_balance_available = (TextView) findViewById(R.id.tv_balance_available);

        tv_balance_total = (TextView) findViewById(R.id.tv_balance_total);


        cbx_balance_toggle = (CheckBox) findViewById(R.id.cbx_balance_toggle);

        cbx_balance_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!hasSetPwd) {
                    //todo 弹出对话框
                    showSetPwdDialog();

                } else {
                    isBalanceChecked = !isBalanceChecked;
                    if (isBalanceChecked) {

                        if (fBalanceLimit >= fRealPayPrice) {
                            fBalanceDeduction = fRealPayPrice;
                        } else {
                            fBalanceDeduction = fBalanceLimit;
                        }

                        tv_balance_total.setTextColor(Color.parseColor("#000000"));
                        tv_balance_available.setTextColor(Color.parseColor("#000000"));
                        //逻辑  如果勾选了积分抵扣，则
                        if (isPointChecked) {
                            fRealPayPrice = fRealPayPrice - fBalanceDeduction;
                        } else {
                            fRealPayPrice = fPriceGoods - fBalanceDeduction;
                        }


                        tv_balance_pricepoint.setText("-￥" + getFomatFloatString(fBalanceDeduction));
                        tv_confirmorder_pricebill.setText("￥" + getFomatFloatString(fRealPayPrice));

                        tv_bottombar_pricebill.setText("实付款：￥" + getFomatFloatString(fRealPayPrice));

                        //changeBalancePayPrice(mMoneyBalance, false);
                    } else {
                        tv_balance_total.setTextColor(Color.parseColor("#999999"));
                        tv_balance_available.setTextColor(Color.parseColor("#999999"));

                        fRealPayPrice = fRealPayPrice + fBalanceDeduction;

                        tv_balance_pricepoint.setText("-￥0.00");

                        tv_confirmorder_pricebill.setText("￥" + getFomatFloatString(fRealPayPrice));

                        tv_bottombar_pricebill.setText("实付款：￥" + getFomatFloatString(fRealPayPrice));

                    }
                }
            }
        });
        tv_balance_pricepoint = (TextView) findViewById(R.id.tv_balance_pricepoint);

    }

    private String getFomatFloatString(Float fMoney) {

        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.

        return decimalFormat.format(fMoney);//format 返回的是字符串

    }

    private void showSetPwdDialog() {


        if (!isCancleSetPwd) {
            //弹出密码设置对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmOrderActivity.this);
            builder.setCustomTitle(null);
            builder.setMessage("为了您的资金安全，请先设置支付密码");
            builder.setNegativeButton("去设置", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(ConfirmOrderActivity.this, SetWithDrawCodeActivity.class);

                    intent.putExtra("isPassExist", false);
                    intent.putExtra("flag_next_activity", "NONE");

                    startActivity(intent);
                }
            });

            builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    isCancleSetPwd = true;
                    cbx_balance_toggle.setChecked(false);
                    isCancleSetPwd = false;
                    dialog.dismiss();
                }
            });
            if (!isFinishing()) {

                builder.create().show();
            }
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
            finish();
            Intent intent = new Intent(getApplicationContext(), ShopCartActivity.class);
            startActivity(intent);
        } else if (R.id.tv_bottombar_pay == id) {
//            与服务器交互生成订单
            MobclickAgent.onEvent(context, "CommitCarOrder");

            if (isBalanceChecked) {
                showPwdInputWindow();

            } else {
                /*if(fRealPayPrice==0f){
                    //todo 转到订单详情页面

                }else if(fRealPayPrice>0f){
                    //todo 转到收银台

                }*/
                generateOrderId();

            }


            //generateOrderId();

        } else if (R.id.rela_address_change == id) {
//            修改收货人地址信息
            isSelectAddr = true;
            Intent intent = new Intent(this, AddrActivity.class);
            intent.putExtra("flag", true);
            startActivityForResult(intent, 120);
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
                            /*if(fRealPayPrice>0.0f){
                                //实付款大于0，需要进入收银台
                                generateOrderId();

                            }else if(fRealPayPrice==0.0f){
                                generateOrderId();
                            }*/
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 121) {
            tv_address_name.setText(data.getStringExtra("name"));
            tv_address_phone.setText(data.getStringExtra("phone"));
            tv_address_address.setText(data.getStringExtra("addr"));
        }else if(resultCode == 125){
            //新建地址未保存时，不更改控件显示内容
        }
    }

    private void generateOrderId() {

        if (TextUtils.isEmpty(type_pay)) {
            LogUtil.showShortToast(context, "请选择支付方式");
            return;
        }
        if (TextUtils.isEmpty(tv_address_address.getText().toString().trim()) || TextUtils.isEmpty(tv_address_name.getText().toString().trim()) || TextUtils.isEmpty(tv_address_phone.getText().toString().trim())) {
            LogUtil.showShortToast(context, "请选择正确的收货地址");
            return;
        }
        /*if (isInvoiceChecked) {
            if (TextUtils.isEmpty(var_invoice)) {
                LogUtil.showShortToast(context, "请输入发票抬头");
                return;
            }
        }*/

        Utils.NoNet(context);
        showProgressDialog();


        GenerateOrderReq req = new GenerateOrderReq();
        req.code = "3008";
        req.id_driver = Utils.getIdDriver();
        req.flag_seckill = "N";
        req.id_goods = "";

//// TODO: 增加地址字段
        req.addr = tv_address_address.getText().toString().trim();
        req.name_addr = tv_address_name.getText().toString().trim();
        req.phone_addr = tv_address_phone.getText().toString().trim();

        req.email_invoice = "";
        req.phone_invoice = "";
//        发票明细 0：车辆配件，1：明细，2：生活用品
        req.type1_invoice = "";
//        发票类型 0：纸质，1：电子，2：不开发票
        req.type_invoice = type_invoice;
//        发票抬头
        req.var_invoice = var_invoice;
//支付方式 0：银行 1：微信，2：支付宝， // TODO: 2015/11/19 暂时传值0 占位无意义
        req.type_pay = "0";

//        抵扣分值
        if (isPointChecked) {
            req.point_bill = mPointLimit;
        } else {
            req.point_bill = "0";
        }

//        积分抵扣金额
        req.price_point = getFomatFloatString(fPointDeduction);
//        总价
        req.price_goods = getFomatFloatString(fPriceGoods);
//        实付款
        req.price_bill = getFomatFloatString(fRealPayPrice);

        req.money_balance = getFomatFloatString(fBalanceDeduction);
//为实付款赋值，在支付完成回调页上显示
        KaKuApplication.realPayment = getFomatFloatString(fRealPayPrice);

        LogUtil.E("chaih req" + req);

        KaKuApiProvider.generateOrderInfo(req, new BaseCallback<GenerateOrderResp>(GenerateOrderResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, GenerateOrderResp t) {
                //stopProgressDialog();
                if (t != null) {
                    LogUtil.E("generateOrderInfo res: " + t.toString());

                    if (Constants.RES.equals(t.res)) {
                        //todo 判断是否进入收银台

                        if (fRealPayPrice == 0f) {
                            /*Intent intent = new Intent(context, TruckOrderDetailActivity.class);
                            intent.putExtra("idbill", t.id_bill);
                            startActivity(intent);*/
                            //变更订单类型为truck
                            KaKuApplication.payType = "TRUCK";
                            Intent intent = new Intent(context, AlipayCallBackActivity.class);

                            startActivity(intent);

                        } else if (fRealPayPrice > 0f) {

                            Intent intent = new Intent(context, OrderPayActivity.class);
                            intent.putExtra("no_bill", t.no_bill);
                            intent.putExtra("price_bill", getFomatFloatString(fRealPayPrice));
                            startActivity(intent);
                        }

                        //intent.putExtra("price_bill", price_bill);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });


    }

    @Override
    protected void onStop() {
        if (isSelectAddr) {

            isSelectAddr = !isSelectAddr;
        } else {
            stopProgressDialog();
            /*todo */
            if (!isPwdPopWindowShow) {
                //如果支付密码验证框关闭则关闭当前页面，否则不finish
                finish();
            }
        }
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //判断新建地址之后，将新建地址赋值给地址栏
        if (KaKuApplication.flag_addr.equals("Y")) {
            ll_address.setVisibility(View.VISIBLE);
            tv_notify.setVisibility(View.INVISIBLE);

            if(!TextUtils.isEmpty(KaKuApplication.name_addr)){
                tv_address_name.setText(KaKuApplication.name_addr);
            }
            if(!TextUtils.isEmpty(KaKuApplication.phone_addr)){
                tv_address_phone.setText(KaKuApplication.phone_addr);
            }
            if(!TextUtils.isEmpty(KaKuApplication.dizhi_addr)){
                tv_address_address.setText(KaKuApplication.dizhi_addr);
            }


            KaKuApplication.flag_addr = "";
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
            LogUtil.E("listAdapter.getCount():" + listAdapter.getCount());
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);

            totalHeight += listItem.getMeasuredHeight();
            LogUtil.E("totalHeight" + i + ":" + totalHeight);
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //params.height += 5;
        LogUtil.E("params.height:" + params.height);
        listView.setLayoutParams(params);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            Intent intent = new Intent(getApplicationContext(), ShopCartActivity.class);
            startActivity(intent);
        }
        return false;
    }

}
