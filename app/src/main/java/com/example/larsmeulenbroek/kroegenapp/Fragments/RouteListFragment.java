package com.example.larsmeulenbroek.kroegenapp.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.larsmeulenbroek.kroegenapp.Model.RouteModel;
import com.example.larsmeulenbroek.kroegenapp.R;
import com.example.larsmeulenbroek.kroegenapp.View.RouteListAdapter;

/**
 * Created by Lars Meulenbroek on 5/23/2016.
 */
public class RouteListFragment extends Fragment {

    private RouteCallBacks activity;
    private RouteListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.route_list_fragment, container, false);

        ListView lvRoutes = (ListView) view.findViewById(R.id.lvRouteList);

        adapter = new RouteListAdapter(getActivity(), R.layout.bar_view, RouteModel.getInstance());

        lvRoutes.setAdapter(adapter);

        lvRoutes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activity.onRouteItemClick(position);
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public interface RouteCallBacks {
        void onRouteItemClick(int position);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (RouteCallBacks) activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
