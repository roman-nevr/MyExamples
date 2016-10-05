package ru.rubicon.myexamples.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by Витя on 05.10.2016.
 */
public class PixelView extends View {

    private TextPaint mTextPaint;
    float x;
    Random r = new Random(10);

    public PixelView(Context context) {
        super(context);
        init(null, 0);
    }

    public PixelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PixelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle){
        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(50);
        startAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        x = r.nextFloat()*10;

        canvas.drawText("hello", x+40,x+40, mTextPaint);
    }

    long mAnimStartTime;

    Handler mHandler = new Handler();
    Runnable mTick = new Runnable() {
        public void run() {
            invalidate();
            mHandler.postDelayed(this, 20); // 20ms == 60fps
        }
    };

    void startAnimation() {
        mAnimStartTime = SystemClock.uptimeMillis();
        mHandler.removeCallbacks(mTick);
        mHandler.post(mTick);
    }

    void stopAnimation() {
        mHandler.removeCallbacks(mTick);
    }
}
