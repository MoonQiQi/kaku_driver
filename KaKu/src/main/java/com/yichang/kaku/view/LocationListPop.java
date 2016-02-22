package com.yichang.kaku.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.yichang.kaku.global.BaseActivity;

import java.util.List;

/**
 * Created by xiaosu on 2015/11/11.
 * poi搜索的popwidow
 */
public class LocationListPop extends PopupWindow {

    private List<PoiInfo> allPoi;
    private int itemH;
    private InnerAddapter innerAddapter;
    private ListView listView;

    public LocationListPop(BaseActivity context, AdapterView.OnItemClickListener listener) {
        super(context);
        listView = new ListView(context);
        listView.setDivider(new ColorDrawable(Color.parseColor("#999999")));
        listView.setDividerHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.7f, context.getResources().getDisplayMetrics()));
        listView.setOnItemClickListener(listener);

        itemH = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, context.getResources().getDisplayMetrics());
        setOutsideTouchable(true);
        setFocusable(true);
        setContentView(listView);
        setTouchable(true);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, context.getResources().getDisplayMetrics()));

        setBackgroundDrawable(new ColorDrawable(Color.WHITE));
    }

    class InnerAddapter extends BaseAdapter {

        @Override
        public int getCount() {
            return allPoi.size();
        }

        @Override
        public PoiInfo getItem(int position) {
            return allPoi.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = new TextView(parent.getContext());
            }

            TextView textView = (TextView) convertView;
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            textView.setText(getItem(position).name);
            textView.setBackground(new ColorDrawable(Color.WHITE));
            textView.setTextColor(Color.BLACK);
            textView.setGravity(Gravity.CENTER);

            AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                    itemH);
            textView.setLayoutParams(params);

            return convertView;
        }
    }

    public void update(List<PoiInfo> allPoi) {
        this.allPoi = allPoi;
        listView.post(new Runnable() {
            @Override
            public void run() {
                listView.setSelection(ListView.FOCUS_DOWN);
            }
        });
        innerAddapter.notifyDataSetChanged();
    }

    public void setAllPoi(List<PoiInfo> allPoi) {
        this.allPoi = allPoi;
        innerAddapter = new InnerAddapter();
        listView.setAdapter(innerAddapter);
    }

}
