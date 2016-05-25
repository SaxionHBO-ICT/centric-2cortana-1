package com.example.larsmeulenbroek.kroegenapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.larsmeulenbroek.kroegenapp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {

    public LoginActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        final LinearLayout llbackground = (LinearLayout) v.findViewById(R.id.background_login);

//        LoadBackGroundImageTask lbgit = new LoadBackGroundImageTask(new LoadBackGroundImageTask.AsyncResponse() {
//            @Override
//            public void processFinish(Drawable image) {
//                llbackground.setBackground(image);
//            }
//        });
//        lbgit.execute("https://www.google.nl/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwj-4M-npvHMAhWEbxQKHYw1C-gQjRwIBw&url=http%3A%2F%2Fwww.photos-public-domain.com%2F2012%2F05%2F24%2Fgreen-paper-texture%2F&psig=AFQjCNHPwE-65l6OnJDAVRzpXrwHgiwPqQ&ust=1464130769110610");
        return v;
    }
}
