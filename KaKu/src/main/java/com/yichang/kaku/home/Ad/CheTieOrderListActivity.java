package com.yichang.kaku.home.Ad;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.obj.CheTieOrderObj;
import com.yichang.kaku.request.CheTieOrderReq;
import com.yichang.kaku.response.CheTieOrderResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.util.ArrayList;
import java.util.List;


public class CheTieOrderListActivity extends BaseActivity implements OnClickListener,ListView.OnItemClickListener{

	private TextView left,right,title;
	private XListView xListView;
	private List<CheTieOrderObj> chetie_list = new ArrayList<CheTieOrderObj>();
	private CheTieOrderAdapter adapter;
	private final static int STEP = 5;
	private int start = 0,pageindex = 0, pagesize = STEP;
	private final static int INDEX = 5;// 一屏显示的个数
	private boolean isShowProgress = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chetieorder);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("我的车贴");
		xListView= (XListView) findViewById(R.id.lv_wodechetie);
		LinearLayout line_chetieorder = findView(R.id.line_chetieorder);
		xListView.setEmptyView(line_chetieorder);
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
			if ("member".equals(KaKuApplication.chetie_order_to)){
				gotoMember();
			} else {
				goToList();
			}
		}
	}

	public void CheTieOrder(int pageIndex, int pageSize){
		Utils.NoNet(context);
		showProgressDialog();
		CheTieOrderReq req = new CheTieOrderReq();
		req.code = "60035";
		req.id_driver = Utils.getIdDriver();
		req.start = String.valueOf(pageIndex);
		req.len = String.valueOf(pageSize);
		KaKuApiProvider.CheTieOrder(req, new KakuResponseListener<CheTieOrderResp>(this,CheTieOrderResp.class) {
			@Override
			public void onSucceed(int what, Response response) {
				super.onSucceed(what, response);
				if (t != null) {
					LogUtil.E("chetieorder res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						setData(t.bills);
						KaKuApplication.id_drop = "";
						onLoadStop();
					} else {
						LogUtil.showShortToast(context, t.msg);
					}
				}
				stopProgressDialog();
			}
		});
	}

	private void setData(List<CheTieOrderObj> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			chetie_list.addAll(list);
		}
		CheTieOrderAdapter adapter = new CheTieOrderAdapter(CheTieOrderListActivity.this, chetie_list);
		adapter.setCallback(new CheTieOrderAdapter.CallBack() {
			@Override
			public void payOrder(String no_bill, String price_bill) {
				KaKuApplication.realPayment = price_bill;

				KaKuApplication.payType = "STICKER";

				Intent intent = new Intent(context, OrderCheTiePayActivity.class);
				intent.putExtra("no_bill", no_bill);
				intent.putExtra("price_bill", price_bill);
				startActivity(intent);
			}
		});
		xListView.setAdapter(adapter);
		xListView.setPullLoadEnable(list.size() < INDEX ? false : true);
		xListView.setSelection(pageindex);
		xListView.setPullRefreshEnable(true);
		xListView.setXListViewListener(new XListView.IXListViewListener() {

			@Override
			public void onRefresh() {
				if (!Utils.checkNetworkConnection(context)) {
					Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
					xListView.stopRefresh();
					return;
				}
				setPullState(false);
			}

			@Override
			public void onLoadMore() {
				if (!Utils.checkNetworkConnection(context)) {
					Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
					xListView.stopLoadMore();
					return;
				}
				setPullState(true);
			}
		});

		xListView.setOnItemClickListener(this);
	}

	private void setPullState(boolean isUp) {
		if (isUp) {
			isShowProgress = true;
			start++;
			pageindex = start*STEP;
		} else {
			start = 0;
			pageindex = 0;
			if (chetie_list != null){
				chetie_list.clear();
			}
		}
		CheTieOrder(pageindex, pagesize);
	}

	private void onLoadStop() {
		xListView.stopRefresh();
		xListView.stopLoadMore();
		xListView.setRefreshTime(DateUtil.dateFormat());
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		MobclickAgent.onEvent(context, "CheTieListItem");
		Intent intent = new Intent(context,CheTieOrderDetailActivity.class);
		KaKuApplication.id_bill_chetie = chetie_list.get(position - 1).getId_advert_bill();
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		LogUtil.E( "AAAAAA" + KaKuApplication.chetie_order_to);
		if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
			if ("member".equals(KaKuApplication.chetie_order_to)){
				gotoMember();
			} else {
				goToList();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private void goToList() {
		Intent intent = new Intent(context, QiangCheTieListActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	private void gotoMember() {
		Intent intent = new Intent(context, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_MEMBER);
		startActivity(intent);
		finish();
	}

}
