package com.example.larsmeulenbroek.kroegenapp.Tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

/**
 * Created by Lars Meulenbroek on 6/16/2016.
 */
/*
Perform background operation asynchronously, to get all profile information from the google+ api
*/
public class LoadProfile extends AsyncTask<GoogleApiClient, Void, Person> {

    private Context context;
    private ProgressDialog progressDialog;

    public interface AsyncResponse {
        void processFinish(Person person);
    }

    public AsyncResponse delegate = null;

    public LoadProfile(AsyncResponse delegate, Context context) {
        this.delegate = delegate;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
    }

    protected Person doInBackground(GoogleApiClient... gApi) {
        GoogleApiClient google_api_client = gApi[0];
        if (Plus.PeopleApi.getCurrentPerson(google_api_client) != null) {
            return Plus.PeopleApi.getCurrentPerson(google_api_client);
        } else {
            Toast.makeText(context,
                    "No Personal info mention", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Person person) {
        delegate.processFinish(person);
        if(progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}


