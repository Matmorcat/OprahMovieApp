package com.example.android.OprahMovieApp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.OprahMovieApp.data.FetchMoviesTask;
import com.example.android.OprahMovieApp.data.Movie;
import com.example.android.OprahMovieApp.data.MovieAdapter;
import com.example.android.OprahMovieApp.favorites.FavoritesModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Class MainActivity represents the view and controller for the main screen of the application
 */
public class MainActivity extends AppCompatActivity {
    private static MovieAdapter movieAdapter;
    private static FavoritesModel favoritesModel;
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

            //Get sort option
            sort = savedInstanceState.getString("USER_SORT");
        } else {
            //Sort option - default : sort by popularity
            sort = "popular";
        }

        setMainScreen(R.layout.activity_main);

        // Initialize the favorites model for storing favorite movies
        favoritesModel = new FavoritesModel(this);

    }

    protected void setMainScreen (int layout) {
        setContentView(layout);

        bindAdapterToView(R.layout.grid_view_pic, R.id.grid_view_layout);

    }

    protected void bindAdapterToView (int imageLayout, int viewLayout) {
        List<Movie> items = new ArrayList<>();
        movieAdapter =
                new MovieAdapter(getApplicationContext(), imageLayout, items);
        Log.d("bindAdapterToView", "Adapter created");

        GridView gridView = (GridView) findViewById(viewLayout);
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

        // Change sorting order of movies
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

        // Take the user to the favorites view
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
    private boolean isNetworkAvailable() throws NullPointerException {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Method to return the movieAdapter member to be acted upon by FetchMoviesTask and FavoritesModel
     * @return The movieAdapter member variable
     */
    public static MovieAdapter getMovieAdapter() {
        return movieAdapter;
    }

    public static FavoritesModel getFavoritesModel(){
        return favoritesModel;
    }
}




