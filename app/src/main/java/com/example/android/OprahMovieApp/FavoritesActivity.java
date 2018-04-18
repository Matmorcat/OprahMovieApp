package com.example.android.OprahMovieApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.OprahMovieApp.data.Movie;
import com.example.android.OprahMovieApp.favorites.FavoritesModel;

import org.w3c.dom.Text;

import java.util.List;

// TODO: Implement favorites activity - code started
public class FavoritesActivity extends AppCompatActivity {

    protected List<Movie> favoriteMovies;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favoriteMovies = MainActivity.getFavoritesModel().getFavoriteMovies();




    }
}
