package com.yichang.kaku.home.nouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.OilBrandObj;

import java.util.List;

public class OilBrandAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<OilBrandObj> list;
	private Context mContext;

	public OilBrandAdapter(Context context, List<OilBrandObj> list) {
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
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		OilBrandObj obj = list.get(position);
		if (obj == null) {
			return convertView;
		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_oilbrand, null);
			holder.iv_itemoil_duihao = (ImageView) convertView.findViewById(R.id.iv_itemoil_duihao);
			holder.tv_itemoil_name = (TextView) convertView.findViewById(R.id.tv_itemoil_name);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_itemoil_name.setText(obj.getName_brand_oil());
		if ("Y".equals(obj.getFlag_check())){
			holder.iv_itemoil_duihao.setVisibility(View.VISIBLE);
		} else {
			holder.iv_itemoil_duihao.setVisibility(View.GONE);
		}
		notifyDataSetChanged();

		return convertView;
	}

	class ViewHolder {

		private ImageView iv_itemoil_duihao;
		private	TextView tv_itemoil_name;

	}
}