package ru.rubicon.myexamples;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;

/**
 * Created by Витя on 25.08.2016.
 */
public class TextAnimationExample extends AppCompatActivity implements TextSwitcher.ViewFactory {

    Button btnTextAnimator;
    TextSwitcher mTextSwitcher;
    ICommand action;
    String word;
    ObjectAnimator rotation2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_animation_main);
        btnTextAnimator = (Button) findViewById(R.id.btn_text_animation);
        btnTextAnimator.setOnClickListener(new ButtonOnClickListener());
        mTextSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);
        mTextSwitcher.setFactory(this);
        mTextSwitcher.setOnClickListener(new TextAnimationOnClickListener());

        Animation inAnimation = AnimationUtils.loadAnimation(this,
               android.R.anim.slide_in_left);
        Animation outAnimation = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_out_right);
        mTextSwitcher.setInAnimation(inAnimation);
        mTextSwitcher.setOutAnimation(outAnimation);
        action = new Hello();
        action.execute();
        action = action.getNext();
        mTextSwitcher.setCurrentText(word);
    }

    @Override
    public View makeView() {
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
        textView.setTextSize(70);
        textView.setTextColor(Color.RED);
        return textView;
    }

    private class TextAnimationOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            action.execute();
            mTextSwitcher.setText(word);
            action = action.getNext();
        }
    }

    private class ButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ObjectAnimator rotation = ObjectAnimator.ofFloat(btnTextAnimator, "rotationY", 0, 90);
            rotation.setDuration(750);
            rotation.setInterpolator(new AccelerateInterpolator());
            rotation.start();
            rotation2 = ObjectAnimator.ofFloat(btnTextAnimator, "rotationY", -90, 0);
//            btnTextAnimator.setText(word);
//            action.execute();
//            action = action.getNext();
            /*btnTextAnimator.setText("bye");
            rotation = ObjectAnimator.ofFloat(btnTextAnimator, "rotationY", 180, 360);
            rotation.setStartDelay(1500);
            rotation.setDuration(1500);
            rotation.start();*/
            rotation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    btnTextAnimator.setText(word);
                    action.execute();
                    action = action.getNext();

                    rotation2.setDuration(750);
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
    }

    private interface ICommand{
        void execute();
        ICommand getNext();
    }
    private class Hello implements ICommand{
        @Override
        public void execute() {
            word = "Hello";
        }

        @Override
        public ICommand getNext() {
            return new ByeBye();
        }
    }
    private class ByeBye implements ICommand{
        @Override
        public void execute() {
            word = "ByeBye1111111111111111111";
        }

        @Override
        public ICommand getNext() {
            return new Hello();
        }
    }

}
/*
// get a TextSwitcher view; instantiate in code or resolve from a layout/XML
TextSwitcher textSwitcher = new TextSwitcher(context);

// specify the in/out animations you wish to use
textSwitcher.setInAnimation(context, R.anim.slide_in_left);
textSwitcher.setOutAnimation(context, R.anim.slide_out_right);

// provide two TextViews for the TextSwitcher to use
// you can apply styles to these Views before adding
textSwitcher.addView(new TextView(context));
textSwitcher.addView(new TextView(context));

// you are now ready to use the TextSwitcher
// it will animate between calls to setText
textSwitcher.setText("hello");
...
textSwitcher.setText("goodbye");
 */