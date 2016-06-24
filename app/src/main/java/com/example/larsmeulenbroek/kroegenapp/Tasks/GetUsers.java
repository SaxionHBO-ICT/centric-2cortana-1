package com.example.larsmeulenbroek.kroegenapp.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lars Meulenbroek on 6/18/2016.
 */

/*
Perform background operation asynchronously, to get all users from the database
*/
public class GetUsers extends AsyncTask<String, Void, Void> {

    private static final String TAG_USER = "user";
    private Boolean isAnUser = false;

    public interface AsyncResponse {
        void processFinish(Boolean isAnUser);
    }

    public AsyncResponse delegate = null;

    public GetUsers(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected Void doInBackground(String... arg0) {
        // Creating service handler class instance
        ServiceHandler sh = new ServiceHandler();

        // Making a request to url and getting response
        String url = "http://kroegenapp.azurewebsites.net/User.php";

        String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

        Log.d("Response: ", "> " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray users = jsonObj.getJSONArray(TAG_USER);

                // looping through All Contacts
                for (int i = 0; i < users.length(); i++) {
                    JSONObject c = users.getJSONObject(i);

                    if (c.get("user_id").equals(arg0[0])) {
                        isAnUser = true;
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
    protected void onPostExecute(Void aVoid) {
        delegate.processFinish(isAnUser);
    }
}
