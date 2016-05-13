package com.yichang.kaku.home.giftmall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.GoodsModelObj;

import java.util.List;

public class ProductModelAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<GoodsModelObj> list;
    private Context mContext;

    public ProductModelAdapter(Context context, List<GoodsModelObj> list) {
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
        GoodsModelObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_productmodel, null);
            holder.tv_baoyangfooditem_name = (TextView) convertView.findViewById(R.id.tv_baoyangfooditem_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_baoyangfooditem_name.setText(obj.getName_goods_model());
        if ("R".equals(obj.getColor())) {
            holder.tv_baoyangfooditem_name.setBackgroundResource(R.drawable.btn_hong);
            holder.tv_baoyangfooditem_name.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.tv_baoyangfooditem_name.setBackgroundResource(R.drawable.btn_hui);
            holder.tv_baoyangfooditem_name.setTextColor(mContext.getResources().getColor(R.color.color_word));
        }
        return convertView;
    }

    class ViewHolder {

        private TextView tv_baoyangfooditem_name;

    }
}