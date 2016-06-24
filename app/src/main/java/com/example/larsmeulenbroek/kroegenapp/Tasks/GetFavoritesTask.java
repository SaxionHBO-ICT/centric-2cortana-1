package com.example.larsmeulenbroek.kroegenapp.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.larsmeulenbroek.kroegenapp.Model.BarModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lars Meulenbroek on 6/18/2016.
 */

/*
Perform background operation asynchronously, to get favorites of a Usern from in the database
*/
public class GetFavoritesTask extends AsyncTask<String, Void, Void> {

    private static final String TAG_FAVORITES = "favorites";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(String... arg0) {
        // Creating service handler class instance
        ServiceHandler sh = new ServiceHandler();

        // Making a request to url and getting response
        String url = "http://kroegenapp.azurewebsites.net/favorites.php?user_id=" + arg0[0];

        String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

        Log.d("Response: ", "> " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray bar_id = jsonObj.getJSONArray(TAG_FAVORITES);

                // looping through All Favorites
                for (int i = 0; i < bar_id.length(); i++) {
                    JSONObject c = bar_id.getJSONObject(i);

                    for(int y = 0; y < BarModel.getInstance().size(); y++) {
                        if(BarModel.getInstance().get(y).getBar_id() == c.getInt("bar_id")) {
                            if(!BarModel.getInstance().get(y).isFavorite()) {
                                BarModel.getInstance().get(y).setFavorite();
                            }
                        }
                    }
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
