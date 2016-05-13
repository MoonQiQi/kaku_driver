package com.yichang.kaku.view.popwindow;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;

public class ImageFanKuiWindow extends PopupWindow {

    private Activity context;
    private String flag_get;
    private String money_coupon;

    public ImageFanKuiWindow(final Activity context, String flag_get, String money_coupon) {
        super(context);
        this.context = context;
        this.flag_get = flag_get;
        this.money_coupon = money_coupon;

        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#C6000000")));
        setOutsideTouchable(false);
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.imagefankui_pupwindow, null);
        setContentView(view);

        TextView tv_pup_imagefankui1 = (TextView) view.findViewById(R.id.tv_pup_imagefankui1);
        TextView tv_pup_imagefankui2 = (TextView) view.findViewById(R.id.tv_pup_imagefankui2);
        RelativeLayout rela_pup_imagefankui = (RelativeLayout) view.findViewById(R.id.rela_pup_imagefankui);

        if ("Y".equals(flag_get)) {
            rela_pup_imagefankui.setBackgroundResource(R.drawable.hongbao_lijilingqu);
            tv_pup_imagefankui1.setText("恭喜您");
            String s = "获得" + money_coupon + "元优惠券";
            SpannableStringBuilder style = new SpannableStringBuilder(s);
            style.setSpan(new AbsoluteSizeSpan(28, true), 2, s.length() - 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_pup_imagefankui2.setText(style);
        } else {
            rela_pup_imagefankui.setBackgroundResource(R.drawable.hongbao_xiacizailai);
            tv_pup_imagefankui1.setText("很遗憾");
            tv_pup_imagefankui2.setText("您未能获得奖品");
        }

        rela_pup_imagefankui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ImageView iv_cancel = (ImageView) view.findViewById(R.id.iv_cancel);

        iv_cancel.setOnClickListener(new View.OnClickListener() {
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
