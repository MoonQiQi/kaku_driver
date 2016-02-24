package com.yichang.kaku.zhaohuo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.MyHuoObj;
import com.yichang.kaku.tools.Utils;

import java.util.List;

public class MyHuoAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<MyHuoObj> list;
    private Context mContext;
    private String call_string;

    public MyHuoAdapter(Context context, List<MyHuoObj> list) {
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
        final MyHuoObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_myhuo, null);
            holder.iv_myhuo_call = (ImageView) convertView.findViewById(R.id.iv_myhuo_call);
            holder.tv_myhuo_qidian = (TextView) convertView.findViewById(R.id.tv_myhuo_qidian);
            holder.tv_myhuo_zhongdian = (TextView) convertView.findViewById(R.id.tv_myhuo_zhongdian);
            holder.tv_myhuo_fabushijian = (TextView) convertView.findViewById(R.id.tv_myhuo_fabushijian);
            holder.tv_myhuo_huoyuanxinxi = (TextView) convertView.findViewById(R.id.tv_myhuo_huoyuanxinxi);
            holder.tv_myhuo_guanzhu = (TextView) convertView.findViewById(R.id.tv_myhuo_guanzhu);
            holder.tv_myhuo_lianxiriqi = (TextView) convertView.findViewById(R.id.tv_myhuo_lianxiriqi);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_myhuo_qidian.setText(obj.getArea_depart());
        holder.tv_myhuo_zhongdian.setText(obj.getArea_arrive());
        holder.tv_myhuo_fabushijian.setText("发布时间：" + obj.getTime_pub());
        holder.tv_myhuo_huoyuanxinxi.setText("货源信息：" + obj.getRemark_supply());
        holder.tv_myhuo_lianxiriqi.setText("联系时间：" + obj.getTime_contact());
        holder.tv_myhuo_guanzhu.setText(obj.getFocus_supply() + "人关注");
        holder.iv_myhuo_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.Many()){
                    return;
                }
                call_string = obj.getPhone_supply().split(",")[0];
                Utils.Call(mContext, call_string);
            }
        });


        return convertView;
    }

    class ViewHolder {

        ImageView iv_myhuo_call;
        TextView tv_myhuo_qidian;
        TextView tv_myhuo_zhongdian;
        TextView tv_myhuo_fabushijian;
        TextView tv_myhuo_huoyuanxinxi;
        TextView tv_myhuo_guanzhu;
        TextView tv_myhuo_lianxiriqi;

    }
}