package com.yichang.kaku.home.giftmall;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.OrderPayActivity;
import com.yichang.kaku.member.cash.SetWithDrawCodeActivity;
import com.yichang.kaku.payhelper.alipay.AlipayCallBackActivity;
import com.yichang.kaku.request.XianChangBuyReq;
import com.yichang.kaku.request.XianChangCommitReq;
import com.yichang.kaku.response.GenerateOrderResp;
import com.yichang.kaku.response.XianChangBuyResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.KakuTextView;
import com.yichang.kaku.view.popwindow.InputPwdPopWindow;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.text.DecimalFormat;

public class ConfirmOrderXianActivity extends BaseActivity implements OnClickListener {


    //titleBar,返回，购物车，标题
    private TextView left, right, title;

    private TextView tv_notify;

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
    private String id_goods;
    private ImageView iv_confirmorder_product;
    private KakuTextView tv_shopname;
    private TextView tv_price;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorderxian);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        //lv_confirmorder_list = (ListView) findViewById(R.id.lv_confirmorder_list);
        //初始化顶部titleBar
        initTitleBar();
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
        id_goods = getIntent().getStringExtra("id_goods");
        iv_confirmorder_product = (ImageView) findViewById(R.id.iv_confirmorder_product);
        tv_shopname = (KakuTextView) findViewById(R.id.tv_shopname);
        tv_price = (TextView) findViewById(R.id.tv_price);
    }

    private void getOrderInfo() {

        Utils.NoNet(context);
        showProgressDialog();

        XianChangBuyReq req = new XianChangBuyReq();
        //todo 测试用id_driver字段
        req.code = "30025";
        req.id_driver = Utils.getIdDriver();
        req.id_goods = id_goods;

        KaKuApiProvider.getXianChangBuyOrder(req, new KakuResponseListener<XianChangBuyResp>(this, XianChangBuyResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getConfirmOrderInfo res: " + t.res);
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

        });

    }

    private void setData(XianChangBuyResp t) {

        //设置积分金额，余额
        mPointLimit = t.point_limit;
        mMoneyBalance = t.money_balance;

        //todo 是否设置过支付密码 Y需要设置N不需要设置
        hasSetPwd = "N".equals(t.flag_pay) ? true : false;

        mPriceGoods = formatFloatPrice(t.price_goods);
        mRealPayPrice = t.price_bill;

        fPointLimit = Float.parseFloat(mPointLimit) / 100;
        fBalanceLimit = Float.parseFloat(mMoneyBalance);

        BitmapUtil.getInstance(context).download(iv_confirmorder_product, KaKuApplication.qian_zhui + t.goods.getImage_goods());
        tv_shopname.setText(t.goods.getName_goods());
        tv_price.setText(t.goods.getPrice_goods());
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

    private void initBottomBar() {
        tv_bottombar_pricebill = (TextView) findViewById(R.id.tv_bottombar_pricebill);

        tv_bottombar_pay = (TextView) findViewById(R.id.tv_bottombar_pay);
        tv_bottombar_pay.setOnClickListener(this);
        tv_bottombar_pay.setEnabled(true);
    }

    private void initPointPrice() {

        tv_point_available = (TextView) findViewById(R.id.tv_point_available);
        tv_point_total = (TextView) findViewById(R.id.tv_point_total);

        cbx_point_toggle = (CheckBox) findViewById(R.id.cbx_point_toggle);
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
                        fPointDeduction = fPointLimit;
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
                        fPointDeduction = fPointLimit;
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

                    fPointDeduction = 0f;
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

                        fBalanceDeduction = 0f;

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
            AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmOrderXianActivity.this);
            builder.setCustomTitle(null);
            builder.setMessage("为了您的资金安全，请先设置支付密码");
            builder.setNegativeButton("去设置", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(ConfirmOrderXianActivity.this, SetWithDrawCodeActivity.class);

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
        }
    }

    private void showPwdInputWindow() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                isPwdPopWindowShow = true;

                InputPwdPopWindow input =
                        new InputPwdPopWindow(ConfirmOrderXianActivity.this);
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

        Utils.NoNet(context);
        showProgressDialog();
        XianChangCommitReq req = new XianChangCommitReq();
        req.code = "30026";
        req.id_driver = Utils.getIdDriver();
        req.id_goods = id_goods;

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
        req.num_shopcar = "1";
//为实付款赋值，在支付完成回调页上显示
        KaKuApplication.realPayment = getFomatFloatString(fRealPayPrice);

        KaKuApiProvider.commitXianChangBuyOrder(req, new KakuResponseListener<GenerateOrderResp>(this, GenerateOrderResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("generateOrderInfo res: " + t.toString());
                    tv_bottombar_pay.setEnabled(true);
                    if (Constants.RES.equals(t.res)) {
                        //todo 判断是否进入收银台

                        if (fRealPayPrice == 0f) {
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
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
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

}
