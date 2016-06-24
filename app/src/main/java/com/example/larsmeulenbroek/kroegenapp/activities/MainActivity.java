package com.example.larsmeulenbroek.kroegenapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.larsmeulenbroek.kroegenapp.Model.Bar;
import com.example.larsmeulenbroek.kroegenapp.Model.BarModel;
import com.example.larsmeulenbroek.kroegenapp.R;
import com.example.larsmeulenbroek.kroegenapp.Tasks.LoadBarDetailActivityTask;
import com.example.larsmeulenbroek.kroegenapp.Tasks.ServiceHandler;
import com.example.larsmeulenbroek.kroegenapp.View.BarListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
Activity which opens when the application is started
 */
public class MainActivity extends AppCompatActivity {


    // JSON Node names
    private static final String TAG_BAR = "bar";


    //Declaring All The Variables Needed
    public static String BAR_ID = "BAR_ID";
    public static String BAR_ID_BUNDLE = "BAR_ID_BUNDLE";

    public static String ROUTE_POSITION = "ROUTE_POSITION";
    public static String ROUTE_POSITION_BUNDLE = "ROUTE_POSITION_BUNDLE";

    private BarListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //check if the using device is connected to the internet. If not show message to user
        if(!isOnline()) {
            Toast.makeText(this, "No internet connection found, please connect to the internet first", Toast.LENGTH_LONG).show();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //check if the list is not already filled with data
        if(BarModel.getInstance().isEmpty()) {
            new getBars().execute();
        }
        setAdapterInListView();
    }

    /*
    Method for setting the adapter in the listView
     */
    public void setAdapterInListView() {
        adapter = new BarListAdapter(MainActivity.this, R.layout.bar_view, BarModel.getInstance());

        ListView lvbars = (ListView) findViewById(R.id.lvBarList);
        if (lvbars != null) {
            lvbars.setAdapter(adapter);
            lvbars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int bar_id = BarModel.getInstance().get(position).getBar_id();
                    LoadBarDetailActivityTask lbd = new LoadBarDetailActivityTask(getApplicationContext());
                    lbd.execute(bar_id);
                }
            });
        }
    }

    /*
    method for using multiDex because the app has over 64k methods
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // launching the profile activity when profile logo is clicked
        switch (item.getItemId()) {
            case R.id.profile_icon_menu:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.search_icon_menu:
                Intent intent2 = new Intent(this, SearchActivity.class);
                startActivity(intent2);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
 method which updates the adapter when acitivty is resumed
  */
    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    /*
    Method to detect if the using device is connect to any sort of internet
     */
    public boolean isOnline() {
        ConnectivityManager connectionmanager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectionmanager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    /*
     Perform background operation asynchronously, to load all bars from the database
     */
    private class getBars extends AsyncTask<Void, Void, Void> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait..");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String url = "http://kroegenapp.azurewebsites.net/bar.php";

            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray bars = jsonObj.getJSONArray(TAG_BAR);

                    // looping through All Contacts
                    for (int i = 0; i < bars.length(); i++) {
                        JSONObject c = bars.getJSONObject(i);

                        Bar bar = new Bar(c);
                        BarModel.getInstance().add(bar);

                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Cannot connect to the database. Server is down", Toast.LENGTH_LONG).show();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            setAdapterInListView();
        }
    }
}