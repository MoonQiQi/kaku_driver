package com.yichang.kaku.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.response.IllegalResp;

import java.util.List;

public class LicenseChoosePop extends PopupWindow {
    private final InnerAdapter adapter;
    private List<IllegalResp.Cities> cities;
    private final int itemHeight;

    public LicenseChoosePop(BaseActivity paramBaseActivity, List<IllegalResp.Cities> paramList, AdapterView.OnItemClickListener paramOnItemClickListener) {
        super(paramBaseActivity);
        this.cities = paramList;
        setBackgroundDrawable(new ColorDrawable(-1));
        setOutsideTouchable(true);
        setFocusable(true);
        ListView localListView = new ListView(paramBaseActivity);
        localListView.setDivider(new ColorDrawable(Color.parseColor("#999999")));
        localListView.setDividerHeight(paramBaseActivity.getResources().getDimensionPixelOffset(R.dimen.x1));
        setContentView(localListView);
        this.adapter = new InnerAdapter();
        localListView.setAdapter(this.adapter);
        localListView.setOnItemClickListener(paramOnItemClickListener);
        this.itemHeight = paramBaseActivity.getResources().getDimensionPixelOffset(R.dimen.x80);
    }

    public void updateData(List<IllegalResp.Cities> paramList) {
        this.cities = paramList;
        this.adapter.notifyDataSetChanged();
    }

    public void updateHeight() {
        if (this.cities.size() < 7) {
            setHeight(this.itemHeight * this.cities.size());
            return;
        }
        setHeight((int) (6.5D * this.itemHeight));
    }

    class InnerAdapter extends BaseAdapter {
        InnerAdapter() {
        }

        public int getCount() {
            return LicenseChoosePop.this.cities.size();
        }

        public IllegalResp.Cities getItem(int paramInt) {
            return (IllegalResp.Cities) LicenseChoosePop.this.cities.get(paramInt);
        }

        public long getItemId(int paramInt) {
            return 0L;
        }

        public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
            if (paramView == null) {
                paramView = new TextView(paramViewGroup.getContext());
                paramView.setLayoutParams(new AbsListView.LayoutParams(-1, itemHeight));
                paramView.setBackgroundColor(-1);
            }
            TextView localTextView = (TextView) paramView;
            localTextView.setTextColor(Color.BLACK);
            localTextView.setGravity(17);
            localTextView.setText(getItem(paramInt).CarNumberPrefix);
            return paramView;
        }
    }
}