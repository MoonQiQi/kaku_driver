package com.yichang.kaku.member.serviceorder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.WuLiaoObj;
import com.yichang.kaku.request.OrderDetailReq;
import com.yichang.kaku.request.QueDingWanChengReq;
import com.yichang.kaku.response.OrderDetailResp;
import com.yichang.kaku.response.QueDingWanChengResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailCActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private TextView tv_A_dingdanhao, tv_A_shopname, tv_A_time, tv_A_point, tv_A_dan, tv_A_addr, tv_A_phone, tv_A_daoche;//, tv_A_state;
    private ImageView iv_A_image, iv_A_call, iv_A_piao, iv_A_bao, iv_A_pei, iv_A_fu, iv_A_callman,iv_A_yuyuema;
    private RatingBar star_A_shop;
    private String phone, phoneman;
    private ListView lv_A_baoyang;
    private List<WuLiaoObj> list_baoyang = new ArrayList<WuLiaoObj>();
    private BaoYangAdapter adapter;
    private TextView tv_A_addrmy, tv_A_zhifufangshi, tv_A_remark, tv_A_daochefuwu;
    private TextView tv_A_fapiaocaizhi, tv_A_fapiaotaitou, tv_A_fapiaomingxi;
    private TextView tv_A_jifen, tv_A_shangpinzonge, tv_A_daochefuwufei, tv_A_jianjifen, tv_A_jianyouhuiquan,
            tv_A_shifukuan, tv_A_ordertime, tv_A_zhipaiweixiugong, tv_A_callman,tv_A_yuyuetime;
    private Button btn_A_quxiaodingdan, btn_A_lijizhifu;
    //    首减金额，满减金额
    private RelativeLayout rela_shoujian, rela_manjian, rela_C_shangmenfuwufei;
    private TextView tv_shoujian, tv_manjian,tv_A_yuyuema;
    //    到车服务地址，如无到车服务则不显示
    private LinearLayout ll_arrival_addr;
    private View view_arrival_addr;
    //    隐藏不需要显示的内容
    private View view_remark;
    private View view_coupon;
    private RelativeLayout rela_coupon;
    private View view_shangmenfuwufei;
    private View view_shoujian;
    private View view_manjian;
    private View view_price_coupon;
    private RelativeLayout rela_price_coupon;
    //    优惠券信息
    private TextView tv_C_youhuiquan,tv_orderA_peoname,tv_orderA_peophone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetailc);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("待确认");
        tv_A_dingdanhao = (TextView) findViewById(R.id.tv_C_dingdanhao);
        tv_A_shopname = (TextView) findViewById(R.id.tv_C_shopname);
        tv_A_time = (TextView) findViewById(R.id.tv_C_time);
        tv_A_point = (TextView) findViewById(R.id.tv_C_point);
        tv_A_dan = (TextView) findViewById(R.id.tv_C_dan);
        tv_A_addr = (TextView) findViewById(R.id.tv_C_addr);
        tv_A_daoche = (TextView) findViewById(R.id.tv_C_daoche);
        tv_A_phone = (TextView) findViewById(R.id.tv_C_phone);
        tv_A_addrmy = (TextView) findViewById(R.id.tv_C_addrmy);
        tv_A_zhifufangshi = (TextView) findViewById(R.id.tv_C_zhifufangshi);
        tv_orderA_peoname= (TextView) findViewById(R.id.tv_order_C_peoname);
        tv_orderA_peophone= (TextView) findViewById(R.id.tv_order_C_peophone);
        //tv_A_fapiaocaizhi= (TextView) findViewById(R.id.tv_C_fapiaocaizhi);
        tv_A_fapiaotaitou = (TextView) findViewById(R.id.tv_C_fapiaotaitou);
        //tv_A_fapiaomingxi= (TextView) findViewById(R.id.tv_C_fapiaomingxi);
        //tv_A_state = (TextView) findViewById(R.id.tv_C_state);
        tv_A_daochefuwu = (TextView) findViewById(R.id.tv_C_daochefuwu);
        tv_A_shifukuan = (TextView) findViewById(R.id.tv_C_shifukuan);
        tv_A_yuyuema = (TextView) findViewById(R.id.tv_C_yuyuema);
        tv_A_yuyuetime = (TextView) findViewById(R.id.tv_C_yuyuetime);
        tv_A_daochefuwufei = (TextView) findViewById(R.id.tv_C_daochefuwufei);
        tv_A_jianyouhuiquan = (TextView) findViewById(R.id.tv_C_jianyouhuiquan);
        //tv_A_jianjifen= (TextView) findViewById(R.id.tv_C_jianjifen);
        tv_A_shangpinzonge = (TextView) findViewById(R.id.tv_C_shangpinzonge);
        tv_A_remark = (TextView) findViewById(R.id.tv_C_remark);
        tv_A_ordertime = (TextView) findViewById(R.id.tv_C_ordertime);
        //tv_A_jifen= (TextView) findViewById(R.id.tv_C_jifen);
        iv_A_image = (ImageView) findViewById(R.id.iv_C_image);
        iv_A_call = (ImageView) findViewById(R.id.iv_C_call);
        iv_A_call.setOnClickListener(this);
        iv_A_yuyuema = (ImageView) findViewById(R.id.iv_C_yuyuema);
        star_A_shop = (RatingBar) findViewById(R.id.star_C_shop);
        iv_A_piao = (ImageView) findViewById(R.id.iv_C_piao);
        iv_A_bao = (ImageView) findViewById(R.id.iv_C_bao);
        iv_A_pei = (ImageView) findViewById(R.id.iv_C_pei);
        iv_A_fu = (ImageView) findViewById(R.id.iv_C_fu);
        lv_A_baoyang = (ListView) findViewById(R.id.lv_C_baoyang);
        lv_A_baoyang.setFocusable(false);
        btn_A_quxiaodingdan = (Button) findViewById(R.id.btn_C_quxiaodingdan);
        btn_A_quxiaodingdan.setOnClickListener(this);
        btn_A_lijizhifu = (Button) findViewById(R.id.btn_C_lijizhifu);
        btn_A_lijizhifu.setOnClickListener(this);

        //        初始化首减，满减
        rela_shoujian = (RelativeLayout) findViewById(R.id.rela_shoujian);
        rela_manjian = (RelativeLayout) findViewById(R.id.rela_manjian);
        rela_C_shangmenfuwufei = (RelativeLayout) findViewById(R.id.rela_C_shangmenfuwufei);
        tv_shoujian = (TextView) findViewById(R.id.tv_shoujian);
        tv_manjian = (TextView) findViewById(R.id.tv_manjian);
        //初始化到车服务地址
        ll_arrival_addr = (LinearLayout) findViewById(R.id.ll_arrival_addr);
        view_arrival_addr = findViewById(R.id.view_arrival_addr);
        initHiddenViews();
        OrderDetail();
    }

    private void initHiddenViews() {
        view_remark = findViewById(R.id.view_remark);
        view_coupon = findViewById(R.id.view_coupon);
        rela_coupon = (RelativeLayout) findViewById(R.id.rela_coupon);
        view_shangmenfuwufei = findViewById(R.id.view_shangmenfuwufei);
        view_shoujian = findViewById(R.id.view_shoujian);
        view_manjian = findViewById(R.id.view_manjian);
        view_price_coupon = findViewById(R.id.view_price_coupon);
        rela_price_coupon = (RelativeLayout) findViewById(R.id.rela_price_coupon);
        tv_C_youhuiquan = (TextView) findViewById(R.id.tv_C_youhuiquan);
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
        } else if (R.id.iv_C_call == id) {
            Call(phone);
        }  else if (R.id.btn_C_quxiaodingdan == id) {
            ShenQingShouHou();
        } else if (R.id.btn_C_lijizhifu == id) {
            QueDingWanCheng();
        }
    }

    public void OrderDetail() {
        Utils.NoNet(context);
        OrderDetailReq req = new OrderDetailReq();
        req.code = "40022";
        req.id_order = KaKuApplication.id_orderC;
        KaKuApiProvider.OrderDetail(req, new KakuResponseListener<OrderDetailResp>(this, OrderDetailResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("orderdetail res: " + t.res);
                    if (Constants.RES.equals(t.res)) {

                        //tv_A_state.setText("待确认");

                        if (TextUtils.isEmpty(t.order.getRemark_order())) {
                            tv_A_remark.setVisibility(View.GONE);
                            view_remark.setVisibility(View.GONE);
                        } else {
                            tv_A_remark.setVisibility(View.VISIBLE);
                            view_remark.setVisibility(View.VISIBLE);
                        }
                        if ("N".equals(t.order.getType_service())) {
                            ll_arrival_addr.setVisibility(View.GONE);
                            view_arrival_addr.setVisibility(View.GONE);
                            rela_C_shangmenfuwufei.setVisibility(View.GONE);
                            view_shangmenfuwufei.setVisibility(View.GONE);
                            tv_A_daoche.setText("");
                        } else {
                            ll_arrival_addr.setVisibility(View.VISIBLE);
                            view_arrival_addr.setVisibility(View.VISIBLE);
                            rela_C_shangmenfuwufei.setVisibility(View.VISIBLE);
                            view_shangmenfuwufei.setVisibility(View.VISIBLE);
                            tv_A_daoche.setText("上门");
                        }
                        tv_A_dingdanhao.setText("订单号：" + t.order.getNo_order());
                        tv_A_shopname.setText(t.order.getName_shop());
                        tv_A_time.setText(t.order.getHour_shop_begin() + "-" + t.order.getHour_shop_end());
                        tv_A_point.setText(t.order.getNum_star());
                        String star = t.order.getNum_star();
                        float starFloat = Float.valueOf(star);
                        star_A_shop.setRating(starFloat);
                        tv_A_dan.setText("(" + t.order.getNum_order() + "单)");
                        tv_A_addr.setText(t.order.getAddr_shop());
                        tv_A_phone.setText(t.order.getMobile_shop());
                        phone = t.order.getMobile_shop();
                        //票
                        if ("Y".equals(t.order.getFlag_ticket())) {
                            iv_A_piao.setVisibility(View.VISIBLE);
                        } else {
                            iv_A_piao.setVisibility(View.GONE);
                        }
                        //保
                        if ("Y".equals(t.order.getFlag_assure())) {
                            iv_A_bao.setVisibility(View.VISIBLE);
                        } else {
                            iv_A_bao.setVisibility(View.GONE);
                        }
                        //赔
                        if ("Y".equals(t.order.getFlag_their())) {
                            iv_A_pei.setVisibility(View.VISIBLE);
                        } else {
                            iv_A_pei.setVisibility(View.GONE);
                        }
                        //付
                        if ("Y".equals(t.order.getFlag_pay())) {
                            iv_A_fu.setVisibility(View.VISIBLE);
                        } else {
                            iv_A_fu.setVisibility(View.GONE);
                        }
                        tv_A_yuyuema.setText(t.order.getCode_order());
                        Bitmap creatMyCode = Utils.createQRCode(t.order.getCode_order());
                        iv_A_yuyuema.setImageBitmap(creatMyCode);
                        tv_A_yuyuetime.setText(t.order.getJlyhsj());
                        BitmapUtil.getInstance(context).download(iv_A_image, KaKuApplication.qian_zhui + t.order.getImage_shop());
                        //保养列表
                        list_baoyang = t.items;
                        adapter = new BaoYangAdapter(context, list_baoyang);
                        lv_A_baoyang.setAdapter(adapter);
                        setListViewHeightBasedOnChildren(lv_A_baoyang);
                        //支付方式
                        if ("0".equals(t.order.getType_pay())) {
                            tv_A_zhifufangshi.setText("微信支付");
                        } else if ("1".equals(t.order.getType_pay())) {
                            tv_A_zhifufangshi.setText("支付宝支付");
                        } else if ("2".equals(t.order.getType_pay())) {
                            tv_A_zhifufangshi.setText("网银支付");
                        } else if ("3".equals(t.order.getType_pay())) {
                            tv_A_zhifufangshi.setText("到店支付");
                        }
                        //发票信息
                        if (TextUtils.isEmpty(t.order.getVar_invoice())) {
                            tv_A_fapiaotaitou.setText("不开发票");
                        } else {
                            tv_A_fapiaotaitou.setText(t.order.getVar_invoice());
                        }
                        tv_A_remark.setText("备注：" + t.order.getRemark_order());
                        //积分
                        String point_order = t.order.getPoint_order();
                        float jifen = Float.parseFloat(point_order);
                        float jifen2 = (float) (Math.round(jifen)) / 100;
                        String s1 = "用" + point_order + "积分，抵¥" + jifen2;
                        SpannableStringBuilder style = new SpannableStringBuilder(s1);
                        style.setSpan(new ForegroundColorSpan(Color.rgb(209, 0, 0)), 5 + point_order.length(), s1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        //tv_A_jifen.setText(style);
                        tv_A_shangpinzonge.setText("¥" + t.order.getPrice_goods());
                        tv_A_daochefuwufei.setText("¥" + t.order.getPrice_visit());
                        //tv_A_jianjifen.setText("¥"+t.order.getPrice_point());
                        tv_A_jianyouhuiquan.setText("¥" + t.order.getPrice_coupon());
                        tv_A_shifukuan.setText("¥" + t.order.getPrice_order());
                        tv_A_ordertime.setText("下单日期：" + t.order.getTime_create());
                        tv_A_addrmy.setText(t.order.getAddr_service());
                        tv_orderA_peoname.setText(t.order.getName_driver());
                        tv_orderA_peophone.setText(t.order.getPhone_driver());
                        phoneman = t.shop_user.getPhone_user();

                        //初始化优惠券信息
                        if (TextUtils.isEmpty(t.order.getCoupon_content())) {
                            rela_coupon.setVisibility(View.GONE);
                            view_coupon.setVisibility(View.GONE);
                            rela_price_coupon.setVisibility(View.GONE);
                            view_price_coupon.setVisibility(View.GONE);

                        } else {
                            rela_coupon.setVisibility(View.VISIBLE);
                            view_coupon.setVisibility(View.VISIBLE);
                            rela_price_coupon.setVisibility(View.VISIBLE);
                            view_price_coupon.setVisibility(View.VISIBLE);
                            tv_C_youhuiquan.setText(t.order.getCoupon_content());
                        }
                        //初始化首减满减信息
                        if (!t.order.getPrice_shouj().trim().equals("0.00")) {
                            rela_shoujian.setVisibility(View.VISIBLE);
                            view_shoujian.setVisibility(View.VISIBLE);
                            tv_shoujian.setText("¥" + t.order.getPrice_shouj().trim());
                        } else {
                            rela_shoujian.setVisibility(View.GONE);
                            view_shoujian.setVisibility(View.GONE);
                            tv_shoujian.setText("");
                        }
                        if (!t.order.getPrice_manj().trim().equals("0.00")) {
                            rela_manjian.setVisibility(View.VISIBLE);
                            view_manjian.setVisibility(View.VISIBLE);
                            tv_manjian.setText("¥" + t.order.getPrice_manj().trim());
                        } else {
                            rela_manjian.setVisibility(View.GONE);
                            view_manjian.setVisibility(View.GONE);
                            tv_manjian.setText("");
                        }
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

        });
    }

    public void Call(final String phone) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage("确认拨打:" + phone + "？");
        builder.setNegativeButton("是", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                startActivity(intent);
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

    public void QueDingWanCheng() {
        Utils.NoNet(context);
        QueDingWanChengReq req = new QueDingWanChengReq();
        req.code = "40013";
        req.id_order = KaKuApplication.id_orderC;
        KaKuApiProvider.QueDingWanCheng(req, new KakuResponseListener<QueDingWanChengResp>(this, QueDingWanChengResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("querenwancheng res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.state_order = "";
                        KaKuApplication.color_order = "";
                        startActivity(new Intent(context, ServiceOrderListActivity.class));
                        finish();
                    }
                    LogUtil.showShortToast(context, t.msg);
                }
            }

        });
    }

    public void ShenQingShouHou() {
        KaKuApplication.fanxiu_order = KaKuApplication.id_orderC;
        startActivity(new Intent(context, FanXiuActivity.class));
    }

}
