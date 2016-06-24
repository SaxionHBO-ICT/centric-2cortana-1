package com.example.larsmeulenbroek.kroegenapp.Model;

import java.util.List;

/**
 * Created by Lars Meulenbroek on 5/30/2016.
 */
public class User {

    //devine all variables
    private String username;
    private String email;
    private List<Bar> Favorites;
    private List<Route> myRoutes;

    //setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFavorites(List<Bar> favorites) {
        Favorites = favorites;
    }

    public void setMyRoutes(List<Route> myRoutes) {
        this.myRoutes = myRoutes;
    }

    //getters

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

}
