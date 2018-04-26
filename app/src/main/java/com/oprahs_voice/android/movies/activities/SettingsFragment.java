package com.oprahs_voice.android.movies.activities;
/**
 * Fragment to handle settings from within the settings activity
 *
 * @authors
 * @date
 */

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.oprahs_voice.android.movies.R;


public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle _savedInstanceState, String _rootKey) {
        setPreferencesFromResource(R.xml.preferences, _rootKey);
    }

}
