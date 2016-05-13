package com.yichang.kaku.home.call;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.CallListObj;

import java.util.List;

public class CallListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<CallListObj> list;
    private Context mContext;

    public CallListAdapter(Context context, List<CallListObj> list) {
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
        CallListObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_calllist, null);

            holder.tv_callitem_phone = (TextView) convertView.findViewById(R.id.tv_callitem_phone);
            holder.tv_callitem_time = (TextView) convertView.findViewById(R.id.tv_callitem_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_callitem_phone.setText(obj.getPhone_people());
        holder.tv_callitem_time.setText(obj.getTime_create());

        return convertView;
    }

    class ViewHolder {

        TextView tv_callitem_phone;
        TextView tv_callitem_time;

    }
}