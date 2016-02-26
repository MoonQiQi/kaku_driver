package com.yichang.kaku.home.nouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.DropObj;

import java.util.List;

public class ShaiXuanOilAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<DropObj> list;
	private Context mContext;

	public ShaiXuanOilAdapter(Context context, List<DropObj> list) {
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
	public View getView( int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		DropObj obj = list.get(position);
		if (obj == null) {
			return convertView;
		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_shaixuan, null);
			holder.tv_shaixuan_name = (TextView) convertView.findViewById(R.id.tv_shaixuan_name);
			holder.iv_shaixuan_duihao = (ImageView) convertView.findViewById(R.id.iv_shaixuan_duihao);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_shaixuan_name.setText(obj.getName_drop());

		return convertView;
	}

	class ViewHolder {

		private	 TextView tv_shaixuan_name;
		private ImageView iv_shaixuan_duihao;

	}
}