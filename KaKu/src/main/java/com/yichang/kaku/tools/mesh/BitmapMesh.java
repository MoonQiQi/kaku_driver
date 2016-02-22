package com.yichang.kaku.tools.mesh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by xiaosu on 2015/12/4.
 */
public class BitmapMesh {
    public static class SampleView extends View {

        private static final int WIDTH = 80;
        private static final int HEIGHT = 80;
        private final int x;
        private final int y;

        private Bitmap mBitmap;
        private final Matrix mMatrix = new Matrix();
        private final Matrix mInverse = new Matrix();

        private boolean mIsDebug = false;
        private Paint mPaint = new Paint();
        private float[] mInhalePt = new float[]{0, 0};
        private InhaleMesh mInhaleMesh = null;

        public SampleView(Context context, Bitmap bitmap, int x, int y) {
            super(context);
            setFocusable(true);

            this.mBitmap = bitmap;
            this.x = x;
            this.y = y;

            mInhaleMesh = new InhaleMesh(WIDTH, HEIGHT);
            mInhaleMesh.setBitmapSize(mBitmap.getWidth(), mBitmap.getHeight());
            mInhaleMesh.setInhaleDir(InhaleMesh.InhaleDir.DOWN);
        }

        public void setIsDebug(boolean isDebug) {
            mIsDebug = isDebug;
        }

        public void setInhaleDir(InhaleMesh.InhaleDir dir) {
            mInhaleMesh.setInhaleDir(dir);

            float w = mBitmap.getWidth();
            float h = mBitmap.getHeight();
            float endX = 0;
            float endY = 0;
            float dx = 10;
            float dy = 10;
            mMatrix.reset();

            switch (dir) {
                case DOWN:
                    endX = w / 2;
                    endY = getHeight() - 20;
                    break;

                case UP:
                    dy = getHeight() - h - 20;
                    endX = w / 2;
                    endY = -dy + 10;
                    break;

                case LEFT:
                    dx = getWidth() - w - 20;
                    endX = -dx + 10;
                    endY = h / 2;
                    break;

                case RIGHT:
                    endX = getWidth() - 20;
                    endY = h / 2;
                    break;
            }

            mMatrix.setTranslate(dx, dy);
            mMatrix.invert(mInverse);
            buildPaths(endX, endY);
            buildMesh(w, h);
            invalidate();
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            float bmpW = mBitmap.getWidth();
            float bmpH = mBitmap.getHeight();

            mMatrix.invert(mInverse);

            mPaint.setColor(Color.RED);
            mPaint.setStrokeWidth(2);
            mPaint.setAntiAlias(true);

            buildPaths(x, h - y);
            buildMesh(bmpW, bmpH);
        }

        public boolean startAnimation(Animator.AnimatorListener listener) {

            ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
            animator.setDuration(1000);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float fraction = animation.getAnimatedFraction();
                    mInhaleMesh.buildMeshes((int) (HEIGHT * fraction));
                    invalidate();
                }
            });
            animator.addListener(listener);
            animator.start();

            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(getResources().getColor(android.R.color.transparent));

            canvas.concat(mMatrix);

            canvas.drawBitmapMesh(mBitmap,
                    mInhaleMesh.getWidth(),
                    mInhaleMesh.getHeight(),
                    mInhaleMesh.getVertices(),
                    0, null, 0, mPaint);

            // ===========================================
            // Draw the target point.
            /*mPaint.setColor(Color.BLUE);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(mInhalePt[0], mInhalePt[1], 10, mPaint);*/
        }

        private void buildMesh(float w, float h) {
            mInhaleMesh.buildMeshes(w, h);
        }

        private void buildPaths(float endX, float endY) {
            mInhalePt[0] = endX;
            mInhalePt[1] = endY;
            mInhaleMesh.buildPaths(endX, endY);
        }

    }

}
