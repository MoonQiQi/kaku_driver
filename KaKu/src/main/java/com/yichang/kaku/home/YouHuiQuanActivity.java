package com.yichang.kaku.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.YouHuiQuanObj;
import com.yichang.kaku.request.YouHuiQuanReq;
import com.yichang.kaku.response.YouHuiQuanResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class YouHuiQuanActivity extends BaseActivity implements OnClickListener,AdapterView.OnItemClickListener{
	
	private TextView left,right,title;
	private ListView lv_youhuiquan;
	private YouHuiQuanAdapter adapter;
	private List<YouHuiQuanObj> list_youhuiquan = new ArrayList<YouHuiQuanObj>();

			/*无数据和无网络界面*/

	private RelativeLayout layout_data_none, layout_net_none;
	private TextView tv_desc, tv_advice;
	private Button btn_refresh;
	private LinearLayout ll_container;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_youhuiquan);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("可用优惠券");
		lv_youhuiquan= (ListView) findViewById(R.id.lv_youhuiquan);
		lv_youhuiquan.setOnItemClickListener(this);
		initNoDataLayout();
		GetYouHuiQuan();
	}

	/*初始化空白页页面布局*/
	private void initNoDataLayout() {
		layout_data_none = (RelativeLayout) findViewById(R.id.layout_data_none);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
		tv_desc.setText("您还没有可用的优惠券");
		tv_advice = (TextView) findViewById(R.id.tv_advice);

		layout_net_none = (RelativeLayout) findViewById(R.id.layout_net_none);

		btn_refresh = (Button) findViewById(R.id.btn_refresh);
		btn_refresh.setOnClickListener(this);

		ll_container = (LinearLayout) findViewById(R.id.ll_container);


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
		} else if (R.id.btn_refresh == id) {

			GetYouHuiQuan();

		}
	}

	public void GetYouHuiQuan(){
		//Utils.NoNet(context);
		if (!Utils.checkNetworkConnection(context)) {
			setNoDataLayoutState(layout_net_none);

			return;
		} else {
			setNoDataLayoutState(ll_container);

		}
		showProgressDialog();
		YouHuiQuanReq req = new YouHuiQuanReq();
		req.code = "40010";
		req.id_driver = Utils.getIdDriver();
		req.total_price = KaKuApplication.money+"";
		KaKuApiProvider.GetYouHuiQuan(req, new BaseCallback<YouHuiQuanResp>(YouHuiQuanResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, YouHuiQuanResp t) {
				if (t != null) {
					LogUtil.E("youhuiquan res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						list_youhuiquan.clear();
						list_youhuiquan.addAll(t.coupons);

						if (list_youhuiquan.size() == 0) {

							setNoDataLayoutState(layout_data_none);
							return;
						} else {

							setNoDataLayoutState(ll_container);
						}
						adapter = new YouHuiQuanAdapter(context,list_youhuiquan);
						lv_youhuiquan.setAdapter(adapter);
					}else {
						if (Constants.RES_TEN.equals(t.res)){
							Utils.Exit(context);
							finish();
						}
						LogUtil.showShortToast(context, t.msg);
					}
				}
				stopProgressDialog();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
				stopProgressDialog();
			}
		});
	}

	private void setNoDataLayoutState(View view) {
		layout_data_none.setVisibility(View.GONE);
		ll_container.setVisibility(View.GONE);
		layout_net_none.setVisibility(View.GONE);

		view.setVisibility(View.VISIBLE);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String name_youhuiquan = list_youhuiquan.get(position).getContent_coupon();
		String money_youhuiquan = list_youhuiquan.get(position).getMoney_coupon();
		String id_youhuiquan = list_youhuiquan.get(position).getId_coupon();
		Intent intent = new Intent();
		intent.putExtra("id_youhuiquan",id_youhuiquan);
		intent.putExtra("name_youhuiquan",name_youhuiquan);
		intent.putExtra("money_youhuiquan",money_youhuiquan);
		setResult(RESULT_OK, intent);
		finish();
	}
}
