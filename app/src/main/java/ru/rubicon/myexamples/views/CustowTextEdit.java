package ru.rubicon.myexamples.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.AttributeSet;
import android.widget.EditText;

import ru.rubicon.myexamples.R;

/**
 * Created by Витя on 13.10.2016.
 */
public class CustowTextEdit extends EditText {
    public CustowTextEdit(Context context) {
        super(context);
        init();
    }

    public CustowTextEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustowTextEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        Drawable drawable = AppCompatDrawableManager.get().getDrawable(getContext(), R.drawable.buble_test);
        setBackgroundDrawable(drawable);
        setPadding(20,20,20,20);
        NinePatchDrawable ninePatchDrawable = (NinePatchDrawable) getContext().getResources().getDrawable(R.drawable.red_2);
        //byte[] chunk = ninePatchDrawable.
        //NinePatch ninePatch = new NinePatch();


    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
    }
}
