package com.yichang.kaku.member.driver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.MemberMsgObj;

import java.util.List;

public class MsgAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<MemberMsgObj> list;
    private Context mContext;

    public MsgAdapter(Context context, List<MemberMsgObj> list) {
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
        final MemberMsgObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_member_msg, null);

            holder.tv_msg_content = (TextView) convertView.findViewById(R.id.tv_msg_content);
            holder.tv_msg_name = (TextView) convertView.findViewById(R.id.tv_msg_name);
            holder.tv_msg_time = (TextView) convertView.findViewById(R.id.tv_msg_time);
            //holder.tv_msg_vieworder = (TextView) convertView.findViewById(R.id.tv_msg_vieworder);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_msg_name.setText(obj.getName_notice());
        //String strContent = "您的订单[" + obj.getId_order() + "]";
        holder.tv_msg_content.setText(obj.getContent());
        holder.tv_msg_time.setText(obj.getTime_pub());
        /*holder.tv_msg_vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*todo 跳转至订单详情页面*//*
                if (Utils.Many()){
                    return;
                }

                    *//*跳转服务订单*//*
                if ("A".equals(obj.getState_order())) {
                    KaKuApplication.id_orderA = obj.getId_order();
                    mContext.startActivity(new Intent(mContext, OrderDetailAActivity.class));
                } else if ("B".equals(obj.getState_order())) {
                    KaKuApplication.id_orderB = obj.getId_order();
                    mContext.startActivity(new Intent(mContext, OrderDetailBActivity.class));
                } else if ("C".equals(obj.getState_order())) {
                    KaKuApplication.id_orderC = obj.getId_order();
                    mContext.startActivity(new Intent(mContext, OrderDetailCActivity.class));
                } else if ("D".equals(obj.getState_order())) {
                    KaKuApplication.id_orderD = obj.getId_order();
                    mContext.startActivity(new Intent(mContext, OrderDetailDActivity.class));
                } else if ("E".equals(obj.getState_order())) {
                    KaKuApplication.id_orderE = obj.getId_order();
                    mContext.startActivity(new Intent(mContext, OrderDetailEActivity.class));
                } else if ("F".equals(obj.getState_order())) {
                    KaKuApplication.id_orderF = obj.getId_order();
                    mContext.startActivity(new Intent(mContext, OrderDetailFActivity.class));
                } else if ("G".equals(obj.getState_order())) {
                    KaKuApplication.id_orderG = obj.getId_order();
                    mContext.startActivity(new Intent(mContext, OrderDetailGActivity.class));
                } else if ("Z".equals(obj.getState_order())) {
                    KaKuApplication.id_orderZ = obj.getId_order();
                    mContext.startActivity(new Intent(mContext, OrderDetailZActivity.class));
                }

            }
        });*/

        return convertView;
    }


    class ViewHolder {

        private TextView tv_msg_content;
        private TextView tv_msg_time;
        //private TextView tv_msg_vieworder;
        private TextView tv_msg_name;

    }


}

