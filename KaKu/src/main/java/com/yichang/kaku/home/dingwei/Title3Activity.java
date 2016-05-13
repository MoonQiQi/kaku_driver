package com.yichang.kaku.home.dingwei;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.tools.Utils;

public class Title3Activity extends BaseActivity implements OnClickListener {
    MapView mMapView;
    BaiduMap mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title3);
        init();


    }

    private void init() {
        // TODO Auto-generated method stub
        mMapView = (MapView) findViewById(R.id.bmapView_yellowoil3);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void onClick(View v) {
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();

    }

}
