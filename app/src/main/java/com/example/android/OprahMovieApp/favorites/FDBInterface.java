package com.example.android.OprahMovieApp.favorites;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.LinkedList;
import java.util.List;

/**
 * An interface that allows for the storage of movie IDs into a favorite movies database
 * Uses an SQLite database via SQLiteOpenHelper, built into Android.
 * This is used exclusively by FavoritesModel.
 */
public class FDBInterface extends SQLiteOpenHelper {

    // Constants for SQLite connection
    private static final int DATABASE_VERSION = 1;                    // Database Version
    private static final String DATABASE_NAME = "FavoriteMoviesDB";   // Database Name
    private static final String TABLE_NAME = "favorite_movies";       // Table Name
    private static final String KEY_MOVIE_ID = "movieid";             // Movie ID Column Name

    FDBInterface(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * If there is no existing table for storing favorite movies, this makes a new one
     * @param db The database for the app
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL command to create a favorite movies database
        String CREATE_FAVORITE_MOVIES = "CREATE TABLE " + TABLE_NAME +
                " ( " + KEY_MOVIE_ID + " INTEGER )";

        // Create the new table in the database
        db.execSQL(CREATE_FAVORITE_MOVIES);
    }

    /**
     * If the database version has changed, this drops the old table and makes a new one
     * @param db The database for the app
     * @param oldVersion The old version of the database
     * @param newVersion The new version of the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop an old version of the database if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create a new table
        this.onCreate(db);
    }

    /**
     * Add a movie to the favorite movies database
     * @param id The movie ID
     */
    protected void addEntry(int id) {

        // Get the reference to writable database
        SQLiteDatabase db = getWritableDatabase();

        // Create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_MOVIE_ID, id);

        // Insert into the table
        db.insert(TABLE_NAME,
                null,
                values);

        // Close the database
        db.close();
    }

    /**
     * Get a list of all movies in the favorite movies database
     * @return A list of all movie IDs
     */
    public List<Integer> getEntries() {
        List<Integer> movies = new LinkedList<>();

        // Make a query
        String query = "SELECT  * FROM " + TABLE_NAME;

        // Get the reference to writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Return all movies saved in favorites
        Cursor cursor = db.rawQuery(query, null);

        // Scan through the rows and add each movie in database to a list
        if (cursor.moveToFirst()) {
            do {
                movies.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }

        // Close the database
        db.close();
        cursor.close();

        return movies;
    }

    /**
     * Remove a movie from the favorite movies database
     * @param id The movie ID
     */
    protected void removeEntry(int id) {

        // Get the reference to writable database
        SQLiteDatabase db = getWritableDatabase();

        // Delete the movie
        db.delete(TABLE_NAME,
                KEY_MOVIE_ID + " = ?",
                new String[] { String.valueOf(id) });

        // Close the database
        db.close();
    }

}
