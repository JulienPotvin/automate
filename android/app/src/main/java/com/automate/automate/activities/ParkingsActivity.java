package com.automate.automate.activities;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.automate.automate.Constants;
import com.automate.automate.R;
import com.automate.automate.models.ParkingSpot;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import static com.automate.automate.Constants.EXTRAS_PARKING_SPOTS;

public class ParkingsActivity extends FragmentActivity implements OnMapReadyCallback {

    private final static String LOGGER_TAG = ParkingsActivity.class.getName();

    private GoogleMap mMap;
    private List<ParkingSpot> spots;

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parkings);


        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        Bundle bundle = getIntent().getExtras();
        spots = bundle.getParcelableArrayList(EXTRAS_PARKING_SPOTS);
        Log.d(LOGGER_TAG, String.format("Parkings spots: %s", spots));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (spots != null && !spots.isEmpty()) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (ParkingSpot spot : spots){
                LatLng latLng = new LatLng(spot.getLatitude(), spot.getLongitude());
                builder.include(latLng);
                mMap.addMarker(new MarkerOptions().position(latLng).title(spot.getId()));

            }
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLatitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }
}
