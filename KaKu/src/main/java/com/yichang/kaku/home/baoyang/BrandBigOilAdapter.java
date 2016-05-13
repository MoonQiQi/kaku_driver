package com.yichang.kaku.home.baoyang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.BrandBigOilObj;

import java.util.List;

public class BrandBigOilAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<BrandBigOilObj> list;
    private Context mContext;

    public BrandBigOilAdapter(Context context, List<BrandBigOilObj> list) {
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
        BrandBigOilObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_brandbigoil, null);
            holder.tv_brandbigoil_name = (TextView) convertView.findViewById(R.id.tv_brandbigoil_name);
            holder.iv_brandbigoil_duihao = (ImageView) convertView.findViewById(R.id.iv_brandbigoil_duihao);
            holder.view_brandoil_item = convertView.findViewById(R.id.view_brandoil_item);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if ("H".equals(obj.getFlag())) {
            holder.tv_brandbigoil_name.setTextColor(mContext.getResources().getColor(R.color.color_red));
            holder.iv_brandbigoil_duihao.setVisibility(View.VISIBLE);
            holder.view_brandoil_item.setVisibility(View.VISIBLE);
        } else {
            holder.tv_brandbigoil_name.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.iv_brandbigoil_duihao.setVisibility(View.GONE);
            holder.view_brandoil_item.setVisibility(View.GONE);
        }

        holder.tv_brandbigoil_name.setText(obj.getName_brand());

        return convertView;
    }

    class ViewHolder {
        private TextView tv_brandbigoil_name;
        private ImageView iv_brandbigoil_duihao;
        private View view_brandoil_item;
    }
}