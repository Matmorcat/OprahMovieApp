package com.example.android.OprahMovieApp.favorites;

import android.content.Context;
import android.util.Log;

import com.example.android.OprahMovieApp.Movie;

import java.util.List;

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

    public List<Movie> getFavoriteMovies(){

        // Loop through all the movie titles and build objects out of them
        for(String movie : db.getEntries()){
            Movie movie = new Movie();
        }
    }

    public void removeMovie(Movie movie){}
}
