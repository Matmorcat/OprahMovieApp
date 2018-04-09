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


/**
 * Class MainActivity represents the view and controller for the main screen of the application
 */
public class MainActivity extends AppCompatActivity {
    private MovieAdapter movieAdapter;
    private String sort; //preference for sorting movie

    /**
     * onCreate is the Android system callback method that is called upon creation of the application
     * @param savedInstanceState any saved information from a previous run, i.e. if the device is
     *                           rotated, a new instance of the app is created with saved information
     *                           from the previous run
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //there is some information from a previous build
        if (savedInstanceState != null) {

            //Sort option - default : sort by popularity
            sort = savedInstanceState.getString("USER_SORT");
        } else {
            //Sort option - default : sort by popularity
            sort = "popular";
        }
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


    /**
     * Android system callback method which is called when the app is terminated
     * @param savedInstanceState The information to be saved
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current sort state
        savedInstanceState.putString("USER_SORT", sort);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Android callback method which creates a menu in the action bar in the upper-right corner of the screen
     * @param menu A "menu" layout file
     * @return True by default
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Android callback method to handle action bar item clicks. The action bar will handle
     * clicks on the Home/Up button
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

        if (id == R.id.action_favorites) {
            Intent favoritesActivityIntent = new Intent(getApplicationContext(), FavoritesActivity.class);
            startActivity(favoritesActivityIntent);
        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to determine whether the device being used has internet access
     * @return True if network is available, false otherwise
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Class FetchMoviesTask creates a separate thread on which to fetch movie data from the TMDB server
     */
    public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

        /**
         * Required method for AsyncTask that defines what operations are to be done on the thread
         * @param params
         * @return A list of movies
         */
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


        /**
         * AsyncTask method which defines what is to be done after finishing the task - in our case,
         * it sends the movie data to the movie adapter
         * @param result
         */
        @Override
        protected void onPostExecute(List<Movie> result) {
            if (result != null) {
                movieAdapter.updateValues(result);
            }
        }


    }
}




