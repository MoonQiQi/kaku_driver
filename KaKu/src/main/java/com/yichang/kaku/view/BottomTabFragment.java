package com.yichang.kaku.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.member.login.LoginActivity;
import com.yichang.kaku.tools.Utils;

public class BottomTabFragment extends Fragment implements OnClickListener {

	/** tab点击事件回调 */
	private OnTabClickCallback onTabClickCallback;

	// private UpdateUserCallback updateUserCallback;

	/** 上下文 */
	private Activity context;

	//todo 恢复发现页面

	private ImageView ivTabHome, ivTabZone, ivTabShoppingCart, ivTabKnow, ivTabMemb;
	private TextView tvTabHome, tvTabZone, tvTabShoppingCart, tvTabKnow, tvTabMemb;
	/*private ImageView ivTabHome, ivTabZone, ivTabShoppingCart, ivTabMemb;
	private TextView tvTabHome, tvTabZone, tvTabShoppingCart, tvTabMemb;*/

	/** 选中文字颜色 */
	private int colorTextSelect;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		if (context instanceof OnTabClickCallback)
			onTabClickCallback = (OnTabClickCallback) getActivity();
		colorTextSelect = context.getResources().getColor(R.color.bottom_text_selected);
	}

	public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container,
			android.os.Bundle savedInstanceState) {

		View contentView = inflater.inflate(R.layout.frgmt_bottom_tab, container, false);
		contentView.findViewById(R.id.btn_tab_home).setOnClickListener(this);
		contentView.findViewById(R.id.btn_tab_zone).setOnClickListener(this);
		//todo chaih 20150828
		contentView.findViewById(R.id.btn_tab_know).setOnClickListener(this);
		contentView.findViewById(R.id.btn_tab_memb).setOnClickListener(this);
		tvTabHome = (TextView) contentView.findViewById(R.id.tv_tab_home);
		tvTabZone = (TextView) contentView.findViewById(R.id.tv_tab_zone);
		//todo chaih 20150828
		tvTabKnow = (TextView) contentView.findViewById(R.id.tv_tab_know);
		tvTabMemb = (TextView) contentView.findViewById(R.id.tv_tab_memb);
		ivTabHome = (ImageView) contentView.findViewById(R.id.iv_tab_home);
		ivTabZone = (ImageView) contentView.findViewById(R.id.iv_tab_zone);
		//todo chaih 20150828
		ivTabKnow = (ImageView) contentView.findViewById(R.id.iv_tab_know);
		ivTabMemb = (ImageView) contentView.findViewById(R.id.iv_tab_memb);
		// 当前Tab默认为home
		if (Constants.current_tab == Constants.TAB_POSITION_UNKONWN) {
			Constants.current_tab = Constants.TAB_POSITION_HOME;
		}
		return contentView;
	}

	@Override
	public void onClick(View v) {
		if (!Utils.isLogin()){
			context.startActivity(new Intent(context, LoginActivity.class));
			return;
		}

		int id = v.getId();
		clearTabState();
		if (R.id.btn_tab_home == id) {
			if (onTabClickCallback != null)
				onTabClickCallback.onHomeTabClick();
			ivTabHome.setImageResource(R.drawable.tabbar_home2);
			tvTabHome.setTextColor(colorTextSelect);
			Constants.current_tab = Constants.TAB_POSITION_HOME;
		} else if (R.id.btn_tab_zone == id) {
			if (onTabClickCallback != null)
				onTabClickCallback.onZoneTabClick();
			ivTabZone.setImageResource(R.drawable.tabbar_zone2);
			tvTabZone.setTextColor(colorTextSelect);
			Constants.current_tab = Constants.TAB_POSITION_ZONE;
		}
		//todo chaih 20150828
		else if (R.id.btn_tab_know == id) {
			if (onTabClickCallback != null)
				onTabClickCallback.onFindTabClick();
			ivTabKnow.setImageResource(R.drawable.tabbar_know2);
			tvTabKnow.setTextColor(colorTextSelect);
			Constants.current_tab = Constants.TAB_POSITION_FIND;
		} else if (R.id.btn_tab_memb == id) {
			if (onTabClickCallback != null)
				onTabClickCallback.onMemberTabClick();
			ivTabMemb.setImageResource(R.drawable.tabbar_memb2);
			tvTabMemb.setTextColor(colorTextSelect);
			Constants.current_tab = Constants.TAB_POSITION_MEMBER;
		}
	}

	private void clearTabState() {
		// 先把当前的Tab变为未选中状态
		ivTabHome.setImageResource(R.drawable.tabbar_home1);
		tvTabHome.setTextColor(context.getResources().getColor(R.color.top_widget_color));
		ivTabZone.setImageResource(R.drawable.tabbar_zone1);
		tvTabZone.setTextColor(context.getResources().getColor(R.color.top_widget_color));
		//todo chaih 20150828
		ivTabKnow.setImageResource(R.drawable.tabbar_know1);
		tvTabKnow.setTextColor(context.getResources().getColor(R.color.top_widget_color));
		ivTabMemb.setImageResource(R.drawable.tabbar_memb1);
		tvTabMemb.setTextColor(context.getResources().getColor(R.color.top_widget_color));
	}

	/**
	 * 设置HomeTab的文字
	 * 
	 * @param homeTabText
	 *            要设置的文字
	 */
	public void setHomeTabText(String homeTabText) {
		if (homeTabText != null)
			tvTabHome.setText(homeTabText);
	}

	public void setTab(int index) {
		clearTabState();
		switch (index) {
		case Constants.TAB_POSITION_HOME:
			ivTabHome.setImageResource(R.drawable.tabbar_home2);
			tvTabHome.setTextColor(colorTextSelect);
			break;
		case Constants.TAB_POSITION_ZONE:
			ivTabZone.setImageResource(R.drawable.tabbar_zone2);
			tvTabZone.setTextColor(colorTextSelect);
			break;
		//todo chaih 20150828
		case Constants.TAB_POSITION_FIND:
			ivTabKnow.setImageResource(R.drawable.tabbar_know2);
			tvTabKnow.setTextColor(colorTextSelect);
			break;
		case Constants.TAB_POSITION_MEMBER:
			ivTabMemb.setImageResource(R.drawable.tabbar_memb2);
			tvTabMemb.setTextColor(colorTextSelect);
			break;
		}
	}

	public OnTabClickCallback getOnTabClickCallback() {
		return onTabClickCallback;
	}

	public void setOnTabClickCallback(OnTabClickCallback onTabClickCallback) {
		this.onTabClickCallback = onTabClickCallback;
	}

	public static interface OnTabClickCallback {
		/**
		 * 需要返回要设置的HomeTab的文字
		 * 
		 * @return
		 */
		public void onHomeTabClick();

		public void onZoneTabClick();

		public void onFindTabClick();

		public void onMemberTabClick();
	}
}
