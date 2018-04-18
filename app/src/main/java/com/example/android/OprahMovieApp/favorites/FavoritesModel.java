package com.example.android.OprahMovieApp.favorites;

import android.content.Context;
import android.util.Log;

import com.example.android.OprahMovieApp.Movie;
import com.example.android.OprahMovieApp.MovieAdapter;

import java.util.LinkedList;
import java.util.List;

public class FavoritesModel{

    private FDBInterface db;
    private MovieAdapter movieAdapter;

    public FavoritesModel(Context _context, MovieAdapter _movieAdapter) {
        this.db = new FDBInterface(_context);
        this.movieAdapter = _movieAdapter;
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
     * Remove a movie from the user's favorite movies
     * @param movie The movie to remove from favorites
     */
    public void removeMovie(Movie movie){}
}
