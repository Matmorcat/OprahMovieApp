package com.example.android.OprahMovieApp.Views;
/**
 * Last Date Modified:
 * This class is responsible for the settings activity of the application.
 * Contributing Authors:
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()) .commit();
    }
}
