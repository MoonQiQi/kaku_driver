package com.yichang.kaku.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.home.CitiesAdapter;
import com.yichang.kaku.home.ProvinceAdapter;
import com.yichang.kaku.response.IllegalResp;
import com.yichang.kaku.tools.LogUtil;

import java.util.List;

/**
 * Created by xiaosu on 2015/11/26.
 * 违章查询的省市选择
 */
public class ChooseCityListPopWindow extends PopupWindow {

    private BaseActivity context;
    private List<IllegalResp.Province> provinces;
    private ProvinceAdapter provinceAdapter;
    private final GridView gridView;
    private CitiesAdapter citiesAdapter;

    public ChooseCityListPopWindow(BaseActivity context,
                                   List<IllegalResp.Province> provinces,
                                   AdapterView.OnItemClickListener listener) {
        super(context);
        this.provinces = provinces;

        this.context = context;

        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F0EFEF")));
        setOutsideTouchable(true);
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        View licenseChoosePanel = context.inflate(R.layout.lay_line_gridview);
        setContentView(licenseChoosePanel);

        gridView = (GridView) licenseChoosePanel.findViewById(R.id.gv_shoplist_city);
        provinceAdapter = new ProvinceAdapter(provinces);
        gridView.setAdapter(provinceAdapter);
        gridView.setOnItemClickListener(listener);
        licenseChoosePanel.findViewById(R.id.tv_pupp_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public Object performClick(int position) {

        if (gridView.getAdapter() instanceof ProvinceAdapter) {

            if (provinces.get(position).Cities.size() == 1) {//如果省份只包含一个城市，直接填写城市
                dismiss();
                return provinceAdapter.getDatas().get(position).Cities.get(0);
            }

            if (listener != null){
                listener.provinceChoose(position);
            }

            if (citiesAdapter == null) {
                citiesAdapter = new CitiesAdapter(provinces.get(position).Cities);
                gridView.setAdapter(citiesAdapter);
            } else {
                citiesAdapter.notifyDataSetChanged(provinces.get(position).Cities);
                gridView.setAdapter(citiesAdapter);
            }
            return provinceAdapter.getDatas().get(position);
        } else {
            dismiss();
            return citiesAdapter.getDatas().get(position);
        }
    }

    public void setListener(OnProvinceChooseListener listener) {
        this.listener = listener;
    }

    public OnProvinceChooseListener listener;

    public interface OnProvinceChooseListener{
        void provinceChoose(int position);
    }

    public boolean isProvincePresent() {
        return gridView.getAdapter() instanceof ProvinceAdapter;
    }

    public void show() {

        if (!(gridView.getAdapter() instanceof ProvinceAdapter)) {
            gridView.setAdapter(provinceAdapter);
        }
        showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

}
