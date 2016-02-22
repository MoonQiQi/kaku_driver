package com.yichang.kaku.home.giftmall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.member.ShowImageActivity;
import com.yichang.kaku.obj.PingJiaObj;
import com.yichang.kaku.obj.ShopCartProductObj;
import com.yichang.kaku.obj.ShopMallProductCommentObj;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class TruckProductCommentAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<ShopMallProductCommentObj> list;
	private Context mContext;
	//存放bitmap的列表
	List<Bitmap> mBimpList = new ArrayList<>();


	public TruckProductCommentAdapter(Context context, List<ShopMallProductCommentObj> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.mContext = context;
		if (mInflater == null) {
			mInflater = LayoutInflater.from(context);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list != null && !list.isEmpty() ? list.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public  int dip2px( float dipValue) {
		final float scale = mContext.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		final ShopMallProductCommentObj obj = list.get(position);
		if (obj == null) {
			return convertView;
		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_pingjia, null);
			holder.iv_pingjia1 = (ImageView) convertView.findViewById(R.id.iv_pingjia1);
			holder.iv_pingjia2 = (ImageView) convertView.findViewById(R.id.iv_pingjia2);
			holder.iv_pingjia3 = (ImageView) convertView.findViewById(R.id.iv_pingjia3);
			holder.iv_pingjia4 = (ImageView) convertView.findViewById(R.id.iv_pingjia4);
			holder.tv_pingjia_time = (TextView) convertView.findViewById(R.id.tv_pingjia_time);
			holder.tv_pingjia_content = (TextView) convertView.findViewById(R.id.tv_pingjia_content);
			holder.star_pingjia_star = (RatingBar) convertView.findViewById(R.id.star_pingjia_star);
			holder.rela_pingjia_images = (RelativeLayout) convertView.findViewById(R.id.rela_pingjia_images);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		/*if ("".equals(obj.getImage1_eval())) {
			holder.iv_pingjia1.setVisibility(View.GONE);
			holder.iv_pingjia2.setVisibility(View.GONE);
			holder.iv_pingjia3.setVisibility(View.GONE);
			holder.iv_pingjia4.setVisibility(View.GONE);
		} else {
			if ("".equals(obj.getImage2_eval())) {
				holder.iv_pingjia1.setVisibility(View.VISIBLE);
				holder.iv_pingjia2.setVisibility(View.GONE);
				holder.iv_pingjia3.setVisibility(View.GONE);
				holder.iv_pingjia4.setVisibility(View.GONE);
			} else {
				if ("".equals(obj.getImage3_eval())) {
					holder.iv_pingjia1.setVisibility(View.VISIBLE);
					holder.iv_pingjia2.setVisibility(View.VISIBLE);
					holder.iv_pingjia3.setVisibility(View.GONE);
					holder.iv_pingjia4.setVisibility(View.GONE);
				} else {
					if ("".equals(obj.getImage4_eval())) {
						holder.iv_pingjia1.setVisibility(View.VISIBLE);
						holder.iv_pingjia2.setVisibility(View.VISIBLE);
						holder.iv_pingjia3.setVisibility(View.VISIBLE);
						holder.iv_pingjia4.setVisibility(View.GONE);
					} else {

						holder.iv_pingjia1.setVisibility(View.VISIBLE);
						holder.iv_pingjia2.setVisibility(View.VISIBLE);
						holder.iv_pingjia3.setVisibility(View.VISIBLE);
						holder.iv_pingjia4.setVisibility(View.VISIBLE);

					}
				}
			}
		}*/
		if (!"".equals(obj.getImage_eval())) {
			holder.iv_pingjia1.setVisibility(View.VISIBLE);
			BitmapUtil.getInstance(mContext).download(holder.iv_pingjia1, KaKuApplication.qian_zhui + obj.getImage_eval());
		}else {
			holder.iv_pingjia1.setVisibility(View.GONE);
		}

		/*if (!"".equals(obj.getImage2_eval())) {
			BitmapUtil.getInstance(mContext).download(holder.iv_pingjia2, KaKuApplication.qian_zhui + obj.getImage2_eval());
		}
		if (!"".equals(obj.getImage3_eval())) {
			BitmapUtil.getInstance(mContext).download(holder.iv_pingjia3, KaKuApplication.qian_zhui + obj.getImage3_eval());
		}
		if (!"".equals(obj.getImage4_eval())) {
			BitmapUtil.getInstance(mContext).download(holder.iv_pingjia4, KaKuApplication.qian_zhui + obj.getImage4_eval());

		}*/

		holder.iv_pingjia1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getImageList(holder);
				Intent intent = new Intent(mContext, ShowImageActivity.class);
				intent.putExtra("position", 0);
				mContext.startActivity(intent);
			}
		});
		/*holder.iv_pingjia2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getImageList(holder);
				Intent intent = new Intent(mContext, ShowImageActivity.class);
				intent.putExtra("position", 1);
				mContext.startActivity(intent);
			}
		});
		holder.iv_pingjia3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getImageList(holder);
				Intent intent = new Intent(mContext, ShowImageActivity.class);
				intent.putExtra("position", 2);
				mContext.startActivity(intent);
			}
		});
		holder.iv_pingjia4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getImageList(holder);
				Intent intent = new Intent(mContext, ShowImageActivity.class);
				intent.putExtra("position", 3);
				mContext.startActivity(intent);
			}
		});*/

		holder.tv_pingjia_time.setText(obj.getName_driver()+"  "+obj.getTime_eval());
		holder.tv_pingjia_content.setText(obj.getContent_eval());
		String star = obj.getStar_eval();
		float starFloat = Float.valueOf(star);
		holder.star_pingjia_star.setRating(starFloat);

		return convertView;
	}

	private void getImageList(ViewHolder holder) {
		for (int i = 0; i < holder.rela_pingjia_images.getChildCount(); i++) {
			View view = holder.rela_pingjia_images.getChildAt(i);
			if (view.getVisibility() == View.VISIBLE) {
				KaKuApplication.mBimpList.add(convertViewToBitmap(view,i));
			}
		}
	}

	private void setImageViewVisibility(List<ImageView> ivList, int num) {
		for (int i = 0; i < num; i++) {
			ivList.get(i).setVisibility(View.VISIBLE);
		}
	}

	public  Bitmap convertViewToBitmap(View view,int sortNum) {
		view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dip2px(60), dip2px(60));
		params.leftMargin=sortNum*(dip2px(10)+dip2px(60))+dip2px(15);
		params.topMargin=dip2px(10);
		view.setLayoutParams(params);
		return bitmap;
	}

	class ViewHolder {

		ImageView iv_pingjia1;
		ImageView iv_pingjia2;
		ImageView iv_pingjia3;
		ImageView iv_pingjia4;
		TextView tv_pingjia_time;
		TextView tv_pingjia_content;
		RatingBar star_pingjia_star;
		RelativeLayout rela_pingjia_images;

	}

}