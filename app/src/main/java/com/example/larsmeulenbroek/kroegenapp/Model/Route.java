package com.example.larsmeulenbroek.kroegenapp.Model;

import java.util.ArrayList;

/**
 * Created by Lars Meulenbroek on 5/23/2016.
 */
public class Route {

    private String name;
    private String theme;
    private Picture picture;
    private ArrayList<Bar> bars = new ArrayList<>();

    public Route(String theme, String name) {
        this.theme = theme;
        this.name = name;
    }
}
