package com.yichang.kaku.home;

import android.content.Context;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseAdapter;
import com.yichang.kaku.response.IllegalResp;

import java.util.List;

public class ProvinceAdapter extends BaseAdapter<IllegalResp.Province> {
    public ProvinceAdapter(List<IllegalResp.Province> paramList) {
        super(paramList);
    }

    public int getLayoutId() {
        return R.layout.item_city;
    }

    protected void getView(BaseAdapter.ViewHolder paramViewHolder, IllegalResp.Province paramProvince, int paramInt, Context paramContext) {
        ((TextView) paramViewHolder.getView(R.id.tv_item_city)).setText(paramProvince.ProvinceName);
    }
}