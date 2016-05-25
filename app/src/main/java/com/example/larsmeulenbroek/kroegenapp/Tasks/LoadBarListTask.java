package com.example.larsmeulenbroek.kroegenapp.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.larsmeulenbroek.kroegenapp.Model.BarModel;
import com.example.larsmeulenbroek.kroegenapp.R;
import com.example.larsmeulenbroek.kroegenapp.View.BarListAdapter;

/**
 * Created by Lars Meulenbroek on 5/24/2016.
 */
public class LoadBarListTask extends AsyncTask<Void, Void, Void> {



    Context context;
    BarListAdapter adapter;
    ListView listView;

    public LoadBarListTask(Context context, ListView rootview) {
        this.context = context;
        listView = rootview;
    }


    @Override
    protected Void doInBackground(Void... params) {
        adapter = new BarListAdapter(context, R.layout.bar_view, BarModel.getInstance());
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        listView.setAdapter(adapter);
    }
}
