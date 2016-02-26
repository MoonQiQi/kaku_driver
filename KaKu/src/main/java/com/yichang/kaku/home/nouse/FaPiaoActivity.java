package com.yichang.kaku.home.nouse;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;

public class FaPiaoActivity extends BaseActivity implements OnClickListener{
	
	private TextView left,right,title;
	private ImageView iv_fapiao_zhizhifapiao,iv_fapiao_dianzifapiao,iv_fapiao_bukaifapiao,iv_fapiao_taitou;
	private LinearLayout line_fapiaotaitou,line_shoupiaorenxinxi,line_fapiaoneirong;
	private EditText et_fapiao_taitou,et_fapiao_shoujianrenshouji,et_fapiao_shoujianrenyouxiang;
	private Button btn_fapiao_ok;
	private String flag_type = "3";
	private String flag_content = "3";
	private RelativeLayout rela_cheliangpeijian,rela_mingxi,rela_shenghuoyongpin;
	private ImageView cb_order_cheliangpeijian,cb_order_mingxi,cb_order_shenghuoyongpin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fapiao);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		right= (TextView) findViewById(R.id.tv_right);
		right.setVisibility(View.VISIBLE);
		right.setText("发票须知");
		right.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("发票信息");
		iv_fapiao_zhizhifapiao= (ImageView) findViewById(R.id.iv_fapiao_zhizhifapiao);
		iv_fapiao_zhizhifapiao.setOnClickListener(this);
		iv_fapiao_dianzifapiao= (ImageView) findViewById(R.id.iv_fapiao_dianzifapiao);
		iv_fapiao_dianzifapiao.setOnClickListener(this);
		iv_fapiao_bukaifapiao= (ImageView) findViewById(R.id.iv_fapiao_bukaifapiao);
		iv_fapiao_bukaifapiao.setOnClickListener(this);
		iv_fapiao_taitou= (ImageView) findViewById(R.id.iv_fapiao_taitou);
		line_fapiaotaitou = (LinearLayout) findViewById(R.id.line_fapiaotaitou);
		line_shoupiaorenxinxi = (LinearLayout) findViewById(R.id.line_shoupiaorenxinxi);
		line_fapiaoneirong = (LinearLayout) findViewById(R.id.line_fapiaoneirong);
		line_shoupiaorenxinxi = (LinearLayout) findViewById(R.id.line_shoupiaorenxinxi);
		btn_fapiao_ok= (Button) findViewById(R.id.btn_fapiao_ok);
		btn_fapiao_ok.setOnClickListener(this);
		et_fapiao_taitou= (EditText) findViewById(R.id.et_fapiao_taitou);
		et_fapiao_shoujianrenshouji= (EditText) findViewById(R.id.et_fapiao_shoujianrenshouji);
		et_fapiao_shoujianrenyouxiang= (EditText) findViewById(R.id.et_fapiao_shoujianrenyouxiang);
		rela_cheliangpeijian= (RelativeLayout) findViewById(R.id.rela_cheliangpeijian);
		rela_cheliangpeijian.setOnClickListener(this);
		rela_mingxi= (RelativeLayout) findViewById(R.id.rela_mingxi);
		rela_mingxi.setOnClickListener(this);
		rela_shenghuoyongpin= (RelativeLayout) findViewById(R.id.rela_shenghuoyongpin);
		rela_shenghuoyongpin.setOnClickListener(this);
		cb_order_cheliangpeijian= (ImageView) findViewById(R.id.cb_order_cheliangpeijian);
		cb_order_mingxi= (ImageView) findViewById(R.id.cb_order_mingxi);
		cb_order_shenghuoyongpin= (ImageView) findViewById(R.id.cb_order_shenghuoyongpin);
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
		} else if (R.id.tv_right == id){

		} else if (R.id.iv_fapiao_zhizhifapiao == id){
			flag_type = "0";
			SetFapiaoLeixing();
			iv_fapiao_zhizhifapiao.setImageResource(R.drawable.zhizhifapiaohong);
			line_fapiaotaitou.setVisibility(View.VISIBLE);
			line_shoupiaorenxinxi.setVisibility(View.GONE);
			line_fapiaoneirong.setVisibility(View.VISIBLE);
			et_fapiao_taitou.setVisibility(View.VISIBLE);
			iv_fapiao_taitou.setVisibility(View.GONE);

		} else if (R.id.iv_fapiao_dianzifapiao == id){
			flag_type = "1";
			SetFapiaoLeixing();
			iv_fapiao_dianzifapiao.setImageResource(R.drawable.dianzifapiaohong);
			line_fapiaotaitou.setVisibility(View.VISIBLE);
			line_shoupiaorenxinxi.setVisibility(View.VISIBLE);
			line_fapiaoneirong.setVisibility(View.VISIBLE);
			et_fapiao_taitou.setVisibility(View.GONE);
			iv_fapiao_taitou.setVisibility(View.VISIBLE);
		} else if (R.id.iv_fapiao_bukaifapiao == id){
			flag_type = "2";
			SetFapiaoLeixing();
			flag_content = "";
			iv_fapiao_bukaifapiao.setImageResource(R.drawable.bukaifapiaohong);
			line_fapiaotaitou.setVisibility(View.GONE);
			line_shoupiaorenxinxi.setVisibility(View.GONE);
			line_fapiaoneirong.setVisibility(View.GONE);
		} else if (R.id.btn_fapiao_ok == id){
			if ("3".equals(flag_type)){
				LogUtil.showShortToast(context,"请选择发票类型");
				return;
			}
			if ("3".equals(flag_content)){
				LogUtil.showShortToast(context,"请选择发票内容");
				return;
			}
			if ("0".equals(flag_type)){
				if (TextUtils.isEmpty(et_fapiao_taitou.getText().toString().trim())){
					LogUtil.showShortToast(context,"请填写发票抬头");
					return;
				}
			}
			if ("1".equals(flag_type)){
				if (TextUtils.isEmpty(et_fapiao_shoujianrenshouji.getText().toString().trim())){
					LogUtil.showShortToast(context,"请填写收票人手机号");
					return;
				}
			}

			Intent intent = new Intent();
			intent.putExtra("type_invoice",flag_type);
			intent.putExtra("type_invoice1",flag_content);
			intent.putExtra("var_invoice",et_fapiao_taitou.getText().toString().trim());
			intent.putExtra("phone_invoice",et_fapiao_shoujianrenshouji.getText().toString().trim());
			intent.putExtra("email_invoice",et_fapiao_shoujianrenyouxiang.getText().toString().trim());
			setResult(RESULT_OK, intent);
			finish();

		} else if (R.id.rela_cheliangpeijian == id){
			SetFapiaoNeiRong();
			flag_content = "0";
			cb_order_cheliangpeijian.setImageResource(R.drawable.check_yuan);
		} else if (R.id.rela_mingxi == id){
			SetFapiaoNeiRong();
			flag_content = "1";
			cb_order_mingxi.setImageResource(R.drawable.check_yuan);
		} else if (R.id.rela_shenghuoyongpin == id){
			SetFapiaoNeiRong();
			flag_content = "2";
			cb_order_shenghuoyongpin.setImageResource(R.drawable.check_yuan);
		}

	}

	public void SetFapiaoLeixing(){
		iv_fapiao_zhizhifapiao.setImageResource(R.drawable.zhizhifapiaohei);
		iv_fapiao_dianzifapiao.setImageResource(R.drawable.dianzifapiaohei);
		iv_fapiao_bukaifapiao.setImageResource(R.drawable.bukaifapiaohei);
	}

	public void SetFapiaoNeiRong(){
		cb_order_cheliangpeijian.setImageResource(R.drawable.uncheck_yuan);
		cb_order_mingxi.setImageResource(R.drawable.uncheck_yuan);
		cb_order_shenghuoyongpin.setImageResource(R.drawable.uncheck_yuan);
	}

}
