package com.yichang.kaku.home.question;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.tools.ImageSpan;

/**
 * Created by xiaosu on 2015/11/12.
 */
public class LookOtherAskActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_ask_other_item1);
        TextView res_content = findView(R.id.res_content);

        SpannableString spanString = new SpannableString(" ");
        Drawable d = getResources().getDrawable(R.drawable.img_tiwen_wen);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, getResources().getDimensionPixelOffset(R.dimen.x10), 0);
        spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    }
}
