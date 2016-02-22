package com.yichang.kaku.view.widget;

import com.yichang.kaku.R;

import android.app.Dialog;
import android.content.Context;


public class DialogRequestProgress extends Dialog {
	public DialogRequestProgress(Context context) {
		super(context,R.style.Translucent_NoTitle);
		setContentView(R.layout.progressing);
		setCancelable(true);
		setCanceledOnTouchOutside(true);
	}
}
