package com.example.larsmeulenbroek.kroegenapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.larsmeulenbroek.kroegenapp.Model.Bar;
import com.example.larsmeulenbroek.kroegenapp.Model.BarModel;
import com.example.larsmeulenbroek.kroegenapp.Model.UserModel;
import com.example.larsmeulenbroek.kroegenapp.R;
import com.example.larsmeulenbroek.kroegenapp.Tasks.GetCommentsByBar;
import com.example.larsmeulenbroek.kroegenapp.View.CommentListAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/*
Activity which show the detail screen of a bar
 */
public class DetailBarScrollActivity extends AppCompatActivity implements OnMapReadyCallback {

    //devine all variables
    private GoogleMap mMap;
    private Bar detailBar;
    private int bar_id;
    private CommentListAdapter comments;
    private SwipeRefreshLayout swipe;
    private ListView lvComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bar_scroll);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get bundle which contains id of the selected bar
        Bundle b = getIntent().getBundleExtra(MainActivity.BAR_ID_BUNDLE);
        if (b != null) {
            bar_id = b.getInt(MainActivity.BAR_ID);
            for (Bar bar : BarModel.getInstance()) {
                if (bar.getBar_id() == bar_id) {
                    detailBar = bar;
                }
            }
        }

        //devine all id's
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipelayout);
        lvComments = (ListView) findViewById(R.id.lvDetailComments);
        TextView tvdName = (TextView) findViewById(R.id.tvDetailName);
        TextView tvdTheme = (TextView) findViewById(R.id.tvDetailTheme);
        TextView tvdDescription = (TextView) findViewById(R.id.tvDetailDescription);
        ImageView ivdBarImage = (ImageView) findViewById(R.id.ivDetailBarImage);
        TextView tvOpeningHours = (TextView) findViewById(R.id.tvOpeningsTijden);
        Button website_btn = (Button) findViewById(R.id.btn_GoToWebsite);
        Button btnShowMap = (Button) findViewById(R.id.btn_show_map);
        Button btnComment = (Button) findViewById(R.id.btn_leave_message);

        //null asserts
        assert swipe != null;
        assert lvComments != null;
        assert tvdName != null;
        assert tvdTheme != null;
        assert tvdDescription != null;
        assert ivdBarImage != null;
        assert tvOpeningHours != null;
        assert website_btn != null;
        assert btnShowMap != null;
        assert btnComment != null;

        //reresh method to load new comments
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getComments();
            }
        });
        swipe.requestFocus();

        getComments();

        lvComments.setFocusable(false);
        lvComments.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;

            }
        });

        //set all detailed information of a bar
        tvdName.setText(detailBar.getName());
        tvdTheme.setText(detailBar.getTheme());
        tvdDescription.setText(detailBar.getDescription());
        ivdBarImage.setImageResource(detailBar.getPicture().getId());
        tvOpeningHours.setText(detailBar.getOpeningHours());

        //Button onclicklisteners
        website_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(detailBar.getUrl_str()));
                startActivity(intent);
            }
        });


        btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt(MainActivity.BAR_ID, bar_id);
                Intent intent = new Intent(DetailBarScrollActivity.this, MapsActivityBar.class);
                intent.putExtra(MainActivity.BAR_ID_BUNDLE, b);
                startActivity(intent);
            }
        });


        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserModel.loggedInUser.getEmail() != null) {
                    Bundle b = new Bundle();
                    b.putInt(MainActivity.BAR_ID, bar_id);
                    Intent intent = new Intent(DetailBarScrollActivity.this, LeaveCommentActivity.class);
                    intent.putExtra(MainActivity.BAR_ID_BUNDLE, b);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder aDialog = new AlertDialog.Builder(DetailBarScrollActivity.this);
                    aDialog.setTitle("U bent nog niet inglogd");
                    aDialog.setMessage("Wilt u nu inloggen?");
                    aDialog.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(DetailBarScrollActivity.this, LoginActivity.class);
                            startActivity(intent);
                            comments.notifyDataSetChanged();
                        }
                    });
                    aDialog.setNegativeButton("Niet nu", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(DetailBarScrollActivity.this, "U moet ingelogd zijn om een reactie te plaatsen", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog alertDialog = aDialog.create();
                    alertDialog.show();
                }
            }
        });

        //get googlemaps map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /*
    method automaticly called when Google Map is created
    this method sets the content of the particulair map such as markers
    and the currentposition
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        if (mMap != null) {

            try {
                final LatLng locationBar = getLocationFromAddress(this, detailBar.getAdres());
                mMap.addMarker(new MarkerOptions().position(locationBar).title(detailBar.getName()));
                mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
                        LatLng currentposition = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("My position"));
                        LatLngBounds builder = new LatLngBounds.Builder().include(locationBar).include(currentposition).build();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder, 30));
                    }
                });

            } catch (IllegalArgumentException ilae) {
                ilae.getMessage();
            }
        }
    }

    /*
    method that calls the getComments task which gets the comments from the database
    and sets them in the adapter
     */
    public void getComments() {
        GetCommentsByBar getCommentsByBar = new GetCommentsByBar(new GetCommentsByBar.AsyncResponse() {
            @Override
            public void processFinish() {
                comments = new CommentListAdapter(DetailBarScrollActivity.this, R.layout.comment_view, detailBar.getComments());
                lvComments.setAdapter(comments);
                comments.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        });
        getCommentsByBar.execute(detailBar);

    }

    /*
    method that sets an address om in a Google Maps marker
     */
    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p1;
    }

    /*
    method which updates the adapter when acitivty is resumed
     */
    @Override
    protected void onResume() {
        super.onResume();
        try {
            comments.notifyDataSetChanged();
        } catch (NullPointerException npe) {
            npe.getMessage();
        }
    }
}
