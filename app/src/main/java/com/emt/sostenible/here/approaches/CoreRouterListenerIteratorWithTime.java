package com.emt.sostenible.here.approaches;

import android.util.Pair;

import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapObject;
import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.routing.CoreRouter;
import com.here.android.mpa.routing.Route;
import com.here.android.mpa.routing.RouteResult;
import com.here.android.mpa.routing.RoutingError;

import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CoreRouterListenerIteratorWithTime implements CoreRouter.Listener {

    private final RouteApproach.OnRouteCalculatedListenerWithTime listener;

    private final RouteApproach routeApproach;

    CoreRouterListenerIteratorWithTime(RouteApproach routeApproach, RouteApproach.OnRouteCalculatedListenerWithTime listener)
    {
        this.routeApproach = routeApproach;
        this.listener = listener;
    }

    @Override
    public void onCalculateRouteFinished(List<RouteResult> list, RoutingError routingError)
    {
        Map<MapRoute, Pair<String, String>> routeList = new HashMap<>();

        Calendar calendar = Calendar.getInstance();
        String departure = calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);

        for (Route route : routeApproach.filter(list)) {
            calendar.add(Calendar.SECOND, route.getTtaIncludingTraffic(Route.WHOLE_ROUTE).getDuration());
            String arrival = calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
            routeList.put(new MapRoute(route), new Pair<>(departure, arrival));
        }

        listener.read(routeList);
    }

    @Override
    public void onProgress(int i) {

    }
}
