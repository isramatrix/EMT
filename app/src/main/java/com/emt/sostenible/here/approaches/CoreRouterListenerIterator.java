package com.emt.sostenible.here.approaches;

import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapObject;
import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.routing.CoreRouter;
import com.here.android.mpa.routing.Route;
import com.here.android.mpa.routing.RouteResult;
import com.here.android.mpa.routing.RoutingError;

import java.util.ArrayList;
import java.util.List;

class CoreRouterListenerIterator implements CoreRouter.Listener {

    private final RouteApproach.OnRouteCalculatedListener listener;

    private final RouteApproach routeApproach;

    CoreRouterListenerIterator(RouteApproach routeApproach, RouteApproach.OnRouteCalculatedListener listener)
    {
        this.routeApproach = routeApproach;
        this.listener = listener;
    }

    @Override
    public void onCalculateRouteFinished(List<RouteResult> list, RoutingError routingError)
    {
        List<MapRoute> routeList = new ArrayList<>();
        for (Route route : routeApproach.filter(list)) routeList.add(new MapRoute(route));
        listener.read(routeList);
    }

    @Override
    public void onProgress(int i) {

    }
}
