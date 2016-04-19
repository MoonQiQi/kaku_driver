package com.yichang.kaku.view;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/9/22.
 */
public class KakuMultiLineTextView extends TextView {
    private Context mContext;


    public KakuMultiLineTextView(Context context) {
        super(context);
        mContext = context;
    }

    public KakuMultiLineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

    }

    public KakuMultiLineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public KakuMultiLineTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Layout layout = getLayout();
        if (layout != null) {
            int height = (int) FloatMath.ceil(getMaxLineHeight(this.getText().toString()))
                    + getCompoundPaddingTop() + getCompoundPaddingBottom();
            int width = getMeasuredWidth();
            setMeasuredDimension(width, height);
        }
    }

    private float getMaxLineHeight(String content) {

        float height = 0.0f;
        float screenW = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();
        float paddingLeft = ((LinearLayout) this.getParent()).getPaddingLeft();
        float paddingReft = ((LinearLayout) this.getParent()).getPaddingRight();
        // float marginLeft=((LinearLayout) this.getParent()).getChildAt()
//这里具体this.getPaint()要注意使用，要看你的TextView在什么位置，这个是拿TextView父控件的Padding的，为了更准确的算出换行
        int line = (int) Math.ceil((this.getPaint().measureText(content) / (screenW - paddingLeft - paddingReft)));
        int measureLines=this.getLineCount();
        if(line<measureLines){
            line=measureLines;
        }

        height = (this.getPaint().getFontMetrics().descent - this.getPaint().getFontMetrics().ascent) * line+10;
        return height;
    }


}
