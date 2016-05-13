package com.yichang.kaku.home;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseFragment;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.guangbo.GuangBoActivity;
import com.yichang.kaku.home.baoyang.BaoYangActivity;
import com.yichang.kaku.home.baoyang.BaoYangHuoDongActivity;
import com.yichang.kaku.home.baoyang.YellowOilActivity;
import com.yichang.kaku.home.call.CallActivity;
import com.yichang.kaku.home.dingwei.TitleActivity;
import com.yichang.kaku.home.faxian.DiscoveryDetailActivity;
import com.yichang.kaku.home.giftmall.ProductDetailActivity;
import com.yichang.kaku.home.giftmall.ShopMallActivity;
import com.yichang.kaku.home.huafei.HuaFeiActivity;
import com.yichang.kaku.home.jiuyuan.SOSActivity;
import com.yichang.kaku.home.mycar.MyCarActivity;
import com.yichang.kaku.home.qiandao.DailySignActivity;
import com.yichang.kaku.home.sousuo.SouSuoActivity;
import com.yichang.kaku.home.weizhang.IllegalQueryActivity;
import com.yichang.kaku.member.login.LoginActivity;
import com.yichang.kaku.obj.HotActivityObj;
import com.yichang.kaku.obj.NewsObj;
import com.yichang.kaku.obj.RollsObj;
import com.yichang.kaku.request.HomeReq;
import com.yichang.kaku.response.HomeResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.wheelview.OnWheelClickedListener;
import com.yichang.kaku.view.wheelview.WheelView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yichang.kaku.zhaohuo.ZhaoHuoActivity;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements OnClickListener {

    private RelativeLayout galleryContainer;
    private AdGalleryHelper mGalleryHelper;
    private Activity mActivity;
    private boolean flag_frag = false;
    private TextView tv_title_home, tv_home_city;
    private ImageView iv_title_home_brand, iv_homecall, iv_homeshopmall;
    private LinearLayout ll_title_home_left, ll_title_home_right;
    private RelativeLayout rela_main_title1, rela_main_title2, rela_home_title;
    private LocationClient mLocationClient = null;
    public MyLocationListener mMyLocationListener;
    private LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
    private String tempcoor = "bd09ll";
    private ImageView iv_home11, iv_home12, iv_home13, iv_home14;
    private ImageView iv_home21, iv_home22, iv_home23, iv_home24;
    private ImageView iv_home31, iv_home32, iv_home33;
    private ImageView iv_home41, iv_home42, iv_home43, iv_home44;
    private List<NewsObj> news_list = new ArrayList<>();
    private static int sCount = 0;
    private ImageView iv_home_rxsp1, iv_home_rxsp2, iv_home_rxsp3;
    private boolean isShowProgress;
    private boolean headlineStarted = false;
    private WheelView wheelView;
    private HeadlinesAdapter adapter1;
    private String productId1, productId2, productId3;
    private ListView lv_home;
    private List<HotActivityObj> list_activity = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
        mLocationClient = new LocationClient(mActivity);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        initLocation();
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
        tv_home_city.setText(KaKuApplication.address);
        if ("".equals(tv_home_city.getText().toString()) || "请选择".equals(tv_home_city.getText().toString())) {
            mLocationClient.start();
        }
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        mLocationClient.stop();
    }

    private void init(View view) {

        galleryContainer = (RelativeLayout) view.findViewById(R.id.home_gallery);

        DisplayMetrics outMetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);

        double anInt;
        anInt = (outMetrics.widthPixels) / 3.5;
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) anInt);
        galleryContainer.setLayoutParams(params1);

        double anInt2;
        anInt2 = (outMetrics.widthPixels) / 4;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams((int) anInt2, (int) anInt2);

        double anInt3;
        anInt3 = (outMetrics.widthPixels) / 2;
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams((int) anInt3, (int) anInt2);

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
                MobclickAgent.onEvent(mActivity, "toutiao");
                KaKuApplication.id_news = news_list.get(itemIndex).getId_news();
                Intent intent = new Intent(mActivity, DiscoveryDetailActivity.class);
                startActivity(intent);
            }
        });

        tv_title_home = (TextView) view.findViewById(R.id.tv_title_home);
        tv_title_home.requestFocus();
        iv_title_home_brand = (ImageView) view.findViewById(R.id.iv_title_home_brand);
        iv_home11 = (ImageView) view.findViewById(R.id.iv_home11);
        iv_home11.setOnClickListener(this);
        iv_home12 = (ImageView) view.findViewById(R.id.iv_home12);
        iv_home12.setOnClickListener(this);
        iv_home13 = (ImageView) view.findViewById(R.id.iv_home13);
        iv_home13.setOnClickListener(this);
        iv_home14 = (ImageView) view.findViewById(R.id.iv_home14);
        iv_home14.setOnClickListener(this);
        iv_home21 = (ImageView) view.findViewById(R.id.iv_home21);
        iv_home21.setOnClickListener(this);
        iv_home22 = (ImageView) view.findViewById(R.id.iv_home22);
        iv_home22.setOnClickListener(this);
        iv_home23 = (ImageView) view.findViewById(R.id.iv_home23);
        iv_home23.setOnClickListener(this);
        iv_home24 = (ImageView) view.findViewById(R.id.iv_home24);
        iv_home24.setOnClickListener(this);
        iv_home31 = (ImageView) view.findViewById(R.id.iv_home31);
        iv_home31.setOnClickListener(this);
        iv_home32 = (ImageView) view.findViewById(R.id.iv_home32);
        iv_home32.setOnClickListener(this);
        iv_home33 = (ImageView) view.findViewById(R.id.iv_home33);
        iv_home33.setOnClickListener(this);
        iv_home41 = (ImageView) view.findViewById(R.id.iv_home41);
        iv_home41.setOnClickListener(this);
        iv_home42 = (ImageView) view.findViewById(R.id.iv_home42);
        iv_home42.setOnClickListener(this);
        iv_home43 = (ImageView) view.findViewById(R.id.iv_home43);
        iv_home43.setOnClickListener(this);
        iv_home44 = (ImageView) view.findViewById(R.id.iv_home44);
        iv_home44.setOnClickListener(this);
        iv_home21.setLayoutParams(params2);
        iv_home22.setLayoutParams(params2);
        iv_home23.setLayoutParams(params2);
        iv_home24.setLayoutParams(params2);
        iv_home31.setLayoutParams(params2);
        iv_home32.setLayoutParams(params3);
        iv_home33.setLayoutParams(params2);
        iv_home41.setLayoutParams(params2);
        iv_home42.setLayoutParams(params2);
        iv_home43.setLayoutParams(params2);
        iv_home44.setLayoutParams(params2);
        iv_home_rxsp1 = (ImageView) view.findViewById(R.id.iv_home_rxsp1);
        iv_home_rxsp1.setOnClickListener(this);
        iv_home_rxsp2 = (ImageView) view.findViewById(R.id.iv_home_rxsp2);
        iv_home_rxsp2.setOnClickListener(this);
        iv_home_rxsp3 = (ImageView) view.findViewById(R.id.iv_home_rxsp3);
        iv_home_rxsp3.setOnClickListener(this);
        ll_title_home_left = (LinearLayout) view.findViewById(R.id.ll_title_home_left);
        ll_title_home_left.setOnClickListener(this);
        ll_title_home_right = (LinearLayout) view.findViewById(R.id.ll_title_home_right);
        ll_title_home_right.setOnClickListener(this);
        tv_home_city = (TextView) view.findViewById(R.id.tv_home_city);
        tv_home_city.setOnClickListener(this);
        iv_homeshopmall = (ImageView) view.findViewById(R.id.iv_homeshopmall);
        iv_homeshopmall.setOnClickListener(this);
        iv_homecall = (ImageView) view.findViewById(R.id.iv_homecall);
        iv_homecall.setOnClickListener(this);
        rela_main_title2 = (RelativeLayout) view.findViewById(R.id.rela_main_title2);
        rela_main_title1 = (RelativeLayout) view.findViewById(R.id.rela_main_title);
        rela_home_title = (RelativeLayout) view.findViewById(R.id.rela_home_title);
        rela_home_title.setOnClickListener(this);
        rela_main_title2.setBackgroundColor(Color.argb(0, 0, 0, 0));
        lv_home = (ListView) view.findViewById(R.id.lv_home);
        lv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!Utils.isLogin()) {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                    return;
                }
                KaKuApplication.id_baoyangshop = list_activity.get(position).getId_shop();
                startActivity(new Intent(getActivity(), BaoYangHuoDongActivity.class));
            }
        });
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
            if ("".equals(Utils.getLat())) {
                LogUtil.showShortToast(mActivity, "请打开手机定位");
                return;
            }
            GoToTitle();
        } else if (R.id.ll_title_home_right == id) {
            GoToRight();
        } else if (R.id.iv_homeshopmall == id) {
            startActivity(new Intent(mActivity, ShopMallActivity.class));
        } else if (R.id.rela_home_title == id) {
            KaKuApplication.flag_addcar = "hometitle";
            GoToLeft();
        } else if (R.id.iv_home11 == id) {
            //换机油
            KaKuApplication.yellowoil = "14";
            MobclickAgent.onEvent(mActivity, "11");
            startActivity(new Intent(mActivity, BaoYangActivity.class));
        } else if (R.id.iv_home12 == id) {
            //打黄油
            KaKuApplication.yellowoil = "11";
            MobclickAgent.onEvent(mActivity, "12");
            startActivity(new Intent(mActivity, YellowOilActivity.class));
        } else if (R.id.iv_home13 == id) {
            //轮胎服务
            KaKuApplication.yellowoil = "12";
            MobclickAgent.onEvent(mActivity, "13");
            startActivity(new Intent(mActivity, YellowOilActivity.class));
        } else if (R.id.iv_home14 == id) {
            //优惠券
            MobclickAgent.onEvent(mActivity, "14");
            Intent intent = new Intent(mActivity, YouHuiQuanLingActivity.class);
           /* intent.putExtra("url_coupon", url_coupon);
            intent.putExtra("url_share", url_share);*/
            startActivity(intent);
        } else if (R.id.iv_home21 == id) {
            //保轮
            KaKuApplication.yellowoil = "13";
            MobclickAgent.onEvent(mActivity, "21");
            startActivity(new Intent(mActivity, YellowOilActivity.class));
        } else if (R.id.iv_home22 == id) {
            //免费电话
            LogUtil.showShortToast(mActivity, "敬请期待");
            return;
            /*MobclickAgent.onEvent(mActivity, "22");
            startActivity(new Intent(getActivity(), CallActivity.class));*/
        } else if (R.id.iv_home23 == id) {
            //违章查询
            MobclickAgent.onEvent(mActivity, "23");
            startActivity(new Intent(mActivity, IllegalQueryActivity.class));
        } else if (R.id.iv_home24 == id) {
            //每日签到
            MobclickAgent.onEvent(mActivity, "24");
            startActivity(new Intent(getActivity(), DailySignActivity.class));
        } else if (R.id.iv_home_rxsp1 == id) {
            MobclickAgent.onEvent(mActivity, "re1");
            KaKuApplication.id_goods = productId1;
            openDetailUrl();
        } else if (R.id.iv_home_rxsp2 == id) {
            MobclickAgent.onEvent(mActivity, "re2");
            KaKuApplication.id_goods = productId2;
            openDetailUrl();
        } else if (R.id.iv_home_rxsp3 == id) {
            MobclickAgent.onEvent(mActivity, "re3");
            KaKuApplication.id_goods = productId3;
            openDetailUrl();
        } else if (R.id.tv_home_city == id) {
            GoToTitle();
        } else if (R.id.iv_homecall == id) {
            Utils.Call(mActivity, Constants.PHONE_KAKU);
        } else if (R.id.iv_home31 == id) {
            //贴车贴
            Utils.GetAdType();
        } else if (R.id.iv_home32 == id) {
            //话费充值
            startActivity(new Intent(getActivity(), HuaFeiActivity.class));
        } else if (R.id.iv_home33 == id) {
            //高速广播
            startActivity(new Intent(getActivity(), GuangBoActivity.class));
        } else if (R.id.iv_home41 == id) {
            //找货源
            startActivity(new Intent(getActivity(), ZhaoHuoActivity.class));
        } else if (R.id.iv_home42 == id) {
            //找货场
            LogUtil.showShortToast(getActivity(), "敬请期待");
            return;
        } else if (R.id.iv_home43 == id) {
            //找救援
            startActivity(new Intent(getActivity(), SOSActivity.class));
        } else if (R.id.iv_home44 == id) {
            //更多
            startActivity(new Intent(getActivity(), MoreActivity.class));
        }
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
            mGalleryHelper = new AdGalleryHelper(mActivity, list, Constants.AUTO_SCROLL_DURATION, true, false, true);
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
    }

    /**
     * 获取首页的数据
     */
    private void getHome() {
        showProgressDialog();
        HomeReq req = new HomeReq();
        req.code = "8001";
        req.id_driver = Utils.getIdDriver();
        req.lat = Utils.getLat();
        req.lon = Utils.getLon();
        KaKuApiProvider.home(req, new KakuResponseListener<HomeResp>(mActivity, HomeResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                // TODO Auto-generated method stub
                LogUtil.E("home res: " + t.res);
                if (flag_frag) {
                    stopProgressDialog();
                    return;
                }
                setData(t);
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {
                stopProgressDialog();
            }

        });
    }

    private void setData(HomeResp t) {
        if (t != null) {
            if (Constants.RES.equals(t.res)) {
                List<RollsObj> rolls_list = t.rolls;
                if (rolls_list.size() != 0) {
                    autoAdvance(rolls_list);
                }

                if (!headlineStarted) {
                    news_list = t.newss;
                    adapter1 = new HeadlinesAdapter(t.newss);
                    wheelView.setViewAdapter(adapter1);
                    headlineStarted = true;
                } else {
                    adapter1.notifyDataChangedEvent(t.newss);
                }

                DisplayMetrics outMetrics = new DisplayMetrics();
                mActivity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
                list_activity = t.activitys_hot;
                HomeLvAdapter adapter = new HomeLvAdapter(mActivity, list_activity, outMetrics.widthPixels / 2);
                lv_home.setAdapter(adapter);
                Utils.setListViewHeightBasedOnChildren(lv_home);

                productId1 = t.goods_hot.get(0).getId_goods();
                productId2 = t.goods_hot.get(1).getId_goods();
                productId3 = t.goods_hot.get(2).getId_goods();
                BitmapUtil.getInstance(mActivity).download(iv_home_rxsp1, KaKuApplication.qian_zhui + t.goods_hot.get(0).getImage_goods());
                BitmapUtil.getInstance(mActivity).download(iv_home_rxsp2, KaKuApplication.qian_zhui + t.goods_hot.get(1).getImage_goods());
                BitmapUtil.getInstance(mActivity).download(iv_home_rxsp3, KaKuApplication.qian_zhui + t.goods_hot.get(2).getImage_goods());
                BitmapUtil.getInstance(mActivity).download(iv_title_home_brand, KaKuApplication.qian_zhui + t.image_brand);
                if ("".equals(t.id_no)) {
                    iv_title_home_brand.setVisibility(View.GONE);
                    tv_title_home.setBackgroundResource(R.drawable.home__top);
                } else {
                    iv_title_home_brand.setVisibility(View.VISIBLE);
                    tv_title_home.setText(t.id_no);
                }
            } else {
                LogUtil.showShortToast(mActivity, t.msg);
            }
        }
    }

    private void openDetailUrl() {
        MobclickAgent.onEvent(getActivity(), "ChePinDetail");
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        startActivity(intent);
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
            if (!TextUtils.isEmpty(location.getAddrStr())) {
                KaKuApplication.address = location.getCity();
                KaKuApplication.addr_string = location.getAddrStr();
                tv_home_city.setText(KaKuApplication.address);
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
                mLocationClient.stop();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}

