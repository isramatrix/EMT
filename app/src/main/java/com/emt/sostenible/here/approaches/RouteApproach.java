package com.emt.sostenible.here.approaches;

import com.emt.sostenible.view.SearchHeader;
import com.here.android.mpa.routing.CoreRouter;
import com.here.android.mpa.routing.Route;
import com.here.android.mpa.routing.RouteOptions;
import com.here.android.mpa.routing.RoutePlan;
import com.here.android.mpa.routing.RouteResult;

import java.util.List;

public abstract class RouteApproach {

    private final RouteOptions routeOptions;

    final int routes;

    public static RouteApproach getRouteApproach(SearchHeader.SearchType searchType, int routes)
    {
        if (searchType == SearchHeader.SearchType.DIRECT)
            return new ShortestRouteApproach(routes);

        if (searchType == SearchHeader.SearchType.GREENEST)
            return new GreenestRouteApproach(routes);

        return new FastestRouteApproach(routes);
    }

    RouteApproach(int routes)
    {
        this.routes = routes;
        this.routeOptions = getRouteOptions();
        routeOptions.setTransportMode(RouteOptions.TransportMode.PUBLIC_TRANSPORT);
    }

    public void calculateRoute(RoutePlan routePlan, OnRouteCalculatedListener listener)
    {
        OnRoutesCalculatedListener it = new OnRoutesCalculatedListener(this, listener);
        CoreRouter coreRouter = new CoreRouter();
        routePlan.setRouteOptions(routeOptions);
        coreRouter.calculateRoute(routePlan, it);
    }

    protected abstract RouteOptions getRouteOptions();

    protected abstract List<Route> filter(List<RouteResult> routeResult);

    public interface OnRouteCalculatedListener { void read(List<Route> route); }
}
