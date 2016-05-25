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
import com.example.larsmeulenbroek.kroegenapp.Model.Route;
import com.example.larsmeulenbroek.kroegenapp.R;

import java.util.List;

/**
 * Created by Lars Meulenbroek on 5/23/2016.
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

        Bar currentBar = BarModel.getInstance().get(position);

        TextView tvName= (TextView) convertView.findViewById(R.id.tvRouteName);

        TextView tvTheme = (TextView) convertView.findViewById(R.id.tvRouteTheme);

        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivRouteImage);

//        ImageView ivFavorite = (ImageView) convertView.findViewById(R.id.ivFavorite);
//        if(currentBar.isFavorite()) {
//           // ivFavorite.setImageResource(R.drawable.is_favorite);
//        } else {
//           // ivFavorite.setImageResource(R.drawable.no_favorite);
//        }

        return convertView;
    }
}
