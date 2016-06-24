package com.example.larsmeulenbroek.kroegenapp.Tasks;

import android.os.AsyncTask;

import com.example.larsmeulenbroek.kroegenapp.Model.Route;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars Meulenbroek on 6/19/2016.
 */

/*
Perform background operation asynchronously, to insert a route in the database
*/
public class InsertRouteTask extends AsyncTask<Route, Void, Void> {

    private String user_id;
    private String bars;

    public InsertRouteTask(String user_id, String bars) {
        this.user_id = user_id;
        this.bars = bars;
    }

    @Override
    protected Void doInBackground(Route... params) {
        ServiceHandler sh = new ServiceHandler();
        String url = "http://kroegenapp.azurewebsites.net/insert_route.php";

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
        nameValuePairs.add(new BasicNameValuePair("name", params[0].getName()));
        nameValuePairs.add(new BasicNameValuePair("thema", params[0].getTheme()));
        nameValuePairs.add(new BasicNameValuePair("bars", bars));
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));

        sh.makeServiceCall(url, ServiceHandler.POST, nameValuePairs);

        return null;
    }
}
