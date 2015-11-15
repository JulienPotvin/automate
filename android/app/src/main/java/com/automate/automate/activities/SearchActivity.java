package com.automate.automate.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.automate.automate.R;

public class SearchActivity extends AbstractSearchActivity {

    public static String LOGGER_TAG = SearchActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNearby(null);
            }
        });
    }
}
