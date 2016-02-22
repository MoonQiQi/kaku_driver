package com.yichang.kaku.home.Ad;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.obj.ImageHisObj;
import com.yichang.kaku.obj.RollsAddObj;
import com.yichang.kaku.request.GetAddReq;
import com.yichang.kaku.request.ImageHisReq;
import com.yichang.kaku.response.GetAddResp;
import com.yichang.kaku.response.ImageHisResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImageHistoryActivity extends BaseActivity implements OnClickListener{
	
	private TextView left,right,title;
	private ListView lv_imagehis;
	private List<ImageHisObj> list_imagehis = new ArrayList<ImageHisObj>();
	private ImageHistoryAdapter adapter;
	private String name_advert;
	private String now_earnings;
	private String total_earning;
	private String day_remaining;
	private String day_continue;
	private String image_size;
	private String image_advert;
	private String free_remind;
	private String num_driver;
	private String time_end;
	private String time_begin;
	private String day_earnings;
	private String approve_opinions;
	private String image0_advert;
	private String image1_advert;
	private String image2_advert;
	private String image0_approve;
	private String image1_approve;
	private String image2_approve;
	private List<RollsAddObj> rollsadd_list;
	
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
		adapter = new ImageHistoryAdapter(context,list_imagehis);
		lv_imagehis.setAdapter(adapter);
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
		req.id_advert = "1";
		KaKuApiProvider.getImageList(req, new BaseCallback<ImageHisResp>(ImageHisResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, ImageHisResp t) {
				if (t != null) {
					LogUtil.E("getimagelist res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						list_imagehis = t.driver_advert;
						adapter = new ImageHistoryAdapter(context,list_imagehis);
						lv_imagehis.setAdapter(adapter);
						Utils.setListViewHeightBasedOnChildren(lv_imagehis);
					}  else {
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

	public void GetAdd(){
		showProgressDialog();
		GetAddReq req = new GetAddReq();
		req.code = "60011";
		req.id_driver = Utils.getIdDriver();
		req.id_advert = "1";
		KaKuApiProvider.GetAdd(req, new BaseCallback<GetAddResp>(GetAddResp.class) {

			@Override
			public void onSuccessful(int statusCode, Header[] headers, GetAddResp t) {
				if (t != null) {
					LogUtil.E("getadd res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						name_advert = t.advert.getName_advert();
						day_earnings = t.advert.getDay_earnings();
						time_begin = t.advert.getTime_begin();
						time_end = t.advert.getTime_end();
						num_driver = t.advert.getNum_driver();
						free_remind = t.advert.getFree_remind();
						image_advert = t.advert.getImage_advert();
						image_size = t.advert.getImage_size();
						day_continue = t.advert.getDay_continue();
						day_remaining = t.advert.getDay_remaining();
						total_earning = t.advert.getTotal_earnings();
						approve_opinions = t.advert.getApprove_opinions();
						now_earnings = t.advert.getNow_earnings();
						image0_advert = t.advert.getImage0_advert();
						image1_advert = t.advert.getImage1_advert();
						image2_advert = t.advert.getImage2_advert();
						image0_approve = t.advert.getImage0_approve();
						image1_approve = t.advert.getImage1_approve();
						image2_approve = t.advert.getImage2_approve();
						rollsadd_list = t.rolls;
						GoToAdd(t.advert.getFlag_type());
					} else {
						if (Constants.RES_TEN.equals(t.res)){
							Utils.Exit(context);
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

	public void GoToAdd(String flag_type){
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		LogUtil.E("flag:"+flag_type);
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
		}
		bundle.putString("name_advert",name_advert);
		bundle.putString("day_earnings",day_earnings);
		bundle.putString("time_begin",time_begin);
		bundle.putString("time_end",time_end);
		bundle.putString("free_remind",free_remind);
		bundle.putString("num_driver",num_driver);
		bundle.putString("image_advert",image_advert);
		bundle.putString("image_size",image_size);
		bundle.putString("day_continue",day_continue);
		bundle.putString("day_remaining",day_remaining);
		bundle.putString("total_earning",total_earning);
		bundle.putString("now_earnings",now_earnings);
		bundle.putString("approve_opinions",approve_opinions);
		bundle.putString("flag_type",flag_type);
		bundle.putString("image0_advert",image0_advert);
		bundle.putString("image1_advert",image1_advert);
		bundle.putString("image2_advert",image2_advert);
		bundle.putString("image0_approve",image0_approve);
		bundle.putString("image1_approve",image1_approve);
		bundle.putString("image2_approve",image2_approve);
		bundle.putSerializable("rollsadd_list", (Serializable) rollsadd_list);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtras(bundle);
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
