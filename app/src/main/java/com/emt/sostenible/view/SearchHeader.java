package com.emt.sostenible.view;

import android.content.Context;
import android.media.Image;
import android.os.Parcel;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class SearchHeader extends LinearLayout {

    private AutoCompleteTextView destination;

    private AutoCompleteTextView origin;

    private ImageButton backButton;

    private ImageButton searchButton;

    private ImageButton locationButton;

    private RadioGroup routeType;

    public SearchHeader(Context context) {
        super(context);
    }

    public SearchHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Finalize inflating a view from XML.  This is called as the last phase
     * of inflation, after all child views have been added.
     *
     * <p>Even if the subclass overrides onFinishInflate, they should always be
     * sure to call the super method, so that we get called.
     */
    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        LinearLayout l0 = (LinearLayout) getChildAt(0);
        LinearLayout l1 = (LinearLayout) getChildAt(1);
        LinearLayout l2 = (LinearLayout) getChildAt(2);

        backButton = (ImageButton) l0.getChildAt(0);
        backButton.setOnClickListener(closeListener());
        destination = (AutoCompleteTextView) l0.getChildAt(1);
        searchButton = (ImageButton) l0.getChildAt(2);

        origin = (AutoCompleteTextView) l1.getChildAt(1);
        locationButton = (ImageButton) l2.getChildAt(2);

        routeType = (RadioGroup) l2.getChildAt(1);
    }

    public void inflateAutoCompleteDestination(List<String> list)
    {
        list = new ArrayList<>();
        list.add("Valencia");
        list.add("Valladolid");
        list.add("Valverde");

        // TODO: Inflate locations instead strings on autocomplete list.
        //destination.setAdapter( new ArrayAdapter<>(getContext(), list));
    }

    public void onDestinationChanged(final OnTextChangedListener onTextChangedListener) {
        destination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextChangedListener.changed(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }


    public void visibilityHeader(boolean visible)
    {
        setVisibility(visible ? VISIBLE : INVISIBLE);
    }


    private OnClickListener closeListener()
    {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                visibilityHeader(false);
            }
        };
    }

    public interface OnTextChangedListener
    {
        void changed(String text);
    }
}
