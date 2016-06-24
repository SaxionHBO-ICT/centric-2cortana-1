package com.example.larsmeulenbroek.kroegenapp.Tasks;

import android.os.AsyncTask;

import com.example.larsmeulenbroek.kroegenapp.Model.Bar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars Meulenbroek on 6/19/2016.
 */
/*
Perform background operation asynchronously, to remove a favorite from the users favorite list in the database
*/
public class RemoveFavoriteTask extends AsyncTask<Bar, Void, Void> {
    private String user_id;

    public RemoveFavoriteTask(String user_id) {
        this.user_id = user_id;
    }

    @Override
    protected Void doInBackground(Bar... params) {
        ServiceHandler sh = new ServiceHandler();
        String url = "http://kroegenapp.azurewebsites.net/remove_favorite.php";

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("bar_id", "" + params[0].getBar_id()));
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));

        sh.makeServiceCall(url, ServiceHandler.POST, nameValuePairs);

        return null;
    }
}
