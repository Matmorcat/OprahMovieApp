package com.example.android.OprahMovieApp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    private MovieAdapter movieAdapter;
    private String sort; //preference for sorting movies

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            sort = savedInstanceState.getString("USER_SORT");
        } else {
            sort = "popular";
        } // sort by popularity if new activity
        setContentView(R.layout.activity_main);
        List<Movie> items = new ArrayList<>();
        movieAdapter =
                new MovieAdapter(getApplicationContext(), R.layout.grid_view_pic, items);

        GridView gridView = (GridView) findViewById(R.id.grid_view_layout);
        gridView.setAdapter(movieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie movie = (Movie) adapterView.getAdapter().getItem(i);
                Intent detailActivityIntent = new Intent(getApplicationContext(), DetailActivity.class);
                detailActivityIntent.putExtra(getString(R.string.movie_string), movie);
                startActivity(detailActivityIntent);


            }
        });

        List<Movie> movies = (List<Movie>) getLastCustomNonConfigurationInstance();
        if (movies == null) {
            FetchMoviesTask fetchMoviesTask = new FetchMoviesTask();
            fetchMoviesTask.execute(sort);
        } else {
            movieAdapter.updateValues(movies);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current sort state
        savedInstanceState.putString("USER_SORT", sort);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_sort) {
            if (sort.equals("popular")) {
                sort = "top_rated";
                FetchMoviesTask fetchMoviesTask = new FetchMoviesTask();
                fetchMoviesTask.execute(sort);
                item.setTitle(R.string.sort_popularity);
            } else {
                sort = "popular";
                FetchMoviesTask fetchMoviesTask = new FetchMoviesTask();
                fetchMoviesTask.execute(sort);
                item.setTitle(R.string.sort_user_rating);
            }

        }


        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... params) {
            //fetch three pages of results
            List<Movie> Movies = new ArrayList<>();
            try {
                for (int i = 1; i < 4; i++) {
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


        private String getData(int page, String sortBy) {
            String moviesData = null;
            for (int i = 1; i <= 3; i++) {
                HttpURLConnection httpURLConnection = null;
                BufferedReader reader = null;
                final String API_KEY = getString(R.string.tmdb_api_key);
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


        @Override
        protected void onPostExecute(List<Movie> result) {
            if (result != null) {
                movieAdapter.updateValues(result);
            }
        }


    }
}




