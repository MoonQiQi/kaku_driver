package com.yichang.kaku.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.umeng.analytics.MobclickAgent;
import com.wly.android.widget.AdGalleryHelper;
import com.wly.android.widget.Advertising;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseFragment;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.Service1;
import com.yichang.kaku.home.Ad.Add_EActivity;
import com.yichang.kaku.home.Ad.Add_FActivity;
import com.yichang.kaku.home.Ad.Add_IActivity;
import com.yichang.kaku.home.Ad.Add_NActivity;
import com.yichang.kaku.home.Ad.Add_PActivity;
import com.yichang.kaku.home.Ad.Add_YActivity;
import com.yichang.kaku.home.Ad.CheTieListActivity;
import com.yichang.kaku.home.dailysign.DailyRemindService;
import com.yichang.kaku.home.dailysign.DailySignActivity;
import com.yichang.kaku.home.dailysign.PollingUtils;
import com.yichang.kaku.home.discovery.DiscoveryActivity;
import com.yichang.kaku.home.giftmall.ProductDetailActivity;
import com.yichang.kaku.home.giftmall.ShopMallActivity;
import com.yichang.kaku.home.illegal.IllegalQueryActivity;
import com.yichang.kaku.home.mycar.MyCarActivity;
import com.yichang.kaku.home.mycar.PinPaiXuanZeActivity;
import com.yichang.kaku.member.login.LoginActivity;
import com.yichang.kaku.obj.GoodsObj;
import com.yichang.kaku.obj.NewsObj;
import com.yichang.kaku.obj.RollsAddObj;
import com.yichang.kaku.obj.RollsObj;
import com.yichang.kaku.obj.SeckillObj;
import com.yichang.kaku.obj.Shops_wxzObj;
import com.yichang.kaku.request.CheckUpdateReq;
import com.yichang.kaku.request.GetAddReq;
import com.yichang.kaku.request.HomeReq;
import com.yichang.kaku.response.CheckUpdateResp;
import com.yichang.kaku.response.GetAddResp;
import com.yichang.kaku.response.HomeResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.DensityUtils;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.wheelview.OnWheelClickedListener;
import com.yichang.kaku.view.wheelview.WheelView;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends BaseFragment implements OnClickListener, AdapterView.OnItemClickListener, ScrollViewListener, ViewTreeObserver.OnGlobalLayoutListener {

    private String versionName;
    private RelativeLayout galleryContainer;
    private AdGalleryHelper mGalleryHelper;
    private AdGalleryHelper mGalleryHelper2;
    private Activity mActivity;
    private boolean flag_frag = false;
    private LayoutInflater mInflater;
    private SharedPreferences prefs;
    private TextView tv_title_home;
    private String android_url;
    private ListView lv_home_item;
    private List<Shops_wxzObj> list_wxz = new ArrayList<Shops_wxzObj>();
    private ShopItemAdapter adapter;
    private ImageView iv_title_home_left, iv_title_home_right;
    private LinearLayout ll_title_home_left, ll_title_home_right,line_home_fuwuzhan;
    private TextView tv_home_dayuhao2, tv_home_dayuhao3,tv_home_teyueweixiuzhan;
    private ObservableScrollView scroll;
    private RelativeLayout rela_main_title1, rela_main_title2, rela_home_title, rela_weather;
    private UpdateAppManager updateManager;
    private LocationClient mLocationClient = null;
    public MyLocationListener mMyLocationListener;
    public Vibrator mVibrator;
    private LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
    private String tempcoor = "bd09ll";
    //tempcoor="gcj02";//国家测绘局标准
    //tempcoor="bd09ll";//百度经纬度标准
    //tempcoor="bd09";//百度墨卡托标准
    private ImageView iv_weather_weather, iv_home1, iv_home2, iv_home3, iv_home5, iv_home6, iv_home7, iv_home8;
    private TextView tv_weather_qiwen, tv_weather_chaiyou, tv_weather_date;

    private GridView grid_shopmall;

    private TextView tv_countdown_hour, tv_countdown_min, tv_countdown_second;
    private List<NewsObj> news_list = new ArrayList<NewsObj>();
    private static int sCount = 0;

    long lastTime = -1;

    Runnable taskRunnable = new Runnable() {

        @Override
        public void run() {
            if (lastTime == -1 || System.currentTimeMillis() - lastTime >= 3000) {
                wheelView.scroll(1, 0);
                wheelView.postDelayed(this, 3000);
                lastTime = System.currentTimeMillis();
            }
        }
    };
    private SharedPreferences sp;
    private boolean hasSetCountDown = false;
    private boolean headlineStarted = false;
    private WheelView wheelView;
    private HeadlinesAdapter adapter1;
    private String name_advert;
    private String now_earnings;
    private String total_earning;
    private String day_remaining;
    private String day_continue;
    private String image_size;
    private String image_advert;
    private String free_remind;
    private String num_driver;
    private String time_end;
    private String time_begin;
    private String day_earnings;
    private String approve_opinions;
    private List<RollsAddObj> rollsadd_list;
    private String image0_advert;
    private String image1_advert;
    private String image2_advert;
    private String image0_approve;
    private String image1_approve;
    private String image2_approve;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mActivity.startService(new Intent(mActivity, Service1.class));
        tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
        mLocationClient = new LocationClient(mActivity);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        mVibrator = (Vibrator) mActivity.getSystemService(Service.VIBRATOR_SERVICE);
        Update();
        initLocation();
        LogUtil.E("签到服务是否运行中:" + PollingUtils.isServiceRun);
        if (!PollingUtils.isServiceRun) {
            startDailySign();
        }
        sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getHome();
        tv_title_home.setText(KaKuApplication.address);
        if ("".equals(tv_title_home.getText().toString())) {
            mLocationClient.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
        mLocationClient.stop();
    }

    View rl_mall;

    private void init(View view) {

        /*好礼商城*/
        rl_mall = view.findViewById(R.id.rl_mall);

        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();

        viewTreeObserver.addOnGlobalLayoutListener(this);

        galleryContainer = (RelativeLayout) view.findViewById(R.id.home_gallery);

        wheelView = (WheelView) view.findViewById(R.id.home_toutiao);
        wheelView.setCyclic(true);
        wheelView.setVisibleItems(1);
        wheelView.addClickingListener(new OnWheelClickedListener() {
            @Override
            public void onItemClicked(WheelView wheel, int itemIndex) {
                if (!Utils.isLogin()) {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                    return;
                }
                MobclickAgent.onEvent(mActivity, "Home4");
                Intent intent = new Intent(mActivity, DiscoveryActivity.class);
                startActivity(intent);
            }
        });

        if (mInflater == null) {
            mInflater = LayoutInflater.from(mActivity);
        }

        tv_title_home = (TextView) view.findViewById(R.id.tv_title_home);
        tv_title_home.requestFocus();
        iv_title_home_left = (ImageView) view.findViewById(R.id.iv_title_home_left);
        iv_title_home_right = (ImageView) view.findViewById(R.id.iv_title_home_right);
        iv_home1 = (ImageView) view.findViewById(R.id.iv_home1);
        iv_home1.setOnClickListener(this);
        iv_home2 = (ImageView) view.findViewById(R.id.iv_home2);
        iv_home2.setOnClickListener(this);
        iv_home3 = (ImageView) view.findViewById(R.id.iv_home3);
        iv_home3.setOnClickListener(this);
        iv_home5 = (ImageView) view.findViewById(R.id.iv_home5);
        iv_home5.setOnClickListener(this);
        iv_home6 = (ImageView) view.findViewById(R.id.iv_home6);
        iv_home6.setOnClickListener(this);
        iv_home7 = (ImageView) view.findViewById(R.id.iv_home7);
        iv_home7.setOnClickListener(this);
        iv_home8 = (ImageView) view.findViewById(R.id.iv_home8);
        iv_home8.setOnClickListener(this);
        ll_title_home_left = (LinearLayout) view.findViewById(R.id.ll_title_home_left);
        ll_title_home_left.setOnClickListener(this);
        ll_title_home_right = (LinearLayout) view.findViewById(R.id.ll_title_home_right);
        ll_title_home_right.setOnClickListener(this);
        line_home_fuwuzhan = (LinearLayout) view.findViewById(R.id.line_home_fuwuzhan);
        lv_home_item = (ListView) view.findViewById(R.id.lv_home_item);
        tv_home_dayuhao2 = (TextView) view.findViewById(R.id.tv_home_dayuhao2);
        tv_home_dayuhao2.setOnClickListener(this);
        tv_home_dayuhao3 = (TextView) view.findViewById(R.id.tv_home_dayuhao3);
        tv_home_dayuhao3.setOnClickListener(this);
        tv_home_teyueweixiuzhan = (TextView) view.findViewById(R.id.tv_home_teyueweixiuzhan);
        grid_shopmall = (GridView) view.findViewById(R.id.grid_shopmall);
        scroll = (ObservableScrollView) view.findViewById(R.id.sv_home_main);
        scroll.setScrollViewListener(this);

        rela_main_title2 = (RelativeLayout) view.findViewById(R.id.rela_main_title2);
        rela_main_title1 = (RelativeLayout) view.findViewById(R.id.rela_main_title);
        rela_home_title = (RelativeLayout) view.findViewById(R.id.rela_home_title);
        rela_home_title.setOnClickListener(this);
        rela_main_title2.setBackgroundColor(Color.argb(0, 0, 0, 0));

        lv_home_item.setFocusable(false);
        lv_home_item.setOnItemClickListener(this);
        initSeckillViews(view);
    }

    private void startDailySign() {
        boolean isRemindChecked = KaKuApplication.sp.getBoolean("isRemindChecked", false);

        LogUtil.E("isRemindChecked:" + isRemindChecked);

        if (isRemindChecked) {
            //每日提醒签到
            PollingUtils.startPollingService(mActivity, 5, DailyRemindService.class, DailyRemindService.ACTION);
        }
    }

    private long currentTime;
    private long startTime;
    private long timeRemains;


    private void initSeckillViews(View view) {

        tv_countdown_hour = (TextView) view.findViewById(R.id.tv_countdown_hour);
        tv_countdown_min = (TextView) view.findViewById(R.id.tv_countdown_min);
        tv_countdown_second = (TextView) view.findViewById(R.id.tv_countdown_second);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Utils.NoNet(mActivity);
        if (Utils.Many()) {
            return;
        }
        if (!Utils.isLogin()) {
            startActivity(new Intent(mActivity, LoginActivity.class));
            return;
        }
        int id = v.getId();
        if (R.id.ll_title_home_left == id) {
            MobclickAgent.onEvent(mActivity, "WoDeAiChe");
            if (TextUtils.isEmpty(Utils.getIdCar())) {
                startActivity(new Intent(mActivity, MyCarActivity.class));
                return;
            }
            GoToLeft();
        } else if (R.id.ll_title_home_right == id) {
            MobclickAgent.onEvent(mActivity, "SouSuo");
            if (TextUtils.isEmpty(Utils.getIdCar())) {
                startActivity(new Intent(mActivity, MyCarActivity.class));
                return;
            }
            GoToRight();
        } else if (R.id.tv_home_dayuhao2 == id) {
            if (TextUtils.isEmpty(Utils.getIdCar())) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提示");
                builder.setMessage("添加爱车，可精准推荐服务站，还送200积分哦~");
                builder.setNegativeButton("是", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(mActivity, PinPaiXuanZeActivity.class));
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
                return;
            }
            GoToPinPaiFuWuZhan();
        } else if (R.id.tv_home_dayuhao3 == id) {
            /*todo 进入车品商城页面*/
            MobclickAgent.onEvent(mActivity, "CarShop");
            GoTOShopingMall();
        } else if (R.id.rela_home_title == id) {
            GoToTitle();
        } else if (R.id.iv_home1 == id) {
            //一键呼
            MobclickAgent.onEvent(mActivity, "Home2");
            startActivity(new Intent(getActivity(), SOSActivity.class));
        } else if (R.id.iv_home2 == id) {
            //查违章
            MobclickAgent.onEvent(mActivity, "Home1");
            startActivity(new Intent(mActivity, IllegalQueryActivity.class));
        } else if (R.id.iv_home3 == id) {
            //问养修
            LogUtil.showShortToast(mActivity, "敬请期待");
            return;
            //MobclickAgent.onEvent(mActivity, "Home3");
            // startActivity(new Intent(mActivity,InterlocutionActivity.class));
        }  else if (R.id.iv_home8 == id) {
            //车贴
            MobclickAgent.onEvent(mActivity, "Home9");
            GetAdd();
        } else if (R.id.iv_home5 == id) {
            //秒杀
            MobclickAgent.onEvent(mActivity, "Home6");
            /*Intent intent = new Intent(mActivity, HomeSeckillActivity.class);
            intent.putExtra("clickTabState", seckillIndex);
            mActivity.startActivity(intent);*/
            startActivity(new Intent(mActivity,CheTieListActivity.class));
        } else if (R.id.iv_home7 == id) {
            //签到
            MobclickAgent.onEvent(mActivity, "Home8");
            startActivity(new Intent(mActivity, DailySignActivity.class));
        } else if (R.id.iv_home6 == id) {
            //抽奖
            MobclickAgent.onEvent(mActivity, "Home7");
            startActivity(new Intent(mActivity, ChouJiangActivity.class));
        }
    }

    private void GoTOShopingMall() {
        Utils.NoNet(getActivity());
        startActivity(new Intent(getActivity(), ShopMallActivity.class));
    }

    private void autoAdvance(List<RollsObj> imgList) {
        // TODO Auto-generated method stub
        if (imgList == null) {
            return;
        }
        if (imgList.size() <= 0) {
            return;
        }
        List<Advertising> list = new ArrayList<Advertising>();

        for (RollsObj obj : imgList) {
            Advertising advertising = new Advertising(obj.getImage_roll(), obj.getUrl_roll(), null);
            advertising.setPicURL(KaKuApplication.qian_zhui + obj.getImage_roll());
            list.add(advertising);
        }

        if (list.size() > 0) {
            mGalleryHelper = new AdGalleryHelper(mActivity, list, Constants.AUTO_SCROLL_DURATION, true, false,true);
            galleryContainer.addView(mGalleryHelper.getLayout());

            mGalleryHelper.startAutoSwitch();
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        flag_frag = true;
        if (mGalleryHelper != null) {
            mGalleryHelper.stopAutoSwitch();
        }
        wheelView.removeCallbacks(taskRunnable);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Utils.NoNet(mActivity);
        if (!Utils.isLogin()) {
            startActivity(new Intent(mActivity, LoginActivity.class));
            return;
        }
        if (Utils.Many()) {
            return;
        }

        int parentId = parent.getId();
        if (TextUtils.isEmpty(Utils.getIdCar())) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("提示");
            if (R.id.lv_home_item == parentId) {
                builder.setMessage("添加爱车，可精准推荐服务站，还送200积分哦~");
            }

            builder.setNegativeButton("是", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(mActivity, PinPaiXuanZeActivity.class));
                }
            });
            builder.setCancelable(false);
            builder.create().show();
            return;
        }

        if (R.id.lv_home_item == parentId) {
            MobclickAgent.onEvent(mActivity, "BaoYang");
            KaKuApplication.id_shop = list_wxz.get(position).getId_shop();
        }

        GoToShopDetail();
    }

    @Override
    public void onGlobalLayout() {
        getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    /**
     * 获取首页的数据
     */
    private void getHome() {
        Utils.NoNet(mActivity);
        showProgressDialog();
        HomeReq req = new HomeReq();
        req.code = "8001";
        req.id_driver = Utils.getIdDriver();
        req.lat = Utils.getLat();
        req.lon = Utils.getLon();
        KaKuApiProvider.home(req, new BaseCallback<HomeResp>(HomeResp.class) {

            @Override
            public void onSuccessful(int statusCode, Header[] headers, HomeResp t) {
                // TODO Auto-generated method stub
                if (flag_frag) {
                    return;
                }

                valueView(t);
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg,
                                  Throwable error) {
                // TODO Auto-generated method stub
                stopProgressDialog();
            }
        });
    }

    private void valueView(HomeResp t) {
        if (t != null) {
            LogUtil.E("home res: " + t.res);
            if (Constants.RES.equals(t.res)) {
                if (TextUtils.equals(t.flag_enter,"N")){
                    if (!"".equals(Utils.getIdCar())){
                        tv_home_teyueweixiuzhan.setText("您的爱车暂未有商家入驻");
                        tv_home_teyueweixiuzhan.setTextColor(getResources().getColor(R.color.black));
                    }
                } else {
                    tv_home_teyueweixiuzhan.setText("特约服务站");
                    tv_home_teyueweixiuzhan.setTextColor(getResources().getColor(R.color.color_red));
                }
                //轮播图
                List<RollsObj> rolls_list = t.rolls;
                if (rolls_list.size() != 0) {
                    autoAdvance(rolls_list);
                }

                if (!headlineStarted) {
                    adapter1 = new HeadlinesAdapter(t.newss);
                    wheelView.setViewAdapter(adapter1);
                    headlineStarted = true;
                    wheelView.postDelayed(taskRunnable, 3000);
                } else {
                    adapter1.notifyDataChangedEvent(t.newss);
                }

                //维修站
                if (!TextUtils.isEmpty(t.image_brand)) {
                    BitmapUtil.getInstance(mActivity).download(iv_title_home_left, KaKuApplication.qian_zhui + t.image_brand);
                }
                list_wxz = t.shops;
                adapter = new ShopItemAdapter(getActivity(), list_wxz);
                lv_home_item.setAdapter(adapter);
                Utils.setListViewHeightBasedOnChildren(lv_home_item);

                //车品商城
                final List<GoodsObj> list = t.goods;
                HomeShopMallAdapter adapter = new HomeShopMallAdapter(getActivity().getApplicationContext(), list);
                int size = list.size();
                int length = DensityUtils.dp2px(mActivity, 88);
                DisplayMetrics dm = new DisplayMetrics();
                mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
                float density = dm.density;
               /* int gridviewWidth = (int) (size * (length + 1) * density);
                int itemWidth = (int) (length * density);*/
                int gridviewWidth = (int) (size * (length + 1));
                int itemWidth = (int) (length);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                grid_shopmall.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
                grid_shopmall.setColumnWidth(itemWidth); // 设置列表项宽
                grid_shopmall.setHorizontalSpacing(0); // 设置列表项水平间距
                grid_shopmall.setStretchMode(GridView.NO_STRETCH);
                grid_shopmall.setNumColumns(size); // 设置列数量=列表集合数
                grid_shopmall.setAdapter(adapter);

                grid_shopmall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                     /*todo 添加点击详情*/
                        Utils.NoNet(mActivity);
                        if (!Utils.isLogin()) {
                            startActivity(new Intent(mActivity, LoginActivity.class));
                            return;
                        }
                        if (Utils.Many()) {
                            return;
                        }
                        openDetailUrl(list.get(position).getId_goods());
                    }
                });

                setSeckillDetail(t.seckills, t.note, t.time_now);
            } else {
                LogUtil.showShortToast(mActivity, t.msg);
            }
        }
    }

    private void setSeckillDetail(List<SeckillObj> seckills, String note, String time_now) {

        if (!hasSetCountDown) {
            initCountDownTimer();
            setCountDownTime(note, time_now);
            hasSetCountDown = true;
        }
    }

    private void openDetailUrl(String productId) {
        //Intent intent = new Intent(getActivity(), TruckProductDetailActivity.class);
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra("productId", productId);
        startActivity(intent);
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
                                int oldx, int oldy) {
        // TODO Auto-generated method stub
        if (y < 100) {

            if (y == 0) {
                rela_main_title1.setBackgroundResource(R.drawable.gradlient_black);
            } else if (y == 10) {
                rela_main_title1.setBackgroundResource(R.drawable.gradlient_black1);
            } else if (y == 20) {
                rela_main_title1.setBackgroundResource(R.drawable.gradlient_black2);
            } else if (y == 30) {
                rela_main_title1.setBackgroundResource(R.drawable.gradlient_black3);
            } else if (y == 40) {
                rela_main_title1.setBackgroundResource(R.drawable.gradlient_black4);
            } else if (y == 50) {
                rela_main_title1.setBackgroundResource(R.drawable.gradlient_black5);
            } else if (y == 60) {
                rela_main_title1.setBackgroundResource(R.drawable.gradlient_black6);
            } else if (y == 70) {
                rela_main_title1.setBackgroundResource(R.drawable.gradlient_black7);
            } else if (y == 80) {
                rela_main_title1.setBackgroundResource(R.drawable.gradlient_black8);
            } else if (y == 90) {
                rela_main_title1.setBackgroundResource(R.drawable.gradlient_black9);
            }

        } else {
            rela_main_title1.setBackgroundResource(R.drawable.gradlient_black9);
        }

        int num = Math.min(y, 260);
        int alpha = Math.round(230f / 260 * num);
        rela_main_title2.setBackgroundColor(Color.argb(alpha, 230, 0, 0));
    }

    public void GoToLeft() {
        Intent intent = new Intent(getActivity(), MyCarActivity.class);
        startActivity(intent);
    }

    public void GoToRight() {
        Intent intent = new Intent(getActivity(), SouSuoActivity.class);
        startActivity(intent);
    }

    public void GoToTitle() {
        Intent intent = new Intent(getActivity(), TitleActivity.class);
        if (!TextUtils.isEmpty(KaKuApplication.city)) {
            intent.putExtra("city", KaKuApplication.city);
        } else {
            intent.putExtra("city", location == null ? "" : location.getCity());
        }
        startActivity(intent);
    }

    public void GoToPinPaiFuWuZhan() {
        startActivity(new Intent(getActivity(), PinPaiFuWuZhanActivity.class));
    }

    public void GoToShopDetail() {
        Intent intent = new Intent(mActivity, ShopDetailActivity.class);
        startActivity(intent);
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setLocationMode(tempMode);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType(tempcoor);//可选，默认gcj02，设置返回的定位结果坐标系，
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setIsNeedAddress(true);
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(option);
    }

    private BDLocation location;

    /**
     * 实现实时位置回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");// 位置语义化信息
            sb.append(location.getLocationDescribe());
            //sb.toString();
            if (!TextUtils.isEmpty(location.getAddrStr())) {
                KaKuApplication.address = location.getAddrStr();
                KaKuApplication.addr_string = location.getAddrStr();
                tv_title_home.setText(KaKuApplication.address);
            }
            if (location.getLatitude() == 0) {
                SharedPreferences.Editor editor = KaKuApplication.editor;
                editor.putString(Constants.LAT, "");
                editor.putString(Constants.LON, "");
                editor.commit();
            }
            if (location.getLatitude() > 0) {
                SharedPreferences.Editor editor = KaKuApplication.editor;
                editor.putString(Constants.LAT, location.getLatitude() + "");
                editor.putString(Constants.LON, location.getLongitude() + "");
                editor.commit();
            }
            if (!TextUtils.isEmpty(location.getAddrStr()) && location.getLatitude() > 0) {
                mLocationClient.stop();
            }
        }
    }

    public void Update() {
        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        CheckUpdateReq req = new CheckUpdateReq();
        req.code = "10010";
        req.version_android = versionName;
        KaKuApiProvider.checkUpdate(req, new BaseCallback<CheckUpdateResp>(CheckUpdateResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, CheckUpdateResp t) {
                if (t != null) {
                    LogUtil.E("update res: " + t.res);
                    if (Constants.RES_ONE.equals(t.res)) {
                        android_url = t.android_url;
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("发现新版本!");
                        //builder.setMessage(getResources().getString(R.string.update));
                        builder.setNegativeButton("立即更新", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                //DownLoad(android_url);
                                updateManager = new UpdateAppManager(getActivity(), android_url);
                                updateManager.checkUpdateInfo();
                            }
                        });

                        builder.setPositiveButton("稍后再说", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    } else if (Constants.RES_NINE.equals(t.res)) {
                        android_url = t.android_url;
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("发现新版本！");
                        //builder.setMessage(getResources().getString(R.string.update));
                        builder.setCancelable(false);
                        builder.setNegativeButton("立即更新", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                //DownLoad(android_url);
                                updateManager = new UpdateAppManager(getActivity(), android_url);
                                updateManager.checkUpdateInfo();
                            }
                        });

                        builder.create().show();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //清空秒杀倒计时handler
        handler.removeCallbacks(runnable);
    }

//todo 秒杀区域


    private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            ComputeTime();

            if (msecond <= 9) {
                tv_countdown_second.setText("0" + String.valueOf(msecond));
            } else {
                tv_countdown_second.setText(String.valueOf(msecond));
            }
            if (mmin <= 9) {
                tv_countdown_min.setText("0" + String.valueOf(mmin));
            } else {

                tv_countdown_min.setText(String.valueOf(mmin));
            }
            if (mhour <= 9) {
                tv_countdown_hour.setText("0" + String.valueOf(mhour));
            } else {
                tv_countdown_hour.setText(String.valueOf(mhour));
            }


            handler.postDelayed(this, 1000);
        }
    };

    private long mday, mhour, mmin, msecond;

    /**
     * 倒计时计算
     */
    private void ComputeTime() {
        msecond--;

        if (msecond < 0) {
            mmin--;
            msecond = 59;
            if (mmin < 0) {
                mmin = 59;
                mhour--;
                if (mhour < 0) {
                    // 倒计时结束，一天有24个小时
                    mhour = 23;
                    mday--;

                }
            }

        }

    }

    /**
     * @param
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @author fy.zhang
     */
    public void formatDuring(long mss) {
        LogUtil.E("logn:" + mss);
        mday = mss / (1000 * 60 * 60 * 24);
        mhour = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        mmin = (mss % (1000 * 60 * 60)) / (1000 * 60);
        msecond = (mss % (1000 * 60)) / 1000;

        /*mhour = 3;
        mmin = 1;
        msecond = 20;*/

        /*return hours + ":" + minutes + ":" + seconds;*/
    }

    private int seckillIndex = 0;

    private void setCountDownTime(String note, String time_now) {
        String tempNote = "12";

        if (Integer.parseInt(note) != 120) {
            tempNote = note;

            Integer tempInt = Integer.parseInt(tempNote);
            if (tempInt == 12) {
                seckillIndex = 0;
            } else if (tempInt == 14) {
                seckillIndex = 1;
            } else if (tempInt == 16) {
                seckillIndex = 2;
            }
            //tv_seckill_note.setText(" · " + tempNote + "点场");
        } else {
            tempNote = note.substring(0, note.length() - 1);
            //tv_seckill_note.setText(" · 明日" + tempNote + "点场");
            seckillIndex = 0;
        }

        String[] nowDateTime = time_now.split(" ");


        //startTime=
        /*Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");*/
        //抢购开始的时间
        String startDate = nowDateTime[0] + " " + tempNote + ":00:00";

        //先把字符串转成Date类型
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //抢购开始时间
        Date newStartDate = new Date();
        //当前时间
        Date nowDate = new Date();
//此处会抛异常
        try {
            newStartDate = sdf.parse(startDate);

            nowDate = sdf.parse(time_now);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//获取开始的毫秒数
        startTime = newStartDate.getTime();

        currentTime = nowDate.getTime();
        //根据抢购开始时间和服务器传来的当前时间计算距离开始还有多少毫秒
        timeRemains = startTime - currentTime;
        if (timeRemains + 7200000 < 0) {
            //如果timeRemains小于0，则取第二天的同一时刻
            timeRemains = timeRemains + 86400000;
        }
        if (timeRemains < 0) {
            timeRemains = 7200000 + timeRemains;
        }
        formatDuring(timeRemains);
        handler.postDelayed(runnable, 1000);
    }

    private void initCountDownTimer() {
        tv_countdown_hour.setText("00");
        tv_countdown_min.setText("00");
        tv_countdown_second.setText("00");
    }

    public void GetAdd(){
        showProgressDialog();
        GetAddReq req = new GetAddReq();
        req.code = "60011";
        req.id_driver = Utils.getIdDriver();
        req.id_advert = "1";
        KaKuApiProvider.GetAdd(req, new BaseCallback<GetAddResp>(GetAddResp.class) {

            @Override
            public void onSuccessful(int statusCode, Header[] headers, GetAddResp t) {
                if (t != null) {
                    LogUtil.E("getadd res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        name_advert = t.advert.getName_advert();
                        day_earnings = t.advert.getDay_earnings();
                        time_begin = t.advert.getTime_begin();
                        time_end = t.advert.getTime_end();
                        num_driver = t.advert.getNum_driver();
                        free_remind = t.advert.getFree_remind();
                        image_advert = t.advert.getImage_advert();
                        image_size = t.advert.getImage_size();
                        day_continue = t.advert.getDay_continue();
                        day_remaining = t.advert.getDay_remaining();
                        total_earning = t.advert.getTotal_earnings();
                        approve_opinions = t.advert.getApprove_opinions();
                        now_earnings = t.advert.getNow_earnings();
                        image0_advert = t.advert.getImage0_advert();
                        image1_advert = t.advert.getImage1_advert();
                        image2_advert = t.advert.getImage2_advert();
                        image0_approve = t.advert.getImage0_approve();
                        image1_approve = t.advert.getImage1_approve();
                        image2_approve = t.advert.getImage2_approve();
                        rollsadd_list = t.rolls;
                        GoToAdd(t.advert.getFlag_type());
                    } else {
                        if (Constants.RES_TEN.equals(t.res)){
                            Utils.Exit(mActivity);
                        }
                        LogUtil.showShortToast(mActivity, t.msg);
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

    public void GoToAdd(String flag_type){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        LogUtil.E("flag:"+flag_type);

        if ("N".equals(flag_type)){
            intent.setClass(mActivity,Add_NActivity.class);
        } else if ("Y".equals(flag_type)){
            intent.setClass(mActivity,Add_YActivity.class);
        } else if ("E".equals(flag_type)){
            intent.setClass(mActivity,Add_EActivity.class);
        } else if ("I".equals(flag_type)){
            intent.setClass(mActivity,Add_IActivity.class);
        } else if ("F".equals(flag_type)){
            intent.setClass(mActivity,Add_FActivity.class);
        } else if ("P".equals(flag_type)){
            intent.setClass(mActivity,Add_PActivity.class);
        }
        bundle.putString("name_advert",name_advert);
        bundle.putString("day_earnings",day_earnings);
        bundle.putString("time_begin",time_begin);
        bundle.putString("time_end",time_end);
        bundle.putString("free_remind",free_remind);
        bundle.putString("num_driver",num_driver);
        bundle.putString("image_advert",image_advert);
        bundle.putString("image_size",image_size);
        bundle.putString("day_continue",day_continue);
        bundle.putString("day_remaining",day_remaining);
        bundle.putString("total_earning",total_earning);
        bundle.putString("now_earnings",now_earnings);
        bundle.putString("approve_opinions",approve_opinions);
        bundle.putString("image0_advert",image0_advert);
        bundle.putString("image1_advert",image1_advert);
        bundle.putString("image2_advert",image2_advert);
        bundle.putString("image0_approve",image0_approve);
        bundle.putString("image1_approve",image1_approve);
        bundle.putString("image2_approve",image2_approve);
        bundle.putString("flag_type",flag_type);
        bundle.putSerializable("rollsadd_list", (Serializable) rollsadd_list);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}

