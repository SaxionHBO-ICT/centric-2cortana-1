package com.example.larsmeulenbroek.kroegenapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.larsmeulenbroek.kroegenapp.Fragments.DetailBarActivityFragment;
import com.example.larsmeulenbroek.kroegenapp.R;

public class DetailBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DetailBarActivityFragment fragment = new DetailBarActivityFragment();

        Bundle b = getIntent().getBundleExtra(MainActivity.BAR_POSITION_BUNDLE);
        fragment.setArguments(b);

        getSupportFragmentManager().beginTransaction().add(R.id.detailcontainer, fragment).commit();
    }

}
