package ru.rubicon.myexamples.fragments;

/**
 * Created by Витя on 22.09.2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.rubicon.myexamples.R;

public class PagerViewFragment1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_view_fragment_1, container, false);
        TextView textView = (TextView) view.findViewById(R.id.detailsText);
        textView.setText("Проверка");
        return view;
    }
}