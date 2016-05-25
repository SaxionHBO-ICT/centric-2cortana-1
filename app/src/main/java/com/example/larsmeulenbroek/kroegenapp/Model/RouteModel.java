package com.example.larsmeulenbroek.kroegenapp.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars Meulenbroek on 5/23/2016.
 */
public class RouteModel {

    private static List<Route> routeList = new ArrayList<>();
    private static boolean addRoutes = true;


    public static List<Route> getInstance() {
        if(addRoutes) {
            addRoutes();
            addRoutes = false;
        }
        return routeList;
    }

    private static void addRoutes() {
        routeList.add(new Route("Epic", "best Route ever"));
    }
}
