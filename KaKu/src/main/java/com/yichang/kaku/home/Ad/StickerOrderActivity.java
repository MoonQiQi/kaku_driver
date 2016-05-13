package com.yichang.kaku.home.ad;

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

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.member.address.AddrActivity;
import com.yichang.kaku.member.cash.SetWithDrawCodeActivity;
import com.yichang.kaku.payhelper.alipay.PaySuccessActivity;
import com.yichang.kaku.request.GenerateStickerOrderReq;
import com.yichang.kaku.request.GetCheTieDetailReq;
import com.yichang.kaku.response.GenerateStickerOrderResp;
import com.yichang.kaku.response.GetCheTieDetailResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.popwindow.InputPwdPopWindow;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

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
    private TextView tv_sticker_num, tv_addr_area;
    //购买数量
    private LinearLayout ll_product_num;

    private ImageView iv_sticker_jianhao, iv_sticker_jiahao;
    private TextView tv_sticker_add_num;
    private Integer productNum = 1;
    //private String priceBill;


    //是否勾选了余额支付
    private boolean isBalanceChecked = false;
    //是否设置过支付密码
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
        KaKuApplication.IsOrderSetPass = false;
        KaKuApplication.success_type = "chetie";
        //获取订单数据
        GetCheTieDetail();
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

        if (cbx_balance_toggle.isChecked()) {

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

        fMoneyBalance = Float.parseFloat(KaKuApplication.money_balance_qiang);
        isMulti = "N".equals(KaKuApplication.flag_one_qiang) ? true : false;
        KaKuApplication.flag_balance = "N".equals(KaKuApplication.flag_pay_qiang) ? true : false;
        fPriceProduct = Float.parseFloat(KaKuApplication.price_advert_qiang);
        fPriceDeduction = Float.parseFloat(KaKuApplication.breaks_money_qiang);
        mName_advert = KaKuApplication.name_advert_qiang;
        mImage_advert = KaKuApplication.image_advert_qiang;
        mId_advert = KaKuApplication.id_advert_qiang;

        tv_balance_available.setText("￥" + getFomatFloatString(fMoneyBalance));
        setViews();
        changePriceView();
        setAddress();
    }

    private void setViews() {
        tv_sticker_title.setText(mName_advert);
        BitmapUtil.download(iv_sticker_product, KaKuApplication.qian_zhui + mImage_advert);
        tv_sticker_price.setText(getFomatFloatString(fPriceProduct));

    }


    private void setAddress() {

        if (KaKuApplication.AddrObj != null) {

            if (TextUtils.isEmpty(KaKuApplication.AddrObj.getName_addr())) {
                ll_address.setVisibility(View.INVISIBLE);
                tv_notify.setVisibility(View.VISIBLE);
            } else {
                ll_address.setVisibility(View.VISIBLE);
                tv_notify.setVisibility(View.INVISIBLE);
                tv_address_name.setText(KaKuApplication.AddrObj.getName_addr());
                tv_address_phone.setText(KaKuApplication.AddrObj.getPhone_addr());
                tv_address_address.setText(KaKuApplication.AddrObj.getAddr());
                tv_addr_area.setText(KaKuApplication.AddrObj.getRemark_area());
            }

        }
    }

    private void initAddress() {
        rela_address_change = (RelativeLayout) findViewById(R.id.rela_address_change);
        rela_address_change.setOnClickListener(this);
        tv_address_name = (TextView) findViewById(R.id.tv_address_name);
        tv_address_phone = (TextView) findViewById(R.id.tv_address_phone);
        tv_address_address = (TextView) findViewById(R.id.tv_address_address);
        tv_addr_area = (TextView) findViewById(R.id.tv_addr_area);

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

                if (isChecked) {
                    if (!KaKuApplication.flag_balance) {
                        //todo 弹出对话框
                        Intent intent = new Intent(StickerOrderActivity.this, SetWithDrawCodeActivity.class);
                        intent.putExtra("isPassExist", false);
                        intent.putExtra("flag_next_activity", "NONE");
                        startActivity(intent);
                        LogUtil.showShortToast(context, "为了您的资金安全，请先设置支付密码");
                    }
                    isBalanceChecked = true;
                    tv_balance_available.setTextColor(Color.parseColor("#d10000"));
                    changePriceView();
                } else {
                    isBalanceChecked = false;
                    tv_balance_available.setTextColor(Color.parseColor("#999999"));
                    changePriceView();
                }
            }
        });
    }

    private String getFomatFloatString(Float fMoney) {

        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.

        return decimalFormat.format(fMoney);//format 返回的是字符串

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
            MobclickAgent.onEvent(context, "CheTieTiJiao");

            if (isBalanceChecked) {
                showPwdInputWindow();
            } else {
                generateOrderId();
            }
        } else if (R.id.rela_address_change == id) {
            KaKuApplication.IsOrderToAddr = true;
            Intent intent = new Intent(this, AddrActivity.class);
            startActivity(intent);
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
        req.flag_type = KaKuApplication.flag_dory;
        req.price_advert = getFomatFloatString(fPriceTotal);
        req.price_balance = getFomatFloatString(fBalanceDeduction);
        req.price_bill = getFomatFloatString(fPriceRealPay);
        req.breaks_money = getFomatFloatString(fPriceDeduction);
        req.name_addr = tv_address_name.getText().toString();
        req.phone_addr = tv_address_phone.getText().toString();
        req.addr = tv_addr_area.getText().toString().trim() + tv_address_address.getText().toString().trim();
        req.num_advert = String.valueOf(productNum);
        req.sign = Utils.getHmacMd5Str(getFomatFloatString(fBalanceDeduction));

        KaKuApiProvider.generateStickOrder(req, new KakuResponseListener<GenerateStickerOrderResp>(this, GenerateStickerOrderResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    if (Constants.RES.equals(t.res)) {
                        LogUtil.E("generateStickOrder res: " + t.no_bill);
                        //todo 进入订单列表页

                        if (fPriceTotal - fPriceDeduction == 0) {
                            KaKuApplication.payType = "STICKER";
                            KaKuApplication.realPayment = "0.00";
                            Intent intent = new Intent(context, PaySuccessActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        }

                        if (fPriceRealPay == 0f) {
                            KaKuApplication.payType = "STICKER";
                            KaKuApplication.realPayment = "0.00";
                            Intent intent = new Intent(context, PaySuccessActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (fPriceRealPay > 0f) {
                            Intent intent = new Intent(context, OrderCheTiePayActivity.class);
                            intent.putExtra("no_bill", t.no_bill);
                            intent.putExtra("price_bill", getFomatFloatString(fPriceRealPay));
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

    @Override
    protected void onResume() {
        super.onResume();
        if (KaKuApplication.flag_balance && KaKuApplication.IsOrderSetPass) {
            cbx_balance_toggle.setChecked(true);
        } else {
            cbx_balance_toggle.setChecked(false);
        }
        setAddress();
    }

    public void GetCheTieDetail() {
        showProgressDialog();
        GetCheTieDetailReq req = new GetCheTieDetailReq();
        req.code = "60032";
        req.id_driver = Utils.getIdDriver();
        req.id_advert = KaKuApplication.id_advert;
        req.flag_type = KaKuApplication.flag_dory;
        KaKuApiProvider.getCheTieDetail(req, new KakuResponseListener<GetCheTieDetailResp>(this, GetCheTieDetailResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getchetiedetail res: " + t.res);
                    if (Constants.RES.equals(t.res)) {

                        KaKuApplication.breaks_money_qiang = t.advert.breaks_money;
                        KaKuApplication.flag_one_qiang = t.flag_one;
                        KaKuApplication.id_advert_qiang = t.advert.id_advert;
                        KaKuApplication.flag_pay_qiang = t.flag_pay;
                        KaKuApplication.image_advert_qiang = t.advert.image_advert;
                        KaKuApplication.money_balance_qiang = t.money_balance;
                        KaKuApplication.price_advert_qiang = t.advert.price_advert;
                        KaKuApplication.name_advert_qiang = t.advert.name_advert;
                        KaKuApplication.AddrObj = t.addr;
                        getOrderInfo();
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
}