package com.example.larsmeulenbroek.kroegenapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.larsmeulenbroek.kroegenapp.Model.Bar;
import com.example.larsmeulenbroek.kroegenapp.Model.BarModel;
import com.example.larsmeulenbroek.kroegenapp.Model.UserModel;
import com.example.larsmeulenbroek.kroegenapp.R;
import com.example.larsmeulenbroek.kroegenapp.Tasks.InsertCommentByBarTask;


/*
Activity where the logged in user can post his message
 */
public class LeaveCommentActivity extends AppCompatActivity {

    //devine all variables
    private int position;
    private Bar detailBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_comment);

        //get bundle which contains id of the selected bar
        Bundle b = getIntent().getBundleExtra(MainActivity.BAR_ID_BUNDLE);
        if (b != null) {
            position = b.getInt(MainActivity.BAR_ID);
            detailBar = BarModel.getInstance().get(position - 1);       //position of bar is his ID minus 1
        }

        //devine all id's
        TextView user = (TextView) findViewById(R.id.tvUserName);
        final EditText reaction = (EditText) findViewById(R.id.etComment);
        Button btn_plaats_reactie = (Button) findViewById(R.id.btn_plaats_reactie);

        //null asserts
        assert user != null;
        assert btn_plaats_reactie != null;
        assert reaction != null;

        //set text
        user.setText(UserModel.loggedInUser.getUsername());

        //button click method
        btn_plaats_reactie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //method which adds the comment, by the selected bar, in the database
                InsertCommentByBarTask insertCommentByBarTask = new InsertCommentByBarTask
                        (UserModel.loggedInUser.getEmail(), reaction.getText().toString(), UserModel.loggedInUser.getUsername());
                insertCommentByBarTask.execute(detailBar);
                finish();
            }
        });
    }
}
