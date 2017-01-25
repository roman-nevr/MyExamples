package ru.rubicon.myexamples;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private Button btnCoordinator, btnBarCode;
    private Button btnCardScroll, btnTextAnimation, btnCustomView, btnRememberWords,
            btnHero, btnFlip, btnPagerView, btnDragNDrop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout llMain = (LinearLayout) findViewById(R.id.ll_main);

        btnCoordinator = (Button) findViewById(R.id.btn_coordinator);
        btnCoordinator.setOnClickListener(new CoordinatorOnClickListener());
        buttonSettings(btnCardScroll, R.id.btn_card_scroll, new CardScrollOnClickListener());
        buttonSettings(btnTextAnimation, R.id.btn_text_animation, new TextAnimationOnClickListener());
        buttonSettings(btnCustomView, R.id.btn_custom_view, new CustomViewOnClickListener());
        buttonSettings(btnRememberWords, R.id.btn_remember_words, new WordCardsOnClickListener());
        buttonSettings(btnHero, R.id.btn_hero, new HeroOnClickListener());
        buttonSettings(btnFlip, R.id.btn_flip_activity, new FlipActivityOnClickListener());
        buttonSettings(btnPagerView, R.id.btn_pager_view, new PagerViewActivityOnClickListener());
        buttonSettings(btnBarCode, R.id.btn_barc_code, new BarCodeActivityOnClickListener());
        buttonSettings(btnDragNDrop, R.id.btn_drag_n_drop, new DragNDropOnClickListener());
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
            Intent intent = new Intent(MainActivity.this, CardScrollDocs.class);
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

    private class WordCardsOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, RememberWords.class);
            startActivity(intent);
        }
    }

    private class HeroOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, Hero.class);
            startActivity(intent);
        }
    }
    private class FlipActivityOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, FlipActivity.class);
            startActivity(intent);
        }
    }
    private class PagerViewActivityOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, PagerViewExample.class);
            startActivity(intent);
        }
    }
    private class BarCodeActivityOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, BarCode.class);
            startActivity(intent);
        }
    }

    private class DragNDropOnClickListener implements View.OnClickListener{
        @Override public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, DragNDropActivity.class);
            startActivity(intent);
        }
    }



}
