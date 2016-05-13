package com.yichang.kaku.view.widget;

import android.app.Dialog;
import android.content.Context;

import com.yichang.kaku.R;


public class DialogRequestProgress extends Dialog {
    public DialogRequestProgress(Context context) {
        super(context, R.style.Translucent_NoTitle);
        setContentView(R.layout.progressing);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }
}
