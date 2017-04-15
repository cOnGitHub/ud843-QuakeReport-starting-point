package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Christi on 04.04.2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    // List for the earthquake objects
    ArrayList<Earthquake> mEarthquakes;

    // Separator of location strings
    private static final String LOCATION_SEPARATOR = " of ";

    public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquakes) {

        super(context, 0, earthquakes);
        mEarthquakes = earthquakes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Earthquake currentEarthquake = mEarthquakes.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_item, parent, false);
        }

        // Get magnitude text view
        TextView magnitudeTextView = (TextView) convertView.findViewById(R.id.magnitude_text_view);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        // Format for decimals with only one decimal place
        DecimalFormat formatter = new DecimalFormat("0.0");

        // Transform magnitude into formatted string
        String magnitudeString = formatter.format(currentEarthquake.getMagnitude());

        // Set magnitude of the text view
        magnitudeTextView.setText(magnitudeString);

        // Get location offset text view
        TextView locationOffsetTextView = (TextView) convertView.findViewById(R.id.location_offset_text_view);

        // Get primary location text view
        TextView primaryLocationTextView = (TextView) convertView.findViewById(R.id.primary_location_text_view);

        // Variable holding the full location string
        String locationString = currentEarthquake.getLocation();

        // Verify whether the location string contains the separator
        if (locationString.contains(LOCATION_SEPARATOR)) {
            // Split location string at the occurrence of string " of"
            String[] splitLocation = locationString.split(LOCATION_SEPARATOR);

            // Add " of" to the first part of the array and assign it to location offset
            locationOffsetTextView.setText(splitLocation[0] + LOCATION_SEPARATOR);

            // Assign the second part of the array to the primary location
            primaryLocationTextView.setText(splitLocation[1]);

        } else {

            // Assign string "Near the" to the location offset
            locationOffsetTextView.setText(R.string.near_the);

            // Assign the full location string to the primary location
            primaryLocationTextView.setText(locationString);
        }

        // Get date text view
        TextView dateTextView = (TextView) convertView.findViewById(R.id.date_text_view);

        // Get time text view
        TextView timeTextView = (TextView) convertView.findViewById(R.id.time_text_view);

        // Date object of the date
        Date dateObject = new Date(currentEarthquake.getDateInMilliseconds());

        // Set date of the text view
        dateTextView.setText(formatDate(dateObject));

        // Set time of the text view
        timeTextView.setText(formatTime(dateObject));

        return convertView;
    }

    /**
     * Helper method for creating a date format MMM d, yyyy from a Date object
     *
     * @param dateObject of a date
     * @return a string representation of the date in format MMM d, yyyy
     */
    private String formatDate(Date dateObject) {

        // Simple date format in the form MMM d, yyyy as required by the App
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");

        // Return the string of the date format
        return dateFormat.format(dateObject);
    }

    /**
     * Helper method for creating a time format HH:mm a from a Date object
     *
     * @param dateObject of a date
     * @return a string representation of the time in format HH:mm a
     */
    private String formatTime(Date dateObject) {

        // Simple date format in the form HH:mm a as required by the App
        SimpleDateFormat dateFormat = new SimpleDateFormat("H:mm a");

        // Return the string of the date format
        return dateFormat.format(dateObject);
    }

    /**
     * Helper method on the background color
     */
    private int getMagnitudeColor(double magnitude) {

        // Cast double to Double
        Double magnitudeDouble = (Double) magnitude;
        // Get int part of the double value
        int magnitudeInt = magnitudeDouble.intValue();

        // Return value
        int magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude1);

        switch (magnitudeInt) {
            case (0):
//                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude1);
//                break;
            case (1):
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude1);
                break;
            case (2):
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude2);
                break;
            case (3):
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude3);
                break;
            case (4):
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude4);
                break;
            case (5):
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude5);
                break;
            case (6):
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude6);
                break;
            case (7):
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude7);
                break;
            case (8):
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude8);
                break;
            case (9):
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude9);
                break;
            default:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
                break;
        }

        return magnitudeColor;
    }
}
