package ru.rubicon.myexamples;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class DoublesideView extends View {
    private String frontSideString, backSideString; // TODO: use a default from R.string...
    private int mExampleColor = Color.BLACK; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;
    private ICommand action;
    private SideAction frontSideAction, backSideAction;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    // Конструктор, необходимый для создания элемента внутри кода программы
    public DoublesideView(Context context) {
        super(context);
        init(null, 0);
    }
    // Конструктор, необходимый для наполнения элемента из файла с ресурсом
    public DoublesideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }
    // Конструктор, необходимый для наполнения элемента из файла с ресурсом
    public DoublesideView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.DoublesideView, defStyle, 0);

        frontSideString = a.getString(R.styleable.DoublesideView_frontSideString);
        backSideString = a.getString(R.styleable.DoublesideView_backSideString);
        mExampleColor = a.getColor(R.styleable.DoublesideView_exampleColor,mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.DoublesideView_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.DoublesideView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.DoublesideView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        frontSideAction = new SideAction(frontSideString);
        backSideAction = new SideAction(backSideString);
        frontSideAction.setNextAction(backSideAction);
        backSideAction.setNextAction(frontSideAction);
        action = frontSideAction;
        setOnClickListener(new DoublesideViewOnClickListener());

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        float frontSideStringWidth = mTextPaint.measureText(frontSideString);
        float backSideStringWidth = mTextPaint.measureText(backSideString);
        if(frontSideStringWidth > backSideStringWidth){
            mTextWidth = frontSideStringWidth;
        }else {
            mTextWidth = backSideStringWidth;
        }
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
        float freeSpace = getWidth()- getPaddingLeft() - getPaddingRight();
        /*if (mTextWidth > freeSpace){
            mExampleDimension = mExampleDimension * (freeSpace/mTextWidth);
            mTextPaint.setTextSize(mExampleDimension);
            mTextWidth = mTextPaint.measureText(frontSideString);
            mTextHeight = mTextPaint.getFontMetrics().bottom;
        }*/
        canvas.drawText(frontSideString,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight /*- mTextPaint.getFontMetrics().top*/) / 2,
                mTextPaint);

        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredHeight = measureHeight(heightMeasureSpec);
        int measuredWidth = measureWidth(widthMeasureSpec);
        // Вы ДОЛЖНЫ сделать вызов метода setMeasuredDimension,
        // иначе получится выброс исключения при
        // размещении элемента внутри разметки.
        float freeSpace = measuredWidth - getPaddingLeft() - getPaddingRight();
        cutFontSize(freeSpace, mTextWidth, mTextPaint);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    private void cutFontSize(float freeSpace, float textWidth, TextPaint textPaint) {
        if (freeSpace < textWidth){
            mExampleDimension = mExampleDimension * freeSpace / textWidth * 0.98f;
            textPaint.setTextSize(mExampleDimension);
        }
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        // Размер по умолчанию, если ограничения не были установлены.
        int result = 500;

        if (specMode == MeasureSpec.AT_MOST) {
            //wrap content
            // Рассчитайте идеальный размер вашего
            // элемента в рамках максимальных значений.
            // Если ваш элемент заполняет все доступное
            // пространство, верните внешнюю границу.
            //result = Math.round(mTextPaint.getFontMetrics().top+mTextPaint.getFontMetrics().bottom);
            result = Math.round(mTextHeight - mTextPaint.getFontMetrics().top) + getPaddingBottom() + getPaddingTop();

        } else if (specMode == MeasureSpec.EXACTLY) {
            // Если ваш элемент может поместиться внутри этих границ, верните это значение.
            result = specSize;
        }
        return result;
    }
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        // Размер по умолчанию, если ограничения не были установлены.
        int result = 500;
        if (specMode == MeasureSpec.AT_MOST) {
            //wrap content
            // Рассчитайте идеальный размер вашего
            // элемента в рамках максимальных значений.
            // Если ваш элемент заполняет все доступное
            // пространство, верните внешнюю границу.
            //result = Math.round(mTextPaint.measureText(frontSideString))+getPaddingRight()+getPaddingLeft();
            result = Math.round(mTextWidth) + getPaddingLeft() + getPaddingRight();

        } else if (specMode == MeasureSpec.EXACTLY) {
            // Если ваш элемент может поместиться внутри этих границ, верните это значение.
            result = specSize;
        }
        return result;
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return frontSideString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        frontSideString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }


    private class DoublesideViewOnClickListener implements OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

    private interface ICommand{
        String execute();
        ICommand getNext();
    }

    private class SideAction implements ICommand{
        String string;
        ICommand nextAction;
        public SideAction(String string){
            this.string = string;
        }

        void setNextAction(ICommand action){
            this.nextAction = action;
        }

        void setString(String string){
            this.string = string;
        }

        @Override
        public String execute() {
            return string;
        }

        @Override
        public ICommand getNext() {
            return nextAction;
        }
    }
}
