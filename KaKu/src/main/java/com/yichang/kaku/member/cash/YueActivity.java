package com.yichang.kaku.member.cash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.obj.YueObj;
import com.yichang.kaku.request.YueReq;
import com.yichang.kaku.response.YueResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.util.ArrayList;
import java.util.List;

public class YueActivity extends BaseActivity implements OnClickListener{
	
	private TextView left,right,title;
	private TextView tv_yue_yue,tv_yue_left_down,tv_yue_right_down;
	private XListView xListView;
	private List<YueObj> yue_list = new ArrayList<YueObj>();
	private final static int STEP = 10;
	private int start = 0, pageindex = 0, pagesize = STEP;
	private final static int INDEX = 10;// 一屏显示的个数
	private boolean isShowProgress = false;

	private boolean isPassExist =false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yue);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("我的余额");
		right = (TextView) findViewById(R.id.tv_right);
		right.setVisibility(View.VISIBLE);
		right.setText("提现");
		right.setOnClickListener(this);
		tv_yue_yue= (TextView) findViewById(R.id.tv_yue_yue);
		tv_yue_left_down= (TextView) findViewById(R.id.tv_yue_left_down);
		tv_yue_right_down= (TextView) findViewById(R.id.tv_yue_right_down);
		xListView= (XListView) findViewById(R.id.lv_yue);
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
		} else if (R.id.tv_right == id){
			//startActivity(new Intent(this,WithdrawActivity.class));
			//密码存在则输入，否则去设置
			MobclickAgent.onEvent(context, "TiXian");
			if(isPassExist){

				startActivity(new Intent(context, InputWithDrawCodeActivity.class));
			}else {

				startActivity(new Intent(context, SetWithDrawCodeActivity.class).putExtra("isPassExist",false).putExtra("flag_next_activity","INPUT"));
			}

		}
	}

	public void GetInfo(int pageIndex, int pageSize){
		if (!Utils.checkNetworkConnection(context)) {
			return;
		}
		showProgressDialog();
		YueReq req = new YueReq();
		req.code ="5001";
		req.id_driver = Utils.getIdDriver();
		req.start = String.valueOf(pageIndex);
		req.len = String.valueOf(pageSize);
		KaKuApiProvider.Yue(req, new KakuResponseListener<YueResp>(this, YueResp.class) {
			@Override
			public void onSucceed(int what, Response response) {
				super.onSucceed(what, response);
				if (t != null) {
					LogUtil.E("yue res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						tv_yue_yue.setText("¥ " + t.money_balance);
						tv_yue_left_down.setText("¥ " + t.money_income);
						tv_yue_right_down.setText("¥ " + t.money_deposit);
						isPassExist = t.pass_exist.equals("Y");
						setData(t.moneys);
					} else {
						LogUtil.showShortToast(context, t.msg);
					}
					onLoadStop();
				}
				stopProgressDialog();
			}

		});
	}

	private void setData(List<YueObj> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			yue_list.addAll(list);
		}
		if (yue_list.size() == 0) {
			return;
		}

		YueAdapter adapter = new YueAdapter(YueActivity.this, yue_list);
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
	}

	private void setPullState(boolean isUp) {
		if (!isUp) {
			isShowProgress = true;
			start++;
			pageindex = start * STEP;
		} else {
			start = 0;
			pageindex = 0;
			if (yue_list != null) {
				yue_list.clear();
			}
		}
		GetInfo(pageindex, pagesize);
	}

	private void onLoadStop() {
		xListView.stopRefresh();
		xListView.stopLoadMore();
		xListView.setRefreshTime(DateUtil.dateFormat());
	}
}
