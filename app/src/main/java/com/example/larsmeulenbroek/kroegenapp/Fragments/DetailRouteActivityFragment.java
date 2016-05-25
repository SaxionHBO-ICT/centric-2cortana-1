package com.example.larsmeulenbroek.kroegenapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.larsmeulenbroek.kroegenapp.Model.BarModel;
import com.example.larsmeulenbroek.kroegenapp.R;
import com.example.larsmeulenbroek.kroegenapp.View.BarListAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailRouteActivityFragment extends Fragment {

    public DetailRouteActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_route, container, false);

        TextView tvdName = (TextView) v.findViewById(R.id.tvDetailRouteName);

        TextView tvdTheme = (TextView) v.findViewById(R.id.tvDetailRouteTheme);

        ScrollView svdDescription = (ScrollView) v.findViewById(R.id.svDetailRouteDescription);

        ImageView ivdBarImage = (ImageView) v.findViewById(R.id.ivDetailRouteImage);

        ListView lvRoute = (ListView) v.findViewById(R.id.lvDetailRouteBarList);
        BarListAdapter adapter = new BarListAdapter(getActivity(), R.layout.bar_route_view, BarModel.getInstance());
        lvRoute.setAdapter(adapter);

        return v;
    }
}
