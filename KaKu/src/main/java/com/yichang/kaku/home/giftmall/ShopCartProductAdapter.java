package com.yichang.kaku.home.giftmall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.ShopCartProductObj;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;

import java.util.List;

public class ShopCartProductAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<ShopCartProductObj> list;
	private Context mContext;
	private boolean isChecked;

	public ShopCartProductAdapter(Context context, List<ShopCartProductObj> list) {
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
		/*isChecked=callback.getCheckedState();
		LogUtil.E("getView isChecked"+isChecked);*/
		final ViewHolder holder;
		ShopCartProductObj obj = list.get(position);
		if (obj == null) {
			return convertView;
		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_shopcart_product, null);
			holder.tv_shopcart_num=(TextView)convertView.findViewById(R.id.tv_shopcart_num);
			holder.iv_shopcart_product= (ImageView) convertView.findViewById(R.id.iv_shopcart_product);
			holder.tv_shopcart_price=(TextView)convertView.findViewById(R.id.tv_shopcart_price);
			holder.tv_shopcart_title=(TextView)convertView.findViewById(R.id.tv_shopcart_title);
			holder.cbx_shopcart_select= (CheckBox) convertView.findViewById(R.id.cbx_shopcart_select);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		BitmapUtil.getInstance(mContext).download(holder.iv_shopcart_product,KaKuApplication.qian_zhui+obj.getImage_goods());

		holder.tv_shopcart_num.setText("X"+obj.getNum_shopcar());
		holder.tv_shopcart_price.setText(obj.getPrice_goods());
		LogUtil.E("idgoods_shopcar:"+obj.getId_goods_shopcar());
		holder.tv_shopcart_price.setTag(obj.getId_goods_shopcar());
		holder.tv_shopcart_title.setText(obj.getName_goods());
		holder.cbx_shopcart_select.setChecked(obj.getIsChecked());

		return convertView;
	}

	class ViewHolder {
		//选择状态
		CheckBox cbx_shopcart_select;
		//产品图片
		ImageView iv_shopcart_product;
		//产品标题、价格
		TextView tv_shopcart_title;
		TextView tv_shopcart_price;
		//		增删数量
		TextView tv_shopcart_num;

	}

	public void setCallback(CallBack callback) {
		this.callback = callback;
	}

	private CallBack callback;
	public interface CallBack{
		/*void callback(List<ShopCartProductObj> list);*/
		boolean getCheckedState();
	}


}