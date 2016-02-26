package com.yichang.kaku.home.nouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.DropObj;
import com.yichang.kaku.obj.ShopCarObj;
import com.yichang.kaku.request.AddOilReq;
import com.yichang.kaku.request.ShaiXuanOilReq;
import com.yichang.kaku.response.AddOilResp;
import com.yichang.kaku.response.ShaiXuanOilResp;
import com.yichang.kaku.tools.DateUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.XListView;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class AddOilActivity extends BaseActivity implements OnClickListener,AdapterView.OnItemClickListener{
	
	private TextView left,right,title;
	private TextView tv_genghuan_jiyou,tv_genghuan_lvxin;
	private int selectColor, unSelectColor;
	private XListView xListView;
	private List<ShopCarObj> wuliao_list = new ArrayList<ShopCarObj>();
	private AddOilAdapter adapter_add;
	private final static int STEP = 8;
	private int start = 0,pageindex = 0, pagesize = STEP;
	private final static int INDEX = 8;// 一屏显示的个数
	private boolean isShowProgress = false;
	private LinearLayout line_addoil_shaixuan,line_addoil_oil,line_addoil_five,line_addoil_hui;
	private TextView tv_addoil_quxiao,tv_addoil_queding,tv_addoil_shaixuan;
	private RelativeLayout rela_addoil_pinpai,rela_addoil_jiyoudengji,rela_addoil_nianchoudu,rela_addoil_leibie,rela_addoil_rongliang;
	private ListView lv_shaixuan;
	private List<DropObj> list_shaixuan = new ArrayList<DropObj>();
	private ShaiXuanOilAdapter adapter_shaixuan;
	private String flag_type = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addoil);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("可更换商品");
		right= (TextView) findViewById(R.id.tv_right);
		right.setVisibility(View.VISIBLE);
		right.setText("筛选");
		right.setOnClickListener(this);
		KaKuApplication.id_drop = "";
		tv_genghuan_jiyou = (TextView) findViewById(R.id.tv_genghuan_jiyou);
		tv_genghuan_jiyou.setOnClickListener(this);
		tv_genghuan_lvxin = (TextView) findViewById(R.id.tv_genghuan_lvxin);
		tv_genghuan_lvxin.setOnClickListener(this);
		tv_addoil_quxiao= (TextView) findViewById(R.id.tv_addoil_quxiao);
		tv_addoil_quxiao.setOnClickListener(this);
		tv_addoil_queding= (TextView) findViewById(R.id.tv_addoil_queding);
		tv_addoil_queding.setOnClickListener(this);
		tv_addoil_shaixuan= (TextView) findViewById(R.id.tv_addoil_shaixuan);
		tv_addoil_shaixuan.setOnClickListener(this);
		rela_addoil_pinpai= (RelativeLayout) findViewById(R.id.rela_addoil_pinpai);
		rela_addoil_pinpai.setOnClickListener(this);
		rela_addoil_jiyoudengji= (RelativeLayout) findViewById(R.id.rela_addoil_jiyoudengji);
		rela_addoil_jiyoudengji.setOnClickListener(this);
		rela_addoil_nianchoudu= (RelativeLayout) findViewById(R.id.rela_addoil_nianchoudu);
		rela_addoil_nianchoudu.setOnClickListener(this);
		rela_addoil_leibie= (RelativeLayout) findViewById(R.id.rela_addoil_leibie);
		rela_addoil_leibie.setOnClickListener(this);
		rela_addoil_rongliang= (RelativeLayout) findViewById(R.id.rela_addoil_rongliang);
		rela_addoil_rongliang.setOnClickListener(this);
		line_addoil_shaixuan= (LinearLayout) findViewById(R.id.line_addoil_shaixuan);
		line_addoil_oil= (LinearLayout) findViewById(R.id.line_addoil_oil);
		line_addoil_five= (LinearLayout) findViewById(R.id.line_addoil_five);
		line_addoil_hui= (LinearLayout) findViewById(R.id.line_addoil_hui);
		line_addoil_hui.setOnClickListener(this);
		selectColor = getResources().getColor(R.color.color_red);
		unSelectColor = getResources().getColor(R.color.black);
		xListView = (XListView) findViewById(R.id.lv_genghuan_baoyang);
		xListView.setOnItemClickListener(this);
		lv_shaixuan= (ListView) findViewById(R.id.lv_shaixuan);
		lv_shaixuan.setOnItemClickListener(this);
		setPullState(false);
	}

	@Override
	public void onClick(View v) {
		Utils.NoNet(context);
		if (Utils.Many()){
			return;
		}
		tv_genghuan_jiyou.setTextColor(unSelectColor);
		tv_genghuan_lvxin.setTextColor(unSelectColor);

		int id = v.getId();
		if (R.id.tv_left == id) {
			AddOilActivity.this.finish();
		} else if (R.id.tv_genghuan_jiyou == id){
			rela_addoil_jiyoudengji.setVisibility(View.VISIBLE);
			rela_addoil_nianchoudu.setVisibility(View.VISIBLE);
			rela_addoil_leibie.setVisibility(View.VISIBLE);
			rela_addoil_rongliang.setVisibility(View.VISIBLE);
			KaKuApplication.type_item = "0";
			tv_genghuan_jiyou.setTextColor(selectColor);
			setPullState(false);
		} else if (R.id.tv_genghuan_lvxin == id){
			rela_addoil_jiyoudengji.setVisibility(View.GONE);
			rela_addoil_nianchoudu.setVisibility(View.GONE);
			rela_addoil_leibie.setVisibility(View.GONE);
			rela_addoil_rongliang.setVisibility(View.GONE);
			KaKuApplication.type_item = "1";
			flag_type = "10";
			tv_genghuan_lvxin.setTextColor(selectColor);
			setPullState(false);
		} else if (R.id.tv_right == id){
			lv_shaixuan.setVisibility(View.GONE);
			line_addoil_shaixuan.setVisibility(View.VISIBLE);
			line_addoil_five.setVisibility(View.VISIBLE);
		} else if (R.id.tv_addoil_quxiao == id){
			line_addoil_shaixuan.setVisibility(View.GONE);
		} else if (R.id.tv_addoil_queding == id){

		} else if (R.id.rela_addoil_pinpai == id){
			if ("0".equals(KaKuApplication.type_item)){
				flag_type = "0";
			} else if ("1".equals(KaKuApplication.type_item)){
				flag_type = "10";
			}
			tv_addoil_shaixuan.setText("品牌");
			line_addoil_five.setVisibility(View.GONE);
			lv_shaixuan.setVisibility(View.VISIBLE);
			lv_shaixuan.setVisibility(View.GONE);
			ShaiXuan();
		} else if (R.id.rela_addoil_jiyoudengji == id){
			flag_type = "1";
			tv_addoil_shaixuan.setText("机油等级");
			line_addoil_five.setVisibility(View.GONE);
			lv_shaixuan.setVisibility(View.VISIBLE);
			lv_shaixuan.setVisibility(View.GONE);
			ShaiXuan();
		} else if (R.id.rela_addoil_nianchoudu == id){
			flag_type = "2";
			tv_addoil_shaixuan.setText("粘稠度");
			line_addoil_five.setVisibility(View.GONE);
			lv_shaixuan.setVisibility(View.VISIBLE);
			lv_shaixuan.setVisibility(View.GONE);
			ShaiXuan();
		} else if (R.id.rela_addoil_leibie == id){
			flag_type = "3";
			tv_addoil_shaixuan.setText("类别");
			line_addoil_five.setVisibility(View.GONE);
			lv_shaixuan.setVisibility(View.VISIBLE);
			lv_shaixuan.setVisibility(View.GONE);
			ShaiXuan();
		} else if (R.id.rela_addoil_rongliang == id){
			flag_type = "4";
			tv_addoil_shaixuan.setText("容量");
			line_addoil_five.setVisibility(View.GONE);
			lv_shaixuan.setVisibility(View.VISIBLE);
			lv_shaixuan.setVisibility(View.GONE);
			ShaiXuan();
		}

	}

	public void AddOil(int pageIndex, int pageSize){
		Utils.NoNet(context);
		showProgressDialog();
		AddOilReq req = new AddOilReq();
		req.code = "4008";
		req.id_shop = KaKuApplication.id_shop;
		req.no_item = KaKuApplication.no_item;
		req.type_item = KaKuApplication.type_item;
		req.id_drop =  KaKuApplication.id_drop;
		req.flag_type = flag_type;
		req.start = String.valueOf(pageIndex);
		req.len = String.valueOf(pageSize);
		KaKuApiProvider.AddOil(req, new BaseCallback<AddOilResp>(AddOilResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, AddOilResp t) {
				if (t != null) {
					LogUtil.E("addoil res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						setData(t.itmes);
						KaKuApplication.id_drop = "";
						onLoadStop();
					}else{
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

	private void setData(List<ShopCarObj> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			wuliao_list.addAll(list);
		}
		AddOilAdapter adapter = new AddOilAdapter(AddOilActivity.this, wuliao_list);
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
		if (isUp) {
			isShowProgress = true;
			start++;
			pageindex = start*STEP;
		} else {
			start = 0;
			pageindex = 0;
			if (wuliao_list != null){
				wuliao_list.clear();
			}
		}
		AddOil(pageindex, pagesize);
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
		if (R.id.lv_genghuan_baoyang == parent.getId()){
		ShopCarObj wuLiaoObj = wuliao_list.get(position-1);
		String id_item = wuliao_list.get(position - 1).getId_item();
		Intent intent = new Intent();
		intent.putExtra("result",wuLiaoObj);
		intent.putExtra("id_item",id_item);
		setResult(RESULT_OK, intent);
		finish();
		} else if (R.id.lv_shaixuan == parent.getId()){
			for (int i = 0; i < list_shaixuan.size(); i++) {
				ImageView duihao= (ImageView) lv_shaixuan.getChildAt(i).findViewById(R.id.iv_shaixuan_duihao);
				duihao.setVisibility(View.GONE);
			}
				ImageView duihao= (ImageView) lv_shaixuan.getChildAt(position).findViewById(R.id.iv_shaixuan_duihao);
				duihao.setVisibility(View.VISIBLE);
			    KaKuApplication.id_drop = list_shaixuan.get(position).getId_drop();
				lv_shaixuan.setVisibility(View.GONE);
				line_addoil_shaixuan.setVisibility(View.GONE);
				setPullState(false);
		}
	}

	public void ShaiXuan(){
		Utils.NoNet(context);
		showProgressDialog();
		ShaiXuanOilReq req = new ShaiXuanOilReq();
		req.code = "40027";
		req.id_shop = KaKuApplication.id_shop;
		req.flag_type = flag_type;
		KaKuApiProvider.ShaiXuanOil(req , new BaseCallback<ShaiXuanOilResp>(ShaiXuanOilResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, ShaiXuanOilResp t) {
				stopProgressDialog();
				if (t != null) {
					LogUtil.E("shaixuan res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						list_shaixuan = t.drops;
						adapter_shaixuan = new ShaiXuanOilAdapter(context,list_shaixuan);
						lv_shaixuan.setAdapter(adapter_shaixuan);
						lv_shaixuan.setVisibility(View.VISIBLE);
					} else {
						if (Constants.RES_TEN.equals(t.res)){
							Utils.Exit(context);
							finish();
						}
						LogUtil.showShortToast(context, t.msg);
					}
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
				stopProgressDialog();
			}
		});
	}
}


