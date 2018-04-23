package com.example.android.OprahMovieApp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.android.OprahMovieApp.data.FavoritesAdapter;
import com.example.android.OprahMovieApp.data.Movie;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    protected static FavoritesAdapter favoritesAdapter;
    protected List<Movie> favoriteMovies;

    public static FavoritesAdapter getFavoritesAdapter() {
        return favoritesAdapter;
    }

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favoriteMovies = MainActivity.getFavoritesModel().getFavoriteMovies();

        favoritesAdapter = new FavoritesAdapter(getApplicationContext(), R.layout.activity_favorites, favoriteMovies);
        ListView listView = (ListView) findViewById(R.id.menu_favorites_list);
        if (listView != null) {
            listView.setAdapter(favoritesAdapter);
        }
    }
}


