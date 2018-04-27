package com.oprahs_voice.android.movies.utilities.exceptions;

import java.io.IOException;


/**
 * This is thrown when something attempts to remove a movie from the favorites list, but the movie
 * does not exist.
 *
 * @author Matthew Moretz
 * @author Luke Orr
 * @date April 27th, 2018
 */
public class MovieFavoritesException extends IOException {
    public MovieFavoritesException(String _message) {
        super(_message);
    }
}