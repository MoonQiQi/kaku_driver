package com.yichang.kaku.home.faxian;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.DiscoveryItemObj;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

public class DiscoveryAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<DiscoveryItemObj> list;
    private Context mContext;

    public DiscoveryAdapter(Context context, List<DiscoveryItemObj> list) {
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
        // TODO Auto-generated method stub
        final ViewHolder holder;
        DiscoveryItemObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_discovery, null);
            holder.tv_discovery_name = (TextView) convertView
                    .findViewById(R.id.tv_discovery_name);
            holder.tv_discovery_yuedu = (TextView) convertView
                    .findViewById(R.id.tv_discovery_yuedu);
            holder.tv_discovery_pinglun = (TextView) convertView
                    .findViewById(R.id.tv_discovery_pinglun);
            holder.iv_discovery_image = (ImageView) convertView
                    .findViewById(R.id.iv_discovery_image);
            holder.tv_discovery_dianzan = (TextView) convertView
                    .findViewById(R.id.tv_discovery_dianzan);
            holder.tv_discovery_time = (TextView) convertView
                    .findViewById(R.id.tv_discovery_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_discovery_name.setText(list.get(position).getName_news());
        holder.tv_discovery_yuedu.setText(list.get(position).getNum_read());
        holder.tv_discovery_pinglun.setText(list.get(position).getNum_comments());
        holder.tv_discovery_dianzan.setText(list.get(position).getNum_collection());
        holder.tv_discovery_time.setText(list.get(position).getTime_pub());
        BitmapUtil.getInstance(mContext).download(holder.iv_discovery_image, KaKuApplication.qian_zhui + list.get(position).getThumbnail_news());

        return convertView;
    }

    class ViewHolder {
        private TextView tv_discovery_name;
        private TextView tv_discovery_yuedu;
        private TextView tv_discovery_pinglun;
        private TextView tv_discovery_dianzan;
        private TextView tv_discovery_time;
        private ImageView iv_discovery_image;
    }

}