package com.example.android.OprahMovieApp.Interfaces;

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
    public ServerInterface(Context _context, String _API_KEY) {
        weakContext = new WeakReference<>(_context);
    }
    public String getData(int page, String sortBy) {
        String moviesData = null;
        for (int i = 1; i <= 3; i++) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader reader = null;
            final String API_KEY = weakContext.get().getResources().getString(R.string.tmdb_api_key);
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

}
