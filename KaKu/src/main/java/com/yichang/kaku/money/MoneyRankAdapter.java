package com.yichang.kaku.money;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.MoneyRankObj;

import java.util.List;

public class MoneyRankAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<MoneyRankObj> list;
    private Context mContext;

    public MoneyRankAdapter(Context context, List<MoneyRankObj> list) {
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
        MoneyRankObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_mokeyrank, null);

            holder.tv_moneyitem_num = (TextView) convertView.findViewById(R.id.tv_moneyitem_num);
            holder.tv_moneyitem_money = (TextView) convertView.findViewById(R.id.tv_moneyitem_money);
            holder.tv_moneyitem_phone = (TextView) convertView.findViewById(R.id.tv_moneyitem_phone);
            holder.iv_moneyitem_num = (ImageView) convertView.findViewById(R.id.iv_moneyitem_num);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == 0) {
            holder.tv_moneyitem_money.setTextColor(mContext.getResources().getColor(R.color.color_red));
            holder.tv_moneyitem_phone.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.tv_moneyitem_num.setVisibility(View.INVISIBLE);
            holder.iv_moneyitem_num.setVisibility(View.VISIBLE);
            holder.iv_moneyitem_num.setBackgroundResource(R.drawable.rank1);
        } else if (position == 1) {
            holder.tv_moneyitem_money.setTextColor(mContext.getResources().getColor(R.color.color_red));
            holder.tv_moneyitem_phone.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.tv_moneyitem_num.setVisibility(View.INVISIBLE);
            holder.iv_moneyitem_num.setVisibility(View.VISIBLE);
            holder.iv_moneyitem_num.setBackgroundResource(R.drawable.rank2);
        } else if (position == 2) {
            holder.tv_moneyitem_money.setTextColor(mContext.getResources().getColor(R.color.color_red));
            holder.tv_moneyitem_phone.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.tv_moneyitem_num.setVisibility(View.INVISIBLE);
            holder.iv_moneyitem_num.setVisibility(View.VISIBLE);
            holder.iv_moneyitem_num.setBackgroundResource(R.drawable.rank3);
        } else {
            holder.tv_moneyitem_money.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.tv_moneyitem_phone.setTextColor(mContext.getResources().getColor(R.color.color_word));
            holder.iv_moneyitem_num.setVisibility(View.GONE);
            holder.tv_moneyitem_num.setVisibility(View.VISIBLE);
            holder.tv_moneyitem_num.setText(obj.getRanking());
        }

        holder.tv_moneyitem_money.setText(obj.getMoney_income_total() + "元");
        holder.tv_moneyitem_phone.setText(obj.getPhone_driver() + "     " + obj.getCarno_advert());
        return convertView;
    }

    class ViewHolder {

        TextView tv_moneyitem_money;
        TextView tv_moneyitem_num;
        TextView tv_moneyitem_phone;
        ImageView iv_moneyitem_num;
    }
}