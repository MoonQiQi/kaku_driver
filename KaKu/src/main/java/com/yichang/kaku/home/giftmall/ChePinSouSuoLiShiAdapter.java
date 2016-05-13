package com.yichang.kaku.home.giftmall;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChePinSouSuoLiShiAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<String> list;
    private Context mContext;
    private String save_Str;
    private List<String> list_string;
    private StringBuilder sb;

    public ChePinSouSuoLiShiAdapter(Context context, List<String> list) {
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
        String obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_sousuolishi, null);
            holder.iv_sslishi_cha = (ImageView) convertView.findViewById(R.id.iv_sslishi_cha);
            holder.tv_sslishi_jilu = (TextView) convertView.findViewById(R.id.tv_sslishi_jilu);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_sslishi_jilu.setText(obj);
        holder.iv_sslishi_cha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
                SharedPreferences sp = mContext.getSharedPreferences("history_strs_chepin", 0);
                save_Str = sp.getString("history_chepin", "");
                list_string = Arrays.asList(save_Str.split(","));
                List arrayList = new ArrayList(list_string);
                arrayList.remove(position);
                sb = new StringBuilder();
                for (int i = 0; i < arrayList.size(); i++) {
                    sb.append(arrayList.get(i) + "");
                }

                sp.edit().putString("history_chepin", sb.toString()).commit();
            }
        });

        return convertView;
    }

    class ViewHolder {

        private ImageView iv_sslishi_cha;
        private TextView tv_sslishi_jilu;

    }
}