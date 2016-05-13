package com.yichang.kaku.member.cash;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.YueObj;

import java.util.List;

public class YueAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<YueObj> list;
    private Context mContext;

    public YueAdapter(Context context, List<YueObj> list) {
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
        YueObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_yue, null);
            holder.tv_yueitem_name = (TextView) convertView.findViewById(R.id.tv_yueitem_name);
            holder.tv_yueitem_time = (TextView) convertView.findViewById(R.id.tv_yueitem_time);
            holder.tv_yueitem_money = (TextView) convertView.findViewById(R.id.tv_yueitem_money);
            holder.tv_yueitem_type = (TextView) convertView.findViewById(R.id.tv_yueitem_type);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if ("N".equals(obj.getBusiness_type())) {
            holder.tv_yueitem_type.setText("审核中");
        } else if ("Y".equals(obj.getBusiness_type())) {
            holder.tv_yueitem_type.setText("已提现");
        } else if ("F".equals(obj.getBusiness_type())) {
            holder.tv_yueitem_type.setText("提现失败");
        } else {
            holder.tv_yueitem_type.setText("");
        }


        if ("G".equals(obj.getFlag_type())) {
            holder.tv_yueitem_money.setText("¥ " + obj.getNum_money());
            holder.tv_yueitem_money.setTextColor(Color.rgb(255, 11, 11));
        } else if ("P".equals(obj.getFlag_type())) {
            holder.tv_yueitem_money.setText("¥ " + obj.getNum_money());
            holder.tv_yueitem_money.setTextColor(Color.rgb(149, 210, 83));
        }
        holder.tv_yueitem_name.setText(obj.getRemark_money());
        holder.tv_yueitem_time.setText(obj.getCreate_time());


        return convertView;
    }

    class ViewHolder {

        private TextView tv_yueitem_name;
        private TextView tv_yueitem_time;
        private TextView tv_yueitem_money;
        private TextView tv_yueitem_type;

    }
}