package com.example.android.OprahMovieApp.MainModel;
/**
 * Last Date Modified:
 * This class creates a separate thread on which to fetch movie data from the TMDB server.
 * Contributing Authors:
 */
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.OprahMovieApp.Interfaces.ServerInterface;
import com.example.android.OprahMovieApp.Views.MainActivity;
import org.json.JSONException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;



public class FetchMovieData extends AsyncTask<String, Void, List<Movie>> {
    private final int NUM_PAGES = 6;
    private WeakReference<Context> weakContext;

    public FetchMovieData(Context _context) {
        this.weakContext = new WeakReference<>(_context);
    }
    /**
     * Required method for AsyncTask that defines what operations are to be done on the thread.
     *
     * @param _params the required parameters
     * @return a list of movies
     */
    @Override
    protected List<Movie> doInBackground(String... _params) {
        List<Movie> Movies = new ArrayList<>();

        // Fetch (NUM_PAGES) pages of movie data.
        try {
            for (int i = 1; i < (this.NUM_PAGES + 1); i++) {
                ServerInterface serverInterface = new ServerInterface(weakContext, "tmdb_api_key");
                String page = serverInterface.getSortedMovies(i, _params[0]);
                MovieDataParser dataParser = new MovieDataParser(page);
                List<Movie> movies = dataParser.getMovies();
                Movies.addAll(movies);
            }
            return Movies;

        } catch (JSONException e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
    }
    /**
     * AsyncTask method which defines what is to be done after finishing the task - in our case,
     * it sends the movie data to the movie adapter.
     *
     * @param _result the list of movies to alter
     */
    @Override
    protected void onPostExecute(List<Movie> _result) {
        if (_result != null) {
            MainActivity.getMovieAdapter().updateValues(_result);
        }
    }


}
