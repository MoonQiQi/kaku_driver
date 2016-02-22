package com.yichang.kaku.member.truckorder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.ConfirmOrderProductObj;
import com.yichang.kaku.obj.TruckOrderObj;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class TruckOrderAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<TruckOrderObj> list;
    private Context mContext;

    public TruckOrderAdapter(Context context, List<TruckOrderObj> list) {
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
        final TruckOrderObj obj = list.get(position);

        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_truck_orderlist, null);

            holder.tv_truck_nobill = (TextView) convertView.findViewById(R.id.tv_truck_shopname);
            holder.tv_truck_state = (TextView) convertView.findViewById(R.id.tv_truck_state);
            holder.lv_truck_item = (ListView) convertView.findViewById(R.id.lv_truck_item);

            holder.iv_truck_finish = (ImageView) convertView.findViewById(R.id.iv_truck_finish);
            holder.tv_truck_bill = (TextView) convertView.findViewById(R.id.tv_truck_bill);
            holder.iv_truck_button = (SimpleDraweeView) convertView.findViewById(R.id.iv_truck_button);


            holder.view_lv = convertView.findViewById(R.id.view_lv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//      todo 暂不处理，均为  卡库车品商城
//        holder.tv_truck_shopname.setText();
        holder.tv_truck_nobill.setText("订单号：" + obj.getNo_bill());

        //订单中商品条目列表
        holder.lv_truck_item.setVisibility(View.VISIBLE);
        holder.view_lv.setVisibility(View.VISIBLE);
        holder.lv_truck_item.setAdapter(new ShopCartAdapter(obj.getShopcar(), obj));
//        设置子listView中的item不可点击，使得父控件处理点击事件
        holder.lv_truck_item.setClickable(false);
        holder.lv_truck_item.setEnabled(false);
        holder.lv_truck_item.setPressed(false);

        holder.setListViewHeightBasedOnChildren(holder.lv_truck_item);

        holder.tv_truck_bill.setText("实付款：￥" + obj.getPrice_bill());
        holder.iv_truck_button.setVisibility(View.VISIBLE);
        switch (obj.getState_bill()) {
            case "A":
                holder.tv_truck_state.setText("待支付");
                holder.iv_truck_finish.setVisibility(View.GONE);
                holder.iv_truck_button.setImageResource(R.drawable.mashangzhifu);
                holder.iv_truck_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        callback.payOrder(obj.getNo_bill(), obj.getType_pay(), obj.getPrice_bill());
                    }
                });
                break;
            case "B":
                holder.tv_truck_state.setText("待发货");
                holder.iv_truck_finish.setVisibility(View.GONE);
                holder.iv_truck_button.setImageResource(R.drawable.dingdanxiangqing);
                holder.iv_truck_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        gotoOrderDetail(obj);

                    }
                });
                break;
            case "C":
                holder.tv_truck_state.setText("物流中");
                holder.iv_truck_finish.setVisibility(View.GONE);
                holder.iv_truck_button.setImageResource(R.drawable.dingdanxiangqing);
                holder.iv_truck_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        gotoOrderDetail(obj);

                    }
                });
                break;
            case "D":
                holder.tv_truck_state.setText("待收货");
                holder.iv_truck_finish.setVisibility(View.GONE);
                holder.iv_truck_button.setImageResource(R.drawable.dingdanxiangqing);
                holder.iv_truck_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        gotoOrderDetail(obj);

                    }
                });
                break;
            case "E":
                holder.tv_truck_state.setText("待评价");
                holder.iv_truck_finish.setVisibility(View.GONE);
                holder.iv_truck_button.setImageResource(R.drawable.mashangpingjia);
                holder.iv_truck_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        gotoOrderDetail(obj);

                    }
                });
                break;
            case "F":
                holder.tv_truck_state.setText("已评价");
                holder.iv_truck_finish.setVisibility(View.VISIBLE);
                holder.iv_truck_button.setImageResource(R.drawable.dingdanxiangqing);
                holder.iv_truck_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        gotoOrderDetail(obj);

                    }
                });
                break;
            case "G":
                holder.tv_truck_state.setText("已取消");
                holder.iv_truck_finish.setVisibility(View.GONE);
                holder.iv_truck_button.setImageResource(R.drawable.dingdanxiangqing);
                holder.iv_truck_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        gotoOrderDetail(obj);
                    }
                });
                break;
            case "H":
                holder.tv_truck_state.setText("取消申请中");
                holder.iv_truck_finish.setVisibility(View.GONE);
                holder.iv_truck_button.setImageResource(R.drawable.dingdanxiangqing);
                holder.iv_truck_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        gotoOrderDetail(obj);

                    }
                });
                break;
            case "Z":
                holder.tv_truck_state.setText("退货申请中");
                holder.iv_truck_finish.setVisibility(View.GONE);
                holder.iv_truck_button.setImageResource(R.drawable.dingdanxiangqing);
                holder.iv_truck_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        gotoOrderDetail(obj);

                    }
                });
                break;

            case "I":
                holder.tv_truck_state.setText("已退货");
                holder.iv_truck_finish.setVisibility(View.GONE);
                holder.iv_truck_button.setImageResource(R.drawable.dingdanxiangqing);
                holder.iv_truck_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        gotoOrderDetail(obj);

                    }
                });
                break;

        }
        if (KaKuApplication.truck_order_state != "") {
            holder.tv_truck_state.setVisibility(View.GONE);

        }

        return convertView;
    }

    private void gotoOrderDetail(TruckOrderObj obj) {
        Intent intent = new Intent(mContext, TruckOrderDetailActivity.class);
                        /*todo 修改idbill为nobill*/
        LogUtil.E("orderDetail:" + "id_bill:" + obj.getNo_bill() + ":" + obj.getId_bill());
        intent.putExtra("idbill", obj.getId_bill());
        mContext.startActivity(intent);
    }

    //设置支付回调
    public void setCallback(CallBack callback) {
        this.callback = callback;
    }

    private CallBack callback;

    public interface CallBack {

        void payOrder(String no_bill, String flag_pay, String price_bill);

    }

    class ViewHolder {

        private TextView tv_truck_nobill;
        private TextView tv_truck_state;
//单条数据显示一条listview

        private ListView lv_truck_item;
        private View view_lv;


        private ImageView iv_truck_finish;
        private TextView tv_truck_bill;
        private SimpleDraweeView iv_truck_button;

        public void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return;
            }

            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }

    class ShopCartAdapter extends BaseAdapter {
        private List<ConfirmOrderProductObj> list = new ArrayList<>();
        private List<List<ConfirmOrderProductObj>> ObjList = new ArrayList<>();
        private boolean isListView = true;
        private TruckOrderObj truckOrderObj;

        ShopCartAdapter(List<ConfirmOrderProductObj> list, TruckOrderObj obj) {
            this.list = list;
            truckOrderObj = obj;
            if (list.size() != 1) {
                isListView = false;
            }
            ObjList.add(list);
        }

        @Override
        public int getCount() {
            return ObjList != null && !ObjList.isEmpty() ? ObjList.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return ObjList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            myViewHolder holder;
            myGridViewHolder holder_gv;
            final List<ConfirmOrderProductObj> listObj = ObjList.get(position);
            if (listObj == null) {
                return convertView;
            }

            if (isListView) {
                if (convertView == null) {
                    holder = new myViewHolder();

                    convertView = mInflater.inflate(R.layout.item_truck_shopcartlist, null);

                    holder.iv_truck_image = (ImageView) convertView.findViewById(R.id.iv_truck_image);
                    holder.tv_truck_name = (TextView) convertView.findViewById(R.id.tv_truck_name);

                    convertView.setTag(holder);
                } else {
                    holder = (myViewHolder) convertView.getTag();
                }
                BitmapUtil.getInstance(mContext).download(holder.iv_truck_image, KaKuApplication.qian_zhui + listObj.get(0).getImage_goods());
                holder.tv_truck_name.setText(listObj.get(0).getName_goods());

            } else {
                if (convertView == null) {
                    holder_gv = new myGridViewHolder();

                    convertView = mInflater.inflate(R.layout.item_truck_shopcartlist_gv, null);
                    //holder_gv.scroll_view = (KaKuHorizontalView) convertView.findViewById(R.id.scroll_view);
                    holder_gv.ll_gv = (LinearLayout) convertView.findViewById(R.id.ll_gv);
                    convertView.setTag(holder_gv);
                } else {
                    holder_gv = (myGridViewHolder) convertView.getTag();
                }

                holder_gv.ll_gv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()) {
                            return;
                        }
                        gotoOrderDetail(truckOrderObj);
                    }
                });
                for (int i = 0; i < ObjList.get(0).size(); i++) {


                    SimpleDraweeView img = new SimpleDraweeView(mContext);

                    //img.setImageResource(R.drawable.ic_launcher);
                    //BitmapUtil.getInstance(mContext).download(img, KaKuApplication.qian_zhui + listObj.get(i).getImage_goods());
                    img.setImageURI(Uri.parse(KaKuApplication.qian_zhui + listObj.get(i).getImage_goods()));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dip2px(mContext, 60), dip2px(mContext, 60));
                    if (i == ObjList.get(0).size() - 1) {
                        params.setMargins(dip2px(mContext, 15), dip2px(mContext, 15), dip2px(mContext, 15), dip2px(mContext, 15));
                    } else {
                        params.setMargins(dip2px(mContext, 15), dip2px(mContext, 15), 0, dip2px(mContext, 15));
                    }

                    img.setLayoutParams(params);

                    holder_gv.ll_gv.addView(img);
                }


            }
            return convertView;
        }


        class myViewHolder {
            private ImageView iv_truck_image;
            private TextView tv_truck_name;
        }

        class myGridViewHolder {
            private LinearLayout ll_gv;
            /*private KaKuHorizontalView scroll_view;
            private ImageView iv_truck_image1;
            private ImageView iv_truck_image2;
            private ImageView iv_truck_image3;
            private ImageView iv_truck_image4;*/

        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

