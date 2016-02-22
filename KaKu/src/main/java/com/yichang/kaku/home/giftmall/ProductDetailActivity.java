package com.yichang.kaku.home.giftmall;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MyActivityManager;
import com.yichang.kaku.home.giftmall.sliding.ProductDetailLinearList;
import com.yichang.kaku.home.giftmall.sliding.ProductDetailScrollViewPage;
import com.yichang.kaku.home.giftmall.sliding.ProductDetailSlidingMenu;
import com.yichang.kaku.obj.ShopMallProductDetailObj;
import com.yichang.kaku.request.ProductDetailInfoReq;
import com.yichang.kaku.request.ShopMallAdd2CartReq;
import com.yichang.kaku.response.ProductDetailInfoResp;
import com.yichang.kaku.response.ShopMallAdd2CartResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.DensityUtils;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.tools.mesh.BitmapMesh;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

public class ProductDetailActivity extends BaseActivity implements OnClickListener {
    //titleBar,返回，购物车，标题
    private TextView left, title;
    private ProductDetailSlidingMenu productDetailSlidingMenu;
    private WebView wv_product_detail;
    private TextView tv_bottombar_pay;
    private String mProductId;

    private LinearLayout ll_product_content;
    private LinearLayout ll_product_web;
    //原生页面商品详情
    private ImageView iv_product_image;
    private TextView tv_product_title;
    private TextView tv_product_price, tv_num_exchange, tv_num_evals;
//    private ListView lv_comment_list;
    private ProductDetailLinearList lv_comment_list;

    //private String detailUrl="http://kaku.wekaku.com/index.php?m=Appweb&a=goods_detail&id_goods=3";
    private String detailUrl = "";
    private TextView tv_down;
    //private boolean isLoading=false;

    private TextView tv_comment_more;

    private LinearLayout ll_service, ll_shopcar;
    private ProductDetailScrollViewPage productDetailScrollViewPage;
    private RelativeLayout rootView;

