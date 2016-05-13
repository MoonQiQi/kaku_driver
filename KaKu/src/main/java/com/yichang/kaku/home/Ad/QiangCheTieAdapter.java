package com.yichang.kaku.home.ad;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.CheTieObj;

import java.util.List;

public class QiangCheTieAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<CheTieObj> list;
	private Context mContext;

	public QiangCheTieAdapter(Context context, List<CheTieObj> list) {
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
			convertView = mInflater.inflate(R.layout.item_chetie, null);
			holder.iv_chetieitem_image = (ImageView) convertView.findViewById(R.id.iv_chetieitem_image);
			holder.iv_chetieitem_mashangqiang = (ImageView) convertView.findViewById(R.id.iv_chetieitem_mashangqiang);
			holder.tv_chetieitem_time = (TextView) convertView.findViewById(R.id.tv_chetieitem_time);
			holder.tv_chetieitem_status = (TextView) convertView.findViewById(R.id.tv_chetieitem_status);
			holder.tv_chetieitem_name = (TextView) convertView.findViewById(R.id.tv_chetieitem_name);
			holder.tv_chetieitem_timeshouyi = (TextView) convertView.findViewById(R.id.tv_chetieitem_timeshouyi);
			holder.tv_chetieitem_money = (TextView) convertView.findViewById(R.id.tv_chetieitem_money);
			holder.tv_chetieitem_people = (TextView) convertView.findViewById(R.id.tv_chetieitem_people);
			holder.tv_chetieitem_yuqishouyi = (TextView) convertView.findViewById(R.id.tv_chetieitem_yuqishouyi);
			holder.tv_chetieitem_percent = (TextView) convertView.findViewById(R.id.tv_chetieitem_percent);
			holder.progress_chetieitem = (ProgressBar) convertView.findViewById(R.id.progress_chetieitem);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.iv_chetieitem_image.setImageURI(Uri.parse(KaKuApplication.qian_zhui + obj.getImage_advert()));
		holder.tv_chetieitem_name.setText(obj.getName_advert());
		holder.tv_chetieitem_percent.setText(obj.getSales_percent()+"%");
		holder.tv_chetieitem_timeshouyi.setText(obj.getTime_begin() +"至"+obj.getTime_end()+"标准收益"+obj.getDay_continue()+"天");

		String string_money = "¥ " + obj.getTotal_earnings()+" 标准收益";
		SpannableStringBuilder style = new SpannableStringBuilder(string_money);
		style.setSpan(new AbsoluteSizeSpan(18, true), 0, string_money.length()-5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		style.setSpan(new ForegroundColorSpan(Color.rgb(255, 81, 92)), 0, string_money.length()-5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		holder.tv_chetieitem_money.setText(style);

		String string_shouyi = "售价：¥ "+obj.getPrice_advert();
		SpannableStringBuilder style2 = new SpannableStringBuilder(string_shouyi);
		style2.setSpan(new ForegroundColorSpan(Color.rgb(255, 81, 92)), 3, string_shouyi.length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		holder.tv_chetieitem_yuqishouyi.setText(style2);

		String string_people = obj.getNum_driver()+"人参与";
		SpannableStringBuilder style3 = new SpannableStringBuilder(string_people);
		style3.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), 0, string_people.length() - 3 , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		holder.tv_chetieitem_people.setText(style3);

		holder.progress_chetieitem.setProgress((int) Float.parseFloat(obj.getSales_percent()));
		if (TextUtils.equals(obj.getFlag_type(),"Y")){
			holder.iv_chetieitem_mashangqiang.setVisibility(View.VISIBLE);
		} else {
			holder.iv_chetieitem_mashangqiang.setVisibility(View.GONE);
		}
		if (TextUtils.equals(obj.getFlag_type(),"O")){
			holder.tv_chetieitem_time.setText(obj.getTime_end_sell()+"起抢购");
		} else {
			holder.tv_chetieitem_time.setText(obj.getTime_end_sell()+"前抢购");
		}
		if (TextUtils.equals(obj.getFlag_type(),"O")){
			holder.tv_chetieitem_status.setText("即将开始");
		} else if (TextUtils.equals(obj.getFlag_type(),"Y")){
			holder.tv_chetieitem_status.setText("抢购中");
		} else if (TextUtils.equals(obj.getFlag_type(),"E")){
			holder.tv_chetieitem_status.setText("已结束");
		} else if (TextUtils.equals(obj.getFlag_type(),"N")){
			holder.tv_chetieitem_status.setText("已抢完");
		}

		return convertView;
	}

	class ViewHolder {

		private ImageView iv_chetieitem_image;
		private ImageView iv_chetieitem_mashangqiang;
		private TextView tv_chetieitem_time;
		private	 TextView tv_chetieitem_status;
		private	 TextView tv_chetieitem_name;
		private	 TextView tv_chetieitem_timeshouyi;
		private	 TextView tv_chetieitem_money;
		private	 TextView tv_chetieitem_people;
		private	 TextView tv_chetieitem_yuqishouyi;
		private	 TextView tv_chetieitem_day;
		private	 TextView tv_chetieitem_percent;
		private ProgressBar progress_chetieitem;

	}
}