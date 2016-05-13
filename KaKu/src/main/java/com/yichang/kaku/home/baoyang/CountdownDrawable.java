package com.yichang.kaku.home.baoyang;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class CountdownDrawable extends Drawable {
    private final static int PROGRESS_FACTOR = -360;//正负顺逆时针

    private Paint mPaint;
    private RectF mArcRect;

    //当前进度条进度
    private float progress;
    //边框圆颜色
    private int outlineColor;
    //内部背景圆颜色
    private int innerColor;
    //进度条颜色
    private int ringColor;
    //进度条宽度
    private int ringWidth;
    //倒计时数字
    private int showNumber;
    //倒计时总时间
    private int totalNumber;
    //数字颜色
    private int textColor;

    public CountdownDrawable(int ringWidth, int outlineColor, int innerColor, int ringColor, int showNumber, int textColor, int totalNumber) {
        mPaint = new Paint();
        mArcRect = new RectF();

        this.outlineColor = outlineColor;
        this.innerColor = innerColor;
        this.ringColor = ringColor;
        this.ringWidth = ringWidth;
        this.showNumber = showNumber;
        this.textColor = textColor;
        this.totalNumber = totalNumber;
    }

    @Override
    public void draw(Canvas canvas) {
        // get view size
        final Rect bounds = getBounds();

        int size = bounds.height() > bounds.width() ? bounds.width() : bounds.height();
        float outerRadius = ((size / 2) * 0.75f) * 0.75f;
        float innerRadius = ((size / 2) * 0.75f) * 0.75f;
        float offsetX = (bounds.width() - outerRadius * 2) / 2;
        float offsetY = (bounds.height() - outerRadius * 2) / 2;

        //outline circle
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(outlineColor);
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius, mPaint);

        //inner circle
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(innerColor);
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), innerRadius, mPaint);

        //show number & set show number center
        float textSize = innerRadius * 0.75f;
        mPaint.setTextSize(textSize);
        mPaint.setTextAlign(Align.CENTER);
        mPaint.setColor(textColor);
        float textX = bounds.centerX();
        float textY = bounds.centerY() - (mPaint.descent() + mPaint.ascent()) / 2;
        //canvas.drawText(Integer.toString(showNumber), textX, textY, mPaint);
        int m = showNumber / 60;
        int n = showNumber % 60;
        String n_num;
        if (n < 10) {
            n_num = "0" + n;
        } else {
            n_num = n + "";
        }
        canvas.drawText((m + ":" + n_num), textX, textY, mPaint);

        //ring circle
        int halfRingWidth = ringWidth / 2;
        float arcX0 = offsetX + halfRingWidth;
        float arcY0 = offsetY + halfRingWidth;
        float arcX = offsetX + outerRadius * 2 - halfRingWidth;
        float arcY = offsetY + outerRadius * 2 - halfRingWidth;

        mPaint.setColor(ringColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(ringWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mArcRect.set(arcX0, arcY0, arcX, arcY);
        canvas.drawArc(mArcRect, 89, progress, false, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
    }

    @Override
    public int getOpacity() {
        return mPaint.getAlpha();
    }

    public float getProgress() {
        return progress / PROGRESS_FACTOR;
    }

    public void setProgress(float progress) {
        this.progress = progress * PROGRESS_FACTOR * showNumber / totalNumber;

        invalidateSelf();
    }

    public int getShowNumber() {
        return showNumber;
    }

    public void setShowNumber(int showNumber) {
        this.showNumber = showNumber;

        invalidateSelf();
    }
}
