package com.yichang.kaku.member;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    public boolean isEnabled(int position) {
        if ("N".equals(list.get(position).getFlag_usable())) {
            return false;
        } else if ("Y".equals(list.get(position).getFlag_usable()) || "O".equals(list.get(position).getFlag_usable())) {
            return true;
        }
        return false;
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

            holder.tv_coupon_money = (TextView) convertView.findViewById(R.id.tv_coupon_money);
            holder.tv_coupon_remark = (TextView) convertView.findViewById(R.id.tv_coupon_remark);
            holder.tv_coupon_name = (TextView) convertView.findViewById(R.id.tv_coupon_name);
            holder.tv_coupon_xianhao = (TextView) convertView.findViewById(R.id.tv_coupon_xianhao);
            holder.tv_coupon_time = (TextView) convertView.findViewById(R.id.tv_coupon_time);
            holder.iv_coupon_nike = (ImageView) convertView.findViewById(R.id.iv_coupon_nike);
            holder.rela_youhuiquan_bg = (RelativeLayout) convertView.findViewById(R.id.rela_youhuiquan_bg);
            holder.view_coupon = convertView.findViewById(R.id.view_coupon);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String money_string = "¥ " + obj.getMoney_coupon();
        SpannableStringBuilder style_money = new SpannableStringBuilder(money_string);
        style_money.setSpan(new AbsoluteSizeSpan(20, true), 1, money_string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tv_coupon_money.setText(style_money);
        holder.tv_coupon_remark.setText("满¥" + obj.getMoney_limit() + "使用");

        if ("Y".equals(obj.getFlag_usable())) {
            holder.rela_youhuiquan_bg.setBackgroundResource(R.drawable.bg_coupon_hong);
            holder.tv_coupon_money.setTextColor(mContext.getResources().getColor(R.color.color_red));
            holder.tv_coupon_remark.setTextColor(mContext.getResources().getColor(R.color.color_red));
            holder.view_coupon.setBackgroundColor(mContext.getResources().getColor(R.color.color_red));
            holder.iv_coupon_nike.setBackgroundResource(R.drawable.unrednike);
        } else if ("N".equals(obj.getFlag_usable())) {
            holder.rela_youhuiquan_bg.setBackgroundResource(R.drawable.bg_coupon_hei);
            holder.tv_coupon_money.setTextColor(mContext.getResources().getColor(R.color.color_word));
            holder.tv_coupon_remark.setTextColor(mContext.getResources().getColor(R.color.color_word));
            holder.view_coupon.setBackgroundColor(mContext.getResources().getColor(R.color.color_word));
            holder.iv_coupon_nike.setBackgroundResource(0);
        } else if ("O".equals(obj.getFlag_usable())) {
            holder.rela_youhuiquan_bg.setBackgroundResource(R.drawable.bg_coupon_hong);
            holder.tv_coupon_money.setTextColor(mContext.getResources().getColor(R.color.color_red));
            holder.tv_coupon_remark.setTextColor(mContext.getResources().getColor(R.color.color_red));
            holder.view_coupon.setBackgroundColor(mContext.getResources().getColor(R.color.color_red));
            holder.iv_coupon_nike.setBackgroundResource(R.drawable.rednike);
        }

        holder.tv_coupon_name.setText(obj.getName_coupon());
        holder.tv_coupon_xianhao.setText(obj.getContent_coupon());
        holder.tv_coupon_time.setText(obj.getTime_start() + "~" + obj.getTime_end());

        return convertView;
    }


    class ViewHolder {
        private TextView tv_coupon_money;
        private TextView tv_coupon_remark;
        private TextView tv_coupon_name;
        private TextView tv_coupon_xianhao;
        private TextView tv_coupon_time;
        private ImageView iv_coupon_nike;
        private RelativeLayout rela_youhuiquan_bg;
        private View view_coupon;
    }

}

