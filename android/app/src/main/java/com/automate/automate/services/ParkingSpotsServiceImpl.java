package com.automate.automate.services;

import android.location.Location;

import com.automate.automate.exceptions.AutomateException;
import com.automate.automate.http.ParkingClient;
import com.automate.automate.models.ParkingSpot;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.util.List;

import static com.automate.automate.http.HttpUtils.requestBuilder;
import static com.automate.automate.http.ParkingClient.nearbyParkingUrl;
import static com.automate.automate.http.ParkingClient.parkingState;

/**
 * Created by davidfrancoeur on 2015-11-14.
 */
public class ParkingSpotsServiceImpl implements ParkingSpotsService {

    private final static LatLng Montreal = new LatLng(45.525837, -73.595178);
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    private final ParkingClient parkingClient;

    public ParkingSpotsServiceImpl() {
        this.parkingClient = new ParkingClient(new OkHttpClient());
    }

    public ParkingSpotsServiceImpl(ParkingClient parkingClient) {
        this.parkingClient = parkingClient;
    }

    @Override
    public AutomateResult<List<ParkingSpot>> findNearby(Location location, Long duration) {

        String degreesLatitude = Location.convert(Montreal.latitude, Location.FORMAT_DEGREES);
        String degreeLongitude = Location.convert(Montreal.longitude, Location.FORMAT_DEGREES);
        String durationStr = duration != null ? duration.toString() : "";

        try {
            Request request =
                    requestBuilder()
                            .get()
                            .url(nearbyParkingUrl(degreesLatitude, degreeLongitude))
                            .build();

            String payload = parkingClient.executeForPayload(request);
            List<ParkingSpot> parkingSpots = ParkingSpot.fromArrayPayload(payload);

            return new AutomateResult<>(parkingSpots, null);
        } catch (AutomateException ex) {
            return new AutomateResult<>(null, ex);
        }
    }

    @Override
    public AutomateResult<List<ParkingSpot>> findNearbyDestination(String query, Location location, Long duration) {
        return null;
    }

    @Override
    public AutomateResult<Boolean> updateAvailable(String id, boolean state) {
        try {

            String payload = ParkingClient.statePayload(id, state);

            Request request =
                    requestBuilder()
                            .post(RequestBody.create(JSON, payload))
                            .url(parkingState())
                            .build();

            return new AutomateResult<>(parkingClient.executeForBoolean(request), null);
        } catch (AutomateException ex) {
            return new AutomateResult<>(false, ex);
        }
    }
}