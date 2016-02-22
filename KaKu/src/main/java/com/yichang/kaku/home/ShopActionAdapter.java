package com.yichang.kaku.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.CouponObj;

import java.util.List;

public class ShopActionAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<CouponObj> list;
	private Context mContext;

	public ShopActionAdapter(Context context, List<CouponObj> list) {
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
		CouponObj obj = list.get(position);
		if (obj == null) {
			return convertView;
		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_shopaction, null);
			holder.iv_shopactionitem = (ImageView) convertView.findViewById(R.id.iv_shopactionitem);
			holder.tv_shopactionitem = (TextView) convertView.findViewById(R.id.tv_shopactionitem);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_shopactionitem.setText(obj.getContent_coupon());
		String type = obj.getType_coupon();
		if ("0".equals(type)){
			holder.iv_shopactionitem.setImageResource(R.drawable.shop_shou);
		} else if ("1".equals(type)){
			holder.iv_shopactionitem.setImageResource(R.drawable.shop_jian);
		} else if ("2".equals(type)){
			holder.iv_shopactionitem.setImageResource(R.drawable.shop_di);
		} else if ("3".equals(type)){
			holder.iv_shopactionitem.setImageResource(R.drawable.shop_zeng);
		} else if ("4".equals(type)){
			holder.iv_shopactionitem.setImageResource(R.drawable.shop_zhe);
		}

		return convertView;
	}

	class ViewHolder {

		 ImageView iv_shopactionitem;
		 TextView tv_shopactionitem;

	}
}