package com.example.android.quakereport;

import android.content.Context;
import android.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by Christi on 12.04.2017.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    // Log tag
    private static final String LOG_TAG = EarthquakeLoader.class.getSimpleName();

    // Url string
    private String mUrl;

    /**
     * Constructor of the class
     */
    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    /**
     * Force load on start loading
     */
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * Method executing load tasks in the background
     * @return
     */
    @Override
    public List<Earthquake> loadInBackground() {

        // Check if url string is null
        if (mUrl == null) {
            return null;
        }

        List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);

        return earthquakes;
    }
}
