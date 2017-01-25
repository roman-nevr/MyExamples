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

    @Override public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int flags = super.getMovementFlags(recyclerView, viewHolder);
        //System.out.println("flags: " + flags + ", string: " + viewHolder.getAdapterPosition());
        return flags;
    }

    @Override public int getDragDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dirs = super.getDragDirs(recyclerView, viewHolder);
        ((RecyclerViewBehaviorAdapter.SimpleViewHolder)viewHolder).cardView.setBackgroundColor(Color.YELLOW);
        //System.out.println("dirs: " + dirs + ", string: " + viewHolder.getAdapterPosition());
        return dirs;
    }


}
