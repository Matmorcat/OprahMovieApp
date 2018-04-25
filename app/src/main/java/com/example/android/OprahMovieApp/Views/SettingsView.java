package com.example.android.OprahMovieApp.Views;
/**
 * This class is responsible for the settings activity of the application.
 * @authors
 * @date
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
