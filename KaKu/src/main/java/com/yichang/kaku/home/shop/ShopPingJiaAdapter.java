package com.yichang.kaku.home.shop;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.ShopPingJiaObj;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

public class ShopPingJiaAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ShopPingJiaObj> list;
    private Context mContext;

    public ShopPingJiaAdapter(Context context, List<ShopPingJiaObj> list) {
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
        ShopPingJiaObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_shoppingjia, null);
            holder.iv_shoppingjia1 = (ImageView) convertView.findViewById(R.id.iv_shoppingjia1);
            holder.iv_shoppingjia2 = (ImageView) convertView.findViewById(R.id.iv_shoppingjia2);
            holder.iv_shoppingjia3 = (ImageView) convertView.findViewById(R.id.iv_shoppingjia3);
            holder.iv_shoppingjia4 = (ImageView) convertView.findViewById(R.id.iv_shoppingjia4);
            holder.tv_shoppingjia_name = (TextView) convertView.findViewById(R.id.tv_shoppingjia_name);
            holder.tv_shoppingjia_time = (TextView) convertView.findViewById(R.id.tv_shoppingjia_time);
            holder.tv_shoppingjia_content = (TextView) convertView.findViewById(R.id.tv_shoppingjia_content);
            holder.star_shoppingjia_star = (RatingBar) convertView.findViewById(R.id.star_shoppingjia_star);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (!"".equals(obj.getImage1_eval())) {
            holder.iv_shoppingjia1.setVisibility(View.VISIBLE);
            BitmapUtil.getInstance(mContext).download(holder.iv_shoppingjia1, KaKuApplication.qian_zhuikong + obj.getImage1_eval());
        } else {
            holder.iv_shoppingjia1.setVisibility(View.GONE);
        }

        if (!"".equals(obj.getImage2_eval())) {
            holder.iv_shoppingjia2.setVisibility(View.VISIBLE);
            BitmapUtil.getInstance(mContext).download(holder.iv_shoppingjia2, KaKuApplication.qian_zhuikong + obj.getImage2_eval());
        } else {
            holder.iv_shoppingjia2.setVisibility(View.GONE);
        }

        if (!"".equals(obj.getImage3_eval())) {
            holder.iv_shoppingjia3.setVisibility(View.VISIBLE);
            BitmapUtil.getInstance(mContext).download(holder.iv_shoppingjia3, KaKuApplication.qian_zhuikong + obj.getImage3_eval());
        } else {
            holder.iv_shoppingjia3.setVisibility(View.GONE);
        }
        if (!"".equals(obj.getImage4_eval())) {
            holder.iv_shoppingjia4.setVisibility(View.VISIBLE);
            BitmapUtil.getInstance(mContext).download(holder.iv_shoppingjia4, KaKuApplication.qian_zhuikong + obj.getImage4_eval());
        } else {
            holder.iv_shoppingjia4.setVisibility(View.GONE);
        }


        if (TextUtils.isEmpty(obj.getName_driver())) {
            holder.tv_shoppingjia_name.setText("**");
        } else {
            holder.tv_shoppingjia_name.setText(obj.getName_driver());
        }
        holder.tv_shoppingjia_time.setText(obj.getTime_evaluation());
        holder.tv_shoppingjia_content.setText(obj.getEvaluation_order());
        String star = obj.getStar_order();
        float starFloat = Float.valueOf(star);
        holder.star_shoppingjia_star.setRating(starFloat);

        return convertView;
    }

    class ViewHolder {

        ImageView iv_shoppingjia1;
        ImageView iv_shoppingjia2;
        ImageView iv_shoppingjia3;
        ImageView iv_shoppingjia4;
        TextView tv_shoppingjia_name;
        TextView tv_shoppingjia_time;
        TextView tv_shoppingjia_content;
        RatingBar star_shoppingjia_star;

    }
}