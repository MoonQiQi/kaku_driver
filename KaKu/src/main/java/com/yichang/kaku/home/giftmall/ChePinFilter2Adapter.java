package com.yichang.kaku.home.giftmall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.ChePinAdapter2Obj;

import java.util.List;

public class ChePinFilter2Adapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ChePinAdapter2Obj> list;
    private Context mContext;

    public ChePinFilter2Adapter(Context context, List<ChePinAdapter2Obj> list) {
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
        ChePinAdapter2Obj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_chepinadapter2, null);
            holder.tv_chepinfilter2_name = (TextView) convertView.findViewById(R.id.tv_chepinfilter2_name);
            holder.rela_chepinfilter2 = (RelativeLayout) convertView.findViewById(R.id.rela_chepinfilter2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if ("R".equals(obj.getColor_type())) {
            holder.tv_chepinfilter2_name.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.rela_chepinfilter2.setBackgroundResource(R.drawable.bg_btn_red);
        } else if ("B".equals(obj.getColor_type())) {
            holder.tv_chepinfilter2_name.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.rela_chepinfilter2.setBackgroundResource(R.drawable.btn_hui);
        }

        holder.tv_chepinfilter2_name.setText(obj.getName_type());

        return convertView;
    }

    class ViewHolder {
        private TextView tv_chepinfilter2_name;
        private RelativeLayout rela_chepinfilter2;
    }
}