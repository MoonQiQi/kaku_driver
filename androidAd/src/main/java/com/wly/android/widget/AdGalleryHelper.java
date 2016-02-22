package com.wly.android.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import java.util.List;

/**
 * 对自定义组件AdGallery进行了一次封装
 * 包含对图片标题和当前位置指示器(RadioGroup)的操作
 * @author zl
 * @date 2014-9-23
 *
 */
public class AdGalleryHelper {

	private AdGallery mAdGallery; //无限滚动Gallery
	private TextView mPicTitle; //广告图片标题
	private RadioGroup mRadioGroup; //滚动标记组件
	
	private Context mContext;
	private LayoutInflater mInflater;
	
	LinearLayout galleryLayout;
	

	public AdGalleryHelper(Context context,final List<Advertising> ads,int switchTime,boolean isVisible,boolean isShowText,boolean isClickble) {
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		galleryLayout = (LinearLayout)mInflater.inflate(R.layout.adgallery_hellper, null);
		mRadioGroup = (RadioGroup)galleryLayout.findViewById(R.id.home_pop_gallery_mark);
		
		//添加RadioButton
		Bitmap b = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_1);
		LayoutParams params = new LayoutParams(
				Util.dpToPx(mContext, b.getWidth()), 
				Util.dpToPx(mContext, b.getHeight()));
		for (int i = 0; i < ads.size(); i++) {
			RadioButton _rb = new RadioButton(mContext);
			_rb.setId(0x1234 + i);
			_rb.setButtonDrawable(mContext.getResources().getDrawable(
					R.drawable.gallery_selector));
			mRadioGroup.addView(_rb, params);
		}
		
		
		mPicTitle = (TextView)galleryLayout.findViewById(R.id.news_gallery_text);
		mAdGallery = (AdGallery)galleryLayout.findViewById(R.id.gallerypop);
		mAdGallery.init(ads, switchTime,isShowText,isClickble,new OnGallerySwitchListener() {
			
			@Override
			public void onGallerySwitch(int position) {
				if (mRadioGroup != null) {
					mRadioGroup.check(mRadioGroup.getChildAt(
							position).getId());
				}
				/*if(mPicTitle != null) {
					mPicTitle.setText(ads.get(position).getPicURL());
				}*/
			}
		});
		if (isVisible){
			mRadioGroup.setVisibility(View.VISIBLE);
		} else {
			mRadioGroup.setVisibility(View.GONE);
		}
	}

	/**
	 * 向外开放布局对象，使得可以将布局对象添加到外部的布局中去
	 * @return
	 */
	public LinearLayout getLayout() {
		galleryLayout.measure(0,0);
		return galleryLayout;
	}
	
	/**
	 * 开始自动循环切换
	 */
	public void startAutoSwitch() {
		mAdGallery.setRunFlag(true);
		mAdGallery.startAutoScroll();
	}

	public void stopAutoSwitch() {
		mAdGallery.setRunFlag(true);
	}
	
	/**
	 * 图片切换回调接口
	 * @author wly
	 *
	 */
	interface OnGallerySwitchListener {
		public void onGallerySwitch(int position);
	}
}
