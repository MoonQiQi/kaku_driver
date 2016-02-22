package com.yichang.kaku.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * 快速索引条
 *
 * @author 小苏
 */
public class QuickIndexBar extends View {

    public static final String[] LETTERS = new String[]{
            "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X",
            "Y", "Z"};

    private Paint paint;

    private float cellHeight; // 单元格高度
    private int cellWidth; // 单元格宽度

    private OnLetterChangeListener mOnLetterChangeListener;

    /**
     * 字母更新监听
     *
     * @author poplar
     */
    public interface OnLetterChangeListener {
        void onLetterChange(String letter);
        void onTouchStart();
        void onTouchEnd();
    }

    public void setOnLetterChangeListener(OnLetterChangeListener mOnLetterChangeListener) {
        this.mOnLetterChangeListener = mOnLetterChangeListener;
    }


    public QuickIndexBar(Context context) {
        this(context, null);
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        int textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.DEFAULT_BOLD);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 把所有字母画到画板上
        for (int i = 0; i < LETTERS.length; i++) {
            String letter = LETTERS[i];

            // 计算水平坐标
            int x = (int) (cellWidth * 0.5f - paint.measureText(letter) * 0.5f);

            // 计算垂直坐标
            // 将一个文本的指定区域放置到 bounds的矩形对象中
            Rect bounds = new Rect();
            paint.getTextBounds(letter, 0, letter.length(), bounds);
            int y = (int) (cellHeight * 0.5f + bounds.height() * 0.5f + cellHeight * i);

            // 设置画笔颜色, 如果是被触摸的, 设置灰色
            paint.setColor(i == currentIndex ? Color.RED : Color.BLACK);

            // 绘制文本
            canvas.drawText(letter, x, y, paint);
        }

    }

    int currentIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int index;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 根据当前的y值, 计算得到字母的索引
                index = (int) (event.getY() / cellHeight);

                if (mOnLetterChangeListener != null) {
                    mOnLetterChangeListener.onTouchStart();
                }

                if (index != currentIndex) {
                    // 如果发生了变化, 才走到这里边
                    if (index >= 0 && index < LETTERS.length) {
                        // 获取到手指按下的字母
//                        System.out.println(LETTERS[index]);
                        if (mOnLetterChangeListener != null) {
                            mOnLetterChangeListener.onLetterChange(LETTERS[index]);
                        }
                    }
                    currentIndex = index;
                }


                break;
            case MotionEvent.ACTION_MOVE:
                index = (int) (event.getY() / cellHeight);

                if (index != currentIndex) {
                    // 如果发生了变化, 才走到这里边
                    if (index >= 0 && index < LETTERS.length) {
                        // 获取到手指按下的字母
//                        System.out.println(LETTERS[index]);

                        if (mOnLetterChangeListener != null) {
                            mOnLetterChangeListener.onLetterChange(LETTERS[index]);
                        }
                    }
                    currentIndex = index;
                }

                break;
            case MotionEvent.ACTION_UP:
                currentIndex = -1;

                if (mOnLetterChangeListener != null) {
                    mOnLetterChangeListener.onTouchEnd();
                }
                break;
            default:
                break;
        }

        // 重绘界面
        invalidate();

        // 由本控件消费事件
        return true;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        cellWidth = getMeasuredWidth();

        int mHeight = getMeasuredHeight();

        cellHeight = mHeight * 1.0f / LETTERS.length;

    }
}
