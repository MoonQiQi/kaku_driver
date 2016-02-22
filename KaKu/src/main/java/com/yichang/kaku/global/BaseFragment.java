package com.yichang.kaku.global;

import android.app.Activity;
import android.support.v4.app.Fragment;


public  class BaseFragment extends Fragment {
	public Activity activity;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}
	

	public void showProgressDialog() {
		try {
			BaseActivity activity = (BaseActivity) getActivity();
			activity.showProgressDialog();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stopProgressDialog() {
		try {
			BaseActivity activity = (BaseActivity) getActivity();
			activity.stopProgressDialog();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
