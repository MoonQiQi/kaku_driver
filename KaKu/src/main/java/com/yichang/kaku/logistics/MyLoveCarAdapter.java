package com.yichang.kaku.logistics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.MyLoveCarObj;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

public class MyLoveCarAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<MyLoveCarObj> list;
    private Context mContext;

    public MyLoveCarAdapter(Context context, List<MyLoveCarObj> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        MyLoveCarObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_mylovecar, null);
            holder.iv_lovecar_image = (ImageView) convertView.findViewById(R.id.iv_lovecar_image);
            holder.tv_lovecar_name = (TextView) convertView.findViewById(R.id.tv_lovecar_name);
            holder.tv_lovecar_mali = (TextView) convertView.findViewById(R.id.tv_lovecar_mali);
            holder.tv_lovecar_chechang = (TextView) convertView.findViewById(R.id.tv_lovecar_chechang);
            holder.tv_lovecar_status = (TextView) convertView.findViewById(R.id.tv_lovecar_status);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_lovecar_name.setText(obj.getName_brand() + obj.getName_series());
        holder.tv_lovecar_mali.setText(obj.getName_power() + "　　　" + obj.getName_actuate() + "　　　" + obj.getName_model());
        holder.tv_lovecar_chechang.setText(obj.getNum_length() + "m   " + obj.getNum_load() + "吨   " + obj.getNum_space() + "m³");
        if ("N".equals(obj.getFlag_approve())) {
            holder.tv_lovecar_status.setText("未认证");
        } else if ("Y".equals(obj.getFlag_approve())) {
            holder.tv_lovecar_status.setText("已认证");
        } else if ("D".equals(obj.getFlag_approve())) {
            holder.tv_lovecar_status.setText("认证中");
        }
        BitmapUtil.getInstance(mContext).download(holder.iv_lovecar_image, KaKuApplication.qian_zhui + obj.getImage_brand());

        return convertView;
    }

    class ViewHolder {

        ImageView iv_lovecar_image;
        TextView tv_lovecar_name;
        TextView tv_lovecar_mali;
        TextView tv_lovecar_status;
        TextView tv_lovecar_chechang;

    }
}