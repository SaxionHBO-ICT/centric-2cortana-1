package com.example.larsmeulenbroek.kroegenapp.Model;

/**
 * Created by Lars Meulenbroek on 6/17/2016.
 */
/*
class which has 1 instance
1> the logged in user
 */
public class UserModel {

    public static User loggedInUser = new User();

    //get the user
    public static User getInstance() {
        return loggedInUser;
    }

}
