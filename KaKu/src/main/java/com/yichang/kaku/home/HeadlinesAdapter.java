package com.yichang.kaku.home;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yichang.kaku.obj.NewsObj;
import com.yichang.kaku.view.wheelview.adapter.AbstractWheelAdapter;

import java.util.List;

public class HeadlinesAdapter extends AbstractWheelAdapter {

    public HeadlinesAdapter(List<NewsObj> news) {
        this.news = news;
    }

    List<NewsObj> news;

    @Override
    public int getItemsCount() {
        return news.size();
    }

    @Override
    public View getItem(int index, View convertView, ViewGroup parent) {
        TextView t;
        if (convertView == null) {
            convertView = new TextView(parent.getContext());
            t = (TextView) convertView;

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(0, 15, 0, 15);
            t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
            t.setTextColor(Color.BLACK);
            t.setGravity(Gravity.CENTER_VERTICAL);
        } else {
            t = (TextView) convertView;
        }

        t.setText(news.get(index).getShort_news());

        return convertView;
    }

    protected void notifyDataChangedEvent(List<NewsObj> news) {
        if (this.news != news) {
            this.news.clear();
            this.news.addAll(news);
        }
        super.notifyDataChangedEvent();
    }

    public List<NewsObj> getNews() {
        return news;
    }
}
