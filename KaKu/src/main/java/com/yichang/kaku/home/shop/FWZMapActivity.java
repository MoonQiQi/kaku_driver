package com.yichang.kaku.home.shop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.Shops_mapObj;
import com.yichang.kaku.request.MapFWZReq;
import com.yichang.kaku.response.MapFWZResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;


public class FWZMapActivity extends BaseActivity implements View.OnClickListener {

    private TextView left, title;
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    MapView mMapView;
    BaiduMap mBaiduMap;
    boolean isFirstLoc = true;// 是否首次定位
    private List<Shops_mapObj> list_map = new ArrayList<Shops_mapObj>();
    BitmapDescriptor bd = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_gcoding);
    private Marker mMarker0;
    private InfoWindow mInfoWindow0;
    private String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fwzmap);
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        if ("0".equals(KaKuApplication.flag_shop)) {
            title.setText("附近服务站");
        } else {
            title.setText("附近维修站");
        }
        mCurrentMode = LocationMode.NORMAL;

        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
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
            finish();
        }
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FWZMap();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    public void FWZMap() {
        MapFWZReq req = new MapFWZReq();
        req.code = "40020";
        req.flag_type = KaKuApplication.flag_shop;
        req.lat = Utils.getLat();
        req.lon = Utils.getLon();
        KaKuApiProvider.MapFWZ(req, new KakuResponseListener<MapFWZResp>(this, MapFWZResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("shopmaps res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        list_map = t.shops;
                        initOverlay(list_map);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }


        });
    }

    public void initOverlay(final List<Shops_mapObj> list) {

        for (int i = 0; i < list.size(); i++) {
            if ("".equals(list.get(i).getVar_lat()) || "".equals(list.get(i).getVar_lon())) {
                return;
            }
            LatLng latlng = new LatLng(Double.parseDouble(list.get(i).getVar_lat()), Double.parseDouble(list.get(i).getVar_lon()));
            OverlayOptions oo = new MarkerOptions().position(latlng).icon(bd).zIndex(5);
            Marker mMarker = (Marker) (mBaiduMap.addOverlay(oo));
            mMarker.setTitle(list.get(i).getName_shop());

            mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                public boolean onMarkerClick(final Marker marker) {

                    LatLng ll = marker.getPosition();
                    TextView tv = new TextView(FWZMapActivity.this);
                    tv.setBackgroundResource(R.color.white);
                    tv.setTextColor(Color.BLACK);
                    tv.setGravity(Gravity.CENTER);
                    tv.setPadding(10, 10, 10, 10);
                    tv.setText(marker.getTitle());
                    InfoWindow mInfoWindow = new InfoWindow(tv, ll, -47);
                    //InfoWindow mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(tv), ll, -47, null);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String title = marker.getTitle();
                            for (int j = 0; j < list.size(); j++) {
                                if (title.equals(list.get(j).getName_shop())) {
                                    KaKuApplication.id_shop = list.get(j).getId_shop();
                                }
                            }
                            startActivity(new Intent(context, ShopDetailActivity.class));
                        }
                    });
                    mBaiduMap.showInfoWindow(mInfoWindow);
                    return true;
                }
            });
        }

    }
}
