package com.yichang.kaku.home;

import android.content.Context;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseAdapter;
import com.yichang.kaku.response.IllegalResp;

import java.util.List;

public class CitiesAdapter extends BaseAdapter<IllegalResp.Cities> {

    public CitiesAdapter(List<IllegalResp.Cities> paramList) {
        super(paramList);
    }

    public int getLayoutId() {
        return R.layout.item_city;
    }

    protected void getView(BaseAdapter.ViewHolder paramViewHolder, IllegalResp.Cities paramCities, int paramInt, Context paramContext) {
        ((TextView) paramViewHolder.getView(R.id.tv_item_city)).setText(paramCities.CityName);
    }
}