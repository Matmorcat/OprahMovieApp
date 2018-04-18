package com.example.android.OprahMovieApp.favorites;

import android.content.Context;
import android.util.Log;

import com.example.android.OprahMovieApp.MainActivity;
import com.example.android.OprahMovieApp.Movie;
import com.example.android.OprahMovieApp.MovieAdapter;

import java.util.LinkedList;
import java.util.List;

public class FavoritesModel{

    private FDBInterface db;
    private MovieAdapter movieAdapter;

    public FavoritesModel(Context _context) {
        this.db = new FDBInterface(_context);
        movieAdapter = MainActivity.getMovieAdapter();
    }

    /**
     * Add a movie to the user's favorite movies
     * @param movie The movie to add to favorites
     */
    public void addMovie(Movie movie){

        //Log that a movie was added
        Log.d("addMovie", movie.toString());

        db.addEntry(movie.getMovieID());
    }

    /**
     * Get a list of all the user's favorite movies
     * @return The list of favorite movies
     */
    public List<Movie> getFavoriteMovies(){

        List<Movie> movies = new LinkedList<>();

        // Loop through all the movie titles and build objects out of them
        for (int movieID : db.getEntries()) {
            movies.add(movieAdapter.getMovieByID(movieID));
        }

        return movies;
    }

    /**
     * Check to see if a specific movie is saved in the favorite movies database
     * @return True if the movie is in favorites, false if not
     */
    public boolean isInFavoriteMovies(Movie movie){

        for (Movie favoriteMovie : getFavoriteMovies()){
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
    }
}
