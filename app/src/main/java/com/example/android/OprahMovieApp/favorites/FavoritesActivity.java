package com.example.android.OprahMovieApp.favorites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.OprahMovieApp.R;

// TODO: Implement favorites activity - code started
public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_favorites);

        FavoritesModel favoritesModel = new FavoritesModel(this);
    }
}
