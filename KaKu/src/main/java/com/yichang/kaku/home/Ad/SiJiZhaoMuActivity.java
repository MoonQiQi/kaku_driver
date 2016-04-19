package com.yichang.kaku.home.Ad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.request.CheckCodeReq;
import com.yichang.kaku.response.CheckCodeResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

public class SiJiZhaoMuActivity extends BaseActivity implements OnClickListener {
	
	private TextView left,right,title;
	private EditText et_sijizhaomu;
	private Button btn_sijizhaomu;
	private ImageView iv_sijizhaomu_bg;
	private LinearLayout line_sijizhaomu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sijizhaomu);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("邀请码");
		et_sijizhaomu= (EditText) findViewById(R.id.et_sijizhaomu);
		btn_sijizhaomu= (Button) findViewById(R.id.btn_sijizhaomu);
		btn_sijizhaomu.setOnClickListener(this);
		iv_sijizhaomu_bg= (ImageView) findViewById(R.id.iv_sijizhaomu_bg);
		iv_sijizhaomu_bg.setOnClickListener(this);
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
		} else if (R.id.btn_sijizhaomu == id){
			if (TextUtils.isEmpty(et_sijizhaomu.getText().toString().trim())){
				LogUtil.showShortToast(context,"请填写邀请码");
				return;
			}
			CheckCode();
		} else if (R.id.iv_sijizhaomu_bg == id){
			InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			et_sijizhaomu.setCursorVisible(false);// 失去光标
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
	}

	public void CheckCode(){
		CheckCodeReq req = new CheckCodeReq();
		req.code = "60013";
		req.id_driver = Utils.getIdDriver();
		req.code_recommended = et_sijizhaomu.getText().toString().trim();
		KaKuApiProvider.CheckCode(req, new KakuResponseListener<CheckCodeResp>(this,CheckCodeResp.class) {
			@Override
			public void onSucceed(int what, Response response) {
				super.onSucceed(what, response);
				if (t != null) {
					LogUtil.E("checkcode res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						btn_sijizhaomu.setEnabled(false);
						startActivity(new Intent(context,AdImageActivity.class));
						finish();
					} else {
						LogUtil.showShortToast(context, t.msg);
					}
				}
			}

		});
	}
}
