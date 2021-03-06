package com.yichang.kaku.home.jiuyuan;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.home.AdActivity;
import com.yichang.kaku.obj.Shops_wxzObj;
import com.yichang.kaku.request.SOSReq;
import com.yichang.kaku.response.SOSResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class SOSActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    MapView mMapView;
    BaiduMap mBaiduMap;
    boolean isFirstLoc = true;// 是否首次定位
    private List<Shops_wxzObj> list_map = new ArrayList<Shops_wxzObj>();
    BitmapDescriptor bd = BitmapDescriptorFactory
            .fromResource(R.drawable.jiuyuandingwei);
    private Marker mMarker0;
    private InfoWindow mInfoWindow0;
    private String id;
    private ImageView iv_sos_jiatong, iv_sos_guanbi;
    private String name_brand, phone_brand;
    private RelativeLayout rela_sos_jiatong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("一键救援");
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("救援电话");
        right.setOnClickListener(this);
        iv_sos_jiatong = (ImageView) findViewById(R.id.iv_sos_jiatong);
        iv_sos_jiatong.setOnClickListener(this);
        iv_sos_guanbi = (ImageView) findViewById(R.id.iv_sos_guanbi);
        iv_sos_guanbi.setOnClickListener(this);
        rela_sos_jiatong = (RelativeLayout) findViewById(R.id.rela_sos_jiatong);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;

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
        } else if (R.id.tv_right == id) {
            Intent intent = new Intent(this, SOSCallActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name_brand", name_brand);
            bundle.putString("phone_brand", phone_brand);
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
        } else if (R.id.iv_sos_jiatong == id) {
            MobclickAgent.onEvent(context, "sosad");
            Intent intent = new Intent(this, AdActivity.class);
            intent.putExtra("name_ad", "广告详情");
            intent.putExtra("url_ad", "http://giti.com/tbdealer/2");
            startActivity(intent);
        } else if (R.id.iv_sos_guanbi == id) {
            rela_sos_jiatong.setVisibility(View.GONE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mMapView.setLayoutParams(params);
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
        SOS();
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

    public void SOS() {
        SOSReq req = new SOSReq();
        req.code = "8003";
        req.id_driver = Utils.getIdDriver();
        req.lat = Utils.getLat();
        req.lon = Utils.getLon();
        req.start = "0";
        req.len = "20";
        KaKuApiProvider.SOS(req, new KakuResponseListener<SOSResp>(this, SOSResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("sos res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        list_map = t.shops;
                        name_brand = t.name_brand;
                        phone_brand = t.mobile_brand;
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

    public void initOverlay(final List<Shops_wxzObj> list) {

        for (int i = 0; i < list.size(); i++) {
            if (!"".equals(list.get(i).getVar_lat())) {
                LatLng latlng = new LatLng(Double.parseDouble(list.get(i).getVar_lat()), Double.parseDouble(list.get(i).getVar_lon()));
                OverlayOptions oo = new MarkerOptions().position(latlng).icon(bd).zIndex(5);
                Marker mMarker = (Marker) (mBaiduMap.addOverlay(oo));
                mMarker.setTitle(list.get(i).getName_shop());

                mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                    public boolean onMarkerClick(final Marker marker) {

                        LatLng ll = marker.getPosition();
                        TextView tv = new TextView(SOSActivity.this);
                        tv.setTextColor(Color.WHITE);
                        tv.setPadding(20, 10, 20, 10);
                        tv.setGravity(Gravity.CENTER_VERTICAL);
                        tv.setBackgroundColor(Color.argb(80, 0, 0, 0));
                        tv.setText(marker.getTitle());
                        Drawable drawable = getResources().getDrawable(R.drawable.bg_jiuyuan);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        tv.setCompoundDrawables(null, null, drawable, null);
                        tv.setCompoundDrawablePadding(20);
                        InfoWindow mInfoWindow = new InfoWindow(tv, ll, -47);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String title = marker.getTitle();
                                for (int j = 0; j < list.size(); j++) {
                                    if (title.equals(list.get(j).getName_shop())) {
                                        Utils.Call(SOSActivity.this, list.get(j).getMobile_shop());
                                    }
                                }
                            }
                        });
                        mBaiduMap.showInfoWindow(mInfoWindow);
                        return true;
                    }
                });
            }

        }
    }

}
