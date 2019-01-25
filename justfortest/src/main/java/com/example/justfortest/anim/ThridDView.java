package com.example.justfortest.anim;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class ThridDView extends View {
    private Paint mPaint;
    private int mCenterX;
    private int mCenterY;
    private Camera mCamera;
    private Matrix mMatrix;
    private float mCanvasRotateY;
    private float mCanvasRotateX;
    private float mCanvasMaxRotateDegree = 20;
    private int startAlpha = 60;
    private float moveX;
    private double degress = 0;
    private float moveY;

    public ThridDView(Context context) {
        super(context);
    }

    public ThridDView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mMatrix = new Matrix();
        mCamera = new Camera();
    }

    public ThridDView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCenterX = getWidth() / 2;
        mCenterY = getHeight() / 2;
//        mMatrix.reset();
//        mCamera.save();
//        mCamera.rotateX(mCanvasRotateX);
//        mCamera.rotateY(mCanvasRotateY);
//        mCamera.getMatrix(mMatrix);
//        mCamera.restore();
//        //改变矩阵作用点
//        mMatrix.preTranslate(-mCenterX, -mCenterY);
//        mMatrix.postTranslate(mCenterX, mCenterY);
//        canvas.concat(mMatrix);
        int mBgColor = Color.parseColor("#227BAE");
        canvas.drawColor(mBgColor);
        mPaint.setColor(Color.WHITE);
        canvas.rotate((float) degress, mCenterX, mCenterY);
        degress = Math.atan((moveX - mCenterX) / (mCenterY - moveY));
        degress = Math.toDegrees(degress);
        if (moveY > mCenterY) {
            degress = degress + 180;
        }
        canvas.save();
        for (int i = 0; i < 120; i++) {
            mPaint.setAlpha((int) (startAlpha + (255 - startAlpha) * (i / 119.0)));
            canvas.drawLine(mCenterX, mCenterY - 450, mCenterX, mCenterY - 390, mPaint);
            canvas.rotate(3, mCenterX, mCenterY);
        }
        canvas.restore();
        canvas.drawCircle(mCenterX, mCenterY - 360, 15, mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        moveX = event.getX();
        moveY = event.getY();

        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE: {
                //这里将camera旋转的变量赋值
                rotateCanvasWhenMove(moveX, moveY);
                invalidate();
            }
            case MotionEvent.ACTION_UP: {

                //这里将camera旋转的变量赋值
                rotateCanvasWhenMove(moveX, moveY);
                invalidate();
            }
        }

        return true;
    }

    private void rotateCanvasWhenMove(float x, float y) {
        float dx = x - mCenterX;
        float dy = y - mCenterY;

        float percentX = dx / mCenterX;
        float percentY = dy / mCenterY;

        if (percentX > 1f) {
            percentX = 1f;
        } else if (percentX < -1f) {
            percentX = -1f;
        }
        if (percentY > 1f) {
            percentY = 1f;
        } else if (percentY < -1f) {
            percentY = -1f;
        }

        mCanvasRotateY = mCanvasMaxRotateDegree * percentX;
        mCanvasRotateX = -(mCanvasMaxRotateDegree * percentY);
    }
}
