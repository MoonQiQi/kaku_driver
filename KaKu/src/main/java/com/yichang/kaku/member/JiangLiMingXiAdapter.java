package com.yichang.kaku.member;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.RecommendedsObj;

import java.util.List;

public class JiangLiMingXiAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<RecommendedsObj> list;
    private Context mContext;

    public JiangLiMingXiAdapter(Context context, List<RecommendedsObj> list) {
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
        RecommendedsObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_jianglimingxi, null);
            holder.tv_rewards_name = (TextView) convertView.findViewById(R.id.tv_rewards_name);
            holder.iv_role = (ImageView) convertView.findViewById(R.id.iv_role);
            holder.tv_rewards_phone = (TextView) convertView.findViewById(R.id.tv_rewards_phone);
            holder.tv_rewards_quasi_cash = (TextView) convertView.findViewById(R.id.tv_rewards_quasi_cash);
            holder.tv_rewards_point = (TextView) convertView.findViewById(R.id.tv_rewards_point);
            holder.tv_rewards_cash = (TextView) convertView.findViewById(R.id.tv_rewards_cash);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_rewards_name.setText(obj.getName_driver());
        if ("Y".equals(obj.getFlag_advert())){
            holder.iv_role.setVisibility(View.VISIBLE);
        }else {
            holder.iv_role.setVisibility(View.INVISIBLE);
        }
        holder.tv_rewards_phone .setText(obj.getPhone_driver());
        holder.tv_rewards_quasi_cash.setText("￥" + obj.getMoney_earnings());
        holder.tv_rewards_point .setText(obj.getPoints());
        holder.tv_rewards_cash .setText("￥"+obj.getMoney_balance());

        return convertView;
    }

    class ViewHolder {
        private TextView tv_rewards_name;
        private ImageView iv_role;
        private TextView tv_rewards_phone;
        private TextView tv_rewards_quasi_cash;
        private TextView tv_rewards_point;
        private TextView tv_rewards_cash;
    }
}