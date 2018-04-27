package com.oprahs_voice.android.movies.models.main;
/**
 * This class processes the data retrieved from the FetchMovieData< to create <tt>Movie</tt>
 * objects for the <tt>MovieAdapter</tt>.
 *
 * @authors
 * @date
 * @see FetchMovieData
 * @see MovieAdapter
 */
import com.oprahs_voice.android.movies.utilities.Movie;

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
        JSONArray results = jsonObject.getJSONArray(RESULTS_KEY);
        for (int i = 0; i < results.length(); i++) {
            JSONObject movieJsonObject = results.getJSONObject(i);
            Movie movie = new Movie(movieJsonObject.getInt(MOVIE_ID), movieJsonObject.getString(ORIGINAL_TITLE_KEY), movieJsonObject.getDouble(VOTE_AVERAGE_KEY), movieJsonObject.getString(RELEASE_DATE_KEY), movieJsonObject.getString(OVERVIEW_KEY), movieJsonObject.getString(POSTER_PATH_KEY));
            movies.add(movie);
        }

        return movies;
    }

    public Movie getMovie() throws JSONException {
        Movie movie = new Movie(jsonObject.getInt(MOVIE_ID), jsonObject.getString(ORIGINAL_TITLE_KEY), jsonObject.getDouble(VOTE_AVERAGE_KEY), jsonObject.getString(RELEASE_DATE_KEY), jsonObject.getString(OVERVIEW_KEY), jsonObject.getString(POSTER_PATH_KEY));
        return movie;
    }


    // Each page has data for 20 movies.
    public int getPage() throws JSONException {
        return this.jsonObject.getInt(PAGE_NUMBER_KEY);
    }
}


