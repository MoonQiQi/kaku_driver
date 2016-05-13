package com.yichang.kaku.home.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.ShopServiceObj;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

public class ShopDetailOneAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ShopServiceObj> list;
    private Context mContext;
    private int p;

    public ShopDetailOneAdapter(Context context, List<ShopServiceObj> list, int p) {
        // TODO Auto-generated constructor stub
        this.list = list;
        this.mContext = context;
        this.p = p;
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

        final ViewHolder holder;
        ShopServiceObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_shopone, null);
            holder.iv_shop1_image = (ImageView) convertView.findViewById(R.id.iv_shop1_image);
            holder.line_shop1 = (LinearLayout) convertView.findViewById(R.id.line_shop1);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == p) {
            holder.line_shop1.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.line_shop1.setBackgroundColor(mContext.getResources().getColor(R.color.bg_color));
        }
        if (!"".equals(obj.getImage_service())) {
            BitmapUtil.getInstance(mContext).download(holder.iv_shop1_image, KaKuApplication.qian_zhui + obj.getImage_service());
        }

        return convertView;
    }

    class ViewHolder {

        ImageView iv_shop1_image;
        LinearLayout line_shop1;
    }
}