package com.automate.automate.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.automate.automate.R;
import com.google.android.gms.actions.SearchIntents;

import static android.app.ProgressDialog.show;
import static java.lang.String.format;

public class SearchActivity extends AppCompatActivity {

    public static String LOGGER_TAG = SearchActivity.class.getName();


    private View mProgressView;
    private View mQueryFormView;
    private EditText mQueryInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mQueryInput = (EditText) findViewById(R.id.query_input);

        mQueryFormView = findViewById(R.id.query_form);
        mProgressView = findViewById(R.id.query_progress);


        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = mQueryInput.getText().toString();
                handleQuery(query);
            }
        });

        Intent intent = getIntent();
        if (SearchIntents.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(LOGGER_TAG, format("Automate received query: %s", query));

            handleQuery(query);
        }
    }

    private void handleQuery(String query) {
        Log.d(LOGGER_TAG, format("Automate handling query: %s", query));

        if (TextUtils.isEmpty(query)) {
            Toast.makeText(getBaseContext(),
                    getString(R.string.empty_query),
                    Toast.LENGTH_SHORT).show();
        } else {
            attemptQuery(query);
        }
    }

    private void attemptQuery(String query) {
        showProgress(true);
        //QUERY
        showProgress(false);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mQueryFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mQueryFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mQueryFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

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
            mQueryFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
