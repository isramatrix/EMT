package com.emt.sostenible.here.approaches;

import com.emt.sostenible.here.MapController;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.TransitType;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapObject;
import com.here.android.mpa.routing.ConsumptionParameters;
import com.here.android.mpa.routing.DynamicPenalty;
import com.here.android.mpa.routing.Route;
import com.here.android.mpa.routing.RouteOptions;
import com.here.android.mpa.routing.RouteResult;
import com.here.android.mpa.routing.RouteWaypoint;

import java.util.ArrayList;
import java.util.List;

class GreenestRouteApproach extends RouteApproach {

    GreenestRouteApproach(int routes) {
        super(routes);
    }

    protected RouteOptions getRouteOptions()
    {
        RouteOptions routeOptions = new RouteOptions();
        routeOptions.setRouteCount(routes);
        return routeOptions;
    }

    protected List<Route> filter(List<RouteResult> routeResult)
    {
        List<Route> routeList = new ArrayList<>();
        for(int i = 0; i < routes && i < routeResult.size(); i++) {
            Route r;
            routeList.add(r = routeResult.get(i).getRoute());
            ConsumptionParameters params = ConsumptionParameters.createDefaultConsumptionParameters();
            params.setAccelerationMultiplier(0.2);
            params.setDecelerationMultiplier(0.2);
            //params.setTurnTimeMultiplier(1.5);

            DynamicPenalty dynamicPenalty = new DynamicPenalty();
            dynamicPenalty.setTrafficPenaltyMode(Route.TrafficPenaltyMode.AVOID_LONG_TERM_CLOSURES);

            System.out.println(r.getConsumption(params, dynamicPenalty).getFirstAvailableConsumptionIndex());
        }

        // TODO: Filter by params to get the greenest result.
        return routeList;
    }


    // r.getRouteGeometry() --> points of the path

}