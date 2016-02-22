package com.yichang.kaku.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yichang.kaku.R;

/**
 * Created by xiaosu on 2015/11/11.
 */
public class ApplyJoinHeader extends FrameLayout {

    private ImageView iv_start;
    private ImageView iv_end;
    private View line;
    private LayoutParams params;
    private final int mPadding;
    private final int linePadding;
    private boolean confirmed = false;

    public ApplyJoinHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_apply_to_join_head, this);

        mPadding = getResources().getDimensionPixelOffset(R.dimen.x30);
        linePadding = getResources().getDimensionPixelOffset(R.dimen.x20);

        setPadding(mPadding, mPadding, mPadding, mPadding);

        findView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        params.rightMargin = iv_end.getMeasuredWidth() + linePadding;
        params.leftMargin = iv_start.getMeasuredWidth() + linePadding;
        params.topMargin = iv_start.getMeasuredHeight() / 2;

        line.setLayoutParams(params);
    }

    private void findView() {
        iv_start = (ImageView) findViewById(R.id.iv_start);
        iv_end = (ImageView) findViewById(R.id.iv_end);
        line = findViewById(R.id.line);

        params = (LayoutParams) line.getLayoutParams();
    }
}
