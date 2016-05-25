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

import com.example.larsmeulenbroek.kroegenapp.R;
import com.example.larsmeulenbroek.kroegenapp.Tasks.LoadBarListTask;


public class BarListFragment extends Fragment {

    private CallBacks activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bar_list_fragment, container, false);

        ListView lvBars = (ListView) view.findViewById(R.id.lvBarList);

        LoadBarListTask lblt = new LoadBarListTask(getContext(), lvBars);
        lblt.execute();

        lvBars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activity.onBarItemClick(position);
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public interface CallBacks {
        void onBarItemClick(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (CallBacks) activity;
    }
}
