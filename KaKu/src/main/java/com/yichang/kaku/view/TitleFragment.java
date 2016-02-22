package com.yichang.kaku.view;

import java.util.List;

import org.apache.http.Header;

import com.yichang.kaku.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class TitleFragment extends Fragment implements OnClickListener{

	private OnTitleClickCallback btnClickCallback;

	/**标题整体视图*/
	private View contentView;
	/**标题文字文本框*/
	private TextView tvTitleName;
	/**标题左侧按钮*/
	private LinearLayout llLeft,llRight;
	/**上下文*/
	private Activity context;
	private ImageView iv_right,iv_left;
	private TextView tv_title_scan;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		if(context instanceof OnTitleClickCallback)
			btnClickCallback = (OnTitleClickCallback) context;
	}
	
	public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.frgmt_title, container, false);
		tvTitleName = (TextView) contentView.findViewById(R.id.tv_title_name);
		llLeft = (LinearLayout) contentView.findViewById(R.id.ll_title_settings);
		llRight = (LinearLayout) contentView.findViewById(R.id.ll_title_scan);
		iv_right=(ImageView) contentView.findViewById(R.id.iv_right);
		iv_left=(ImageView) contentView.findViewById(R.id.iv_left);
		//tv_title_scan=(TextView) contentView.findViewById(R.id.tv_title_scan);
		llLeft.setOnClickListener(this);
		llRight.setOnClickListener(this);
		return contentView;
	}
	
	public void setTitleName(String title){
		tvTitleName.setText(title);
	}
	
	public void setTitleImage(int image){
		iv_right.setImageResource(image);
	}
	
	public void setTitleLeftImage(int image){
		iv_left.setImageResource(image);
	}
	
	public void setTitleColor(int color){
		contentView.setBackgroundColor(color);
	}
	
	public void setTitleColorResource(int colorId){
		contentView.setBackgroundResource(colorId);
	}
	
	
	
	/**
	 * 设置左侧按钮的隐藏和显示
	 * @param isShow	true表示显示,false表示隐藏
	 */
	public void showLeftButton(boolean isShow){
		if(isShow)
			llLeft.setVisibility(View.VISIBLE);
		else
			llLeft.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(R.id.ll_title_settings == id){
			if(btnClickCallback!=null)
				btnClickCallback.onLeftClick();
		} else if(R.id.ll_title_scan == id){
			if(btnClickCallback!=null)
				btnClickCallback.onRightClick();
		}
	}
	
	
	public OnTitleClickCallback getBtnClickCallback() {
		return btnClickCallback;
	}

	public void setBtnClickCallback(OnTitleClickCallback btnClickCallback) {
		this.btnClickCallback = btnClickCallback;
	}
	
	public static interface OnTitleClickCallback{
		public void onLeftClick();
		public void onRightClick();
	}
}
