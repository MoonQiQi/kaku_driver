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

public class BaoYangQingDan2Adapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<BigOilObj> list;
    private Context mContext;

    public BaoYangQingDan2Adapter(Context context, List<BigOilObj> list) {
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
            convertView = mInflater.inflate(R.layout.item_baoyangqingdan, null);

            holder.tv_qingdan_name = (TextView) convertView.findViewById(R.id.tv_qingdan_name);
            holder.tv_qingdan_price = (TextView) convertView.findViewById(R.id.tv_qingdan_price);
            holder.tv_qingdan_num = (TextView) convertView.findViewById(R.id.tv_qingdan_num);
            holder.iv_qingdan_image = (ImageView) convertView.findViewById(R.id.iv_qingdan_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BitmapUtil.getInstance(mContext).download(holder.iv_qingdan_image, KaKuApplication.qian_zhui + obj.getImage_item());
        holder.tv_qingdan_name.setText(obj.getName_item());
        holder.tv_qingdan_num.setText("x " + obj.getNum_item());
        holder.tv_qingdan_price.setText("¥ " + obj.getPrice_item());

        return convertView;
    }

    class ViewHolder {

        TextView tv_qingdan_price;
        TextView tv_qingdan_num;
        TextView tv_qingdan_name;
        ImageView iv_qingdan_image;
    }
}