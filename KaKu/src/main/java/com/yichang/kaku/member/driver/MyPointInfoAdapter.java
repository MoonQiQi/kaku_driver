package com.yichang.kaku.member.driver;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.PointHistoryObj;

import java.util.List;

public class MyPointInfoAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<PointHistoryObj> list;
    private Context mContext;

    public MyPointInfoAdapter(Context context, List<PointHistoryObj> list) {
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
        final PointHistoryObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_member_point, null);

            holder.tv_point_title = (TextView) convertView.findViewById(R.id.tv_point_title);
            holder.tv_point_date = (TextView) convertView.findViewById(R.id.tv_point_date);
            holder.tv_point_num = (TextView) convertView.findViewById(R.id.tv_point_num);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_point_date.setText(obj.getTime_his());
        holder.tv_point_title.setText(obj.getRemark_his());
        String symbol="+";
        if (obj.getFlag_type().equals("P")){
            holder.tv_point_num.setTextColor(Color.rgb(255,0,0));
            symbol="-";
        }else {
            holder.tv_point_num.setTextColor(Color.parseColor("#00BC87"));
        }
        holder.tv_point_num.setText(symbol+obj.getPoint());


        return convertView;
    }


    class ViewHolder {

        private TextView tv_point_title;
        private TextView tv_point_date;
        private TextView tv_point_num;

    }


}

