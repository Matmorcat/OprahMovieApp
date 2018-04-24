package com.example.android.OprahMovieApp.Interfaces;
/**
 * Last Date Modified:
 * This is an interface that is used to access and fetch movie data from a server.
 * Contributing Authors:
 *
 */
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.android.OprahMovieApp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;


public class ServerInterface {
    private WeakReference<Context> weakContext;
    public ServerInterface(WeakReference<Context> _weakContext, String _API_KEY) {
        this.weakContext = _weakContext;
    }
    /**
     * Method to fetch the movie data from the TMDB server.
     *
     * @param _page   data from the server are separated into pages, this determines what page is fetched
     * @param _sortBy the method of sorting movies
     * @return the String containing the movies data
     */
    public String getSortedMovies(int _page, String _sortBy) {
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
    //TODO finish this and get it working properly -- currently hasn't been implemented in doInBackground
    private String getMovieByID (int _id) {
        String movieData = null;
        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;
        final String API_KEY = weakContext.get().getResources().getString(R.string.tmdb_api_key);
        String SERVER_BASE_URL ="api.themoviedb.org/3/movie/" + _id + "?api_key=" + API_KEY;
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

            movieData = buffer.toString();

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
        return movieData;
    }
}
