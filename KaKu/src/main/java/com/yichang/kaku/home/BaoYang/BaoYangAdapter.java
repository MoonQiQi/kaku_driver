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

public class BaoYangAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<BigOilObj> list;
    private Context mContext;
    private int num;

    public BaoYangAdapter(Context context, List<BigOilObj> list) {
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
            convertView = mInflater.inflate(R.layout.item_baoyang, null);
            holder.iv_baoyangitem_image = (ImageView) convertView.findViewById(R.id.iv_baoyangitem_image);
            holder.tv_baoyangitem_name = (TextView) convertView.findViewById(R.id.tv_baoyangitem_name);
            holder.tv_baoyangitem_remark = (TextView) convertView.findViewById(R.id.tv_baoyangitem_remark);
            holder.tv_baoyangitem_price = (TextView) convertView.findViewById(R.id.tv_baoyangitem_price);
            holder.tv_baoyangitem_num = (TextView) convertView.findViewById(R.id.tv_baoyangitem_num);
            holder.iv_baoyangitem_jiahao = (ImageView) convertView.findViewById(R.id.iv_baoyangitem_jiahao);
            holder.iv_baoyangitem_jianhao = (ImageView) convertView.findViewById(R.id.iv_baoyangitem_jianhao);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BitmapUtil.getInstance(mContext).download(holder.iv_baoyangitem_image, KaKuApplication.qian_zhui + obj.getImage_item());
        holder.tv_baoyangitem_name.setText(obj.getName_item());
        holder.tv_baoyangitem_remark.setText(obj.getName1_item());
        holder.tv_baoyangitem_num.setText(obj.getNum_item());
        holder.tv_baoyangitem_price.setText("¥ " + obj.getPrice_item());
        num = Integer.parseInt(obj.getNum_item());

        holder.iv_baoyangitem_jiahao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                list.get(position).setNum_item(num + "");
                baoYangButton.Jia();
                notifyDataSetChanged();
            }
        });
        holder.iv_baoyangitem_jianhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num == 0) {
                    return;
                }
                num--;
                list.get(position).setNum_item(num + "");
                baoYangButton.Jian();
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    class ViewHolder {

        private ImageView iv_baoyangitem_image;
        private TextView tv_baoyangitem_name;
        private TextView tv_baoyangitem_remark;
        private TextView tv_baoyangitem_price;
        private TextView tv_baoyangitem_num;
        private ImageView iv_baoyangitem_jiahao;
        private ImageView iv_baoyangitem_jianhao;

    }

    public BaoYangButton baoYangButton;

    public interface BaoYangButton {
        void Jia();

        void Jian();

    }

    public void setButton(BaoYangButton baoYangButton) {
        this.baoYangButton = baoYangButton;
    }

}