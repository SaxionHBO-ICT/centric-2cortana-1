package com.example.larsmeulenbroek.kroegenapp.Tasks;

import android.os.AsyncTask;

import com.example.larsmeulenbroek.kroegenapp.Model.Picture;
import com.example.larsmeulenbroek.kroegenapp.R;

/**
 * Created by Lars Meulenbroek on 5/25/2016.
 */
public class CreateCategoryImageTask extends AsyncTask<String, Void, Picture> {

    public interface AsyncResponse {
        void processFinish(Picture image);
    }

    public AsyncResponse delegate = null;

    public CreateCategoryImageTask(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected Picture doInBackground(String... params) {
        Picture beer = new Picture("Beer", R.drawable.beer);
        Picture karaoke = new Picture("Karaoke", R.drawable.karaoke);
        Picture party = new Picture("Party", R.drawable.party);
        Picture alternative = new Picture("Alternative", R.drawable.alternative);
        String theme = params[0];
        switch (theme) {
            case "Beer": return beer;
            case "Karaoke": return karaoke;
            case "Party": return party;
            case "Alternative": return alternative;
            default: return alternative;
        }
    }

    @Override
    protected void onPostExecute(Picture drawable) {
        delegate.processFinish(drawable);
    }
}
