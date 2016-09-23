package ru.rubicon.myexamples.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.rubicon.myexamples.R;
import ru.rubicon.myexamples.views.DoublesideView;

/**
 * Created by Витя on 08.09.2016.
 */
public class WordCardsAdapter extends RecyclerView.Adapter<WordCardsAdapter.CardsViewHolder> {

    private ArrayList<WordPairs> wordPairsList;

    public WordCardsAdapter(ArrayList<WordPairs> wordPairsList) {
        this.wordPairsList = wordPairsList;
    }

    @Override
    public CardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_cards_item, parent, false);
        CardsViewHolder cardsViewHolder = new CardsViewHolder(view);
        return cardsViewHolder;
    }

    @Override
    public void onBindViewHolder(CardsViewHolder holder, int position) {
        holder.doublesideView.setFrontSideString(wordPairsList.get(position).getFront());
        holder.doublesideView.setBackSideString(wordPairsList.get(position).getBack());
    }

    @Override
    public int getItemCount() {
        return wordPairsList == null ? 0 : wordPairsList.size();
        //return mCatNames == null ? 0 : mCatNames.size();
    }

    public class CardsViewHolder extends RecyclerView.ViewHolder{
        DoublesideView doublesideView;
        public CardsViewHolder(View itemView) {
            super(itemView);
            doublesideView = (DoublesideView) itemView.findViewById(R.id.dw_word_card);
            doublesideView.setRotationTime(500);
        }
    }

    public static class WordPairs {
        private String front, back;
        public WordPairs(String front, String back) {
            this.front = front;
            this.back = back;
        }
        public String getFront() {
            return front;
        }

        public void setFront(String front) {
            this.front = front;
        }

        public String getBack() {
            return back;
        }

        public void setBack(String back) {
            this.back = back;
        }
    }

}
