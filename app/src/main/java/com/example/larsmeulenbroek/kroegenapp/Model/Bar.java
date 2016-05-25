package com.example.larsmeulenbroek.kroegenapp.Model;

import com.example.larsmeulenbroek.kroegenapp.Tasks.CreateCategoryImageTask;

import java.util.ArrayList;

/**
 * Created by Lars Meulenbroek on 5/9/2016.
 */
public class Bar {

    private String name;
    private String theme;
    private Picture picture;
    private String description;
    private ArrayList<Comment> comments = new ArrayList<>();


    private String url_str;

    private boolean isFavorite = false;


    public Bar(String name, String theme) {
        this.name = name;
        this.theme = theme;
        this.picture = setPictureForTheme();
    }

    public Picture setPictureForTheme() {
        CreateCategoryImageTask cblvt = new CreateCategoryImageTask(new CreateCategoryImageTask.AsyncResponse() {
            @Override
            public void processFinish(Picture image) {
                picture = image;
            }
        });
        cblvt.execute(this.theme);
        return picture;
    }

    public String getName() {
        return name;
    }

    public String getTheme() {
        return theme;
    }

    public Picture getPicture() {
        return picture;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
}
