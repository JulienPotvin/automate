package com.automate.automate.services;

import android.location.Location;

import com.automate.automate.exceptions.AutomateException;
import com.automate.automate.http.ParkingClient;
import com.automate.automate.models.ParkingSpot;
import com.google.android.gms.maps.model.Marker;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.automate.automate.http.HttpUtils.requestBuilder;
import static com.automate.automate.http.ParkingClient.nearbyParkingUrl;
import static com.automate.automate.http.ParkingClient.parkingUrl;

/**
 * Created by davidfrancoeur on 2015-11-14.
 */
public class ParkingSpotsServiceImpl implements ParkingSpotsService {

    private final ParkingClient parkingClient;

    public ParkingSpotsServiceImpl() {
        this.parkingClient =  new ParkingClient(new OkHttpClient());
    }

    public ParkingSpotsServiceImpl(ParkingClient parkingClient) {
        this.parkingClient = parkingClient;
    }

    @Override
    public AutomateResult<List<ParkingSpot>> findNearby(Location location, Long duration) {

        String degreesLatitude = Location.convert(location.getLatitude(), Location.FORMAT_DEGREES);
        String degreeLongitude = Location.convert(location.getLongitude(), Location.FORMAT_DEGREES);
        String durationStr = duration != null ? duration.toString() : "";

        try {
            Request request =
                    requestBuilder()
                            .get()
                            .url(nearbyParkingUrl(degreesLatitude, degreeLongitude))
                            .build();

            String payload = parkingClient.execute(request);
            List<ParkingSpot> parkingSpots = ParkingSpot.fromArrayPayload(payload);

            return new AutomateResult<>(parkingSpots, null);
        } catch (AutomateException ex){
            return new AutomateResult<>(null, ex);
        }
    }

    @Override
    public AutomateResult<List<ParkingSpot>> findNearbyDestination(String query, Location location, Long duration) {
        return null;
    }
}