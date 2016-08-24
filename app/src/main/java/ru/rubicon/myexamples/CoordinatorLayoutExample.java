package ru.rubicon.myexamples;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Витя on 24.08.2016.
 */
public class CoordinatorLayoutExample extends AppCompatActivity {

    private CoordinatorLayout clMain;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinator_layout_example_activity);
        clMain = (CoordinatorLayout) findViewById(R.id.clMain);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new ActionButtonOnClickListener());
    }

    private class ActionButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Snackbar.make(clMain, "Example ", Snackbar.LENGTH_LONG).show();
        }
    }
}