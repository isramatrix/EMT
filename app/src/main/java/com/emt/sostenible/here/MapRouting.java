package com.emt.sostenible.here;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.routing.CoreRouter;
import com.here.android.mpa.routing.RouteOptions;
import com.here.android.mpa.routing.RoutePlan;
import com.here.android.mpa.routing.RouteResult;
import com.here.android.mpa.routing.RouteWaypoint;
import com.here.android.mpa.routing.RoutingError;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Defines a set of point on the map and traces a route between them
 * as a public transport would do.
 */
public final class MapRouting implements CoreRouter.Listener {

    private final List<CoreRouter> coreRouters = new ArrayList<>();

    private final List<MapRoute> mapRoute = new ArrayList<>();

    private final List<RoutePlan> routeList;

    private final AtomicInteger counter;

    private OnCalculatedListener onCalculated;

    public MapRouting(GeoCoordinate... coordinates)
    {
        counter = new AtomicInteger(coordinates.length - 1);
        routeList = populateCoordenates(coordinates);

        RouteOptions routeOptions = new RouteOptions();
        routeOptions.setTransportMode(RouteOptions.TransportMode.PUBLIC_TRANSPORT);

        startRouting();
    }

    /**
     * Applies the changes and traces the route on the specified map.
     * @param map on which changes will be applied.
     */
    public void trace(Map map) {
        for (MapRoute route : mapRoute)
            map.addMapObject(route);
    }

    /**
     *  Programs an action to do when Route calculation is finished.
     */
    public void setOnCalculateRouteFinished(OnCalculatedListener onCalculatedListener)
    {
        onCalculated = onCalculatedListener;
    }

    /**
     * DO NOT CALL: Interface behaviour to do when the route is successfully calculated.
     * @param routeResult
     * @param error
     */
    @Override
    public void onCalculateRouteFinished(List<RouteResult> routeResult, RoutingError error)
    {
        // If the route was calculated successfully
        if (error == RoutingError.NONE) {
            // Render the route on the map
            mapRoute.add(new MapRoute(routeResult.get(0).getRoute()));
            if (counter.decrementAndGet() == 0) onCalculated.complete(this);
        }
        else {
            // Display a message indicating route calculation failure
        }
    }

    /**
     *
     * @param i
     */
    @Override
    public void onProgress(int i)
    {
    }

    private List<RoutePlan> populateCoordenates(GeoCoordinate... coordinates)
    {
        List<RoutePlan> routeList = new ArrayList<>();
        routeList.add(new RoutePlan());

        int i = 0;
        for (GeoCoordinate c : coordinates) {
            routeList.add(new RoutePlan());
            coreRouters.add(new CoreRouter());
            routeList.get(i).addWaypoint(new RouteWaypoint(c));
            routeList.get(++i).addWaypoint(new RouteWaypoint(c));
        }

        routeList.remove(i);

        return routeList;
    }

    public void startRouting()
    {
        for (int i = 0; i < coreRouters.size(); i++) {
            coreRouters.get(i).calculateRoute(routeList.get(i), this);
        }
    }


    public interface OnCalculatedListener
    {
        void complete(MapRouting mapRouting);
    }
}
