package com.yichang.kaku.member;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.CangKuObj;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

public class CangKu2Adapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<CangKuObj> list;
    private Context mContext;

    public CangKu2Adapter(Context context, List<CangKuObj> list) {
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
        final CangKuObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_cangku2, null);
            holder.iv_baoyangchangeitem_image = (ImageView) convertView.findViewById(R.id.iv_baoyangchangeitem_image);
            holder.tv_cangkuitem_name = (TextView) convertView.findViewById(R.id.tv_cangkuitem_name);
            holder.tv_cangku_no = (TextView) convertView.findViewById(R.id.tv_cangku_no);
            holder.tv_cangkuitem_remark = (TextView) convertView.findViewById(R.id.tv_cangkuitem_remark);
            holder.tv_cangkuitem_num = (TextView) convertView.findViewById(R.id.tv_cangkuitem_num);
            holder.tv_cangku_shop = (TextView) convertView.findViewById(R.id.tv_cangku_shop);
            holder.tv_cangku_time = (TextView) convertView.findViewById(R.id.tv_cangku_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.tv_cangkuitem_name.setText(obj.getName_item());
        holder.tv_cangkuitem_remark.setText(obj.getName1_item());
        holder.tv_cangku_no.setText("订单编号：" + obj.getNo_bill());
        holder.tv_cangkuitem_num.setText("X" + obj.getNum_item());
        holder.tv_cangku_shop.setText("服务商家：" + obj.getName_shop());
        holder.tv_cangku_time.setText(obj.getTime_used());
        BitmapUtil.getInstance(mContext).download(holder.iv_baoyangchangeitem_image, KaKuApplication.qian_zhui + obj.getImage_item());

        return convertView;
    }

    class ViewHolder {

        private ImageView iv_baoyangchangeitem_image;
        private TextView tv_cangkuitem_name;
        private TextView tv_cangku_no;
        private TextView tv_cangkuitem_remark;
        private TextView tv_cangkuitem_num;
        private TextView tv_cangku_shop;
        private TextView tv_cangku_time;

    }

}