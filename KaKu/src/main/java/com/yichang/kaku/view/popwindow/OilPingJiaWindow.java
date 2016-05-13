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
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.home.giftmall.ShopMallActivity;
import com.yichang.kaku.money.TuiJianJiangLiActivity;

public class OilPingJiaWindow extends PopupWindow {

    private Activity context;

    public OilPingJiaWindow(final Activity context) {
        super(context);
        this.context = context;

        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#C6000000")));
        setOutsideTouchable(false);
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.oilpingjia_pupwindow, null);
        setContentView(view);

        ImageView iv_oilpup_yqhy50 = (ImageView) view.findViewById(R.id.iv_oilpup_yqhy50);
        ImageView iv_oilpup_ggsc = (ImageView) view.findViewById(R.id.iv_oilpup_ggsc);
        ImageView iv_oilpupcha = (ImageView) view.findViewById(R.id.iv_oilpupcha);

        iv_oilpupcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                dismiss();
            }
        });

        iv_oilpup_yqhy50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TuiJianJiangLiActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                context.finish();
                dismiss();
            }
        });

        iv_oilpup_ggsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShopMallActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                context.finish();
                dismiss();
            }
        });

    }

    public void show() {
        showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }
}
