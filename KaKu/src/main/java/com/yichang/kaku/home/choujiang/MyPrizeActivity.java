package com.yichang.kaku.home.choujiang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.obj.JiangPinObj;
import com.yichang.kaku.request.MyPrizeReq;
import com.yichang.kaku.response.MyPrizeResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class MyPrizeActivity extends BaseActivity implements OnClickListener,AdapterView.OnItemClickListener{
	
	private TextView left,right,title;
	private XListView xListView;
	private final static int STEP = 5;
	private int start = 0, pageindex = 0, pagesize = STEP;
	private final static int INDEX = 5;// 一屏显示的个数
	private boolean isShowProgress = false;
	private List<JiangPinObj> list_jiangpin = new ArrayList<JiangPinObj>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myprize);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("我的奖品");
		xListView= (XListView) findViewById(R.id.lv_myprize);
		xListView.setOnItemClickListener(this);
		setPullState(false);
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

	private void MyPrize(int pageIndex, int pageSize){
		if (!Utils.checkNetworkConnection(context)) {
			//setNoDataLayoutState(layout_net_none);
			return;
		}
		showProgressDialog();
		MyPrizeReq req = new MyPrizeReq();
		req.code = "70021";
		req.id_driver = Utils.getIdDriver();
		req.start = String.valueOf(pageIndex);
		req.len = String.valueOf(pageSize);
		KaKuApiProvider.MyPrize(req, new BaseCallback<MyPrizeResp>(MyPrizeResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, MyPrizeResp t) {
				if (t != null) {
					LogUtil.E("choujiang res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						setData(t.wheel_wins);
					} else {
						if (Constants.RES_TEN.equals(t.res)){
							Utils.Exit(context);
							finish();
						}
						LogUtil.showShortToast(context, t.msg);
					}
					onLoadStop();
				}
				stopProgressDialog();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
				stopProgressDialog();
			}
		});
	}

	private void setData(List<JiangPinObj> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			list_jiangpin.addAll(list);
		}
		if (list_jiangpin.size() == 0) {

			//setNoDataLayoutState(layout_data_none);
			return;
		} else {

			//setNoDataLayoutState(ll_container);
		}

		MyPrizeAdapter adapter = new MyPrizeAdapter(MyPrizeActivity.this, list_jiangpin);
		xListView.setAdapter(adapter);
		xListView.setPullLoadEnable(list.size() < INDEX ? false : true);
		xListView.setSelection(pageindex);
		xListView.setPullRefreshEnable(true);
		xListView.setXListViewListener(new XListView.IXListViewListener() {

			@Override
			public void onRefresh() {
				if (!Utils.checkNetworkConnection(context)) {
					stopProgressDialog();
					Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
					xListView.stopRefresh();
					return;
				}
				setPullState(false);
			}

			@Override
			public void onLoadMore() {
				if (!Utils.checkNetworkConnection(context)) {
					stopProgressDialog();
					Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
					xListView.stopLoadMore();
					return;
				}
				setPullState(true);
			}
		});
	}

	private void setPullState(boolean isUp) {
		if (isUp) {
			isShowProgress = true;
			start++;
			pageindex = start * STEP;
		} else {
			start = 0;
			pageindex = 0;
			if (list_jiangpin != null) {
				list_jiangpin.clear();
			}
		}
		MyPrize(pageindex, pagesize);
	}

	private void onLoadStop() {
		xListView.stopRefresh();
		xListView.stopLoadMore();
		xListView.setRefreshTime(DateUtil.dateFormat());
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (Utils.Many()){
			return;
		}

		JiangPinObj obj = list_jiangpin.get(position-1);
		Bundle bundle = new Bundle();
		if ("1".equals(list_jiangpin.get(position-1).getType())){
			//实物
			Intent intent = new Intent(context,PrizeDetailShiActivity.class);
			bundle.putString("company_delivery",obj.getCompany_delivery());
			bundle.putString("no_delivery",obj.getNo_delivery());
			bundle.putString("addr_driver",obj.getAddr_driver());
			bundle.putString("name_driver",obj.getName_driver());
			bundle.putString("phone_driver",obj.getPhone_driver());
			bundle.putString("flag_exchange",obj.getFlag_exchange());
			bundle.putString("image_prize",obj.getImage_prize());
			bundle.putString("name_prize",obj.getName_prize());
			bundle.putString("time_wheel",obj.getTime_wheel());
			bundle.putString("time_send",obj.getTime_send());
			bundle.putString("id_wheel_win",obj.getId_wheel_win());
			intent.putExtras(bundle);
			startActivity(intent);
		} else if ("2".equals(list_jiangpin.get(position-1).getType())){
			//充值卡
			Intent intent = new Intent(context,PrizeDetailXuActivity.class);
			bundle.putString("no_card",obj.getNo_card());
			bundle.putString("pass_card",obj.getPass_card());
			bundle.putString("flag_exchange",obj.getFlag_exchange());
			bundle.putString("image_prize",obj.getImage_prize());
			bundle.putString("name_prize",obj.getName_prize());
			bundle.putString("name_driver",obj.getName_driver());
			bundle.putString("time_wheel",obj.getTime_wheel());
			bundle.putString("time_send",obj.getTime_send());
			bundle.putString("id_wheel_win",obj.getId_wheel_win());
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}
}
