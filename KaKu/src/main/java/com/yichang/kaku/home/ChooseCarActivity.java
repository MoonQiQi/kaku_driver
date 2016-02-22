package com.yichang.kaku.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.home.mycar.CarListActivity;
import com.yichang.kaku.obj.SeriessObj;
import com.yichang.kaku.request.ChooseCarReq;
import com.yichang.kaku.response.ChooseCarResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class ChooseCarActivity extends BaseActivity implements OnClickListener{
	/*brand		品牌
	series		系列
	model		型号
	actuate		驱动
	fuel		燃料
	emissions	排放*/

	private TextView left,right,title;
	private MyGridView gv_series,gv_model,gv_actuate,gv_fuel,gv_emissions,gv_gonglv;
	private List<SeriessObj> series_list;
	private List<SeriessObj> model_list;
	private List<SeriessObj> actuate_list;
	private List<SeriessObj> fuel_list;
	private List<SeriessObj> emissions_list;
	private List<SeriessObj> power_list;
	private ChooseCarAdapter adapter;
	private int last_series,last_model,last_actuate,last_fuel,last_emissions,last_gonglv;
	private String id_brand,id_series,id_model,id_actuate,id_fuel,id_emissions,id_gonglv;
	private LinearLayout line_choosecar_series,line_choosecar_model,line_choosecar_actuate,
			             line_choosecar_fuel,line_choosecar_emissions,line_choosecar_gonglv;
	private String flag = "0";
	//private Button btn_choosecar_next;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choosecar);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("选择车辆");
/*		btn_choosecar_next = (Button) findViewById(R.id.btn_choosecar_next);
		btn_choosecar_next.setOnClickListener(this);
		btn_choosecar_next.setEnabled(false);*/
		line_choosecar_series = (LinearLayout) findViewById(R.id.line_choosecar_chexi);
		line_choosecar_model = (LinearLayout) findViewById(R.id.line_choosecar_chexing);
		line_choosecar_actuate = (LinearLayout) findViewById(R.id.line_choosecar_qudong);
		line_choosecar_fuel = (LinearLayout) findViewById(R.id.line_choosecar_ranliao);
		line_choosecar_emissions = (LinearLayout) findViewById(R.id.line_choosecar_paifang);
		line_choosecar_gonglv = (LinearLayout) findViewById(R.id.line_choosecar_gonglv);
		line_choosecar_series.setVisibility(View.GONE);
		line_choosecar_model.setVisibility(View.GONE);
		line_choosecar_actuate.setVisibility(View.GONE);
		line_choosecar_fuel.setVisibility(View.GONE);
		line_choosecar_emissions.setVisibility(View.GONE);
		line_choosecar_gonglv.setVisibility(View.GONE);
		gv_series = (MyGridView) findViewById(R.id.gv_chexi);
		gv_model = (MyGridView) findViewById(R.id.gv_chexing);
		gv_actuate = (MyGridView) findViewById(R.id.gv_qudong);
		gv_fuel = (MyGridView) findViewById(R.id.gv_ranliao);
		gv_emissions = (MyGridView) findViewById(R.id.gv_paifang);
		gv_gonglv = (MyGridView) findViewById(R.id.gv_gonglv);
		series_list = new ArrayList<SeriessObj>();
		model_list = new ArrayList<SeriessObj>();
		actuate_list = new ArrayList<SeriessObj>();
		fuel_list = new ArrayList<SeriessObj>();
		emissions_list = new ArrayList<SeriessObj>();
		power_list = new ArrayList<SeriessObj>();
		adapter = new ChooseCarAdapter(this,series_list);
		gv_series.setAdapter(adapter);
		gv_model.setAdapter(adapter);
		gv_actuate.setAdapter(adapter);
		gv_fuel.setAdapter(adapter);
		gv_emissions.setAdapter(adapter);
		gv_gonglv.setAdapter(adapter);
		//车系GridView
		gv_series.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				flag = "1";
				setCheXiView();
				LinearLayout layout =  (LinearLayout)view;
				TextView tv_grid = (TextView) layout.getChildAt(0);
				tv_grid.setTextColor(Color.WHITE);
				tv_grid.setBackgroundResource(R.drawable.green_btn_normal);
				last_series = position;
				id_series = series_list.get(position).getId_data();
				ChooseCar(id_brand,id_series,"","","","",gv_model,line_choosecar_model);
				gv_actuate.setVisibility(View.GONE);
				gv_fuel.setVisibility(View.GONE);
				gv_emissions.setVisibility(View.GONE);
				gv_gonglv.setVisibility(View.GONE);
				line_choosecar_actuate.setVisibility(View.GONE);
				line_choosecar_fuel.setVisibility(View.GONE);
				line_choosecar_emissions.setVisibility(View.GONE);
				line_choosecar_gonglv.setVisibility(View.GONE);
				/*btn_choosecar_next.setEnabled(false);*/
			}
		});
		//车型GridView
		gv_model.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				flag = "2";
				setCheXingView();
				LinearLayout layout =  (LinearLayout)view;
				TextView tv_grid = (TextView) layout.getChildAt(0);
				if ("Y".equals(tv_grid.getTag().toString())){
					tv_grid.setTextColor(Color.WHITE);
					tv_grid.setBackgroundResource(R.drawable.green_btn_normal);
					last_model = position;
					id_model = model_list.get(position).getId_data();
					ChooseCar(id_brand,id_series,id_model,"","","",gv_actuate,line_choosecar_actuate);
					gv_actuate.setVisibility(View.VISIBLE);
					gv_fuel.setVisibility(View.GONE);
					gv_emissions.setVisibility(View.GONE);
					gv_gonglv.setVisibility(View.GONE);
					line_choosecar_actuate.setVisibility(View.VISIBLE);
					line_choosecar_fuel.setVisibility(View.GONE);
					line_choosecar_emissions.setVisibility(View.GONE);
					line_choosecar_gonglv.setVisibility(View.GONE);
					/*btn_choosecar_next.setEnabled(false);*/
					view.setEnabled(true);
				} else {
					view.setEnabled(false);
				}

			}
		});
		//驱动GridView
		gv_actuate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				flag = "3";
				setQuDongView();
				LinearLayout layout =  (LinearLayout)view;
				TextView tv_grid = (TextView) layout.getChildAt(0);
				if ("Y".equals(tv_grid.getTag().toString())){
					tv_grid.setTextColor(Color.WHITE);
					tv_grid.setBackgroundResource(R.drawable.green_btn_normal);
					last_actuate = position;
					id_actuate = actuate_list.get(position).getId_data();
					ChooseCar(id_brand,id_series,id_model,id_actuate,"","",gv_fuel,line_choosecar_fuel);
					gv_actuate.setVisibility(View.VISIBLE);
					gv_fuel.setVisibility(View.VISIBLE);
					gv_emissions.setVisibility(View.GONE);
					gv_gonglv.setVisibility(View.GONE);
					line_choosecar_actuate.setVisibility(View.VISIBLE);
					line_choosecar_fuel.setVisibility(View.VISIBLE);
					line_choosecar_emissions.setVisibility(View.GONE);
					line_choosecar_gonglv.setVisibility(View.GONE);
					/*btn_choosecar_next.setEnabled(false);*/
					view.setEnabled(true);
				} else {
					view.setEnabled(false);
				}
			}
		});
		//燃料GridView
		gv_fuel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				flag = "4";
				setRanLiaoView();
				LinearLayout layout =  (LinearLayout)view;
				TextView tv_grid = (TextView) layout.getChildAt(0);
				if ("Y".equals(tv_grid.getTag().toString())){
					tv_grid.setTextColor(Color.WHITE);
					tv_grid.setBackgroundResource(R.drawable.green_btn_normal);
					last_fuel = position;
					id_fuel = fuel_list.get(position).getId_data();
					ChooseCar(id_brand,id_series,id_model,id_actuate,id_fuel,"",gv_emissions,line_choosecar_emissions);
					gv_actuate.setVisibility(View.VISIBLE);
					gv_fuel.setVisibility(View.VISIBLE);
					gv_emissions.setVisibility(View.VISIBLE);
					gv_gonglv.setVisibility(View.GONE);
					line_choosecar_actuate.setVisibility(View.VISIBLE);
					line_choosecar_fuel.setVisibility(View.VISIBLE);
					line_choosecar_emissions.setVisibility(View.VISIBLE);
					line_choosecar_gonglv.setVisibility(View.GONE);
					/*btn_choosecar_next.setEnabled(false);*/
					view.setEnabled(true);
				} else {
					view.setEnabled(false);
				}
			}
		});
		//排放GridView
		gv_emissions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				flag = "5";
				setPaiFangView();
				LinearLayout layout =  (LinearLayout)view;
				TextView tv_grid = (TextView) layout.getChildAt(0);
				if ("Y".equals(tv_grid.getTag().toString())){
					tv_grid.setTextColor(Color.WHITE);
					tv_grid.setBackgroundResource(R.drawable.green_btn_normal);
					last_emissions = position;
					id_emissions = emissions_list.get(position).getId_data();
					ChooseCar(id_brand,id_series,id_model,id_actuate,id_fuel,id_emissions,gv_gonglv,line_choosecar_gonglv);
					gv_actuate.setVisibility(View.VISIBLE);
					gv_fuel.setVisibility(View.VISIBLE);
					gv_emissions.setVisibility(View.VISIBLE);
					gv_gonglv.setVisibility(View.VISIBLE);
					line_choosecar_actuate.setVisibility(View.VISIBLE);
					line_choosecar_fuel.setVisibility(View.VISIBLE);
					line_choosecar_emissions.setVisibility(View.VISIBLE);
					line_choosecar_gonglv.setVisibility(View.VISIBLE);
					/*btn_choosecar_next.setEnabled(false);*/
					view.setEnabled(true);
				} else {
					view.setEnabled(false);
				}
			}
		});
		//功率GridView
		gv_gonglv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				setGongLvView();
				LinearLayout layout =  (LinearLayout)view;
				TextView tv_grid = (TextView) layout.getChildAt(0);
				if ("Y".equals(tv_grid.getTag().toString())){
					tv_grid.setTextColor(Color.WHITE);
					tv_grid.setBackgroundResource(R.drawable.green_btn_normal);
					last_gonglv = position;
					id_gonglv = power_list.get(position).getId_data();
					/*btn_choosecar_next.setEnabled(true);*/
					/*取消下一步点击按钮，选择功率后直接跳转到选车界面*/
					GoToCarList();
					view.setEnabled(true);
				} else {
					view.setEnabled(false);
				}

			}
		});

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id_brand = bundle.getString("id_brand");
		ChooseCar(id_brand,"","","","","",gv_series,line_choosecar_series);

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
		} /*else if (R.id.btn_choosecar_next == id){
			GoToCarList();
		}*/
	}

	public void ChooseCar(String id_brand,String id_series,String id_model,String id_actuate,
						  String id_fuel,String id_emissions, final GridView gv,
						  final LinearLayout ll){
		Utils.NoNet(this);
		showProgressDialog();
		ChooseCarReq req = new ChooseCarReq();
		req.code = "2003";
		req.id_brand = id_brand;
		req.id_series = id_series;
		req.id_model = id_model;
		req.id_actuate = id_actuate;
		req.id_fuel = id_fuel;
		req.id_emissions = id_emissions;
		KaKuApiProvider.ChooseCar(req, new BaseCallback<ChooseCarResp>(ChooseCarResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, ChooseCarResp t) {
				stopProgressDialog();
				if (t != null) {
					LogUtil.E("choosecar res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						if ("0".equals(flag)){
							series_list = t.datas;
							adapter = new ChooseCarAdapter(ChooseCarActivity.this,series_list);
						} else if ("1".equals(flag)){
							model_list = t.datas;
							adapter = new ChooseCarAdapter(ChooseCarActivity.this,model_list);
						} else if ("2".equals(flag)){
							actuate_list = t.datas;
							adapter = new ChooseCarAdapter(ChooseCarActivity.this,actuate_list);
						} else if ("3".equals(flag)){
							fuel_list = t.datas;
							adapter = new ChooseCarAdapter(ChooseCarActivity.this,fuel_list);
						} else if ("4".equals(flag)){
							emissions_list = t.datas;
							adapter = new ChooseCarAdapter(ChooseCarActivity.this,emissions_list);
						} else if ("5".equals(flag)){
							power_list = t.datas;
							adapter = new ChooseCarAdapter(ChooseCarActivity.this,power_list);
						}
						gv.setAdapter(adapter);
						ll.setVisibility(View.VISIBLE);
					}else{
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

	public void setCheXiView(){
		LinearLayout lastLayout = (LinearLayout)gv_series.getChildAt(last_series);
		TextView tv_grid = (TextView)lastLayout.getChildAt(0);
		if ("Y".equals(tv_grid.getTag().toString())){
			tv_grid.setBackgroundResource(R.drawable.btn_white);
			tv_grid.setEnabled(true);
		} else {
			tv_grid.setBackgroundResource(R.drawable.btn_hui);
			tv_grid.setEnabled(false);
		}
		tv_grid.setTextColor(Color.BLACK);
	}
	public void setCheXingView(){
		LinearLayout lastLayout = (LinearLayout)gv_model.getChildAt(last_model);
		TextView tv_grid = (TextView)lastLayout.getChildAt(0);
		if ("Y".equals(tv_grid.getTag().toString())){
			tv_grid.setBackgroundResource(R.drawable.btn_white);
			tv_grid.setEnabled(true);
		} else {
			tv_grid.setBackgroundResource(R.drawable.btn_hui);
			tv_grid.setEnabled(false);
		}
		tv_grid.setTextColor(Color.BLACK);
	}
	public void setQuDongView(){
		LinearLayout lastLayout = (LinearLayout)gv_actuate.getChildAt(last_actuate);
		TextView tv_grid = (TextView)lastLayout.getChildAt(0);
		if ("Y".equals(tv_grid.getTag().toString())){
			tv_grid.setBackgroundResource(R.drawable.btn_white);
			tv_grid.setEnabled(true);
		} else {
			tv_grid.setBackgroundResource(R.drawable.btn_hui);
			tv_grid.setEnabled(false);
		}
		tv_grid.setTextColor(Color.BLACK);
	}
	public void setRanLiaoView(){
		LinearLayout lastLayout = (LinearLayout)gv_fuel.getChildAt(last_fuel);
		TextView tv_grid = (TextView)lastLayout.getChildAt(0);
		if ("Y".equals(tv_grid.getTag().toString())){
			tv_grid.setBackgroundResource(R.drawable.btn_white);
			tv_grid.setEnabled(true);
		} else {
			tv_grid.setBackgroundResource(R.drawable.btn_hui);
			tv_grid.setEnabled(false);
		}
		tv_grid.setTextColor(Color.BLACK);
	}
	public void setPaiFangView(){
		LinearLayout lastLayout = (LinearLayout)gv_emissions.getChildAt(last_emissions);
		TextView tv_grid = (TextView)lastLayout.getChildAt(0);
		if ("Y".equals(tv_grid.getTag().toString())){
			tv_grid.setBackgroundResource(R.drawable.btn_white);
			tv_grid.setEnabled(true);
		} else {
			tv_grid.setBackgroundResource(R.drawable.btn_hui);
			tv_grid.setEnabled(false);
		}
		tv_grid.setTextColor(Color.BLACK);
	}
	public void setGongLvView(){
		LinearLayout lastLayout = (LinearLayout)gv_gonglv.getChildAt(last_gonglv);
		TextView tv_grid = (TextView)lastLayout.getChildAt(0);
		if ("Y".equals(tv_grid.getTag().toString())){
			tv_grid.setBackgroundResource(R.drawable.btn_white);
			tv_grid.setEnabled(true);
		} else {
			tv_grid.setBackgroundResource(R.drawable.btn_hui);
			tv_grid.setEnabled(false);
		}
		tv_grid.setTextColor(Color.BLACK);
	}

	public void GoToCarList(){
		MobclickAgent.onEvent(context, "ChooseCar");
		Intent intent = new Intent(this,CarListActivity.class);
		intent.putExtra("id_brand",id_brand);
		intent.putExtra("id_series",id_series);
		intent.putExtra("id_model",id_model);
		intent.putExtra("id_actuate",id_actuate);
		intent.putExtra("id_fuel",id_fuel);
		intent.putExtra("id_emissions",id_emissions);
		intent.putExtra("id_gonglv",id_gonglv);
		startActivity(intent);
	}
}
