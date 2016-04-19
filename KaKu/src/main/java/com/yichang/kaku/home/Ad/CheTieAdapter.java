package com.yichang.kaku.home.Ad;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.CheTieObj;

import java.util.List;

public class CheTieAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<CheTieObj> list;
	private Context mContext;

	public CheTieAdapter(Context context, List<CheTieObj> list) {
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
		CheTieObj obj = list.get(position);
		if (obj == null) {
			return convertView;
		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_taskchetie, null);
			holder.iv_taskchetieitem_image = (ImageView) convertView.findViewById(R.id.iv_taskchetieitem_image);
			holder.iv_taskchetieitem_status = (ImageView) convertView.findViewById(R.id.iv_taskchetieitem_status);
			holder.iv_taskchetieitem_me = (ImageView) convertView.findViewById(R.id.iv_taskchetieitem_me);
			holder.tv_taskchetieitem_time = (TextView) convertView.findViewById(R.id.tv_taskchetieitem_time);
			holder.tv_taskchetieitem_name = (TextView) convertView.findViewById(R.id.tv_taskchetieitem_name);
			holder.tv_taskchetieitem_money = (TextView) convertView.findViewById(R.id.tv_taskchetieitem_money);
			holder.tv_taskchetieitem_people = (TextView) convertView.findViewById(R.id.tv_taskchetieitem_people);
			holder.tv_taskchetieitem_rishouyi = (TextView) convertView.findViewById(R.id.tv_taskchetieitem_rishouyi);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String string_shouyi = "¥ "+obj.getDay_earnings()+" 日收益";
		SpannableStringBuilder style2 = new SpannableStringBuilder(string_shouyi);
		style2.setSpan(new ForegroundColorSpan(Color.rgb(255, 81, 92)), 0, string_shouyi.length() - 4 , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		style2.setSpan(new AbsoluteSizeSpan(18, true), 1, string_shouyi.length() - 4 , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		holder.tv_taskchetieitem_rishouyi.setText(style2);

		holder.iv_taskchetieitem_image.setImageURI(Uri.parse(KaKuApplication.qian_zhui + obj.getImage_advert()));
		holder.tv_taskchetieitem_name.setText(obj.getName_advert());
		holder.tv_taskchetieitem_money.setText(obj.getTotal_earnings()+"元标准收益");
		holder.tv_taskchetieitem_people.setText(obj.getNum_driver()+"人参与");
		holder.tv_taskchetieitem_time.setText(obj.getTime_begin()+"至"+obj.getTime_end()+"收益");

		if (TextUtils.equals(obj.getFlag_type(),"O")){
			holder.iv_taskchetieitem_status.setImageResource(R.drawable.chetieyuyuezhong);
		} else if (TextUtils.equals(obj.getFlag_type(),"Y")){
			holder.iv_taskchetieitem_status.setImageResource(R.drawable.chetiejinxingzhong);
		} else if (TextUtils.equals(obj.getFlag_type(),"E")){
			holder.iv_taskchetieitem_status.setImageResource(R.drawable.chetieyijieshu);
		}

		if ("Y".equals(obj.getFlag_join())){
			holder.iv_taskchetieitem_me.setVisibility(View.VISIBLE);
		} else {
			holder.iv_taskchetieitem_me.setVisibility(View.GONE);
		}

		return convertView;
	}

	class ViewHolder {

		private  ImageView iv_taskchetieitem_image;
		private  ImageView iv_taskchetieitem_status;
		private  ImageView iv_taskchetieitem_me;
		private  TextView tv_taskchetieitem_time;
		private	 TextView tv_taskchetieitem_name;
		private	 TextView tv_taskchetieitem_rishouyi;
		private	 TextView tv_taskchetieitem_money;
		private	 TextView tv_taskchetieitem_people;

	}
}