package com.emt.sostenible.here.approaches;

import com.here.android.mpa.routing.Route;
import com.here.android.mpa.routing.RouteOptions;
import com.here.android.mpa.routing.RouteResult;

import java.util.ArrayList;
import java.util.List;

class GreenestRouteApproach extends RouteApproach {

    GreenestRouteApproach(int routes) {
        super(routes);
    }

    protected RouteOptions getRouteOptions()
    {
        RouteOptions routeOptions = new RouteOptions();
        routeOptions.setRouteType(RouteOptions.Type.SHORTEST);
        return routeOptions;
    }

    protected List<Route> filter(List<RouteResult> routeResult)
    {
        List<Route> routeList = new ArrayList<>();
        for(int i = 0; i < routes && i < routeResult.size(); i++) routeList.add(routeResult.get(i).getRoute());
        return routeList;

        // TODO: Filter by params to get the greenest result.
    }


}