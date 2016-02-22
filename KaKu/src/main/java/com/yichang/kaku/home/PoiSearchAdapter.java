package com.yichang.kaku.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.yichang.kaku.R;

import java.util.List;

/**
 * 所在位置适配器
 * 
 * @author yxx
 * 
 */
public class PoiSearchAdapter extends BaseAdapter {

	private Context context;
	private List<PoiInfo> list;
	private ViewHolder holder;
	
	public PoiSearchAdapter(Context context,List<PoiInfo> appGroup) {
		this.context = context;
		this.list = appGroup;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int location) {
		return list.get(location);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public void addObject(List<PoiInfo> mAppGroup) {
		this.list = mAppGroup;
		notifyDataSetChanged();
	}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        if (convertView == null) {
        	holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.activity_poi_search_item, null);
            holder.mpoi_name = (TextView) convertView
                    .findViewById(R.id.mpoiNameT);
            holder.mpoi_address = (TextView) convertView
                    .findViewById(R.id.mpoiAddressT);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mpoi_name.setText(list.get(position).name);
        holder.mpoi_address.setText(list.get(position).address);
//        Log.i("yxx", "==1=poi===城市："+poiInfo.city+"名字："+poiInfo.name+"地址："+poiInfo.address);
        return convertView;
    }

    public class ViewHolder {
        public TextView mpoi_name;// 名称
        public TextView mpoi_address;// 地址

    }

}
