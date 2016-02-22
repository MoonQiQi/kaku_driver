package com.yichang.kaku.member.serviceorder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.OrderObj;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.Utils;

import java.util.List;

public class ServiceOrderAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<OrderObj> list;
    private Context mContext;

    public ServiceOrderAdapter(Context context, List<OrderObj> list) {
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
        final OrderObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_orderlist, null);
            holder.iv_order_daoche = (ImageView) convertView.findViewById(R.id.iv_order_daoche);
            holder.iv_order_image = (ImageView) convertView.findViewById(R.id.iv_order_image);
            holder.iv_order_call = (ImageView) convertView.findViewById(R.id.iv_order_call);
            holder.iv_order_mashang = (ImageView) convertView.findViewById(R.id.iv_order_mashang);
            holder.iv_order_wancheng = (ImageView) convertView.findViewById(R.id.iv_order_wancheng);
            holder.tv_order_shop = (TextView) convertView.findViewById(R.id.tv_order_shop);
            holder.tv_order_time = (TextView) convertView.findViewById(R.id.tv_order_time);
            holder.tv_order_name = (TextView) convertView.findViewById(R.id.tv_order_name);
            holder.tv_order_num = (TextView) convertView.findViewById(R.id.tv_order_num);
            holder.tv_order_state = (TextView) convertView.findViewById(R.id.tv_order_state);
            holder.tv_order_price = (TextView) convertView.findViewById(R.id.tv_order_price);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BitmapUtil.getInstance(mContext).download(holder.iv_order_image, KaKuApplication.qian_zhui + obj.getGoods_image_order());
        holder.tv_order_shop.setText(obj.getName_shop());
        holder.tv_order_name.setText(obj.getName_order());
        if ("Y".equals(obj.getType_service())) {
            holder.iv_order_daoche.setVisibility(View.VISIBLE);
        } else {
            holder.iv_order_daoche.setVisibility(View.GONE);
        }
        //数量+价格
        holder.tv_order_num.setText("共" + obj.getGoods_nums_order() + "件 合计");
        holder.tv_order_price.setText(" ¥ " + obj.getPrice_order());

        //状态判断
        if ("A".equals(obj.getState_order())) {
            holder.tv_order_state.setText("待支付");
            String string = "预约时间：" + obj.getTime_order();
            SpannableStringBuilder ssb = new SpannableStringBuilder(string);
            ssb.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), 5, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            //holder.tv_order_time.setText("预约时间："+obj.getTime_order());
            holder.tv_order_time.setText(ssb);
            holder.iv_order_mashang.setImageResource(R.drawable.mashangzhifu);
            holder.iv_order_wancheng.setVisibility(View.GONE);
            //添加支付点击功能
            holder.iv_order_mashang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.Many()) {
                        return;
                    }
                    //LogUtil.showShortToast(mContext,"立即支付");
                    callback.payOrder(obj.getNo_order(), obj.getType_pay(), obj.getPrice_order());
                }
            });
        } else if ("B".equals(obj.getState_order())) {
            holder.tv_order_state.setText("待安装");
            String string = "预约时间：" + obj.getTime_order();
            SpannableStringBuilder ssb = new SpannableStringBuilder(string);
            ssb.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), 5, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tv_order_time.setText(ssb);
            //holder.tv_order_time.setText("预约时间："+obj.getTime_order());
            holder.iv_order_mashang.setImageResource(R.drawable.dingdanxiangqing);
            holder.iv_order_wancheng.setVisibility(View.GONE);
        } else if ("C".equals(obj.getState_order())) {
            holder.tv_order_state.setText("待确认");
            String string = "安装时间：" + obj.getTime_service();
            SpannableStringBuilder ssb = new SpannableStringBuilder(string);
            ssb.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), 5, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tv_order_time.setText(ssb);
            //holder.tv_order_time.setText("安装时间："+obj.getTime_service());
            holder.iv_order_mashang.setImageResource(R.drawable.querenwancheng);
            holder.iv_order_wancheng.setVisibility(View.GONE);
        } else if ("D".equals(obj.getState_order())) {
            holder.tv_order_state.setText("待评价");
            //holder.tv_order_time.setText("确认时间："+obj.getTime_end());
            String string = "确认时间：" + obj.getTime_end();
            SpannableStringBuilder ssb = new SpannableStringBuilder(string);
            ssb.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), 5, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tv_order_time.setText(ssb);
            holder.iv_order_mashang.setImageResource(R.drawable.mashangpingjia);
            holder.iv_order_wancheng.setVisibility(View.VISIBLE);
        } else if ("E".equals(obj.getState_order())) {
            holder.tv_order_state.setText("已评价");
            //holder.tv_order_time.setText("确认时间："+obj.getTime_end());
            String string = "确认时间：" + obj.getTime_end();
            SpannableStringBuilder ssb = new SpannableStringBuilder(string);
            ssb.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), 5, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tv_order_time.setText(ssb);

            holder.iv_order_mashang.setImageResource(R.drawable.dingdanxiangqing);
            holder.iv_order_wancheng.setVisibility(View.VISIBLE);
        } else if ("F".equals(obj.getState_order()) || "G".equals(obj.getState_order())) {
            holder.tv_order_state.setText("申请取消");
            //holder.tv_order_time.setText("预约时间："+obj.getTime_order());
            String string = "预约时间：" + obj.getTime_order();
            SpannableStringBuilder ssb = new SpannableStringBuilder(string);
            ssb.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), 5, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tv_order_time.setText(ssb);

            holder.iv_order_mashang.setImageResource(R.drawable.dingdanxiangqing);
            holder.iv_order_wancheng.setVisibility(View.GONE);
        } else if ("Z".equals(obj.getState_order())) {
            holder.tv_order_state.setText("返修处理中");
            //holder.tv_order_time.setText("预约时间："+obj.getTime_order());
            String string = "预约时间：" + obj.getTime_order();
            SpannableStringBuilder ssb = new SpannableStringBuilder(string);
            ssb.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), 5, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tv_order_time.setText(ssb);

            holder.iv_order_mashang.setImageResource(R.drawable.dingdanxiangqing);
            holder.iv_order_wancheng.setVisibility(View.GONE);
        }
        //非全部订单状态下，隐藏订单状态位
        if (KaKuApplication.state_order != "") {
            holder.tv_order_state.setWidth(0);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.iv_order_daoche.setLayoutParams(params);

        }

        holder.iv_order_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.Many()){
                    return;
                }
                Call(obj.getMobile_shop());
            }
        });

        return convertView;
    }


    class ViewHolder {

        private ImageView iv_order_daoche;
        private ImageView iv_order_image;
        private ImageView iv_order_call;
        private ImageView iv_order_mashang;
        private ImageView iv_order_wancheng;
        private TextView tv_order_shop;
        private TextView tv_order_time;
        private TextView tv_order_name;
        private TextView tv_order_num;
        private TextView tv_order_state;
        private TextView tv_order_price;

    }

    public void Call(final String phone) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示");
        builder.setMessage("确认拨打:" + phone + "？");
        builder.setNegativeButton("是", new android.content.DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                mContext.startActivity(intent);
            }
        });

        builder.setPositiveButton("否", new android.content.DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    //设置支付回调
    public void setCallback(CallBack callback) {
        this.callback = callback;
    }

    private CallBack callback;

    public interface CallBack {

        void payOrder(String no_order, String flag_pay, String price_order);

    }

}