package com.emt.sostenible.view;

import android.animation.Animator;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class SearchHeader extends LinearLayout {

    public enum SearchType { FASTEST, DIRECT, GREENEST }

    private SearchType searchType;

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
        ((RadioButton) routeType.getChildAt(0)).setChecked(true);
        ((RadioButton) routeType.getChildAt(0)).setOnClickListener(setSearchType(SearchType.FASTEST));
        ((RadioButton) routeType.getChildAt(1)).setOnClickListener(setSearchType(SearchType.DIRECT));
        ((RadioButton) routeType.getChildAt(2)).setOnClickListener(setSearchType(SearchType.GREENEST));

        searchType = SearchType.FASTEST;
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

    public void visibilityHeader(final boolean visible)
    {
        int x = searchButton.getRight();
        int y = searchButton.getBottom();

        int opened = (int) Math.hypot(getWidth(), getHeight());

        int startRadius = visible ? 0 : opened;
        int endRadius = visible ? opened : 0;

        Animator anim = ViewAnimationUtils.createCircularReveal(this, x, y, startRadius, endRadius);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (visible) setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!visible) setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) { }
            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
        anim.start();
    }

    public void setOnSearchButtonClicked(final OnSearchButtonListener listener)
    {
        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(destination.getText().toString(), searchType);
                visibilityHeader(false);
            }
        });
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

    private OnClickListener setSearchType(final SearchType s)
    {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchType = s;
            }
        };
    }

    public interface OnTextChangedListener
    {
        void changed(String text);
    }

    public interface OnSearchButtonListener
    {
        void onClick(String text, SearchType searchType);
    }
}
