package com.yichang.kaku.home.join;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.response.IllegalResp;

/**
 * Created by xiaosu on 2015/11/10.
 * 选择城市
 */
public class ChooseCityActivity extends BaseActivity implements ExpandableListView.OnChildClickListener {

    private ExpandableListView expandableListView;
    private InnerAdapter innerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_choose_city);
        initUI();
    }

    private void initUI() {
        ((TextView) findView(R.id.tv_mid)).setText("选择城市");
        /**结束页面*/
        findView(R.id.tv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        IllegalResp resp = getIntent().getParcelableExtra("IllegalResp");

        innerAdapter = new InnerAdapter(resp);
        expandableListView.setAdapter(innerAdapter);
        expandableListView.setOnChildClickListener(this);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        setResult(RESULT_OK, new Intent().
                putExtra("groupPosition", groupPosition).
                putExtra("childPosition", childPosition));
        finish();

        return false;
    }

    class InnerAdapter extends BaseExpandableListAdapter {

        public InnerAdapter(IllegalResp resp) {
            this.resp = resp;
        }

        IllegalResp resp;

        @Override
        public int getGroupCount() {
            return resp.Data.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return resp.Data.get(groupPosition).Cities.size();
        }

        @Override
        public IllegalResp.Province getGroup(int groupPosition) {
            return resp.Data.get(groupPosition);
        }

        @Override
        public IllegalResp.Cities getChild(int groupPosition, int childPosition) {
            return resp.Data.get(groupPosition).Cities.get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflate(android.R.layout.simple_expandable_list_item_1);
            }

            TextView textView = (TextView) convertView;
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundColor(Color.WHITE);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                    getResources().getDimensionPixelOffset(R.dimen.x88));
            textView.setLayoutParams(params);
            textView.setText(getGroup(groupPosition).ProvinceName);

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflate(android.R.layout.simple_expandable_list_item_1);
            }

            TextView textView = (TextView) convertView;
            textView.setText(getChild(groupPosition, childPosition).CityName);
            textView.setBackgroundColor(Color.parseColor("#999999"));
            textView.setTextColor(Color.BLACK);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                    getResources().getDimensionPixelOffset(R.dimen.x70));
            textView.setLayoutParams(params);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

}
