package com.automate.automate.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.automate.automate.R;
import com.automate.automate.models.ParkingSpot;
import com.automate.automate.services.AutomateResult;
import com.automate.automate.services.CompletionCallback;
import com.automate.automate.services.ParkingSpotsService;
import com.automate.automate.services.ParkingSpotsServiceImpl;
import com.automate.automate.tasks.UpdateStateAsyncTask;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.automate.automate.Constants.EXTRAS_PARKING_SPOTS;

public class ParkingsActivity extends FragmentActivity implements OnMapReadyCallback {

    private final static String LOGGER_TAG = ParkingsActivity.class.getName();

    private final static LatLng Montreal = new LatLng(45.5034, -73.62878);

    private GoogleMap mMap;
    private List<ParkingSpot> spots;

    protected ParkingSpotsService parkingSpotsService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parkings);


        parkingSpotsService = new ParkingSpotsServiceImpl();

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
            final BitmapDescriptor red = BitmapDescriptorFactory.fromResource(R.mipmap.red_marker);
            final BitmapDescriptor green = BitmapDescriptorFactory.fromResource(R.mipmap.green_marker);

            final Map<Marker, ParkingSpot> spotsMap = new HashMap<>();

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (ParkingSpot spot : spots) {
                LatLng latLng = new LatLng(spot.getLatitude(), spot.getLongitude());
                builder.include(latLng);

                MarkerOptions marker = new MarkerOptions()
                        .position(latLng).title(toTitle(spot));

                if (spot.getAvailability()) {
                    marker.icon(green);
                } else {
                    marker.icon(red);
                }
                spotsMap.put(mMap.addMarker(marker), spot);
            }

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(final Marker marker) {
                    final ParkingSpot spot = spotsMap.get(marker);
                    if (spot != null) {
                        UpdateStateAsyncTask task = new UpdateStateAsyncTask(spot.getId(), !spot.getAvailability(),
                                parkingSpotsService, new CompletionCallback<Boolean>() {
                            @Override
                            public void onCompletion(AutomateResult<Boolean> result) {
                                if (result != null && result.getResult() && result.hasError()) {
                                    Toast.makeText(getApplicationContext(), "Parking state not updated", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getApplicationContext(), "Parking state updated", Toast.LENGTH_SHORT).show();
                                    if (!spot.getAvailability()) {
                                        marker.setIcon(green);
                                    } else {
                                        marker.setIcon(red);
                                    }
                                }
                            }
                        });
                        task.execute();
                    }
                    return false;
                }
            });
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 150));
        } else {
            mMap.animateCamera(CameraUpdateFactory.newLatLng(Montreal));
        }


    }

    private String toTitle(ParkingSpot spot) {
        String str = "";
        if (spot != null) {
            str = spot.getId() + "\\n test";
        }
        return str;
    }
}
