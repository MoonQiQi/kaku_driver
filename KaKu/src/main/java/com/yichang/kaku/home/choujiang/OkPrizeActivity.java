package com.yichang.kaku.home.choujiang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.member.address.AddrActivity;
import com.yichang.kaku.request.GetAddrReq;
import com.yichang.kaku.request.LingQuPrizeReq;
import com.yichang.kaku.response.GetAddrResp;
import com.yichang.kaku.response.LingQuPrizeResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class OkPrizeActivity extends BaseActivity implements OnClickListener{
	
	private TextView left,right,title;
	private TextView tv_okprize_noaddr,tv_okprize_people,tv_okprize_phone,tv_okprize_addr,tv_okprize_name;
	private ImageView iv_okprize_image;
	private Button btn_okprize_lignqu;
	private String id_wheel_win;
	private RelativeLayout rela_okprize_addr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_okprize);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("领取信息确认");
		tv_okprize_noaddr= (TextView) findViewById(R.id.tv_okprize_noaddr);
		tv_okprize_people= (TextView) findViewById(R.id.tv_okprize_people);
		tv_okprize_phone= (TextView) findViewById(R.id.tv_okprize_phone);
		tv_okprize_addr= (TextView) findViewById(R.id.tv_okprize_addr);
		tv_okprize_name= (TextView) findViewById(R.id.tv_okprize_name);
		iv_okprize_image= (ImageView) findViewById(R.id.iv_okprize_image);
		btn_okprize_lignqu= (Button) findViewById(R.id.btn_okprize_lignqu);
		btn_okprize_lignqu.setOnClickListener(this);
		rela_okprize_addr= (RelativeLayout) findViewById(R.id.rela_okprize_addr);
		rela_okprize_addr.setOnClickListener(this);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String image_prize = bundle.getString("image_prize");
		String name_prize = bundle.getString("name_prize");
		id_wheel_win = bundle.getString("id_wheel_win");
		tv_okprize_name.setText(name_prize);
		BitmapUtil.getInstance(context).download(iv_okprize_image, KaKuApplication.qian_zhui+image_prize);
		GetAddr();
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
		} else if (R.id.btn_okprize_lignqu == id){
			if (TextUtils.isEmpty(tv_okprize_addr.getText().toString().trim())){
				LogUtil.showShortToast(context,"请选择收货地址");
				return;
			}
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage("确认领取奖品？");
			builder.setNegativeButton("是", new android.content.DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					LingQuPrize();
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
		} else if (R.id.rela_okprize_addr == id){
			KaKuApplication.flag_addr = "Prize";
			Intent intent = new Intent(context, AddrActivity.class);
			startActivityForResult(intent,0);
		}
	}

	public void LingQuPrize(){
		Utils.NoNet(context);
		LingQuPrizeReq req = new LingQuPrizeReq();
		req.code = "70023";
		req.id_wheel_win = id_wheel_win;
		req.name_addr = tv_okprize_name.getText().toString().trim();
		req.phone_addr = tv_okprize_phone.getText().toString().trim();
		req.addr =tv_okprize_addr.getText().toString().trim();
		KaKuApiProvider.LingQuPrize(req, new KakuResponseListener<LingQuPrizeResp>(this,LingQuPrizeResp.class) {
			@Override
			public void onSucceed(int what, Response response) {
				super.onSucceed(what, response);
				if (t != null) {
					LogUtil.E("lingquprize res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						Intent intent = new Intent(context,MyPrizeActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
						LogUtil.showShortToast(context, t.msg);
					} else {
					}
					LogUtil.showShortToast(context, t.msg);
				}
			}

			@Override
			public void onFailed(int i, Response response) {

			}

		});
	}

	public void GetAddr(){
		GetAddrReq req = new GetAddrReq();
		req.code = "70022";
		req.id_driver = Utils.getIdDriver();
		KaKuApiProvider.GetAddr2(req, new KakuResponseListener<GetAddrResp>(this,GetAddrResp.class) {
			@Override
			public void onSucceed(int what, Response response) {
				super.onSucceed(what, response);
				if (t != null) {
					LogUtil.E("getaddr2 res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						if (TextUtils.isEmpty(t.addr.getAddr())){
							tv_okprize_noaddr.setVisibility(View.VISIBLE);
							tv_okprize_people.setVisibility(View.GONE);
							tv_okprize_phone.setVisibility(View.GONE);
							tv_okprize_addr.setVisibility(View.GONE);
						} else {
							tv_okprize_noaddr.setVisibility(View.GONE);
							tv_okprize_people.setVisibility(View.VISIBLE);
							tv_okprize_phone.setVisibility(View.VISIBLE);
							tv_okprize_addr.setVisibility(View.VISIBLE);
							tv_okprize_people.setText(t.addr.getName_addr());
							tv_okprize_phone.setText(t.addr.getPhone_addr());
							tv_okprize_addr.setText(t.addr.getAddr());
						}
					} else {
						LogUtil.showShortToast(context, t.msg);
					}
				}
			}

			@Override
			public void onFailed(int i, Response response) {

			}

		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case 0:
				if (data != null) {
					tv_okprize_people.setText(data.getExtras().getString("name"));
					tv_okprize_phone.setText(data.getExtras().getString("phone"));
					tv_okprize_addr.setText(data.getExtras().getString("addr"));
					tv_okprize_noaddr.setVisibility(View.GONE);
					tv_okprize_people.setVisibility(View.VISIBLE);
					tv_okprize_phone.setVisibility(View.VISIBLE);
					tv_okprize_addr.setVisibility(View.VISIBLE);
				}
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
