package com.example.larsmeulenbroek.kroegenapp.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lars Meulenbroek on 6/18/2016.
 */
/*
Perform background operation asynchronously, to insert a new user in the database
*/
public class CreateUserTask extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... params) {
        ServiceHandler sh = new ServiceHandler();
        String url = "http://kroegenapp.azurewebsites.net/users.php";

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user_id", params[0]));

        sh.makeServiceCall(url, ServiceHandler.POST, nameValuePairs);

        Log.d("check", "doInBackground: " + sh.makeServiceCall(url, ServiceHandler.POST, nameValuePairs).toString());

        return null;
    }
}
