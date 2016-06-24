package com.example.larsmeulenbroek.kroegenapp.activities;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.larsmeulenbroek.kroegenapp.Model.Route;
import com.example.larsmeulenbroek.kroegenapp.Model.RouteModel;
import com.example.larsmeulenbroek.kroegenapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
/*
activity which show a map (Route)
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //devine all variables
    private GoogleMap mMap;
    private int position;
    private Route detailRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //get bundle which contains position of the selected route
        Bundle b = getIntent().getBundleExtra(MainActivity.ROUTE_POSITION_BUNDLE);
        if (b != null) {
            position = b.getInt(MainActivity.ROUTE_POSITION);
            detailRoute = RouteModel.getInstance().get(position);
        }
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

        if (mMap != null) {
            for (int i = 0; i < detailRoute.getBars().size(); i++) {
                LatLng locationBar = getLocationFromAddress(this, detailRoute.getBars().get(i).getAdres());
                mMap.addMarker(new MarkerOptions().position(locationBar).title(detailRoute.getBars().get(i).getName()));
                builder = new LatLngBounds.Builder().include(locationBar).build();
            }
            assert builder != null;
            LatLng deventer = new LatLng(52.25325, 6.157517);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(deventer,14));
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

