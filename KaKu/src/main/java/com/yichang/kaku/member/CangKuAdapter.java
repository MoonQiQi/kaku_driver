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

public class CangKuAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<CangKuObj> list;
    private Context mContext;

    public CangKuAdapter(Context context, List<CangKuObj> list) {
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
            convertView = mInflater.inflate(R.layout.item_cangku, null);
            holder.iv_baoyangchangeitem_image = (ImageView) convertView.findViewById(R.id.iv_baoyangchangeitem_image);
            holder.iv_cangku_shiyong = (ImageView) convertView.findViewById(R.id.iv_cangku_shiyong);
            holder.tv_cangkuitem_name = (TextView) convertView.findViewById(R.id.tv_cangkuitem_name);
            holder.tv_cangku_no = (TextView) convertView.findViewById(R.id.tv_cangku_no);
            holder.tv_cangkuitem_remark = (TextView) convertView.findViewById(R.id.tv_cangkuitem_remark);
            holder.tv_cangkuitem_num = (TextView) convertView.findViewById(R.id.tv_cangkuitem_num);
            holder.tv_cangku_shop = (TextView) convertView.findViewById(R.id.tv_cangku_shop);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.tv_cangkuitem_name.setText(obj.getName_item());
        holder.tv_cangkuitem_remark.setText(obj.getName1_item());
        holder.tv_cangku_no.setText("订单编号：" + obj.getNo_bill());
        holder.tv_cangkuitem_num.setText("X" + obj.getNum_item());
        holder.tv_cangku_shop.setText("服务商家：" + obj.getName_shop());
        BitmapUtil.getInstance(mContext).download(holder.iv_baoyangchangeitem_image, KaKuApplication.qian_zhui + obj.getImage_item());
        holder.iv_cangku_shiyong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pupinterface.Pup(obj.getCode_used());
            }
        });

        return convertView;
    }

    class ViewHolder {

        private ImageView iv_baoyangchangeitem_image;
        private ImageView iv_cangku_shiyong;
        private TextView tv_cangkuitem_name;
        private TextView tv_cangku_no;
        private TextView tv_cangkuitem_remark;
        private TextView tv_cangkuitem_num;
        private TextView tv_cangku_shop;

    }

    public Pupinterface pupinterface;

    public interface Pupinterface {
        void Pup(String no_id);
    }

    public void setPop(Pupinterface pupinterface) {
        this.pupinterface = pupinterface;
    }

}