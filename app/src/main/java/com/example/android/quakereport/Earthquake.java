package com.example.android.quakereport;

/**
 * Created by Christi on 04.04.2017.
 * A class representing earthquakes.
 */

public class Earthquake {

    // The magnitude of an earthquake
    private double mMagnitude;

    // The location used to locate an earthquake
    private String mLocation;

    // The time in milliseconds when an earthquake occurred
    private long mTimeInMilliseconds;

    // The URL to the earthquake on the USGS Internet site
    private String mUrl;

    /**
     * Constructs a new Earthquake object
     *
     * @param magnitude of the earthquake
     * @param location of the earthquake
     * @param timeInMilliseconds when the earthquake occurred
     */
    public Earthquake(double magnitude, String location, long timeInMilliseconds, String url) {
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;
    }

    // Get the magnitude of an earthquake
    public double getMagnitude() {
        return mMagnitude;
    }

    // Get the location used to locate an earthquake
    public String getLocation() {
        return mLocation;
    }

    // Get the date in milliseconds when an earthquake occurred
    public long getDateInMilliseconds() {
        return mTimeInMilliseconds;
    }

    // Get the URL of the earthquake
    public String getUrl() {
        return mUrl;
    }

    @Override
    public String toString() {
        return "" + mMagnitude + " "
                + mLocation + " "
                + mTimeInMilliseconds + " "
                + mUrl;
    }
}
