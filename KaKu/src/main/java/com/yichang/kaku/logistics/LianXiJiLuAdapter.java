package com.yichang.kaku.logistics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.LianXiJiLuObj;
import com.yichang.kaku.tools.Utils;

import java.util.List;

public class LianXiJiLuAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<LianXiJiLuObj> list;
    private Context mContext;

    public LianXiJiLuAdapter(Context context, List<LianXiJiLuObj> list) {
        this.list = list;
        this.mContext = context;
        if (mInflater == null) {
            mInflater = LayoutInflater.from(context);
        }
    }

    @Override
    public int getCount() {
        return list != null && !list.isEmpty() ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        final LianXiJiLuObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_lianxijilu, null);
            holder.iv_lianxijilu_call = (ImageView) convertView.findViewById(R.id.iv_lianxijilu_call);
            holder.tv_lianxijilu_name = (TextView) convertView.findViewById(R.id.tv_lianxijilu_name);
            holder.tv_lianxijilu_time = (TextView) convertView.findViewById(R.id.tv_lianxijilu_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_lianxijilu_name.setText("货主名称：" + obj.getName_contact());
        holder.tv_lianxijilu_time.setText(obj.getTime_contact());

        holder.iv_lianxijilu_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.Many()) {
                    return;
                }
                Utils.Call(mContext, obj.getPhone_contact());
            }
        });


        return convertView;
    }

    class ViewHolder {

        ImageView iv_lianxijilu_call;
        TextView tv_lianxijilu_name;
        TextView tv_lianxijilu_time;

    }
}