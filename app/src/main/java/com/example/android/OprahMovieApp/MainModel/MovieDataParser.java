package com.example.android.OprahMovieApp.MainModel;
/**
 * Last Date Modified:
 * This class processes the data retrieved from the FetchMovieData< to create <tt>Movie</tt>
 * objects for the <tt>MovieAdapter</tt>.
 * Contributing Authors:
 * @see FetchMovieData
 * @see MovieAdapter
 */
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MovieDataParser {
    private static final String RESULTS_KEY = "results";
    private static final String PAGE_NUMBER_KEY = "page";
    private static final String MOVIE_ID = "id";
    private static final String ORIGINAL_TITLE_KEY = "original_title";
    private static final String VOTE_AVERAGE_KEY = "vote_average";
    private static final String RELEASE_DATE_KEY = "release_date";
    private static final String OVERVIEW_KEY = "overview";
    private static final String POSTER_PATH_KEY = "poster_path";
    private final JSONObject jsonObject;


    public MovieDataParser(String _data) throws JSONException {
        this.jsonObject = new JSONObject(_data);
    }


    public List<Movie> getMovies() throws JSONException {
        List<Movie> movies = new ArrayList<>();
        JSONArray results = this.jsonObject.getJSONArray(RESULTS_KEY);
        for (int i = 0; i < results.length(); i++) {
            JSONObject movieJsonObject = results.getJSONObject(i);
            Movie movie = new Movie(movieJsonObject.getInt(MOVIE_ID), movieJsonObject.getString(ORIGINAL_TITLE_KEY), movieJsonObject.getDouble(VOTE_AVERAGE_KEY), movieJsonObject.getString(RELEASE_DATE_KEY), movieJsonObject.getString(OVERVIEW_KEY), movieJsonObject.getString(POSTER_PATH_KEY));
            movies.add(movie);
        }

        return movies;
    }


    // Each page has data for 20 movies.
    public int getPage() throws JSONException {
        return this.jsonObject.getInt(PAGE_NUMBER_KEY);
    }
}


