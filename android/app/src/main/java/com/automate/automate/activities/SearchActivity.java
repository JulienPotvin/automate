package com.automate.automate.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.automate.automate.R;

public class SearchActivity extends AbstractSearchActivity {

    public static String LOGGER_TAG = SearchActivity.class.getName();

    private EditText mQueryInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mQueryInput = (EditText) findViewById(R.id.query_input);

        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = mQueryInput.getText().toString();
                attemptQuery(query);
            }
        });
    }
}
