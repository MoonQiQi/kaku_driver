package com.yichang.kaku.home.moneybook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.MokeyBook2Obj;

import java.util.List;

public class MoneyBook2Adapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<MokeyBook2Obj> list;
    private Context mContext;
    private int num;

    public MoneyBook2Adapter(Context context, List<MokeyBook2Obj> list) {
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
        MokeyBook2Obj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_moneybook2, null);
            holder.tv_mb2_name = (TextView) convertView.findViewById(R.id.tv_mb2_name);
            holder.tv_mb2_price = (TextView) convertView.findViewById(R.id.tv_mb2_price);
            holder.iv_mb2_image = (ImageView) convertView.findViewById(R.id.iv_mb2_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_mb2_name.setText(obj.getRemark_account());
        if ("G".equals(obj.getFlag_type())) {
            holder.tv_mb2_price.setText("+" + obj.getMoney_account());
        } else {
            holder.tv_mb2_price.setText("-" + obj.getMoney_account());
        }
        if ("G1".equals(obj.getFlag_way())) {
            holder.iv_mb2_image.setImageResource(R.drawable.mb_yunfei);
        } else if ("G2".equals(obj.getFlag_way())) {
            holder.iv_mb2_image.setImageResource(R.drawable.mb_gongzi);
        } else if ("G3".equals(obj.getFlag_way())) {
            holder.iv_mb2_image.setImageResource(R.drawable.mb_waikuai);
        } else if ("G4".equals(obj.getFlag_way())) {
            holder.iv_mb2_image.setImageResource(R.drawable.mb_qitalv);
        } else if ("P1".equals(obj.getFlag_way())) {
            holder.iv_mb2_image.setImageResource(R.drawable.mb_jiayou);
        } else if ("P2".equals(obj.getFlag_way())) {
            holder.iv_mb2_image.setImageResource(R.drawable.mb_guolufei);
        } else if ("P3".equals(obj.getFlag_way())) {
            holder.iv_mb2_image.setImageResource(R.drawable.mb_canfei);
        } else if ("P4".equals(obj.getFlag_way())) {
            holder.iv_mb2_image.setImageResource(R.drawable.mb_weixiu);
        } else if ("P5".equals(obj.getFlag_way())) {
            holder.iv_mb2_image.setImageResource(R.drawable.mb_tongxun);
        } else if ("P6".equals(obj.getFlag_way())) {
            holder.iv_mb2_image.setImageResource(R.drawable.mb_qitahong);
        }

        return convertView;
    }

    class ViewHolder {

        private ImageView iv_mb2_image;
        private TextView tv_mb2_name;
        private TextView tv_mb2_price;

    }

}