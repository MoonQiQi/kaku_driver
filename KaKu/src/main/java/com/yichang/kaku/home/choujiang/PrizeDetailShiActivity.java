package com.yichang.kaku.home.choujiang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.request.XianChangLingQuReq;
import com.yichang.kaku.response.XianChangLingQuResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

public class PrizeDetailShiActivity extends BaseActivity implements OnClickListener{
	
	private TextView left,right,title;
	private ImageView iv_prizedetail_prize,iv_prizedetail_image;
	private TextView tv_prizedetail_zhongjiangtime,tv_prizedetail_people,tv_prizedetail_phone,tv_prizedetail_addr,tv_prizedetail_name;
	private TextView tv_prizedetail_wuliugongsi,tv_prizedetail_dingdanbianhao,tv_prizedetail_fahuotime;
	private Button btn_prizedetail_lingqu,btn_prizedetail_xianchang;
	private RelativeLayout rela_prizedetail_shouhuo;
	private String image_prize,name_prize,id_wheel_win;
	private LinearLayout line_prizedetailshi_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prizedetailshi);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("奖品信息");
		iv_prizedetail_prize= (ImageView) findViewById(R.id.iv_prizedetail_prize);
		iv_prizedetail_image= (ImageView) findViewById(R.id.iv_prizedetail_image);
		tv_prizedetail_zhongjiangtime= (TextView) findViewById(R.id.tv_prizedetail_zhongjiangtime);
		tv_prizedetail_people= (TextView) findViewById(R.id.tv_prizedetail_people);
		tv_prizedetail_phone= (TextView) findViewById(R.id.tv_prizedetail_phone);
		tv_prizedetail_addr= (TextView) findViewById(R.id.tv_prizedetail_addr);
		tv_prizedetail_name= (TextView) findViewById(R.id.tv_prizedetail_name);
		tv_prizedetail_fahuotime= (TextView) findViewById(R.id.tv_prizedetail_fahuotime);
		tv_prizedetail_wuliugongsi= (TextView) findViewById(R.id.tv_prizedetail_wuliugongsi);
		tv_prizedetail_dingdanbianhao= (TextView) findViewById(R.id.tv_prizedetail_dingdanbianhao);
		rela_prizedetail_shouhuo= (RelativeLayout) findViewById(R.id.rela_prizedetail_shouhuo);
		rela_prizedetail_shouhuo.setOnClickListener(this);
		btn_prizedetail_lingqu= (Button) findViewById(R.id.btn_prizedetail_lingqu);
		btn_prizedetail_lingqu.setOnClickListener(this);
		btn_prizedetail_xianchang= (Button) findViewById(R.id.btn_prizedetail_xianchang);
		btn_prizedetail_xianchang.setOnClickListener(this);
		line_prizedetailshi_info= (LinearLayout) findViewById(R.id.line_prizedetailshi_info);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String addr_driver =bundle.getString("addr_driver");
		name_prize =bundle.getString("name_prize");
		String name_driver =bundle.getString("name_driver");
		image_prize =bundle.getString("image_prize");
		String flag_exchange =bundle.getString("flag_exchange");
		String phone_driver =bundle.getString("phone_driver");
		String time_send =bundle.getString("time_send");
		String time_wheel =bundle.getString("time_wheel");
		String company_delivery =bundle.getString("company_delivery");
		String no_delivery =bundle.getString("no_delivery");
		id_wheel_win =bundle.getString("id_wheel_win");
		BitmapUtil.getInstance(context).download(iv_prizedetail_image, KaKuApplication.qian_zhui+image_prize);
		tv_prizedetail_zhongjiangtime.setText("中奖时间："+time_wheel);
		tv_prizedetail_people.setText(name_driver);
		tv_prizedetail_phone.setText(phone_driver);
		tv_prizedetail_addr.setText(addr_driver);
		tv_prizedetail_name.setText(name_prize);
		tv_prizedetail_fahuotime.setText(time_send);
		tv_prizedetail_wuliugongsi.setText(company_delivery);
		tv_prizedetail_dingdanbianhao.setText(no_delivery);
		if ("N".equals(flag_exchange)){
			//未领取
			iv_prizedetail_prize.setImageResource(R.drawable.jiangpinhui);
			btn_prizedetail_lingqu.setVisibility(View.VISIBLE);
			btn_prizedetail_xianchang.setVisibility(View.VISIBLE);
			rela_prizedetail_shouhuo.setVisibility(View.GONE);
			line_prizedetailshi_info.setVisibility(View.GONE);

		} else if ("Y".equals(flag_exchange)){
			//已领取
			iv_prizedetail_prize.setImageResource(R.drawable.jiangpinhong);
			btn_prizedetail_lingqu.setVisibility(View.GONE);
			btn_prizedetail_xianchang.setVisibility(View.GONE);
			rela_prizedetail_shouhuo.setVisibility(View.VISIBLE);
			line_prizedetailshi_info.setVisibility(View.VISIBLE);

		}else if("I".equals(flag_exchange)){
			//领取中
			iv_prizedetail_prize.setImageResource(R.drawable.jiangpinhongzhong);
			btn_prizedetail_lingqu.setVisibility(View.GONE);
			btn_prizedetail_xianchang.setVisibility(View.GONE);
			line_prizedetailshi_info.setVisibility(View.GONE);
		}
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
		} else if (R.id.btn_prizedetail_lingqu == id){
			Intent intent = new Intent(this,OkPrizeActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("image_prize",image_prize);
			bundle.putString("name_prize",name_prize);
			bundle.putString("id_wheel_win",id_wheel_win);
			intent.putExtras(bundle);
			startActivity(intent);
		} else if (R.id.rela_prizedetail_shouhuo == id){

		} else if (R.id.btn_prizedetail_xianchang == id){
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage("确认领取奖品？");
			builder.setNegativeButton("是", new android.content.DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					XianChangLingQu();
				}
			});

			builder.setPositiveButton("否", new android.content.DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			builder.create().show();
		}
	}

	public void XianChangLingQu(){
		XianChangLingQuReq req = new XianChangLingQuReq();
		req.code = "70020";
		req.id_wheel_win = id_wheel_win;
		KaKuApiProvider.XianChangLingQu(req, new KakuResponseListener<XianChangLingQuResp>(this,XianChangLingQuResp.class) {
			@Override
			public void onSucceed(int what, Response response) {
				super.onSucceed(what, response);
				if (t != null) {
					LogUtil.E("xianchang res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						Intent intent = new Intent(context,MyPrizeActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
					} else {
						LogUtil.showShortToast(context, t.msg);
					}
				}
			}
		});
	}

}
