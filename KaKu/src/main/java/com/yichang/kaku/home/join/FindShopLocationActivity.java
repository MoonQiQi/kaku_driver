package com.yichang.kaku.home.join;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.response.LocationInfo;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.LocationListPop;

import de.greenrobot.event.EventBus;

/**
 * Created by xiaosu on 2015/11/11.
 * 定位店铺地址
 */
public class FindShopLocationActivity extends BaseActivity implements OnGetPoiSearchResultListener, AdapterView.OnItemClickListener, View.OnClickListener, BaiduMap.OnMapClickListener, OnGetGeoCoderResultListener, TextView.OnEditorActionListener {

    private BaiduMap mMap;
    private EditText et_search;
    private PoiSearch poiSearch;
    private MapView mMapView;
    private LocationListPop locationListPop;
    private PoiResult poiResult;
    private TextView location;
    private PoiInfo mPoiInfo;
    private GeoCoder geoCoder;
    private LocationInfo mLocationInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_shop_location);

        initView();

        poiSearch = PoiSearch.newInstance();
        geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(this);
        poiSearch.setOnGetPoiSearchResultListener(this);
        initMap();
    }

    private void initMap() {
        //定义Maker坐标点
        LatLng point = new LatLng(getIntent().getDoubleExtra("lat", 39.963175), getIntent().getDoubleExtra("lng", 116.400244));
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.fangxiang);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mMap.addOverlay(option);

        MapStatus mMapStatus = new MapStatus.Builder()
                .target(point)
                .zoom(18)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mMap.setMapStatus(mMapStatusUpdate);
        mMap.setOnMapClickListener(this);//给地图设置点击事件
    }

    private void initView() {
        mMapView = findView(R.id.map);
        et_search = findView(R.id.et_search);
        location = findView(R.id.location);
        TextView tv_right = findView(R.id.tv_right);
        this.mMap = mMapView.getMap();

        tv_right.setVisibility(View.VISIBLE);
        tv_right.setOnClickListener(this);
        ((TextView) findView(R.id.tv_mid)).setText("地图定位");
        et_search.setOnEditorActionListener(this);

        location.setText(getIntent().getStringExtra("name"));
        /**结束页面*/
        findView(R.id.tv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {

        if (poiResult == null || poiResult.error != SearchResult.ERRORNO.NO_ERROR) {
            return;
        }

        this.poiResult = poiResult;//记录搜索的结果

        if (locationListPop == null) {
            locationListPop = new LocationListPop(this, this);
            locationListPop.setAllPoi(poiResult.getAllPoi());
        } else {
            locationListPop.update(poiResult.getAllPoi());
        }

        if (!locationListPop.isShowing()) {
            locationListPop.showAsDropDown(et_search);
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

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
        mMapView.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        locationListPop.dismiss();
        mPoiInfo = poiResult.getAllPoi().get(position);

        if (mLocationInfo == null) {
            mLocationInfo = new LocationInfo(mPoiInfo.location, mPoiInfo.name, mPoiInfo.city);
        } else {
            mLocationInfo.update(mPoiInfo.location, mPoiInfo.name, mPoiInfo.city);
        }

        location.setText(mPoiInfo.name);
        updateMap(mPoiInfo.location);
    }

    private void updateMap(LatLng latLng) {
        mMap.clear();//清空所以的标记
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(latLng)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mMap.setMapStatus(mMapStatusUpdate);

        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.fangxiang);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(latLng)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mMap.addOverlay(option);
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()){
            return;
        }
        switch (v.getId()) {
            case R.id.tv_right:
                if (null != mLocationInfo) {
                    EventBus.getDefault().post(mLocationInfo);
                }
                finish();
                break;
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        showProgressDialog();
        updateMap(latLng);
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

        stopProgressDialog();
        if (reverseGeoCodeResult == null) {
            showShortToast("网络状态不好，请稍后再试");
            return;
        }

        if (mLocationInfo == null) {
            mLocationInfo = new LocationInfo(reverseGeoCodeResult.getLocation(), reverseGeoCodeResult.getAddress(), reverseGeoCodeResult.getAddressDetail().city);
        } else {
            mLocationInfo.update(reverseGeoCodeResult.getLocation(), reverseGeoCodeResult.getAddress(), reverseGeoCodeResult.getAddressDetail().city);
        }
        location.setText(reverseGeoCodeResult.getAddress());
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            poiSearch.searchInCity(new PoiCitySearchOption()
                    .city(getIntent().getStringExtra("city") == null ? "北京" : getIntent().getStringExtra("city"))
                    .keyword(getText(et_search))
                    .pageNum(1)
                    .pageCapacity(20));
            return true;
        } else {
            return false;
        }
    }
}
