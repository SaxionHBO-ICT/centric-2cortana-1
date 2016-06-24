package com.example.larsmeulenbroek.kroegenapp.Tasks;

import android.os.AsyncTask;

import com.example.larsmeulenbroek.kroegenapp.Model.Bar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars Meulenbroek on 6/20/2016.
 */

/*
Perform background operation asynchronously, to insert comments in the database
*/
public class InsertCommentByBarTask extends AsyncTask<Bar, Void, Void> {

    private String user_id;
    private String reaction;
    private String username;

    public InsertCommentByBarTask(String user_id, String reaction, String username) {
        this.user_id = user_id;
        this.reaction = reaction;
        this.username = username;
    }

    @Override
    protected Void doInBackground(Bar... params) {
        ServiceHandler sh = new ServiceHandler();
        String url = "http://kroegenapp.azurewebsites.net/insert_comment.php";

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
        nameValuePairs.add(new BasicNameValuePair("bar_id", "" + params[0].getBar_id()));
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("text", reaction));

        sh.makeServiceCall(url, ServiceHandler.POST, nameValuePairs);

        return null;
    }
}
