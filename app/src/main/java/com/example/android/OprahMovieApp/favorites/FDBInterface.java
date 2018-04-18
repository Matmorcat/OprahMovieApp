package com.example.android.OprahMovieApp.favorites;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class FDBInterface extends SQLiteOpenHelper {

    FDBInterface(Context context) {
        super(context, FDBInfo.DATABASE_NAME, null, FDBInfo.DATABASE_VERSION);
    }

    /**
     * If there is no existing table for storing favorite movies, this makes a new one
     * @param db The database for the app
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL command to create a favorite movies database
        String CREATE_FAVORITE_MOVIES = "CREATE TABLE " + FDBInfo.TABLE_NAME +
                " ( " + FDBInfo.KEY_MOVIE_ID + " INTEGER )";

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
        db.execSQL("DROP TABLE IF EXISTS " + FDBInfo.TABLE_NAME);

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
        values.put(FDBInfo.KEY_MOVIE_ID, id);

        // Insert into the table
        db.insert(FDBInfo.TABLE_NAME,
                null,
                values);

        db.close();
    }

    /**
     * Get a list of all movies in the favorite movies database
     * @return A list of all movie IDs
     */
    public List<Integer> getEntries() {
        List<Integer> movies = new LinkedList<>();

        // Make a query
        String query = "SELECT  * FROM " + FDBInfo.TABLE_NAME;

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

        db.close();
        cursor.close();

        return movies;
    }

}
