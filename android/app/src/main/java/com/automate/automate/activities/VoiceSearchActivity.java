package com.automate.automate.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.automate.automate.R;
import com.automate.automate.models.ParkingSpot;
import com.automate.automate.services.AutomateResult;
import com.automate.automate.services.CompletionCallback;
import com.automate.automate.services.ParkingSpotsService;
import com.automate.automate.services.ParkingSpotsServiceImpl;
import com.automate.automate.tasks.FindNearbyAsyncTask;
import com.google.android.gms.actions.SearchIntents;

import java.util.ArrayList;
import java.util.List;

import static com.automate.automate.Constants.EXTRAS_PARKING_SPOTS;
import static java.lang.String.format;

public class VoiceSearchActivity extends AbstractSearchActivity implements SearchView.OnQueryTextListener {
    public static String LOGGER_TAG = SearchActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (SearchIntents.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(LOGGER_TAG, format("Automate received findNearby: %s", query));

            handleQuery(query);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
