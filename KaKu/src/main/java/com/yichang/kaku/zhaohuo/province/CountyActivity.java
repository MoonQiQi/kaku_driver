package com.yichang.kaku.zhaohuo.province;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.member.address.NewAddrActivity;
import com.yichang.kaku.obj.AreaObj;
import com.yichang.kaku.request.AreaReq;
import com.yichang.kaku.response.AreaResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class CountyActivity extends BaseActivity implements OnClickListener,AdapterView.OnItemClickListener{
	
	private TextView left,right,title;
	private ListView lv_province;
	private ProvinceAdapter adapter_province;
	private List<AreaObj> list_province = new ArrayList<AreaObj>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_county);
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
		showProgressDialog();
		AreaReq req = new AreaReq();
		req.code = "10018";
		req.id_area = KaKuApplication.city_addr;
		KaKuApiProvider.Area(req, new BaseCallback<AreaResp>(AreaResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, AreaResp t) {
				if (t != null) {
					LogUtil.E("area res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						list_province = t.areas;
						adapter_province = new ProvinceAdapter(context,list_province);
						lv_province.setAdapter(adapter_province);
					}
					LogUtil.showShortToast(context, t.msg);
				}
				stopProgressDialog();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
				stopProgressDialog();
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		if (Utils.Many()){
			return;
		}
		KaKuApplication.county_addr = list_province.get(position).getName_area();
		KaKuApplication.county_addrid = list_province.get(position).getId_area();
		Intent intent = new Intent(context,NewAddrActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}
