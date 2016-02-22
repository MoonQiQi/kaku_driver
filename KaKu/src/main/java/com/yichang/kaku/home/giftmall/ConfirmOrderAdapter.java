package com.yichang.kaku.home.giftmall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.ConfirmOrderProductObj;
import com.yichang.kaku.obj.ShopCartProductObj;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.view.KakuTextView;

import java.util.List;

public class ConfirmOrderAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<ConfirmOrderProductObj> list;
	private Context mContext;

	public ConfirmOrderAdapter(Context context, List<ConfirmOrderProductObj> list) {
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
		ConfirmOrderProductObj obj = list.get(position);
		if (obj == null) {
			return convertView;
		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_confirmorder_product, null);
			holder.tv_confirmorder_num=(TextView)convertView.findViewById(R.id.tv_confirmorder_num);
			holder.iv_confirmorder_product= (ImageView) convertView.findViewById(R.id.iv_confirmorder_product);
			holder.tv_price=(TextView)convertView.findViewById(R.id.tv_price);
			holder.tv_title=(KakuTextView)convertView.findViewById(R.id.tv_shopname);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		BitmapUtil.getInstance(mContext).download(holder.iv_confirmorder_product,KaKuApplication.qian_zhui+obj.getImage_goods());

		holder.tv_confirmorder_num.setText("×"+obj.getNum_shopcar());
		holder.tv_price.setText(obj.getPrice_goods());
		holder.tv_title.setText(obj.getName_goods());
		return convertView;
	}

	class ViewHolder {

		//产品图片
		ImageView iv_confirmorder_product;
		//产品标题、价格
		KakuTextView tv_title;
		TextView tv_price;
		/*//todo 是否包邮 暂时所有商品包邮
		TextView tv_freefhip;*/
		//		数量
		TextView tv_confirmorder_num;

	}

	public void setCallback(CallBack callback) {
		this.callback = callback;
	}

	private CallBack callback;
	public interface CallBack{
		void callback(List<ShopCartProductObj> list);
	}


}