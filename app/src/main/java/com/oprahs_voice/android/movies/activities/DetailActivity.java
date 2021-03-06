package com.oprahs_voice.android.movies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.oprahs_voice.android.movies.R;
import com.oprahs_voice.android.movies.utilities.Movie;
import com.oprahs_voice.android.movies.utilities.exceptions.MovieFavoritesException;

/**
 * This class controls the display of specific information of movies from the main activity. Menu
 * options for things such as adding and removing movies from favorites are handled in this class.
 * Movie displays are handled by a fragment that runs within this view.
 *
 * @author John Weber
 * @author Matthew Moretz
 * @author Luke Orr
 * @date April 27th, 2018
 * @see DetailActivityFragment
 */
public class DetailActivity extends AppCompatActivity {


    /**
     * When the view is loaded, update the menu button that gives the option to add the movie to the
     * favorites list with the text to remove the movie from the favorites list if the movie is
     * already in the favorites list.
     *
     * @param _menu the created menu reference
     * @return <tt>if the menu was updated successfully</tt>
     */
    @Override
    public boolean onCreateOptionsMenu(Menu _menu) {
        getMenuInflater().inflate(R.menu.menu_detail, _menu);

        // Get the menu option to add to favorites.
        MenuItem favorite_toggle = _menu.findItem(R.id.action_favorite);

        // Check if the movie is already in the favorites list.
        if (MainActivity.getFavoritesModel().isInFavoriteMovies(getMovieInfoFromActivity())) {

            // If the movie is in favorites, set the option to remove from favorites.
            favorite_toggle.setTitle(R.string.menu_favorites_remove);
        }

        return true;
    }


    /**
     * When the user clicks the add to favorites or remove from favorites button in the detailed
     * view menu, add or remove the movie currently in view from the favorites list and update the
     * button in the menu.
     *
     * @param _item the menu item clicked
     * @return      <tt>true</tt> if there is a successful recursive call
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem _item) {
        int id = _item.getItemId();

        // Remove the movie from favorites.
        if (id == R.id.action_favorite) {
            if (hasActivityMovieInfo()) {

                // Find the movie that is currently being viewed by the user.
                Movie movie = getMovieInfoFromActivity();

                // If the movie is already in the favorites, remove it.
                if (MainActivity.getFavoritesModel().isInFavoriteMovies(movie)) {

                    // Try to remove the movie from the favorites list
                    try {

                        // Remove the movie from the favorites list.
                        MainActivity.getFavoritesModel().removeMovie(movie);

                        // Toast to display confirmation that the movie has been added to favorites.
                        Toast.makeText(getApplicationContext(), R.string.toast_favorites_removed, Toast.LENGTH_SHORT).show();

                    } catch (MovieFavoritesException e) {

                        // If the movie is not in the favorites list, catch the exception.
                        Log.e("DetailActivity", "onOptionsItemSelected: ", e);

                        // Toast to display confirmation that the movie is already in favorites.
                        Toast.makeText(getApplicationContext(), R.string.toast_favorites_exists_false, Toast.LENGTH_SHORT).show();
                    }

                    _item.setTitle(R.string.menu_favorites_add);


                } else {

                    // Try to add the movie to the favorites list
                    try {

                        // Add the movie to the favorites list.
                        MainActivity.getFavoritesModel().addMovie(movie);

                        // Toast to display confirmation that movie has been added to favorites.
                        Toast.makeText(getApplicationContext(), R.string.toast_favorites_added, Toast.LENGTH_SHORT).show();

                    } catch (MovieFavoritesException e) {

                        // If the movie is in the favorites list, catch the exception.
                        Log.e("DetailActivity", "onOptionsItemSelected: ", e);

                        // Toast to display confirmation that movie is already in favorites.
                        Toast.makeText(getApplicationContext(), R.string.toast_favorites_exists, Toast.LENGTH_SHORT).show();
                    }

                    _item.setTitle(R.string.menu_favorites_remove);

                }
            }

        }

        return super.onOptionsItemSelected(_item);
    }


    /**
     * A method to retrieve the movie object in which the activity pertains.
     *
     * @return the movie in the activity
     */
    private Movie getMovieInfoFromActivity() {
        // This returns the movie of the activity.
        return (Movie) this.getIntent().getSerializableExtra(getString(R.string.intent_movie_string));
    }


    /**
     * This method checks to see if the activity has information about a specific movie.
     *
     * @return <tt>true</tt> if the movie information is present
     */
    private boolean hasActivityMovieInfo() {
        // Intent to retrieve movie information.
        Intent intent = this.getIntent();

        return (intent != null && intent.hasExtra(getString(R.string.intent_movie_string)));
    }


    /**
     * The initial creation of the activity and detailing the layout.
     *
     * @param _savedInstanceState the initial call state
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}
