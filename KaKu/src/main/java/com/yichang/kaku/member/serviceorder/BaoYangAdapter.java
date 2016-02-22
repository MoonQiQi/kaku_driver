package com.yichang.kaku.member.serviceorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.WuLiaoObj;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

public class BaoYangAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<WuLiaoObj> list;
	private Context mContext;
	private int num ;

	public BaoYangAdapter(Context context, List<WuLiaoObj> list) {
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
		WuLiaoObj obj = list.get(position);
		if (obj == null) {
			return convertView;
		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_shopcara, null);
			holder.iv_baoyang_image = (ImageView) convertView.findViewById(R.id.iv_baoyang_image);
			holder.tv_baoyang_name = (TextView) convertView.findViewById(R.id.tv_baoyang_name);
			holder.tv_baoyang_price = (TextView) convertView.findViewById(R.id.tv_baoyang_price);
			holder.tv_baoyang_num = (TextView) convertView.findViewById(R.id.tv_baoyang_num);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		BitmapUtil.getInstance(mContext).download(holder.iv_baoyang_image,KaKuApplication.qian_zhui+obj.getImage_item());
		holder.tv_baoyang_name.setText(obj.getName_item());
		holder.tv_baoyang_price.setText("¥"+obj.getPrice_item());
		holder.tv_baoyang_num.setText("X"+obj.getNum_item());

		return convertView;
	}

	class ViewHolder {

		ImageView iv_baoyang_image;
		TextView tv_baoyang_name;
		TextView tv_baoyang_price;
		TextView tv_baoyang_num;

	}

}