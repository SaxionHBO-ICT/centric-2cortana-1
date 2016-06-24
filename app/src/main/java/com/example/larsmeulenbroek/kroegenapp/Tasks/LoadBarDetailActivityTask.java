package com.example.larsmeulenbroek.kroegenapp.Tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.larsmeulenbroek.kroegenapp.activities.DetailBarScrollActivity;
import com.example.larsmeulenbroek.kroegenapp.activities.MainActivity;

/**
 * Created by Lars Meulenbroek on 5/24/2016.
 */
/*
Perform background operation asynchronously, to go to the detail acitivity of a bar
*/
public class LoadBarDetailActivityTask extends AsyncTask<Integer, Void, Void>{

    protected int bar_id;

    private Context context;

    public LoadBarDetailActivityTask(Context context) {
        this.context = context.getApplicationContext();
    }


    @Override
    protected Void doInBackground(Integer... params) {
        bar_id = params[0];
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Bundle positionBundle = new Bundle();
        positionBundle.putInt(MainActivity.BAR_ID, bar_id);

        final Intent intent = new Intent(context, DetailBarScrollActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(MainActivity.BAR_ID_BUNDLE, positionBundle);
        context.startActivity(intent);
    }
}
