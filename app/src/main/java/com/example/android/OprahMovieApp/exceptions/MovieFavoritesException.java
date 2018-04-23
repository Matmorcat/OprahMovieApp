package com.example.android.OprahMovieApp.exceptions;

import java.io.IOException;

/**
 * This is thrown when something attempts to remove a movie from the favorites list, but the movie
 * does not exist.
 */
public class MovieFavoritesException extends IOException {
    public MovieFavoritesException(String message) {
        super(message);
    }
}