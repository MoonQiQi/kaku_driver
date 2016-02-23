package com.yichang.kaku.home;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DaoHangActivity extends BaseActivity implements OnGetRoutePlanResultListener,View.OnClickListener{

    MapView mMapView;
    private TextView left,title;
    private RoutePlanSearch routePlanSearch;
    private BaiduMap mBaidumap;
    private BNRoutePlanNode sNode;
    private BNRoutePlanNode eNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daohang);
        showProgressDialog();
        left=(TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title=(TextView) findViewById(R.id.tv_mid);
        title.setText("导航");
        mMapView = (MapView) findViewById(R.id.ma);

        if ("".equals(Utils.getLat())||"".equals(Utils.getLon())){
            return;
        }
        double s_Lat = Double.parseDouble(Utils.getLat());
        double s_Lon = Double.parseDouble(Utils.getLon());
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String e_lat1 = bundle.getString("e_lat");
        String e_lat2 = bundle.getString("e_lon");
        double e_lat = Double.parseDouble(e_lat1);
        double e_Lon = Double.parseDouble(e_lat2);

        BDLocation sLocation = new BDLocation();//构建起点坐标
        sLocation.setLatitude(s_Lat);
        sLocation.setLongitude(s_Lon);

        BDLocation eLocation = new BDLocation();//构建终点坐标
        eLocation.setLatitude(e_lat);
        eLocation.setLongitude(e_Lon);

        //转换成导航坐标
        sLocation = LocationClient.getBDLocationInCoorType(sLocation, BDLocation.BDLOCATION_BD09LL_TO_GCJ02);
        eLocation = LocationClient.getBDLocationInCoorType(eLocation, BDLocation.BDLOCATION_BD09LL_TO_GCJ02);

        sNode = new BNRoutePlanNode(sLocation.getLongitude(), sLocation.getLatitude(), null, null);
        eNode = new BNRoutePlanNode(eLocation.getLongitude(), eLocation.getLatitude(), null, null);

        mBaidumap = mMapView.getMap();

        PlanNode stNode = PlanNode.withLocation(new LatLng(s_Lat, s_Lon));
        PlanNode enNode = PlanNode.withLocation(new LatLng(e_lat, e_Lon));

        routePlanSearch = RoutePlanSearch.newInstance();
        routePlanSearch.setOnGetRoutePlanResultListener(this);

        DrivingRoutePlanOption option = new DrivingRoutePlanOption().from(stNode).to(enNode);

        routePlanSearch.drivingSearch(option);

        if ( initDirs() ) {
            initNavi();
            }
        }

        private void routeplanToNavi(){
            if (sNode != null && eNode != null) {
                List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
                list.add(sNode);
                list.add(eNode);
                BaiduNaviManager.getInstance().launchNavigator(DaoHangActivity.this, list, 1, true, new DemoRoutePlanListener(sNode));
                Toast.makeText(DaoHangActivity.this, "开始导航", Toast.LENGTH_LONG).show();
            }
        }

    String authinfo = null;



    private void initNavi() {

        //BaiduNaviManager.getInstance().setNativeLibraryPath(mSDCardPath + "/BaiduNaviSDK_SO");

        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME,
                new BaiduNaviManager.NaviInitListener() {
                    @Override
                    public void onAuthResult(int status, String msg) {
                        if (0 == status) {
                            authinfo = "key校验成功!";
                        } else {
                            authinfo = "key校验失败, " + msg;
                        }
                        DaoHangActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                               // Toast.makeText(DaoHangActivity.this, authinfo, Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                    public void initSuccess() {
                        Toast.makeText(DaoHangActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                    }

                    public void initStart() {
                        Toast.makeText(DaoHangActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
                    }

                    public void initFailed() {
                        Toast.makeText(DaoHangActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
                    }
                }, null);
    }

    private String mSDCardPath = null;
    private static final String APP_FOLDER_NAME = "kaku";

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }


    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(
                Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        }
    }

    class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {

            Intent intent = new Intent(DaoHangActivity.this, BNDemoGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(BNDemoMainActivity.ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);

            stopProgressDialog();
            finish();
        }

        @Override
        public void onRoutePlanFailed() {
            LogUtil.showShortToast(context,"路径规划失败");
            stopProgressDialog();
        }
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
        mMapView.onDestroy();
        BaiduNaviManager.getInstance().uninit();
        super.onDestroy();
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(DaoHangActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {

            DrivingRouteLine routeLine = result.getRouteLines().get(0);
            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaidumap);
            mBaidumap.setOnMarkerClickListener(overlay);
            if (overlay != null && mBaidumap != null){
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }
        }
        stopProgressDialog();
    }

    //定制RouteOverly
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.shi);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.zhong);
        }
    }

}
