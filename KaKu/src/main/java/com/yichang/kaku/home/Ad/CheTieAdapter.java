package com.yichang.kaku.home.ad;

import android.content.Context;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.CheTieTaskObj;

import java.util.List;

public class CheTieAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<CheTieTaskObj> list;
    private Context mContext;

    public CheTieAdapter(Context context, List<CheTieTaskObj> list) {
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
        CheTieTaskObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_taskchetie, null);
            holder.iv_taskchetieitem_image = (ImageView) convertView.findViewById(R.id.iv_taskchetieitem_image);
            holder.iv_taskchetieitem_me = (ImageView) convertView.findViewById(R.id.iv_taskchetieitem_me);
            holder.iv_taskchetieitem_daili = (ImageView) convertView.findViewById(R.id.iv_taskchetieitem_daili);
            holder.tv_taskchetieitem_status = (TextView) convertView.findViewById(R.id.tv_taskchetieitem_status);
            holder.tv_taskchetieitem_name = (TextView) convertView.findViewById(R.id.tv_taskchetieitem_name);
            holder.tv_taskchetieitem_money = (TextView) convertView.findViewById(R.id.tv_taskchetieitem_money);
            holder.tv_taskchetieitem_people = (TextView) convertView.findViewById(R.id.tv_taskchetieitem_people);
            holder.tv_taskchetieitem_task = (TextView) convertView.findViewById(R.id.tv_taskchetieitem_task);
            holder.tv_taskchetieitem_month = (TextView) convertView.findViewById(R.id.tv_taskchetieitem_month);
            holder.tv_taskchetieitem_biaozhunshouyi = (TextView) convertView.findViewById(R.id.tv_taskchetieitem_biaozhunshouyi);
            holder.rela_taskchetieitem_6f = (RelativeLayout) convertView.findViewById(R.id.rela_taskchetieitem_6f);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String string_money = "¥ " + obj.getEarnings_total();
        SpannableStringBuilder style_money = new SpannableStringBuilder(string_money);
        style_money.setSpan(new AbsoluteSizeSpan(20, true), 1, string_money.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tv_taskchetieitem_money.setText(style_money);

        holder.iv_taskchetieitem_image.setImageURI(Uri.parse(KaKuApplication.qian_zhui + obj.getImage_advert()));
        holder.tv_taskchetieitem_name.setText(obj.getName_advert());
        holder.tv_taskchetieitem_month.setText(obj.getMonth_continue() + "个月收益期");
        holder.tv_taskchetieitem_task.setText(obj.getOrder_ceiling() + "总任务");
        holder.tv_taskchetieitem_people.setText(obj.getNum_driver() + "人已参与");
        holder.tv_taskchetieitem_biaozhunshouyi.setText("标准收益（¥" + obj.getEarnings_average() + "/天）");

        if (TextUtils.equals(obj.getFlag_active(), "Y")) {
            holder.iv_taskchetieitem_daili.setVisibility(View.VISIBLE);
            holder.tv_taskchetieitem_status.setTextColor(mContext.getResources().getColor(R.color.color_red));
            if (TextUtils.equals(obj.getFlag_type(), "O")) {
                holder.tv_taskchetieitem_status.setText("预约进行中");
                holder.iv_taskchetieitem_daili.setImageResource(R.drawable.btn_ljyy);
            } else if (TextUtils.equals(obj.getFlag_type(), "Y")) {
                if (TextUtils.equals(obj.getFlag_show(), "Y")) {
                    holder.tv_taskchetieitem_status.setText("参与进行中");
                    holder.iv_taskchetieitem_daili.setImageResource(R.drawable.btn_ljdl);
                } else {
                    holder.tv_taskchetieitem_status.setText("参与进行中");
                    holder.iv_taskchetieitem_daili.setImageResource(R.drawable.btn_ljcj);
                }
            }
        } else {
            holder.iv_taskchetieitem_daili.setVisibility(View.GONE);
            if (TextUtils.equals(obj.getFlag_type(), "O")) {
                holder.tv_taskchetieitem_status.setText(obj.getTime_begin() + "开始任务");
            } else if (TextUtils.equals(obj.getFlag_type(), "Y")) {
                holder.tv_taskchetieitem_status.setText(obj.getTime_end_join() + "结束参与");
            } else if (TextUtils.equals(obj.getFlag_type(), "E")) {
                holder.tv_taskchetieitem_status.setText(obj.getTime_end() + "结束任务");
            }
            holder.tv_taskchetieitem_status.setTextColor(mContext.getResources().getColor(R.color.color_word));
        }

        if (position != 0 && "N".equals(obj.getFlag_show())){
            holder.rela_taskchetieitem_6f.setVisibility(View.VISIBLE);
        } else {
            holder.rela_taskchetieitem_6f.setVisibility(View.GONE);
        }


        if ("Y".equals(obj.getFlag_join())) {
            holder.iv_taskchetieitem_me.setVisibility(View.VISIBLE);
        } else {
            holder.iv_taskchetieitem_me.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    class ViewHolder {

        private ImageView iv_taskchetieitem_image;
        private ImageView iv_taskchetieitem_me;
        private ImageView iv_taskchetieitem_daili;
        private TextView tv_taskchetieitem_status;
        private TextView tv_taskchetieitem_name;
        private TextView tv_taskchetieitem_biaozhunshouyi;
        private TextView tv_taskchetieitem_money;
        private TextView tv_taskchetieitem_people;
        private TextView tv_taskchetieitem_task;
        private TextView tv_taskchetieitem_month;
        private RelativeLayout rela_taskchetieitem_6f;

    }
}