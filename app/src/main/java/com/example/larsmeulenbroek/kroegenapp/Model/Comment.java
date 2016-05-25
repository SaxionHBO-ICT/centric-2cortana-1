package com.example.larsmeulenbroek.kroegenapp.Model;

/**
 * Created by Lars Meulenbroek on 5/23/2016.
 */
public class Comment {

    private String name;
    private String text;

    public Comment(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
