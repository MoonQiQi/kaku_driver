package com.yichang.kaku.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.ShopCarObj;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

public class AddOilAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<ShopCarObj> list;
	private Context mContext;

	public AddOilAdapter(Context context, List<ShopCarObj> list) {
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
		ShopCarObj obj = list.get(position);
		if (obj == null) {
			return convertView;
		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_wuliao, null);
			holder.iv_wuliao_image = (ImageView) convertView.findViewById(R.id.iv_wuliao_image);
			holder.tv_wuliao_name = (TextView) convertView.findViewById(R.id.tv_wuliao_name);
			holder.tv_wuliao_price = (TextView) convertView.findViewById(R.id.tv_wuliao_price);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		BitmapUtil.getInstance(mContext).download(holder.iv_wuliao_image,KaKuApplication.qian_zhui+obj.getImage_item());
		holder.tv_wuliao_name.setText(obj.getName_item());
		holder.tv_wuliao_price.setText(obj.getPrice_item());

		return convertView;
	}

	class ViewHolder {

		private ImageView iv_wuliao_image;
		private	TextView tv_wuliao_name;
		private	TextView tv_wuliao_price;

	}
}