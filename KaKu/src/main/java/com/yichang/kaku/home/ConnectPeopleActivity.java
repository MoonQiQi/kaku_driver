package com.yichang.kaku.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;

public class ConnectPeopleActivity extends BaseActivity implements OnClickListener{
	
	private TextView left,right,title;
	private RelativeLayout rela_connect_name,rela_connect_phone;
	private String string1,string2,string3;
	private EditText tv_connect_name,tv_connect_phone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connectpeople);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("联系方式");
		right= (TextView) findViewById(R.id.tv_right);
		right.setVisibility(View.VISIBLE);
		right.setOnClickListener(this);
		right.setText("保存");
		tv_connect_name= (EditText) findViewById(R.id.tv_connect_name);
		tv_connect_name.setText(KaKuApplication.name_connect);
		tv_connect_phone= (EditText) findViewById(R.id.tv_connect_phone);
		tv_connect_phone.setText(KaKuApplication.phone_connect);
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
			if (TextUtils.isEmpty(tv_connect_name.getText().toString().trim())){
				LogUtil.showShortToast(context,"联系人不能为空");
				return;
			}
			if (TextUtils.isEmpty(tv_connect_phone.getText().toString().trim())){
				LogUtil.showShortToast(context,"联系电话不能为空");
				return;
			}
			Intent intent = new Intent();
			intent.putExtra("name",tv_connect_name.getText().toString().trim());
			intent.putExtra("phone",tv_connect_phone.getText().toString().trim());
			setResult(RESULT_OK, intent);
			finish();
		}
	}

}
