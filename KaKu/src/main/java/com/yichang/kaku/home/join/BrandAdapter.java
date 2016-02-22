package com.yichang.kaku.home.join;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.response.BrandListResp;
import com.yichang.kaku.tools.BitmapUtil;

import java.util.List;

/**
 * Created by xiaosu on 2015/11/12.
 */
public class BrandAdapter extends BaseAdapter {

    public BrandAdapter(List<BrandListResp.ItemEntity> list) {
        this.list = list;
    }

    List<BrandListResp.ItemEntity> list;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public BrandListResp.ItemEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.layout_brand_item, null);
        }

        ImageView iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);

        BitmapUtil.getInstance(parent.getContext()).download(iv_pic, "http://kaku.wekaku.com/index.php?m=Img&a=imgAction&img=" + getItem(position).image_brand);
        tv_name.setText(getItem(position).name_brand);

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                AbsListView.LayoutParams.MATCH_PARENT);
        convertView.setLayoutParams(params);

        return convertView;
    }

    public void notifyDataSetChanged(List<BrandListResp.ItemEntity> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}
