package com.example.larsmeulenbroek.kroegenapp.activities;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.larsmeulenbroek.kroegenapp.Model.Bar;
import com.example.larsmeulenbroek.kroegenapp.Model.BarModel;
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
activity which show a map (Bar)
 */
public class MapsActivityBar extends FragmentActivity implements OnMapReadyCallback {

    //devine all variables
    private GoogleMap mMap;
    private int position;
    private Bar detailBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //get bundle which contains bar_id of the selected bar
        Bundle b = getIntent().getBundleExtra(MainActivity.BAR_ID_BUNDLE);
        if (b != null) {
            position = b.getInt(MainActivity.BAR_ID);
            detailBar = BarModel.getInstance().get(position - 1);
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
