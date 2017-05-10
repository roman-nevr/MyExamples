package ru.rubicon.myexamples.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.widget.AppCompatDrawableManager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

import ru.rubicon.myexamples.R;

/**
 * Created by Витя on 05.10.2016.
 */
public class PixelView extends View {

    private TextPaint mTextPaint;
    private int x,y;
    private Random random;
    private Bitmap bitmap;
    private int width, height;
    private boolean finish;

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
        random = new Random();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = measureWidth(widthMeasureSpec);
        height = measureHeight(heightMeasureSpec);
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixel(100,100, Color.RED);
        setMeasuredDimension(width, height);
    }

    private int measureHeight(int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        int result = 500;

        if ((specMode == MeasureSpec.AT_MOST)||(specMode == MeasureSpec.UNSPECIFIED)) {

        }else if (specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }

        return result;
    }

    private int measureWidth(int widthMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        int result = 500;

        if ((specMode == MeasureSpec.AT_MOST)||(specMode == MeasureSpec.UNSPECIFIED)) {

        }else if (specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }

        return result;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //canvas.drawBitmap(bitmap, 0,0, mTextPaint);
        final Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_bike);
        Drawable drawable = AppCompatDrawableManager.get().getDrawable(getContext(), R.drawable.buble_test);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas1 = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas1.getHeight(), canvas1.getWidth());
        drawable.draw(canvas1);
        final Bitmap shadow = addShadow(src, src.getHeight(), src.getWidth(), Color.BLACK, 5, 1, 3);
        final Bitmap shadow2 = addShadow2(src, src.getHeight(), src.getWidth(), Color.BLACK, 5, 2, 5);
        canvas.drawBitmap(shadow, 0, 0, null);
        canvas.drawBitmap(shadow2, shadow.getWidth(), 0, null);
        canvas.drawBitmap(bitmap, shadow.getWidth()*2+50, 0, null);
        canvas.drawLine(0f, 50, 250f, 50f, mTextPaint);
        canvas.drawLine(0f, bitmap.getHeight(), 400f, bitmap.getHeight(), mTextPaint);
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
        //mHandler.removeCallbacks(myPainter);
        //mHandler.post(myPainter);
    }

    void stopAnimation() {
        mHandler.removeCallbacks(mTick);
    }

    private Runnable myPainter = new Runnable() {
        @Override
        public void run() {
            x = random.nextInt(width);
            y = random.nextInt(height);
            int color = random.nextInt(45000000);
            bitmap.setPixel(x,y, - color);
            mHandler.post(this);
        }
    };



    /*private class MyThread extends Thread{
        @Override
        public void run() {
            while (!finish){
                x = random.nextInt(width);
                y = random.nextInt(height);
                bitmap.setPixel(x,y, random.nextInt(450000000));
            }
        }
    }*/

    public Bitmap addShadow(final Bitmap bm, final int dstHeight, final int dstWidth, int color, int size, float dx, float dy) {
        final Bitmap mask = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ALPHA_8);

        final Matrix scaleToFit = new Matrix();
        final RectF src = new RectF(0, 0, bm.getWidth(), bm.getHeight());
        final RectF dst = new RectF(0, 0, dstWidth - dx, dstHeight - dy);
        scaleToFit.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);

        final Matrix dropShadow = new Matrix(scaleToFit);
        dropShadow.postTranslate(dx, dy);

        final Canvas maskCanvas = new Canvas(mask);
        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maskCanvas.drawBitmap(bm, scaleToFit, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        maskCanvas.drawBitmap(bm, dropShadow, paint);

        final BlurMaskFilter filter = new BlurMaskFilter(size, BlurMaskFilter.Blur.NORMAL);
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(color);
        //paint.setMaskFilter(filter);
        //paint.setFilterBitmap(true);

        final Bitmap ret = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ARGB_8888);
        final Canvas retCanvas = new Canvas(ret);
        retCanvas.drawBitmap(mask, 0,  0, paint);
        //retCanvas.drawBitmap(bm, scaleToFit, null);
        mask.recycle();
        return ret;
    }

    public Bitmap addShadow2(final Bitmap bm, final int dstHeight, final int dstWidth, int color, int size, float dx, float dy) {
        final Bitmap mask = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ALPHA_8);

        final Matrix scaleToFit = new Matrix();
        final RectF src = new RectF(0, 0, bm.getWidth(), bm.getHeight());
        final RectF dst = new RectF(0, 0, dstWidth - dx, dstHeight - dy);
        scaleToFit.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);

        final Matrix dropShadow = new Matrix(scaleToFit);
        dropShadow.postTranslate(dx, dy);

        final Canvas maskCanvas = new Canvas(mask);
        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maskCanvas.drawBitmap(bm, scaleToFit, paint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
//        maskCanvas.drawBitmap(bm, dropShadow, paint);

        final BlurMaskFilter filter = new BlurMaskFilter(size, BlurMaskFilter.Blur.NORMAL);
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setMaskFilter(filter);
        paint.setFilterBitmap(true);

        final Bitmap ret = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ARGB_8888);
        final Canvas retCanvas = new Canvas(ret);
        retCanvas.drawBitmap(mask, 0,  0, paint);
        retCanvas.drawBitmap(bm, scaleToFit, null);
        mask.recycle();
        return ret;
    }
}
