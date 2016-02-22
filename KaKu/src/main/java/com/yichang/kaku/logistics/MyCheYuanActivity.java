package com.yichang.kaku.logistics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.obj.MyCheYuanObj;
import com.yichang.kaku.request.MyCheYuanReq;
import com.yichang.kaku.response.MyCheYuanResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class MyCheYuanActivity extends BaseActivity implements OnClickListener{
	
	private TextView left,right,title;
	private XListView xListView;
	private List<MyCheYuanObj> list_cheyuan = new ArrayList<MyCheYuanObj>();
	private final static int STEP = 5;
	private int start = 0,pageindex = 0, pagesize = STEP;
	private final static int INDEX = 5;// 一屏显示的个数
	private boolean isShowProgress = false;

/*无数据和无网络界面*/

	private RelativeLayout layout_data_none, layout_net_none;
	private TextView tv_desc, tv_advice;
	private Button btn_refresh;
	private LinearLayout ll_container;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mycheyuan);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		right= (TextView) findViewById(R.id.tv_right);
		right.setVisibility(View.VISIBLE);
		right.setText("发布车源");
		right.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("我的车源");

		initNoDataLayout();
		xListView= (XListView) findViewById(R.id.lv_mycheyuan);
	}

	@Override
	protected void onStart() {
		super.onStart();
		setPullState(false);
	}

	/*初始化空白页页面布局*/
	private void initNoDataLayout() {
		layout_data_none = (RelativeLayout) findViewById(R.id.layout_data_none);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
		tv_desc.setText("您还没有发布的车源");
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
		} else if (R.id.tv_right == id){
			GoToRight();
		}else if (R.id.btn_refresh == id) {

			setPullState(false);

		}
	}

	public void MyCheYuan(int pageIndex, int pageSize){
//		Utils.NoNet(context);
		if (!Utils.checkNetworkConnection(context)) {
			setNoDataLayoutState(layout_net_none);

			return;
		} else {
			setNoDataLayoutState(ll_container);

		}
		showProgressDialog();
		MyCheYuanReq req = new MyCheYuanReq();
		req.code = "6005";
		req.id_driver = Utils.getIdDriver();
		req.start = String.valueOf(pageIndex);
		req.len = String.valueOf(pageSize);
		KaKuApiProvider.MyCheYuan(req, new BaseCallback<MyCheYuanResp>(MyCheYuanResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, MyCheYuanResp t) {
				if (t != null) {
					LogUtil.E("myhuo res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						setData(t.optionss);
					}else {
						if (Constants.RES_TEN.equals(t.res)){
							Utils.Exit(context);
							finish();
						}
						LogUtil.showShortToast(context, t.msg);
					}
					onLoadStop();
					//LogUtil.showShortToast(context, t.msg);
				}
				stopProgressDialog();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
				stopProgressDialog();
			}
		});
	}

	private void setData(List<MyCheYuanObj> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			list_cheyuan.addAll(list);
		}

		if (list_cheyuan.size() == 0) {

			setNoDataLayoutState(layout_data_none);
			return;
		} else {

			setNoDataLayoutState(ll_container);
		}

		MyCheYuanAdapter adapter = new MyCheYuanAdapter(context, list_cheyuan);
		xListView.setAdapter(adapter);
		xListView.setPullLoadEnable(list.size() < INDEX ? false : true);
		xListView.setSelection(pageindex);
		xListView.setXListViewListener(new XListView.IXListViewListener() {

			@Override
			public void onRefresh() {
				if (!Utils.checkNetworkConnection(context)) {
					Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
					setNoDataLayoutState(layout_net_none);
					xListView.stopRefresh();
					return;
				}
				setPullState(false);
			}

			@Override
			public void onLoadMore() {
				if (!Utils.checkNetworkConnection(context)) {
					Toast.makeText(BaseActivity.context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
					setNoDataLayoutState(layout_net_none);
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
			pageindex = start*STEP;
		} else {
			start = 0;
			pageindex = 0;
			if (list_cheyuan != null){
				list_cheyuan.clear();
			}
		}
		MyCheYuan(pageindex, pagesize);
	}

	private void onLoadStop() {
		xListView.stopRefresh();
		xListView.stopLoadMore();
		xListView.setRefreshTime(DateUtil.dateFormat());
	}

	public void GoToRight(){
		startActivity(new Intent(context,FaBuCheYuanActivity.class));
	}


	private void setNoDataLayoutState(View view) {
		layout_data_none.setVisibility(View.GONE);
		ll_container.setVisibility(View.GONE);
		layout_net_none.setVisibility(View.GONE);

		view.setVisibility(View.VISIBLE);
	}
}
