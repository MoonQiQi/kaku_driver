package com.yichang.kaku.home.shop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.dingwei.DaoHangActivity;
import com.yichang.kaku.obj.ShopService2Obj;
import com.yichang.kaku.obj.ShopServiceObj;
import com.yichang.kaku.request.ShopDetailReq;
import com.yichang.kaku.response.ShopDetailResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class ShopDetailActivity extends BaseActivity implements OnClickListener {

    private TextView left, title;
    private ImageView iv_shopdetail_image, iv_shopdetail_daohang, iv_shopdetail_phone, iv_shopdetail_shopname;
    private TextView tv_shopdetail_shopname, tv_shopdetail_shoptime, tv_shopdetail_addr, tv_shopdetail_more,
            tv_shopdetail_pingjianum, tv_shopdingdan, tv_shoppingjia, tv_shopdetail_title;
    private RatingBar star_shopdetail;
    private String lat, lon;
    private String phone_shop;
    private RelativeLayout rela_shopdetail_pingjia;
    private ListView lv_shopdetail_one, lv_shopdetail_two;
    private List<ShopServiceObj> list_one = new ArrayList<>();
    private List<ShopService2Obj> list_two = new ArrayList<>();
    private ShopDetailOneAdapter adapter_one;
    private ShopDetailTwoAdapter adapter_two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopdetail);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub

        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("店铺详情");
        iv_shopdetail_image = (ImageView) findViewById(R.id.iv_shopdetail_image);
        iv_shopdetail_shopname = (ImageView) findViewById(R.id.iv_shopdetail_shopname);
        iv_shopdetail_daohang = (ImageView) findViewById(R.id.iv_shopdetail_daohang);
        iv_shopdetail_daohang.setOnClickListener(this);
        iv_shopdetail_phone = (ImageView) findViewById(R.id.iv_shopdetail_phone);
        iv_shopdetail_phone.setOnClickListener(this);
        tv_shopdetail_shopname = (TextView) findViewById(R.id.tv_shopdetail_shopname);
        tv_shopdetail_title = (TextView) findViewById(R.id.tv_shopdetail_title);
        tv_shopdetail_shoptime = (TextView) findViewById(R.id.tv_shopdetail_shoptime);
        tv_shopdetail_pingjianum = (TextView) findViewById(R.id.tv_shopdetail_pingjianum);
        tv_shopdingdan = (TextView) findViewById(R.id.tv_shopdingdan);
        tv_shoppingjia = (TextView) findViewById(R.id.tv_shoppingjia);
        tv_shopdetail_addr = (TextView) findViewById(R.id.tv_shopdetail_addr);
        star_shopdetail = (RatingBar) findViewById(R.id.star_shopdetail);
        rela_shopdetail_pingjia = (RelativeLayout) findViewById(R.id.rela_shopdetail_pingjia);
        rela_shopdetail_pingjia.setOnClickListener(this);
        lv_shopdetail_two = (ListView) findViewById(R.id.lv_shopdetail_two);
        lv_shopdetail_one = (ListView) findViewById(R.id.lv_shopdetail_one);
        lv_shopdetail_one.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter_one = new ShopDetailOneAdapter(context, list_one, position);
                lv_shopdetail_one.setAdapter(adapter_one);

                tv_shopdetail_title.setText(list_one.get(position).getName_service());
                list_two = list_one.get(position).getService_list();
                adapter_two = new ShopDetailTwoAdapter(context, list_two);
                lv_shopdetail_two.setAdapter(adapter_two);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        ShopDetail();
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
        } else if (R.id.iv_shopdetail_daohang == id) {
            Intent intent = new Intent(this, DaoHangActivity.class);
            intent.putExtra("e_lat", lat);
            intent.putExtra("e_lon", lon);
            startActivity(intent);
        } else if (R.id.iv_shopdetail_phone == id) {
            Utils.Call(ShopDetailActivity.this, phone_shop);
        } else if (R.id.rela_shopdetail_pingjia == id) {
            startActivity(new Intent(this, ShopPingJiaListActivity.class));
        }
    }

    public void ShopDetail() {
        showProgressDialog();
        ShopDetailReq req = new ShopDetailReq();
        req.code = "8005";
        req.id_shop = KaKuApplication.id_shop;
        KaKuApiProvider.ShopDetail(req, new KakuResponseListener<ShopDetailResp>(this, ShopDetailResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("shopdetail res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        SetText(t);
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

    public void SetText(ShopDetailResp t) {
        if ("0".equals(t.shop.getFlag_type())) {
            iv_shopdetail_shopname.setImageResource(R.drawable.fuwuzhanxiao);
        } else if ("9".equals(t.shop.getFlag_type())) {
            iv_shopdetail_shopname.setImageResource(R.drawable.shopdetailkxd);
        } else if ("8".equals(t.shop.getFlag_type())) {
            iv_shopdetail_shopname.setImageResource(R.drawable.shopdetailyhd);
        } else if ("7".equals(t.shop.getFlag_type())) {
            iv_shopdetail_shopname.setImageResource(R.drawable.shopdetailwsd);
        }

        tv_shopdetail_shopname.setText(t.shop.getName_shop());
        tv_shopdetail_shoptime.setText("营业时间：" + t.shop.getHour_shop_begin() + "-" + t.shop.getHour_shop_end());
        tv_shopdetail_addr.setText(t.shop.getAddr_shop());
        tv_shopdingdan.setText("总订单  " + t.shop.getNum_bill());
        if ("0".equals(t.shop.getNum_eval())) {
            tv_shopdetail_pingjianum.setText("暂无评价");
        } else {
            String s = t.shop.getNum_eval() + "条评价";
            SpannableStringBuilder styless = new SpannableStringBuilder(s);
            styless.setSpan(new ForegroundColorSpan(Color.rgb(209, 0, 0)), 0, s.length() - 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_shopdetail_pingjianum.setText(styless);
        }
        lat = t.shop.getVar_lat();
        lon = t.shop.getVar_lon();
        phone_shop = t.shop.getMobile_shop();

        String star1 = t.shop.getNum_star();
        tv_shoppingjia.setText(star1);
        if (!TextUtils.isEmpty(star1)) {
            float starFloat1 = Float.valueOf(star1);
            star_shopdetail.setRating(starFloat1);
        }
        BitmapUtil.getInstance(context).download(iv_shopdetail_image, t.shop.getImage_shop());

        DisplayMetrics outMetrics = new DisplayMetrics();
        ShopDetailActivity.this.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        double anInt;
        anInt = (outMetrics.widthPixels);
        FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams((int) anInt, (int) anInt);
        iv_shopdetail_image.setLayoutParams(params1);

        list_one = t.services;
        adapter_one = new ShopDetailOneAdapter(context, list_one, 0);
        lv_shopdetail_one.setAdapter(adapter_one);

        tv_shopdetail_title.setText(list_one.get(0).getName_service());
        list_two = list_one.get(0).getService_list();
        adapter_two = new ShopDetailTwoAdapter(context, list_two);
        lv_shopdetail_two.setAdapter(adapter_two);

    }

}
