package ru.rubicon.myexamples.views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

import ru.rubicon.myexamples.R;

/**
 * TODO: document your custom view class.
 */
public class DoublesideView extends View implements View.OnClickListener {
    private String frontSideString, backSideString, longestSting, currentString; // TODO: use a default from R.string...
    private int mExampleColor = Color.BLACK; // TODO: use a default from R.color...
    private float textSize; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;
    private NinePatchDrawable ninePatchDrawable;
    private ICommand action;
    private SideAction frontSideAction, backSideAction;
    private ObjectAnimator rotation1, rotation2;
    private int rotationTime;
    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    int maxWidth, maxHeight;
    private Rect rect;
    private int symbolsNumber;
    private ArrayList<String> stringList;

    private final static String ROTATION = "rotationX";

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
        rotationTime = 800;
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.DoublesideView, defStyle, 0);
        if (a.hasValue(R.styleable.DoublesideView_frontSideString)){
            frontSideString = a.getString(R.styleable.DoublesideView_frontSideString);
        } else {
            frontSideString = "";
        }
        if (a.hasValue(R.styleable.DoublesideView_backSideString)){
            backSideString = a.getString(R.styleable.DoublesideView_backSideString);
        } else {
            backSideString = "";
        }
        if(a.hasValue(R.styleable.DoublesideView_exampleColor)){
            mExampleColor = a.getColor(R.styleable.DoublesideView_exampleColor,mExampleColor);
        }else {
            mExampleColor = Color.BLACK;
        }

        if (a.hasValue(R.styleable.DoublesideView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.DoublesideView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        if (a.hasValue(R.styleable.DoublesideView_textSize)){
            textSize = a.getDimension(R.styleable.DoublesideView_textSize, 30f);
        } else {
            textSize = 30f;
        }

        //Resources resources = getContext().getResources();
        //textSize = resources.getDimension()
        ninePatchDrawable = (NinePatchDrawable) getContext().getResources().getDrawable(R.drawable.np_test2);
        rect = new Rect();
        ninePatchDrawable.getPadding(rect);
        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(textSize);
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        frontSideAction = new SideAction(frontSideString);
        backSideAction = new SideAction(backSideString);
        frontSideAction.setNextAction(backSideAction);
        backSideAction.setNextAction(frontSideAction);
        action = frontSideAction;
        setOnClickListener(this);
        currentString = frontSideString;
        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(mExampleColor);
        float frontSideStringWidth = mTextPaint.measureText(frontSideString);
        float backSideStringWidth = mTextPaint.measureText(backSideString);
        if(frontSideStringWidth > backSideStringWidth){
            mTextWidth = frontSideStringWidth;
            longestSting = frontSideString;
        }else {
            mTextWidth = backSideStringWidth;
            longestSting = backSideString;
        }
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = ( - fontMetrics.top + fontMetrics.bottom);

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
        int contentWidth = getWidth() - paddingLeft - paddingRight - rect.left - rect.right;
        int contentHeight = getHeight() - paddingTop - paddingBottom - rect.top - rect.bottom;


        // Draw the example drawable on top of the text.
        //NinePatchDrawable ninepatch = new NinePatchDrawable(R.drawable.np_test_9p, );
        if (ninePatchDrawable != null) {
            ninePatchDrawable.setBounds(0, 0, getWidth(), getHeight());
            ninePatchDrawable.draw(canvas);
        }
        // Draw the text.
        /*
        float freeSpace = getWidth()- getPaddingLeft() - getPaddingRight();
        if (mTextWidth > freeSpace){
            textSize = textSize * (freeSpace/mTextWidth);
            mTextPaint.setTextSize(textSize);
            mTextWidth = mTextPaint.measureText(frontSideString);
            mTextHeight = mTextPaint.getFontMetrics().bottom;
        }
        */
        //symbolsNumber = mTextPaint.breakText(action.execute(), true, getWidth() - rect.right - rect.left, null);
        //float x = paddingLeft + (contentWidth) / 2;
        //float y = paddingTop + (contentHeight / 2 + mTextHeight) /*+ 6*/;
        //float y = paddingTop + rect.top  - mTextPaint.getFontMetrics().top;
        //canvas.drawText(action.execute(), x, y, mTextPaint);
        //canvas.drawText(action.execute(),  x, y, mTextPaint);
        //ArrayList<String> stringList = breakStringToDraw(frontSideString, mTextPaint, contentWidth);
        //drawMultilineText(stringList, canvas, mTextPaint, )
        stringList = breakStringToDraw(action.execute(), mTextPaint, contentWidth);
        float startX = paddingLeft + rect.left + (contentWidth) / 2;
        //float startY = paddingTop + rect.top  - mTextPaint.getFontMetrics().top;
        float top = - mTextPaint.getFontMetrics().top;
        float bottom = mTextPaint.getFontMetrics().bottom;
        float leading = mTextPaint.getFontMetrics().leading;
        float startY = paddingTop + rect.top + contentHeight/2 - (top + bottom)*((stringList.size()-1)/2.0f) +
                (top - bottom)/2f;

        for(int rowNumber = 0; rowNumber < stringList.size(); rowNumber++){
            canvas.drawText(stringList.get(rowNumber),  startX, startY +
                    (bottom + leading + top) * rowNumber, mTextPaint);
        }
    }

    /**
     * Gets the arrayList of Strings which can be shown in multiple rows
     *
     * @param string The source string
     * @param textPaint params of font
     *
     * @return The arrayList of Strings
     */
    private ArrayList<String> breakStringToDraw(String string, TextPaint textPaint, float width){
        ArrayList<String> result = new ArrayList<String>();
        boolean finish = false;
        int pointer = 0;
        while (!finish){
            //if next substring starts with space
            while ((pointer < string.length()) &&(string.charAt(pointer) == ' ')){
                pointer++;
            }//so string starts without space
            int number = textPaint.breakText(string.substring(pointer), true, width, null);
            if ((pointer + number) != string.length()){
                String substring = string.substring(pointer , pointer + number);
                //if string ends with space
                int lastIndex = substring.lastIndexOf(" ");
                int end = lastIndex;
                if (lastIndex != -1){
                    while((substring.charAt(lastIndex) == ' ')&&(lastIndex > 0)){
                        lastIndex--;
                    }
                    //add startspacefree string to result
                    result.add(string.substring(pointer, pointer + lastIndex));
                    //move pointer to the end of substring
                    pointer = pointer +end + 1;
                } else {
                    result.add(substring);
                    pointer = pointer + substring.length();
                }
            } else {
                result.add(string.substring(pointer, pointer + number));
                finish = true;
            }

        }
        return result;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = measureWidth(widthMeasureSpec);
        int measuredHeight = measureHeight(heightMeasureSpec);
        // Вы ДОЛЖНЫ сделать вызов метода setMeasuredDimension,
        // иначе получится выброс исключения при
        // размещении элемента внутри разметки.

        //cutFontSize(measuredWidth);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    /*private void cutFontSize(int measuredWidth) {
        float freeSpace = measuredWidth - getPaddingLeft() - getPaddingRight();
        if (freeSpace < mTextWidth){
            textSize = textSize * freeSpace / mTextWidth;
            mTextPaint.setTextSize(textSize);
            mTextWidth = freeSpace;
            mTextHeight = mTextPaint.getFontMetrics().bottom;
        }
    }*/

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        // Размер по умолчанию, если ограничения не были установлены.
        int result = 500;

        if ((specMode == MeasureSpec.AT_MOST)||(specMode == MeasureSpec.UNSPECIFIED)) {
            //wrap content
            // Рассчитайте идеальный размер вашего
            // элемента в рамках максимальных значений.
            // Если ваш элемент заполняет все доступное
            // пространство, верните внешнюю границу.
            //result = Math.round(mTextPaint.getFontMetrics().top+mTextPaint.getFontMetrics().bottom);
            result = Math.round(stringList.size()*(mTextHeight + mTextPaint.getFontMetrics().leading*(stringList.size()-1))) + getPaddingBottom()
                    + getPaddingTop() + rect.top + rect.bottom;

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
            //frontSideString = frontSideString + specSize;
            result = Math.round(mTextWidth) + getPaddingLeft() + getPaddingRight() + rect.left + rect.right;
            if (result > specSize){
                result = specSize;
            }
        } else if (specMode == MeasureSpec.EXACTLY) {
            // Если ваш элемент может поместиться внутри этих границ, верните это значение.
            result = specSize;
        }
        stringList = breakStringToDraw(longestSting, mTextPaint, result - rect.left - rect.right-getPaddingLeft()-getPaddingRight());
        return result;
    }
    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */

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
    public float getTextSize() {
        return textSize;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param textSize The example dimension attribute value to use.
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */

    public String getFrontSideString() {
        return frontSideString;
    }

    public void setFrontSideString(String frontSideString) {
        frontSideAction.setString(frontSideString);
    }

    public String getBackSideString() {
        return backSideString;
    }

    public void setBackSideString(String backSideString) {
        backSideAction.setString(backSideString);
        //invalidate();
    }

    public void setRotationTime(int rotationTime) {
        this.rotationTime = rotationTime;
    }

    @Override
        public void onClick(View view) {
            rotation1 = ObjectAnimator.ofFloat(view, ROTATION, 0, 90);
            rotation1.setDuration(rotationTime / 2);
            rotation1.start();
            rotation2 = ObjectAnimator.ofFloat(view, ROTATION, -90, 0);
            rotation1.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    action = action.getNext();
                    invalidate();
                    rotation2.setDuration(rotationTime / 2);
                    rotation2.start();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
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
            currentString = nextAction.execute();
            return nextAction;
        }
    }
}
