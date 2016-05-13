package com.yichang.kaku.zhaohuo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.QuanZiObj;
import com.yichang.kaku.tools.Utils;

import java.util.List;

public class QuanziAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<QuanZiObj> list;
    private Context mContext;
    private String call_string;

    public QuanziAdapter(Context context, List<QuanZiObj> list) {
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

        Viewholder holder;
        final QuanZiObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new Viewholder();
            convertView = mInflater.inflate(R.layout.item_quanzi, null);
            holder.iv_quanzi_tu = (ImageView) convertView.findViewById(R.id.iv_quanzi_tu);
            holder.iv_quanzi_re = (ImageView) convertView.findViewById(R.id.iv_quanzi_re);
            holder.iv_quanzi_head = (ImageView) convertView.findViewById(R.id.iv_quanzi_head);
            holder.tv_quanzi_title = (TextView) convertView.findViewById(R.id.tv_quanzi_title);
            holder.tv_quanzi_name = (TextView) convertView.findViewById(R.id.tv_quanzi_name);
            holder.tv_quanzi_yuedu = (TextView) convertView.findViewById(R.id.tv_quanzi_yuedu);
            holder.tv_quanzi_pinglun = (TextView) convertView.findViewById(R.id.tv_quanzi_pinglun);
            holder.tv_quanzi_time = (TextView) convertView.findViewById(R.id.tv_quanzi_time);

            convertView.setTag(holder);
        } else {
            holder = (Viewholder) convertView.getTag();
        }

        holder.tv_quanzi_name.setText(obj.getName_driver());
        holder.tv_quanzi_title.setText(obj.getName_circle());
        holder.tv_quanzi_pinglun.setText(obj.getNum_comments());
        holder.tv_quanzi_yuedu.setText(obj.getNum_read());
        if (Integer.parseInt(obj.getNum_read()) >= 1000) {
            holder.iv_quanzi_re.setVisibility(View.VISIBLE);
        } else {
            holder.iv_quanzi_re.setVisibility(View.GONE);
        }
        if ("".equals(obj.getImage_circle1())) {
            holder.iv_quanzi_tu.setVisibility(View.GONE);
        } else {
            holder.iv_quanzi_tu.setVisibility(View.VISIBLE);
        }

        holder.tv_quanzi_time.setText(Utils.TimeChange(obj.getTime_pub()));

        return convertView;
    }

    class Viewholder {
        ImageView iv_quanzi_tu;
        ImageView iv_quanzi_re;
        ImageView iv_quanzi_head;
        TextView tv_quanzi_title;
        TextView tv_quanzi_name;
        TextView tv_quanzi_yuedu;
        TextView tv_quanzi_pinglun;
        TextView tv_quanzi_time;
    }

}
