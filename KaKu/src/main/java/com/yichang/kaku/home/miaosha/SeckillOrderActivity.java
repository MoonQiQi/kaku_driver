package com.yichang.kaku.home.miaosha;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.OrderPayActivity;
import com.yichang.kaku.home.giftmall.ConfirmOrderAdapter;
import com.yichang.kaku.member.address.AddrActivity;
import com.yichang.kaku.obj.ConfirmOrderProductObj;
import com.yichang.kaku.request.GenerateOrderReq;
import com.yichang.kaku.response.GenerateOrderResp;
import com.yichang.kaku.response.SeckillOrderResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SeckillOrderActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {


    //titleBar,返回，购物车，标题
    private TextView left, right, title;

    private ListView lv_confirmorder_list;//购物车商品列表

    private RelativeLayout rela_address_change;// 更改收货地址
    //    收货人姓名 手机号 收货地址
    private TextView tv_address_name, tv_address_phone, tv_address_address;
    private TextView tv_notify;
    private LinearLayout ll_address;

    ////可用积分//使用积分//抵扣金额
    private TextView tv_point_available, et_point_use, tv_point_deduction;
    //商品总额 //减积分//实付款
    private TextView tv_confirmorder_totalprice, tv_point_pricepoint, tv_confirmorder_pricebill;

    private CheckBox cbx_point_toggle;
    private LinearLayout ll_point_use;
    private RelativeLayout rela_point_pricepoint;
    //实付款  //付款按钮
    private TextView tv_bottombar_pricebill, tv_bottombar_pay;


    private List<ConfirmOrderProductObj> list = new ArrayList<>();
    private Integer maxPoint;
    private String priceBill;

    /*收货地址字段*/
    private String addr, phone_addr, name_addr;
    //默认支付类型为微信支付
    private String type_pay = "0";

    //抵扣分值 //积分抵扣金额//总价//实付款
    private String point_bill, price_point, price_goods, price_bill;
    private String money, desc, notify_url, out_trade_no;
    private String phone_invoice;
    private String email_invoice;
    private String var_invoice = "";
    private String type_invoice1;
    private String type_invoice = "2";

    final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
    //    判断是否在修改地址
    private boolean isSelectAddr = false;

    //    分割线
    private View view_point, view_pricepoint;
    private String mId_goods;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seckill_order);
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
        //初始化底部支付条
        initBottomBar();
        //初始化单选框
        //initRadioBtn();

        view_point = findViewById(R.id.view_point);
        view_pricepoint = findViewById(R.id.view_pricepoint);
        mId_goods = getIntent().getStringExtra("id_goods");
        //获取订单数据
    }


    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("确认订单");
    }

    private void setData(SeckillOrderResp t) {
        if (t.shopcar != null) {
            list.addAll(t.shopcar);
        }

        ConfirmOrderAdapter adapter = new ConfirmOrderAdapter(context, list);
        lv_confirmorder_list.setAdapter(adapter);
        setListViewHeightBasedOnChildren(lv_confirmorder_list);

        phone_addr = t.addr.getPhone_addr();
        name_addr = t.addr.getName_addr();
        addr = t.addr.getAddr();
        setAddress();
        /*//抵扣分值 //积分抵扣金额//总价//实付款
        private String point_bill,price_point,price_goods,price_bill;*/


//        float price = Float.parseFloat(t.price_goods);
//        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
//        price_goods = decimalFormat.format(price);//format 返回的是字符串
        price_goods = formatFloatPrice(t.price_goods);
        price_bill = t.price_bill;
//设置积分金额
        setPointPrice(t.point_limit, t.price_point, t.price_goods, t.price_bill);


    }

    private String formatFloatPrice(String strPrice) {
        float price = Float.parseFloat(strPrice);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(price);
    }

    private void setPointPrice(String point_limit, String price_point, String price_goods, String price_bill) {
//        可用积分
        priceBill = price_bill;
        maxPoint = Integer.parseInt(point_limit);
        tv_point_available.setText("可用" + point_limit + "积分，抵用￥" + getDeduction(point_limit));
        //et_point_use.setText(price_point);
        //抵扣金额
        tv_point_deduction.setText("￥" + getDeduction(price_point));


//商品总额

//        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
//        String p = decimalFormat.format(Float.parseFloat(price_goods));//format 返回的是字符串
        tv_confirmorder_totalprice.setText("￥" + formatFloatPrice(price_goods));
        changePayPrice("0");

    }

    private String getPriceRealPay(String price_bill, String price_point) {
        Float fPriceBill = Float.parseFloat(price_bill);
        Float fPricePoint = Float.parseFloat(price_point) / 100;

        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(fPriceBill - fPricePoint);//format 返回的是字符串


        return p;
    }

    /*获取抵扣金额*/
    private String getDeduction(String point_limit) {
        float price = Float.parseFloat(point_limit) / 100;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(price);//format 返回的是字符串
        /*if (price < 1) {

            p = "0" + decimalFormat.format(price);//format 返回的是字符串
        } else {
            p = decimalFormat.format(price);//format 返回的是字符串
        }*/
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


    private void initPointPrice() {
        tv_point_available = (TextView) findViewById(R.id.tv_point_available);
        et_point_use = (EditText) findViewById(R.id.et_point_use);
        et_point_use.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (et_point_use.getText() == "0") {
                    et_point_use.setText("");
                }
            }
        });
        et_point_use.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    changePayPrice("0");
                    return;
                }
                if (Integer.parseInt(s.toString()) > maxPoint) {
                    LogUtil.showShortToast(context, "您的积分不够哟~");
                    et_point_use.setText(s.toString().substring(0, s.length() - 1));
                    Editable ea = et_point_use.getEditableText();
                    Selection.setSelection(ea, ea.length());

                } else {
                    changePayPrice(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        tv_point_deduction = (TextView) findViewById(R.id.tv_point_deduction);

        tv_confirmorder_totalprice = (TextView) findViewById(R.id.tv_confirmorder_totalprice);
        tv_point_pricepoint = (TextView) findViewById(R.id.tv_point_pricepoint);
        tv_confirmorder_pricebill = (TextView) findViewById(R.id.tv_confirmorder_pricebill);

        cbx_point_toggle = (CheckBox) findViewById(R.id.cbx_point_toggle);
        // cbx_point_toggle.setOnClickListener(this);
        cbx_point_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isPointChecked = !isPointChecked;
                if (isPointChecked) {
                    ll_point_use.setVisibility(View.VISIBLE);
                    rela_point_pricepoint.setVisibility(View.VISIBLE);
                    view_point.setVisibility(View.VISIBLE);
                    view_pricepoint.setVisibility(View.VISIBLE);

                } else {
                    ll_point_use.setVisibility(View.GONE);
                    et_point_use.setText("");
                    rela_point_pricepoint.setVisibility(View.GONE);
                    view_point.setVisibility(View.GONE);
                    view_pricepoint.setVisibility(View.GONE);
                    changePayPrice("0");
                }
            }
        });
        ll_point_use = (LinearLayout) findViewById(R.id.ll_point_use);
        rela_point_pricepoint = (RelativeLayout) findViewById(R.id.rela_point_pricepoint);

    }

    /*更改付款金额
    * 如使用积分金额变化，则付款金额随之改变
    *
    * */
    private void changePayPrice(CharSequence s) {

        point_bill = s.toString();
        price_point = getDeduction(s.toString());

        tv_point_deduction.setText("￥" + getDeduction(s.toString()));

        //减积分
        tv_point_pricepoint.setText("-￥" + getDeduction(s.toString()));

        //实付款
        price_bill = getPriceRealPay(priceBill, s.toString());
        tv_confirmorder_pricebill.setText("￥" + price_bill);

        tv_bottombar_pricebill.setText("实付款：￥" + price_bill);
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

   /* private void initRadioBtn() {
        rg_order_pay = (RadioGroup) findViewById(R.id.rg_order_pay);

        rg_order_pay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_order_alipay:
                        type_pay = "1";
                        break;
                    case R.id.rb_order_wxpay:
                        type_pay = "0";
                        break;
                }
            }
        });

        rb_order_wxpay = (RadioButton) findViewById(R.id.rb_order_wxpay);
        rb_order_wxpay.setChecked(true);
        rb_order_wxpay.setOnClickListener(this);
        rb_order_alipay = (RadioButton) findViewById(R.id.rb_order_alipay);
        rb_order_alipay.setOnClickListener(this);
        *//*rb_order_onlinepay = (RadioButton) findViewById(R.id.rb_order_onlinepay);
        rb_order_onlinepay.setOnClickListener(this);*//*
    }*/

    private boolean isPointChecked = false;
    private boolean isInvoiceChecked = false;

    int i = 0;

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();

        if (R.id.tv_left == id) {
            finish();
            /*Intent intent = new Intent(getApplicationContext(), ShopCartActivity.class);
            startActivity(intent);*/
        } else if (R.id.tv_bottombar_pay == id) {
//            与服务器交互生成订单
            MobclickAgent.onEvent(context, "CommitCarOrder");
            generateOrderId();

        } else if (R.id.rela_address_change == id) {
//            修改收货人地址信息
            isSelectAddr = true;
            Intent intent = new Intent(this, AddrActivity.class);
            intent.putExtra("flag", true);
            startActivityForResult(intent, 120);
        }
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
        if (isInvoiceChecked) {
            if (TextUtils.isEmpty(var_invoice)) {
                LogUtil.showShortToast(context, "请输入发票抬头");
                return;
            }
        }

        Utils.NoNet(context);
        showProgressDialog();


        GenerateOrderReq req = new GenerateOrderReq();
        req.code = "3008";
        req.id_driver = Utils.getIdDriver();


//// TODO: 增加地址字段
        req.addr = tv_address_address.getText().toString().trim();
        req.name_addr = tv_address_name.getText().toString().trim();
        req.phone_addr = tv_address_phone.getText().toString().trim();

        req.price_point = price_point;
//        总价
        req.price_goods = price_goods;
//        实付款
        req.price_bill = price_bill;
//todo 为实付款赋值，在支付完成回调页上显示
        KaKuApplication.realPayment = price_bill;

        KaKuApiProvider.generateOrderInfo(req, new KakuResponseListener<GenerateOrderResp>(this, GenerateOrderResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                //stopProgressDialog();
                if (t != null) {
                    LogUtil.E("generateOrderInfo res: " + t.toString());

                    if (Constants.RES.equals(t.res)) {
                        Intent intent = new Intent(context, OrderPayActivity.class);
                        intent.putExtra("no_bill", t.no_bill);
                        intent.putExtra("price_bill", price_bill);
                        startActivity(intent);
                    } else {
                        LogUtil.showShortToast(context, t.msg);

                        Timer timer = new Timer();
                        timer.schedule(task, 2000);


                    }
                }
                //stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });


    }

    TimerTask task = new TimerTask() {
        public void run() {
            stopProgressDialog();
            finish();
        }
    };

    @Override
    protected void onStop() {
        if (isSelectAddr) {

            isSelectAddr = !isSelectAddr;
        } else {
            stopProgressDialog();
            /*todo */
            finish();
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
            tv_address_name.setText(KaKuApplication.name_addr);
            tv_address_phone.setText(KaKuApplication.phone_addr);
            tv_address_address.setText(KaKuApplication.dizhi_addr);
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
           /* Intent intent = new Intent(getApplicationContext(), ShopCartActivity.class);
            startActivity(intent);*/
        }
        return false;
    }

}
