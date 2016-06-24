package com.example.larsmeulenbroek.kroegenapp.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars Meulenbroek on 5/23/2016.
 */
/*
class which has 1 instance
1> the route list
 */
public class RouteModel {

    private static List<Route> routeList = new ArrayList<>();

    //get routelist
    public static List<Route> getInstance() {
        return routeList;
    }

}
