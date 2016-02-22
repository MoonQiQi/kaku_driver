package com.yichang.kaku.view;

import com.yichang.kaku.R;

import android.app.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class BottomSetpFragment extends Fragment implements OnClickListener {

	private OnStepClickCallback stepClickCallback;

	private Activity context;

	private Button btnPrev, btnNext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		if (context instanceof OnStepClickCallback)
			stepClickCallback = (OnStepClickCallback) context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.frgmt_bottom_step,
				container, false);
		btnPrev = (Button) contentView.findViewById(R.id.btn_prev);
		btnPrev.setOnClickListener(this);
		btnNext = (Button) contentView.findViewById(R.id.btn_next);
		btnNext.setOnClickListener(this);
		return contentView;
	}

	/**
	 * 上一步、下一步回调
	 * 
	 * @author Administrator
	 * 
	 */
	public static interface OnStepClickCallback {
		public void onPrevClick();

		public void onNextClick();
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (R.id.btn_prev == id) {
			if (stepClickCallback != null)
				stepClickCallback.onPrevClick();
		} else if (R.id.btn_next == id) {
			if (stepClickCallback != null)
				stepClickCallback.onNextClick();
		}
	}

	public void setPrevText(String prevText) {
		btnPrev.setText(prevText);
	}

	public void setNextText(String nextText) {
		btnNext.setText(nextText);
	}
}
