package com.example.larsmeulenbroek.kroegenapp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.larsmeulenbroek.kroegenapp.Model.Bar;
import com.example.larsmeulenbroek.kroegenapp.Model.BarModel;
import com.example.larsmeulenbroek.kroegenapp.Model.Route;
import com.example.larsmeulenbroek.kroegenapp.Model.RouteModel;
import com.example.larsmeulenbroek.kroegenapp.Model.UserModel;
import com.example.larsmeulenbroek.kroegenapp.R;
import com.example.larsmeulenbroek.kroegenapp.Tasks.InsertRouteTask;
import com.example.larsmeulenbroek.kroegenapp.View.BarListAdapter;

/*
Activity where a logged in user can create his own route
 */

public class AddRouteActivity extends AppCompatActivity {

    private BarListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //devine all id's
        final ListView lvBars = (ListView) findViewById(R.id.lvAddBarList);
        final Spinner spinner = (Spinner) findViewById(R.id.sAddThema);
        final EditText etRouteName = (EditText) findViewById(R.id.etAddRouteName);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //null asserts
        assert lvBars != null;
        assert spinner != null;
        assert etRouteName != null;
        assert fab != null;

        //create adapter for listview
        adapter = new BarListAdapter(this, R.layout.bar_view, BarModel.getInstance());
        try {

            lvBars.setAdapter(adapter);
            lvBars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (!BarModel.getInstance().get(position).getAddToRoute()) {
                        BarModel.getInstance().get(position).setAddToRoute();
                        Toast.makeText(getBaseContext(), BarModel.getInstance().get(position).getName() + " is toegevoegd aan route", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    } else {
                        BarModel.getInstance().get(position).setAddToRoute();
                        Toast.makeText(getBaseContext(), BarModel.getInstance().get(position).getName() + " is verwijderd van route", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        } catch (NullPointerException npe) {
            npe.getMessage();
        }

        //create adapter for spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.thema_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        //method to add a route to the logged in users account
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bars = "";
                Route route = new Route(etRouteName.getText().toString(), spinner.getSelectedItem().toString());
                for (Bar b : BarModel.getInstance()) {
                    if (b.getAddToRoute()) {
                        route.getBars().add(b);
                        bars = bars + String.valueOf(b.getBar_id()) + ",";
                    }
                }
                RouteModel.getInstance().add(route);

                //insert tasks to insert the route in the database
                InsertRouteTask insertRouteTask = new InsertRouteTask(UserModel.loggedInUser.getEmail(), bars);
                insertRouteTask.execute(route);

                //reset boolean
                for (Bar b : BarModel.getInstance()) {
                    if (b.getAddToRoute()) {
                        b.setAddToRoute();
                    }
                }

                finish();
            }
        });
    }

}
