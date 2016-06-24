package com.example.larsmeulenbroek.kroegenapp.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.larsmeulenbroek.kroegenapp.Model.Bar;
import com.example.larsmeulenbroek.kroegenapp.Model.BarModel;
import com.example.larsmeulenbroek.kroegenapp.Model.Route;
import com.example.larsmeulenbroek.kroegenapp.Model.RouteModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lars Meulenbroek on 6/19/2016.
 */
/*
Perform background operation asynchronously, to get routes of the logged in user from in the database
*/
public class GetRoutesTask extends AsyncTask<String, Void, Void> {

    private static final String TAG_ROUTES = "routes";

    @Override
    protected Void doInBackground(String... arg0) {
        // Creating service handler class instance
        ServiceHandler sh = new ServiceHandler();

        // Making a request to url and getting response
        String url = "http://kroegenapp.azurewebsites.net/routes.php?user_id=" + arg0[0];

        String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

        Log.d("Response: ", "> " + jsonStr);

        if (jsonStr != null) {
            try {
                RouteModel.getInstance().clear();
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray routes = jsonObj.getJSONArray(TAG_ROUTES);

                // looping through All Favorites
                for (int i = 0; i < routes.length(); i++) {
                    JSONObject c = routes.getJSONObject(i);

                    Route route = new Route(c.getString("name"), c.getString("thema"));

                    String[] barIDlist = c.getString("bars").split(",");

                    for(int v = 0; v < barIDlist.length; v++) {
                        for(Bar b : BarModel.getInstance()) {
                            if(b.getBar_id() == Integer.parseInt(barIDlist[v])) {
                                route.getBars().add(b);
                            }
                        }
                    }
                    RouteModel.getInstance().add(route);
                }

            } catch (JSONException e) {

            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }
}
