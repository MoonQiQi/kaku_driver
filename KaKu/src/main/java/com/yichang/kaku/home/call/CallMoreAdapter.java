package com.yichang.kaku.home.call;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.CallMoreObj;

import java.util.List;

public class CallMoreAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<CallMoreObj> list;
    private Context mContext;

    public CallMoreAdapter(Context context, List<CallMoreObj> list) {
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
        CallMoreObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_callmore, null);

            holder.tv_carmore_remark = (TextView) convertView.findViewById(R.id.tv_carmore_remark);
            holder.tv_carmore_time = (TextView) convertView.findViewById(R.id.tv_carmore_time);
            holder.tv_carmore_fen = (TextView) convertView.findViewById(R.id.tv_carmore_fen);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_carmore_remark.setText(obj.getRemark_duration());
        holder.tv_carmore_time.setText(obj.getTime_create());
        String s = obj.getCall_duration();
        int fen = Integer.parseInt(s);
        holder.tv_carmore_fen.setText(String.valueOf(fen / 60));

        return convertView;
    }

    class ViewHolder {

        TextView tv_carmore_remark;
        TextView tv_carmore_time;
        TextView tv_carmore_fen;

    }
}