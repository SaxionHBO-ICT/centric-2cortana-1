package com.example.larsmeulenbroek.kroegenapp.Model;

import com.example.larsmeulenbroek.kroegenapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars Meulenbroek on 5/23/2016.
 */
public class Route {

    //all route variables
    private String name;
    private String theme;
    private Picture picture;
    private List<Bar> bars = new ArrayList<>();

    //devine the categories pictures
    private Picture beer = new Picture("Beer", R.drawable.beer);
    private Picture karaoke = new Picture("Karaoke", R.drawable.karaoke);
    private Picture party = new Picture("Party", R.drawable.party);
    private Picture alternative = new Picture("Alternative", R.drawable.alternative);

    public Route(String name, String theme) {
        this.name = name;
        this.theme = theme;
        this.picture = setPictureForTheme();
    }

    //Setters

    public Picture setPictureForTheme() {
        switch (theme) {
            case "Beer":
                return beer;
            case "Karaoke":
                return karaoke;
            case "Party":
                return party;
            case "Alternative":
                return alternative;
            default:
                return alternative;
        }
    }

    //Getters

    public String getName() {
        return name;
    }

    public String getTheme() {
        return theme;
    }

    public Picture getPicture() {
        return picture;
    }

    public List<Bar> getBars() {
        return bars;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
