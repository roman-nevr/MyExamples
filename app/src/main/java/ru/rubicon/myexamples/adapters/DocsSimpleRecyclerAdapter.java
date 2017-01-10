package ru.rubicon.myexamples.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.rubicon.myexamples.R;

/**
 * Created by Витя on 25.08.2016.
 */
public class DocsSimpleRecyclerAdapter extends RecyclerView.Adapter<DocsSimpleRecyclerAdapter.SimpleViewHolder> {
    private List<String> mCatNames = new ArrayList<>();

    public DocsSimpleRecyclerAdapter(List<String> catNames) {
        this.mCatNames = catNames;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_scroll_docs_item,
                viewGroup, false);
        SimpleViewHolder viewHolder = new SimpleViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder simpleViewHolder, int i) {
    }

    @Override
    public int getItemCount() {
        return mCatNames == null ? 0 : mCatNames.size();
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            cardView.setUseCompatPadding(true);
        }
    }
}