package com.yichang.kaku.home.baoyang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.BigOilObj;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

public class BaoYangChooseFilterAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<BigOilObj> list;
    private Context mContext;

    public BaoYangChooseFilterAdapter(Context context, List<BigOilObj> list) {
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
        BigOilObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_baoyangchoosefilter, null);
            holder.iv_baoyangchangeitem_image = (ImageView) convertView.findViewById(R.id.iv_baoyangchangeitem_image);
            holder.tv_baoyangchangeitem_name = (TextView) convertView.findViewById(R.id.tv_baoyangchangeitem_name);
            holder.tv_baoyangchangeitem_remark = (TextView) convertView.findViewById(R.id.tv_baoyangchangeitem_remark);
            holder.tv_baoyangchangeitem_price = (TextView) convertView.findViewById(R.id.tv_baoyangchangeitem_price);
            holder.iv_baoyangchangeitem_now = (ImageView) convertView.findViewById(R.id.iv_baoyangchangeitem_now);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BitmapUtil.getInstance(mContext).download(holder.iv_baoyangchangeitem_image, KaKuApplication.qian_zhui + obj.getImage_item());
        holder.tv_baoyangchangeitem_name.setText(obj.getName_item());
        holder.tv_baoyangchangeitem_remark.setText(obj.getName1_item());
        holder.tv_baoyangchangeitem_price.setText("¥" + obj.getPrice_item());
        if ("Y".equals(obj.getFlag_choose())) {
            holder.iv_baoyangchangeitem_now.setVisibility(View.VISIBLE);
        } else {
            holder.iv_baoyangchangeitem_now.setVisibility(View.GONE);
        }

        return convertView;
    }

    class ViewHolder {

        private ImageView iv_baoyangchangeitem_image;
        private TextView tv_baoyangchangeitem_name;
        private TextView tv_baoyangchangeitem_remark;
        private TextView tv_baoyangchangeitem_price;
        private ImageView iv_baoyangchangeitem_now;

    }

}