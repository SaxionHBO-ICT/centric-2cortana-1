package com.example.larsmeulenbroek.kroegenapp.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars Meulenbroek on 5/10/2016.
 */
public class BarModel {

    private static List<Bar> barList = new ArrayList<>();
    private static boolean addBars = true;


    public static List<Bar> getInstance() {
        if(addBars) {
            addBars();
            addBars = false;
        }
        return barList;
    }

    public static void addBars() {
        barList.add(new Bar("de Tijd", "Party"));
        barList.add(new Bar("de Heks", "Beer"));
        barList.add(new Bar("Time to Party", "Party"));
        barList.add(new Bar("de Hip", "Alternative"));
        barList.add(new Bar("Dorst", "Party"));
        barList.add(new Bar("Burger Weeshuis", "Alternative"));
    }


}
