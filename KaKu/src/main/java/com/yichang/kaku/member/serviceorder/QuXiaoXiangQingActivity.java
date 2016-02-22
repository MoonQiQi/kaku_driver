package com.yichang.kaku.member.serviceorder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.request.ShenQingQuXiaoReq;
import com.yichang.kaku.response.ShenQingQuXiaoResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

public class QuXiaoXiangQingActivity extends BaseActivity implements OnClickListener{
	
	private TextView left,right,title;
	private RelativeLayout rela_quxiao_reason1,rela_quxiao_reason2,rela_quxiao_reason3,rela_quxiao_reason4,rela_quxiao_reason5;
	private String flag1 = "N";
	private String flag2 = "N";
	private String flag3 = "N";
	private String flag4 = "N";
	private String flag5 = "N";
	private Button btn_quxiao_commit;
	private ImageView iv_quxiao_reason1,iv_quxiao_reason2,iv_quxiao_reason3,iv_quxiao_reason4,iv_quxiao_reason5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shenqingquxiao);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("申请取消");
		rela_quxiao_reason1= (RelativeLayout) findViewById(R.id.rela_quxiao_reason1);
		rela_quxiao_reason2= (RelativeLayout) findViewById(R.id.rela_quxiao_reason2);
		rela_quxiao_reason3= (RelativeLayout) findViewById(R.id.rela_quxiao_reason3);
		rela_quxiao_reason4= (RelativeLayout) findViewById(R.id.rela_quxiao_reason4);
		rela_quxiao_reason5= (RelativeLayout) findViewById(R.id.rela_quxiao_reason5);
		rela_quxiao_reason1.setOnClickListener(this);
		rela_quxiao_reason2.setOnClickListener(this);
		rela_quxiao_reason3.setOnClickListener(this);
		rela_quxiao_reason4.setOnClickListener(this);
		rela_quxiao_reason5.setOnClickListener(this);
		btn_quxiao_commit= (Button) findViewById(R.id.btn_quxiao_commit);
		btn_quxiao_commit.setOnClickListener(this);
		iv_quxiao_reason1= (ImageView) findViewById(R.id.iv_quxiao_reason1);
		iv_quxiao_reason2= (ImageView) findViewById(R.id.iv_quxiao_reason2);
		iv_quxiao_reason3= (ImageView) findViewById(R.id.iv_quxiao_reason3);
		iv_quxiao_reason4= (ImageView) findViewById(R.id.iv_quxiao_reason4);
		iv_quxiao_reason5= (ImageView) findViewById(R.id.iv_quxiao_reason5);
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
		} else if (R.id.rela_quxiao_reason1 == id){
			if ("N".equals(flag1)){
				flag1 = "Y";
				iv_quxiao_reason1.setBackgroundResource(R.drawable.check_yuan);
			} else {
				flag1 = "N";
				iv_quxiao_reason1.setBackgroundResource(R.drawable.uncheck_yuan);
			}
		} else if (R.id.rela_quxiao_reason2 == id){
			if ("N".equals(flag2)){
				flag2 = "Y";
				iv_quxiao_reason2.setBackgroundResource(R.drawable.check_yuan);
			} else {
				flag2 = "N";
				iv_quxiao_reason2.setBackgroundResource(R.drawable.uncheck_yuan);
			}
		} else if (R.id.rela_quxiao_reason3 == id){
			if ("N".equals(flag3)){
				flag3 = "Y";
				iv_quxiao_reason3.setBackgroundResource(R.drawable.check_yuan);
			} else {
				flag3 = "N";
				iv_quxiao_reason3.setBackgroundResource(R.drawable.uncheck_yuan);
			}
		} else if (R.id.rela_quxiao_reason4 == id){
			if ("N".equals(flag4)){
				flag4 = "Y";
				iv_quxiao_reason4.setBackgroundResource(R.drawable.check_yuan);
			} else {
				flag4 = "N";
				iv_quxiao_reason4.setBackgroundResource(R.drawable.uncheck_yuan);
			}
		} else if (R.id.rela_quxiao_reason5 == id){
			if ("N".equals(flag5)){
				flag5 = "Y";
				iv_quxiao_reason5.setBackgroundResource(R.drawable.check_yuan);
			} else {
				flag5 = "N";
				iv_quxiao_reason5.setBackgroundResource(R.drawable.uncheck_yuan);
			}
		} else if (R.id.btn_quxiao_commit == id){
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage("取消订单后，存在的优惠可能一并取消，是否继续？");
			builder.setNegativeButton("是", new android.content.DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Commit();
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

	public void Commit(){
        Utils.NoNet(context);
        showProgressDialog();
        ShenQingQuXiaoReq req = new ShenQingQuXiaoReq();
        req.code = "40028";
        req.id_order = KaKuApplication.id_orderB;
		req.reason_cancel = flag1+","+flag2+","+flag3+","+flag4+","+flag5+",";
        KaKuApiProvider.ShenQingQuXiao(req, new BaseCallback<ShenQingQuXiaoResp>(ShenQingQuXiaoResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, ShenQingQuXiaoResp t) {
				if (t != null) {
					LogUtil.E("shenqingquxiao res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						Intent intent = new Intent(context,QuXiaoChengGongActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
					} else {
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

}
