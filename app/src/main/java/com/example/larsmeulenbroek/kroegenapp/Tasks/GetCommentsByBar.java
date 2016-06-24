package com.example.larsmeulenbroek.kroegenapp.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.larsmeulenbroek.kroegenapp.Model.Bar;
import com.example.larsmeulenbroek.kroegenapp.Model.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lars Meulenbroek on 6/20/2016.
 */
/*
Perform background operation asynchronously, to get comment by a paticialur bar from in the database
*/
public class GetCommentsByBar extends AsyncTask<Bar, Void, Void> {

    private static final String TAG_COMMENTS = "comments";

    public interface AsyncResponse {
        void processFinish();
    }

    public AsyncResponse delegate = null;

    public GetCommentsByBar(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected Void doInBackground(Bar... bar_id) {
        // Creating service handler class instance
        ServiceHandler sh = new ServiceHandler();

        // Making a request to url and getting response
        String url = "http://kroegenapp.azurewebsites.net/comment.php?bar_id=" + bar_id[0].getBar_id();

        String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

        Log.d("Response: ", "> " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                //clear duplicates
                bar_id[0].getComments().clear();

                // Getting JSON Array node
                JSONArray comments = jsonObj.getJSONArray(TAG_COMMENTS);

                // looping through All Contacts
                for (int i = 0; i < comments.length(); i++) {
                    JSONObject c = comments.getJSONObject(i);

                    Comment comment = new Comment(c.getString("username"), c.getString("text"));
                    bar_id[0].getComments().add(comment);

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
        delegate.processFinish();
    }
}
