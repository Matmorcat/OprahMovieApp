package com.example.android.OprahMovieApp.data;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.OprahMovieApp.MainActivity;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



/**
 * Class FetchMoviesTask creates a separate thread on which to fetch movie data from the TMDB server
 */
public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {
    private final String API_KEY = "5b64200ad380b583694cc584abe83484";
    private final int NUM_PAGES = 3;

    /**
     * Required method for AsyncTask that defines what operations are to be done on the thread
     * @param params
     * @return A list of movies
     */
    @Override
    protected List<Movie> doInBackground(String... params) {
        //fetch NUM_PAGES pages of movie data
        List<Movie> Movies = new ArrayList<>();
        try {
            for (int i = 1; i < (NUM_PAGES + 1); i++) {
                String page = getData(i, params[0]);
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
     * Method to fetch the movie data from the TMDB server
     * @param page Data from the server are separated into pages, this determines what page is fetched
     * @param sortBy The method of sorting movies
     * @return The String containing the movies data
     */
    private String getData(int page, String sortBy) {
        String moviesData = null;
        for (int i = 1; i <= 3; i++) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader reader = null;
            String SERVER_BASE_URL = "https://api.tmdb.org/3/movie/" + sortBy + "?language=en&api_key=" + API_KEY + "&page=" + page;
            Uri uri = Uri.parse(SERVER_BASE_URL);
            try {
                httpURLConnection = (HttpURLConnection) new URL(uri.toString()).openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
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
     * it sends the movie data to the movie adapter
     * @param result
     */
    @Override
    protected void onPostExecute(List<Movie> result) {
        if (result != null) {
            MainActivity.getMovieAdapter().updateValues(result);
        }
    }


}
