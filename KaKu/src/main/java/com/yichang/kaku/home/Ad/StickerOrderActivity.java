package com.yichang.kaku.home.Ad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.OrderCheTiePayActivity;
import com.yichang.kaku.member.address.AddrActivity;
import com.yichang.kaku.member.cash.SetWithDrawCodeActivity;
import com.yichang.kaku.obj.Addr2Obj;
import com.yichang.kaku.payhelper.alipay.AlipayCallBackActivity;
import com.yichang.kaku.request.GenerateStickerOrderReq;
import com.yichang.kaku.response.GenerateStickerOrderResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.InputPwdPopWindow;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.text.DecimalFormat;

public class StickerOrderActivity extends BaseActivity implements OnClickListener {


    //titleBar,返回，购物车，标题
    private TextView left, right, title;

    private RelativeLayout rela_address_change;// 更改收货地址
    //    收货人姓名 手机号 收货地址
    private TextView tv_address_name, tv_address_phone, tv_address_address;
    private TextView tv_notify;
    private LinearLayout ll_address;

    //实付款  //付款按钮
    private TextView tv_bottombar_pricebill, tv_bottombar_pay;

    /*收货地址字段*/
    private String addr, phone_addr, name_addr;
    //判断是否在修改地址
    private boolean isSelectAddr = false;


    //商品总价，优惠价格，使用余额，实付款
    private TextView tv_order_totalprice, tv_preferential_price, tv_balance_price, tv_order_realpay;
    //余额使用开关
    private CheckBox cbx_balance_toggle;
    //可用余额
    private TextView tv_balance_available;

    //商品详情条目

    //产品图片
    private ImageView iv_sticker_product;
    //产品标题、价格
    private TextView tv_sticker_title;
    private TextView tv_sticker_price;
    private TextView tv_sticker_num;
    //购买数量
    private LinearLayout ll_product_num;

    private ImageView iv_sticker_jianhao, iv_sticker_jiahao;
    private TextView tv_sticker_add_num;
    private Integer productNum = 1;
    //private String priceBill;


    //是否勾选了余额支付
    private boolean isBalanceChecked = false;
    //是否设置过支付密码
    private boolean hasSetPwd = true;
    //是否可以购买多个
    private Boolean isMulti = false;


    //是否弹出密码输入框
    private Boolean isPwdPopWindowShow = false;
    //商品价格
    private Float fPriceProduct = 28f;
    private Float fPriceDeduction = 27f;
    private Float fMoneyBalance = 3f;
    private Float fBalanceDeduction;
    private Float fPriceTotal = 0f;
    private Float fPriceRealPay = 0f;

    private Bundle bundle;

