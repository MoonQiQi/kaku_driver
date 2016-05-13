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
import com.yichang.kaku.obj.MyCarObj;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

public class BaoYangChooseCarAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<MyCarObj> list;
    private Context mContext;
    private int num;

    public BaoYangChooseCarAdapter(Context context, List<MyCarObj> list) {
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
        MyCarObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_baoyangchoosecar, null);
            holder.iv_bychoosecar_moren = (ImageView) convertView.findViewById(R.id.iv_bychoosecar_moren);
            holder.iv_bychoosecar_brand = (ImageView) convertView.findViewById(R.id.iv_bychoosecar_brand);
            holder.tv_bychoosecar_brand = (TextView) convertView.findViewById(R.id.tv_bychoosecar_brand);
            holder.tv_bychoosecar_xilie = (TextView) convertView.findViewById(R.id.tv_bychoosecar_xilie);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BitmapUtil.getInstance(mContext).download(holder.iv_bychoosecar_brand, KaKuApplication.qian_zhui + obj.getImage_brand());
        holder.tv_bychoosecar_brand.setText(obj.getName_brand());
        holder.tv_bychoosecar_xilie.setText(obj.getName_brand() + obj.getSeries_engine() + "      " + obj.getPower() + "马力");
        if ("Y".equals(obj.getFlag_default())) {
            holder.iv_bychoosecar_moren.setVisibility(View.VISIBLE);
        } else {
            holder.iv_bychoosecar_moren.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    class ViewHolder {

        private ImageView iv_bychoosecar_moren;
        private ImageView iv_bychoosecar_brand;
        private TextView tv_bychoosecar_brand;
        private TextView tv_bychoosecar_xilie;

    }

}