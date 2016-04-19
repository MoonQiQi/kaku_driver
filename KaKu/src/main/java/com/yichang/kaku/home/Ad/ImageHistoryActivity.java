package com.yichang.kaku.home.Ad;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.ImageHisObj;
import com.yichang.kaku.request.GetAddReq;
import com.yichang.kaku.request.ImageHisReq;
import com.yichang.kaku.response.GetAddResp;
import com.yichang.kaku.response.ImageHisResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.util.ArrayList;
import java.util.List;

public class ImageHistoryActivity extends BaseActivity implements OnClickListener{
	
	private TextView left,right,title;
	private ListView lv_imagehis;
	private List<ImageHisObj> list_imagehis = new ArrayList<ImageHisObj>();
	private ImageHistoryAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imagehistory);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("上传图片历史");
		lv_imagehis= (ListView) findViewById(R.id.lv_imagehis);

	}

	@Override
	protected void onStart() {
		super.onStart();
		GetList();
	}

	@Override
	public void onClick(View v) {
		Utils.NoNet(context);
		if (Utils.Many()){
			return;
		}
		int id = v.getId();
		if (R.id.tv_left == id) {
			GetAdd();
		} 
	}

	public void GetList(){
		showProgressDialog();
		ImageHisReq req = new ImageHisReq();
		req.code = "60016";
		req.id_driver = Utils.getIdDriver();
		req.id_advert = KaKuApplication.id_advert;
		KaKuApiProvider.getImageList(req, new KakuResponseListener<ImageHisResp>(this,ImageHisResp.class) {
			@Override
			public void onSucceed(int what, Response response) {
				super.onSucceed(what, response);
				if (t != null) {
					LogUtil.E("getimagelist res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						list_imagehis = t.driver_advert;
						adapter = new ImageHistoryAdapter(context,list_imagehis);
						lv_imagehis.setAdapter(adapter);
					}  else {
						LogUtil.showShortToast(context, t.msg);
					}
				}
				stopProgressDialog();
			}

		});
	}

	public void GetAdd(){
		GetAddReq req = new GetAddReq();
		req.code = "60011";
		req.id_driver = Utils.getIdDriver();
		req.id_advert = KaKuApplication.id_advert;
		KaKuApiProvider.GetAdd(req, new KakuResponseListener<GetAddResp>(this,GetAddResp.class) {

			@Override
			public void onSucceed(int what, Response response) {
				super.onSucceed(what, response);
				if (t != null) {
					LogUtil.E("getadd res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						KaKuApplication.id_advert = t.advert.getId_advert();
						KaKuApplication.flag_position = t.advert.getFlag_position();
						KaKuApplication.flag_show = t.advert.getFlag_show();

						GoToAdd(t.advert.getFlag_type());
					} else {
						LogUtil.showShortToast(context, t.msg);
					}
				}
			}

		});
	}

	public void GoToAdd(String flag_type){
		Intent intent = new Intent();
		if ("N".equals(flag_type)){
			intent.setClass(context,Add_NActivity.class);
		} else if ("Y".equals(flag_type)){
			intent.setClass(context,Add_YActivity.class);
		} else if ("E".equals(flag_type)){
			intent.setClass(context,Add_EActivity.class);
		} else if ("I".equals(flag_type)){
			intent.setClass(context,Add_IActivity.class);
		} else if ("F".equals(flag_type)){
			intent.setClass(context,Add_FActivity.class);
		} else if ("P".equals(flag_type)){
			intent.setClass(context,Add_PActivity.class);
		} else if ("A".equals(flag_type)){
			intent.setClass(context,CheTieListActivity.class);
		} else if ("M".equals(flag_type)){
			intent.setClass(context,Add_MActivity.class);
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK )
		{
			GetAdd();
		}
		return false;
	}

}
