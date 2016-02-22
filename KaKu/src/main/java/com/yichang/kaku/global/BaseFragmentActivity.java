package com.yichang.kaku.global;

import com.yichang.kaku.view.TitleFragment;
import com.yichang.kaku.view.TitleFragment.OnTitleClickCallback;

import android.content.Context;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

public abstract class BaseFragmentActivity extends BaseActivity{
	
	protected Context context;
	/**Fragment管理器*/
	protected FragmentManager fragMgr;
	/**标题栏*/
	protected TitleFragment titleFragment;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		context = this;
		fragMgr = getSupportFragmentManager();
		initViews();
	}


	protected abstract void initViews();


	/**
	 * 切换Fragment
	 * @param containerId	容器ID
	 * @param fragment		要切换的Fragment
	 */
	protected void switchFragment(FragmentTransaction ft, int containerId, Fragment fragment, String tag) {
		ft.replace(containerId, fragment, tag);
		ft.commitAllowingStateLoss();
	}
	
	/**
	 * 切换Fragment
	 * @param containerId	容器ID
	 * @param fragment		要切换的Fragment
	 */
	protected void switchFragment(FragmentTransaction ft, int containerId, Fragment fragment) {
		ft.replace(containerId, fragment);
		ft.commit();
	}
	
	/**
	 * 设置标题栏文字
	 * @param titleFrgId	标题栏Fragment的Id
	 * @param title			设置的文字
	 */
	protected void setTitleName(int titleFrgId, String title){
		if(titleFragment == null)
			titleFragment = (TitleFragment) fragMgr.findFragmentById(titleFrgId);
		titleFragment.setTitleName(title);
	}
	
	/**
	 * 设置右上角图片
	 * @param titleFrgId	标题栏Fragment的Id
	 * @param title			设置的文字
	 */
	protected void setTitleImage(int titleFrgId, int image){
		if(titleFragment == null)
			titleFragment = (TitleFragment) fragMgr.findFragmentById(titleFrgId);
		titleFragment.setTitleImage(image);
	}
	
	/**
	 * 设置左上角图片
	 * @param titleFrgId	标题栏Fragment的Id
	 * @param title			设置的文字
	 */
	protected void setTitleLeftImage(int titleFrgId, int image){
		if(titleFragment == null)
			titleFragment = (TitleFragment) fragMgr.findFragmentById(titleFrgId);
		titleFragment.setTitleLeftImage(image);
	}
	
	/**
	 * 设置标题栏背景色
	 * @param titleFrgId	标题栏Fragment的Id
	 * @param color			背景色值
	 */
	protected void setTitleColor(int titleFrgId, int color){
		if(titleFragment == null)
			titleFragment = (TitleFragment) fragMgr.findFragmentById(titleFrgId);
		titleFragment.setTitleColor(color);
	}
	
	/**
	 * 设置标题栏背景色
	 * @param titleFrgId	标题栏Fragment的Id
	 * @param colorId			背景资源id
	 */
	protected void setTitleColorResource(int titleFrgId, int colorId){
		if(titleFragment == null)
			titleFragment = (TitleFragment) fragMgr.findFragmentById(titleFrgId);
		titleFragment.setTitleColorResource(colorId);
	}

	/**
	 * 设置标题栏按钮回调
	 * @param titleFrgId	标题栏Fragment的Id
	 * @param callback		回调
	 */
	protected void setTitleButtonCallback(int titleFrgId, OnTitleClickCallback callback) {
		if(titleFragment == null)
			titleFragment = (TitleFragment) fragMgr.findFragmentById(titleFrgId);
		titleFragment.setBtnClickCallback(callback);
	}
	
}
