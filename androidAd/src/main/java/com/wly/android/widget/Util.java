package com.wly.android.widget;

import android.content.Context;
import android.util.Log;

public class Util {

	public static int dpToPx(Context context, int dp) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	public static int pxToDp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	public void Get(Context context,float f){
		int result = pxToDp(context, 30);
		System.out.println(result);
	}
	
}

