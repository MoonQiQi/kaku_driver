package com.yichang.kaku.home.mycar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.CarData2Obj;

import java.util.List;

public class Step2Adapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<CarData2Obj> list;
	private Context mContext;

	public Step2Adapter(Context context, List<CarData2Obj> list) {
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
		CarData2Obj obj = list.get(position);
		if (obj == null) {
			return convertView;
		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_step2, null);

			holder.tv_item_xuanche1 = (TextView) convertView.findViewById(R.id.tv_item_xuanche1);
			holder.tv_item_xuanche2 = (TextView) convertView.findViewById(R.id.tv_item_xuanche2);
			holder.iv_item_xuanche2 = (ImageView) convertView.findViewById(R.id.iv_item_xuanche2);
			holder.rela_item_xuanche2 = (RelativeLayout) convertView.findViewById(R.id.rela_item_xuanche2);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if ("N".equals(obj.getFlag_show())){
			holder.tv_item_xuanche1.setPadding(0,0,0,0);
			holder.iv_item_xuanche2.setVisibility(View.GONE);
			holder.rela_item_xuanche2.setBackgroundResource(R.color.bg_color);
		} else {
			holder.tv_item_xuanche1.setPadding(15,15,15,15);
			holder.iv_item_xuanche2.setVisibility(View.VISIBLE);
			holder.rela_item_xuanche2.setBackgroundResource(R.color.white);
		}
		holder.tv_item_xuanche1.setText(obj.getStep2_name1());
		holder.tv_item_xuanche2.setText(obj.getStep2_name2());

		return convertView;
	}

	class ViewHolder {

		TextView tv_item_xuanche1;
		TextView tv_item_xuanche2;
		ImageView iv_item_xuanche2;
		RelativeLayout rela_item_xuanche2;
	}
}