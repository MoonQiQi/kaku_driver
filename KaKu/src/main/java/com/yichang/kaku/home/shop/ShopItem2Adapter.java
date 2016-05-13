package com.yichang.kaku.home.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.Shops_wxzObj;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

public class ShopItem2Adapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Shops_wxzObj> list;
    private Context mContext;

    public ShopItem2Adapter(Context context, List<Shops_wxzObj> list) {
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
        Shops_wxzObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_shop2, null);
            holder.iv_item_shoppic = (ImageView) convertView.findViewById(R.id.iv_item_shoppic);
            holder.tv_item_shopname = (TextView) convertView.findViewById(R.id.tv_item_shopname);
            holder.tv_item_shopaddr = (TextView) convertView.findViewById(R.id.tv_item_shopaddr);
            holder.tv_item_shopdistance = (TextView) convertView.findViewById(R.id.tv_item_shopdistance);
            holder.tv_item_shopdan = (TextView) convertView.findViewById(R.id.tv_item_shopdan);
            holder.tv_item_shopyewu1 = (TextView) convertView.findViewById(R.id.tv_item_shopyewu1);
            holder.tv_item_shopyewu2 = (TextView) convertView.findViewById(R.id.tv_item_shopyewu2);
            holder.tv_item_shopyewu3 = (TextView) convertView.findViewById(R.id.tv_item_shopyewu3);
            holder.tv_item_shopstar = (TextView) convertView.findViewById(R.id.tv_item_shopstar);
            holder.star_item_shopstar = (RatingBar) convertView.findViewById(R.id.star_item_shopstar);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (obj.name_services.size() == 1) {
            holder.tv_item_shopyewu1.setText(obj.name_services.get(0));
            holder.tv_item_shopyewu1.setVisibility(View.VISIBLE);
            holder.tv_item_shopyewu2.setVisibility(View.GONE);
            holder.tv_item_shopyewu3.setVisibility(View.GONE);
        } else if (obj.name_services.size() == 2) {
            holder.tv_item_shopyewu1.setText(obj.name_services.get(0));
            holder.tv_item_shopyewu2.setText(obj.name_services.get(1));
            holder.tv_item_shopyewu1.setVisibility(View.VISIBLE);
            holder.tv_item_shopyewu2.setVisibility(View.VISIBLE);
            holder.tv_item_shopyewu3.setVisibility(View.GONE);
        } else if (obj.name_services.size() == 3) {
            holder.tv_item_shopyewu1.setText(obj.name_services.get(0));
            holder.tv_item_shopyewu2.setText(obj.name_services.get(1));
            holder.tv_item_shopyewu3.setText(obj.name_services.get(2));
            holder.tv_item_shopyewu1.setVisibility(View.VISIBLE);
            holder.tv_item_shopyewu2.setVisibility(View.VISIBLE);
            holder.tv_item_shopyewu3.setVisibility(View.VISIBLE);
        }

        if (!"".equals(obj.getImage_shop())) {
            BitmapUtil.getInstance(mContext).download(holder.iv_item_shoppic, obj.getImage_shop());
        }

        float a = Float.parseFloat(obj.getNum_star());
        holder.star_item_shopstar.setRating(a);
        holder.tv_item_shopname.setText(obj.getName_shop());
        holder.tv_item_shopaddr.setText(obj.getAddr_shop());
        holder.tv_item_shopstar.setText(obj.getNum_star());
        holder.tv_item_shopdistance.setText(obj.getDistance() + "km");
        holder.tv_item_shopdan.setText(obj.getNum_bill() + "单");

        return convertView;
    }

    class ViewHolder {

        ImageView iv_item_shoppic;
        TextView tv_item_shopname;
        TextView tv_item_shopaddr;
        TextView tv_item_shopdistance;
        TextView tv_item_shopdan;
        TextView tv_item_shopyewu1;
        TextView tv_item_shopyewu2;
        TextView tv_item_shopyewu3;
        TextView tv_item_shopstar;
        RatingBar star_item_shopstar;

    }
}