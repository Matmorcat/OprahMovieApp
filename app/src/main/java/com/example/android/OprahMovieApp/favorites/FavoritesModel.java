package com.example.android.OprahMovieApp.favorites;

import android.content.Context;
import android.util.Log;

import com.example.android.OprahMovieApp.Movie;

public class FavoritesModel {

    FDBInterface db;

    public FavoritesModel(Context context) {
        db = new FDBInterface(context);
    }

    /**
     * Add a favorite movie to the favorite movies database
     * @param movie The movie to add to favorites
     */
    public void addMovie(Movie movie){

        //Log that a movie was added
        Log.d("addMovie", movie.toString());

        db.addEntry(movie.getTitle());
    }

    //public Movie[] getFavoriteMovies(){}

    public void removeMovie(Movie movie){}
}
