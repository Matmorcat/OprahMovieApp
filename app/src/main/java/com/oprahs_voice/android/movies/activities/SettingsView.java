package com.oprahs_voice.android.movies.activities;
/**
 * This class is responsible for the settings activity of the application.
 *
 * @authors
 * @date
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SettingsView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
            super.onCreate(_savedInstanceState);
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()) .commit();
    }
}
