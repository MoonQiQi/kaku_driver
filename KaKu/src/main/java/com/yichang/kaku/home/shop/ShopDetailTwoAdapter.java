package com.yichang.kaku.home.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.ShopService2Obj;

import java.util.List;

public class ShopDetailTwoAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ShopService2Obj> list;
    private Context mContext;

    public ShopDetailTwoAdapter(Context context, List<ShopService2Obj> list) {
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
        ShopService2Obj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_shoptwo, null);
            holder.tv_shop2_name = (TextView) convertView.findViewById(R.id.tv_shop2_name);
            holder.tv_shop2_remark = (TextView) convertView.findViewById(R.id.tv_shop2_remark);
            holder.tv_shop2_price = (TextView) convertView.findViewById(R.id.tv_shop2_price);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_shop2_name.setText(obj.getName_service());
        holder.tv_shop2_remark.setText(obj.getRemark_service());
        holder.tv_shop2_price.setText(obj.getPrice_service());


        return convertView;
    }

    class ViewHolder {

        TextView tv_shop2_name;
        TextView tv_shop2_remark;
        TextView tv_shop2_price;
    }
}