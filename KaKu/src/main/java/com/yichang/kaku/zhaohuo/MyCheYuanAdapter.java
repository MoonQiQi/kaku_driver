package com.yichang.kaku.zhaohuo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.MyCheYuanObj;
import com.yichang.kaku.tools.Utils;

import java.util.List;

public class MyCheYuanAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<MyCheYuanObj> list;
    private Context mContext;

    public MyCheYuanAdapter(Context context, List<MyCheYuanObj> list) {
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
        final MyCheYuanObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_mycheyuan, null);
            holder.tv_mycheyuan_qidian = (TextView) convertView.findViewById(R.id.tv_mycheyuan_qidian);
            holder.tv_mycheyuan_zhongdian = (TextView) convertView.findViewById(R.id.tv_mycheyuan_zhongdian);
            holder.tv_mycheyuan_fabushijian = (TextView) convertView.findViewById(R.id.tv_mycheyuan_fabushijian);
            holder.tv_mycheyuan_zhuanghuoshijian = (TextView) convertView.findViewById(R.id.tv_mycheyuan_zhuanghuoshijian);
            holder.tv_mycheyuan_cheliangxinxi = (TextView) convertView.findViewById(R.id.tv_mycheyuan_cheliangxinxi);
            holder.tv_mycheyuan_tujingchengshi = (TextView) convertView.findViewById(R.id.tv_mycheyuan_tujingchengshi);
            holder.tv_mycheyuan_lianxijilu = (TextView) convertView.findViewById(R.id.tv_mycheyuan_lianxijilu);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_mycheyuan_qidian.setText(obj.getArea_depart());
        holder.tv_mycheyuan_zhongdian.setText(obj.getArea_arrive());
        holder.tv_mycheyuan_fabushijian.setText("发布时间："+obj.getTime_pub());
        holder.tv_mycheyuan_zhuanghuoshijian.setText("可装货时间："+obj.getTime_loading());
        holder.tv_mycheyuan_cheliangxinxi.setText("车辆信息："+obj.getRemark_options());
        holder.tv_mycheyuan_tujingchengshi.setText("途径城市："+obj.getAreas_hsbc());
        holder.tv_mycheyuan_lianxijilu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.Many()){
                    return;
                }
                Intent intent = new Intent(mContext,LianXiJiLuActivity.class);
                intent.putExtra("id_options",obj.getId_options());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {

        TextView tv_mycheyuan_qidian;
        TextView tv_mycheyuan_zhongdian;
        TextView tv_mycheyuan_fabushijian;
        TextView tv_mycheyuan_zhuanghuoshijian;
        TextView tv_mycheyuan_cheliangxinxi;
        TextView tv_mycheyuan_tujingchengshi;
        TextView tv_mycheyuan_lianxijilu;

    }
}