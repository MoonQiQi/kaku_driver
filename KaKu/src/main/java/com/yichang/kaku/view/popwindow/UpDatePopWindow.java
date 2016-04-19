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

import com.yichang.kaku.R;
import com.yichang.kaku.global.UpdateAppManager;

public class UpDatePopWindow extends PopupWindow {

    private Activity context;
    private boolean flag;
    private UpdateAppManager updateManager;
    private String android_url;

    public UpDatePopWindow(final Activity context, final boolean flag, final String android_url) {
        super(context);
        this.context = context;
        this.flag = flag;
        this.android_url = android_url;

        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#77000000")));
        setOutsideTouchable(false);
        setFocusable(false);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.update_pupwindow, null);
        setContentView(view);

        ImageView iv_update_liji= (ImageView) view.findViewById(R.id.iv_update_liji);
        ImageView iv_update_yihouzaishuo = (ImageView) view.findViewById(R.id.iv_update_yihouzaishuo);
        if (flag){
            iv_update_yihouzaishuo.setVisibility(View.VISIBLE);
        } else {
            iv_update_yihouzaishuo.setVisibility(View.INVISIBLE);
        }

        iv_update_yihouzaishuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        iv_update_liji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateManager = new UpdateAppManager(context, android_url);
                updateManager.checkUpdateInfo();
                dismiss();
            }
        });

    }

    public void show() {
        showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

}
