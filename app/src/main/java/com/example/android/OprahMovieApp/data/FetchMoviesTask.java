package com.example.android.OprahMovieApp.data;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.OprahMovieApp.MainActivity;
import com.example.android.OprahMovieApp.R;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * This class creates a separate thread on which to fetch movie data from the TMDB server.
 */
public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {
    private final int NUM_PAGES = 6;
    private WeakReference<Context> weakContext;


    public FetchMoviesTask(Context _context) {
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
                String page = getData(i, _params[0]);
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
     * Method to fetch the movie data from the TMDB server.
     *
     * @param _page   data from the server are separated into pages, this determines what page is fetched
     * @param _sortBy the method of sorting movies
     * @return the String containing the movies data
     */
    private String getData(int _page, String _sortBy) {
        String moviesData = null;

        for (int i = 1; i <= 3; i++) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader reader = null;
            final String API_KEY = weakContext.get().getResources().getString(R.string.tmdb_api_key);
            String SERVER_BASE_URL = "https://api.tmdb.org/3/movie/" + _sortBy + "?language=en&api_key=" + API_KEY + "&page=" + _page;
            Uri uri = Uri.parse(SERVER_BASE_URL);

            try {
                httpURLConnection = (HttpURLConnection) new URL(uri.toString()).openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();

                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                moviesData = buffer.toString();

            } catch (IOException e) {
                return null;
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("MainActivity", "Error closing stream", e);
                    }
                }
            }
        }

        return moviesData;
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
