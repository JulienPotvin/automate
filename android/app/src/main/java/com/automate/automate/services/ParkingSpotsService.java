package com.automate.automate.services;

import android.location.Location;

import com.automate.automate.models.ParkingSpot;

import java.util.List;

/**
 * Created by davidfrancoeur on 2015-11-14.
 */
public interface ParkingSpotsService {

    Long DEFAULT_PARKING_DURATION = 1L;

    AutomateResult<List<ParkingSpot>> findNearby(Location location, Long duration);
    AutomateResult<List<ParkingSpot>> findNearbyDestination(String query, Location location, Long duration);

}
