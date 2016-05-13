package com.yichang.kaku.home.baoyang;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.BigOilObj;
import com.yichang.kaku.request.BaoYangListReq;
import com.yichang.kaku.response.BaoYangListResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class BaoYangListActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private ListView lv_baoyanglist1, lv_baoyanglist2, lv_baoyanglist3;
    private ImageView iv_byshopdetail_mffw, iv_byshopdetail_cu, iv_byshopdetail_shop, iv_byshopdetail_call,
            iv_baoyang_shanchujiyou, iv_byshopdetail_huanlvxin;
    private TextView tv_byshopdetail_name, tv_byshopdetail_star, tv_byshopdetail_dan, tv_byshopdetail_juli, tv_byshopdetail_cu,
            tv_baoyanglist_money, tv_byshopdetail_addr, tv_byshopdetail_time;
    private List<BigOilObj> list1 = new ArrayList<>();
    private List<BigOilObj> list2 = new ArrayList<>();
    private List<BigOilObj> list3 = new ArrayList<>();
    private RelativeLayout rela_baoyanglist_jiesuan;
    private RatingBar star_byshopdetail;
    private LinearLayout line_baoyanglist_call;
    private View view_byshopdetail;
    private String phone, id_items = "", num_items = "";
    private float total1, total2, total3, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baoyanglist);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("保养方案");
        tv_byshopdetail_name = (TextView) findViewById(R.id.tv_byshopdetail_name);
        tv_byshopdetail_dan = (TextView) findViewById(R.id.tv_byshopdetail_dan);
        tv_byshopdetail_juli = (TextView) findViewById(R.id.tv_byshopdetail_juli);
        tv_byshopdetail_cu = (TextView) findViewById(R.id.tv_byshopdetail_cu);
        tv_byshopdetail_addr = (TextView) findViewById(R.id.tv_byshopdetail_addr);
        tv_byshopdetail_time = (TextView) findViewById(R.id.tv_byshopdetail_time);
        tv_byshopdetail_star = (TextView) findViewById(R.id.tv_byshopdetail_star);
        tv_baoyanglist_money = (TextView) findViewById(R.id.tv_baoyanglist_money);
        iv_byshopdetail_mffw = (ImageView) findViewById(R.id.iv_byshopdetail_mffw);
        iv_byshopdetail_cu = (ImageView) findViewById(R.id.iv_byshopdetail_cu);
        iv_baoyang_shanchujiyou = (ImageView) findViewById(R.id.iv_baoyang_shanchujiyou);
        iv_baoyang_shanchujiyou.setOnClickListener(this);
        iv_byshopdetail_huanlvxin = (ImageView) findViewById(R.id.iv_byshopdetail_huanlvxin);
        iv_byshopdetail_huanlvxin.setOnClickListener(this);
        iv_byshopdetail_call = (ImageView) findViewById(R.id.iv_byshopdetail_call);
        iv_byshopdetail_call.setOnClickListener(this);
        iv_byshopdetail_shop = (ImageView) findViewById(R.id.iv_byshopdetail_shop);
        star_byshopdetail = (RatingBar) findViewById(R.id.star_byshopdetail);
        view_byshopdetail = findViewById(R.id.view_byshopdetail);
        lv_baoyanglist1 = (ListView) findViewById(R.id.lv_baoyanglist1);
        lv_baoyanglist2 = (ListView) findViewById(R.id.lv_baoyanglist2);
        lv_baoyanglist3 = (ListView) findViewById(R.id.lv_baoyanglist3);
        rela_baoyanglist_jiesuan = (RelativeLayout) findViewById(R.id.rela_baoyanglist_jiesuan);
        rela_baoyanglist_jiesuan.setOnClickListener(this);
        line_baoyanglist_call = (LinearLayout) findViewById(R.id.line_baoyanglist_call);
        line_baoyanglist_call.setOnClickListener(this);
        GetList();
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
        } else if (R.id.rela_baoyanglist_jiesuan == id) {
            id_items = "";
            num_items = "";
            for (int i = 0; i < list1.size(); i++) {
                id_items += list1.get(i).getId_item() + ',';
                num_items += list1.get(i).getNum_item() + ',';
            }
            for (int i = 0; i < list2.size(); i++) {
                id_items += list2.get(i).getId_item() + ',';
                num_items += list2.get(i).getNum_item() + ',';
            }
            for (int i = 0; i < list3.size(); i++) {
                id_items += list3.get(i).getId_item() + ',';
                num_items += list3.get(i).getNum_item() + ',';
            }

            int num_total = 0;
            for (int i = 0; i < list1.size(); i++) {
                num_total += Integer.parseInt(list1.get(i).getNum_item());
            }
            for (int i = 0; i < list2.size(); i++) {
                num_total += Integer.parseInt(list2.get(i).getNum_item());
            }

            if (num_total == 0) {
                LogUtil.showShortToast(context, "请至少选择一种油品");
                return;
            }
            KaKuApplication.id_upkeep_coupon = "";
            Intent intent = new Intent(this, BaoYangOrderActivity.class);
            intent.putExtra("id_items", id_items);
            intent.putExtra("num_items", num_items);
            startActivity(intent);
        } else if (R.id.line_baoyanglist_call == id) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Constants.PHONE_KAKU));
            context.startActivity(intent);
        } else if (R.id.iv_byshopdetail_call == id) {
            Utils.Call(BaoYangListActivity.this, phone);
        } else if (R.id.iv_baoyang_shanchujiyou == id) {
            list3.clear();
            iv_baoyang_shanchujiyou.setVisibility(View.GONE);
            BaoYangAdapter adapter3 = new BaoYangAdapter(context, list3);
            adapter3.setButton(new BaoYangAdapter.BaoYangButton() {
                @Override
                public void Jia() {
                    Total();
                }

                @Override
                public void Jian() {
                    Total();
                }

            });
            Utils.setListViewHeightBasedOnChildren(lv_baoyanglist3);
            lv_baoyanglist3.setAdapter(adapter3);
            Total();
            KaKuApplication.id_item_lv = "";
        } else if (R.id.iv_byshopdetail_huanlvxin == id) {
            Intent intent = new Intent(this, BaoYangChooseFilterActivity.class);
            startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String type = data.getStringExtra("type");
        if ("0".equals(type)) {
            BigOilObj obj = (BigOilObj) data.getSerializableExtra("obj");
            switch (requestCode) {
                case 0:
                    list3.clear();
                    list3.add(obj);
                    lv_baoyanglist3.setVisibility(View.VISIBLE);
                    BaoYangAdapter adapter3 = new BaoYangAdapter(context, list3);
                    adapter3.setButton(new BaoYangAdapter.BaoYangButton() {
                        @Override
                        public void Jia() {
                            Total();
                        }

                        @Override
                        public void Jian() {
                            Total();
                        }

                    });
                    Utils.setListViewHeightBasedOnChildren(lv_baoyanglist3);
                    lv_baoyanglist3.setAdapter(adapter3);
                    iv_baoyang_shanchujiyou.setVisibility(View.VISIBLE);
                    Total();
                    break;
                default:
                    break;
            }

        }
    }

    public void GetList() {
        showProgressDialog();
        BaoYangListReq req = new BaoYangListReq();
        req.code = "400113";
        req.id_shop = KaKuApplication.id_baoyangshop;
        req.id_item = KaKuApplication.id_item;
        req.var_lat = Utils.getLat();
        req.var_lon = Utils.getLon();
        KaKuApiProvider.BaoYangShopDetail(req, new KakuResponseListener<BaoYangListResp>(this, BaoYangListResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("baoyanglist res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        SetText(t);
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
            public void onFailed(int i, Response response) {

            }

        });
    }

    public void SetText(BaoYangListResp t) {
        phone = t.shop.getTel_shop();
        tv_byshopdetail_name.setText(t.shop.getName_shop());
        float starFloat1 = Float.valueOf(t.shop.getNum_star());
        star_byshopdetail.setRating(starFloat1);
        tv_byshopdetail_star.setText(starFloat1 + "");
        tv_byshopdetail_dan.setText(t.shop.getNum_bill() + "单");
        tv_byshopdetail_juli.setText(t.shop.getDistance() + "km");
        tv_byshopdetail_cu.setText(t.shop.getActivity_shop_content());
        if (TextUtils.isEmpty(t.shop.getActivity_shop_content())) {
            iv_byshopdetail_cu.setVisibility(View.GONE);
        } else {
            iv_byshopdetail_cu.setVisibility(View.VISIBLE);
        }
        tv_byshopdetail_addr.setText(t.shop.getAddr_shop());
        tv_byshopdetail_time.setText("营业时间：" + t.shop.getHour_shop_begin() + "~" + t.shop.getHour_shop_end());
        ShowPrice(t.price_goods);
        BitmapUtil.getInstance(context).download(iv_byshopdetail_shop, t.shop.getImage_shop());
        if ("".equals(t.shop.getActivity_shop_content())) {
            view_byshopdetail.setVisibility(View.GONE);
            view_byshopdetail.setVisibility(View.GONE);
        } else {
            view_byshopdetail.setVisibility(View.VISIBLE);
            view_byshopdetail.setVisibility(View.VISIBLE);
        }

        list1.clear();
        list2.clear();
        list3.clear();
        list1 = t.item_oil_big;
        list2 = t.item_oil_small;
        list3 = t.item_filter;

        if (list2.size() == 0) {
            lv_baoyanglist2.setVisibility(View.GONE);
        }

        if (list3.size() == 0) {
            KaKuApplication.id_item_lv = "";
            lv_baoyanglist3.setVisibility(View.GONE);
            iv_baoyang_shanchujiyou.setVisibility(View.GONE);
        } else {
            KaKuApplication.id_item_lv = list3.get(0).getId_item();
            iv_baoyang_shanchujiyou.setVisibility(View.VISIBLE);
        }


        BaoYangAdapter adapter1 = new BaoYangAdapter(context, list1);
        adapter1.setButton(new BaoYangAdapter.BaoYangButton() {
            @Override
            public void Jia() {
                Total();
            }

            @Override
            public void Jian() {
                Total();
            }

        });
        Utils.setListViewHeightBasedOnChildren(lv_baoyanglist1);
        lv_baoyanglist1.setAdapter(adapter1);

        BaoYangAdapter adapter2 = new BaoYangAdapter(context, list2);
        adapter2.setButton(new BaoYangAdapter.BaoYangButton() {
            @Override
            public void Jia() {
                Total();
            }

            @Override
            public void Jian() {
                Total();
            }

        });
        Utils.setListViewHeightBasedOnChildren(lv_baoyanglist2);
        lv_baoyanglist2.setAdapter(adapter2);

        BaoYangAdapter adapter3 = new BaoYangAdapter(context, list3);
        adapter3.setButton(new BaoYangAdapter.BaoYangButton() {
            @Override
            public void Jia() {
                Total();
            }

            @Override
            public void Jian() {
                Total();
            }

        });
        Utils.setListViewHeightBasedOnChildren(lv_baoyanglist3);
        lv_baoyanglist3.setAdapter(adapter3);
    }

    public void Total() {
        total1 = 0.0f;
        total2 = 0.0f;
        total3 = 0.0f;
        for (int i = 0; i < list1.size(); i++) {
            total1 = Float.parseFloat(list1.get(i).getPrice_item()) * Float.parseFloat(list1.get(i).getNum_item());
        }
        for (int i = 0; i < list2.size(); i++) {
            total2 = Float.parseFloat(list2.get(i).getPrice_item()) * Float.parseFloat(list2.get(i).getNum_item());
        }
        for (int i = 0; i < list3.size(); i++) {
            total3 = Float.parseFloat(list3.get(i).getPrice_item()) * Float.parseFloat(list3.get(i).getNum_item());
        }
        total = total1 + total2 + total3;
        ShowPrice(total + "");
        KaKuApplication.baoyang_money = total;
    }

    public void ShowPrice(String price) {
        String stringss = "合计：" + price + "元";
        SpannableStringBuilder styless = new SpannableStringBuilder(stringss);
        styless.setSpan(new ForegroundColorSpan(Color.rgb(225, 0, 0)), 3, stringss.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_baoyanglist_money.setText(styless);
    }

}
