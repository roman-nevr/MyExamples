package ru.rubicon.myexamples;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnCoordinator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCoordinator = (Button) findViewById(R.id.btn_coordinator);
        btnCoordinator.setOnClickListener(new CoordinatorOnClickListener());
    }

    private class CoordinatorOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, CoordinatorLayoutExample.class);
            startActivity(intent);
        }
    }
}
