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

    /**
     * onCreatePreferences is called during the settingsView onCreate method to supply the preferences for this fragment.
     * @param _savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
     * @param _rootKey If non-null, this key will cause this preference fragment to be rooted at the PreferenceScreen.
     */
    @Override
    public void onCreatePreferences(Bundle _savedInstanceState, String _rootKey) {
        setPreferencesFromResource(R.xml.preferences, _rootKey);
    }

}
