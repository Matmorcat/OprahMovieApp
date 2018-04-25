package com.oprahs_voice.android.movies.activities;
/**
 * settings fragment
 * @authors
 * @date
 */

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.oprahs_voice.android.movies.R;


public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

}
