package com.automate.automate.services;

import android.location.Location;

import com.google.android.gms.maps.model.Marker;

import java.util.List;

/**
 * Created by davidfrancoeur on 2015-11-14.
 */
public interface ParkingSpotsService {

    Long DEFAULT_PARKING_DURATION = 1L;

    AutomateResult<List<Marker>> findNearby(String query, Location location, Long duration);

}
