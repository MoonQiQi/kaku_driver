package com.yichang.kaku.home.baoyang;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MyActivityManager;
import com.yichang.kaku.home.giftmall.sliding.ProductDetailScrollViewPage;
import com.yichang.kaku.home.giftmall.sliding.ProductDetailSlidingMenu;
import com.yichang.kaku.obj.AreaObj;
import com.yichang.kaku.obj.BaoYangObj;
import com.yichang.kaku.request.AreaReq;
import com.yichang.kaku.request.BaoYangDetailReq;
import com.yichang.kaku.response.AreaResp;
import com.yichang.kaku.response.BaoYangBuyResp;
import com.yichang.kaku.response.BaoYangDetailResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.DensityUtils;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yichang.kaku.zhaohuo.LineGridView;
import com.yichang.kaku.zhaohuo.province.CityAdapter;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class BaoYangDetailActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private ImageView iv_baoyangdetail_image, iv_baoyangdetail_chamengban, iv_baoyangdetail_mengbanimage;
    private ProductDetailSlidingMenu productDetailSlidingMenu;
    private ProductDetailScrollViewPage productDetailScrollViewPage;
    private TextView tv_baoyangdetail_pay, tv_baoyangdetail_name, tv_baoyangdetail_remark, tv_baoyangdetail_price,
            tv_baoyangdetail_yuanjia, tv_baoyangdetail_taocan, tv_baoyangdetail_area, tv_baoyangdetail_pay2,
            tv_baoyangdetail_pricemengban;
    private RelativeLayout rela_baoyang_taocan, rela_baoyang_area, rela_zhaohuo_grid, rela_baoyangdetail_taocanmengban;
    private TextView tv_pup_left, tv_pup_mid;
    private String id_type = "province";
    private String area_name1, area_name2, area_name3;
    private GridView gv_city;
    private String id_province, id_city, id_county;
    private List<AreaObj> list_province = new ArrayList<AreaObj>();
    private CityAdapter adapter;
    private ListView lv_baoyangdetail_taocan;
    private List<BaoYangObj> list_baoyangtaocan = new ArrayList<>();
    private LinearLayout line_baoyangdetail_call;
    private BaoYangTaoCanAdapter adapter_baoyang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baoyangdetail);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        KaKuApplication.id_upkeep_picture = "";
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("商品详情");
        iv_baoyangdetail_image = (ImageView) findViewById(R.id.iv_baoyangdetail_image);
        iv_baoyangdetail_mengbanimage = (ImageView) findViewById(R.id.iv_baoyangdetail_mengbanimage);
        iv_baoyangdetail_chamengban = (ImageView) findViewById(R.id.iv_baoyangdetail_chamengban);
        iv_baoyangdetail_chamengban.setOnClickListener(this);
        tv_baoyangdetail_name = (TextView) findViewById(R.id.tv_baoyangdetail_name);
        tv_baoyangdetail_remark = (TextView) findViewById(R.id.tv_baoyangdetail_remark);
        tv_baoyangdetail_price = (TextView) findViewById(R.id.tv_baoyangdetail_price);
        tv_baoyangdetail_yuanjia = (TextView) findViewById(R.id.tv_baoyangdetail_yuanjia);
        tv_baoyangdetail_taocan = (TextView) findViewById(R.id.tv_baoyangdetail_taocan);
        tv_baoyangdetail_pricemengban = (TextView) findViewById(R.id.tv_baoyangdetail_pricemengban);
        tv_baoyangdetail_area = (TextView) findViewById(R.id.tv_baoyangdetail_area);
        tv_baoyangdetail_pay = (TextView) findViewById(R.id.tv_baoyangdetail_pay);
        tv_baoyangdetail_pay.setOnClickListener(this);
        tv_baoyangdetail_pay2 = (TextView) findViewById(R.id.tv_baoyangdetail_pay2);
        tv_baoyangdetail_pay2.setOnClickListener(this);
        lv_baoyangdetail_taocan = (ListView) findViewById(R.id.lv_baoyangdetail_taocan);
        lv_baoyangdetail_taocan.setOnItemClickListener(this);
        tv_pup_mid = (TextView) findViewById(R.id.tv_pup_mid);
        tv_pup_left = (TextView) findViewById(R.id.tv_pup_left);
        tv_pup_left.setOnClickListener(this);
        gv_city = (LineGridView) findViewById(R.id.gv_city);
        gv_city.setOnItemClickListener(this);
        rela_baoyang_taocan = (RelativeLayout) findViewById(R.id.rela_baoyang_taocan);
        rela_baoyang_taocan.setOnClickListener(this);
        rela_baoyangdetail_taocanmengban = (RelativeLayout) findViewById(R.id.rela_baoyangdetail_taocanmengban);
        rela_baoyangdetail_taocanmengban.setOnClickListener(this);
        line_baoyangdetail_call = (LinearLayout) findViewById(R.id.line_baoyangdetail_call);
        line_baoyangdetail_call.setOnClickListener(this);
        rela_baoyang_area = (RelativeLayout) findViewById(R.id.rela_baoyang_area);
        rela_baoyang_area.setOnClickListener(this);
        rela_zhaohuo_grid = (RelativeLayout) findViewById(R.id.rela_zhaohuo_grid);
        rela_zhaohuo_grid.setOnClickListener(this);
        rela_zhaohuo_grid.setVisibility(View.GONE);
        productDetailScrollViewPage = findView(R.id.ysnowScrollViewPageOne);
        productDetailScrollViewPage.setScreenHeight(100);

        productDetailSlidingMenu = (ProductDetailSlidingMenu) findViewById(R.id.expanded_menu);
        productDetailSlidingMenu.setScreenHeight(100);
        BaoYangDetail();
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
        } else if (R.id.tv_baoyangdetail_pay == id || R.id.tv_baoyangdetail_pay2 == id) {
            MobclickAgent.onEvent(context, "BaoYangBuy");
            Buy();
        } else if (R.id.rela_baoyang_taocan == id) {
            rela_baoyangdetail_taocanmengban.setVisibility(View.VISIBLE);
            for (int i = 0; i < list_baoyangtaocan.size(); i++) {
                if (KaKuApplication.id_baoyang.equals(list_baoyangtaocan.get(i).getId_upkeep())) {
                    list_baoyangtaocan.get(i).setColor("R");
                }
            }
        } else if (R.id.rela_baoyang_area == id) {
            tv_pup_mid.setText("中国");
            GetProvince();
        } else if (R.id.rela_zhaohuo_grid == id) {
            rela_zhaohuo_grid.setVisibility(View.GONE);
            id_type = "province";
            tv_pup_mid.setText("中国");
        } else if (R.id.tv_pup_left == id) {
            rela_zhaohuo_grid.setVisibility(View.GONE);
            id_type = "province";
            tv_pup_mid.setText("中国");
        } else if (R.id.iv_baoyangdetail_chamengban == id) {
            rela_baoyangdetail_taocanmengban.setVisibility(View.GONE);
        } else if (R.id.line_baoyangdetail_call == id) {
            Utils.Call(this, "4006867585");
        }
    }

    public void BaoYangDetail() {
        showProgressDialog();
        BaoYangDetailReq req = new BaoYangDetailReq();
        req.code = "40052";
        req.id_upkeep = KaKuApplication.id_baoyang;
        KaKuApiProvider.BaoYangDetail(req, new KakuResponseListener<BaoYangDetailResp>(this, BaoYangDetailResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("baoyangdetail res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        SetText(t.upkeep);
                        list_baoyangtaocan = t.upkeeps;
                        adapter_baoyang = new BaoYangTaoCanAdapter(context, list_baoyangtaocan);
                        lv_baoyangdetail_taocan.setAdapter(adapter_baoyang);
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

    public void Buy() {
        showProgressDialog();
        BaoYangDetailReq req = new BaoYangDetailReq();
        req.code = "40053";
        req.id_upkeep = KaKuApplication.id_baoyang;
        KaKuApiProvider.OrderSure(req, new KakuResponseListener<BaoYangBuyResp>(this, BaoYangBuyResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("baoyangbuy res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        rela_baoyangdetail_taocanmengban.setVisibility(View.GONE);
                        Intent intent = new Intent(BaoYangDetailActivity.this, BaoYangOrderActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("t", t);
                        intent.putExtras(bundle);
                        startActivity(intent);
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

    public void SetText(BaoYangObj obj) {
        String string_price = "¥ " + obj.getPrice_goods();
        SpannableStringBuilder style_price = new SpannableStringBuilder(string_price);
        style_price.setSpan(new AbsoluteSizeSpan(20, true), 1, string_price.length() - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_baoyangdetail_price.setText(style_price);
        tv_baoyangdetail_name.setText(obj.getName_goods());
        tv_baoyangdetail_remark.setText(obj.getRemark_goods());
        tv_baoyangdetail_yuanjia.setText(obj.getPrice_goods_line());
        tv_baoyangdetail_taocan.setText(obj.getRemark_goods());
        String string_pricemeng = "¥ " + obj.getPrice_goods();
        SpannableStringBuilder style_pricemeng = new SpannableStringBuilder(string_pricemeng);
        style_pricemeng.setSpan(new AbsoluteSizeSpan(20, true), 1, string_pricemeng.length() - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_baoyangdetail_pricemengban.setText(style_pricemeng);
        BitmapUtil.getInstance(BaoYangDetailActivity.this).download(iv_baoyangdetail_mengbanimage, KaKuApplication.qian_zhui + obj.getImage_goods());
        BitmapUtil.getInstance(context).download(iv_baoyangdetail_image, KaKuApplication.qian_zhui + obj.getImage_goods());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                productDetailSlidingMenu.smoothScrollTo(0, -DensityUtils.dp2px(context, 50) - DensityUtils.getStatusHeight(MyActivityManager.getInstance().getActivty()));
            }
        }, 200);

        productDetailSlidingMenu.setWebUrl(obj.getUrl_goods());
    }

    public void GetProvince() {
        showProgressDialog();
        AreaReq req = new AreaReq();
        req.code = "10018";
        req.id_area = "0";
        KaKuApiProvider.Area(req, new KakuResponseListener<AreaResp>(context, AreaResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("area res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        list_province = t.areas;
                        adapter = new CityAdapter(context, list_province);
                        gv_city.setAdapter(adapter);
                        rela_zhaohuo_grid.setVisibility(View.VISIBLE);
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

    public void GetCity(String id_province) {
        showProgressDialog();
        AreaReq req = new AreaReq();
        req.code = "10018";
        req.id_area = id_province;
        KaKuApiProvider.Area(req, new KakuResponseListener<AreaResp>(context, AreaResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("area res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        list_province = t.areas;
                        adapter = new CityAdapter(context, list_province);
                        gv_city.setAdapter(adapter);
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

    public void GetCounty(String id_city) {
        showProgressDialog();
        AreaReq req = new AreaReq();
        req.code = "10018";
        req.id_area = id_city;
        KaKuApiProvider.Area(req, new KakuResponseListener<AreaResp>(context, AreaResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("area res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        list_province = t.areas;
                        adapter = new CityAdapter(context, list_province);
                        gv_city.setAdapter(adapter);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int idParent = parent.getId();
        if (R.id.gv_city == idParent) {
            if ("province".equals(id_type)) {
                id_province = list_province.get(position).getId_area();
                tv_pup_mid.setText(list_province.get(position).getName_area());
                GetCity(id_province);
                id_type = "city";
                area_name1 = list_province.get(position).getName_area();
            } else if ("city".equals(id_type)) {
                id_city = list_province.get(position).getId_area();
                tv_pup_mid.setText(list_province.get(position).getName_area());
                GetCounty(id_city);
                id_type = "county";
                area_name2 = list_province.get(position).getName_area();
            } else if ("county".equals(id_type)) {
                id_county = list_province.get(position).getId_area();
                tv_pup_mid.setText(list_province.get(position).getName_area());
                id_type = "province";
                rela_zhaohuo_grid.setVisibility(View.GONE);
                area_name3 = list_province.get(position).getName_area();
                tv_baoyangdetail_area.setText(area_name1 + area_name2 + area_name3);
            }
        } else if (R.id.lv_baoyangdetail_taocan == idParent) {
            for (int i = 0; i < list_baoyangtaocan.size(); i++) {
                list_baoyangtaocan.get(i).setColor("B");
            }
            KaKuApplication.id_baoyang = list_baoyangtaocan.get(position).getId_upkeep();
            list_baoyangtaocan.get(position).setColor("R");
            tv_baoyangdetail_pricemengban.setText("¥ " + list_baoyangtaocan.get(position).getPrice_goods());
            BitmapUtil.getInstance(BaoYangDetailActivity.this).download(iv_baoyangdetail_mengbanimage, KaKuApplication.qian_zhui + list_baoyangtaocan.get(position).getImage_goods());
            SetText(list_baoyangtaocan.get(position));
            adapter_baoyang.notifyDataSetChanged();
            lv_baoyangdetail_taocan.setAdapter(adapter_baoyang);
        }
    }
}
