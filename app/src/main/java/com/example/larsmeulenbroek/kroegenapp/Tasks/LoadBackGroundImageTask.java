package com.example.larsmeulenbroek.kroegenapp.Tasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Lars Meulenbroek on 5/24/2016.
 */
public class LoadBackGroundImageTask extends AsyncTask<String, Void, Drawable> {

    public interface AsyncResponse {
        void processFinish(Drawable image);
    }

    @Override
    protected Drawable doInBackground(String... params) {
        try {
            InputStream is = (InputStream) new URL(params[0]).getContent();
            Drawable d = Drawable.createFromStream(is, "profile_image_url");
            return d;
        } catch (MalformedURLException mfue) {
            mfue.getMessage();
        } catch (IOException ioe) {

        }
        return null;
    }

    public AsyncResponse delegate = null;

    public LoadBackGroundImageTask(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onPostExecute(Drawable drawable) {
        delegate.processFinish(drawable);
    }
}


