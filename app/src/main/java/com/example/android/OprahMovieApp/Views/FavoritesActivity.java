package com.example.android.OprahMovieApp.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import com.example.android.OprahMovieApp.R;
import com.example.android.OprahMovieApp.data.FavoritesAdapter;
import com.example.android.OprahMovieApp.data.Movie;
import java.util.List;

// TODO: Implement favorites activity - code started
public class FavoritesActivity extends AppCompatActivity {

    protected List<Movie> favoriteMovies;
    protected FavoritesAdapter adapter;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favoriteMovies = MainActivity.getFavoritesModel().getFavoriteMovies();

        adapter = new FavoritesAdapter(getApplicationContext(), R.layout.activity_favorites, favoriteMovies);
        ListView listView = (ListView) findViewById(R.id.favorites_list);
        listView.setAdapter(adapter);


    }
}
