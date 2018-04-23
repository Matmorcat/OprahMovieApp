package com.example.android.OprahMovieApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.OprahMovieApp.data.Movie;

public class DetailActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu _menu) {
        getMenuInflater().inflate(R.menu.menu_detail, _menu);

        // Get the menu option to add to favorites.
        MenuItem favorite_toggle = _menu.findItem(R.id.action_favorite);

        // Check if the movie is already in the favorites list.
        if (MainActivity.getFavoritesModel().isInFavoriteMovies(getMovieInfoFromActivity())) {

            // If the movie is in favorites, set the option to remove from favorites.
            favorite_toggle.setTitle(R.string.remove_favorites);
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    /**
     * When the user clicks the add to favorites or remove from favorites button in the detailed
     * view menu, add or remove the movie currently in view from the favorites list and update the
     * button in the menu.
     *
     * @param item The menu item clicked
     * @return Success recursive call
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Remove the movie from favorites.
        if (id == R.id.action_favorite) {
            if (hasActivityMovieInfo()) {

                // Find the movie that is currently being viewed by the user.
                Movie movie = getMovieInfoFromActivity();

                // If the movie is already in the favorites, remove it.
                if (MainActivity.getFavoritesModel().isInFavoriteMovies(movie)) {

                    // Remove the movie from the favorites list.
                    MainActivity.getFavoritesModel().removeMovie(movie);
                    FavoritesActivity.getFavoritesAdapter().notifyDataSetChanged();
                    item.setTitle(R.string.add_favorites);


                } else {

                    // Add the movie to the favorites list.
                    MainActivity.getFavoritesModel().addMovie(movie);
                    item.setTitle(R.string.remove_favorites);

                }
            }

        }

        return super.onOptionsItemSelected(item);
    }

    private Movie getMovieInfoFromActivity() {
        return (Movie) this.getIntent().getSerializableExtra(getString(R.string.movie_string));
    }

    private boolean hasActivityMovieInfo() {
        // Intent to retrieve movie information.
        Intent intent = this.getIntent();

        // This returns the movie of the activity.
        return (intent != null && intent.hasExtra(getString(R.string.movie_string)));
    }
}
