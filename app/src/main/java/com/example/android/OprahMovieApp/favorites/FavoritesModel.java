package com.example.android.OprahMovieApp.favorites;

import android.content.Context;
import android.util.Log;

import com.example.android.OprahMovieApp.Views.MainActivity;
import com.example.android.OprahMovieApp.MainModel.Movie;
import com.example.android.OprahMovieApp.exceptions.MovieFavoritesException;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * This class has a collection of public methods that retrieve information about movies saved in the
 * favorites list of the application. There are methods to add, remove, retrieve a list of
 * movies, and check if a movie is stored in the database via a local database interface.
 *
 * @see FDBInterface
 */
public class FavoritesModel {

    private FDBInterface db;                                // Reference to the database interface.
    private WeakReference<Context> weakContext;                                // Reference to the app main.
    private List<Integer> movieCache = new LinkedList<>();  // Cache of the favorite movies database.


    public FavoritesModel(Context _context) {
        this.db = new FDBInterface(_context);
        this.weakContext = new WeakReference<>(_context);
    }


    /**
     * Add a movie to the user's favorite movies.
     *
     * @param _movie the movie to add to favorites
     */
    public void addMovie(Movie _movie) throws MovieFavoritesException {

        // Log that a movie was added.
        Log.d("addMovie", _movie.toString());

        // Check to see if the movie selected is already in the favorites list.
        if (!isInFavoriteMovies(_movie)) {

            // If the movie is not in the favorites list, add it to the database and cache.
            this.db.addEntry(_movie.getMovieID());
            this.movieCache.add(_movie.getMovieID());

        } else {

            // If the movie is already in the favorites list, throw an exception.
            throw new MovieFavoritesException("Tried to add movie to favorites, but the movie is already in favorites.");
        }
    }


    /**
     * Get a list of all the user's favorite movies.
     *
     * @return the list of favorite movies
     */
    public List<Movie> getFavoriteMovies() {

        // Check if the data is not cached in memory (reduces queries to the database).
        // FavoritesModel is the only way movies get added/removed, so the cache should never de-sync.
        if (this.movieCache.isEmpty()) {

            // Load all the movie IDs from the database into a cache.
            this.movieCache.addAll(this.db.getEntries());
        }

        // Create a new list to assemble Movie objects from IDs in memory.
        List<Movie> movies = new LinkedList<>();

        for (int movieID : this.movieCache) {
            movies.add(MainActivity.getMovieAdapter().getMovieByID(movieID));
        }

        return movies;
    }


    /**
     * Check to see if a specific movie is saved in the favorite movies database.
     *
     * @return <tt>true</tt> if the movie is in favorites
     */
    public boolean isInFavoriteMovies(Movie _movie) {

        for (Movie favoriteMovie : getFavoriteMovies()) {

            // Compare movie IDs.
            if (_movie.getMovieID() == favoriteMovie.getMovieID()) {
                return true;
            }
        }

        return false;
    }


    /**
     * Remove a movie from the user's favorite movies.
     *
     * @param _movie the movie to remove from favorites
     */
    public void removeMovie(Movie _movie) throws MovieFavoritesException {

        // Log that a movie was removed.
        Log.d("removeMovie", _movie.toString());

        // Check to see if the movie selected is in the favorites list.
        if (isInFavoriteMovies(_movie)) {

            // If the movie is in the favorites list, add it to the database and cache.
            this.db.removeEntry(_movie.getMovieID());
            for (int i = 0; i < this.movieCache.size(); i++) {
                if (this.movieCache.get(i) == _movie.getMovieID()) {
                    this.movieCache.remove(i);
                }
            }

        } else {

            // If the movie is not in the favorites list, throw an exception.
            throw new MovieFavoritesException("Tried to remove movie from favorites, but the movie is not in favorites.");
        }
    }


}