    private TextView tv_product_title_sub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        init();
    }

    public void toggleMenu(View v) {
        productDetailSlidingMenu.toggleMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private View iv_shopcar;
    private View shopcar_fra;
    private TextView goodsNum;

    private void init() {
        initTitleBar();
        showProgressDialog();

        startLocation = new int[2];
        endLocation = new int[2];

        iv_shopcar = findView(R.id.iv_shopcar);
        shopcar_fra = findView(R.id.shopcar_fra);
        rootView = findView(R.id.rootView);

        goodsNum = findView(R.id.num);
        productDetailScrollViewPage = findView(R.id.ysnowScrollViewPageOne);
        productDetailScrollViewPage.setScreenHeight(100);

        productDetailSlidingMenu = (ProductDetailSlidingMenu) findViewById(R.id.expanded_menu);
        productDetailSlidingMenu.setScreenHeight(100);

        tv_bottombar_pay = (TextView) findViewById(R.id.tv_bottombar_pay);
        tv_bottombar_pay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductToShopCart();

            }
        });
        wv_product_detail = (WebView) findViewById(R.id.wv_product_detail);
        mProductId = getIntent().getStringExtra("productId");

        tv_comment_more = (TextView) findViewById(R.id.tv_comment_more);
        tv_comment_more.setOnClickListener(this);

        ll_service = (LinearLayout) findViewById(R.id.ll_service);
        ll_service.setOnClickListener(this);

        ll_shopcar = (LinearLayout) findViewById(R.id.ll_shopcar);
        ll_shopcar.setOnClickListener(this);
        initProductDetailInfo();

        getProductDetailInfo();

        /*// 更强的打开链接控制：自己覆写一个WebViewClient类：除了指定链接从WebView打开，其他的链接默认打开
        wv_product_detail.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = wv_product_detail.getSettings(); // webView: 类WebView的实例
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);  //就是这句
        webSettings.setSupportZoom(true);
        // 设置缩放工具的显示
        webSettings.setBuiltInZoomControls(true);*/

        ll_product_web = (LinearLayout) findViewById(R.id.ll_product_content);
        ll_product_content = (LinearLayout) findViewById(R.id.ll_product_content);

        tv_product_title_sub= (TextView) findViewById(R.id.tv_product_title_sub);

    }

    private void initProductDetailInfo() {
        iv_product_image = (ImageView) findViewById(R.id.iv_product_image);
        tv_product_title = (TextView) findViewById(R.id.tv_product_title);
        tv_product_price = (TextView) findViewById(R.id.tv_product_price);
        tv_num_exchange = (TextView) findViewById(R.id.tv_num_exchange);
        tv_num_evals = (TextView) findViewById(R.id.tv_num_evals);

        lv_comment_list = (ProductDetailLinearList) findViewById(R.id.lv_comment_list);

        tv_down = (TextView) findViewById(R.id.tv_down);
    }

    private void getProductDetailInfo() {
        Utils.NoNet(context);
        showProgressDialog();

        ProductDetailInfoReq req = new ProductDetailInfoReq();
        req.code = "30023";
        req.id_driver = Utils.getIdDriver();
        req.id_goods = mProductId;

        KaKuApiProvider.getProductDetailInfo(req, new BaseCallback<ProductDetailInfoResp>(ProductDetailInfoResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, ProductDetailInfoResp t) {
                if (t != null) {
                    LogUtil.E("getProductDetailUrl res: " + t.res);
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

                    //LogUtil.showShortToast(context, t.msg);
                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });

    }

    private int[] endLocation;
    private int[] startLocation;

    private static Bitmap scaleBitmap(Bitmap bitmap, float scaleX, float scaleY) {
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);//长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        iv_shopcar.getLocationInWindow(endLocation);
        iv_product_image.getLocationInWindow(startLocation);
    }

    private void startAnimation() {
        /*获取图片*/
        BitmapDrawable bitmapDrawable = (BitmapDrawable) iv_product_image.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        float scaleX = iv_product_image.getMeasuredWidth() * 1f / bitmap.getWidth();
        float scaleY = iv_product_image.getMeasuredHeight() * 1f / bitmap.getHeight();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int x = endLocation[0] + iv_shopcar.getMeasuredWidth() / 2;
        int y = shopcar_fra.getBottom() + iv_shopcar.getMeasuredHeight() / 2;

        final BitmapMesh.SampleView sampleView = new BitmapMesh.SampleView(this, scaleBitmap(bitmap, scaleX, scaleY), x, y);

        rootView.addView(sampleView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        sampleView.startAnimation(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (goodsNum.getVisibility() != View.VISIBLE) {
                    goodsNum.setVisibility(View.VISIBLE);
                }
                String num = getText(goodsNum);
                if (TextUtils.isEmpty(num)) {
                    goodsNum.setText("1");
                } else {
                    goodsNum.setText(String.valueOf(Integer.parseInt(num) + 1));

                }
                rootView.removeView(sampleView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void setData(ProductDetailInfoResp t) {
        ShopMallProductDetailObj obj = t.goods;
        BitmapUtil.getInstance(context).download(iv_product_image, KaKuApplication.qian_zhui + obj.getImage_goods());

        tv_product_title.setText(obj.getName_goods());
        tv_product_price.setText(obj.getPrice_goods());
        tv_num_exchange.setText(obj.getNum_exchange());
        tv_num_evals.setText(obj.getEval_goods());

        tv_product_title_sub.setText(obj.getPromotion_goods());

        //lv_comment_list = (ProductDetailLinearList) findViewById(R.id.lv_comment_list);
       /* lv_comment_list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return getParent().dispatchTouchEvent(event);
            }
        });*/
        //lv_comment_list.setFocusable(false);
        lv_comment_list.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
        /*lv_comment_list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });*/
        lv_comment_list.setAdapter(new TruckProductCommentAdapter(context, t.evals));
       // Utils.setListViewHeightBasedOnChildren(lv_comment_list);
        detailUrl = obj.getUrl_goods();

        ll_product_content.measure(0, 0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                productDetailSlidingMenu.smoothScrollTo(0, -DensityUtils.dp2px(context, 50) - DensityUtils.getStatusHeight(MyActivityManager.getInstance().getActivty()));
            }
        }, 200);

       /* new Handler().post(new Runnable() {
            @Override
            public void run() {
                slidingMenu.fullScroll(ScrollView.FOCUS_UP);
            }
        });*/
        productDetailSlidingMenu.setWebUrl(detailUrl);
        LogUtil.E("detailUrl" + detailUrl);
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

/*    private void getProductDetailUrl(String id_goods) {
        Utils.NoNet(context);
        showProgressDialog();

        ProductDetailUrlReq req = new ProductDetailUrlReq();
        req.code = "3002";
        req.id_goods = id_goods;
        KaKuApiProvider.getProductDetailUrl(req, new BaseCallback<ProductDetailUrlResp>(ProductDetailUrlResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, ProductDetailUrlResp t) {

                if (t != null) {
                    LogUtil.E("getProductDetailUrl res: " + t.res + "::" + t.url);
                    if (Constants.RES.equals(t.res)) {

                        if (TextUtils.isEmpty(t.url)) {
                            return;
                        } else {
                            wv_product_detail.loadUrl(t.url);
                        }

                    }
                    //LogUtil.showShortToast(context, t.msg);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });


    }*/

    private void addProductToShopCart() {
        Utils.NoNet(context);
        showProgressDialog();

        ShopMallAdd2CartReq req = new ShopMallAdd2CartReq();
        req.code = "3003";
        req.id_driver = Utils.getIdDriver();
        req.id_goods = mProductId;

        KaKuApiProvider.addProductToCart(req, new BaseCallback<ShopMallAdd2CartResp>(ShopMallAdd2CartResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, ShopMallAdd2CartResp t) {
                if (t != null) {
                    LogUtil.E("getProductsLst res: " + t.res);
                }
                stopProgressDialog();
                startAnimation();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
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
            intent.putExtra("id_goods", mProductId);
            startActivity(intent);

        } else if (R.id.ll_service == id) {
            Utils.Call(this, "4006867585");

        } else if (R.id.ll_shopcar == id) {

            startActivity(new Intent(context, ShopCartActivity.class));
            finish();
        }
    }

/*
    *//**
     * 自定义的WebViewClient类，将特殊链接从WebView打开，其他链接仍然用默认浏览器打开
     *
     * @author chaih
     *//*
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            stopProgressDialog();
            super.onPageFinished(view, url);
        }
    }*/


}
