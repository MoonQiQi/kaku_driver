package com.yichang.kaku.view.popwindow;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;


public class InputPhoneWindow extends PopupWindow {

    private BaseActivity context;

    private String strPwd;
    private EditText et_callphone;
    private TextView tv_boda;


    public InputPhoneWindow(final BaseActivity context) {
        super(context);
        this.context = context;

        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#C6000000")));
        setOutsideTouchable(false);
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        final View view = context.inflate(R.layout.layout_input_phonecode_confirm);
        setContentView(view);

        LinearLayout ll_pwd_container = (LinearLayout) view.findViewById(R.id.ll_pwd_container);

        et_callphone = (EditText) view.findViewById(R.id.et_callphone);
        tv_boda = (TextView) view.findViewById(R.id.tv_boda);
        tv_boda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_callphone.getText().toString().trim().length() == 11) {
                    mListener.Jump();
                    SharedPreferences.Editor editor = KaKuApplication.editor;
                    editor.putString(Constants.CALL, et_callphone.getText().toString().trim());
                    editor.putString(Constants.CALLNAME, et_callphone.getText().toString().trim());
                    editor.commit();
                    dismiss();
                }
            }
        });


        ImageView iv_close = (ImageView) view.findViewById(R.id.iv_close);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });

    }

    public void show() {
        showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    public interface ConfirmListener {
        void Jump();
    }

    private ConfirmListener mListener;

    public void setConfirmListener(ConfirmListener listener) {
        this.mListener = listener;
    }
}
