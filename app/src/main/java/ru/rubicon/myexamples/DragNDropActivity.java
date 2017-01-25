package ru.rubicon.myexamples;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

import ru.rubicon.myexamples.adapters.BehavorTouchHelper;
import ru.rubicon.myexamples.adapters.RecyclerViewBehaviorAdapter;

/**
 * Created by roma on 24.01.2017.
 */
public class DragNDropActivity extends AppCompatActivity{

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_behavior);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerViewBehaviorAdapter adapter = new RecyclerViewBehaviorAdapter(fillItems());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ItemTouchHelper helper = new ItemTouchHelper(new BehavorTouchHelper(adapter));
        helper.attachToRecyclerView(recyclerView);
    }

    private List<String> fillItems(){
        List<String> values = new ArrayList<>();
        values.add("First");
        values.add("Second");
        values.add("Third");
        return values;
    }
}
