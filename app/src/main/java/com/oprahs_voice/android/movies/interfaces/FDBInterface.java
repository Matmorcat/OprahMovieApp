package com.oprahs_voice.android.movies.interfaces;
/**
 * This is an interface that allows for the storage of movie IDs into a favorite movies database.
 * It uses an SQLite database via SQLiteOpenHelper which is built directly into Android.
 *
 * @authors
 * @date
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;


public class FDBInterface extends SQLiteOpenHelper {

    // Constants for SQLite connection.
    private static final int DATABASE_VERSION = 1;                    // Database Version.
    private static final String DATABASE_NAME = "FavoriteMoviesDB";   // Database Name.
    private static final String TABLE_NAME = "favorite_movies";       // Table Name.
    private static final String KEY_MOVIE_ID = "movieid";             // Movie ID Column Name.


    public FDBInterface(Context _context) {
        super(_context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * Get a list of all movies in the favorite movies database.
     *
     * @return a list of all movie IDs
     */
    public List<Integer> getEntries() {
        List<Integer> movies = new LinkedList<>();

        // Make a query.
        String query = "SELECT  * FROM " + TABLE_NAME;

        // Get the reference to writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Return all movies saved in favorites.
        Cursor cursor = db.rawQuery(query, null);

        // Scan through the rows and add each movie in database to a list.
        if (cursor.moveToFirst()) {
            do {
                movies.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }

        // Close the database.
        db.close();
        cursor.close();

        return movies;
    }


    /**
     * If there is no existing table for storing favorite movies, this makes a new one.
     *
     * @param _db the database for the app
     */
    @Override
    public void onCreate(SQLiteDatabase _db) {
        // SQL command to create a favorite movies database.
        String CREATE_FAVORITE_MOVIES = "CREATE TABLE " + TABLE_NAME +
                " ( " + KEY_MOVIE_ID + " INTEGER )";

        // Create the new table in the database.
        _db.execSQL(CREATE_FAVORITE_MOVIES);
    }


    /**
     * If the database version has changed, this drops the old table and makes a new one.
     *
     * @param _db         the database for the app
     * @param _oldVersion the old version of the database
     * @param _newVersion the new version of the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {

        // Drop an old version of the database if it exists.
        _db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create a new table.
        this.onCreate(_db);
    }


    /**
     * Add a movie to the favorite movies database.
     *
     * @param _id the movie ID
     */
    public void addEntry(int _id) {

        // Get the reference to writable database.
        SQLiteDatabase db = getWritableDatabase();

        // Create ContentValues to add key "column"/value.
        ContentValues values = new ContentValues();
        values.put(KEY_MOVIE_ID, _id);

        // Insert into the table.
        db.insert(TABLE_NAME, null, values);

        // Close the database.
        db.close();
    }


    /**
     * Remove a movie from the favorite movies database
     *
     * @param _id the movie ID
     */
    public void removeEntry(int _id) {

        // Get the reference to writable database.
        SQLiteDatabase db = getWritableDatabase();

        // Delete the movie.
        db.delete(TABLE_NAME, KEY_MOVIE_ID + " = ?", new String[]{String.valueOf(_id)});

        // Close the database.
        db.close();
    }

}
