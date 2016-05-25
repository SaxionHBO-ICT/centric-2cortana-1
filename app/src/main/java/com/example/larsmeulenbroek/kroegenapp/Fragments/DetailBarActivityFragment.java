package com.example.larsmeulenbroek.kroegenapp.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.larsmeulenbroek.kroegenapp.Model.Bar;
import com.example.larsmeulenbroek.kroegenapp.Model.BarModel;
import com.example.larsmeulenbroek.kroegenapp.R;
import com.example.larsmeulenbroek.kroegenapp.activities.MainActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailBarActivityFragment extends Fragment {

    private Bar detailBar;

    public DetailBarActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        int position = b.getInt(MainActivity.BAR_POSITION);
        if (b != null) {
            detailBar = BarModel.getInstance().get(position);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_bar, container, false);

        TextView tvdName = (TextView) v.findViewById(R.id.tvDetailName);

        TextView tvdTheme = (TextView) v.findViewById(R.id.tvDetailTheme);

        ScrollView svdDescription = (ScrollView) v.findViewById(R.id.svDetailDescription);

        ImageView ivdBarImage = (ImageView) v.findViewById(R.id.ivDetailBarImage);


        ImageButton website_btn = (ImageButton) v.findViewById(R.id.btn_GoToWebsite);
        website_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.google.com"));
                startActivity(intent);
            }
        });

        return v;
    }
}
