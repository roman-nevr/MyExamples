package ru.rubicon.myexamples.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import ru.rubicon.myexamples.R;

/**
 * Created by roma on 30.08.2016.
 */
public class SideSpinner extends LinearLayout {
    private Button mPreviousButton;
    private Button mNextButton;

    public SideSpinner(Context context) {
        super(context);
        initializeViews(context);
    }

    public SideSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public SideSpinner(Context context,
                       AttributeSet attrs,
                       int defStyle) {
        super(context, attrs, defStyle);
        initializeViews(context);
    }

    /**
     * Inflates the views in the layout.
     *
     * @param context
     *           the current context for the view.
     */
    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sidespinner_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // Sets the images for the previous and next buttons. Uses
        // built-in images so you don't need to add images, but in
        // a real application your images should be in the
        // application package so they are always available.
        mPreviousButton = (Button) this
                .findViewById(R.id.sidespinner_view_previous);
        mPreviousButton
                .setBackgroundResource(android.R.drawable.ic_media_previous);

        mNextButton = (Button)this
                .findViewById(R.id.sidespinner_view_next);
        mNextButton
                .setBackgroundResource(android.R.drawable.ic_media_next);
    }
}
