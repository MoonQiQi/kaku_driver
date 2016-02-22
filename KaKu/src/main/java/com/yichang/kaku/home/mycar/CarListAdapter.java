package com.yichang.kaku.home.mycar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.CarListObj;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

public class CarListAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<CarListObj> list;
	private Context mContext;

	public CarListAdapter(Context context, List<CarListObj> list) {
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
		CarListObj obj = list.get(position);
		if (obj == null) {
			return convertView;
		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_carlist, null);
			holder.iv_carlistitem_image = (ImageView) convertView.findViewById(R.id.iv_carlistitem_image);
			holder.iv_carlistitem_icon = (ImageView) convertView.findViewById(R.id.iv_carlistitem_icon);
			holder.tv_carlistitem_pinpai = (TextView) convertView.findViewById(R.id.tv_carlistitem_pinpai);
			holder.tv_carlistitem_qudong = (TextView) convertView.findViewById(R.id.tv_carlistitem_qudong);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		BitmapUtil.getInstance(mContext).download(holder.iv_carlistitem_image,KaKuApplication.qian_zhui+obj.getImage_brand());
		BitmapUtil.getInstance(mContext).download(holder.iv_carlistitem_icon,KaKuApplication.qian_zhui+obj.getImage_model());
		holder.tv_carlistitem_pinpai.setText(obj.getName_brand());
		holder.tv_carlistitem_qudong.setText("驱动形式："+obj.getName_actuate()+"   "+obj.getName_power());

		return convertView;
	}

	class ViewHolder {

		private ImageView iv_carlistitem_image;
		private	ImageView iv_carlistitem_icon;
		private	TextView tv_carlistitem_pinpai;
		private	TextView tv_carlistitem_qudong;

	}
}