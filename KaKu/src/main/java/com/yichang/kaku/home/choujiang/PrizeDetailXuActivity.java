package com.yichang.kaku.home.choujiang;

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
import com.yichang.kaku.request.LingQuPrizeReq;
import com.yichang.kaku.response.LingQuPrizeResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

public class PrizeDetailXuActivity extends BaseActivity implements OnClickListener{
	
	private TextView left,right,title;
	private ImageView iv_prizedetail_prize2,iv_prizedetail_image2;
	private TextView tv_prizedetail_zhongjiangtime2,tv_prizedetail_name2;
	private TextView tv_prizedetail_wuliugongsi2,tv_prizedetail_dingdanbianhao2,tv_prizedetail_fahuotime2;
	private Button btn_prizedetail_lingqu2;
	private RelativeLayout rela_prizedetail_shouhuo2;
	private String image_prize,name_prize,id_wheel_win;
	private LinearLayout line_prizedetailxu_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prizedetailxu);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("奖品信息");
		iv_prizedetail_prize2= (ImageView) findViewById(R.id.iv_prizedetail_prize2);
		iv_prizedetail_image2= (ImageView) findViewById(R.id.iv_prizedetail_image2);
		tv_prizedetail_zhongjiangtime2= (TextView) findViewById(R.id.tv_prizedetail_zhongjiangtime2);
		tv_prizedetail_name2= (TextView) findViewById(R.id.tv_prizedetail_name2);
		tv_prizedetail_fahuotime2= (TextView) findViewById(R.id.tv_prizedetail_fahuotime2);
		tv_prizedetail_wuliugongsi2= (TextView) findViewById(R.id.tv_prizedetail_wuliugongsi2);
		tv_prizedetail_dingdanbianhao2= (TextView) findViewById(R.id.tv_prizedetail_dingdanbianhao2);
		btn_prizedetail_lingqu2= (Button) findViewById(R.id.btn_prizedetail_lingqu2);
		btn_prizedetail_lingqu2.setOnClickListener(this);
		line_prizedetailxu_info= (LinearLayout) findViewById(R.id.line_prizedetailxu_info);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String no_card =bundle.getString("no_card");
		String pass_card =bundle.getString("pass_card");
		name_prize =bundle.getString("name_prize");
		image_prize =bundle.getString("image_prize");
		String flag_exchange =bundle.getString("flag_exchange");
		String time_wheel =bundle.getString("time_wheel");
		String time_send =bundle.getString("time_send");
		id_wheel_win =bundle.getString("id_wheel_win");
		BitmapUtil.getInstance(context).download(iv_prizedetail_image2, KaKuApplication.qian_zhui+image_prize);
		tv_prizedetail_zhongjiangtime2.setText("中奖时间："+time_wheel);
		tv_prizedetail_name2.setText(name_prize);
		tv_prizedetail_fahuotime2.setText(time_send);
		tv_prizedetail_wuliugongsi2.setText(no_card);
		tv_prizedetail_dingdanbianhao2.setText(pass_card);
		if ("N".equals(flag_exchange)){
			//未领取
			iv_prizedetail_prize2.setImageResource(R.drawable.jiangpinhui);
			btn_prizedetail_lingqu2.setVisibility(View.VISIBLE);
			line_prizedetailxu_info.setVisibility(View.GONE);

		} else if("Y".equals(flag_exchange)){
			//已领取
			iv_prizedetail_prize2.setImageResource(R.drawable.jiangpinhong);
			btn_prizedetail_lingqu2.setVisibility(View.GONE);
			line_prizedetailxu_info.setVisibility(View.VISIBLE);

		} else if("I".equals(flag_exchange)){
			//领取中
			iv_prizedetail_prize2.setImageResource(R.drawable.jiangpinhongzhong);
			btn_prizedetail_lingqu2.setVisibility(View.GONE);
			line_prizedetailxu_info.setVisibility(View.GONE);

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
		} else if (R.id.btn_prizedetail_lingqu2 == id){
			LingQuPrize();
		}
	}

	public void LingQuPrize(){
		Utils.NoNet(context);
		LingQuPrizeReq req = new LingQuPrizeReq();
		req.code = "70023";
		req.id_wheel_win = id_wheel_win;
		req.name_addr = "";
		req.phone_addr = "";
		req.addr = "";
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
					} else {
						LogUtil.showShortToast(context, t.msg);
					}
				}
			}

		});
	}

}
