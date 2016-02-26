package com.yichang.kaku.home.nouse;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
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
import com.yichang.kaku.obj.GoodsSSObj;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

public class CarShopSSAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<GoodsSSObj> list;
	private Context mContext;

	public CarShopSSAdapter(Context context, List<GoodsSSObj> list) {
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
		
		final ViewHolder holder;
		GoodsSSObj obj = list.get(position);
		if (obj == null) {
			return convertView;
		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_carshop, null);
			holder.iv_shopcaritem_shoppic = (ImageView) convertView.findViewById(R.id.iv_shopcaritem_shoppic);
			holder.iv_shopcaritem_baoyou = (ImageView) convertView.findViewById(R.id.iv_shopcaritem_baoyou);
			holder.tv_shopcaritem_shopname = (TextView) convertView.findViewById(R.id.tv_shopcaritem_shopname);
			holder.tv_shopcaritem_jianjie = (TextView) convertView.findViewById(R.id.tv_shopcaritem_jianjie);
			holder.tv_shopcaritem_buy = (TextView) convertView.findViewById(R.id.tv_shopcaritem_buy);
			holder.tv_shopcaritem_ping = (TextView) convertView.findViewById(R.id.tv_shopcaritem_ping);
			holder.tv_shopcaritem_shopprice = (TextView) convertView.findViewById(R.id.tv_shopcaritem_shopprice);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//搜索—车品商城照片
		if (!"".equals(obj.getImage_goods())) {
			BitmapUtil.getInstance(mContext).download(holder.iv_shopcaritem_shoppic, KaKuApplication.qian_zhui+obj.getImage_goods());
		}
		//包邮
		if ("Y".equals(obj.getFlag_mail())) {
			holder.iv_shopcaritem_baoyou.setVisibility(View.VISIBLE);
		} else {
			holder.iv_shopcaritem_baoyou.setVisibility(View.GONE);
		}
		holder.tv_shopcaritem_shopname.setText(obj.getName_goods());//车品名称
		holder.tv_shopcaritem_jianjie.setText(obj.getPromotion_goods());//车品简介
		holder.tv_shopcaritem_buy.setText(obj.getNum_exchange()+"人购买");//购买人数
		holder.tv_shopcaritem_ping.setText(obj.getNum_eval()+"人评");//评论数
		String string = "¥"+obj.getPrice_goods()+"起";
		SpannableStringBuilder style = new SpannableStringBuilder(string);   
	    style.setSpan(new ForegroundColorSpan(Color.rgb(209, 0, 0)),0,string.length()-1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    style.setSpan(new AbsoluteSizeSpan(20,true),1,string.length()-1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//起价
	    holder.tv_shopcaritem_shopprice.setText(style);

		return convertView;
	}

	class ViewHolder {

		ImageView iv_shopcaritem_shoppic;
		TextView tv_shopcaritem_shopname;
		TextView tv_shopcaritem_jianjie;
		TextView tv_shopcaritem_buy;
		TextView tv_shopcaritem_ping;
		TextView tv_shopcaritem_shopprice;
		ImageView iv_shopcaritem_baoyou;

	}
}