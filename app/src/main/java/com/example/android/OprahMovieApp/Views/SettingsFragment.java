package com.example.android.OprahMovieApp.Views;
/**
 * Last Date Modified:
 * Settings fragment
 * Contributing Authors:
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.android.OprahMovieApp.R;


public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

}