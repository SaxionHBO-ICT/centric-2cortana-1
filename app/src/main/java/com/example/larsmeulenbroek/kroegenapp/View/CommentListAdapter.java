package com.example.larsmeulenbroek.kroegenapp.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.larsmeulenbroek.kroegenapp.Model.Comment;
import com.example.larsmeulenbroek.kroegenapp.R;

import java.util.List;

/**
 * Created by Lars Meulenbroek on 5/30/2016.
 */

public class CommentListAdapter extends ArrayAdapter<Comment> {

    private List<Comment> commentsByBar;

    public CommentListAdapter(Context context, int resource, List<Comment> objects) {
        super(context, resource, objects);
        this.commentsByBar = objects;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_view, parent, false);
        }

        Comment currentComment = commentsByBar.get(position);

        //devine id's
        TextView tvTheme = (TextView) convertView.findViewById(R.id.tvComment);
        TextView tvName= (TextView) convertView.findViewById(R.id.tvUser);

        //null assert
        assert tvTheme != null;
        assert tvName != null;

        //set information
        tvName.setText(currentComment.getName());
        tvTheme.setText(currentComment.getText());

        return convertView;
    }
}
