package com.automate.automate;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.actions.SearchIntents;

import static android.app.ProgressDialog.show;
import static java.lang.String.format;

public class SearchActivity extends AppCompatActivity {

    public static String ACTIVITY_NAME = SearchActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (SearchIntents.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(ACTIVITY_NAME, format("Automate received query: %s", query));

            handleQuery(query);
        }
    }

    private void handleQuery(String query) {
        if (TextUtils.isEmpty(query)) {
            TextView txtSearchQuery = (TextView) findViewById(R.id.search_query);
            txtSearchQuery.setText(getString(R.string.empty_query));
        } else {
            showSearchLoadingPopup(query);
        }
    }

    private void showSearchLoadingPopup(String query) {
        ProgressDialog dialog =
                show(SearchActivity.this, getString(R.string.empty_query),
                        String.format("Query: %s", query), true);

        //query to the server goes here, on the callback, hide the progress dialog
    }
}
