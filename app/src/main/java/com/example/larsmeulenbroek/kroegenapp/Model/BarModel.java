package com.example.larsmeulenbroek.kroegenapp.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars Meulenbroek on 5/10/2016.
 */
/*
class which has 2 instances
1> the bar list
2> the favorite list
 */
public class BarModel {

    private static List<Bar> barList = new ArrayList<>();
    private static List<Bar> barListFavorite = new ArrayList<>();

    //get the barlist
    public static List<Bar> getInstance() {
        return barList;
    }
    //get the favoritelist
    public static List<Bar> getInstanceFavoriteList() {
        return barListFavorite;
    }

    //create favoritelist with a boolean which loops through barlist
    public static void sortBarOnFavorite() {
        for(int i = 0; i < barList.size(); i++) {
            if(barList.get(i).isFavorite()) {
                barListFavorite.add(barList.get(i));
            }
        }
    }

    //delete all content from favoritelist
    public static void clearFavoriteList() {
        barListFavorite.clear();
    }
}
