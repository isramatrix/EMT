package com.emt.sostenible.here.approaches;

import com.here.android.mpa.routing.Route;
import com.here.android.mpa.routing.RouteOptions;
import com.here.android.mpa.routing.RouteResult;

import java.util.ArrayList;
import java.util.List;

class FastestRouteApproach extends RouteApproach {

    FastestRouteApproach(int routes) {
        super(routes);
    }

    protected RouteOptions getRouteOptions()
    {
        RouteOptions routeOptions = new RouteOptions();
        routeOptions.setRouteType(RouteOptions.Type.FASTEST);
        return routeOptions;
    }

    protected List<Route> filter(List<RouteResult> routeResult)
    {
        List<Route> routeList = new ArrayList<>();
        for(int i = 0; i < routes && i < routeResult.size(); i++) routeList.add(routeResult.get(i).getRoute());
        return routeList;
    }
}