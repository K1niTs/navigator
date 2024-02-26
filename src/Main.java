import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Navigator navigator = new NavigatorImpl();

        addRoutes(navigator);

        System.out.println("Navigator Size: " + navigator.size());

        String routeIdToGet = "Route2";
        Route routeById = navigator.getRoute(routeIdToGet);
        if (routeById != null) {
            System.out.println("\nRoute with id " + routeIdToGet + ": " + routeById);
        } else {
            System.out.println("\nRoute with id " + routeIdToGet + " not found.");
        }

        Route routeToCheck = new Route("Route2", 0.0, 0, false, Arrays.asList("CityA", "CityB", "CityC"));
        boolean containsRoute = navigator.contains(routeToCheck);
        System.out.println("\nNavigator contains Route2: " + containsRoute);

        navigator.chooseRoute("Route2");
        navigator.chooseRoute("Route8");
        navigator.chooseRoute("Route8");
        navigator.chooseRoute("Route8");
        navigator.chooseRoute("Route12");
        navigator.chooseRoute("Route12");


        System.out.println("\nSearch Routes from CityA to CityC:");
        printRoutes(navigator.searchRoutes("CityA", "CityC"));

        String destinationPoint = "CityC";
        System.out.println("\nFavorite Routes to " + destinationPoint + ":");
        printRoutes(navigator.getFavoriteRoutes(destinationPoint));

        System.out.println("\nTop 3 Routes:");
        printRoutes(navigator.getTop3Routes());

        navigator.removeRoute("Route5");

        System.out.println("\nAfter removing Route5:");
        System.out.println("Search Routes from CityA to CityC:");
        printRoutes(navigator.searchRoutes("CityA", "CityC"));
    }

    private static void addRoutes(Navigator navigator) {
        List<String> locationPoints1 = Arrays.asList("CityA", "CityB", "CityC");
        Route route1 = new Route("Route2", 30.0, 0, false, locationPoints1);

        List<String> locationPoints2 = Arrays.asList("CityA", "CityD", "CityC");
        Route route2 = new Route("Route5", 60.0, 0, true, locationPoints2);

        List<String> locationPoints3 = Arrays.asList("CityE", "CityF", "CityG", "CityC");
        Route route3 = new Route("Route8", 40.0, 0, false, locationPoints3);

        List<String> locationPoints4 = Arrays.asList("CityA", "CityH", "CityI", "CityJ", "CityC");
        Route route4 = new Route("Route10", 70.0, 0, true, locationPoints4);

        List<String> locationPoints5 = Arrays.asList("CityK", "CityL", "CityC");
        Route route5 = new Route("Route12", 50.0, 0, true, locationPoints5);

        List<String> locationPoints6 = Arrays.asList("CityA", "CityB", "CityC");
        Route route6 = new Route("Route2", 30.0, 0, false, locationPoints6);
        List<String> locationPoints7 = Arrays.asList("CityA", "CityB", "CityC");
        Route route7 = new Route("Route1111111", 3333.0, 0, false, locationPoints7);

        navigator.addRoute(route1);
        navigator.addRoute(route2);
        navigator.addRoute(route3);
        navigator.addRoute(route4);
        navigator.addRoute(route5);
        navigator.addRoute(route6);
        navigator.addRoute(route7);

    }

    private static void printRoutes(Iterable<Route> routes) {
        for (Route route : routes) {
            System.out.println(route);
        }
    }
}
