package com.example.android.OprahMovieApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.OprahMovieApp.favorites.FavoritesModel;

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

    /**
     * Android callback method to handle action bar item clicks. The action bar will handle
     * clicks on the Home/Up button
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_favorite) {

            //intent to retrieve movie information
            Intent intent = this.getIntent();
            if (intent != null && intent.hasExtra(getString(R.string.movie_string))) {
                Movie movie = (Movie) intent.getSerializableExtra(getString(R.string.movie_string));


                // Adding the movie to the favorites database
                Log.d("DetailActivity", "Adding movie with ID: " + movie.getMovieID() + " through the detailed view...");
                FavoritesModel favoritesModel = MainActivity.getFavoritesModel();


                // Check to see if the movie selected is already in the favorites list
                if (favoritesModel.isInFavoriteMovies(movie)){

                    //Toast to display confirmation that movie is already in favorites
                    Toast addFavoritesToast = new Toast(getApplicationContext());
                    addFavoritesToast.makeText(getApplicationContext(), R.string.favorites_toast_exists, Toast.LENGTH_SHORT).show();

                } else {

                    // If the movie is not in the favorites list, add it
                    favoritesModel.addMovie(movie);

                    //Toast to display confirmation that movie has been added to favorites
                    Toast addFavoritesToast = new Toast(getApplicationContext());
                    addFavoritesToast.makeText(getApplicationContext(), R.string.favorites_toast_added, Toast.LENGTH_SHORT).show();
                }
                // TODO: Add ability to remove movies from favorites list
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
