package com.yichang.kaku.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.SeriessObj;

import java.util.List;

public class ChooseCarAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<SeriessObj> list;
	private Context mContext;

	public ChooseCarAdapter(Context context, List<SeriessObj> list) {
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
		SeriessObj obj = list.get(position);
		if (obj == null) {
			return convertView;
		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_choosecar, null);
			holder.tv_item_choosecar = (TextView) convertView.findViewById(R.id.tv_item_choosecar);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_item_choosecar.setText(obj.getName_data());
		if ("Y".equals(obj.getCan_check())){
			holder.tv_item_choosecar.setBackgroundResource(R.drawable.btn_white);
			holder.tv_item_choosecar.setTag("Y");
		} else {
			holder.tv_item_choosecar.setBackgroundResource(R.drawable.btn_hui);
			holder.tv_item_choosecar.setTag("N");
		}

		return convertView;
	}

	class ViewHolder {
		private	TextView tv_item_choosecar;
	}
}