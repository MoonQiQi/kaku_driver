package com.yichang.kaku.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.BrandObj;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<BrandObj> list;
	private Context mContext;

	public ImageAdapter(Context context, List<BrandObj> list) {
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
		BrandObj obj = list.get(position);
		if (obj == null) {
			return convertView;
		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_image, null);
			holder.iv_imageitem_image = (ImageView) convertView.findViewById(R.id.iv_imageitem_image);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		BitmapUtil.getInstance(mContext).download(holder.iv_imageitem_image,KaKuApplication.qian_zhui+obj.getImage_brand());

		return convertView;
	}

	class ViewHolder {

		private ImageView iv_imageitem_image;

	}
}