package com.emt.sostenible.here;

import com.emt.sostenible.here.approaches.RouteApproach;
import com.emt.sostenible.view.SearchHeader;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.routing.RoutePlan;
import com.here.android.mpa.routing.RouteWaypoint;

import java.util.List;

/**
 * Creates a petition to the here service which will trace a path
 * between the given GeoCoordinates using the type of search the user has
 * selected.
 *
 * Take into account this petition is service-dependant so the returned paths will be
 * different following the traffic, bus-scheduling and other environment variables.
 */
public final class EMTRoutePlanner {

    private final RouteApproach routeApproach;

    private final RoutePlan routePlan;

    private final GeoCoordinate origin;

    private final GeoCoordinate destine;

    /**
     * Creates a petition to the here service which will trace a specified number of
     * paths between the given GeoCoordinates using the type of search the user has
     * selected.
     *
     * Take into account this petition is service-dependant so the returned paths will be
     * different following the traffic, bus-scheduling and other environment variables.
     *
     * @param type of path to consider.
     * @param routes number of routes will be traced.
     * @param origin the origin coordinate.
     * @param destine the destine coordinate.
     */
    public EMTRoutePlanner(SearchHeader.SearchType type, int routes, GeoCoordinate origin, GeoCoordinate destine)
    {
        routePlan = new RoutePlan();
        routePlan.addWaypoint(new RouteWaypoint(this.origin = origin));
        routePlan.addWaypoint(new RouteWaypoint(this.destine = destine));

        this.routeApproach = RouteApproach.getRouteApproach(type, routes);
    }

    /**
     * Creates a petition to the here service which will trace a path
     * between the given GeoCoordinates using the type of search the user has
     * selected.
     *
     * Take into account this petition is service-dependant so the returned paths will be
     * different following the traffic, bus-scheduling and other environment variables.
     *
     * @param type of path to consider.
     * @param origin the origin coordinate.
     * @param destine the destine coordinate.
     */
    public EMTRoutePlanner(SearchHeader.SearchType type, GeoCoordinate origin, GeoCoordinate destine)
    {
        this(type, 1, origin, destine);
    }

    /**
     * Once the path is loaded, this will trace the path on the specified map.
     * @param map on which path will be traced.
     */
    public void trace(final MapController map) {

        routeApproach.calculateRoute(routePlan, new RouteApproach.OnRouteCalculatedListener() {
            @Override
            public void read(List<MapRoute> routes) {
                map.addRoutes(routes);
            }
        });
    }

    /**
     * Once the path is loaded, this will trace the path on the specified map, with all markers
     * the current path passes though.
     * @param map on which path will be traced.
     */
    public void traceWithMarks(final MapController map)
    {
        routeApproach.calculateRoute(routePlan, new RouteApproach.OnRouteCalculatedListener() {
            @Override
            public void read(List<MapRoute> routes) {
                map.addRoutes(routes);
                map.addMarker(origin.getLatitude(), origin.getLongitude());
                map.addMarker(destine.getLatitude(), destine.getLongitude());
            }
        });
    }

    public void traceWithColor(final MapController map, final int color)
    {
        routeApproach.calculateRoute(routePlan, new RouteApproach.OnRouteCalculatedListener() {
            @Override
            public void read(List<MapRoute> routes) {
                for (MapRoute route : routes) route.setColor(color);
                map.addRoutes(routes);
            }
        });
    }
}
