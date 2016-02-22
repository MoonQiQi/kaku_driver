package com.wly.android.widget;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.wly.android.widget.AdGalleryHelper.OnGallerySwitchListener;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 无限滚动广告栏组件 持有自身容器布局引用，可以操作滚动指示器RadioGroup和标题TextView
 * 
 * @author zl
 * @date 2014-9-23
 */
public class AdGallery extends Gallery implements
		android.widget.AdapterView.OnItemClickListener,
		android.widget.AdapterView.OnItemSelectedListener, OnTouchListener {

	private Context mContext;
	private int mSwitchTime; // 图片切换时间
	private boolean IsShowText;
	private boolean IsClickble;

	private boolean runflag = false;
	private Timer mTimer; // 自动滚动的定时器

	private OnGallerySwitchListener mGallerySwitchListener;

	private List<Advertising> mAds;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int position = getSelectedItemPosition();
			if (position >= (getCount() - 1)) {
				setSelection(getCount() / 2, true); // 跳转到第二张图片，在向左滑动一张就到了第一张图片
				onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
			} else {
				onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
			}
		}
	};

	public AdGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;

		mTimer = new Timer();

	}

	public AdGallery(Context context) {
		super(context);
		this.mContext = context;

		mTimer = new Timer();

	}

	public AdGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;

		mTimer = new Timer();
	}

	class ViewHolder {
		ImageView imageview;
		TextView textview;
	}

	/**
	 * 初始化配置参数
	 * 
	 * @param ads
	 *            图片数据数组
	 * @param switchTime
	 *            图片切换间隔
	 * @param l_margin_p
	 *            图片到距离屏幕左边界距离占屏幕的"比例"
	 * @param t_margin_p
	 *            图片到距离屏幕上边界距离占屏幕的"比例"
	 * @param w_percent
	 *            图片占屏幕的宽度"比例"
	 * @param h_percent
	 *            图片占屏幕的高度"比例"
	 */
	public void init(List<Advertising> ads, int switchTime,boolean isShowText,boolean isClickble,
			OnGallerySwitchListener gallerySwitchListener) {
		this.mSwitchTime = switchTime;
		this.mGallerySwitchListener = gallerySwitchListener;
		this.IsShowText = isShowText;
		this.IsClickble = isClickble;
		this.mAds = ads;
		setAdapter(new AdAdapter());
		if (isClickble){
			this.setOnItemClickListener(this);
			this.setOnTouchListener(this);
			this.setOnItemSelectedListener(this);
			this.setSoundEffectsEnabled(false);
		}

		// setSpacing(10); //不能包含spacing，否则会导致onKeyDown()失效!!!
		setSelection(getCount() / 2); // 默认选中中间位置为起始位置
		setFocusableInTouchMode(true);
	}

	class AdAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mAds.size() * (Integer.MAX_VALUE / mAds.size());
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.adgallery_image, null);
				Gallery.LayoutParams params = new Gallery.LayoutParams(
						Gallery.LayoutParams.FILL_PARENT,
						Gallery.LayoutParams.WRAP_CONTENT);
				convertView.setLayoutParams(params);

				viewHolder = new ViewHolder();
				viewHolder.imageview = (ImageView) convertView
						.findViewById(R.id.gallery_image);
				viewHolder.textview = (TextView) convertView
						.findViewById(R.id.gallery_text);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			FinalBitmap.create(mContext).display(viewHolder.imageview,
					mAds.get(position % mAds.size()).getPicURL(),
					viewHolder.imageview.getWidth(),
					viewHolder.imageview.getHeight(), null, null);
			viewHolder.textview.setText(mAds.get(position % mAds.size()).getPicURL());
			if (IsShowText){
				viewHolder.textview.setVisibility(View.VISIBLE);
			} else {
				viewHolder.textview.setVisibility(View.GONE);
			}
			return convertView;
		}
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		int kEvent;
		if (isScrollingLeft(e1, e2)) { // 检查是否往左滑动
			kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
		} else { // 检查是否往右滑动
			kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
		}

		onKeyDown(kEvent, null);
		return true;

	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > (e1.getX() + 50);
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return super.onScroll(e1, e2, distanceX, distanceY);
	}

	/**
	 * 开始自动滚动
	 */
	public void startAutoScroll() {
		mTimer.schedule(new TimerTask() {

			public void run() {
				if (runflag) {
					Message msg = new Message();
					if (getSelectedItemPosition() < (getCount() - 1)) {
						msg.what = getSelectedItemPosition() + 1;
					} else {
						msg.what = 0;
					}
					handler.sendMessage(msg);
				}
			}
		}, mSwitchTime, mSwitchTime);

	}

	public void setRunFlag(boolean flag) {
		runflag = flag;
	}

	public boolean isAutoScrolling() {
		if (mTimer == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (MotionEvent.ACTION_UP == event.getAction()
				|| MotionEvent.ACTION_CANCEL == event.getAction()) {
			// 重置自动滚动任务
			setRunFlag(true);
		} else {
			// 停止自动滚动任务
			setRunFlag(false);
		}
		return false;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		
		mGallerySwitchListener.onGallerySwitch(position % (mAds.size()));

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		if ("".equals(mAds.get(position % mAds.size()).getId())) {
			return;
		}
		Intent i = new Intent();
		i.setClass(mContext, MyWebViewActivity.class);
		i.putExtra("url", mAds.get(position % mAds.size()).getId());
		mContext.startActivity(i);
	}
}
