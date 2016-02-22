package com.yichang.kaku.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.YouHuiQuanObj;

import java.util.List;

public class YouHuiQuanAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<YouHuiQuanObj> list;
    private Context mContext;

    public YouHuiQuanAdapter(Context context, List<YouHuiQuanObj> list) {
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
        YouHuiQuanObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_coupon_list, null);

            holder.tv_rmb = (TextView) convertView.findViewById(R.id.tv_rmb);
            holder.tv_condition = (TextView) convertView.findViewById(R.id.tv_condition);
            holder.tv_deadline = (TextView) convertView.findViewById(R.id.tv_deadline);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_rmb.setText(obj.getMoney_coupon());

        holder.tv_condition.setText(obj.getContent_coupon());

        String timeLimit="使用期限："+obj.getTime_start().substring(0,obj.getTime_start().indexOf(" "))+
                "~"+obj.getTime_end().substring(0, obj.getTime_end().indexOf(" "));

        holder.tv_deadline.setText(timeLimit);

        return convertView;
    }


    class ViewHolder {
        //抵用价格
        private TextView tv_rmb;
        //使用条件
        private TextView tv_condition;
        private TextView tv_deadline;

    }

}

