package com.example.android.OprahMovieApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.OprahMovieApp.data.Movie;

public class DetailActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu (Menu _menu) {
        getMenuInflater().inflate(R.menu.menu_detail, _menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_favorite) {

            // Intent to retrieve movie information
            Intent intent = this.getIntent();
            if (intent != null && intent.hasExtra(getString(R.string.movie_string))) {
                Movie movie = (Movie) intent.getSerializableExtra(getString(R.string.movie_string));

                // Add the movie to the favorites list
                MainActivity.getFavoritesModel().addMovie(movie);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
