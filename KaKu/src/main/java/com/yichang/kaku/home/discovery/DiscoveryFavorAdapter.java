package com.yichang.kaku.home.discovery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.DiscoveryItemObj;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.Utils;

import java.util.List;

public class DiscoveryFavorAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<DiscoveryItemObj> list;
    private Context mContext;
    /*private String flag;
    private int num_shoucang;
	private String code;
	private String str_shoucang;*/

    public DiscoveryFavorAdapter(Context context, List<DiscoveryItemObj> list) {
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
        final DiscoveryItemObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        //final String id_news = obj.getId_news();
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_discovery_favor, null);
            holder.tv_favor_name = (TextView) convertView
                    .findViewById(R.id.tv_discovery_favor_name);
            holder.tv_favor_time = (TextView) convertView
                    .findViewById(R.id.tv_discovery_favor_time);
            holder.tv_favor_content = (TextView) convertView
                    .findViewById(R.id.tv_discovery_favor_content);
            holder.iv_favor_image = (ImageView) convertView
                    .findViewById(R.id.iv_discovery_favor_image);
            holder.ll_discovery_remove = (LinearLayout) convertView.findViewById(R.id.ll_discovery_remove);
            holder.iv_discovery_remove = (ImageView) convertView.findViewById(R.id.iv_discovery_remove);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_favor_name.setText(list.get(position).getName_news());
        holder.tv_favor_time.setText(list.get(position).getTime_pub());
        holder.tv_favor_content.setText(list.get(position).getIntro_news());
        String image = KaKuApplication.qian_zhui + list.get(position).getThumbnail_news();
        BitmapUtil.getInstance(mContext).download(holder.iv_favor_image, image);
        holder.ll_discovery_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.Many()) {
                    return;
                }
                remove.removeItem(obj);
            }
        });
        if (!KaKuApplication.isShowRemoveImg_discovery) {
            holder.ll_discovery_remove.setVisibility(View.GONE);
        } else {
            holder.ll_discovery_remove.setVisibility(View.VISIBLE);
        }

        return convertView;
    }


    class ViewHolder {
        private TextView tv_favor_name;
        private TextView tv_favor_time;
        private TextView tv_favor_content;
        private ImageView iv_favor_image;
        private LinearLayout ll_discovery_remove;
        private ImageView iv_discovery_remove;
    }

    private IRemove remove;

    public void setRemove(IRemove remove) {
        this.remove = remove;
    }

    public interface IRemove {
        void removeItem(final DiscoveryItemObj obj);
    }

}