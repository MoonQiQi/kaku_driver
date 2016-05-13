package com.yichang.kaku.home.baoyang;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.ServiceOrderObj;
import com.yichang.kaku.tools.Utils;

import java.util.List;

public class BaoYangOrderAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ServiceOrderObj> list;
    private Context mContext;

    public BaoYangOrderAdapter(Context context, List<ServiceOrderObj> list) {
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
        final ServiceOrderObj obj = list.get(position);

        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_baoyang_orderlist, null);

            holder.tv_baoyangitem_time = (TextView) convertView.findViewById(R.id.tv_baoyangitem_time);
            holder.tv_order_itemprice = (TextView) convertView.findViewById(R.id.tv_order_itemprice);
            holder.tv_baoyangitem_state = (TextView) convertView.findViewById(R.id.tv_baoyangitem_state);
            holder.tv_order_shifukuan = (TextView) convertView.findViewById(R.id.tv_order_shifukuan);
            holder.iv_baoyangitem_button = (ImageView) convertView.findViewById(R.id.iv_baoyangitem_button);
            holder.lv_baoyangorder_item = (ListView) convertView.findViewById(R.id.lv_baoyangorder_item);
            holder.rela_baoyangitem = (RelativeLayout) convertView.findViewById(R.id.rela_baoyangitem);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        int sum = 0;
        int num = 0;
        for (int i = 0; i < obj.getShopcar().size(); i++) {
            sum += Float.parseFloat(obj.getShopcar().get(i).getNum_item()) * Float.parseFloat(obj.getShopcar().get(i).getPrice_item());
            num += Integer.parseInt(obj.getShopcar().get(i).getNum_item());
        }
        holder.tv_baoyangitem_time.setText("订单号：" + obj.getNo_bill());
        holder.tv_order_itemprice.setText("共" + (num - 1) + "件商品，应付：¥" + sum);
        holder.tv_order_shifukuan.setText("实付款：¥ " + obj.getPrice_bill());
        BaoYangQingDan2Adapter adapter = new BaoYangQingDan2Adapter(mContext, list.get(position).getShopcar());
        holder.lv_baoyangorder_item.setAdapter(adapter);
        Utils.setListViewHeightBasedOnChildren(holder.lv_baoyangorder_item);

        holder.rela_baoyangitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KaKuApplication.id_upkeep_bill = list.get(position).getId_upkeep_bill();
                Intent intent = new Intent(mContext, BaoYangOrderDetailActivity.class);
                mContext.startActivity(intent);
            }
        });


        if ("A".equals(obj.getState_bill())) {
            if ("8".equals(obj.getType_pay())) {
                holder.iv_baoyangitem_button.setVisibility(View.VISIBLE);
                holder.iv_baoyangitem_button.setImageResource(R.drawable.shouhoufuwu);
                holder.iv_baoyangitem_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.Call(mContext, Constants.PHONE_KAKU);
                    }
                });
            } else {
                holder.iv_baoyangitem_button.setVisibility(View.VISIBLE);
                holder.iv_baoyangitem_button.setImageResource(R.drawable.lijifukuan);
                holder.iv_baoyangitem_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        callback.payOrder(obj.getNo_bill(), obj.getType_pay(), obj.getPrice_bill(), obj.getFlag_type(), obj.getId_upkeep_bill());
                    }
                });
            }
            holder.tv_baoyangitem_state.setText("待付款");

        } else if ("D".equals(obj.getState_bill())) {
            holder.tv_baoyangitem_state.setText("待服务");
            holder.iv_baoyangitem_button.setVisibility(View.VISIBLE);
            holder.iv_baoyangitem_button.setImageResource(R.drawable.querenfuwu);
            holder.iv_baoyangitem_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.Many()) {
                        return;
                    }
                    KaKuApplication.id_upkeep_bill = list.get(position).getId_upkeep_bill();
                    callback.QueRenFuWu();

                }
            });
        } else if ("E".equals(obj.getState_bill())) {
            holder.tv_baoyangitem_state.setText("待评价");
            holder.iv_baoyangitem_button.setVisibility(View.VISIBLE);
            holder.iv_baoyangitem_button.setImageResource(R.drawable.lijipingjia);
            holder.iv_baoyangitem_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.Many()) {
                        return;
                    }
                    KaKuApplication.id_upkeep_bill = obj.getId_upkeep_bill();
                    if ("U".equals(obj.getFlag_type())) {
                        Intent intent = new Intent(mContext, BaoYangPingJiaActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("addr", obj.getAddr());
                        bundle.putString("name_addr", obj.getName_addr());
                        bundle.putString("phone_addr", obj.getPhone_addr());
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    } else {
                        KaKuApplication.worker = obj.getWorker();
                        mContext.startActivity(new Intent(mContext, OilPingJiaActivity.class));
                    }
                }
            });
        } else if ("F".equals(obj.getState_bill())) {
            holder.tv_baoyangitem_state.setText("已完成");
            holder.iv_baoyangitem_button.setVisibility(View.GONE);
            holder.iv_baoyangitem_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.Many()) {
                        return;
                    }
                }
            });
        } else if ("G".equals(obj.getState_bill())) {
            holder.tv_baoyangitem_state.setText("已取消");
            holder.iv_baoyangitem_button.setVisibility(View.GONE);
            holder.iv_baoyangitem_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.Many()) {
                        return;
                    }
                }
            });
        } else {
            holder.tv_baoyangitem_state.setText("已完成");
            holder.iv_baoyangitem_button.setVisibility(View.GONE);
        }

        return convertView;
    }

    //设置支付回调
    public void setCallback(CallBack callback) {
        this.callback = callback;
    }

    private CallBack callback;

    public interface CallBack {

        void payOrder(String no_bill, String flag_pay, String price_bill, String flag_type, String id);

        void QueRenFuWu();

    }

    class ViewHolder {

        private TextView tv_baoyangitem_time;
        private TextView tv_baoyangitem_state;
        private TextView tv_order_itemprice;
        private TextView tv_order_shifukuan;
        private ImageView iv_baoyangitem_button;
        private ListView lv_baoyangorder_item;
        private RelativeLayout rela_baoyangitem;
    }
}

