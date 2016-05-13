package com.yichang.kaku.view.popwindow;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yichang.kaku.R;

public class YuEPopWindow extends PopupWindow {

    private Activity context;
    private String ketixian,buketixian;

    public YuEPopWindow(final Activity context, final String ketixian,final String buketixian) {
        super(context);
        this.context = context;
        this.ketixian = ketixian;
        this.buketixian = buketixian;

        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#C6000000")));
        setOutsideTouchable(false);
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.yue_pupwindow, null);
        setContentView(view);

        ImageView iv_close= (ImageView) view.findViewById(R.id.iv_close);
        TextView tv_pop_ketixian = (TextView) view.findViewById(R.id.tv_pop_ketixian);
        TextView tv_pop_buketixian = (TextView) view.findViewById(R.id.tv_pop_buketixian);
        TextView tv_pop_close = (TextView) view.findViewById(R.id.tv_pop_close);
        tv_pop_ketixian.setText("可提现金额：¥ " + ketixian);
        tv_pop_buketixian.setText("不可提现金额：¥ " + buketixian);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_pop_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public void show() {
        showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }
}
