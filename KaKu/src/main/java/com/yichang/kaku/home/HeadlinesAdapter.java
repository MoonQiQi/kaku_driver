package com.yichang.kaku.home;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.obj.NewsObj;
import com.yichang.kaku.view.wheelview.adapter.AbstractWheelAdapter;

import java.util.List;

/**
 * Created by xiaosu on 2015/12/10.
 */
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

            t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            Drawable drawable = parent.getContext().getResources().getDrawable(R.drawable.redu);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            t.setCompoundDrawables(drawable, null, null, null);
            t.setCompoundDrawablePadding(10);
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
