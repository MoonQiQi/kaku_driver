package com.yichang.kaku.money;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.TuiJianObj;

import java.util.List;

public class TuiJianAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<TuiJianObj> list;
    private Context mContext;

    public TuiJianAdapter(Context context, List<TuiJianObj> list) {
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
        TuiJianObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_tuijian, null);

            holder.tv_item_tuijian1 = (TextView) convertView.findViewById(R.id.tv_item_tuijian1);
            holder.tv_item_tuijian2 = (TextView) convertView.findViewById(R.id.tv_item_tuijian2);
            holder.tv_item_tuijian3 = (TextView) convertView.findViewById(R.id.tv_item_tuijian3);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_item_tuijian1.setText(obj.getPhone_driver());
        if ("Y".equals(obj.getFlag_get())) {
            holder.tv_item_tuijian3.setTextColor(mContext.getResources().getColor(R.color.color_red));
        } else {
            holder.tv_item_tuijian3.setTextColor(mContext.getResources().getColor(R.color.color_word));
        }
        if ("Y".equals(obj.getFlag_recommended())) {
            holder.tv_item_tuijian2.setTextColor(mContext.getResources().getColor(R.color.color_red));
        } else {
            holder.tv_item_tuijian2.setTextColor(mContext.getResources().getColor(R.color.color_word));
        }
        return convertView;
    }

    class ViewHolder {

        TextView tv_item_tuijian1;
        TextView tv_item_tuijian2;
        TextView tv_item_tuijian3;
    }
}