package com.example.larsmeulenbroek.kroegenapp.Model;

import com.example.larsmeulenbroek.kroegenapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars Meulenbroek on 5/9/2016.
 */
public class Bar {

    //all bar variables
    private int bar_id;
    private String name;
    private String theme;
    private Picture picture;
    private String description;
    private String openingHours;
    private List<Comment> comments = new ArrayList<>();

    private Boolean addToRoute = false;

    private String url_str;
    private String adres;

    private boolean isFavorite = false;

    //devine the categories pictures
    private Picture beer = new Picture("Beer", R.drawable.beer);
    private Picture karaoke = new Picture("Karaoke", R.drawable.karaoke);
    private Picture party = new Picture("Party", R.drawable.party);
    private Picture alternative = new Picture("Alternative", R.drawable.alternative);

    /*
    Constructor to create a bar from a JSONObject
    and calls all the needed methods to create a full functional bar
     */
    public Bar(JSONObject jsonObject) throws JSONException {
        this.bar_id = jsonObject.getInt("bar_id");
        this.name = jsonObject.getString("name");
        this.theme = jsonObject.getString("theme");
        this.description = jsonObject.getString("description");
        this.adres = jsonObject.getString("adres");
        this.url_str = jsonObject.getString("url");
        this.openingHours = jsonObject.getString("opening_hours");
        this.picture = setPictureForTheme();
        createOpeningHoursForSelectedTextview();

    }

    /*
    Method for converting the OpeningHours string to a line that fits in the Textview
     */
    private void createOpeningHoursForSelectedTextview() {
        String openinghours = "";
        String[] split = this.openingHours.split(", ");
        for(int i = 0; i < split.length; i++) {
            openinghours = openinghours + split[i] + "\n";
        }
        this.openingHours = "Openingstijden: " + "\n" + openinghours;
    }


    /*
    Method that is called while changing the background color of a selected bar with a boolean
     */
    public void setAddToRoute() {
        if(!addToRoute) {
            addToRoute = true;
        } else {
            addToRoute = false;
        }
    }


    //setters

    public Picture setPictureForTheme() {
                switch (theme) {
            case "Beer": return beer;
            case "Karaoke": return karaoke;
            case "Party": return party;
            case "Alternative": return alternative;
            default: return alternative;
        }
    }

    public void setFavorite() {
        if(!isFavorite) {
            isFavorite = true;
        } else {
            isFavorite = false;
        }
    }


    //getters

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

    public String getDescription() {
        return description;
    }

    public String getUrl_str() {
        return url_str;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getAdres() {
        return adres;
    }

    public Boolean getAddToRoute() {
        return addToRoute;
    }

    public int getBar_id() {
        return bar_id;
    }

    public String getOpeningHours() {
        return openingHours;
    }

}
