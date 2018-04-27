package com.oprahs_voice.android.movies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * This class is responsible for the settings activity of the application.
 *
 * @author Luke Orr
 * @author Matthew Moretz
 * @date April 27th, 2018
 * @see SettingsActivityFragment
 */
public class SettingsActivity extends AppCompatActivity {
    /**
     * onCreate is the Android system callback method that is called upon creation of the application.
     *
     * @param _savedInstanceState any saved information from a previous run, i.e. if the device is
     *                           rotated, a new instance of the app is created with saved information
     *                           from the previous run
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
            super.onCreate(_savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsActivityFragment()).commit();
    }
}
