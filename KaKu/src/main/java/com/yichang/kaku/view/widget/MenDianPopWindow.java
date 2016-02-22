package com.yichang.kaku.view.widget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yichang.kaku.R;

public class MenDianPopWindow extends PopupWindow {

    private Activity context;
    private String phone;

    public MenDianPopWindow(final Activity context , final String phone) {
        super(context);
        this.context = context;
        this.phone = phone;

        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#77000000")));
        setOutsideTouchable(false);
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.mendian_pupwindow, null);
        setContentView(view);

        LinearLayout ll_pwd_container= (LinearLayout) view.findViewById(R.id.ll_pwd_container);
        TextView tv_mendianpop_phone = (TextView) view.findViewById(R.id.tv_mendianpop_phone);
        tv_mendianpop_phone.setText(phone);
        TextView tv_mendianpop_call = (TextView) view.findViewById(R.id.tv_mendianpop_call);

        ImageView iv_close= (ImageView) view.findViewById(R.id.iv_close);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_mendianpop_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                context.startActivity(intent);
            }
        });

    }

    public void show() {
        showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }
    ;
}
