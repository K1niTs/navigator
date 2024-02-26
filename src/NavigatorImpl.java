import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class NavigatorImpl implements Navigator {
    private CustomMap<String, Route> routes;
    private List<Route> favoriteRoutes;

    public NavigatorImpl() {
        this.routes = new CustomMap<>();
        this.favoriteRoutes = new ArrayList<>();
    }

    @Override
    public void addRoute(Route route) {
        boolean isDuplicate = false;

        for (CustomMap.Entry<String, Route> entry : routes.getTable()) {
            if (entry != null && entry.getValue().equals(route)) {
                isDuplicate = true;
                break;
            }
        }
        if (!isDuplicate) {
            routes.put(route.getId(), route);
            if (route.isFavorite()) {
                favoriteRoutes.add(route);
            }
        }
    }

    @Override
    public void removeRoute(String routeId) {
        Route route = routes.get(routeId);
        if (route != null) {
            routes.remove(routeId);
            favoriteRoutes.remove(route);
        }
    }

    @Override
    public boolean contains(Route route) {
        return routes.get(route.getId()) != null;
    }

    @Override
    public int size() {
        return routes.size();
    }

    @Override
    public Route getRoute(String routeId) {
        return routes.get(routeId);
    }

    @Override
    public void chooseRoute(String routeId) {
        Route route = routes.get(routeId);
        if (route != null) {
            route.setPopularity(route.getPopularity() + 1);
        }
    }

    @Override
    public Iterable<Route> searchRoutes(String startPoint, String endPoint) {
        List<Route> resultRoutes = new ArrayList<>();

        for (CustomMap.Entry<String, Route> entry : routes.getTable()) {
            if (entry != null) {
                Route route = entry.getValue();
                List<String> locationPoints = route.getLocationPoints();
                int startId = locationPoints.indexOf(startPoint);
                int endId = locationPoints.indexOf(endPoint);

                if (startId != -1 && endId != -1 && startId <= endId) {
                    resultRoutes.add(route);
                }
            }
        }
        resultRoutes.sort(Comparator.<Route, Boolean>comparing(Route::getFavourite)
                .thenComparingDouble(Route::getDistance)
                .thenComparingInt(Route::getPopularity).reversed());


        return resultRoutes;
    }

    @Override
    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
        List<Route> resultRoutes = new ArrayList<>();
        for (Route route : favoriteRoutes) {
            List<String> locationPoints = route.getLocationPoints();
            if (!locationPoints.isEmpty() && !locationPoints.get(0).equals(destinationPoint)) {
                resultRoutes.add(route);
            }
        }

        resultRoutes.sort(Comparator.comparingDouble(Route::getDistance).reversed()
                .thenComparingInt(Route::getPopularity).reversed());

        return resultRoutes;
    }

    @Override
    public Iterable<Route> getTop3Routes() {
        List<Route> allRoutes = new ArrayList<>(routes.size());
        for (CustomMap.Entry<String, Route> entry : routes.getTable()) {
            if (entry != null) {
                allRoutes.add(entry.getValue());
            }
        }
        allRoutes.sort(Comparator.comparingInt(Route::getPopularity).reversed()
                .thenComparingDouble(Route::getDistance)
                .thenComparingInt(route -> route.getLocationPoints().size()));

        return allRoutes.subList(0, Math.min(allRoutes.size(), 3));
    }


}
