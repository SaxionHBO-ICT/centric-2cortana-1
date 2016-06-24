package com.example.larsmeulenbroek.kroegenapp.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.larsmeulenbroek.kroegenapp.Model.Route;
import com.example.larsmeulenbroek.kroegenapp.Model.RouteModel;
import com.example.larsmeulenbroek.kroegenapp.R;
import com.example.larsmeulenbroek.kroegenapp.View.DetailRouteBarListAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/*
Activity which show the detail screen of a route
 */
public class DetailRouteActivity extends AppCompatActivity implements OnMapReadyCallback {

    //devine all variables
    private GoogleMap mMap;
    private int position;
    private Route detailRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_route);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get bundle which contains position of the selected route
        Bundle b = getIntent().getBundleExtra(MainActivity.ROUTE_POSITION_BUNDLE);
        if (b != null) {
            position = b.getInt(MainActivity.ROUTE_POSITION);
            detailRoute = RouteModel.getInstance().get(position);
        }

        //devine all id's
        TextView tvdName = (TextView) findViewById(R.id.tvDetailRouteName);
        TextView tvdTheme = (TextView) findViewById(R.id.tvDetailRouteTheme);
        ListView lvRoute = (ListView) findViewById(R.id.lvDetailRouteBars);
        Button btnShowMap = (Button) findViewById(R.id.btn_show_map);
        ImageView ivdBarImage = (ImageView) findViewById(R.id.ivDetailRouteImage);

        //null asserts
        assert tvdName != null;
        assert tvdTheme != null;
        assert ivdBarImage != null;
        assert btnShowMap != null;
        assert lvRoute != null;

        //set detail information of route
        tvdName.setText(detailRoute.getName());
        tvdTheme.setText(detailRoute.getTheme());
        ivdBarImage.setImageResource(detailRoute.getPicture().getId());

        //create adapter
        DetailRouteBarListAdapter adapter = new DetailRouteBarListAdapter(this, R.layout.bar_route_view, detailRoute.getBars());
        lvRoute.setAdapter(adapter);

        //button onclick listeners
        btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt(MainActivity.ROUTE_POSITION, position);
                Intent intent = new Intent(DetailRouteActivity.this, MapsActivity.class);
                intent.putExtra(MainActivity.ROUTE_POSITION_BUNDLE, b);
                startActivity(intent);
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
        LatLngBounds builder =  null;

        try {
            if (mMap != null) {
                for (int i = 0; i < detailRoute.getBars().size(); i++) {
                        LatLng locationBar = getLocationFromAddress(this, detailRoute.getBars().get(i).getAdres());
                        mMap.addMarker(new MarkerOptions().position(locationBar).title(detailRoute.getBars().get(i).getName()));
                        builder = new LatLngBounds.Builder().include(locationBar).build();
                }
                assert builder != null;
                LatLng deventer = new LatLng(52.25325, 6.157517);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(deventer, 14));
            }
        } catch (IllegalArgumentException ilae) {
            ilae.getMessage();
        }
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
}
