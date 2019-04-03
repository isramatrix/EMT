package com.emt.sostenible.here;

import com.emt.sostenible.logic.LocationService;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.Identifier;
import com.here.android.mpa.common.ViewObject;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapGesture;
import com.here.android.mpa.mapping.MapProxyObject;
import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.mapping.TransitAccessObject;
import com.here.android.mpa.mapping.TransitLineObject;
import com.here.android.mpa.mapping.TransitStopInfo;
import com.here.android.mpa.mapping.TransitStopObject;
import com.here.android.mpa.routing.CoreRouter;
import com.here.android.mpa.routing.RouteOptions;
import com.here.android.mpa.routing.RoutePlan;
import com.here.android.mpa.routing.RouteResult;
import com.here.android.mpa.routing.RouteWaypoint;
import com.here.android.mpa.routing.RoutingError;

import java.util.ArrayList;
import java.util.List;

import static com.here.android.mpa.mapping.MapProxyObject.Type.TRANSIT_ACCESS;
import static com.here.android.mpa.mapping.MapProxyObject.Type.TRANSIT_LINE;
import static com.here.android.mpa.mapping.MapProxyObject.Type.TRANSIT_STOP;

public class MapTouchAdapter extends MapGesture.OnGestureListener.OnGestureListenerAdapter {

    private final Map map;

    public MapTouchAdapter(Map map) {
        this.map = map;
    }

    @Override
    public boolean onMapObjectsSelected(List<ViewObject> list) {

        List<Identifier> identifierList = new ArrayList<>();

        for (ViewObject obj : list) {
            if (obj.getBaseType() == ViewObject.Type.PROXY_OBJECT) {
                MapProxyObject proxyObj = (MapProxyObject) obj;

                if (proxyObj.getType() == MapProxyObject.Type.TRANSIT_STOP) {
                    TransitStopObject transitStopObj
                            = (TransitStopObject) proxyObj;
                    TransitStopInfo transitStopInfo
                            = transitStopObj.getTransitStopInfo();


                }
            }
        }

        return true;
    }
}
