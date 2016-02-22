package com.yichang.kaku.view.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupWindow;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.home.ChooseCityAdapter;
import com.yichang.kaku.response.CityResp;
import com.yichang.kaku.tools.ViewUtils;
import com.yichang.kaku.view.PinnedSectionListView;

import java.util.LinkedList;
import java.util.Map;

public class CityChoosePopWindow extends PopupWindow implements QuickIndexBar.OnLetterChangeListener, AdapterView.OnItemClickListener {

    private LinkedList<CityResp.ListEntity.InnerListEntity> cities;
    private Map<String, Integer> positionMap;
    private PinnedSectionListView listView;

    public CityChoosePopWindow(BaseActivity context,
                               LinkedList<CityResp.ListEntity.InnerListEntity> cities,
                               Map<String, Integer> positionMap,
                               AdapterView.OnItemClickListener listener) {
        super(context);
        init(context, cities,listener);
        this.positionMap = positionMap;
        this.cities = cities;
    }

    private void init(Context context, LinkedList<CityResp.ListEntity.InnerListEntity> cities, AdapterView.OnItemClickListener listener) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.lay_city_list, null);

        listView = ViewUtils.findView(rootView, R.id.pinnedSectionListView);
        QuickIndexBar indexBar = ViewUtils.findView(rootView, R.id.indexbar);
        indexBar.setOnLetterChangeListener(this);
        listView.setAdapter(new ChooseCityAdapter(cities));
        listView.setOnItemClickListener(listener);

        setContentView(rootView);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.bg_gray)));
        setTouchable(true);
//        setAnimationStyle(R.style.popwin_anim_style);
        setOutsideTouchable(true);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onLetterChange(String letter) {
        if (positionMap.containsKey(letter)) {
            listView.setSelection(positionMap.get(letter) + 2);
        }
    }

    @Override
    public void onTouchStart() {

    }

    @Override
    public void onTouchEnd() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
