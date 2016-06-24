package com.example.larsmeulenbroek.kroegenapp.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.larsmeulenbroek.kroegenapp.Model.Route;
import com.example.larsmeulenbroek.kroegenapp.Model.RouteModel;
import com.example.larsmeulenbroek.kroegenapp.R;

import java.util.List;

/**
 * Created by Lars Meulenbroek on 5/23/2016.
 */
/*
adapter class which shows a route
 */
public class RouteListAdapter extends ArrayAdapter<Route> {

    public RouteListAdapter(Context context, int resource, List<Route> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.route_view, parent, false);
        }

        Route currentRoute = RouteModel.getInstance().get(position);

        //devine all id's
        TextView tvName= (TextView) convertView.findViewById(R.id.tvRouteName);
        TextView tvTheme = (TextView) convertView.findViewById(R.id.tvRouteTheme);
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivRouteImage);

        //null asserts
        assert tvName != null;
        assert tvTheme != null;
        assert ivImage != null;

        //set information
        tvName.setText(currentRoute.getName());
        tvTheme.setText(currentRoute.getTheme());
        ivImage.setImageResource(currentRoute.getPicture().getId());

        return convertView;
    }
}
