package com.yichang.kaku.home.baoyang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.BaoYangShopObj;

import java.util.List;

public class BaoYangShopAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<BaoYangShopObj> list;
    private Context mContext;

    public BaoYangShopAdapter(Context context, List<BaoYangShopObj> list) {
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
        BaoYangShopObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_baoyangshop, null);
            holder.star_byshopitem = (RatingBar) convertView.findViewById(R.id.star_byshopitem);
            holder.iv_byshopitem_cu = (ImageView) convertView.findViewById(R.id.iv_byshopitem_cu);
            holder.tv_byshopitem_cu = (TextView) convertView.findViewById(R.id.tv_byshopitem_cu);
            holder.tv_byshopitem_name = (TextView) convertView.findViewById(R.id.tv_byshopitem_name);
            holder.tv_byshopitem_star = (TextView) convertView.findViewById(R.id.tv_byshopitem_star);
            holder.tv_byshopitem_dan = (TextView) convertView.findViewById(R.id.tv_byshopitem_dan);
            holder.tv_byshopitem_price = (TextView) convertView.findViewById(R.id.tv_byshopitem_price);
            holder.tv_byshopitem_juli = (TextView) convertView.findViewById(R.id.tv_byshopitem_juli);
            holder.rela_byshopitem2 = (RelativeLayout) convertView.findViewById(R.id.rela_byshopitem2);
            holder.view_byshopitem = convertView.findViewById(R.id.view_byshopitem);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_byshopitem_name.setText(obj.getName_shop());
        float starFloat1 = Float.valueOf(obj.getNum_star());
        holder.star_byshopitem.setRating(starFloat1);
        holder.tv_byshopitem_star.setText(starFloat1 + "");
        holder.tv_byshopitem_dan.setText(obj.getNum_bill() + "单");
        holder.tv_byshopitem_price.setText("¥" + obj.getPrice_item());
        holder.tv_byshopitem_juli.setText(obj.getDistance() + "km");
        holder.tv_byshopitem_cu.setText(obj.getActivity_shop_content());
        if ("".equals(obj.getActivity_shop_content())) {
            holder.view_byshopitem.setVisibility(View.GONE);
            holder.rela_byshopitem2.setVisibility(View.GONE);
        } else {
            holder.view_byshopitem.setVisibility(View.VISIBLE);
            holder.rela_byshopitem2.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    class ViewHolder {

        ImageView iv_byshopitem_cu;
        TextView tv_byshopitem_cu;
        TextView tv_byshopitem_name;
        TextView tv_byshopitem_star;
        TextView tv_byshopitem_dan;
        TextView tv_byshopitem_price;
        TextView tv_byshopitem_juli;
        RatingBar star_byshopitem;
        View view_byshopitem;
        RelativeLayout rela_byshopitem2;

    }
}