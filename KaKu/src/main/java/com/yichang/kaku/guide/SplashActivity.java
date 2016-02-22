package com.yichang.kaku.guide;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.tools.Utils;

import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;

public class SplashActivity extends BaseActivity {
	private ImageView ivHome;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		MobclickAgent.openActivityDurationTrack(false);
		MobclickAgent.updateOnlineConfig(context);
		if (Utils.isFirst()) {
			startActivity(new Intent(context, GuideActivity.class));
			finish();
		} else {
			init();
		}
	}
	
	private void init() {
		ivHome = (ImageView) findViewById(R.id.iv_home);
		ivHome.setImageResource(R.drawable.home);
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
					goToHome();
			}
		};
		timer.schedule(task, Constants.AUTO_SCROLL_DURATION);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(this);
		MobclickAgent.onPageStart("Splash"); //统计页面
		MobclickAgent.onResume(this);  //统计时长
		if (Utils.isShowDialog() == true) {
			Editor editor = KaKuApplication.editor;
			editor.putBoolean(Constants.ISSHOWDIALOG, false);
			editor.commit();
		}
	}
	
	private void goToHome() {
		Intent intent = new Intent(context, MainActivity.class);
		startActivity(intent);
		SplashActivity.this.finish();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(this);
		MobclickAgent.onPageEnd("Splash"); // 保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息 
	    MobclickAgent.onPause(this);
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (ivHome != null) {
			ivHome.setVisibility(View.GONE);
			ivHome.setImageResource(0);
		}
		
	}
}
