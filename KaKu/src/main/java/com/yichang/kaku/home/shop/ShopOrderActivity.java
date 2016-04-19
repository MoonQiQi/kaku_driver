package com.yichang.kaku.home.shop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.ConnectPeopleActivity;
import com.yichang.kaku.home.nouse.FaPiaoActivity;
import com.yichang.kaku.home.nouse.TimeActivity;
import com.yichang.kaku.home.nouse.YouHuiQuanActivity;
import com.yichang.kaku.member.serviceorder.ServiceOrderListActivity;
import com.yichang.kaku.payhelper.alipay.AlipayHelper;
import com.yichang.kaku.payhelper.arrivalpay.ArrivalpayCallBackActivity;
import com.yichang.kaku.payhelper.wxpay.PayActivity;
import com.yichang.kaku.request.CommitOrderReq;
import com.yichang.kaku.request.GetOrderReq;
import com.yichang.kaku.response.CommitOrderResp;
import com.yichang.kaku.response.GetOrderResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.text.DecimalFormat;

public class ShopOrderActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private TextView tv_shoporder_commit;
    private TextView tv_order_shifukuan1, tv_order_shifukuan2;
    private TextView tv_order_shangpinjine, tv_order_daochefuwufei, tv_order_jifen, tv_order_jifenfei, tv_order_jifenkeyong;
    private TextView tv_order_gongshifei, tv_order_shoujianfei, tv_order_manjianfei, tv_order_youhuiquanfei;
    private float shangpinjine = 0.00f;
    private float daochefuwufei = 0.00f;
    private float jifen = 0.00f;
    private float youhuiquan = 0.00f;
    private float shoujianfei = 0.00f;
    private float manjianfei = 0.00f;
    private float money = 0.00f;
    private String type_pay = "0";
    private RelativeLayout rela_weixin, rela_zhifubao, rela_wangyin, rela_daodian, rela_order_time, rela_order_fapiao, rela_order_youhuiquan;
    private LinearLayout rela_order_jifendi,line_shoporder_fapiao;
    private ImageView cb_order_weixin, cb_order_zhifubao, cb_order_wangyin, cb_order_daodian;
    private CheckBox cb_order_jifen, cb_order_fapiao;
    private EditText et_order_jifen;
    private String jifen_string;
    private String s1, s2;
    private String point_limit;
    private int point = 0;
    private TextView tv_shoporder_showdate, tv_shoporder_showtime;
    private String time, date;
    private String type_invoice = "", type_invoice1 = "", var_invoice = "", phone_invoice = "", email_invoice = "";
    private TextView tv_orderdetail_fapiao1, tv_orderdetail_fapiao2, tv_orderdetail_fapiao3,tv_shoporder_peoname,tv_shoporder_peophone;
    private float jifen2 = 0.00f;
    private EditText et_shoporder_beizhu, et_shoporder_fapiao;
    private TextView tv_order_shoujian, tv_order_manjian, tv_order_youhuiquan;
    private String manjianqian, shoujianqian, manjianqian2, shoujianqian2;
    private String id_youhuiquan = "", name_youhuiquan = "", money_youhuiquan = "";
    private float youhui;
    private int max;
    private View view_11;
    private boolean isPaying = false;
    private RelativeLayout rela_order_shoujian,rela_order_manjian,rela_order_lianxiren;

    private View  view_fapiao_high,view_fapiao_normal,view_fapiao_title;
    private String idDriver;

    private View view_daodian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoporder);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("确认订单");
        tv_shoporder_commit = (TextView) findViewById(R.id.tv_shoporder_commit);
        tv_shoporder_commit.setOnClickListener(this);
        tv_order_shifukuan1 = (TextView) findViewById(R.id.tv_order_shifukuan1);
        tv_order_shifukuan2 = (TextView) findViewById(R.id.tv_order_shifukuan2);
        tv_order_shangpinjine = (TextView) findViewById(R.id.tv_order_shangpinjine);
        tv_order_daochefuwufei = (TextView) findViewById(R.id.tv_order_daochefuwufei);
        view_11 = findViewById(R.id.view_11);

        float carmoney = KaKuApplication.carmoney;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String format1 = decimalFormat.format(carmoney);
        tv_order_daochefuwufei.setText("¥ " + format1);

        float cmoney = KaKuApplication.money;
        DecimalFormat decimalFormat2 = new DecimalFormat("0.00");
        String format2 = decimalFormat.format(cmoney);
        tv_order_shangpinjine.setText("¥ " + format2);
        tv_order_manjian = (TextView) findViewById(R.id.tv_order_manjian);
        tv_order_shoujianfei = (TextView) findViewById(R.id.tv_order_shoujianfei);
        tv_order_manjianfei = (TextView) findViewById(R.id.tv_order_manjianfei);
        tv_order_youhuiquanfei = (TextView) findViewById(R.id.tv_order_youhuiquanfei);
        tv_order_youhuiquan = (TextView) findViewById(R.id.tv_order_youhuiquan);
        tv_shoporder_peoname = (TextView) findViewById(R.id.tv_shoporder_peoname);
        tv_shoporder_peophone = (TextView) findViewById(R.id.tv_shoporder_peophone);
        rela_weixin = (RelativeLayout) findViewById(R.id.rela_weixin);
        rela_weixin.setOnClickListener(this);
        rela_zhifubao = (RelativeLayout) findViewById(R.id.rela_zhifubao);
        rela_zhifubao.setOnClickListener(this);
        rela_order_lianxiren = (RelativeLayout) findViewById(R.id.rela_order_lianxiren);
        rela_order_lianxiren.setOnClickListener(this);

        //初始化到店支付分割线
        view_daodian=findViewById(R.id.view_daodian);
        rela_daodian = (RelativeLayout) findViewById(R.id.rela_daodian);
        rela_daodian.setOnClickListener(this);
        if(KaKuApplication.carmoney!=0){
            rela_daodian.setVisibility(View.GONE);
            //隐藏到店支付分割线
            view_daodian.setVisibility(View.GONE);
        }
        line_shoporder_fapiao = (LinearLayout) findViewById(R.id.line_shoporder_fapiao);
        rela_order_fapiao = (RelativeLayout) findViewById(R.id.rela_order_fapiao);
        rela_order_time = (RelativeLayout) findViewById(R.id.rela_order_time);
        rela_order_time.setOnClickListener(this);
        rela_order_shoujian = (RelativeLayout) findViewById(R.id.rela_order_shoujian);
        rela_order_manjian = (RelativeLayout) findViewById(R.id.rela_order_manjian);
        rela_order_youhuiquan = (RelativeLayout) findViewById(R.id.rela_order_youhuiquan);
        rela_order_youhuiquan.setOnClickListener(this);
        /*rela_order_jifendi = (LinearLayout) findViewById(R.id.rela_order_jifendi);
        rela_order_jifendi.setVisibility(View.GONE);
        et_order_jifen = (EditText) findViewById(R.id.et_order_jifen);*/
        tv_order_shoujian = (TextView) findViewById(R.id.tv_order_shoujian);
        et_shoporder_beizhu = (EditText) findViewById(R.id.et_shoporder_beizhu);
        et_shoporder_fapiao = (EditText) findViewById(R.id.et_shoporder_fapiao);
        tv_shoporder_showdate = (TextView) findViewById(R.id.tv_shoporder_showdate);
        tv_shoporder_showtime = (TextView) findViewById(R.id.tv_shoporder_showtime);

        cb_order_weixin = (ImageView) findViewById(R.id.cb_order_weixin);
        cb_order_zhifubao = (ImageView) findViewById(R.id.cb_order_zhifubao);
        cb_order_daodian = (ImageView) findViewById(R.id.cb_order_daodian);
        cb_order_fapiao = (CheckBox) findViewById(R.id.cb_order_fapiao);
        cb_order_fapiao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    line_shoporder_fapiao.setVisibility(View.VISIBLE);
                    view_fapiao_title.setVisibility(View.VISIBLE);
                } else {
                    line_shoporder_fapiao.setVisibility(View.GONE);
                    view_fapiao_title.setVisibility(View.GONE);
                }
            }
        });

        shangpinjine = KaKuApplication.money;
        daochefuwufei = KaKuApplication.carmoney;
        String string = "实付款： ¥ " + money;
        SpannableStringBuilder style = new SpannableStringBuilder(string);
        style.setSpan(new ForegroundColorSpan(Color.rgb(209, 0, 0)), 5, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_order_shifukuan1.setText(style);
        tv_order_shifukuan2.setText("实付款 ¥ " + money);

//        初始化间隔线，当隐藏发票栏时，更改高度
        view_fapiao_high=findViewById(R.id.view_fapiao_high);
        view_fapiao_normal=findViewById(R.id.view_fapiao_normal);
        view_fapiao_title=findViewById(R.id.view_fapiao_title);

        GetOrder();
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
        } else if (R.id.tv_shoporder_commit == id) {
            if ("".equals(tv_shoporder_showdate.getText().toString().trim()) || "".equals(tv_shoporder_showtime.getText().toString().trim())) {
                LogUtil.showShortToast(context, "请选择预约时间");
                return;
            }

            CommitOrder();
        } else if (R.id.rela_weixin == id) {
            SetImage();
            type_pay = "0";
            cb_order_weixin.setImageResource(R.drawable.check_yuan);
        } else if (R.id.rela_zhifubao == id) {
            SetImage();
            type_pay = "1";
            cb_order_zhifubao.setImageResource(R.drawable.check_yuan);
        } else if (R.id.rela_daodian == id) {
            SetImage();
            type_pay = "3";
            cb_order_daodian.setImageResource(R.drawable.check_yuan);
        } else if (R.id.rela_order_time == id) {
            GetTime();
        } else if (R.id.rela_order_youhuiquan == id) {
            Intent intent = new Intent(context, YouHuiQuanActivity.class);
            startActivityForResult(intent, 2);
        } else if (R.id.rela_order_lianxiren == id){
            Intent intent = new Intent(context, ConnectPeopleActivity.class);
            startActivityForResult(intent, 3);
        }
    }

    public void CommitOrder() {
        Utils.NoNet(context);

        CommitOrderReq req = new CommitOrderReq();
        req.code = "40011";
        req.id_driver = Utils.getIdDriver();
        req.id_shop = KaKuApplication.id_shop;
        req.time_order = date + " " + time;
        req.type_pay = type_pay;
        req.type_invoice = type_invoice;
        req.type1_invoice = type_invoice1;
        req.var_invoice = et_shoporder_fapiao.getText().toString().trim();
        req.phone_invoice = phone_invoice;
        req.email_invoice = email_invoice;
        req.remark_order = et_shoporder_beizhu.getText().toString().trim();
        req.point_order = "0.00";
        req.id_coupon = id_youhuiquan;
        req.price_goods = String.valueOf(shangpinjine);
        req.price_visit = String.valueOf(daochefuwufei);
        req.price_point = String.valueOf(jifen);
        req.price_coupon = money_youhuiquan;
        req.price_order = String.valueOf(money);
        //设置付款金额，支付完成页面调用显示支付金额
        KaKuApplication.realPayment=String.valueOf(money);

        req.price_hour = "0.00";
        req.price_manj = manjianqian2;
        req.price_shouj = shoujianqian2;
        req.id_items = KaKuApplication.id_string;
        req.num_items = KaKuApplication.num_string;
        req.price_items = KaKuApplication.price_string;
        req.addr_service = KaKuApplication.addr_string;
        req.lat_service = KaKuApplication.lat;
        req.lon_service =  KaKuApplication.lon;
        req.name_driver = tv_shoporder_peoname.getText().toString();
        req.phone_driver = tv_shoporder_peophone.getText().toString() ;

        KaKuApiProvider.CommitOrder(req, new KakuResponseListener<CommitOrderResp>(this, CommitOrderResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("commitorder res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        //LogUtil.showShortToast(context, "订单提交成功，请在24小时内完成支付哦~");
                        KaKuApplication.payType = "SERVICE";
                        isPaying = true;
                        /*todo 增加支付类型判断*/
                        switch (type_pay) {
                            case "0":
                                //weixin
                                LogUtil.showLongToast(context, "订单提交成功，请在24小时内完成支付哦~");
                                wxPay(t);
                                //finish();
                                break;
                            case "1":
                                LogUtil.showLongToast(context, "订单提交成功，请在24小时内完成支付哦~");
                                aliPay(t);
                                //finish();
                                break;
                            case "3":
                                LogUtil.showLongToast(context, "订单提交成功，我们期待您的光临~");
                                /*gotoShopListActivity();*/
                                startActivity(new Intent(ShopOrderActivity.context, ArrivalpayCallBackActivity.class));
                                break;
                        }

                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

        });
    }

    private void aliPay(CommitOrderResp t) {
        //支付宝支付
        AlipayHelper helper = new AlipayHelper(ShopOrderActivity.this);
        //todo dou
        helper.pay("卡库养车" + t.no_order, "车品订单", String.valueOf(money), t.no_order);
    }

    private void wxPay(CommitOrderResp params) {
        Utils.NoNet(context);

        KaKuApplication.id_order = params.no_order;
        LogUtil.E("支付参数" + params.toString());
        Intent intent = new Intent(getApplicationContext(), PayActivity.class);

        intent.putExtra("no_order", params.no_order);
        intent.putExtra("appid", params.appid);
        intent.putExtra("noncestr", params.noncestr);
        intent.putExtra("package", params.package0);
        intent.putExtra("partnerid", params.partnerid);
        intent.putExtra("prepayid", params.prepay_id);
        intent.putExtra("timestamp", params.timestamp);
        intent.putExtra("sign", params.sign);


        try {
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        if (isPaying) {
            stopProgressDialog();
            finish();
            isPaying=false;
        }
        super.onStop();
    }

    public void SetImage() {
        cb_order_weixin.setImageResource(R.drawable.uncheck_yuan);
        cb_order_zhifubao.setImageResource(R.drawable.uncheck_yuan);
        cb_order_daodian.setImageResource(R.drawable.uncheck_yuan);
    }

    public void GetOrder() {
        Utils.NoNet(context);
        GetOrderReq req = new GetOrderReq();
        req.code = "4009";
        req.id_driver = Utils.getIdDriver();
        req.id_shop = KaKuApplication.id_shop;
        req.total_price = String.valueOf(KaKuApplication.money + KaKuApplication.carmoney);
        KaKuApiProvider.GetOrder(req, new KakuResponseListener<GetOrderResp>(this, GetOrderResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getorder res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
//                        根据状态判断是否隐藏发票栏
                        if ("N".equals(t.flag_ticket)) {
                            rela_order_fapiao.setVisibility(View.GONE);
                            view_fapiao_high.setVisibility(View.GONE);
                            view_fapiao_normal.setVisibility(View.VISIBLE);
                            view_11.setVisibility(View.GONE);
                        } else {
                            rela_order_fapiao.setVisibility(View.VISIBLE);
                            view_fapiao_high.setVisibility(View.VISIBLE);
                            view_fapiao_normal.setVisibility(View.GONE);
                            view_11.setVisibility(View.VISIBLE);
                        }
                        tv_order_youhuiquan.setText(t.num_coupons + "张可用");
                        tv_shoporder_peoname.setText(t.name_driver);
                        tv_shoporder_peophone.setText(t.phone_driver);
                        KaKuApplication.name_connect = tv_shoporder_peoname.getText().toString().trim();
                        KaKuApplication.phone_connect = tv_shoporder_peophone.getText().toString().trim();
                        if ("0".equals(t.num_coupons)) {
                            rela_order_youhuiquan.setEnabled(false);
                        } else {
                            rela_order_youhuiquan.setEnabled(true);
                        }
                        point_limit = t.point_limit;
                        point = Integer.parseInt(point_limit);
                        max = point;
                        if (TextUtils.isEmpty(manjianqian)) {
                            rela_order_manjian.setVisibility(View.GONE);
                        } else {
                            rela_order_manjian.setVisibility(View.VISIBLE);
                        }
                        if (TextUtils.isEmpty(shoujianqian)) {
                            rela_order_shoujian.setVisibility(View.GONE);
                        } else {
                            rela_order_shoujian.setVisibility(View.VISIBLE);
                        }
                        manjianqian = t.activity_mj;
                        shoujianqian = t.activity_sj;
                        manjianqian2 = t.price_manj;
                        shoujianqian2 = t.price_shouj;
                        tv_order_shoujian.setText(t.activity_sj);
                        tv_order_manjian.setText(t.activity_mj);
                        tv_order_shoujianfei.setText(" ¥ " + t.price_shouj);
                        tv_order_manjianfei.setText(" ¥ " + t.price_manj);
                        shoujianfei = Float.parseFloat(t.price_shouj);
                        manjianfei = Float.parseFloat(t.price_manj);
                        money = shangpinjine + daochefuwufei - jifen - youhuiquan - shoujianfei - manjianfei;
                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                        String format_money = decimalFormat.format(money);
                        String string = "实付款： ¥ " + format_money;
                        SpannableStringBuilder style3 = new SpannableStringBuilder(string);
                        style3.setSpan(new ForegroundColorSpan(Color.rgb(209, 0, 0)), 5, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv_order_shifukuan1.setText(style3);
                        tv_order_shifukuan2.setText("实付款:¥ " + format_money);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

        });
    }

    public void GetTime() {
        Intent intent = new Intent(context, TimeActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (data != null) {
                    time = data.getExtras().getString("time");
                    date = data.getExtras().getString("date");
                    tv_shoporder_showdate.setText(date);
                    tv_shoporder_showtime.setText(time);
                }
                break;

            case 1:
                if (data != null) {
                    type_invoice = data.getExtras().getString("type_invoice");
                    type_invoice1 = data.getExtras().getString("type_invoice1");
                    var_invoice = data.getExtras().getString("var_invoice");
                    phone_invoice = data.getExtras().getString("phone_invoice");
                    email_invoice = data.getExtras().getString("email_invoice");
                    if ("0".equals(type_invoice)) {
                        tv_orderdetail_fapiao1.setText("纸质发票");
                    } else if ("1".equals(type_invoice)) {
                        tv_orderdetail_fapiao1.setText("电子发票");
                    } else if ("2".equals(type_invoice)) {
                        tv_orderdetail_fapiao1.setText("不开发票");
                    }
                    if ("1".equals(type_invoice)) {
                        tv_orderdetail_fapiao2.setText("个人");
                    } else {
                        tv_orderdetail_fapiao2.setText(var_invoice);
                    }
                    if ("0".equals(type_invoice1)) {
                        tv_orderdetail_fapiao3.setText("车辆配件");
                    } else if ("1".equals(type_invoice1)) {
                        tv_orderdetail_fapiao3.setText("明细");
                    } else if ("2".equals(type_invoice1)) {
                        tv_orderdetail_fapiao3.setText("生活用品");
                    }
                }
                break;
            case 2:
                if (data != null) {
                    id_youhuiquan = data.getExtras().getString("id_youhuiquan");
                    name_youhuiquan = data.getExtras().getString("name_youhuiquan");
                    money_youhuiquan = data.getExtras().getString("money_youhuiquan");
                    youhuiquan = Float.parseFloat(money_youhuiquan);
                    tv_order_youhuiquan.setText(name_youhuiquan);
                    tv_order_youhuiquanfei.setText(" ¥ " + money_youhuiquan);
                    money = shangpinjine + daochefuwufei - jifen - youhuiquan - shoujianfei - manjianfei;
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    String format_money = decimalFormat.format(money);
                    String string = "实付款： ¥ " + format_money;
                    SpannableStringBuilder style3 = new SpannableStringBuilder(string);
                    style3.setSpan(new ForegroundColorSpan(Color.rgb(209, 0, 0)), 5, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_order_shifukuan1.setText(style3);
                    tv_order_shifukuan2.setText("实付款 ¥ " + format_money);
                }
                break;
            case 3:
                if (data != null) {
                    tv_shoporder_peoname.setText(data.getExtras().getString("name"));
                    tv_shoporder_peophone.setText(data.getExtras().getString("phone"));
                    KaKuApplication.name_connect = tv_shoporder_peoname.getText().toString().trim();
                    KaKuApplication.phone_connect = tv_shoporder_peophone.getText().toString().trim();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void GoToFaPiao() {
        Intent intent = new Intent(context, FaPiaoActivity.class);
        startActivityForResult(intent, 1);
    }

    private void gotoShopListActivity() {
        Intent intent = new Intent(context, ServiceOrderListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        KaKuApplication.color_order = "";
        KaKuApplication.state_order = "";
        startActivity(intent);
    }
}
