package com.example.larsmeulenbroek.kroegenapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.larsmeulenbroek.kroegenapp.Model.BarModel;
import com.example.larsmeulenbroek.kroegenapp.R;
import com.example.larsmeulenbroek.kroegenapp.Tasks.LoadBarDetailActivityTask;
import com.example.larsmeulenbroek.kroegenapp.View.BarListFavoriteAdapter;


/*
Activity which shows all the favorites of a user
 */
public class FavoriteListActivity extends AppCompatActivity {

    //devine variables
    private BarListFavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //devine all id's
        ListView lvBars = (ListView) findViewById(R.id.lvBarListFavorites);

        //null asserts
        assert lvBars != null;

        //sort all bars on the users favorites
        BarModel.sortBarOnFavorite();

        adapter = new BarListFavoriteAdapter(this, R.layout.bar_view, BarModel.getInstanceFavoriteList());
        try {
            lvBars.setAdapter(adapter);
            lvBars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int bar_id = BarModel.getInstanceFavoriteList().get(position).getBar_id();
                    LoadBarDetailActivityTask lbd = new LoadBarDetailActivityTask(getApplicationContext());
                    lbd.execute(bar_id);
                }
            });
        } catch (NullPointerException npe) {
            npe.getMessage();
        }

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
