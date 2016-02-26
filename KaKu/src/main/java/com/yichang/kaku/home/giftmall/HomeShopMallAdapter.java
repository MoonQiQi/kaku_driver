package com.yichang.kaku.home.giftmall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.GoodsObj;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

public class HomeShopMallAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<GoodsObj> list;
	private Context mContext;

	public HomeShopMallAdapter(Context context, List<GoodsObj> list) {
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
		GoodsObj obj = list.get(position);
		if (obj == null) {
			return convertView;
		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_home_shopmall, null);
			holder.iv_home_good = (ImageView) convertView.findViewById(R.id.iv_home_good);
			holder.tv_home_good = (TextView) convertView.findViewById(R.id.tv_home_good);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		BitmapUtil.getInstance(mContext).download(holder.iv_home_good,KaKuApplication.qian_zhui+obj.getImage_goods());
		holder.tv_home_good.setText("¥" +obj.getPrice_goods());

		return convertView;
	}

	class ViewHolder {

		private ImageView iv_home_good;
		private	TextView tv_home_good;


	}
}