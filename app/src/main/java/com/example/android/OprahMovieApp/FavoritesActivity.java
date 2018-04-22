package com.example.android.OprahMovieApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.OprahMovieApp.data.FavoritesAdapter;
import com.example.android.OprahMovieApp.data.Movie;
import com.example.android.OprahMovieApp.favorites.FavoritesModel;

import org.w3c.dom.Text;

import java.util.List;

// TODO: Implement favorites activity - code started
public class FavoritesActivity extends AppCompatActivity {

    protected List<Movie> favoriteMovies;
    protected static FavoritesAdapter adapter;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favoriteMovies = MainActivity.getFavoritesModel().getFavoriteMovies();

        adapter = new FavoritesAdapter(getApplicationContext(), R.layout.activity_favorites, favoriteMovies);
        ListView listView = (ListView) findViewById(R.id.favorites_list);
        listView.setAdapter(adapter);
    }

    public static FavoritesAdapter getFavoritesAdapter() {
        return adapter;
    }
}
