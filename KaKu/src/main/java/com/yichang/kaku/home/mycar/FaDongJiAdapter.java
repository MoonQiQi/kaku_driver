package com.yichang.kaku.home.mycar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.CarData2Obj;

import java.util.List;

public class FaDongJiAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<CarData2Obj> list;
    private Context mContext;

    public FaDongJiAdapter(Context context, List<CarData2Obj> list) {
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
        CarData2Obj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_step2, null);

            holder.tv_item_fadongji = (TextView) convertView.findViewById(R.id.tv_item_fadongji);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if ("R".equals(obj.getColor_type())) {
            holder.tv_item_fadongji.setBackgroundResource(R.drawable.baidi);
            holder.tv_item_fadongji.setTextColor(mContext.getResources().getColor(R.color.color_red));
        } else {
            holder.tv_item_fadongji.setBackgroundResource(R.drawable.huidi);
            holder.tv_item_fadongji.setTextColor(mContext.getResources().getColor(R.color.black));
        }

        holder.tv_item_fadongji.setText(obj.getName_data());

        return convertView;
    }

    class ViewHolder {

        TextView tv_item_fadongji;
    }
}