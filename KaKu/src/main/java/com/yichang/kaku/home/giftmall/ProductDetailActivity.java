package com.yichang.kaku.home.giftmall;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MyActivityManager;
import com.yichang.kaku.home.ad.StickerOrderActivity;
import com.yichang.kaku.home.giftmall.sliding.ProductDetailLinearList;
import com.yichang.kaku.home.giftmall.sliding.ProductDetailScrollViewPage;
import com.yichang.kaku.home.giftmall.sliding.ProductDetailSlidingMenu;
import com.yichang.kaku.obj.GoodsModelObj;
import com.yichang.kaku.obj.ShopMallProductDetailObj;
import com.yichang.kaku.request.ProductDetailInfoReq;
import com.yichang.kaku.request.QueHuoReq;
import com.yichang.kaku.request.ShopMallAdd2CartReq;
import com.yichang.kaku.response.ExitResp;
import com.yichang.kaku.response.ProductDetailInfoResp;
import com.yichang.kaku.response.ShopMallAdd2CartResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.DensityUtils;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {
    //titleBar,返回，购物车，标题
    private TextView left, title;
    private ProductDetailSlidingMenu productDetailSlidingMenu;
    private WebView wv_product_detail;
    private TextView tv_bottombar_pay;

    private LinearLayout ll_product_content;
    private LinearLayout ll_product_web;
    //原生页面商品详情
    private ImageView iv_product_image, iv_product_que1, iv_product_que2;
    private TextView tv_product_title;
    private TextView tv_product_price, tv_num_exchange, tv_num_evals;
    private ProductDetailLinearList lv_comment_list;

    private String detailUrl = "";
    private TextView goodsNum;

    private TextView tv_comment_more;

    private LinearLayout ll_service, ll_shopcar;
    private ProductDetailScrollViewPage productDetailScrollViewPage;
    private RelativeLayout rootView, rela_productdetail_taocanmengban, rela_baoyang_taocan;
    private GridView lv_productdetail_taocan;
    private TextView tv_product_title_sub, tv_giftmalldetail_price, tv_giftmalldetail_xunzhang, tv_productdetail_taocanzi,
            tv_productdetail_pricemengban, tv_productdetail_pay2, tv_productdetail_xuanzezi, tv_productdetail_taocan;
    private String flag_shopcar;
    private ImageView iv_xunzhang, iv_jifenhuangou, iv_productdetail_mengbanimage, iv_productdetail_chamengban;
    private String num_goodsmodel, productdetail_taocanzi, image_taocan, productdetail_pricemengban;
    private List<GoodsModelObj> list_goodsmodel = new ArrayList<>();
    private ProductModelAdapter adapter;
    private String type_pay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init() {
        initTitleBar();
        rootView = findView(R.id.rootView);

        goodsNum = findView(R.id.num);
        productDetailScrollViewPage = findView(R.id.ysnowScrollViewPageOne);
        productDetailScrollViewPage.setScreenHeight(100);

        productDetailSlidingMenu = (ProductDetailSlidingMenu) findViewById(R.id.expanded_menu);
        productDetailSlidingMenu.setScreenHeight(100);

        tv_bottombar_pay = (TextView) findViewById(R.id.tv_bottombar_pay);
        tv_bottombar_pay.setOnClickListener(this);
        wv_product_detail = (WebView) findViewById(R.id.wv_product_detail);
        iv_xunzhang = (ImageView) findViewById(R.id.iv_xunzhang);
        iv_productdetail_chamengban = (ImageView) findViewById(R.id.iv_productdetail_chamengban);
        iv_productdetail_chamengban.setOnClickListener(this);
        tv_productdetail_pay2 = (TextView) findViewById(R.id.tv_productdetail_pay2);
        tv_productdetail_pay2.setOnClickListener(this);
        tv_productdetail_xuanzezi = (TextView) findViewById(R.id.tv_productdetail_xuanzezi);
        tv_productdetail_taocan = (TextView) findViewById(R.id.tv_productdetail_taocan);
        tv_comment_more = (TextView) findViewById(R.id.tv_comment_more);
        tv_comment_more.setOnClickListener(this);
        tv_productdetail_pricemengban = (TextView) findViewById(R.id.tv_productdetail_pricemengban);
        tv_giftmalldetail_xunzhang = (TextView) findViewById(R.id.tv_giftmalldetail_xunzhang);
        tv_giftmalldetail_xunzhang.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_giftmalldetail_xunzhang.setOnClickListener(this);

        ll_service = (LinearLayout) findViewById(R.id.ll_service);
        ll_service.setOnClickListener(this);
        ll_shopcar = (LinearLayout) findViewById(R.id.ll_shopcar);
        ll_shopcar.setOnClickListener(this);
        initProductDetailInfo();

        getProductDetailInfo();

        ll_product_web = (LinearLayout) findViewById(R.id.ll_product_content);
        ll_product_content = (LinearLayout) findViewById(R.id.ll_product_content);
        tv_product_title_sub = (TextView) findViewById(R.id.tv_product_title_sub);
        rela_productdetail_taocanmengban = (RelativeLayout) findViewById(R.id.rela_productdetail_taocanmengban);
        rela_productdetail_taocanmengban.setOnClickListener(this);
        rela_baoyang_taocan = (RelativeLayout) findViewById(R.id.rela_baoyang_taocan);
        rela_baoyang_taocan.setOnClickListener(this);
    }

    private void initProductDetailInfo() {
        iv_product_image = (ImageView) findViewById(R.id.iv_product_image);
        iv_product_que1 = (ImageView) findViewById(R.id.iv_product_que1);
        iv_product_que2 = (ImageView) findViewById(R.id.iv_product_que2);
        iv_jifenhuangou = (ImageView) findViewById(R.id.iv_jifenhuangou);
        iv_productdetail_mengbanimage = (ImageView) findViewById(R.id.iv_productdetail_mengbanimage);
        tv_product_title = (TextView) findViewById(R.id.tv_product_title);
        tv_product_price = (TextView) findViewById(R.id.tv_product_price);
        tv_productdetail_taocanzi = (TextView) findViewById(R.id.tv_productdetail_taocanzi);
        tv_num_exchange = (TextView) findViewById(R.id.tv_num_exchange);
        lv_productdetail_taocan = (GridView) findViewById(R.id.lv_productdetail_taocan);
        lv_productdetail_taocan.setOnItemClickListener(this);
        tv_giftmalldetail_price = (TextView) findViewById(R.id.tv_giftmalldetail_price);
        tv_num_evals = (TextView) findViewById(R.id.tv_num_evals);
        lv_comment_list = (ProductDetailLinearList) findViewById(R.id.lv_comment_list);

    }

    private void getProductDetailInfo() {
        Utils.NoNet(context);
        showProgressDialog();

        ProductDetailInfoReq req = new ProductDetailInfoReq();
        req.code = "30023";
        req.id_goods = KaKuApplication.id_goods;

        KaKuApiProvider.getProductDetailInfo(req, new KakuResponseListener<ProductDetailInfoResp>(this, ProductDetailInfoResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getProductDetail res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t);
                        goodsNum.setText(t.num_shopcar);
                        goodsNum.setVisibility(TextUtils.equals(t.num_shopcar, "0") ? View.INVISIBLE : View.VISIBLE);
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

    private void setData(ProductDetailInfoResp t) {
        if (list_goodsmodel.size() == 0) {
            list_goodsmodel = t.goods_models;
        }
        adapter = new ProductModelAdapter(context, list_goodsmodel);
        lv_productdetail_taocan.setAdapter(adapter);
        if (list_goodsmodel != null) {
            for (int i = 0; i < list_goodsmodel.size(); i++) {
                if (KaKuApplication.id_goods.equals(list_goodsmodel.get(i).getId_goods())) {
                    num_goodsmodel = list_goodsmodel.get(i).getNum_goods();
                    productdetail_taocanzi = list_goodsmodel.get(i).getName_model();
                    image_taocan = list_goodsmodel.get(i).getImage_goods();
                    productdetail_pricemengban = list_goodsmodel.get(i).getPrice_goods_buy();
                }
            }
        }

        flag_shopcar = t.goods.getFlag_shopcar();
        ShopMallProductDetailObj obj = t.goods;
        BitmapUtil.getInstance(context).download(iv_product_image, KaKuApplication.qian_zhui + obj.getImage_goods());
        BitmapUtil.getInstance(context).download(iv_productdetail_mengbanimage, KaKuApplication.qian_zhui + image_taocan);

        if ("Y".equals(t.goods.getFlag_medal())) {
            iv_xunzhang.setVisibility(View.VISIBLE);
        } else {
            iv_xunzhang.setVisibility(View.GONE);
        }
        if ("Y".equals(t.goods.getFlag_point())) {
            iv_jifenhuangou.setVisibility(View.VISIBLE);
        } else {
            iv_jifenhuangou.setVisibility(View.GONE);
        }

        tv_product_title.setText(obj.getName_goods());
        tv_product_price.setText(obj.getPrice_goods_buy());
        tv_num_exchange.setText(obj.getNum_exchange());
        tv_num_evals.setText(obj.getEval_goods());
        tv_giftmalldetail_xunzhang.setText(obj.getRemark_medal());
        tv_product_title_sub.setText(obj.getPromotion_goods());
        tv_giftmalldetail_price.setText("价格：" + obj.getPrice_goods());
        tv_productdetail_taocanzi.setText(productdetail_taocanzi);
        tv_productdetail_xuanzezi.setText("已选：" + t.goods.getName_goods_model());
        tv_productdetail_pricemengban.setText("¥ " + productdetail_pricemengban);
        if ("0".equals(num_goodsmodel)) {
            type_pay = "que";
            ll_shopcar.setVisibility(View.GONE);
            iv_product_que1.setVisibility(View.VISIBLE);
            iv_product_que2.setVisibility(View.VISIBLE);
            tv_bottombar_pay.setText("缺货登记");
            tv_bottombar_pay.setBackgroundColor(Color.rgb(253, 190, 20));
            tv_productdetail_pay2.setText("缺货登记");
            tv_productdetail_pay2.setBackgroundColor(Color.rgb(253, 190, 20));
        } else {
            type_pay = "add";
            iv_product_que1.setVisibility(View.GONE);
            iv_product_que2.setVisibility(View.GONE);
            if ("N".equals(flag_shopcar)) {
                ll_shopcar.setVisibility(View.GONE);
                tv_bottombar_pay.setText("我要购买");
                tv_bottombar_pay.setBackgroundColor(Color.rgb(209, 0, 0));
                tv_productdetail_pay2.setText("我要购买");
                tv_productdetail_pay2.setBackgroundColor(Color.rgb(209, 0, 0));
            } else {
                ll_shopcar.setVisibility(View.VISIBLE);
                tv_bottombar_pay.setText("加入购物车");
                tv_bottombar_pay.setBackgroundColor(Color.rgb(209, 0, 0));
                tv_productdetail_pay2.setText("加入购物车");
                tv_productdetail_pay2.setBackgroundColor(Color.rgb(209, 0, 0));
            }
        }

        lv_comment_list.setOverScrollMode(ListView.OVER_SCROLL_NEVER);

        lv_comment_list.setAdapter(new TruckProductCommentAdapter(context, t.evals));

        detailUrl = obj.getUrl_goods();

        ll_product_content.measure(0, 0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                productDetailSlidingMenu.smoothScrollTo(0, -DensityUtils.dp2px(context, 50) - DensityUtils.getStatusHeight(MyActivityManager.getInstance().getActivty()));
            }
        }, 200);

        productDetailSlidingMenu.setWebUrl(detailUrl);
    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("商品详情");
    }

    private void addProductToShopCart() {
        Utils.NoNet(context);
        showProgressDialog();

        ShopMallAdd2CartReq req = new ShopMallAdd2CartReq();
        req.code = "3003";
        req.id_driver = Utils.getIdDriver();
        req.id_goods = KaKuApplication.id_goods;

        KaKuApiProvider.addProductToCart(req, new KakuResponseListener<ShopMallAdd2CartResp>(this, ShopMallAdd2CartResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getProductsLst res: " + t.res);
                    if (Constants.RES.equals(t.res) || Constants.RES_TWO.equals(t.res)) {
                        if (goodsNum.getVisibility() != View.VISIBLE) {
                            goodsNum.setVisibility(View.VISIBLE);
                        }
                        String num = getText(goodsNum);
                        if (TextUtils.isEmpty(num)) {
                            goodsNum.setText("1");
                        } else {
                            goodsNum.setText(String.valueOf(Integer.parseInt(num) + 1));
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

    public void QueHuo() {
        showProgressDialog();
        QueHuoReq req = new QueHuoReq();
        req.code = "30028";
        req.id_goods = KaKuApplication.id_goods;
        KaKuApiProvider.QueHuo(req, new KakuResponseListener<ExitResp>(this, ExitResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("quehuo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {

                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }

                    }
                    LogUtil.showShortToast(context, t.msg);
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }

        int id = v.getId();
        if (R.id.tv_comment_more == id) {

            Intent intent = new Intent(context, ProductCommentListActivity.class);
            startActivity(intent);

        } else if (R.id.ll_service == id) {
            Utils.Call(this, "4006867585");

        } else if (R.id.ll_shopcar == id) {
            startActivity(new Intent(context, ShopCartActivity.class));
            finish();
        } else if (R.id.tv_giftmalldetail_xunzhang == id) {
            KaKuApplication.flag_dory = "D";
            Intent intent = new Intent();
            intent.setClass(this, StickerOrderActivity.class);
            startActivity(intent);
        } else if (R.id.iv_productdetail_chamengban == id) {
            rela_productdetail_taocanmengban.setVisibility(View.GONE);
        } else if (R.id.rela_baoyang_taocan == id) {
            rela_productdetail_taocanmengban.setVisibility(View.VISIBLE);
            for (int i = 0; i < list_goodsmodel.size(); i++) {
                if (KaKuApplication.id_goods.equals(list_goodsmodel.get(i).getId_goods())) {
                    list_goodsmodel.get(i).setColor("R");
                }
            }
        } else if (R.id.tv_bottombar_pay == id || R.id.tv_productdetail_pay2 == id) {
            rela_productdetail_taocanmengban.setVisibility(View.GONE);
            if ("add".equals(type_pay)) {
                if ("N".equals(flag_shopcar)) {
                    Intent intent = new Intent(ProductDetailActivity.this, ConfirmOrderXianActivity.class);
                    startActivity(intent);
                } else {
                    addProductToShopCart();
                }
            } else {
                QueHuo();
            }
        } else if (R.id.rela_productdetail_taocanmengban == id) {

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        KaKuApplication.id_goods = list_goodsmodel.get(position).getId_goods();
        for (int i = 0; i < list_goodsmodel.size(); i++) {
            list_goodsmodel.get(i).setColor("B");
        }
        list_goodsmodel.get(position).setColor("R");
        adapter.notifyDataSetChanged();
        getProductDetailInfo();
    }
}
