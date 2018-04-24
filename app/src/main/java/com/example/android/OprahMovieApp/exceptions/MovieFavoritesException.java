package com.example.android.OprahMovieApp.exceptions;
/**
 * Last Date Modified:
 * This is thrown when something attempts to remove a movie from the favorites list, but the movie
 * does not exist.
 * Contributing Authors:
 */
import java.io.IOException;


public class MovieFavoritesException extends IOException {
    public MovieFavoritesException(String message) {
        super(message);
    }
}