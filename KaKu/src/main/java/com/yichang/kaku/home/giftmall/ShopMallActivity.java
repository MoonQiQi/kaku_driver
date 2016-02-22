package com.yichang.kaku.home.giftmall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.ShopMallProductObj;
import com.yichang.kaku.request.ShopMallAdd2CartReq;
import com.yichang.kaku.request.ShopMallProductsReq;
import com.yichang.kaku.response.ShopMallAdd2CartResp;
import com.yichang.kaku.response.ShopMallProductsResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.DensityUtils;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.PullToRefreshView;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class ShopMallActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private List<ShopMallProductObj> list = new ArrayList<>();

    //titleBar,返回，购物车，标题
    private TextView left, title;
    private ImageView right;
    //排序按钮，销量，价格，筛选
    private TextView tv_shopmall_sales, tv_shopmall_price, tv_shopmall_newest;
    private RelativeLayout rela_shopmall_sales, rela_shopmall_price, rela_shopmall_filter;
    //排序按钮，最新
    private RelativeLayout rela_shopmall_newest;
    //    车品展示列表
//    private ShopMallGridView gv_shopmall_products;
    private GridView gv_shopmall_products;


    //    设置步数
    private final static int STEP = 50;
    /*start:页码；pageindex:最后一条记录索引；pagesize：每页加载条目数量*/
    private int start = 0, pageindex = 0, pagesize = STEP;
    private final static int INDEX = 6;// 一屏显示的个数

    private PullToRefreshView mPullToRefreshView;

    /*N：最新PU：价格低到高PD:价格高到低H：销量*/
    private String sortRule = "N";
    //    价格升序
    private boolean isPriceAsc = true;
    //    是否显示进度条
    private boolean isShowProgress;
    private TextView goodsNum;
    private int[] endLocation;
    private ValueAnimator scaleAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_mall);

    }

    @Override
    protected void onStart() {
        init();
        super.onStart();
    }

    private void init() {
//        初始化titleBar
        initTitleBar();
//        初始化筛选按钮
        initShopMallClassify();

        initGridView();
        //获取商品信息列表

        setPullState(false);

    }

    private void initGridView() {
//        初始化下拉刷新控件
        mPullToRefreshView = (PullToRefreshView) this.findViewById(R.id.pulltofresh_shopmall_products);
//        初始化下拉列表控件
        gv_shopmall_products = (GridView) this.findViewById(R.id.gv_shopmall_products);
        gv_shopmall_products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openDetailUrl(list.get(position).getId_goods());
            }
        });


        mPullToRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                mPullToRefreshView.onHeaderRefreshComplete();
            }
        });
        mPullToRefreshView.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                if (!Utils.checkNetworkConnection(context)) {
                    stopProgressDialog();
                    Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("onPullUpToRefresh:", "上拉刷新");
                setPullState(true);
                mPullToRefreshView.onFooterRefreshComplete();
                //        设置最后一页时不能下拉刷新数据并提示
                if (list.size() < INDEX) {
                    LogUtil.showShortToast(ShopMallActivity.context, "当前已是尾页数据");
                }
            }
        });

        /*gv_shopmall_products = (PullToRefreshGridView) this.findViewById(R.id.gv_shopmall_products);*/

    }


    private void openDetailUrl(String productId) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra("productId", productId);
        startActivity(intent);
    }

    private void initShopMallClassify() {
        tv_shopmall_sales = (TextView) this.findViewById(R.id.tv_shopmall_sales);
        tv_shopmall_price = (TextView) this.findViewById(R.id.tv_shopmall_price);
        tv_shopmall_newest = (TextView) this.findViewById(R.id.tv_shopmall_newest);
        //tv_shopmall_filter = (TextView) this.findViewById(R.id.tv_shopmall_filter);

//        分类布局
        rela_shopmall_sales = (RelativeLayout) this.findViewById(R.id.rela_shopmall_sales);
        rela_shopmall_sales.setOnClickListener(this);
        rela_shopmall_price = (RelativeLayout) this.findViewById(R.id.rela_shopmall_price);
        rela_shopmall_price.setOnClickListener(this);
//        rela_shopmall_filter = (RelativeLayout) this.findViewById(R.id.rela_shopmall_filter);
//        rela_shopmall_filter.setOnClickListener(this);
        rela_shopmall_newest = (RelativeLayout) this.findViewById(R.id.rela_shopmall_newest);
        rela_shopmall_newest.setOnClickListener(this);

    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        right = (ImageView) findViewById(R.id.tv_right);
        /*right.setVisibility(View.VISIBLE);
        //right.setText("购物车");
        right.setText("");
        right.setBackgroundResource(R.drawable.gwc);*/
        right.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("好礼商城");
        goodsNum = findView(R.id.num);
    }

    private void getProductsInfoLst(final int pageIndex, int pageSize, String srule) {

        //pageSize=20;
        //list=new ArrayList<>();
        Utils.NoNet(context);
        showProgressDialog();

        ShopMallProductsReq req = new ShopMallProductsReq();
        req.code = "3001";
        req.id_driver = Utils.getIdDriver();
        //每次读取记录条数
        req.len = String.valueOf(pageSize);
        // 排序规则
        req.sort = srule;
        //开始位置
        req.start = String.valueOf(pageIndex);
        // TODO CHAIH 货品类型，为筛选功能设定
        req.type_goods = "0";
        //读取货品数据
        KaKuApiProvider.getShopMallProductsLst(req, new BaseCallback<ShopMallProductsResp>(ShopMallProductsResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, ShopMallProductsResp t) {
                if (t != null) {
                    LogUtil.E("getProductsLst res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        //为gridView设置数据
                        setData(t.goods);
                    }
                    if (pageIndex == 0) {
                        goodsNum.setText(t.num_shopcar);
                        goodsNum.setVisibility(TextUtils.equals(t.num_shopcar, "0") ? View.INVISIBLE : View.VISIBLE);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shop_mall, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        } else if (R.id.tv_right == id) {
            MobclickAgent.onEvent(context, "GoToShopCar");
            GoToShopCart();
        } else if (R.id.rela_shopmall_sales == id) {
            /*list.clear();
            pageindex = 0;
            start = 0;
            sortRule = "H";
            getProductsInfoLst(pageindex, pagesize, sortRule);*/
            setTextViewColor(tv_shopmall_sales);
            getSortList("H");
            setPriceNomal();
        } else if (R.id.rela_shopmall_price == id) {
            /*list.clear();
            start = 0;
            pageindex = 0;*/
            setTextViewColor(tv_shopmall_price);
            isPriceAsc = !isPriceAsc;
            /*PU：价格低到高PD:价格高到低
            * public void  setCompoundDrawables  (Drawable left, Drawable top, Drawable right, Drawable bottom);
            * */
            if (isPriceAsc) {
                Drawable drawable = getResources().getDrawable(R.drawable.shopmall_asc);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_shopmall_price.setCompoundDrawables(null, null, drawable, null);
                sortRule = "PU";
                getSortList("PU");
            } else {
                Drawable drawable = getResources().getDrawable(R.drawable.shopmall_desc);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_shopmall_price.setCompoundDrawables(null, null, drawable, null);
                sortRule = "PD";
                getSortList("PD");
            }
//            LogUtil.E("onclick:pageindex=" + pageindex + "||pagesize=" + pagesize + "||sortRule=" + sortRule);
/*            getProductsInfoLst(pageindex, pagesize, sortRule);*/
        } else if (R.id.rela_shopmall_newest == id) {
            getSortList("N");
            setPriceNomal();
            setTextViewColor(tv_shopmall_newest);
        }/*else if (R.id.tv_shopmall_filter == id) {
            LogUtil.showShortToast(context, "敬请期待");
        }*/
    }

    private void setTextViewColor(TextView tv) {
        tv_shopmall_newest.setTextColor(Color.parseColor("#000000"));
        tv_shopmall_sales.setTextColor(Color.parseColor("#000000"));
        tv_shopmall_price.setTextColor(Color.parseColor("#000000"));
        tv.setTextColor(Color.parseColor("#d10000"));
    }

    private void setPriceNomal() {
        Drawable drawable = getResources().getDrawable(R.drawable.shopmall_normal);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_shopmall_price.setCompoundDrawables(null, null, drawable, null);
    }

    private void getSortList(String rule) {
        list.clear();
        pageindex = 0;
        start = 0;
        sortRule = rule;
        getProductsInfoLst(pageindex, pagesize, sortRule);
    }

    private void GoToShopCart() {
        /*todo 转到购物车*/
        finish();
        Intent intent = new Intent(context, ShopCartActivity.class);
        startActivity(intent);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Utils.Many()) {
            return;
        }
        LogUtil.showShortToast(context, "点击");
    }

    private void setData(List<ShopMallProductObj> list) {

        // TODO Auto-generated method stub
        if (list != null) {
            this.list.addAll(list);
            LogUtil.E("list长度" + this.list.size());
        }
        ShopMallProductAdapter adapter = new ShopMallProductAdapter();

        gv_shopmall_products.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        gv_shopmall_products.requestFocus();
        LogUtil.E("pageindex" + pageindex);
        gv_shopmall_products.setItemChecked(pageindex + 1, true);


        gv_shopmall_products.setSelection(pageindex + 1);

        mPullToRefreshView.setEnablePullTorefresh(pageindex == 0 ? false : true);

        mPullToRefreshView.setEnablePullLoadMoreDataStatus(list.size() < INDEX ? false : true);
    }

    private void setPullState(boolean isUp) {
        if (isUp) {
            isShowProgress = true;
            start++;
            pageindex = start * STEP;
            LogUtil.E("setPullState:pageindex=" + pageindex + "||start=" + start + "||STEP=" + STEP);
        } else {
            start = 0;
            pageindex = 0;
            if (list != null) {
                list.clear();
            }
        }

        LogUtil.E("pageindex=" + pageindex + "||pagesize=" + pagesize + "||sortRule=" + sortRule);
        getProductsInfoLst(pageindex, pagesize, sortRule);
    }


    /*gridview 适配器*/
    class ShopMallProductAdapter extends BaseAdapter {

        private int num = 0;

        /*public ShopMallProductAdapter(AbsListView listView) {
            super(listView);
        }*/

        @Override
        public int getCount() {
            return list != null && !list.isEmpty() ? list.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            final ShopMallProductObj obj = list.get(position);
            if (obj == null) {
                return convertView;
            }
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(ShopMallActivity.this, R.layout.item_shopmall_products, null);
                holder.iv_img = (SimpleDraweeView) convertView.findViewById(R.id.iv_shopmall_item_img);
                //测量并绘制图片大小
                convertView.measure(0, 0);
                Log.e("convertView",convertView.toString());
                int maxLength = DensityUtils.dp2px(context, (360 - 2) / 2);

                RelativeLayout.LayoutParams rela_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, maxLength);

                holder.iv_img.setLayoutParams(rela_params);

                //holder.iv_img.measure(0, 0);
                holder.iv_add = (ImageView) convertView.findViewById(R.id.iv_shopmall_add);
                holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_shopmall_desc);
                holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //BitmapUtil.getInstance(ShopMallActivity.context).download(holder.iv_img, KaKuApplication.qian_zhui + obj.getImage_goods());

            holder.iv_img.setImageURI(Uri.parse(KaKuApplication.qian_zhui + obj.getImage_goods()));

            holder.tv_desc.setText(obj.getName_goods());
            holder.tv_price.setText(obj.getPrice_goods());

            final View finalConvertView = holder.iv_img;

            holder.iv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MobclickAgent.onEvent(context, "AddShopCar");
                    Utils.NoNet(context);
                    if (Utils.Many()) {
                        return;
                    }
                    showProgressDialog();

                    ShopMallAdd2CartReq req = new ShopMallAdd2CartReq();
                    req.code = "3003";
                    req.id_driver = Utils.getIdDriver();
                    req.id_goods = obj.getId_goods();

                    KaKuApiProvider.addProductToCart(req, new BaseCallback<ShopMallAdd2CartResp>(ShopMallAdd2CartResp.class) {
                        @Override
                        public void onSuccessful(int statusCode, Header[] headers, ShopMallAdd2CartResp t) {
                            if (t != null) {
                                LogUtil.E("getProductsLst res: " + t.res);
                            }

                            stopProgressDialog();
                            startAnimation(finalConvertView);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                            stopProgressDialog();
                        }
                    });

                }
            });
            //notifyDataSetChanged();

            //LogUtil.E("GRIDVIEW ==width:"+width+";;;"+position+":height:"+height);
            return convertView;
        }



        class ViewHolder {
            private SimpleDraweeView iv_img;
            private TextView tv_desc;
            private TextView tv_price;
            private ImageView iv_add;

        }


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        endLocation = new int[2];
        right.getLocationInWindow(endLocation);
    }

    private void startAnimation(final View target) {

        /*获取view的坐标*/
        final int[] startLocation = new int[2];
        target.getLocationInWindow(startLocation);

        /*获取view的缓存图片*/
        final ImageView imageView = createViewCacheImg(target);

        /*创建布局参数*/
        final WindowManager.LayoutParams params = createLayoutParams(startLocation, target);

        final WindowManager wm = getWindowManager();
        wm.addView(imageView, params);

        final float value = goodsNum.getMeasuredHeight() * 1f / target.getMeasuredHeight();

        scaleAnimator = ValueAnimator.ofFloat(1, value);
        scaleAnimator.setDuration(500);
        scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                params.width = (int) (target.getMeasuredWidth() * v);
                params.height = (int) (target.getMeasuredHeight() * v);

                wm.updateViewLayout(imageView, params);
            }
        });
        scaleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /*终点坐标*/
                int endX = endLocation[0] + (right.getMeasuredWidth() - goodsNum.getMeasuredWidth()) / 2;
                int endY = endLocation[1] + (right.getMeasuredHeight() - goodsNum.getMeasuredHeight()) / 2 - getStatusBarHeight();

                startMoveAnimator(new int[]{params.x, params.y}, imageView, params, wm, new int[]{endX, endY});
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        scaleAnimator.start();
    }

    @NonNull
    private WindowManager.LayoutParams createLayoutParams(int[] startLocation, View target) {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();

        /*缩略图的大小*/
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        params.gravity = Gravity.LEFT + Gravity.TOP;

        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;// 修改类型为电话类型

        /*初始位置*/
        params.x = startLocation[0];
        params.y = startLocation[1] - getStatusBarHeight();

        return params;
    }

    private void startMoveAnimator(final int[] startLocation, final ImageView imageView, final WindowManager.LayoutParams params, final WindowManager wm, int desLocation[]) {

        final int disX = Math.abs(desLocation[0] - startLocation[0]);
        final int disY = Math.abs(desLocation[1] - startLocation[1]);


        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(500);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                params.x = (int) (startLocation[0] + disX * fraction);
                params.y = (int) (startLocation[1] - disY * fraction);
                wm.updateViewLayout(imageView, params);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                wm.removeViewImmediate(imageView);
                if (goodsNum.getVisibility() != View.VISIBLE) {
                    goodsNum.setVisibility(View.VISIBLE);
                }
                String num = getText(goodsNum);
                if (TextUtils.isEmpty(num)) {
                    goodsNum.setText("1");
                } else {
                    goodsNum.setText(String.valueOf(Integer.parseInt(num) + 1));

                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }

    @NonNull
    private ImageView createViewCacheImg(View convertView) {
        convertView.destroyDrawingCache();
        convertView.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(convertView.getDrawingCache());
        convertView.setDrawingCacheEnabled(false);
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(cache);
        return imageView;
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
    protected void onDestroy() {
        super.onDestroy();
        if (scaleAnimator != null && scaleAnimator.isRunning()) {
            scaleAnimator.cancel();
        }
    }
}
