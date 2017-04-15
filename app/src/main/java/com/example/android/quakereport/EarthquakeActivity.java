/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<Earthquake>> {

    // Log tag of the class
    private static final String LOG_TAG = EarthquakeActivity.class.getName();

    // Constant value for earthquake loader ID
    private static final int EARTHQUAKE_LOADER_ID = 1;

    // The HTTP request used in this app
    private static final String HTTP_REQUEST = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    // The Adapter for the list of earthquakes
    private EarthquakeAdapter mAdapter;

    // Empty state text view
    private TextView mEmptyStateTextView;

    // The loader view
    private View mLoaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Find the empty state text view
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_state_text_view);

        // Set empty view on the list view
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        // Find the loader view
        mLoaderView = findViewById(R.id.progress_bar);

        // Create a new {@link ArrayAdapter} of earthquakes
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        // Set an OnItemClickListener to the list view
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // Find the current earthquake that was clicked
                Earthquake currentEarthquake = mAdapter.getItem(i);

                // Convert the String URL of the current Earthquake into a URI object
                // to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create an intent to open the URI in a Browser
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Check for internet connectivity
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            // Get a reference to the LoaderManager
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            mLoaderView.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_connection_text);
        }
    }

    /**
     * Method executed when the Loader object is created
     *
     * @param id   as the number of the Loader object
     * @param urls of the Loader method
     * @return the Loader object
     */
    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle urls) {
        // Create a new loader for the given URL
        return new EarthquakeLoader(this, HTTP_REQUEST);
    }

    /**
     * Method called when the Loader has finished loading
     *
     * @param loader      is the Loader object
     * @param earthquakes is the list of earthquakes transfered from the Loader
     */
    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {

        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.empty_state_text);

        // Set progress bar view invisible
        View progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        //
        if ((earthquakes != null) && (!earthquakes.isEmpty())) {
            mAdapter.addAll(earthquakes);
        }

    }

    /**
     * Method called when the Loader object is destroyed
     *
     * @param loader object
     */
    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

    }

}
