package com.yichang.kaku.home.mycar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.PinPaiXuanZeObj;

import java.util.List;

public class PinPaiZiAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<PinPaiXuanZeObj> list;
	private Context mContext;

	public PinPaiZiAdapter(Context context, List<PinPaiXuanZeObj> list) {
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
			convertView = mInflater.inflate(R.layout.item_pinpaizi, null);

			holder.tv_item_pinpaizi = (TextView) convertView.findViewById(R.id.tv_item_pinpaizi);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_item_pinpaizi.setText(obj.getName_brand());

		return convertView;
	}

	class ViewHolder {

		TextView tv_item_pinpaizi;

	}
}