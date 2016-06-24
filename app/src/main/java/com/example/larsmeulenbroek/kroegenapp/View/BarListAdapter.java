package com.example.larsmeulenbroek.kroegenapp.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
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
import com.example.larsmeulenbroek.kroegenapp.activities.LoginActivity;

import java.util.List;
/*
adapter class which shows a bar
 */
public class BarListAdapter extends ArrayAdapter<Bar> {

    private int bar_id;

    public BarListAdapter(Context context, int resource, List<Bar> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bar_view, parent, false);
        }

        final Bar currentBar = BarModel.getInstance().get(position);
        bar_id = currentBar.getBar_id();

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

        //only used in addRoute activity
        //make a selected bar change background color
        if(currentBar.getAddToRoute()) {
            convertView.findViewById(R.id.barBackground).setBackgroundColor(Color.parseColor("#ccffcc"));
        } else {
            convertView.findViewById(R.id.barBackground).setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checks if a user is logged in
                if (UserModel.loggedInUser.getEmail() != null) {
                    BarModel.getInstance().get(position).setFavorite();
                    if (BarModel.getInstance().get(position).isFavorite()) {
                        Toast.makeText(getContext(), BarModel.getInstance().get(position).getName() + " is aan favorieten toegevoegd", Toast.LENGTH_SHORT).show();
                        InsertFavoriteTask insertFavoriteTask = new InsertFavoriteTask(UserModel.loggedInUser.getEmail());
                        insertFavoriteTask.execute(currentBar);
                    } else {
                        Toast.makeText(getContext(), BarModel.getInstance().get(position).getName() + " is verwijderd uit favorieten", Toast.LENGTH_SHORT).show();
                        RemoveFavoriteTask removeFavoriteTask = new RemoveFavoriteTask(UserModel.loggedInUser.getEmail());
                        removeFavoriteTask.execute(currentBar);
                    }
                    notifyDataSetChanged();
                } else {
                    //alertdialog which allows the user to login
                    final AlertDialog.Builder aDialog = new AlertDialog.Builder(getContext());
                    aDialog.setTitle("U bent nog niet inglogd");
                    aDialog.setMessage("Wilt u nu inloggen?");
                    aDialog.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            getContext().startActivity(intent);
                        }
                    });
                    aDialog.setNegativeButton("Niet nu", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getContext(), "U moet ingelogd zijn om een kroeg aan favorieten toe te voegen", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog alertDialog = aDialog.create();
                    alertDialog.show();
                }

            }
        });

        return convertView;
    }
}

