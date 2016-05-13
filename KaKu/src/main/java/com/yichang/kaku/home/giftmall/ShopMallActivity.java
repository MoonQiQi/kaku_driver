package com.yichang.kaku.home.giftmall;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.obj.ChePinAdapter1Obj;
import com.yichang.kaku.obj.ShopMallProductObj;
import com.yichang.kaku.request.ExitReq;
import com.yichang.kaku.request.ShopMallAdd2CartReq;
import com.yichang.kaku.request.ShopMallProductsReq;
import com.yichang.kaku.response.ChePinFilterResp;
import com.yichang.kaku.response.ShopMallAdd2CartResp;
import com.yichang.kaku.response.ShopMallProductsResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.PullToRefreshView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopMallActivity extends BaseActivity implements View.OnClickListener, TextView.OnEditorActionListener, View.OnTouchListener {

    private List<ShopMallProductObj> list = new ArrayList<>();

    //排序按钮，销量，价格，筛选
    private TextView tv_shopmall_sales, tv_shopmall_price, tv_shopmall_newest, tv_shopmall_filter;
    private RelativeLayout rela_shopmall_sales, rela_shopmall_price, rela_shopmall_filter;
    //排序按钮，最新
    private RelativeLayout rela_shopmall_newest;
    //    车品展示列表
    private GridView gv_shopmall_products;


    //    设置步数
    private final static int STEP = 20;
    /*start:页码；pageindex:最后一条记录索引；pagesize：每页加载条目数量*/
    private int start = 0, pageindex = 0, pagesize = STEP;
    private final static int INDEX = 6;// 一屏显示的个数

    private PullToRefreshView mPullToRefreshView;

    /*N：最新PU：价格低到高PD:价格高到低H：销量*/
    private String sort = "N";
    //    价格升序
    private boolean isPriceAsc = true;
    //    是否显示进度条
    private boolean isShowProgress;
    private TextView tv_gwc_num;
    private int[] endLocation;
    private ValueAnimator scaleAnimator;
    private ListView lv_expand, lv_lishi;
    private List<ChePinAdapter1Obj> list_1 = new ArrayList();
    private String id_goods_type;
    private RelativeLayout rela_filter;
    private ImageView nosearch;
    private ImageView iv_shopmall_fanhui;
    private EditText et_shopmall_sousuo;
    private TextView tv_shopmall_quxiao;
    private List<String> list_string;
    private StringBuilder sb;
    private LinearLayout ll_chepin_lishi;
    private TextView tv_lishi_qingkong_chepin;
    private RelativeLayout rela_chepin_grid, rela_chepin_b2;
    private String name_goods;
    private String save_history;
    private ChePinSouSuoLiShiAdapter adapter_lishi;
    private FrameLayout frame_shopcar;
    private int hide = 0;
    private LinearLayout line_shopmall_table;
    private ChePinFilter1Adapter adapter_filter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_mall);
        init();
    }

    private void init() {
        initTitleBar();
        initShopMallClassify();
        initGridView();
        setPullState(false);
    }

    private void initGridView() {
        mPullToRefreshView = (PullToRefreshView) this.findViewById(R.id.pulltofresh_shopmall_products);
        gv_shopmall_products = (GridView) this.findViewById(R.id.gv_shopmall_products);
        gv_shopmall_products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MobclickAgent.onEvent(context, "ChePinDetail");
                KaKuApplication.id_goods = list.get(position).getId_goods();
                Intent intent = new Intent(context, ProductDetailActivity.class);
                startActivity(intent);
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
                setPullState(true);
                mPullToRefreshView.onFooterRefreshComplete();
                //        设置最后一页时不能下拉刷新数据并提示
                if (list.size() < INDEX) {
                    LogUtil.showShortToast(ShopMallActivity.context, "没有更多了");
                }
            }
        });

    }

    private void initShopMallClassify() {
        tv_shopmall_sales = (TextView) this.findViewById(R.id.tv_shopmall_sales);
        tv_shopmall_price = (TextView) this.findViewById(R.id.tv_shopmall_price);
        tv_shopmall_newest = (TextView) this.findViewById(R.id.tv_shopmall_newest);
        tv_shopmall_filter = (TextView) this.findViewById(R.id.tv_shopmall_filter);
        rela_shopmall_sales = (RelativeLayout) this.findViewById(R.id.rela_shopmall_sales);
        rela_shopmall_sales.setOnClickListener(this);
        rela_shopmall_price = (RelativeLayout) this.findViewById(R.id.rela_shopmall_price);
        rela_shopmall_price.setOnClickListener(this);
        rela_shopmall_filter = (RelativeLayout) this.findViewById(R.id.rela_shopmall_filter);
        rela_shopmall_filter.setOnClickListener(this);
        line_shopmall_table = (LinearLayout) this.findViewById(R.id.line_shopmall_table);
        rela_shopmall_newest = (RelativeLayout) this.findViewById(R.id.rela_shopmall_newest);
        rela_shopmall_newest.setOnClickListener(this);
        rela_chepin_grid = (RelativeLayout) this.findViewById(R.id.rela_chepin_grid);
        rela_chepin_b2 = (RelativeLayout) this.findViewById(R.id.rela_chepin_b2);
        rela_filter = (RelativeLayout) this.findViewById(R.id.rela_filter);
        rela_filter.setOnClickListener(this);
        tv_lishi_qingkong_chepin = (TextView) findViewById(R.id.tv_lishi_qingkong_chepin);
        tv_lishi_qingkong_chepin.setOnClickListener(this);
        frame_shopcar = (FrameLayout) findViewById(R.id.frame_shopcar);
        frame_shopcar.setOnClickListener(this);
        nosearch = (ImageView) findViewById(R.id.nosearch);
        iv_shopmall_fanhui = (ImageView) findViewById(R.id.iv_shopmall_fanhui);
        iv_shopmall_fanhui.setOnClickListener(this);
        et_shopmall_sousuo = (EditText) findViewById(R.id.et_shopmall_sousuo);
        et_shopmall_sousuo.setOnTouchListener(new View.OnTouchListener() {
            int touch_flag = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touch_flag++;
                if (touch_flag == 2) {
                    touch_flag = 0;
                    SouSuoShow();
                }
                return false;
            }
        });
        et_shopmall_sousuo.setOnEditorActionListener(this);
        tv_shopmall_quxiao = (TextView) findViewById(R.id.tv_shopmall_quxiao);
        tv_shopmall_quxiao.setOnClickListener(this);
        ll_chepin_lishi = (LinearLayout) findViewById(R.id.ll_chepin_lishi);
        lv_expand = (ListView) findViewById(R.id.lv_expand);
        lv_lishi = (ListView) findViewById(R.id.lv_lishi_chepin);
        lv_lishi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et_shopmall_sousuo.setText(list_string.get(position));
                name_goods = list_string.get(position);
                id_goods_type = "1";
                setPullState(false);
            }
        });

    }

    private void initTitleBar() {
        tv_gwc_num = findView(R.id.tv_gwc_num);
        id_goods_type = KaKuApplication.id_fenlei;
    }

    private void getProductsInfoLst(final int pageIndex, int pageSize) {

        Utils.NoNet(context);
        showProgressDialog();
        ShopMallProductsReq req = new ShopMallProductsReq();
        req.code = "3001";
        req.len = String.valueOf(pageSize);
        req.sort = sort;
        req.start = String.valueOf(pageIndex);
        req.type_goods = "0";
        req.id_goods_type = id_goods_type;
        req.name_goods = name_goods;
        KaKuApiProvider.getShopMallProductsLst(req, new KakuResponseListener<ShopMallProductsResp>(this, ShopMallProductsResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getProductsLst res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        SouSuoHide();
                        setData(t.goods);
                    }
                    if (pageIndex == 0) {
                        tv_gwc_num.setText(t.num_shopcar);
                        tv_gwc_num.setVisibility(TextUtils.equals(t.num_shopcar, "0") ? View.INVISIBLE : View.VISIBLE);
                        frame_shopcar.setVisibility(TextUtils.equals(t.num_shopcar, "0") ? View.INVISIBLE : View.VISIBLE);
                    }
                }
                stopProgressDialog();
            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shop_mall, menu);
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


    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        setPriceNomal();
        if (R.id.rela_shopmall_sales == id) {
            name_goods = "";
            setTextViewColor(tv_shopmall_sales);
            getSortList("H");
            setPriceNomal();
            HideFilterClose();
        } else if (R.id.rela_shopmall_price == id) {
            name_goods = "";
            setTextViewColor(tv_shopmall_price);
            isPriceAsc = !isPriceAsc;
            if (isPriceAsc) {
                Drawable drawable = getResources().getDrawable(R.drawable.shopmall_asc);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_shopmall_price.setCompoundDrawables(null, null, drawable, null);
                sort = "PU";
                getSortList("PU");
            } else {
                Drawable drawable = getResources().getDrawable(R.drawable.shopmall_desc);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_shopmall_price.setCompoundDrawables(null, null, drawable, null);
                sort = "PD";
                getSortList("PD");
            }
            HideFilterClose();
        } else if (R.id.rela_shopmall_newest == id) {
            name_goods = "";
            getSortList("N");
            setTextViewColor(tv_shopmall_newest);
            HideFilterClose();
        } else if (R.id.rela_shopmall_filter == id) {
            name_goods = "";
            setTextViewColor(tv_shopmall_filter);
            /*if (list_1.size() == 0) {
                GetFilter();
            }*/
            GetFilter();
            HideFilter();
        } else if (R.id.rela_filter == id) {
            HideFilter();
        } else if (R.id.iv_shopmall_fanhui == id) {
            goToHome();
        } else if (R.id.tv_shopmall_quxiao == id) {
            et_shopmall_sousuo.setText("");
            SouSuoHide();
        } else if (R.id.tv_lishi_qingkong_chepin == id) {
            QingKong();
        } else if (R.id.et_shopmall_sousuo == id) {
            SouSuoShow();
            for (int i = 0; i < list_1.size(); i++) {
                list_1.get(i).setColor_type("B");
                for (int j = 0; j < list_1.get(i).getList().size(); j++) {
                    list_1.get(i).getList().get(j).setColor_type("B");
                }
            }
        } else if (R.id.frame_shopcar == id) {
            GoToShopCart();
        }
    }

    private void setTextViewColor(TextView tv) {
        tv_shopmall_newest.setTextColor(Color.parseColor("#000000"));
        tv_shopmall_sales.setTextColor(Color.parseColor("#000000"));
        tv_shopmall_price.setTextColor(Color.parseColor("#000000"));
        tv_shopmall_filter.setTextColor(Color.parseColor("#000000"));
        tv.setTextColor(Color.parseColor("#d10000"));
    }

    private void setPriceNomal() {
        Drawable drawable = getResources().getDrawable(R.drawable.shopmall_normal);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_shopmall_price.setCompoundDrawables(null, null, drawable, null);
    }

    private void getSortList(String rule) {
        list.clear();
        pageindex = 0;
        start = 0;
        sort = rule;
        getProductsInfoLst(pageindex, pagesize);
    }

    private void GoToShopCart() {
        /*todo 转到购物车*/
        finish();
        Intent intent = new Intent(context, ShopCartActivity.class);
        startActivity(intent);

    }

    private void setData(List<ShopMallProductObj> list) {

        // TODO Auto-generated method stub
        if (list != null) {
            this.list.addAll(list);
            if (this.list.size() == 0) {
                nosearch.setVisibility(View.VISIBLE);
            } else {
                nosearch.setVisibility(View.GONE);
            }
        }
        ShopMallProductAdapter adapter = new ShopMallProductAdapter();

        gv_shopmall_products.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        gv_shopmall_products.requestFocus();
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
        } else {
            start = 0;
            pageindex = 0;
            if (list != null) {
                list.clear();
            }
        }
        getProductsInfoLst(pageindex, pagesize);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            //点软键盘的搜索
            String tempWord = et_shopmall_sousuo.getText().toString().trim();

            if (TextUtils.isEmpty(tempWord)) {
                LogUtil.showShortToast(context, "请输入搜索内容");
                return false;
            }
            Save();
            name_goods = tempWord;
            id_goods_type = "1";
            setPullState(false);
            return true;
        } else {
            return false;
        }
    }

    public void Save() {

        String text = et_shopmall_sousuo.getText().toString();
        SharedPreferences sp = getSharedPreferences("history_strs_chepin", 0);
        String save_Str = sp.getString("history_chepin", "");
        list_string = Arrays.asList(save_Str.split(","));
        List arrayList = new ArrayList(list_string);
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(text)) {
                return;
            }
        }
        sb = new StringBuilder(save_Str);
        sb.append(text + ",");

        sp.edit().putString("history_chepin", sb.toString()).commit();
    }

    public void ShowHis() {
        SharedPreferences sp = getSharedPreferences("history_strs_chepin", 0);
        save_history = sp.getString("history_chepin", "");
        if ("".equals(save_history)) {
            return;
        }
        list_string = Arrays.asList(save_history.split(","));
        List arrayList = new ArrayList(list_string);
        adapter_lishi = new ChePinSouSuoLiShiAdapter(this, arrayList);
        lv_lishi.setAdapter(adapter_lishi);

    }

    public void QingKong() {
        SharedPreferences sp = getSharedPreferences("history_strs_chepin", 0);
        sp.edit().putString("history_chepin", "").commit();
        lv_lishi.setVisibility(View.GONE);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    class ShopMallProductAdapter extends BaseAdapter {

        private int num = 0;

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
                holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_shopmall_item_img);
                holder.iv_add = (ImageView) convertView.findViewById(R.id.iv_shopmall_add);
                holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_shopmall_desc2);
                holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price2);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //测量并绘制图片大小
            //holder.iv_img.setImageURI(Uri.parse(KaKuApplication.qian_zhui + obj.getImage_goods()));
            BitmapUtil.getInstance(context).download(holder.iv_img, KaKuApplication.qian_zhui + obj.getImage_goods());
            convertView.measure(0, 0);

            DisplayMetrics outMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
            int anInt = outMetrics.widthPixels / 2;

            RelativeLayout.LayoutParams rela_params = new RelativeLayout.LayoutParams(anInt, anInt);

            holder.iv_img.setLayoutParams(rela_params);
            holder.tv_desc.setText(obj.getName_goods());
            holder.tv_price.setText(obj.getPrice_goods_buy());

            if ("N".equals(obj.getFlag_shopcar())) {
                holder.iv_add.setVisibility(View.GONE);
            } else {
                holder.iv_add.setVisibility(View.VISIBLE);
            }

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

                    KaKuApiProvider.addProductToCart(req, new KakuResponseListener<ShopMallAdd2CartResp>(context, ShopMallAdd2CartResp.class) {
                        @Override
                        public void onSucceed(int what, Response response) {
                            super.onSucceed(what, response);
                            if (t != null) {
                                LogUtil.E("getProductsLst res: " + t.res);
                                if (Constants.RES.equals(t.res)) {
                                    stopProgressDialog();
                                    //startAnimation(finalConvertView);
                                    if (tv_gwc_num.getVisibility() != View.VISIBLE) {
                                        tv_gwc_num.setVisibility(View.VISIBLE);
                                        frame_shopcar.setVisibility(View.VISIBLE);
                                    }
                                    String num = getText(tv_gwc_num);
                                    if (TextUtils.isEmpty(num)) {
                                        tv_gwc_num.setText("1");
                                    } else {
                                        tv_gwc_num.setText(String.valueOf(Integer.parseInt(num) + 1));
                                    }
                                } else {
                                    LogUtil.showShortToast(context, t.msg);
                                    stopProgressDialog();
                                }
                            }
                        }


                    });
                }
            });
            return convertView;
        }

        class ViewHolder {
            //private SimpleDraweeView iv_img;
            private ImageView iv_img;
            private TextView tv_desc;
            private TextView tv_price;
            private ImageView iv_add;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        endLocation = new int[2];
        //right.getLocationInWindow(endLocation);
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

        final float value = tv_gwc_num.getMeasuredHeight() * 1f / target.getMeasuredHeight();

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
                //int endX = endLocation[0] + (right.getMeasuredWidth() - tv_gwc_num.getMeasuredWidth()) / 2;
                //int endY = endLocation[1] + (right.getMeasuredHeight() - tv_gwc_num.getMeasuredHeight()) / 2 - getStatusBarHeight();
                int endX = endLocation[0] + (0);
                int endY = endLocation[1] + (0);

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
                if (tv_gwc_num.getVisibility() != View.VISIBLE) {
                    tv_gwc_num.setVisibility(View.VISIBLE);
                }
                String num = getText(tv_gwc_num);
                if (TextUtils.isEmpty(num)) {
                    tv_gwc_num.setText("1");
                } else {
                    tv_gwc_num.setText(String.valueOf(Integer.parseInt(num) + 1));
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

    public void GetFilter() {
        showProgressDialog();
        ExitReq req = new ExitReq();
        KaKuApiProvider.getShopMallFenLeiList(req, new KakuResponseListener<ChePinFilterResp>(this, ChePinFilterResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("chepinfilter res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        if (list_1.size() == 0) {
                            list_1 = t.types;
                        }
                        adapter_filter1 = new ChePinFilter1Adapter(ShopMallActivity.this, list_1);
                        lv_expand.setAdapter(adapter_filter1);
                        adapter_filter1.setShowProgress(new ChePinFilter1Adapter.ShowProgress() {
                            @Override
                            public void showDialog() {
                                showProgressDialog();
                            }

                            @Override
                            public void stopDialog() {
                                stopProgressDialog();
                            }

                            @Override
                            public void setId(String id, int position) {
                                for (int i = 0; i < list_1.size(); i++) {
                                    list_1.get(i).setColor_type("B");
                                    for (int j = 0; j < list_1.get(i).getList().size(); j++) {
                                        list_1.get(i).getList().get(j).setColor_type("B");
                                    }
                                }
                                list_1.get(position).setColor_type("R");
                                adapter_filter1 = new ChePinFilter1Adapter(context, list_1);
                                lv_expand.setAdapter(adapter_filter1);
                                id_goods_type = id;
                                name_goods = "";
                                HideFilter();
                                setPullState(false);
                            }
                        });
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

        });
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

    public void HideFilter() {
        if (hide == 0) {
            rela_filter.setVisibility(View.VISIBLE);
            nosearch.setVisibility(View.GONE);
            Drawable drawable = getResources().getDrawable(R.drawable.filter_down);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_shopmall_filter.setCompoundDrawables(null, null, drawable, null);
            hide = 1;
        } else {
            rela_filter.setVisibility(View.GONE);
            Drawable drawable = getResources().getDrawable(R.drawable.filter_up);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_shopmall_filter.setCompoundDrawables(null, null, drawable, null);
            hide = 0;
        }
    }

    public void HideFilterClose() {
        rela_filter.setVisibility(View.GONE);
        Drawable drawable = getResources().getDrawable(R.drawable.filter_normal);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_shopmall_filter.setCompoundDrawables(null, null, drawable, null);
        hide = 0;
    }

    public void SouSuoHide() {
        rela_filter.setVisibility(View.GONE);
        rela_chepin_b2.setVisibility(View.GONE);
        rela_chepin_grid.setVisibility(View.VISIBLE);
        ll_chepin_lishi.setVisibility(View.GONE);
        rela_filter.setVisibility(View.GONE);
        gv_shopmall_products.setVisibility(View.VISIBLE);
        line_shopmall_table.setVisibility(View.VISIBLE);
    }

    public void SouSuoShow() {
        ShowHis();
        rela_filter.setVisibility(View.GONE);
        rela_chepin_b2.setVisibility(View.GONE);
        rela_chepin_grid.setVisibility(View.GONE);
        ll_chepin_lishi.setVisibility(View.VISIBLE);
        rela_filter.setVisibility(View.GONE);
        gv_shopmall_products.setVisibility(View.GONE);
        line_shopmall_table.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goToHome();
        }
        return false;
    }

    private void goToHome() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (scaleAnimator != null && scaleAnimator.isRunning()) {
            scaleAnimator.cancel();
        }
    }
}
