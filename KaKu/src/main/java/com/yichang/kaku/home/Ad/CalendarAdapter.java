package com.yichang.kaku.home.ad;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.DayObj;

import java.util.List;

public class CalendarAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<DayObj> list;
    private Context mContext;
    private int index_y;

    public CalendarAdapter(Context context, List<DayObj> list, int index_y) {
        // TODO Auto-generated constructor stub
        this.list = list;
        this.mContext = context;
        this.index_y = index_y;
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
        DayObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_calendar, null);
            holder.tv_gv_calendar1 = (TextView) convertView.findViewById(R.id.tv_gv_calendar1);
            holder.tv_gv_calendar2 = (TextView) convertView.findViewById(R.id.tv_gv_calendar2);
            holder.iv_gv_calendar = (ImageView) convertView.findViewById(R.id.iv_gv_calendar);
            holder.iv_gv_calendar2 = (ImageView) convertView.findViewById(R.id.iv_gv_calendar2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String sign = obj.getSign();
        String sign0 = obj.getSign0();
        String sign1 = obj.getSign1();
        String date = obj.getDate();


        /*第二排是否是黄字，第三排是否有黄底*/
        if ("C".equals(sign)) {
            holder.tv_gv_calendar1.setTextColor(Color.rgb(99, 99, 99));
            holder.tv_gv_calendar2.setBackgroundResource(0);
        } else if ("Y".equals(sign)) {
            holder.tv_gv_calendar1.setTextColor(Color.rgb(255, 180, 0));
            holder.iv_gv_calendar2.setBackgroundResource(R.drawable.rili_huangquan);
            holder.tv_gv_calendar2.setText(date.substring(8));
        } else if ("P".equals(sign)) {
            holder.tv_gv_calendar1.setTextColor(Color.rgb(255, 180, 0));
            holder.iv_gv_calendar2.setBackgroundResource(R.drawable.rili_huangquandaixiangji);
            holder.tv_gv_calendar2.setText(date.substring(8));
        }

        /*第一排是相机还是审核中*/
        if ("N".equals(sign1)) {

        } else if ("P".equals(sign1)) {
            holder.iv_gv_calendar.setImageResource(R.drawable.rili_xiangji);
        } else if ("I".equals(sign1)) {
            holder.iv_gv_calendar.setImageResource(R.drawable.rili_shenhezhong);
        }

        /*第二排是不是0，是0就不显示*/
        if (TextUtils.equals(obj.getMoney(), "0")) {
            holder.tv_gv_calendar1.setText("");
        } else {
            holder.tv_gv_calendar1.setText("¥" + obj.getMoney());
        }

        /*第三排是不是今天，是今天就不显示日期数字*/
        if (position == index_y) {
            holder.tv_gv_calendar2.setText("");
        } else {
            if ("00".equals(date.substring(8))) {
                holder.tv_gv_calendar2.setText("");
            } else {
                holder.tv_gv_calendar2.setText(date.substring(8));
            }
        }

        /*第三排背景图*/
        if ("B".equals(sign0)) {
            holder.tv_gv_calendar2.setText("");
            holder.tv_gv_calendar2.setBackgroundResource(R.drawable.rili_shi);
        } else if ("E".equals(sign0)) {
            holder.tv_gv_calendar2.setText("");
            holder.tv_gv_calendar2.setBackgroundResource(R.drawable.rili_zhong);
        } else if ("Y".equals(sign0)) {
            holder.tv_gv_calendar2.setText("");
            holder.tv_gv_calendar2.setBackgroundResource(R.drawable.rili_jin);
        } else if ("C".equals(sign0)) {
            if ("00".equals(date.substring(8))) {
                holder.tv_gv_calendar2.setText("");
            } else {
                holder.tv_gv_calendar2.setText(date.substring(8));
            }
            holder.tv_gv_calendar2.setBackgroundResource(R.drawable.rili_blue);
        } else if ("N".equals(sign0)) {
            if ("00".equals(date.substring(8))) {
                holder.tv_gv_calendar2.setText("");
            } else {
                holder.tv_gv_calendar2.setText(date.substring(8));
            }
        }

        return convertView;
    }

    class ViewHolder {

        private TextView tv_gv_calendar1;
        private TextView tv_gv_calendar2;
        private ImageView iv_gv_calendar;
        private ImageView iv_gv_calendar2;

    }
}