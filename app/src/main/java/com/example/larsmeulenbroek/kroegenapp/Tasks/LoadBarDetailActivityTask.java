package com.example.larsmeulenbroek.kroegenapp.Tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.larsmeulenbroek.kroegenapp.activities.DetailBarActivity;
import com.example.larsmeulenbroek.kroegenapp.activities.MainActivity;

/**
 * Created by Lars Meulenbroek on 5/24/2016.
 */
public class LoadBarDetailActivityTask extends AsyncTask<Integer, Void, Void>{

    protected int position;

    Context context;
    public LoadBarDetailActivityTask(Context context) {
        this.context = context.getApplicationContext();
    }


    @Override
    protected Void doInBackground(Integer... params) {
        position = params[0];
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Bundle positionBundle = new Bundle();
        positionBundle.putInt(MainActivity.BAR_POSITION, position);

        final Intent intent = new Intent(context, DetailBarActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(MainActivity.BAR_POSITION_BUNDLE, positionBundle);
        context.startActivity(intent);
    }
}
