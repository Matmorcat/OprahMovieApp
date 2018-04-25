package com.oprahs_voice.android.movies.utilities.exceptions;
/**
 * This is thrown when something attempts to remove a movie from the favorites list, but the movie
 * does not exist.
 *
 * @authors
 * @date
 */
import java.io.IOException;


public class MovieFavoritesException extends IOException {
    public MovieFavoritesException(String message) {
        super(message);
    }
}