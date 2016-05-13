package com.yichang.kaku.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.ShopMallProductObj;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

public class HomeShopMallAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<ShopMallProductObj> list;
    private Context mContext;
    private int anInt;

    public HomeShopMallAdapter(Context context, List<ShopMallProductObj> list, int anInt) {
        // TODO Auto-generated constructor stub
        this.list = list;
        this.anInt = anInt;
        this.mContext = context;
        if (mInflater == null) {
            mInflater = LayoutInflater.from(context);
        }
    }

    @Override
    public int getCount() {
        return list != null && !list.isEmpty() ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        ShopMallProductObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_shopmall_products, null);
            holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_shopmall_item_img);

            RelativeLayout.LayoutParams rela_params = new RelativeLayout.LayoutParams(anInt, anInt);

            holder.iv_img.setLayoutParams(rela_params);

            holder.iv_add = (ImageView) convertView.findViewById(R.id.iv_shopmall_add);
            holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_shopmall_desc2);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price2);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //holder.iv_img.setImageURI(Uri.parse(KaKuApplication.qian_zhui + obj.getImage_goods()));
        BitmapUtil.getInstance(mContext).download(holder.iv_img, KaKuApplication.qian_zhui + obj.getImage_goods());
        holder.iv_add.setVisibility(View.GONE);
        holder.tv_desc.setText(obj.getName_goods());
        holder.tv_price.setText(obj.getPrice_goods_buy());

        return convertView;
    }

    class ViewHolder {
        private ImageView iv_img;
        private TextView tv_desc;
        private TextView tv_price;
        private ImageView iv_add;
    }
}