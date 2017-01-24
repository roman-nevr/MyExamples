package ru.rubicon.myexamples.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.rubicon.myexamples.R;

/**
 * Created by Витя on 25.08.2016.
 */
public class RecyclerViewBehaviorAdapter extends RecyclerView.Adapter<RecyclerViewBehaviorAdapter.SimpleViewHolder> {
    private List<String> values = new ArrayList<>();

    public RecyclerViewBehaviorAdapter(List<String> catNames) {
        this.values = catNames;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_scroll_item,
                viewGroup, false);
        final SimpleViewHolder viewHolder = new SimpleViewHolder(view);
        /*view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override public boolean onLongClick(View v) {
                int adapterPosition = viewHolder.getAdapterPosition();
                if(adapterPosition != RecyclerView.NO_POSITION){
                }
                return true;
            }
        });*/

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder simpleViewHolder, int i) {
        simpleViewHolder.titleTextView.setText(values.get(i));
    }

    @Override
    public int getItemCount() {
        return values == null ? 0 : values.size();
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView titleTextView;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            cardView.setUseCompatPadding(true);
            titleTextView = (TextView) itemView.findViewById(R.id.card_textView_name);
        }
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    public void swap(int firstPosition, int secondPosition){
        Collections.swap(values, firstPosition, secondPosition);
        notifyItemMoved(firstPosition, secondPosition);
    }
}