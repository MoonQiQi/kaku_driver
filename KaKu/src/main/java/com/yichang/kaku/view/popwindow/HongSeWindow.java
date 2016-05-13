package com.yichang.kaku.view.popwindow;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.ad.StickerOrderActivity;

public class HongSeWindow extends PopupWindow {

    private Activity context;
    private String phone;

    public HongSeWindow(final Activity context) {
        super(context);
        this.context = context;

        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#C6000000")));
        setOutsideTouchable(false);
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.hongse_pupwindow, null);
        setContentView(view);

        ImageView iv_yuyue = (ImageView) view.findViewById(R.id.iv_yuyue);

        ImageView iv_cancel = (ImageView) view.findViewById(R.id.iv_cancel);

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        iv_yuyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KaKuApplication.flag_dory = "D";
                Intent intent = new Intent(context, StickerOrderActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                dismiss();
            }
        });

    }

    public void show() {
        showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }
}
