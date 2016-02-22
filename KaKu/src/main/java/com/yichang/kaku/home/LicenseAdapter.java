package com.yichang.kaku.home;

import android.content.Context;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseAdapter;
import com.yichang.kaku.response.IllegalResp;

import java.util.List;

/**
 * Created by xiaosu on 2015/11/25.
 */
public class LicenseAdapter extends BaseAdapter<IllegalResp.Cities> {

    public LicenseAdapter(List<IllegalResp.Cities> datas) {
        super(datas);
    }

    @Override
    protected void getView(ViewHolder holder, IllegalResp.Cities item, int position, Context context) {
        TextView tv_city = holder.getView(R.id.tv_item_city);
        tv_city.setText(item.CarNumberPrefix);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_city;
    }
}
