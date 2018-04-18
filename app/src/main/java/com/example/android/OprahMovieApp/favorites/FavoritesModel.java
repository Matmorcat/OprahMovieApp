package com.example.android.OprahMovieApp.favorites;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.android.OprahMovieApp.MainActivity;
import com.example.android.OprahMovieApp.R;
import com.example.android.OprahMovieApp.data.Movie;
import com.example.android.OprahMovieApp.data.MovieAdapter;

import java.util.LinkedList;
import java.util.List;

public class FavoritesModel{

    private FDBInterface db; // Reference to the database interface
    private MovieAdapter movieAdapter; // Reference to the movie info
    private Context context; // Reference to the app main
    private List<Movie> movieCache = new LinkedList<>(); // Cache of favorite movies in the database

    public FavoritesModel(Context _context) {
        this.db = new FDBInterface(_context);
        this.context = _context;
        movieAdapter = MainActivity.getMovieAdapter();
    }

    /**
     * Add a movie to the user's favorite movies
     * @param movie The movie to add to favorites
     */
    public void addMovie(Movie movie){

        //Log that a movie was added
        Log.d("addMovie", movie.toString());

        // Check to see if the movie selected is already in the favorites list
        if (isInFavoriteMovies(movie)){

            //Toast to display confirmation that movie is already in favorites
            Toast.makeText(context.getApplicationContext(), R.string.favorites_toast_exists, Toast.LENGTH_SHORT).show();

        } else {

            // If the movie is not in the favorites list, add it to the database and cache
            db.addEntry(movie.getMovieID());
            movieCache.add(movie);

            // Toast to display confirmation that movie has been added to favorites
            Toast.makeText(context.getApplicationContext(), R.string.favorites_toast_added, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Get a list of all the user's favorite movies
     * @return The list of favorite movies
     */
    public List<Movie> getFavoriteMovies(){

        // If the data is not cached in memory (reduces queries to the database)
        // FavoritesModel is the only way movies get added/removed, so the cache should never de-sync
        if (movieCache.isEmpty()) {

            // Loop through all the movie titles and build objects out of them
            for (int movieID : db.getEntries()) {
                movieCache.add(movieAdapter.getMovieByID(movieID));
            }
        }

        return movieCache;
    }

    /**
     * Check to see if a specific movie is saved in the favorite movies database
     * @return True if the movie is in favorites, false if not
     */
    public boolean isInFavoriteMovies(Movie movie){

        for (Movie favoriteMovie : getFavoriteMovies()){

            // Compare movie IDs
            if (movie.getMovieID() == favoriteMovie.getMovieID()){
                return true;
            }
        }

        return false;
    }


    /**
     * Remove a movie from the user's favorite movies
     * @param movie The movie to remove from favorites
     */
    public void removeMovie(Movie movie){

        //Log that a movie was removed
        Log.d("removeMovie", movie.toString());

        db.removeEntry(movie.getMovieID());
        movieCache.remove(movie);

        // Check to see if the movie selected is in the favorites list
        if (isInFavoriteMovies(movie)){

            // If the movie is in the favorites list, remove it from the database and cache
            db.removeEntry(movie.getMovieID());
            movieCache.remove(movie);

            // Toast to display confirmation that movie has been removed from favorites
            Toast.makeText(context.getApplicationContext(), R.string.favorites_toast_removed, Toast.LENGTH_SHORT).show();

        } else {

            //Toast to display confirmation that movie is already in favorites
            Toast.makeText(context.getApplicationContext(), R.string.favorites_toast_exists_false, Toast.LENGTH_SHORT).show();
        }
    }
}
