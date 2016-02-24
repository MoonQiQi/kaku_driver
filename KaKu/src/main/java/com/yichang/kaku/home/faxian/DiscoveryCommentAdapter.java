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
import com.yichang.kaku.obj.DiscoveryCommentsObj;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.view.KakuMultiLineTextView;

import java.util.List;

public class DiscoveryCommentAdapter extends BaseAdapter{
	private LayoutInflater mInflater;
	private List<DiscoveryCommentsObj> list;
	private Context mContext;

	public DiscoveryCommentAdapter(Context context, List<DiscoveryCommentsObj> list) {
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
		// TODO Auto-generated method stub
		final ViewHolder holder;
		DiscoveryCommentsObj obj = list.get(position);
		if (obj == null) {
			return convertView;
		}
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.item_discovery_comment, null);
				holder.tv_discovery_comment_name = (TextView) convertView
						.findViewById(R.id.tv_discovery_comment_name);
				holder.tv_discovery_comment_time = (TextView) convertView
						.findViewById(R.id.tv_discovery_comment_time);
				holder.tv_discovery_comment_content = (KakuMultiLineTextView) convertView
						.findViewById(R.id.tv_discovery_comment_content);
				holder.iv_discovery_comment_head = (ImageView) convertView
						.findViewById(R.id.iv_discovery_comment_head);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.tv_discovery_comment_name.setText(list.get(position).getName_driver());
			holder.tv_discovery_comment_time.setText(list.get(position).getTime_comment());

			holder.tv_discovery_comment_content.setText(ToDBC(list.get(position).getContent_comment()));
		//holder.tv_discovery_comment_content.
			LogUtil.E("head:" + list.get(position).getHead());
			if (!"".equals(list.get(position).getHead())) {
				String head = KaKuApplication.qian_zhui+list.get(position).getHead();
				BitmapUtil.getInstance(mContext).download(holder.iv_discovery_comment_head, head);
			}
		
		return convertView;
	}
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i< c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}if (c[i]> 65280&& c[i]< 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	class ViewHolder {
		private TextView tv_discovery_comment_name;
		private TextView tv_discovery_comment_time;
		private KakuMultiLineTextView tv_discovery_comment_content;
		private ImageView iv_discovery_comment_head;
	}

}