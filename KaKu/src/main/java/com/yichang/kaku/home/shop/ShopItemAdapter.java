package com.yichang.kaku.home.shop;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.Shops_wxzObj;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

public class ShopItemAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<Shops_wxzObj> list;
	private Context mContext;

	public ShopItemAdapter(Context context, List<Shops_wxzObj> list) {
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
		
		final ViewHolder holder;
		Shops_wxzObj obj = list.get(position);
		if (obj == null) {
			return convertView;
		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_shop, null);
			holder.iv_item_shoppic = (ImageView) convertView.findViewById(R.id.iv_item_shoppic);
			holder.iv_item_shoppic2 = (ImageView) convertView.findViewById(R.id.iv_item_shoppic2);
			holder.tv_item_shopname = (TextView) convertView.findViewById(R.id.tv_item_shopname);
			holder.tv_item_shopaddr = (TextView) convertView.findViewById(R.id.tv_item_shopaddr);
			holder.tv_item_shopbrand = (TextView) convertView.findViewById(R.id.tv_item_shopbrand);
			holder.tv_item_shopdistance = (TextView) convertView.findViewById(R.id.tv_item_shopdistance);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//维修店照片
		if (!"".equals(obj.getImage_shop())) {
			BitmapUtil.getInstance(mContext).download(holder.iv_item_shoppic, KaKuApplication.qian_zhui+obj.getImage_shop());
		}
		if (!"".equals(obj.getImage_shop_up())) {
			BitmapUtil.getInstance(mContext).download(holder.iv_item_shoppic2, KaKuApplication.qian_zhui+obj.getImage_shop_up());
		}
		holder.tv_item_shopname.setText(obj.getName_shop());
		holder.tv_item_shopaddr.setText(obj.getAddr_shop());
		holder.tv_item_shopbrand.setText(obj.getName_brands());
		String string = obj.getDistance()+"km";
		SpannableStringBuilder style = new SpannableStringBuilder(string);
	    style.setSpan(new AbsoluteSizeSpan(20,true),0,string.length()-2,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//起价
		holder.tv_item_shopdistance.setText(style);

		return convertView;
	}

	class ViewHolder {

		ImageView iv_item_shoppic;
		ImageView iv_item_shoppic2;
		TextView tv_item_shopname;
		TextView tv_item_shopaddr;
		TextView tv_item_shopbrand;
		TextView tv_item_shopdistance;

	}
}