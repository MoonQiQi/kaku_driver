package com.yichang.kaku.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;

public class IllegalPopWindow extends PopupWindow {

    private BaseActivity context;

    public IllegalPopWindow(final BaseActivity context) {
        super(context);
        this.context = context;

        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#77000000")));
        setOutsideTouchable(false);
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        View lay_pop_prize = context.inflate(R.layout.lay_pop_prize);
        setContentView(lay_pop_prize);

        ImageView iv_prize = context.findView(lay_pop_prize, R.id.prize);

        iv_prize.setImageResource(R.drawable.img_xsz);


        context.findView(lay_pop_prize, R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ((View) iv_prize.getParent()).setOnClickListener(new View.OnClickListener() {
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
