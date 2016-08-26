package ru.rubicon.myexamples;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnCoordinator;
    private Button btnCardScroll, btnTextAnimation, btnCustomView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCoordinator = (Button) findViewById(R.id.btn_coordinator);
        btnCoordinator.setOnClickListener(new CoordinatorOnClickListener());
        buttonSettings(btnCardScroll, R.id.btn_card_scroll, new CardScrollOnClickListener());
        buttonSettings(btnTextAnimation, R.id.btn_text_animation, new TextAnimationOnClickListener());
        buttonSettings(btnCustomView, R.id.btn_custom_view, new CustomViewOnClickListener());
    }

    private void buttonSettings(Button btn, @IdRes int id, View.OnClickListener listener){
        btn = (Button) findViewById(id);
        btn.setOnClickListener(listener);
    }

    private class CoordinatorOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, CoordinatorLayoutExample.class);
            startActivity(intent);
        }
    }

    private class CardScrollOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, CardScroll.class);
            startActivity(intent);
        }
    }

    private class TextAnimationOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, TextAnimationExample.class);
            startActivity(intent);
        }
    }

    private class CustomViewOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, CustomViewExample.class);
            startActivity(intent);
        }
    }


}
