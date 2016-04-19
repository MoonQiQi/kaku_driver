package com.yichang.kaku.member.serviceorder;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.request.FanXiuDetailReq;
import com.yichang.kaku.response.FanXiuDetailResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

public class OrderDetailZActivity extends BaseActivity implements OnClickListener{

	private TextView left,right,title;
	private Button btn_Z_call;
	private TextView tv_Z_time,tv_Z_shouhoubianhao,tv_Z_fanxiushuoming;
	private ImageView iv_Z_image;
	private String phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderdetailz);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("返修详情");
		btn_Z_call= (Button) findViewById(R.id.btn_Z_call);
		btn_Z_call.setOnClickListener(this);
		iv_Z_image= (ImageView) findViewById(R.id.iv_Z_image);
		tv_Z_time= (TextView) findViewById(R.id.tv_Z_time);
		tv_Z_shouhoubianhao= (TextView) findViewById(R.id.tv_Z_shouhoubianhao);
		tv_Z_fanxiushuoming= (TextView) findViewById(R.id.tv_Z_fanxiushuoming);
		FanXiuDetail();
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
		}else if (R.id.btn_Z_call == id){
			Utils.Call(context, phone);
		}
	}

	public void FanXiuDetail(){
		Utils.NoNet(context);
		FanXiuDetailReq req = new FanXiuDetailReq();
		req.code = "40025";
		req.id_order = KaKuApplication.id_orderZ;
		KaKuApiProvider.FanXiuDetail(req, new KakuResponseListener<FanXiuDetailResp>(this, FanXiuDetailResp.class) {
			@Override
			public void onSucceed(int what, Response response) {
				super.onSucceed(what, response);
				if (t != null) {
					LogUtil.E("fanxiudetail res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						phone = t.repair.getTel_repair();
						tv_Z_time.setText("申请时间：" + t.repair.getTime_repair());
						tv_Z_fanxiushuoming.setText(t.repair.getDescribe_repair());
						tv_Z_shouhoubianhao.setText(t.repair.getId_repair());
						if (!"".equals(t.repair.getImage_repair())) {
							BitmapUtil.getInstance(context).download(iv_Z_image, KaKuApplication.qian_zhui + t.repair.getImage_repair());
						}
					} else {
						LogUtil.showShortToast(context, t.msg);
					}
				}
			}

		});
	}

}
