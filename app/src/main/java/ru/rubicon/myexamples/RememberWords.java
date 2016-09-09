package ru.rubicon.myexamples;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import ru.rubicon.myexamples.adapters.WordCardsAdapter;


/**
 * Created by Витя on 08.09.2016.
 */
public class RememberWords extends AppCompatActivity {

    private WordCardsAdapter wordCardsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remember_words_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<WordCardsAdapter.WordPairs> wordPairses = new ArrayList<>();
        wordPairses.add(new WordCardsAdapter.WordPairs("Hello!", "Привет"));
        wordPairses.add(new WordCardsAdapter.WordPairs("bye", "пока"));
        wordPairses.add(new WordCardsAdapter.WordPairs("Hi!", "здарова"));

        if (wordCardsAdapter == null) {
            wordCardsAdapter = new WordCardsAdapter(wordPairses);
            recyclerView.setAdapter(wordCardsAdapter);
        } else {
            wordCardsAdapter.notifyDataSetChanged();
        }
    }
}
