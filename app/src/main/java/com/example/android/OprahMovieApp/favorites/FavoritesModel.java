package com.example.android.OprahMovieApp.favorites;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.android.OprahMovieApp.MainActivity;
import com.example.android.OprahMovieApp.R;
import com.example.android.OprahMovieApp.data.Movie;

import java.util.LinkedList;
import java.util.List;

public class FavoritesModel{

    private FDBInterface db; // Reference to the database interface
    private Context context; // Reference to the app main
    private List<Integer> movieCache = new LinkedList<>(); // Cache of favorite movies in the database

    public FavoritesModel(Context _context) {
        this.db = new FDBInterface(_context);
        this.context = _context;
    }

    /**
     * Add a movie to the user's favorite movies
     * @param movie The movie to add to favorites
     */
    public void addMovie(Movie movie) {

        // Log that a movie was added.
        Log.d("addMovie", movie.toString());

        // Check to see if the movie selected is already in the favorites list.
        if (isInFavoriteMovies(movie)) {

            //Toast to display confirmation that movie is already in favorites.
            Toast.makeText(context.getApplicationContext(), R.string.favorites_toast_exists, Toast.LENGTH_SHORT).show();

        } else {

            // If the movie is not in the favorites list, add it to the database and cache.
            db.addEntry(movie.getMovieID());
            movieCache.add(movie.getMovieID());

            // Toast to display confirmation that movie has been added to favorites.
            Toast.makeText(context.getApplicationContext(), R.string.favorites_toast_added, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Get a list of all the user's favorite movies
     * @return The list of favorite movies
     */
    public List<Movie> getFavoriteMovies() {

        // Check if the data is not cached in memory (reduces queries to the database).
        // FavoritesModel is the only way movies get added/removed, so the cache should never de-sync.
        if (movieCache.isEmpty()) {

            // Load all the movie IDs from the database into a cache.
            movieCache.addAll(db.getEntries());
        }

        // Create a new list to assemble Movie objects from IDs in memory.
        List<Movie> movies = new LinkedList<>();

        for (int movieID : movieCache) {
            movies.add(MainActivity.getMovieAdapter().getMovieByID(movieID));
        }

        return movies;
    }

    /**
     * Check to see if a specific movie is saved in the favorite movies database
     * @return True if the movie is in favorites, false if not
     */
    public boolean isInFavoriteMovies(Movie movie) {

        for (Movie favoriteMovie : getFavoriteMovies()) {

            // Compare movie IDs.
            if (movie.getMovieID() == favoriteMovie.getMovieID()) {
                return true;
            }
        }

        return false;
    }


    /**
     * Remove a movie from the user's favorite movies
     * @param movie The movie to remove from favorites
     */
    public void removeMovie(Movie movie) {

        // Log that a movie was removed.
        Log.d("removeMovie", movie.toString());

        // Check to see if the movie selected is in the favorites list.
        if (isInFavoriteMovies(movie)) {

            // If the movie is in the favorites list, add it to the database and cache.
            db.removeEntry(movie.getMovieID());
            for (int i = 0; i < movieCache.size(); i++) {
                if (movieCache.get(i) == movie.getMovieID()) {
                    movieCache.remove(i);
                }
            }

            // Toast to display confirmation that movie has been added to favorites.
            Toast.makeText(context.getApplicationContext(), R.string.favorites_toast_removed, Toast.LENGTH_SHORT).show();

        } else {

            // Toast to display confirmation that movie is already in favorites.
            Toast.makeText(context.getApplicationContext(), R.string.favorites_toast_exists_false, Toast.LENGTH_SHORT).show();
        }
    }


}
