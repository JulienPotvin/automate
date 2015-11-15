package com.automate.automate.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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

/**
 * Created by davidfrancoeur on 2015-11-14.
 */
public abstract class AbstractSearchActivity extends AppCompatActivity {
    public static String LOGGER_TAG = SearchActivity.class.getName();

    protected ParkingSpotsService parkingSpotsService;
    protected LocationManager locationManager;

    protected View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressView = findViewById(R.id.query_progress);

        parkingSpotsService = new ParkingSpotsServiceImpl();
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);


        Intent intent = getIntent();
        if (SearchIntents.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(LOGGER_TAG, format("Automate received findNearby: %s", query));

            handleQuery(query);
        }
    }


    protected void handleQuery(String query) {
        Log.d(LOGGER_TAG, format("Automate handling query: %s", query));

        if (TextUtils.isEmpty(query)) {
            Toast.makeText(getBaseContext(),
                    getString(R.string.empty_query),
                    Toast.LENGTH_SHORT).show();
        } else {
            findNearby(query);
        }
    }

    protected void findNearby(String query) {
        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        FindNearbyAsyncTask task = new FindNearbyAsyncTask(query, locationGPS, null,
                parkingSpotsService, new CompletionCallback<List<ParkingSpot>>(){
            @Override
            public void onCompletion(AutomateResult<List<ParkingSpot>> result) {
                if (result.hasError()){
                    Toast.makeText(getApplicationContext(), "This failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "This worked", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ParkingsActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(EXTRAS_PARKING_SPOTS, new ArrayList<>(result.getResult()));

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        task.execute();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    protected void showProgress(final boolean show) {
        if(mProgressView == null) {
            return;
        }
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}
