package ru.rubicon.myexamples.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by roma on 24.01.2017.
 */

public class BehavorTouchHelper extends ItemTouchHelper.SimpleCallback {

    private RecyclerViewBehaviorAdapter adapter;

    public BehavorTouchHelper(RecyclerViewBehaviorAdapter adapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        adapter.swap(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        ((RecyclerViewBehaviorAdapter.SimpleViewHolder)viewHolder).cardView.setBackgroundColor(Color.WHITE);
        return true;
    }

    @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.remove(viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        System.out.println("selected changed");
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        System.out.println("clear view");
    }
}
