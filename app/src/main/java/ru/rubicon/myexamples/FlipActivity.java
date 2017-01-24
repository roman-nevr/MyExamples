package ru.rubicon.myexamples;

/**
 * Created by Витя on 21.09.2016.
 */
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.animation.ObjectAnimator;
import android.os.Bundle;

public class FlipActivity extends ActionBarActivity {

    TextView textTitle;
    Button buttonFlip;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flip_activity);
        textTitle = (TextView)findViewById(R.id.title);
        textTitle.setOnClickListener(MyOnClickListener);
        buttonFlip = (Button)findViewById(R.id.buttonflip);
        buttonFlip.setOnClickListener(MyOnClickListener);
        image = (ImageView)findViewById(R.id.image);
        image.setOnClickListener(MyOnClickListener);

    }

    OnClickListener MyOnClickListener = new OnClickListener(){
        @Override
        public void onClick(View v) {
            flipit(v);
        }

    };

    private void flipit(final View viewToFlip) {
        ObjectAnimator flip = ObjectAnimator.ofFloat(viewToFlip, "rotationY", 0f, 360f);
        flip.setDuration(3000);
        flip.start();

    }

}
