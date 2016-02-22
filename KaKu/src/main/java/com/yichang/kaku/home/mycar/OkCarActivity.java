package com.yichang.kaku.home.mycar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.request.GetOkCarReq;
import com.yichang.kaku.request.OkCarReq;
import com.yichang.kaku.response.GetOkCarResp;
import com.yichang.kaku.response.OkCarResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

public class OkCarActivity extends BaseActivity implements OnClickListener{
	
	private TextView left,right,title;
	private ImageView iv_okcar_image;
	private TextView tv_okar_pinpai,tv_okcar_chexinginfo,tv_okcar_qudonginfo,tv_okcar_ranliaoinfo,
			         tv_okcar_paifanginfo,tv_okcar_gonglvinfo;
	private String id_car;
	private Button btn_okcar_save;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_okcar);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("我的爱车");
		btn_okcar_save= (Button) findViewById(R.id.btn_okcar_save);
		btn_okcar_save.setOnClickListener(this);
		iv_okcar_image = (ImageView) findViewById(R.id.iv_okcar_image);
		tv_okar_pinpai = (TextView) findViewById(R.id.tv_okar_pinpai);
		tv_okcar_chexinginfo = (TextView) findViewById(R.id.tv_okcar_chexinginfo);
		tv_okcar_qudonginfo = (TextView) findViewById(R.id.tv_okcar_qudonginfo);
		tv_okcar_ranliaoinfo = (TextView) findViewById(R.id.tv_okcar_ranliaoinfo);
		tv_okcar_paifanginfo = (TextView) findViewById(R.id.tv_okcar_paifanginfo);
		tv_okcar_gonglvinfo = (TextView) findViewById(R.id.tv_okcar_gonglvinfo);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id_car = bundle.getString("id_car");
		GetOkCar(id_car);
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
		} else if (R.id.btn_okcar_save == id){
			MobclickAgent.onEvent(context, "SaveCar");
			OkCar(id_car);
		}
	}

	public void OkCar(final String id_car ){
		showProgressDialog();
		OkCarReq req = new OkCarReq();
		req.code = "2005";
		req.id_driver = Utils.getIdDriver();
		req.id_car = id_car;
		KaKuApiProvider.OkCar(req, new BaseCallback<OkCarResp>(OkCarResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, OkCarResp t) {
				if (t != null) {
					LogUtil.E("okcar res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						GoToMyCar();
						SharedPreferences.Editor editor = KaKuApplication.editor;
						editor.putString(Constants.IDCAR,id_car);
						editor.commit();
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

	public void GetOkCar(String id_car){
		showProgressDialog();
		GetOkCarReq req = new GetOkCarReq();
		req.code = "20010";
		req.id_car = id_car;
		KaKuApiProvider.GetOkCar(req, new BaseCallback<GetOkCarResp>(GetOkCarResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, GetOkCarResp t) {
				if (t != null) {
					LogUtil.E("getokcar res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						BitmapUtil.getInstance(OkCarActivity.this).download(iv_okcar_image,KaKuApplication.qian_zhui+t.car.getImage_brand());
						tv_okar_pinpai.setText(t.car.getName_brand()+"—"+t.car.getName_series());
						tv_okcar_chexinginfo.setText(t.car.getName_model());
						tv_okcar_qudonginfo.setText(t.car.getName_actuate());
						tv_okcar_ranliaoinfo.setText(t.car.getName_fuel());
						tv_okcar_paifanginfo.setText(t.car.getName_emissions());
						tv_okcar_gonglvinfo.setText(t.car.getName_power());
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

	public void GoToMyCar(){
		startActivity(new Intent(this,MyCarActivity.class));
	}

}
