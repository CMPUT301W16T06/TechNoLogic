package ca.ualberta.cs.technologic.Activities;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import java.util.ArrayList;

import ca.ualberta.cs.technologic.R;

/**
 * Allows the user to enter latitude and longitude and a map pops up
 */

//http://stackoverflow.com/questions/21120970/google-maps-android-api-v2-problems-with-android-4-3
public class Maps extends FragmentActivity {
    private GoogleMap googleMap;
    private Double latitude = -37.814251;
    private Double longitude = 144.963169;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);
        try {
            initializeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initializeMap() {

        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if map is created successfully
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(), "Unable to create maps",Toast.LENGTH_SHORT).show();
            }
        }
        double[] location = getIntent().getDoubleArrayExtra("Location");
        latitude = location[0];
        longitude = location[1];
        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(longitude, latitude))
                .title("Item Location"));

        //http://stackoverflow.com/questions/14074129/google-maps-v2-set-both-my-location-and-zoom-in
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(longitude, latitude));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);
        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
    }



    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        initializeMap();
    }


}

