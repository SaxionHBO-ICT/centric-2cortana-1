package com.example.larsmeulenbroek.kroegenapp.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.larsmeulenbroek.kroegenapp.Model.Bar;
import com.example.larsmeulenbroek.kroegenapp.Model.BarModel;
import com.example.larsmeulenbroek.kroegenapp.R;

import java.util.List;

/**
 * Created by Lars Meulenbroek on 5/23/2016.
 */
public class RouteBarListAdapter extends ArrayAdapter<Bar> {

    public RouteBarListAdapter(Context context, int resource, List<Bar> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bar_route_view, parent, false);
        }

        Bar currentBar = BarModel.getInstance().get(position);

        TextView tvName = (TextView) convertView.findViewById(R.id.tvBarName);
        tvName.setText(currentBar.getName());

        TextView tvTheme = (TextView) convertView.findViewById(R.id.tvBarTheme);
        tvTheme.setText(currentBar.getTheme());

        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivBarImage);
        ivImage.setImageResource(currentBar.getPicture().getId());

        return convertView;
    }
}
