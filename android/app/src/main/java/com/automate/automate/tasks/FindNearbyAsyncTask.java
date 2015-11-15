package com.automate.automate.tasks;

import android.location.Location;
import android.os.AsyncTask;

import com.automate.automate.models.ParkingSpot;
import com.automate.automate.services.AutomateResult;
import com.automate.automate.services.CompletionCallback;
import com.automate.automate.services.ParkingSpotsService;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

import static com.automate.automate.services.ParkingSpotsService.DEFAULT_PARKING_DURATION;

/**
 * Created by davidfrancoeur on 2015-11-14.
 */
public class FindNearbyAsyncTask extends AsyncTask<Void, Void, AutomateResult<List<ParkingSpot>>> {

    private final String query;
    private final Location location;
    private final Long duration;
    private final ParkingSpotsService service;
    private final CompletionCallback<List<ParkingSpot>> callback;

    public FindNearbyAsyncTask(String query, Location location, Long duration,
                               ParkingSpotsService service,
                               CompletionCallback<List<ParkingSpot>> callback) {
        this.query = query;
        this.location = location;
        this.duration = duration != null ? duration : DEFAULT_PARKING_DURATION;
        this.service = service;
        this.callback = callback;
    }

    @Override
    protected AutomateResult<List<ParkingSpot>> doInBackground(Void... params) {

        return service.findNearby(query, location, duration);
    }

    @Override
    protected void onPostExecute(AutomateResult<List<ParkingSpot>> markers) {
        super.onPostExecute(markers);

        callback.onCompletion(markers);
    }

}
