package com.yichang.kaku.home;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.response.CityResp;
import com.yichang.kaku.view.PinnedSectionListView;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by xiaosu on 2015/11/24.
 * 首页-标题-搜索位置
 */
public class ChooseCityAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {

    public ChooseCityAdapter(LinkedList<CityResp.ListEntity.InnerListEntity> cities) {
        this.cities = cities;
    }

    LinkedList<CityResp.ListEntity.InnerListEntity> cities;
    Map<String, Integer> letterMap;

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public CityResp.ListEntity.InnerListEntity getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = new TextView(parent.getContext());
        }
        AbsListView.LayoutParams params = null;
        TextView textView = (TextView) convertView;
        switch (getItemViewType(position)) {
            case 0:
                textView.setBackgroundColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setTextColor(Color.BLACK);
                params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, parent.getContext().getResources().getDimensionPixelOffset(R.dimen.x80));
                break;
            case 1:
                textView.setTextColor(Color.parseColor("#999999"));
                textView.setBackgroundColor(Color.parseColor("#F0EFEF"));
                textView.setGravity(Gravity.CENTER_VERTICAL);
                params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, parent.getContext().getResources().getDimensionPixelOffset(R.dimen.x40));
                break;
            case 2:
                textView.setBackgroundColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setTextColor(Color.BLACK);
                params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, parent.getContext().getResources().getDimensionPixelOffset(R.dimen.x80));
                break;
            case 3:
                textView.setTextColor(Color.parseColor("#999999"));
                textView.setBackgroundColor(Color.parseColor("#F0EFEF"));
                textView.setGravity(Gravity.CENTER_VERTICAL);
                params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, parent.getContext().getResources().getDimensionPixelOffset(R.dimen.x40));
                break;
        }
        textView.setPadding(parent.getContext().getResources().getDimensionPixelOffset(R.dimen.x30), 0, 0, 0);
        textView.setLayoutParams(params);

        textView.setText(getItem(position).name_area);

        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == 1;
    }
}
