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
import com.yichang.kaku.response.OrderDetailResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailBActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private TextView tv_B_dingdanhao, tv_B_shopname, tv_B_time, tv_B_point, tv_B_dan, tv_B_addr, tv_B_phone, tv_B_daoche;//, tv_B_state;
    private ImageView iv_B_image, iv_B_call, iv_B_piao, iv_B_bao, iv_B_pei, iv_B_fu, iv_B_callman,iv_B_yuyuema,iv_B_wodeweizhi;
    private RatingBar star_A_shop;
    private String phone, phoneman,lat,lon;
    private ListView lv_A_baoyang;
    private List<WuLiaoObj> list_baoyang = new ArrayList<WuLiaoObj>();
    private BaoYangAdapter adapter;
    private TextView tv_B_addrmy, tv_B_zhifufangshi, tv_B_remark, tv_B_daochefuwu;
    private TextView tv_B_fapiaocaizhi, tv_B_fapiaotaitou, tv_B_fapiaomingxi;
    private TextView tv_B_jifen, tv_B_shangpinzonge, tv_B_daochefuwufei, tv_B_jianjifen, tv_B_jianyouhuiquan,
            tv_B_shifukuan, tv_B_ordertime, tv_B_zhipaiweixiugong, tv_B_callman;
    private Button btn_A_quxiaodingdan, btn_A_lijizhifu;
    private TextView tv_B_yuyuetime,tv_B_yuyuema;

    //    首减金额，满减金额
    private RelativeLayout rela_shoujian, rela_manjian, rela_B_shangmenfuwufei, rela_B_callman;
    private TextView tv_shoujian, tv_manjian;
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
    private TextView tv_B_youhuiquan,tv_orderA_peoname,tv_orderA_peophone,tv_B_yuyueshijian;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetailb);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("待安装");
        right = (TextView) findViewById(R.id.tv_right);
        right.setOnClickListener(this);
        right.setVisibility(View.VISIBLE);
        right.setText("申请取消");
        right.setTextColor(Color.rgb(99,99,99));
        tv_B_dingdanhao = (TextView) findViewById(R.id.tv_B_dingdanhao);
        tv_B_shopname = (TextView) findViewById(R.id.tv_B_shopname);
        tv_B_time = (TextView) findViewById(R.id.tv_B_time);
        tv_B_point = (TextView) findViewById(R.id.tv_B_point);
        tv_B_dan = (TextView) findViewById(R.id.tv_B_dan);
        tv_B_addr = (TextView) findViewById(R.id.tv_B_addr);
        tv_B_daochefuwu = (TextView) findViewById(R.id.tv_B_daochefuwu);
        tv_B_daoche = (TextView) findViewById(R.id.tv_B_daoche);
        tv_B_phone = (TextView) findViewById(R.id.tv_B_phone);
        tv_B_addrmy = (TextView) findViewById(R.id.tv_B_addrmy);
        tv_B_zhifufangshi = (TextView) findViewById(R.id.tv_B_zhifufangshi);
        tv_B_fapiaotaitou = (TextView) findViewById(R.id.tv_B_fapiaotaitou);
        //tv_B_state = (TextView) findViewById(R.id.tv_B_state);
        tv_B_shifukuan = (TextView) findViewById(R.id.tv_B_shifukuan);
        tv_B_yuyuetime = (TextView) findViewById(R.id.tv_B_yuyuetime);
        tv_B_yuyuema = (TextView) findViewById(R.id.tv_B_yuyuema);
        tv_B_callman = (TextView) findViewById(R.id.tv_B_callman);
        tv_B_yuyueshijian = (TextView) findViewById(R.id.tv_B_yuyueshijian);
        tv_B_zhipaiweixiugong = (TextView) findViewById(R.id.tv_B_zhipaiweixiugong);
        tv_B_daochefuwufei = (TextView) findViewById(R.id.tv_B_daochefuwufei);
        tv_B_jianyouhuiquan = (TextView) findViewById(R.id.tv_B_jianyouhuiquan);
        tv_orderA_peoname= (TextView) findViewById(R.id.tv_order_B_peoname);
        tv_orderA_peophone= (TextView) findViewById(R.id.tv_order_B_peophone);
        //tv_B_jianjifen= (TextView) findViewById(R.id.tv_B_jianjifen);
        tv_B_shangpinzonge = (TextView) findViewById(R.id.tv_B_shangpinzonge);
        tv_B_remark = (TextView) findViewById(R.id.tv_B_remark);
        tv_B_ordertime = (TextView) findViewById(R.id.tv_B_ordertime);
        //tv_B_jifen= (TextView) findViewById(R.id.tv_B_jifen);
        iv_B_image = (ImageView) findViewById(R.id.iv_B_image);
        iv_B_yuyuema = (ImageView) findViewById(R.id.iv_B_yuyuema);
        iv_B_call = (ImageView) findViewById(R.id.iv_B_call);
        iv_B_call.setOnClickListener(this);
        iv_B_callman = (ImageView) findViewById(R.id.iv_B_callman);
        iv_B_callman.setOnClickListener(this);
        iv_B_wodeweizhi = (ImageView) findViewById(R.id.iv_B_wodeweizhi);
        iv_B_wodeweizhi.setOnClickListener(this);
        star_A_shop = (RatingBar) findViewById(R.id.star_B_shop);
        iv_B_piao = (ImageView) findViewById(R.id.iv_B_piao);
        iv_B_bao = (ImageView) findViewById(R.id.iv_B_bao);
        iv_B_pei = (ImageView) findViewById(R.id.iv_B_pei);
        iv_B_fu = (ImageView) findViewById(R.id.iv_B_fu);
        lv_A_baoyang = (ListView) findViewById(R.id.lv_B_baoyang);
        lv_A_baoyang.setFocusable(false);
        //        初始化首减，满减
        rela_shoujian = (RelativeLayout) findViewById(R.id.rela_shoujian);
        rela_manjian = (RelativeLayout) findViewById(R.id.rela_manjian);
        rela_B_shangmenfuwufei = (RelativeLayout) findViewById(R.id.rela_B_shangmenfuwufei);
        rela_B_callman = (RelativeLayout) findViewById(R.id.rela_B_callman);
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
        tv_B_youhuiquan = (TextView) findViewById(R.id.tv_B_youhuiquan);
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
        } else if (R.id.iv_B_call == id) {
            Call(phone);
        } else if (R.id.iv_B_callman == id) {
            Call(phoneman);
        } else if (R.id.tv_right == id){
            ShenQingQuXiao();
        } else if (R.id.iv_B_wodeweizhi == id){
            Intent intent = new Intent(this,LookLocationActivity.class);
            intent.putExtra("lat",lat);
            intent.putExtra("lon",lon);
            startActivity(intent);
        }
    }

    public void OrderDetail() {
        Utils.NoNet(context);
        OrderDetailReq req = new OrderDetailReq();
        req.code = "40022";
        req.id_order = KaKuApplication.id_orderB;
        KaKuApiProvider.OrderDetail(req, new KakuResponseListener<OrderDetailResp>(this, OrderDetailResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("orderdetail res: " + t.res);
                    if (Constants.RES.equals(t.res)) {

                        //tv_B_state.setText("待安装");

                        if (TextUtils.isEmpty(t.order.getRemark_order())) {
                            tv_B_remark.setVisibility(View.GONE);
                            view_remark.setVisibility(View.GONE);
                        } else {
                            tv_B_remark.setVisibility(View.VISIBLE);
                            view_remark.setVisibility(View.VISIBLE);
                        }

                        if ("N".equals(t.order.getType_service())) {
                            ll_arrival_addr.setVisibility(View.GONE);
                            view_arrival_addr.setVisibility(View.GONE);
                            rela_B_shangmenfuwufei.setVisibility(View.GONE);
                            view_shangmenfuwufei.setVisibility(View.GONE);
                            tv_B_daoche.setText("");
                        } else {
                            ll_arrival_addr.setVisibility(View.VISIBLE);
                            view_arrival_addr.setVisibility(View.VISIBLE);
                            rela_B_shangmenfuwufei.setVisibility(View.VISIBLE);
                            view_shangmenfuwufei.setVisibility(View.VISIBLE);
                            tv_B_daoche.setText("上门");
                        }
                        lat = t.order.getLat_service();
                        lon = t.order.getLon_service();
                        tv_B_dingdanhao.setText("订单号：" + t.order.getNo_order());
                        tv_B_shopname.setText(t.order.getName_shop());
                        tv_B_time.setText(t.order.getHour_shop_begin() + "-" + t.order.getHour_shop_end());
                        tv_B_point.setText(t.order.getNum_star());
                        tv_orderA_peoname.setText(t.order.getName_driver());
                        tv_orderA_peophone.setText(t.order.getPhone_driver());
                        String star = t.order.getNum_star();
                        float starFloat = Float.valueOf(star);
                        star_A_shop.setRating(starFloat);
                        tv_B_dan.setText("(" + t.order.getNum_order() + "单)");
                        tv_B_addr.setText(t.order.getAddr_shop());
                        tv_B_phone.setText(t.order.getMobile_shop());

                        phone = t.order.getMobile_shop();
                        //票
                        if ("Y".equals(t.order.getFlag_ticket())) {
                            iv_B_piao.setVisibility(View.VISIBLE);
                        } else {
                            iv_B_piao.setVisibility(View.GONE);
                        }
                        //保
                        if ("Y".equals(t.order.getFlag_assure())) {
                            iv_B_bao.setVisibility(View.VISIBLE);
                        } else {
                            iv_B_bao.setVisibility(View.GONE);
                        }
                        //赔
                        if ("Y".equals(t.order.getFlag_their())) {
                            iv_B_pei.setVisibility(View.VISIBLE);
                        } else {
                            iv_B_pei.setVisibility(View.GONE);
                        }
                        //付
                        if ("Y".equals(t.order.getFlag_pay())) {
                            iv_B_fu.setVisibility(View.VISIBLE);
                        } else {
                            iv_B_fu.setVisibility(View.GONE);
                        }
                        String substring1 = t.order.getTime_order().substring(0, 10);
                        String substring2 = t.order.getTime_order().substring(11, 19);
                        tv_B_yuyueshijian.setText(substring1 + "\n" + "     " + substring2);
                        tv_B_yuyuema.setText(t.order.getCode_order());
                        Bitmap creatMyCode = Utils.createQRCode(t.order.getCode_order());
                        iv_B_yuyuema.setImageBitmap(creatMyCode);
                        BitmapUtil.getInstance(context).download(iv_B_image, KaKuApplication.qian_zhui + t.order.getImage_shop());
                        //保养列表
                        list_baoyang = t.items;
                        adapter = new BaoYangAdapter(context, list_baoyang);
                        lv_A_baoyang.setAdapter(adapter);
                        setListViewHeightBasedOnChildren(lv_A_baoyang);
                        //支付方式
                        if ("0".equals(t.order.getType_pay())) {
                            tv_B_zhifufangshi.setText("微信支付");
                        } else if ("1".equals(t.order.getType_pay())) {
                            tv_B_zhifufangshi.setText("支付宝支付");
                        } else if ("2".equals(t.order.getType_pay())) {
                            tv_B_zhifufangshi.setText("网银支付");
                        } else if ("3".equals(t.order.getType_pay())) {
                            tv_B_zhifufangshi.setText("到店支付");
                        }
                        //发票信息
                        if (TextUtils.isEmpty(t.order.getVar_invoice())) {
                            tv_B_fapiaotaitou.setText("不开发票");
                        } else {
                            tv_B_fapiaotaitou.setText(t.order.getVar_invoice());
                        }
                        tv_B_remark.setText("备注：" + t.order.getRemark_order());
                        //积分
                        String point_order = t.order.getPoint_order();
                        float jifen = Float.parseFloat(point_order);
                        float jifen2 = (float) (Math.round(jifen)) / 100;
                        String s1 = "用" + point_order + "积分，抵¥" + jifen2;
                        SpannableStringBuilder style = new SpannableStringBuilder(s1);
                        style.setSpan(new ForegroundColorSpan(Color.rgb(209, 0, 0)), 5 + point_order.length(), s1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        //tv_B_jifen.setText(style);
                        tv_B_shangpinzonge.setText("¥" + t.order.getPrice_goods());
                        tv_B_daochefuwufei.setText("¥" + t.order.getPrice_visit());
                        //tv_B_jianjifen.setText("¥"+t.order.getPrice_point());
                        tv_B_jianyouhuiquan.setText("¥" + t.order.getPrice_coupon());
                        tv_B_shifukuan.setText("¥" + t.order.getPrice_order());
                        tv_B_ordertime.setText("下单日期：" + t.order.getTime_create());
                        if (TextUtils.isEmpty(t.shop_user.getName_user())) {
                            tv_B_zhipaiweixiugong.setText("未指派");
                            rela_B_callman.setVisibility(View.GONE);
                        } else {
                            tv_B_zhipaiweixiugong.setText(t.shop_user.getName_user());
                            rela_B_callman.setVisibility(View.VISIBLE);
                        }
                        tv_B_callman.setText(t.shop_user.getPhone_user());
                        phoneman = t.shop_user.getPhone_user();
                        tv_B_yuyuetime.setText(t.order.getJlyhsj());
                        tv_B_addrmy.setText(t.order.getAddr_service());
//初始化优惠券信息
                        if (TextUtils.isEmpty(t.order.getCoupon_content())) {
                            rela_coupon.setVisibility(View.GONE);
                            view_coupon.setVisibility(View.GONE);
                            rela_price_coupon.setVisibility(View.GONE);
                            view_price_coupon.setVisibility(View.GONE);
                            tv_B_youhuiquan.setText("");
                        } else {
                            rela_coupon.setVisibility(View.VISIBLE);
                            view_coupon.setVisibility(View.VISIBLE);
                            rela_price_coupon.setVisibility(View.VISIBLE);
                            view_price_coupon.setVisibility(View.VISIBLE);
                            tv_B_youhuiquan.setText(t.order.getCoupon_content());
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

    public void ShenQingQuXiao(){
        Intent intent = new Intent(context,QuXiaoXiangQingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
