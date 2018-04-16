package com.example.android.OprahMovieApp.favorites;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.OprahMovieApp.Movie;

public class FavoritesModel {


    /**
     * Add a favorite movie to the favorite movies database
     * @param movie The movie to add to favorites
     */
    public void addMovie(Movie movie){

        //Log that a movie was added
        Log.d("addMovie", movie.toString());

        // Get the reference to writable database
        SQLiteDatabase db = FDBInterface.getWritableDatabase();

        // Create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(FDBInfo.KEY_TITLE, movie.getTitle()); // get title

        // Insert into the table
        db.insert(FDBInfo.TABLE_NAME, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        db.close();
    }
}
