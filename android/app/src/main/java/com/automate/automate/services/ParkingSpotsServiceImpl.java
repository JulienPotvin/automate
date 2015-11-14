package com.automate.automate.services;

import android.location.Location;

import com.automate.automate.exceptions.AutomateException;
import com.automate.automate.http.ParkingClient;
import com.automate.automate.models.ParkingSpot;
import com.google.android.gms.maps.model.Marker;
import com.squareup.okhttp.Request;

import java.util.Collections;
import java.util.List;

import static com.automate.automate.http.ParkingClient.builder;
import static com.automate.automate.http.ParkingClient.parkingUrl;

/**
 * Created by davidfrancoeur on 2015-11-14.
 */
public class ParkingSpotsServiceImpl implements ParkingSpotsService {

    private final ParkingClient parkingClient;

    public ParkingSpotsServiceImpl(ParkingClient parkingClient) {
        this.parkingClient = parkingClient;
    }

    @Override
    public void query(String query, Location location,
                              CompletionCallback<List<Marker>> callback) {

        Request request =
                builder()
                .get()
                .url(parkingUrl(query, location.getLatitude(), location.getLongitude()))
                .build();

        try {
            String payload = parkingClient.execute(request);
            List<ParkingSpot> parkingSpots = ParkingSpot.fromArrayPayload(payload);

            callback.onCompletion(Collections.<Marker>emptyList(), null);
        } catch (AutomateException ex){
            callback.onCompletion(Collections.<Marker>emptyList(), ex);
        }


    }
}
