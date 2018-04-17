package com.example.android.OprahMovieApp.favorites;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// TODO: Create a database interface (SQLite)
public class FDBInterface extends SQLiteOpenHelper {

    public FDBInterface(Context context) {
        super(context, FDBInfo.DATABASE_NAME, null, FDBInfo.DATABASE_VERSION);
    }

    /**
     * If there is no existing table for storing favorite movies, this makes a new one
     * @param db The database for the app
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL command to create a favorite movies database
        String CREATE_FAVORITE_MOVIES_DB = "CREATE TABLE " + FDBInfo.TABLE_NAME + " ( " +
                FDBInfo.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FDBInfo.KEY_TITLE + " TEXT, " +
                " )";

        // Create the new table in the database
        db.execSQL(CREATE_FAVORITE_MOVIES_DB);
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

    protected void addEntry(String title) {

        // Get the reference to writable database
        SQLiteDatabase db = getWritableDatabase();

        // Create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(FDBInfo.KEY_TITLE, title); // Get title

        // Insert into the table
        db.insert(FDBInfo.TABLE_NAME,
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        db.close();
    }

}
