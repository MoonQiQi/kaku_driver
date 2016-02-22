package com.yichang.kaku.member.driver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.AttentionShopObj;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

public class AttentionShopsAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<AttentionShopObj> list;
    private Context mContext;

    public AttentionShopsAdapter(Context context, List<AttentionShopObj> list) {
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

        final ViewHolder holder;
        AttentionShopObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_member_attention, null);
            holder.iv_item_shoppic_attention = (ImageView) convertView.findViewById(R.id.iv_item_shoppic_attention);
            holder.tv_item_shopname_attention = (TextView) convertView.findViewById(R.id.tv_item_shopname_attention);
            holder.tv_item_shopaddr_attention = (TextView) convertView.findViewById(R.id.tv_item_shopaddr_attention);
            holder.tv_item_shopbrand_attention = (TextView) convertView.findViewById(R.id.tv_item_shopbrand_attention);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //维修店照片
        if (!"".equals(obj.getImage_shop())) {
            BitmapUtil.getInstance(mContext).download(holder.iv_item_shoppic_attention, KaKuApplication.qian_zhui+obj.getImage_shop());
        }
        holder.tv_item_shopname_attention.setText(obj.getName_shop());
        holder.tv_item_shopaddr_attention.setText(obj.getAddr_shop());
        holder.tv_item_shopbrand_attention.setText(obj.getName_brands());



        return convertView;
    }

    class ViewHolder {

        ImageView iv_item_shoppic_attention;
        TextView tv_item_shopname_attention;
        TextView tv_item_shopaddr_attention;
        TextView tv_item_shopbrand_attention;

    }

}

