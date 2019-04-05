package com.emt.sostenible.view;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.emt.sostenible.R;
import com.emt.sostenible.logic.LocationService;
import com.here.android.mpa.common.GeoCoordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchHeader extends LinearLayout {

    public enum SearchType { FASTEST, DIRECT, GREENEST }

    private SearchType searchType;

    private AutoCompleteTextView destination;

    private AutoCompleteTextView origin;

    private ImageButton backButton;

    private ImageButton searchButton;

    private ImageButton locationButton;

    private RadioGroup routeType;

    private Map<String, GeoCoordinate> locations;

    private Map<String, GeoCoordinate> origins;

    private boolean locationEnabled;

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
        locationButton = (ImageButton) l1.getChildAt(2);

        locationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                locationEnabled = !locationEnabled;
                Map<String, GeoCoordinate> map = new HashMap<>();
                Location location = LocationService.getInstance(null).getActualLocation();
                GeoCoordinate geo = new GeoCoordinate(location.getLatitude(), location.getLongitude());
                origin.setHint(locationEnabled ? "Mi ubicación" : "Eligir origen");
                origin.setEnabled(!locationEnabled);
                origin.setHintTextColor(locationEnabled ? Color.BLUE : Color.GRAY);
                map.put("Mi ubicación", geo);
                origins = map;
            }
        });

        routeType = (RadioGroup) l2.getChildAt(1);
        ((RadioButton) routeType.getChildAt(0)).setChecked(true);
        ((RadioButton) routeType.getChildAt(0)).setOnClickListener(setSearchType(SearchType.FASTEST));
        ((RadioButton) routeType.getChildAt(1)).setOnClickListener(setSearchType(SearchType.DIRECT));
        ((RadioButton) routeType.getChildAt(2)).setOnClickListener(setSearchType(SearchType.GREENEST));

        searchType = SearchType.FASTEST;
        locations = new HashMap<>();
    }

    public void inflateAutoCompleteDestination(Map<String, GeoCoordinate> locations)
    {
        List<String> list = new LinkedList<String>();
        for(String temp : locations.keySet()){
            list.add(temp);
        }

        System.out.println(locations.keySet());
        this.locations = locations;

        // TODO: Inflate locations instead strings on autocomplete list.
        //destination.setAdapter( new ArrayAdapter<>(getContext(), list));
    }

    public void inflateAutoCompleteOrigins(Map<String, GeoCoordinate> locations)
    {
        System.out.println(locations.keySet());
        this.origins = locations;

        System.out.println(locations.keySet());

        // TODO: Inflate locations instead strings on autocomplete list.
        //destination.setAdapter( new ArrayAdapter<>(getContext(), list));
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

    /**
     * Sets an action to perform when destination text of the header has changed.
     * @param onTextChangedListener action to perform.
     */
    public void setOnDestinationChanged(final OnTextChangedListener onTextChangedListener) {
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

    /**
     * Sets an action to perform when origins text of the header has changed.
     * @param onTextChangedListener action to perform.
     */
    public void setOnOriginChanged(final OnTextChangedListener onTextChangedListener) {
        origin.addTextChangedListener(new TextWatcher() {
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

    /**
     * Sets an action to perform when search button was pressed.
     * @param listener
     */
    public void setOnSearchButtonClicked(final OnSearchButtonListener listener)
    {
        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (locations.size() == 0) return;
                if (origins.size() == 0) return;

                GeoCoordinate destine = (GeoCoordinate) locations.values().toArray()[0];
                GeoCoordinate origens = (GeoCoordinate) origins.values().toArray()[0];

                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (destination.isFocused()) imm.hideSoftInputFromWindow(destination.getWindowToken(), 0);
                else if (origin.isFocused()) imm.hideSoftInputFromWindow(origin.getWindowToken(), 0);

                if (destine != null) {
                    listener.onClick(destine, origens, searchType);
                    destination.setText("");
                    origin.setText("");
                    visibilityHeader(false);
                }
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
        void onClick(GeoCoordinate dest, GeoCoordinate ori, SearchType searchType);
    }
}


