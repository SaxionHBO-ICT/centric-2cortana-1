package com.example.larsmeulenbroek.kroegenapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.larsmeulenbroek.kroegenapp.Fragments.DetailBarActivityFragment;
import com.example.larsmeulenbroek.kroegenapp.Fragments.DetailRouteActivityFragment;
import com.example.larsmeulenbroek.kroegenapp.R;

public class DetailRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_route);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DetailRouteActivityFragment fragment = new DetailRouteActivityFragment();

        Bundle b = getIntent().getBundleExtra(MainActivity.ROUTE_POSITION_BUNDLE);
        fragment.setArguments(b);

        getSupportFragmentManager().beginTransaction().add(R.id.routeDetailContainer, fragment).commit();
    }

}
