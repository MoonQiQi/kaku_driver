package com.yichang.kaku.home.dingwei;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.squareup.okhttp.Request;
import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.response.CityResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.tools.okhttp.OkHttpUtil;
import com.yichang.kaku.tools.okhttp.Params;
import com.yichang.kaku.tools.okhttp.RequestCallback;
import com.yichang.kaku.view.popwindow.CityChoosePopWindow;
import com.yichang.kaku.webService.UrlCtnt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TitleActivity extends BaseActivity implements OnClickListener, OnGetPoiSearchResultListener, OnGetSuggestionResultListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private TextView tv_dingwei_dingwei;
    private LinearLayout ll_dingwei_lishi;
    private List<String> list_string;
    private List<PoiInfo> ppp = new ArrayList<PoiInfo>();
    private StringBuilder sb;
    private TextView tv_dingwei_qingkong;
    private DingWeiLiShiAdapter adapter_lishi;
    private String save_history;
    private String lon, lat;
    private Location location;

    private LocationClient mLocationClient;
    public MyLocationListener mMyLocationListener;
    public Vibrator mVibrator;
    private LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
    private String tempcoor = "bd09ll";

    private PoiSearch mPoiSearch = null;
    private SuggestionSearch mSuggestionSearch = null;
    private ListView listView;
    private PoiSearchAdapter adapter;
    private boolean flag_location = false;
    private View view_title1;
    private ImageView iv_dingwei_fjdz;

    /**
     * 搜索关键字输入窗口
     */
    private AutoCompleteTextView keyWorldsView = null;
    private ArrayAdapter<String> sugAdapter = null;
    private int load_Index = 0;
    private static StringBuilder sb2;
    private Button btn;
    private LinkedList<CityResp.ListEntity.InnerListEntity> cities;
    private CityChoosePopWindow popWindow;
    private View rl_search;

    private Map<String, Integer> positionMap;
    private TextView tv_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        init();

        tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
        mLocationClient = new LocationClient(context);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        mVibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);

        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
        keyWorldsView = (AutoCompleteTextView) findViewById(R.id.et_dingwei_dizhi);
        sugAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line);
        keyWorldsView.setAdapter(sugAdapter);
        keyWorldsView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                if (cs.length() <= 0) {
                    return;
                }
                /**
                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                 */
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(cs.toString()).city(getKeyWord()));
                searchButtonProcess();
            }
        });

        keyWorldsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchButtonProcess();
            }
        });
        initLocation();

        showProgressDialog();
        Params.builder builder = new Params.builder();
        builder.p("sid", Utils.getSid()).p("code", "90013");

        OkHttpUtil.postAsync(UrlCtnt.BASEIP + "login/area_list_0", builder.build(), new RequestCallback<CityResp>(this, CityResp.class) {
            @Override
            public void onSuccess(final CityResp obj, String result) {

                new Thread() {
                    @Override
                    public void run() {
                        cities = new LinkedList<>();

                        for (CityResp.ListEntity item : obj.list) {
                            cities.addAll(item.list);
                        }
                        /*排序*/
                        Collections.sort(cities);

                        Map<String, Integer> letterMap = new LinkedHashMap<>();
                        //第0为肯定是A
                        int size = cities.size();
                        for (int i = 0; i < size; i++) {
                            String currentLetter = cities.get(i).getPinyin().charAt(0) + "";
                            if (i == 0) {
                                /*在这个位置要插第一个字母*/
                                letterMap.put(currentLetter, 0);
                                continue;
                            }

                            String lastLetter = cities.get(i - 1).getPinyin().charAt(0) + "";

                            if (!currentLetter.equals(lastLetter)) {
                                letterMap.put(currentLetter, i);
                            }
                        }

                        Set<Map.Entry<String, Integer>> entries = letterMap.entrySet();
                        positionMap = new LinkedHashMap<>();
                        int count = 0;
                        for (Iterator<Map.Entry<String, Integer>> iterator = entries.iterator(); iterator.hasNext(); ) {
                            Map.Entry<String, Integer> next = iterator.next();
                            /*获取字母*/
                            String letter = next.getKey();
                            int position = next.getValue();

                            CityResp.ListEntity.InnerListEntity entity = new CityResp.ListEntity.InnerListEntity();
                            entity.type = 1;
                            entity.name_area = letter;

                            /*要插入的位置*/
                            position += count;

                            /*保存当前字母的位置*/
                            positionMap.put(letter, position);
                            cities.add(position, entity);
                            count++;
                        }

                        CityResp.ListEntity.InnerListEntity second = new CityResp.ListEntity.InnerListEntity();
                        second.type = 2;
                        second.name_area = TextUtils.isEmpty(getIntent().getStringExtra("city")) ? "北京" : getIntent().getStringExtra("city");

                        CityResp.ListEntity.InnerListEntity first = new CityResp.ListEntity.InnerListEntity();
                        first.type = 3;
                        first.name_area = "当前选择的城市";
                        cities.addFirst(second);
                        cities.addFirst(first);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stopProgressDialog();
                            }
                        });
                    }
                }.start();

            }

            @Override
            public void onInnerFailure(Request request, IOException e) {
                stopProgressDialog();
                showShortToast("网络连接失败，请稍后再试");
            }
        });

        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(getKeyWord())
                .keyword("高速")
                .pageNum(load_Index));
        sb = new StringBuilder();

    }

    private String getKeyWord() {
        String text = getText(tv_city);
        String s = text.replace("辖区", "").replace("地区", "").replace("辖县", "");
        return s;
    }

    public void chooseCity(View view) {
        if (cities == null || positionMap == null) {
            return;
        }
        if (popWindow == null) {
            popWindow = new CityChoosePopWindow(this, cities, positionMap, this);
        }
        popWindow.showAsDropDown(rl_search);
    }

    @Override
    public void onStop() {
        super.onStop();
        mLocationClient.stop();
    }

    @Override
    protected void onDestroy() {
        mPoiSearch.destroy();
        mSuggestionSearch.destroy();
        super.onDestroy();
        flag_location = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void searchButtonProcess() {
        if (flag_location) {
            return;
        }
        EditText editSearchKey = (EditText) findViewById(R.id.et_dingwei_dizhi);
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(getKeyWord())
                .keyword(editSearchKey.getText().toString())
                .pageNum(load_Index));
        sb = new StringBuilder();
        iv_dingwei_fjdz.setVisibility(View.GONE);
    }

    public void goToNextPage(View v) {
        load_Index++;
        searchButtonProcess();
    }

    private void init() {

        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        rl_search = findViewById(R.id.rl_search);
        iv_dingwei_fjdz = (ImageView) findViewById(R.id.iv_dingwei_fjdz);
        tv_city = findView(R.id.tv_city);

        title.setText("切换位置");
        tv_city.setText(TextUtils.isEmpty(getIntent().getStringExtra("city")) ? "北京" : getIntent().getStringExtra("city"));

        if (location == null) {
            lon = "";
            lat = "";
        } else {
            lon = location.getLongitude() + "";
            lat = location.getLatitude() + "";
        }
        /*SharedPreferences.Editor editor = KaKuApplication.editor;
        editor.putString(Constants.LAT, lat);
        editor.putString(Constants.LON, lon);
        editor.commit();*/
        ll_dingwei_lishi = (LinearLayout) findViewById(R.id.ll_dingwei_lishi);
        tv_dingwei_dingwei = (TextView) findViewById(R.id.tv_dingwei_dingwei);
        tv_dingwei_dingwei.setOnClickListener(this);
        view_title1 = findViewById(R.id.view_title1);
        listView = (ListView) findViewById(R.id.lv_dingwei_lishi);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                KaKuApplication.address = ppp.get(position).city;
                KaKuApplication.city = ppp.get(position).city;
                KaKuApplication.addr_string = ppp.get(position).address;
                /*SharedPreferences.Editor editor = KaKuApplication.editor;
                editor.putString(Constants.LAT, ppp.get(position).location.latitude + "");
                editor.putString(Constants.LON, ppp.get(position).location.longitude + "");
                editor.commit();*/
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        } else if (R.id.tv_dingwei_dingwei == id) {
            mLocationClient.start();
            showProgressDialog();
        }
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        int span = 2000;
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

    @Override
    public void onGetPoiResult(PoiResult result) {
        if (flag_location) {
            return;
        }
        if (result == null
                || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(TitleActivity.this, "未找到结果", Toast.LENGTH_LONG).show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            ppp = result.getAllPoi();
            if (ppp != null && ppp.size() != 0) {
                for (PoiInfo poiInfo : ppp) {
                    LogUtil.E("==1=poi===城市：" + poiInfo.city + "名字：" + poiInfo.name + "地址：" + poiInfo.address);
                }
                adapter = new PoiSearchAdapter(this, ppp);
                listView.setAdapter(adapter);
            }

            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            Toast.makeText(TitleActivity.this, strInfo, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {
        if (flag_location) {
            return;
        }
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(TitleActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                    .show();
        } else {
            LogUtil.E("==2=poi===" + result.getName() + ": " + result.getAddress());
        }
    }

    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        if (flag_location) {
            return;
        }
        if (res == null || res.getAllSuggestions() == null) {
            return;
        }
        sugAdapter.clear();
        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
            if (info.key != null) {
                sugAdapter.add(info.key);
            }
        }
        sugAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (popWindow.isShowing()) {
            popWindow.dismiss();
        }

        CityResp.ListEntity.InnerListEntity entity = cities.get(position);

        if (entity.type == 0 || entity.type == 2) {
            tv_city.setText(entity.name_area);
            cities.get(1).name_area = entity.name_area;

            getKeyWord();

            int firstVisiblePosition = parent.getFirstVisiblePosition();
            int lastVisiblePosition = parent.getLastVisiblePosition();
            if (1 >= firstVisiblePosition && 1 <= lastVisiblePosition) {
                TextView child = (TextView) parent.getChildAt(1 - firstVisiblePosition);
                child.setText(entity.name_area);
            }
        }
    }

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
            if (!TextUtils.isEmpty(location.getCity())) {
                KaKuApplication.address = location.getCity();
                KaKuApplication.addr_string = location.getAddrStr();
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
                startActivity(intent);
                finish();
            }
            /*if (location.getLatitude() > 0) {
                SharedPreferences.Editor editor = KaKuApplication.editor;
                editor.putString(Constants.LAT, location.getLatitude() + "");
                editor.putString(Constants.LON, location.getLongitude() + "");
                editor.commit();
            }*/
            if (!TextUtils.isEmpty(location.getAddrStr()) && location.getLatitude() > 0) {
                mLocationClient.stop();
            }
        }
    }

}
