package com.yichang.kaku.home.moneybook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.MokeyBook1Obj;
import com.yichang.kaku.tools.Utils;

import java.util.List;

public class MoneyBookAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<MokeyBook1Obj> list;
    private Context mContext;
    private int num;

    public MoneyBookAdapter(Context context, List<MokeyBook1Obj> list) {
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
        MokeyBook1Obj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_moneybook, null);
            holder.tv_mbitem_day = (TextView) convertView.findViewById(R.id.tv_mbitem_day);
            holder.lv_mbitem = (ListView) convertView.findViewById(R.id.lv_mbitem);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_mbitem_day.setText(obj.getDate_remark());
        MoneyBook2Adapter adapter = new MoneyBook2Adapter(mContext, obj.getData_list());
        holder.lv_mbitem.setAdapter(adapter);
        Utils.setListViewHeightBasedOnChildren(holder.lv_mbitem);

        return convertView;
    }

    class ViewHolder {

        private TextView tv_mbitem_day;
        private ListView lv_mbitem;

    }

}