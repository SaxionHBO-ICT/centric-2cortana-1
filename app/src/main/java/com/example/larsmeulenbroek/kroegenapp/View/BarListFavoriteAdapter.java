package com.example.larsmeulenbroek.kroegenapp.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.larsmeulenbroek.kroegenapp.Model.Bar;
import com.example.larsmeulenbroek.kroegenapp.Model.BarModel;
import com.example.larsmeulenbroek.kroegenapp.Model.UserModel;
import com.example.larsmeulenbroek.kroegenapp.R;
import com.example.larsmeulenbroek.kroegenapp.Tasks.InsertFavoriteTask;
import com.example.larsmeulenbroek.kroegenapp.Tasks.RemoveFavoriteTask;

import java.util.List;

/**
 * Created by Lars Meulenbroek on 5/30/2016.
 */
/*
adapter class which shows a bar of a favorite
 */
public class BarListFavoriteAdapter extends ArrayAdapter<Bar> {

    public BarListFavoriteAdapter(Context context, int resource, List<Bar> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bar_view, parent, false);
        }

        final Bar currentBar = BarModel.getInstanceFavoriteList().get(position);

        //devine all id's
        TextView tvName = (TextView) convertView.findViewById(R.id.tvBarName);
        TextView tvTheme = (TextView) convertView.findViewById(R.id.tvBarTheme);
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivBarImage);
        ImageView ivFavorite = (ImageView) convertView.findViewById(R.id.ivFavorite);

        //null asserts
        assert tvName != null;
        assert tvTheme != null;
        assert ivImage != null;
        assert ivFavorite != null;

        //set text and images
        tvName.setText(currentBar.getName());
        tvTheme.setText(currentBar.getTheme());
        ivImage.setImageResource(currentBar.getPicture().getId());
        ivFavorite.setImageResource(R.drawable.is_favorite);
        if (currentBar.isFavorite()) {
            ivFavorite.setAlpha(255);
        } else {
            ivFavorite.setAlpha(70);
        }

        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarModel.getInstanceFavoriteList().get(position).setFavorite();
                if (BarModel.getInstanceFavoriteList().get(position).isFavorite()) {
                    Toast.makeText(getContext(), BarModel.getInstance().get(position).getName() + " is aan favorieten toegevoegd", Toast.LENGTH_SHORT).show();
                    InsertFavoriteTask insertFavoriteTask = new InsertFavoriteTask(UserModel.loggedInUser.getEmail());
                    insertFavoriteTask.execute(currentBar);
                } else {
                    Toast.makeText(getContext(), BarModel.getInstance().get(position).getName() + " is verwijderd uit favorieten", Toast.LENGTH_SHORT).show();
                    RemoveFavoriteTask removeFavoriteTask = new RemoveFavoriteTask(UserModel.loggedInUser.getEmail());
                    removeFavoriteTask.execute(currentBar);
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