    private Addr2Obj mAddr;
    private String mName_advert;
    private String mImage_advert;
    private String mId_advert;
    private boolean isCancleSetPwd;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_order);
        init();
    }

    private void init() {

        //初始化顶部titleBar
        initTitleBar();
        //初始化地址信息
        initAddress();

        //初始化余额信息
        initBalancePrice();
        //初始化底部支付条
        initBottomBar();

        initProductInfo();

        tv_order_totalprice = (TextView) findViewById(R.id.tv_order_totalprice);
        tv_preferential_price = (TextView) findViewById(R.id.tv_preferential_price);
        tv_balance_price = (TextView) findViewById(R.id.tv_balance_price);
        tv_order_realpay = (TextView) findViewById(R.id.tv_order_realpay);


        //获取订单数据
        getOrderInfo();
    }

    private void initProductInfo() {

        iv_sticker_product = (ImageView) findViewById(R.id.iv_sticker_product);
        tv_sticker_title = (TextView) findViewById(R.id.tv_sticker_title);
        tv_sticker_price = (TextView) findViewById(R.id.tv_sticker_price);
        tv_sticker_num = (TextView) findViewById(R.id.tv_sticker_num);
        tv_sticker_num.setText("X" + String.valueOf(productNum));
        //购买数量
        tv_sticker_add_num = (TextView) findViewById(R.id.tv_sticker_add_num);
        tv_sticker_add_num.setText(String.valueOf(productNum));
        iv_sticker_jianhao = (ImageView) findViewById(R.id.iv_sticker_jianhao);
        iv_sticker_jianhao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMulti) {
                    if (productNum > 1) {
                        productNum--;
                        tv_sticker_add_num.setText(String.valueOf(productNum));
                        tv_sticker_num.setText(String.valueOf(productNum));
                        changePriceView();
                    }
                } else {
                    LogUtil.showShortToast(context, "您只能购买一组车贴~");
                }

            }
        });
        iv_sticker_jiahao = (ImageView) findViewById(R.id.iv_sticker_jiahao);

        iv_sticker_jiahao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isMulti) {
                    productNum++;
                    tv_sticker_add_num.setText(String.valueOf(productNum));
                    tv_sticker_num.setText("X" + String.valueOf(productNum));
                    changePriceView();
                } else {
                    LogUtil.showShortToast(context, "您只能购买一组车贴~");
                }

            }
        });

    }

    private void changePriceView() {
        fPriceTotal = fPriceProduct * productNum;
        tv_order_totalprice.setText("￥" + getFomatFloatString(fPriceTotal));

        tv_preferential_price.setText("-￥" + getFomatFloatString(fPriceDeduction));


        tv_balance_price.setText("-￥" + getFomatFloatString(getfBalanceDeduction()));

        fPriceRealPay = fPriceTotal - fPriceDeduction - fBalanceDeduction;

        tv_bottombar_pricebill.setText("实付款:￥" + getFomatFloatString(fPriceRealPay));
        tv_order_realpay.setText("￥" + getFomatFloatString(fPriceRealPay));

    }

    private Float getfBalanceDeduction() {

        if (isBalanceChecked) {

            if (fMoneyBalance + fPriceDeduction - fPriceTotal >= 0) {
                fBalanceDeduction = fPriceTotal - fPriceDeduction;
            } else {
                fBalanceDeduction = fMoneyBalance;
            }
        } else {
            fBalanceDeduction = 0f;
        }

        return fBalanceDeduction;
    }


    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("确认订单");
    }

    private void getOrderInfo() {

        /*bundle = getIntent().getExtras();
        fMoneyBalance = Float.parseFloat(bundle.getString("money_balance"));
        isMulti = "N".equals(bundle.getString("flag_one")) ? true : false;
        hasSetPwd = "N".equals(bundle.getString("flag_pay")) ? true : false;
        mAddr = (Addr2Obj) bundle.get("addr");

        fPriceProduct = Float.parseFloat(bundle.getString("price_advert"));
        fPriceDeduction = Float.parseFloat(bundle.getString("breaks_money"));

        mName_advert = bundle.getString("name_advert");
        mImage_advert = bundle.getString("image_advert");
        mId_advert = bundle.getString("id_advert");*/


        fMoneyBalance = Float.parseFloat(KaKuApplication.money_balance_qiang);
        isMulti = "N".equals(KaKuApplication.flag_one_qiang) ? true : false;
        hasSetPwd = "N".equals(KaKuApplication.flag_pay_qiang) ? true : false;
        fPriceProduct = Float.parseFloat(KaKuApplication.price_advert_qiang);
        fPriceDeduction = Float.parseFloat(KaKuApplication.breaks_money_qiang);
        mName_advert = KaKuApplication.name_advert_qiang;
        mImage_advert = KaKuApplication.image_advert_qiang;
        mId_advert = KaKuApplication.id_advert_qiang;
        mAddr = KaKuApplication.addr_qiang;


        addr = mAddr.getAddr();
        name_addr = mAddr.getName_addr();
        phone_addr = mAddr.getPhone_addr();

        tv_balance_available.setText("￥" + getFomatFloatString(fMoneyBalance));
        setViews();
        changePriceView();
    }

    private void setViews() {
        tv_sticker_title.setText(mName_advert);
//        iv_sticker_product
        BitmapUtil.download(iv_sticker_product, KaKuApplication.qian_zhui + mImage_advert);
        tv_sticker_price.setText(getFomatFloatString(fPriceProduct));

        setAddress();
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


    private void initBalancePrice() {
        tv_balance_available = (TextView) findViewById(R.id.tv_balance_available);


        cbx_balance_toggle = (CheckBox) findViewById(R.id.cbx_balance_toggle);

        cbx_balance_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!hasSetPwd) {
                    //todo 弹出对话框
                    showSetPwdDialog();

                } else {
                    isBalanceChecked = cbx_balance_toggle.isChecked();

                    if (isBalanceChecked) {
                        tv_balance_available.setTextColor(Color.parseColor("#d10000"));
                        changePriceView();
                    } else {
                        tv_balance_available.setTextColor(Color.parseColor("#999999"));
                        changePriceView();

                    }

                }

            }
        });


    }

    private String getFomatFloatString(Float fMoney) {

        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.

        return decimalFormat.format(fMoney);//format 返回的是字符串

    }

    private void showSetPwdDialog() {

        if (!isCancleSetPwd) {
            //弹出密码设置对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(StickerOrderActivity.this);
            builder.setCustomTitle(null);
            builder.setMessage("为了您的资金安全，请先设置支付密码");
            builder.setNegativeButton("去设置", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(StickerOrderActivity.this, SetWithDrawCodeActivity.class);

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
            // MobclickAgent.onEvent(context, "CommitCarOrder");

            if (isBalanceChecked) {
                showPwdInputWindow();
            } else {
                generateOrderId();
            }
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
                        new InputPwdPopWindow(StickerOrderActivity.this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 121) {
            tv_address_name.setText(data.getStringExtra("name"));
            tv_address_phone.setText(data.getStringExtra("phone"));
            tv_address_address.setText(data.getStringExtra("addr"));
        }
    }

    private void generateOrderId() {


        if (TextUtils.isEmpty(tv_address_address.getText().toString().trim()) || TextUtils.isEmpty(tv_address_name.getText().toString().trim()) || TextUtils.isEmpty(tv_address_phone.getText().toString().trim())) {
            LogUtil.showShortToast(context, "请选择正确的收货地址");
            return;
        }

        Utils.NoNet(context);
        showProgressDialog();

        GenerateStickerOrderReq req = new GenerateStickerOrderReq();
        req.code = "60034";
        req.id_driver = Utils.getIdDriver();
        req.id_advert = mId_advert;
        req.price_advert = getFomatFloatString(fPriceTotal);
        req.money_balance = getFomatFloatString(fBalanceDeduction);
        req.price_bill = getFomatFloatString(fPriceRealPay);
        req.breaks_money = getFomatFloatString(fPriceDeduction);
        req.name_addr = mAddr.getName_addr();
        req.phone_addr = mAddr.getPhone_addr();
        req.addr = mAddr.getAddr();
        req.num_advert = String.valueOf(productNum);

        KaKuApiProvider.generateStickOrder(req, new BaseCallback<GenerateStickerOrderResp>(GenerateStickerOrderResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, GenerateStickerOrderResp t) {
                //stopProgressDialog();
                if (t != null) {


                    if (Constants.RES.equals(t.res)) {
                        LogUtil.E("generateStickOrder res: " + t.no_bill);
                        //todo 进入订单列表页
                        if (fPriceRealPay == 0f) {
                            /*Intent intent = new Intent(context, TruckOrderDetailActivity.class);
                            intent.putExtra("idbill", t.id_bill);
                            startActivity(intent);*/
                            //变更订单类型为truck
                            KaKuApplication.payType = "STICKER";
                            KaKuApplication.realPayment = "0.00";
                            Intent intent = new Intent(context, AlipayCallBackActivity.class);
                            startActivity(intent);

                        } else if (fPriceRealPay > 0f) {

                            Intent intent = new Intent(context, OrderCheTiePayActivity.class);
                            intent.putExtra("no_bill", t.no_bill);
                            intent.putExtra("price_bill", getFomatFloatString(fPriceRealPay));
                            startActivity(intent);
                        }
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

}
