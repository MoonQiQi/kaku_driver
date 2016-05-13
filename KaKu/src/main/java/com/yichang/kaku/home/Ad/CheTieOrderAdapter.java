package com.yichang.kaku.home.ad;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.CheTieOrderObj;

import java.util.List;

public class CheTieOrderAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<CheTieOrderObj> list;
    private Context mContext;

    public CheTieOrderAdapter(Context context, List<CheTieOrderObj> list) {
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
        final CheTieOrderObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_wodechetie, null);
            holder.iv_wodechetieitem_image = (ImageView) convertView.findViewById(R.id.iv_wodechetieitem_image);
            holder.iv_wodechetieitem_zhifu = (ImageView) convertView.findViewById(R.id.iv_wodechetieitem_zhifu);
            holder.iv_wodechetieitem_wancheng = (ImageView) convertView.findViewById(R.id.iv_wodechetieitem_wancheng);
            holder.tv_wodechetieitem_time = (TextView) convertView.findViewById(R.id.tv_wodechetieitem_time);
            holder.tv_wodechetieitem_status = (TextView) convertView.findViewById(R.id.tv_wodechetieitem_status);
            holder.tv_wodechetieitem_name = (TextView) convertView.findViewById(R.id.tv_wodechetieitem_name);
            holder.tv_wodechetieitem_yuqishouyi = (TextView) convertView.findViewById(R.id.tv_wodechetieitem_yuqishouyi);
            holder.tv_wodechetieitem_num = (TextView) convertView.findViewById(R.id.tv_wodechetieitem_num);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.iv_wodechetieitem_image.setImageURI(Uri.parse(KaKuApplication.qian_zhui + obj.getImage_advert()));
        holder.tv_wodechetieitem_name.setText(obj.getName_advert());
        holder.tv_wodechetieitem_num.setText("x " + obj.getNum_advert());
        holder.tv_wodechetieitem_time.setText("下单时间：" + obj.getTime_create());

        String string_shouyi = "实付款：¥ " + obj.getPrice_bill();
        SpannableStringBuilder style2 = new SpannableStringBuilder(string_shouyi);
        style2.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), 4, string_shouyi.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tv_wodechetieitem_yuqishouyi.setText(style2);


        if (TextUtils.equals(obj.getState_bill(), "A")) {
            if ("Y".equals(obj.getFlag_operate())){
                holder.iv_wodechetieitem_zhifu.setVisibility(View.VISIBLE);
            } else {
                holder.iv_wodechetieitem_zhifu.setVisibility(View.GONE);
            }
            holder.tv_wodechetieitem_status.setText("待付款");
            holder.iv_wodechetieitem_zhifu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.payOrder(obj.getNo_bill(), obj.getPrice_bill());
                }
            });
            holder.iv_wodechetieitem_wancheng.setVisibility(View.GONE);
        } else if (TextUtils.equals(obj.getState_bill(), "B")) {
            holder.tv_wodechetieitem_status.setText("待发货");
            holder.iv_wodechetieitem_zhifu.setVisibility(View.GONE);
            holder.iv_wodechetieitem_wancheng.setVisibility(View.GONE);
        } else if (TextUtils.equals(obj.getState_bill(), "E")) {
            holder.tv_wodechetieitem_status.setText("已完成");
            holder.iv_wodechetieitem_zhifu.setVisibility(View.GONE);
            holder.iv_wodechetieitem_wancheng.setVisibility(View.GONE);
        } else if (TextUtils.equals(obj.getState_bill(), "D")) {
            holder.tv_wodechetieitem_status.setText("待确认");
            holder.iv_wodechetieitem_zhifu.setVisibility(View.GONE);
            holder.iv_wodechetieitem_wancheng.setVisibility(View.GONE);
        } else if (TextUtils.equals(obj.getState_bill(), "G")) {
            holder.tv_wodechetieitem_status.setText("已取消");
            holder.iv_wodechetieitem_zhifu.setVisibility(View.GONE);
            holder.iv_wodechetieitem_wancheng.setVisibility(View.GONE);
        }

        return convertView;
    }

    //设置支付回调
    public void setCallback(CallBack callback) {
        this.callback = callback;
    }

    private CallBack callback;

    public interface CallBack {

        void payOrder(String no_bill, String price_bill);

    }

    class ViewHolder {

        private ImageView iv_wodechetieitem_image;
        private ImageView iv_wodechetieitem_zhifu;
        private ImageView iv_wodechetieitem_wancheng;
        private TextView tv_wodechetieitem_time;
        private TextView tv_wodechetieitem_status;
        private TextView tv_wodechetieitem_name;
        private TextView tv_wodechetieitem_yuqishouyi;
        private TextView tv_wodechetieitem_num;

    }
}