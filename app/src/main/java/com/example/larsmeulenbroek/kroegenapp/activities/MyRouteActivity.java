package com.example.larsmeulenbroek.kroegenapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.larsmeulenbroek.kroegenapp.Model.RouteModel;
import com.example.larsmeulenbroek.kroegenapp.R;
import com.example.larsmeulenbroek.kroegenapp.View.RouteListAdapter;

/*
activity which shows the users routes in a list
 */
public class MyRouteActivity extends AppCompatActivity {

    //devine all variables
    private RouteListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_route);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //devine all id's
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        ListView lvmyroutes = (ListView) findViewById(R.id.lvRouteList);

        //null asserts
        assert fab != null;
        assert lvmyroutes != null;

        adapter = new RouteListAdapter(this, R.layout.route_view, RouteModel.getInstance());

        lvmyroutes.setAdapter(adapter);
        lvmyroutes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle positionBundle = new Bundle();
                positionBundle.putInt(MainActivity.ROUTE_POSITION, position);

                Intent intent = new Intent(MyRouteActivity.this, DetailRouteActivity.class);
                intent.putExtra(MainActivity.ROUTE_POSITION_BUNDLE, positionBundle);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyRouteActivity.this, AddRouteActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
    method which updates the adapter when acitivty is resumed
     */
    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();

    }
}
