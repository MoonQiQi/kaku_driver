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

public class ShopHuoDongAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<BigOilObj> list;
    private Context mContext;

    public ShopHuoDongAdapter(Context context, List<BigOilObj> list) {
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
            convertView = mInflater.inflate(R.layout.item_shophuodong, null);
            holder.tv_item_shophuodong_name = (TextView) convertView.findViewById(R.id.tv_item_shophuodong_name);
            holder.tv_item_shophuodong_name1 = (TextView) convertView.findViewById(R.id.tv_item_shophuodong_name1);
            holder.tv_item_shophuodong_price = (TextView) convertView.findViewById(R.id.tv_item_shophuodong_price);
            holder.iv_item_shophuodong_image = (ImageView) convertView.findViewById(R.id.iv_item_shophuodong_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_item_shophuodong_name.setText(obj.getName_item());
        holder.tv_item_shophuodong_name1.setText(obj.getName1_item());
        holder.tv_item_shophuodong_price.setText("¥" + obj.getPrice_item());
        BitmapUtil.getInstance(mContext).download(holder.iv_item_shophuodong_image, KaKuApplication.qian_zhui + obj.getImage_item());

        return convertView;
    }

    class ViewHolder {
        private TextView tv_item_shophuodong_name;
        private TextView tv_item_shophuodong_name1;
        private TextView tv_item_shophuodong_price;
        private ImageView iv_item_shophuodong_image;
    }
}