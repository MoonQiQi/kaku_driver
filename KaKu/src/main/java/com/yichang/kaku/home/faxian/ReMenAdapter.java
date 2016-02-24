package com.yichang.kaku.home.faxian;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.PinPaiXuanZeObj;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

public class ReMenAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<PinPaiXuanZeObj> list;
	private Context mContext;

	public ReMenAdapter(Context context, List<PinPaiXuanZeObj> list) {
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
		PinPaiXuanZeObj obj = list.get(position);
		if (obj == null) {
			return convertView;
		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_remen, null);
			holder.tv_item_remen = (TextView) convertView.findViewById(R.id.tv_item_remen);
			holder.iv_item_remen = (ImageView) convertView.findViewById(R.id.iv_item_remen);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_item_remen.setText(obj.getName_brand());
		BitmapUtil.getInstance(mContext).download(holder.iv_item_remen, KaKuApplication.qian_zhui+obj.getImage_brand());

		return convertView;
	}

	class ViewHolder {
		TextView tv_item_remen;
		ImageView iv_item_remen;
	}
}