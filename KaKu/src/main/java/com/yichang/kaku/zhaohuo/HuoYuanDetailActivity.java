package com.yichang.kaku.zhaohuo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wly.android.widget.AdGalleryHelper;
import com.wly.android.widget.Advertising;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.RollsObj;
import com.yichang.kaku.request.HuoYuanDetailReq;
import com.yichang.kaku.response.HuoYuanDetailResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.util.ArrayList;
import java.util.List;

public class HuoYuanDetailActivity extends BaseActivity implements OnClickListener{
	
	private TextView left,right,title;
	private TextView tv_huoyuandetail_qidian,tv_huoyuandetail_zhongdian,tv_huoyuandetail_fabushijian,
			         tv_huoyuandetail_huoyuanxinxi,tv_huoyuandetail_zonglichengshu,tv_huoyuandetail_cankaojiage;
	private Button btn_huoyuandetail_call,btn_huoyuandetail_pingjia;
	private String phone;
	private String id_supply;
	private RelativeLayout rela_huoyuandetail_gallery;
	private AdGalleryHelper mGalleryHelper;
	private List<RollsObj> rolls_list = new ArrayList<RollsObj>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_huoyuandetail);
		init();
	}
	
	private void init() {
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("货源详情");
		btn_huoyuandetail_call= (Button) findViewById(R.id.btn_huoyuandetail_call);
		btn_huoyuandetail_call.setOnClickListener(this);
		btn_huoyuandetail_pingjia= (Button) findViewById(R.id.btn_huoyuandetail_pingjia);
		btn_huoyuandetail_pingjia.setOnClickListener(this);
		btn_huoyuandetail_pingjia.setVisibility(View.GONE);
		tv_huoyuandetail_qidian= (TextView) findViewById(R.id.tv_huoyuandetail_qidian);
		tv_huoyuandetail_zhongdian= (TextView) findViewById(R.id.tv_huoyuandetail_zhongdian);
		tv_huoyuandetail_fabushijian= (TextView) findViewById(R.id.tv_huoyuandetail_fabushijian);
		tv_huoyuandetail_huoyuanxinxi= (TextView) findViewById(R.id.tv_huoyuandetail_huoyuanxinxi);
		tv_huoyuandetail_zonglichengshu= (TextView) findViewById(R.id.tv_huoyuandetail_zonglichengshu);
		tv_huoyuandetail_cankaojiage= (TextView) findViewById(R.id.tv_huoyuandetail_cankaojiage);
		rela_huoyuandetail_gallery = (RelativeLayout) findViewById(R.id.huoyuandetail_gallery);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id_supply = bundle.getString("id_supply");
		HuoYuanDetail();
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
		} else if (R.id.btn_huoyuandetail_call == id){
			String call_string = phone.split(",")[0];
			Utils.Call(HuoYuanDetailActivity.this, call_string);
		} else if (R.id.btn_huoyuandetail_pingjia == id){
			GoToPingJia();
		}
	}

	public void HuoYuanDetail(){
		Utils.NoNet(context);
		HuoYuanDetailReq req = new HuoYuanDetailReq();
		req.code = "6002";
		req.id_supply = id_supply;
		KaKuApiProvider.HuoYuanDetail(req, new KakuResponseListener<HuoYuanDetailResp>(this, HuoYuanDetailResp.class) {
			@Override
			public void onSucceed(int what, Response response) {
				super.onSucceed(what, response);
				if (t != null) {
					LogUtil.E("huoyuandetail res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						rolls_list = t.rolls;
						autoAdvance(rolls_list);
						tv_huoyuandetail_qidian.setText(t.supply.getArea_depart());
						tv_huoyuandetail_zhongdian.setText(t.supply.getArea_arrive());
						tv_huoyuandetail_fabushijian.setText(t.supply.getTime_pub());
						tv_huoyuandetail_huoyuanxinxi.setText(t.supply.getRemark_supply());
						tv_huoyuandetail_zonglichengshu.setText(t.supply.getMileage_supply());
						tv_huoyuandetail_cankaojiage.setText("¥ " + t.supply.getPrice_supply());
						phone = t.supply.getPhone_supply();
					} else {
						LogUtil.showShortToast(context, t.msg);
					}
				}
			}

		});
	}

	public void GoToPingJia(){
		Intent intent = new Intent(context,PingJiaHuoDanActivity.class);
		intent.putExtra("id_supply",id_supply);
		startActivity(intent);
	}

	private void autoAdvance(List<RollsObj> imgList) {
		// TODO Auto-generated method stub
		if (imgList == null) {
			return;
		}
		if (imgList.size() <= 0) {
			return;
		}
		List<Advertising> list = new ArrayList<Advertising>();

		for (RollsObj obj : imgList) {
			Advertising advertising = new Advertising(obj.getImage_roll(), obj.getUrl_roll(), null);
			advertising.setPicURL(KaKuApplication.qian_zhui + obj.getImage_roll());
			list.add(advertising);
		}

		if (list.size() > 0) {
			mGalleryHelper = new AdGalleryHelper(context, list, Constants.AUTO_SCROLL_DURATION,false,false,true);
			rela_huoyuandetail_gallery.addView(mGalleryHelper.getLayout());

		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mGalleryHelper != null) {
			mGalleryHelper.stopAutoSwitch();
		}
	}

}
