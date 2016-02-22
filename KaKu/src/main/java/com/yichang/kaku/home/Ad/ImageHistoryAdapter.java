package com.yichang.kaku.home.Ad;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.ImageHisObj;

import java.util.List;

public class ImageHistoryAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<ImageHisObj> list;
	private Context mContext;

	public ImageHistoryAdapter(Context context, List<ImageHisObj> list) {
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

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		final ImageHisObj obj = list.get(position);
		if (obj == null) {
			return convertView;
		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_tupianlishi, null);
			holder.iv_imagehis_image1 = (SimpleDraweeView) convertView.findViewById(R.id.iv_imagehis_image1);
			holder.iv_imagehis_image2 = (SimpleDraweeView) convertView.findViewById(R.id.iv_imagehis_image2);
			holder.iv_imagehis_image3 = (SimpleDraweeView) convertView.findViewById(R.id.iv_imagehis_image3);
			holder.tv_imagehis_time1 = (TextView) convertView.findViewById(R.id.tv_imagehis_time1);
			holder.tv_imagehis_time2 = (TextView) convertView.findViewById(R.id.tv_imagehis_time2);
			//holder.tv_imagehis_time3 = (TextView) convertView.findViewById(R.id.tv_imagehis_time3);
			holder.tv_imagehis_type1 = (TextView) convertView.findViewById(R.id.tv_imagehis_type1);
			holder.tv_imagehis_reason = (TextView) convertView.findViewById(R.id.tv_imagehis_reason);
			holder.btn_imagehis_commit = (Button) convertView.findViewById(R.id.btn_imagehis_commit);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String type = obj.getFlag_type();
		if ("I".equals(type)){
			holder.tv_imagehis_type1.setText("等待审核");
			holder.tv_imagehis_reason.setVisibility(View.GONE);
			holder.btn_imagehis_commit.setVisibility(View.GONE);
		} else if ("Y".equals(type)){
			holder.tv_imagehis_type1.setText("审核成功");
			holder.tv_imagehis_reason.setVisibility(View.GONE);
			holder.btn_imagehis_commit.setVisibility(View.GONE);
		} else if ("F".equals(type)){
			holder.tv_imagehis_type1.setText("审核失败");
			holder.tv_imagehis_reason.setVisibility(View.VISIBLE);
			holder.btn_imagehis_commit.setVisibility(View.VISIBLE);
		}
		holder.tv_imagehis_time1.setText(obj.getApprove_time());
		holder.tv_imagehis_time2.setText(obj.getCreate_time());
		//holder.tv_imagehis_time3.setText(obj.getUpload_time());
		holder.tv_imagehis_reason.setText(obj.getApprove_opinions()+"，请 ");
		holder.iv_imagehis_image1.setImageURI(Uri.parse(KaKuApplication.qian_zhuikong+obj.getImage0_advert()));
		holder.iv_imagehis_image2.setImageURI(Uri.parse(KaKuApplication.qian_zhuikong+obj.getImage1_advert()));
		holder.iv_imagehis_image3.setImageURI(Uri.parse(KaKuApplication.qian_zhuikong+obj.getImage2_advert()));
		holder.btn_imagehis_commit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				KaKuApplication.reason_upload = obj.getApprove_opinions();
				Intent intent = new Intent(mContext,ReImageActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("image0_advert",obj.getImage0_advert());
				bundle.putString("image1_advert",obj.getImage1_advert());
				bundle.putString("image2_advert",obj.getImage2_advert());
				bundle.putString("image0_approve",obj.getImage0_approve());
				bundle.putString("image1_approve",obj.getImage1_approve());
				bundle.putString("image2_approve",obj.getImage2_approve());
				intent.putExtras(bundle);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				mContext.startActivity(intent);
			}
		});

		return convertView;
	}

	class ViewHolder {

		 SimpleDraweeView iv_imagehis_image1;
		 SimpleDraweeView iv_imagehis_image2;
		 SimpleDraweeView iv_imagehis_image3;
		 TextView tv_imagehis_time1;
		 TextView tv_imagehis_time2;
		 //TextView tv_imagehis_time3;
		 TextView tv_imagehis_type1;
		 TextView tv_imagehis_reason;
		 Button btn_imagehis_commit;

	}
}