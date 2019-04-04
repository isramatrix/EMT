package com.emt.sostenible.here.approaches;

import android.util.Pair;

import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.routing.CoreRouter;
import com.here.android.mpa.routing.Route;
import com.here.android.mpa.routing.RouteResult;
import com.here.android.mpa.routing.RoutingError;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

class OnRoutesCalculatedListener implements CoreRouter.Listener {

    private final RouteApproach.OnRouteCalculatedListener listener;

    private final RouteApproach routeApproach;

    OnRoutesCalculatedListener(RouteApproach routeApproach, RouteApproach.OnRouteCalculatedListener listener)
    {
        this.routeApproach = routeApproach;
        this.listener = listener;
    }

    @Override
    public void onCalculateRouteFinished(List<RouteResult> list, RoutingError routingError)
    {
        listener.read(routeApproach.filter(list));
    }

    @Override
    public void onProgress(int i) { }
}
