package com.yichang.kaku.home.baoyang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.home.dingwei.Title2Activity;
import com.yichang.kaku.home.giftmall.ShopMallActivity;
import com.yichang.kaku.home.mycar.MyCarActivity;
import com.yichang.kaku.obj.WorkerObj;
import com.yichang.kaku.obj.YellowOilObj;
import com.yichang.kaku.request.BaoYangOrderCancleReq;
import com.yichang.kaku.request.HuJiaoFuWuReq;
import com.yichang.kaku.request.XuanZeFuWuReq;
import com.yichang.kaku.request.YellowOilReq;
import com.yichang.kaku.response.ExitResp;
import com.yichang.kaku.response.HuJiaoFuWuResp;
import com.yichang.kaku.response.XuanZeFuWuResp;
import com.yichang.kaku.response.YellowOilResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.CircularImage;
import com.yichang.kaku.view.widget.MyGridView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class YellowOilActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    MapView mMapView;
    BaiduMap mBaiduMap;
    boolean isFirstLoc = true;// 是否首次定位
    private InfoWindow mInfoWindow0;
    private TextView btn_yellowoil, text_yellowoil_shifukuan, tv_yellow_xianxiazhifuyouhui;
    private String phone;
    private MyGridView gv_yellowoil;
    private List<YellowOilObj> list_yellow = new ArrayList<>();
    private YellowOilAdapter adapter;
    private TextView tv_yellowoil_addr, tv_yellowoil_name, tv_yellowoil_dan;
    private RelativeLayout rela_yellowoil_addr, rela_yellowoil_top;
    private View view_yellowoil1, view_yellowoil0;
    private ReverseGeoCodeOption reoption = new ReverseGeoCodeOption();
    GeoCoder coder = GeoCoder.newInstance();
    private ImageView iv_yellowoil_call, tv_yellowoil_top, iv_yellowoil_yellowtext;
    private ImageView iv_yellowoil_dahuangyou, iv_yellowoil_luntai, iv_yellowoil_baolun;
    private CircularImage iv_yellowoil_head;
    private RatingBar star_yellowoil_star;
    Marker mMarker2, mMarker;
    private List<WorkerObj> worker_list = new ArrayList<>();
    private String id_service = "", var_lon, var_lat;
    BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.drawable.weixiugong);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yellowoil);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("打黄油");
        right = (TextView) findViewById(R.id.tv_right);
        right.setOnClickListener(this);
        KaKuApplication.success_type = "yellow";
        KaKuApplication.flag_addcar = "dahuangyou";
        if ("11".equals(KaKuApplication.yellowoil)) {
            title.setText("打黄油");
        } else if ("12".equals(KaKuApplication.yellowoil)) {
            title.setText("轮胎服务");
        } else if ("13".equals(KaKuApplication.yellowoil)) {
            title.setText("保轮");
        }
        text_yellowoil_shifukuan = (TextView) findViewById(R.id.text_yellowoil_shifukuan);
        btn_yellowoil = (TextView) findViewById(R.id.btn_yellowoil);
        btn_yellowoil.setOnClickListener(this);
        gv_yellowoil = (MyGridView) findViewById(R.id.gv_yellowoil);
        gv_yellowoil.setOnItemClickListener(this);
        tv_yellowoil_addr = (TextView) findViewById(R.id.tv_yellowoil_addr);
        tv_yellow_xianxiazhifuyouhui = (TextView) findViewById(R.id.tv_yellow_xianxiazhifuyouhui);
        tv_yellowoil_name = (TextView) findViewById(R.id.tv_yellowoil_name);
        tv_yellowoil_dan = (TextView) findViewById(R.id.tv_yellowoil_dan);
        tv_yellowoil_top = (ImageView) findViewById(R.id.tv_yellowoil_top);
        view_yellowoil1 = findViewById(R.id.view_yellowoil1);
        view_yellowoil0 = findViewById(R.id.view_yellowoil0);
        star_yellowoil_star = (RatingBar) findViewById(R.id.star_yellowoil_star);
        iv_yellowoil_head = (CircularImage) findViewById(R.id.iv_yellowoil_head);
        iv_yellowoil_yellowtext = (ImageView) findViewById(R.id.iv_yellowoil_yellowtext);
        iv_yellowoil_dahuangyou = (ImageView) findViewById(R.id.iv_yellowoil_dahuangyou);
        iv_yellowoil_luntai = (ImageView) findViewById(R.id.iv_yellowoil_luntai);
        iv_yellowoil_baolun = (ImageView) findViewById(R.id.iv_yellowoil_baolun);
        iv_yellowoil_call = (ImageView) findViewById(R.id.iv_yellowoil_call);
        iv_yellowoil_call.setOnClickListener(this);
        rela_yellowoil_addr = (RelativeLayout) findViewById(R.id.rela_yellowoil_addr);
        rela_yellowoil_addr.setOnClickListener(this);
        rela_yellowoil_top = (RelativeLayout) findViewById(R.id.rela_yellowoil_top);
        var_lon = Utils.getLon();
        var_lat = Utils.getLat();

        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapView_yellowoil);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.clear();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(17).build()));
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(2000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mLocClient.start();
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            goToHome();
        } else if (R.id.iv_yellowoil_call == id) {
            Utils.Call(this, phone);
        } else if (R.id.tv_right == id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("当日只允许取消三次订单,达到取消次数当日将不允许呼叫服务");
            builder.setNegativeButton("确定取消", new android.content.DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    QuXiaoFuWu();
                }
            });

            builder.setPositiveButton("暂不取消", new android.content.DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } else if (R.id.btn_yellowoil == id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("是否确认服务？");
            builder.setNegativeButton("确认", new android.content.DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    startActivity(new Intent(context, YellowOilOrderActivity.class));
                }
            });

            builder.setPositiveButton("取消", new android.content.DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } else if (R.id.rela_yellowoil_addr == id) {
            Intent intent = new Intent(this, Title2Activity.class);
            startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String addr = data.getStringExtra("addr");
        if ("kong".equals(addr)) {
            return;
        }
        var_lat = data.getStringExtra("lat");
        var_lon = data.getStringExtra("lon");
        switch (requestCode) {
            case 0:
                mBaiduMap.clear();
                tv_yellowoil_addr.setText(addr);
                initmark(var_lat, var_lon);
                if (!"".equals(id_service)) {
                    XuanZeFuWu(id_service, var_lon, var_lat);
                }
                break;
            default:
                break;
        }
    }

    public void getYelloOil() {
        showProgressDialog();
        YellowOilReq req = new YellowOilReq();
        req.code = "400101";
        req.type_service = KaKuApplication.yellowoil;
        KaKuApiProvider.YellowOil(req, new KakuResponseListener<YellowOilResp>(context, YellowOilResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("yellowoil res: " + t.res);
                    LogUtil.E("flag_state: " + t.flag_state);
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

    public void SetText(YellowOilResp t) {
        mBaiduMap.clear();
        if ("A".equals(t.flag_state)) { //可预约
            initmark(Utils.getLat(), Utils.getLon());
            tv_yellowoil_top.setVisibility(View.GONE);
            right.setVisibility(View.GONE);
            btn_yellowoil.setVisibility(View.GONE);
            gv_yellowoil.setVisibility(View.VISIBLE);
            view_yellowoil0.setVisibility(View.VISIBLE);
            rela_yellowoil_addr.setVisibility(View.VISIBLE);
            tv_yellow_xianxiazhifuyouhui.setVisibility(View.VISIBLE);
            view_yellowoil1.setVisibility(View.VISIBLE);
            rela_yellowoil_top.setVisibility(View.GONE);
            text_yellowoil_shifukuan.setVisibility(View.GONE);
            iv_yellowoil_yellowtext.setImageResource(R.drawable.qxzcc);
        } else if ("B".equals(t.flag_state)) { //可取消，可确认服务
            phone = t.worker.getTel_worker();
            initmark(t.var_lat0, t.var_lon0);
            tv_yellowoil_top.setVisibility(View.VISIBLE);
            if ("Y".equals(t.worker.getFlag_butter())) {
                iv_yellowoil_dahuangyou.setVisibility(View.VISIBLE);
            } else {
                iv_yellowoil_dahuangyou.setVisibility(View.GONE);
            }
            if ("Y".equals(t.worker.getFlag_tire())) {
                iv_yellowoil_luntai.setVisibility(View.VISIBLE);
            } else {
                iv_yellowoil_luntai.setVisibility(View.GONE);
            }
            if ("Y".equals(t.worker.getFlag_wheel())) {
                iv_yellowoil_baolun.setVisibility(View.VISIBLE);
            } else {
                iv_yellowoil_baolun.setVisibility(View.GONE);
            }
            right.setText("取消服务");
            right.setVisibility(View.VISIBLE);
            btn_yellowoil.setVisibility(View.VISIBLE);
            btn_yellowoil.setText("确认服务");
            gv_yellowoil.setVisibility(View.GONE);
            view_yellowoil0.setVisibility(View.GONE);
            rela_yellowoil_addr.setVisibility(View.GONE);
            view_yellowoil1.setVisibility(View.GONE);
            tv_yellow_xianxiazhifuyouhui.setVisibility(View.GONE);
            rela_yellowoil_top.setVisibility(View.VISIBLE);
            text_yellowoil_shifukuan.setVisibility(View.VISIBLE);
            tv_yellowoil_name.setText(t.worker.getName_worker());
            tv_yellowoil_dan.setText(t.worker.getNum_bill() + "单");
            text_yellowoil_shifukuan.setText("服务指导价：¥" + t.price_goods);
            float starFloat = Float.valueOf(t.worker.getNum_star());
            star_yellowoil_star.setRating(starFloat);
            BitmapUtil.getInstance(context).download(iv_yellowoil_head, KaKuApplication.qian_zhuikong + t.worker.getImage_worker());
            LatLng latlng = new LatLng(Double.parseDouble(t.worker.getVar_lat()), Double.parseDouble(t.worker.getVar_lon()));
            OverlayOptions oo = new MarkerOptions().position(latlng).icon(bd).zIndex(5);
            mMarker = (Marker) (mBaiduMap.addOverlay(oo));
        } else if ("C".equals(t.flag_state)) {
            KaKuApplication.worker = t.worker;
            startActivity(new Intent(context, XianXiaPayActivity.class));
        }
        KaKuApplication.id_upkeep_bill = t.id_upkeep_bill;
        list_yellow = t.services;
        adapter = new YellowOilAdapter(context, list_yellow);
        gv_yellowoil.setAdapter(adapter);
    }

    public void initmark(String lat, String lon) {
        mBaiduMap.setOnMapStatusChangeListener(null);
        BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.drawable.zaizheliweixiu);
        LatLng latlng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
        OverlayOptions oo = new MarkerOptions().position(latlng).icon(bd).zIndex(5);
        mMarker2 = (Marker) (mBaiduMap.addOverlay(oo));

        MapStatus mMapStatus = new MapStatus.Builder().target(latlng).zoom(18).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }

    public void XuanZeFuWu(String id_service, String var_lon, String var_lat) {
        showProgressDialog();
        XuanZeFuWuReq req = new XuanZeFuWuReq();
        req.code = "400102";
        req.id_service = id_service;
        req.var_lat = var_lat;
        req.var_lon = var_lon;
        KaKuApiProvider.XuanZeFuWu(req, new KakuResponseListener<XuanZeFuWuResp>(context, XuanZeFuWuResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("hujiaofuwu res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        SetWorker(t);
                        iv_yellowoil_yellowtext.setImageResource(R.drawable.djtxckjg);
                    } else if (Constants.RES_TWO.equals(t.res)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(YellowOilActivity.this);
                        builder.setTitle("对不起，附近暂无维修工提供服务");
                        builder.setMessage("送您一张5元卡库商城优惠券，期待下次为您服务");
                        builder.setNegativeButton("知道了", new android.content.DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        });

                        builder.setPositiveButton("逛逛商城", new android.content.DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                startActivity(new Intent(context, ShopMallActivity.class));
                                finish();
                            }
                        });
                        builder.create().show();
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

    public void SetWorker(XuanZeFuWuResp t) {
        worker_list = t.workers;

        for (int i = 0; i < worker_list.size(); i++) {
            if (!"".equals(worker_list.get(i).getVar_lat())) {
                LatLng latlng = new LatLng(Double.parseDouble(worker_list.get(i).getVar_lat()), Double.parseDouble(worker_list.get(i).getVar_lon()));
                OverlayOptions oo = new MarkerOptions().position(latlng).icon(bd).zIndex(5);
                Marker mMarker = (Marker) (mBaiduMap.addOverlay(oo));
                mMarker.setTitle(worker_list.get(i).getName_worker() + "@" + worker_list.get(i).getNum_bill() + "@" + worker_list.get(i).getNum_star()
                        + "@" + worker_list.get(i).getPrice_service() + "@" + worker_list.get(i).getId_worker());

                mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                    public boolean onMarkerClick(final Marker marker) {

                        final String a[] = marker.getTitle().split("@");
                        LatLng ll = marker.getPosition();

                        //名字
                        RelativeLayout.LayoutParams params_name = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params_name.setMargins(5, 0, 0, 0);
                        TextView tv_name = new TextView(YellowOilActivity.this);
                        tv_name.setTextColor(Color.BLACK);
                        tv_name.setText(a[0]);
                        tv_name.setId(1);
                        tv_name.setTextSize(10.0f);
                        tv_name.setLayoutParams(params_name);

                        //单数
                        RelativeLayout.LayoutParams params_dan = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params_dan.addRule(RelativeLayout.RIGHT_OF, 1);
                        params_dan.addRule(RelativeLayout.ALIGN_BOTTOM, 1);
                        params_dan.setMargins(15, 0, 0, 0);
                        TextView tv_dan = new TextView(YellowOilActivity.this);
                        tv_dan.setTextColor(Color.BLACK);
                        tv_dan.setText(a[1] + "单");
                        tv_dan.setId(2);
                        tv_dan.setTextSize(8.0f);
                        tv_dan.setLayoutParams(params_dan);

                        //星星
                        RelativeLayout.LayoutParams params_star = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params_star.addRule(RelativeLayout.BELOW, 1);
                        params_star.setMargins(5, 0, 0, 0);
                        RatingBar star = new RatingBar(YellowOilActivity.this, null, android.R.attr.ratingBarStyleSmall);
                        star.setRating(Float.parseFloat(a[2]));
                        star.setId(3);
                        star.setNumStars(5);
                        star.setIsIndicator(true);
                        star.setBackgroundColor(0);
                        star.setLayoutParams(params_star);

                        //价格
                        RelativeLayout.LayoutParams params_price = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params_price.addRule(RelativeLayout.BELOW, 3);
                        params_price.setMargins(5, 0, 0, 0);
                        TextView tv_price = new TextView(YellowOilActivity.this);
                        tv_price.setTextColor(Color.RED);
                        tv_price.setText(a[3]);
                        tv_price.setId(4);
                        tv_price.setTextSize(10.0f);
                        tv_price.setLayoutParams(params_price);

                        //选择呼叫
                        RelativeLayout.LayoutParams params_image = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params_image.addRule(RelativeLayout.RIGHT_OF, 3);
                        params_image.addRule(RelativeLayout.ALIGN_BOTTOM, 4);
                        params_image.addRule(RelativeLayout.ALIGN_TOP, 1);
                        params_image.setMargins(45, 0, 0, 0);
                        TextView iv = new TextView(YellowOilActivity.this);
                        iv.setText("选择\n呼叫");
                        iv.setTextSize(12.0f);
                        iv.setTextColor(Color.WHITE);
                        iv.setGravity(Gravity.CENTER);
                        iv.setPadding(10, 0, 10, 0);
                        iv.setBackgroundColor(Color.rgb(18, 108, 210));
                        iv.setId(5);
                        iv.setLayoutParams(params_image);

                        //大布局
                        RelativeLayout.LayoutParams params_rela = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        RelativeLayout relativeLayout = new RelativeLayout(YellowOilActivity.this);
                        relativeLayout.setLayoutParams(params_rela);
                        relativeLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        relativeLayout.setId(6);
                        relativeLayout.addView(tv_name);
                        relativeLayout.addView(tv_dan);
                        relativeLayout.addView(star);
                        relativeLayout.addView(tv_price);
                        relativeLayout.addView(iv);


                        InfoWindow mInfoWindow = new InfoWindow(relativeLayout, ll, -47);

                        relativeLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HuJiaoFuWu(a[4]);
                            }
                        });
                        mBaiduMap.showInfoWindow(mInfoWindow);
                        return true;
                    }
                });
            }

        }
    }

    public void HuJiaoFuWu(String id_worker) {
        showProgressDialog();
        HuJiaoFuWuReq req = new HuJiaoFuWuReq();
        req.code = "400103";
        req.id_worker = id_worker;
        req.id_service = id_service;
        req.var_lon = var_lon;
        req.var_lat = var_lat;
        req.addr = tv_yellowoil_addr.getText().toString().trim();
        KaKuApiProvider.HuJiaoFuWu(req, new KakuResponseListener<HuJiaoFuWuResp>(context, HuJiaoFuWuResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("hujiaofuwu res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        DengDaiFuWu(t);
                    } else if (Constants.RES_ONE.equals(t.res)) {
                        startActivity(new Intent(context, MyCarActivity.class));
                        finish();
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

    public void DengDaiFuWu(HuJiaoFuWuResp t) {
        mBaiduMap.clear();
        phone = t.worker.getTel_worker();
        initmark(t.var_lat0, t.var_lon0);
        tv_yellowoil_top.setVisibility(View.VISIBLE);
        if ("Y".equals(t.worker.getFlag_butter())) {
            iv_yellowoil_dahuangyou.setVisibility(View.VISIBLE);
        } else {
            iv_yellowoil_dahuangyou.setVisibility(View.GONE);
        }
        if ("Y".equals(t.worker.getFlag_tire())) {
            iv_yellowoil_luntai.setVisibility(View.VISIBLE);
        } else {
            iv_yellowoil_luntai.setVisibility(View.GONE);
        }
        if ("Y".equals(t.worker.getFlag_wheel())) {
            iv_yellowoil_baolun.setVisibility(View.VISIBLE);
        } else {
            iv_yellowoil_baolun.setVisibility(View.GONE);
        }
        KaKuApplication.worker = t.worker;
        right.setText("取消订单");
        right.setVisibility(View.VISIBLE);
        btn_yellowoil.setVisibility(View.VISIBLE);
        btn_yellowoil.setText("确认服务");
        gv_yellowoil.setVisibility(View.GONE);
        rela_yellowoil_addr.setVisibility(View.GONE);
        view_yellowoil1.setVisibility(View.GONE);
        tv_yellow_xianxiazhifuyouhui.setVisibility(View.GONE);
        rela_yellowoil_top.setVisibility(View.VISIBLE);
        text_yellowoil_shifukuan.setVisibility(View.VISIBLE);
        tv_yellowoil_name.setText(t.worker.getName_worker());
        tv_yellowoil_dan.setText(t.worker.getNum_bill() + "单");
        text_yellowoil_shifukuan.setText("实付款：¥" + t.price_goods);
        float starFloat = Float.valueOf(t.worker.getNum_star());
        star_yellowoil_star.setRating(starFloat);
        KaKuApplication.id_upkeep_bill = t.id_upkeep_bill;
        BitmapUtil.getInstance(context).download(iv_yellowoil_head, KaKuApplication.qian_zhuikong + t.worker.getImage_worker());
        LatLng latlng = new LatLng(Double.parseDouble(t.worker.getVar_lat()), Double.parseDouble(t.worker.getVar_lon()));
        OverlayOptions oo = new MarkerOptions().position(latlng).icon(bd).zIndex(5);
        mMarker = (Marker) (mBaiduMap.addOverlay(oo));
    }

    public void QuXiaoFuWu() {
        showProgressDialog();
        BaoYangOrderCancleReq req = new BaoYangOrderCancleReq();
        req.code = "400104";
        req.id_upkeep_bill = KaKuApplication.id_upkeep_bill;
        KaKuApiProvider.QuXiaoFuWu(req, new KakuResponseListener<ExitResp>(context, ExitResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("quxiaofuwu res: " + t.res);
                    if (!Constants.RES_TEN.equals(t.res)) {
                        getYelloOil();
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

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mBaiduMap.clear();
        for (int i = 0; i < list_yellow.size(); i++) {
            list_yellow.get(i).setFlag_check("N");
        }
        if (TextUtils.isEmpty(tv_yellowoil_addr.getText().toString().trim())) {
            LogUtil.showShortToast(context, "请选择服务地址");
            return;
        }
        list_yellow.get(position).setFlag_check("Y");
        adapter.notifyDataSetChanged();
        gv_yellowoil.setAdapter(adapter);
        id_service = list_yellow.get(position).getId_service();
        XuanZeFuWu(id_service, var_lon, var_lat);
    }


    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            //Receive Location
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder().latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }

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

            if (!TextUtils.isEmpty(location.getAddrStr()) && TextUtils.isEmpty(tv_yellowoil_addr.getText().toString().trim())) {
                tv_yellowoil_addr.setText(location.getAddrStr());
            } else {
                LogUtil.showShortToast(context, "请打开手机定位功能");
            }

            if (!"".equals(location.getLatitude())) {
                SharedPreferences.Editor editor = KaKuApplication.editor;
                editor.putString(Constants.LAT, location.getLatitude() + "");
                editor.putString(Constants.LON, location.getLongitude() + "");
                editor.commit();
                mLocClient.stop();
                getYelloOil();
            }
        }
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
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
