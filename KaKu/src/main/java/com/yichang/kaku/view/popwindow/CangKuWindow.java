package com.yichang.kaku.view.popwindow;

import android.app.Activity;
import android.graphics.Bitmap;
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
import com.yichang.kaku.tools.Utils;

public class CangKuWindow extends PopupWindow {

    private Activity context;
    private String no;

    public CangKuWindow(final Activity context, final String no) {
        super(context);
        this.context = context;
        this.no = no;

        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#C6000000")));
        setOutsideTouchable(false);
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cangku_pupwindow, null);
        setContentView(view);

        TextView tv_cangkupup_no = (TextView) view.findViewById(R.id.tv_cangkupup_no);
        ImageView iv_cangkupup_qrcode = (ImageView) view.findViewById(R.id.iv_cangkupup_qrcode);
        tv_cangkupup_no.setText(no);

        Bitmap creatMyCode = Utils.createQRCode(no);
        iv_cangkupup_qrcode.setImageBitmap(creatMyCode);

        ImageView iv_cangkupup_cha = (ImageView) view.findViewById(R.id.iv_cangkupup_cha);
        iv_cangkupup_cha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.cha();
                dismiss();
            }
        });
    }

    public void show() {
        showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    public static CangKuCallBack callBack;

    public void CangKuCallBack(CangKuCallBack callBack) {
        this.callBack = callBack;
    }

    public interface CangKuCallBack {
        void cha();
    }
}
