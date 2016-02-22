package com.yichang.kaku.home;

import android.location.Location;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.tools.Utils;

import java.util.ArrayList;
import java.util.List;

public class Title3Activity extends BaseActivity implements OnClickListener {
	
	private TextView left,right,title;
	private TextView tv_dingwei_dingwei;
	private LinearLayout ll_dingwei_lishi;
	private List<String> list_string;
	private List<PoiInfo> ppp = new ArrayList<PoiInfo>();
	private StringBuilder sb;
	private TextView tv_dingwei_qingkong;
	private DingWeiLiShiAdapter adapter_lishi;
	private String save_history;
	private String lon,lat;
	private Location location;

	private LocationClient mLocationClient;
	public Vibrator mVibrator;
	private LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
	private String tempcoor = "bd09ll";

	private PoiSearch mPoiSearch = null;
	private SuggestionSearch mSuggestionSearch = null;
	private ListView listView;
	private PoiSearchAdapter adapter;
	private boolean flag_location = false;

	/**
	 * 搜索关键字输入窗口
	 */
	private AutoCompleteTextView keyWorldsView = null;
	private ArrayAdapter<String> sugAdapter = null;
	private int load_Index = 0;
	private static StringBuilder sb2;
	private Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title2);
		init();

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
			}
		});

		keyWorldsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			}
		});

	}

	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("切换位置");
	}

	@Override
	public void onClick(View v) {
		if (Utils.Many()){
			return;
		}
		int id = v.getId();
		if (R.id.tv_left == id) {
			finish();
		} else if (R.id.tv_dingwei_dingwei == id){

		}
	}

}
