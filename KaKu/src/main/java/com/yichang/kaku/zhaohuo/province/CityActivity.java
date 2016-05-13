package com.yichang.kaku.zhaohuo.province;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.AreaObj;
import com.yichang.kaku.request.AreaReq;
import com.yichang.kaku.response.AreaResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class CityActivity extends BaseActivity implements OnClickListener,AdapterView.OnItemClickListener{
	
	private TextView left,right,title;
	private ListView lv_province;
	private ProvinceAdapter adapter_province;
	private List<AreaObj> list_province = new ArrayList<AreaObj>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("所在地区");
		lv_province= (ListView) findViewById(R.id.lv_province);
		lv_province.setOnItemClickListener(this);
		GetProvince();
	}

	@Override
	public void onClick(View v) {
		Utils.NoNet(context);
		if (Utils.Many()){
			return;
		}
		int id = v.getId();
		if (R.id.tv_left == id) {
			finish();
		} 
	}

	public void GetProvince(){
		Utils.NoNet(context);
		AreaReq req = new AreaReq();
		req.code = "10018";
		req.id_area = KaKuApplication.province_addr;
		KaKuApiProvider.Area(req, new KakuResponseListener<AreaResp>(this, AreaResp.class) {
			@Override
			public void onSucceed(int what, Response response) {
				super.onSucceed(what, response);
				if (t != null) {
					LogUtil.E("area res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						list_province = t.areas;
						adapter_province = new ProvinceAdapter(context, list_province);
						lv_province.setAdapter(adapter_province);
					}
					LogUtil.showShortToast(context, t.msg);
				}
			}

			@Override
			public void onFailed(int i, Response response) {

			}


		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (Utils.Many()){
			return;
		}
		Intent intent = new Intent(context,CountyActivity.class);
		KaKuApplication.city_addr = list_province.get(position).getId_area();
		KaKuApplication.city_addrname = list_province.get(position).getName_area();
		startActivity(intent);
	}
}
