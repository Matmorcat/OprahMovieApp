package com.example.android.OprahMovieApp.favorites;

// Favorite Database Info
public class FDBInfo {

    // Database version
    protected static final int DATABASE_VERSION = 1;
    // Database name
    protected static final String DATABASE_NAME = "FavoriteMoviesDB";


    // Table name
    protected static final String TABLE_NAME = "favorite_movies";

    // Columns of table
    protected static final String KEY_MOVIE_ID = "movieid";

    protected static final String[] COLUMNS = {KEY_MOVIE_ID};
}
